package com.leeweeder.finlog.data.local

import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment

@RunWith(RobolectricTestRunner::class)
class CurrenciesJsonAssetBundlingTest {
    @Test
    fun `currencies json is bundled and readable`() {
        val context = RuntimeEnvironment.getApplication()
        val text = context.assets.open("currencies.json").bufferedReader().use { it.readText() }
        assertTrue( "currencies.json should not be empty", text.isNotBlank())
    }
}