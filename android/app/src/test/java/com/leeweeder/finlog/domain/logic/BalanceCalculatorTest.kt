package com.leeweeder.finlog.domain.logic

import com.leeweeder.finlog.domain.model.Amount
import com.leeweeder.finlog.domain.model.transaction.Transaction
import com.leeweeder.finlog.domain.model.transaction.TransactionType
import junit.framework.TestCase.assertEquals
import org.junit.Test
import java.math.BigDecimal
import java.math.RoundingMode

class BalanceCalculatorTest {

    private val calculator = BalanceCalculator()

    /**
     * Mirrors how real transactions are created: the amount is already at the account's scale.
     * UNNECESSARY makes the test fail loudly if a test amount has more decimals than [digits].
     */
    private fun tx(
        amount: String,
        type: TransactionType,
        digits: Int = DEFAULT_DIGITS,
        accountId: Long = 1
    ) = Transaction(
        accountId = accountId,
        amount = Amount(BigDecimal(amount).setScale(digits, RoundingMode.UNNECESSARY)),
        transactionType = type
    )

    private fun balance(digits: Int, vararg txs: Transaction): String =
        calculator.calculateBalance(txs.toList(), digits).value.toPlainString()

    private fun balance(vararg txs: Transaction): String = balance(DEFAULT_DIGITS, *txs)

    // --- Basic arithmetic (account with 2 digits) ---

    @Test
    fun `empty list gives zero at the account's scale`() {
        assertEquals("0.00", balance())
    }

    @Test
    fun `income adds`() {
        assertEquals(
            "80.00",
            balance(
                tx("20", TransactionType.Income),
                tx("20", TransactionType.Income),
                tx("20", TransactionType.Income),
                tx("20", TransactionType.Income),
            )
        )
    }

    @Test
    fun `expense subtracts`() {
        assertEquals(
            "70.00",
            balance(tx("100", TransactionType.Income), tx("30", TransactionType.Expense))
        )
    }

    @Test
    fun `adjustment increase adds and adjustment decrease subtracts`() {
        assertEquals(
            "30.00",
            balance(
                tx("50", TransactionType.AdjustmentIncrease),
                tx("20", TransactionType.AdjustmentDecrease)
            )
        )
    }

    @Test
    fun `balance can go negative`() {
        assertEquals(
            "-15.00",
            balance(tx("10", TransactionType.Income), tx("25", TransactionType.Expense))
        )
    }

    @Test
    fun `all four types together`() {
        assertEquals(
            "70.50",
            balance(
                tx("100", TransactionType.Income),
                tx("40.25", TransactionType.Expense),
                tx("15.75", TransactionType.AdjustmentIncrease),
                tx("5", TransactionType.AdjustmentDecrease)
            )
        )
    }

    @Test
    fun `balance that nets to zero keeps the scale`() {
        assertEquals(
            "0.00",
            balance(tx("10", TransactionType.Income), tx("10", TransactionType.Expense))
        )
    }

    @Test
    fun `zero amount changes nothing`() {
        assertEquals(
            "10.00",
            balance(tx("10", TransactionType.Income), tx("0", TransactionType.Expense))
        )
    }

    @Test
    fun `decimals stay exact`() {
        // 0.1 + 0.2 would not equal 0.3 with Double
        assertEquals(
            "0.30",
            balance(tx("0.1", TransactionType.Income), tx("0.2", TransactionType.Income))
        )
    }

    @Test
    fun `order does not matter`() {
        val items = arrayOf(
            tx("100", TransactionType.Income),
            tx("40", TransactionType.Expense),
            tx("15", TransactionType.AdjustmentIncrease),
        )
        assertEquals(balance(*items), balance(*items.reversedArray()))
    }

    @Test
    fun `large values do not overflow`() {
        assertEquals(
            "100000000000000000000000000.00",
            balance(
                tx("99999999999999999999999999", TransactionType.Income),
                tx("1", TransactionType.Income)
            )
        )
    }

