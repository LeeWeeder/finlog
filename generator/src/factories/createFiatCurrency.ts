import { code as getCurrencyCode } from "currency-codes";
import type { FiatCurrency } from "../types/fiat.js";

export function createFiatCurrency(code: string): FiatCurrency {
  var currencyCode = getCurrencyCode(code.trim());

  if (currencyCode === undefined) {
    throw new Error("Invalid currency code");
  }

  return {
    code: currencyCode.code,
    name: currencyCode.currency,
    digits: currencyCode.digits
  };
}