    // --- Result scale follows the account's digits ---

    @Test
    fun `empty list returns the account's scale for any digits`() {
        assertEquals("0", balance(0))
        assertEquals("0.00", balance(2))
        assertEquals("0.0000", balance(4))
    }

    @Test
    fun `same logical amounts give a result at each account's scale`() {
        fun total(digits: Int) = balance(
            digits,
            tx("1", TransactionType.Income, digits),
            tx("2", TransactionType.Income, digits)
        )
        assertEquals("3", total(0))
        assertEquals("3.00", total(2))
        assertEquals("3.0000", total(4))
    }

    @Test
    fun `zero digits gives no decimal point`() {
        assertEquals("100", balance(0, tx("100", TransactionType.Income, digits = 0)))
    }

    @Test
    fun `sub-unit amounts are exact at their own digits`() {
        assertEquals(
            "1.375",
            balance(
                3,
                tx("1", TransactionType.Income, 3),
                tx("0.5", TransactionType.Income, 3),
                tx("0.125", TransactionType.Expense, 3).let {
                    // 1 + 0.5 - 0.125
                    it
                }
            )
        )
    }

    @Test
    fun `eighteen digits keeps full precision`() {
        assertEquals(
            "0.000000000000000003",
            balance(
                18,
                tx("0.000000000000000001", TransactionType.Income, 18),
                tx("0.000000000000000002", TransactionType.Income, 18)
            )
        )
    }

    @Test
    fun `eighteen digits pads whole numbers`() {
        assertEquals(
            "1.500000000000000000",
            balance(18, tx("1.5", TransactionType.Income, 18))
        )
    }

    // --- Safety net: amounts NOT at the account's scale (legacy or unnormalized data) ---
    // If every stored amount is normalized on input, these never trigger in the app.
    // They pin down what the calculator does if bad data gets through.

    private fun rawTx(amount: String, type: TransactionType = TransactionType.Income) =
        Transaction(accountId = 1, amount = Amount(BigDecimal(amount)), transactionType = type)

    private fun rawBalance(digits: Int, vararg txs: Transaction) = balance(digits, *txs)

    @Test
    fun `unnormalized input is rescaled to the account's digits`() {
        assertEquals("0.62", rawBalance(2, rawTx("0.5"), rawTx("0.125")))   // 0.625 -> half even
    }

    @Test
    fun `half even rounds ties to the even neighbor`() {
        assertEquals("2", rawBalance(0, rawTx("2.5")))
        assertEquals("4", rawBalance(0, rawTx("3.5")))
        assertEquals("0.12", rawBalance(2, rawTx("0.125")))
        assertEquals("0.14", rawBalance(2, rawTx("0.135")))
    }

    @Test
    fun `non-tie values round to nearest`() {
        assertEquals("1.23", rawBalance(2, rawTx("1.234")))
        assertEquals("1.24", rawBalance(2, rawTx("1.236")))
    }

    @Test
    fun `negative balances round symmetrically`() {
        assertEquals("-2", rawBalance(0, rawTx("2.5", TransactionType.Expense)))
        assertEquals("-4", rawBalance(0, rawTx("3.5", TransactionType.Expense)))
        assertEquals("-1.24", rawBalance(2, rawTx("1.236", TransactionType.Expense)))
    }

    @Test
    fun `rounding happens once on the total, not per transaction`() {
        // 0.004 + 0.004 = 0.008 -> 0.01. Rounding each first would give 0.00.
        assertEquals("0.01", rawBalance(2, rawTx("0.004"), rawTx("0.004")))
    }

    @Test
    fun `value below the precision rounds to the nearest even`() {
        assertEquals("0.00", rawBalance(2, rawTx("0.004")))
        assertEquals("0.00", rawBalance(2, rawTx("0.005")))
        assertEquals("0.02", rawBalance(2, rawTx("0.015")))
    }

    private companion object {
        const val DEFAULT_DIGITS = 2
    }
}