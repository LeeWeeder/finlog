import { createFiatCurrency } from "../factories/createFiatCurrency.js";
import type { FiatCurrency } from "../types/fiat.js";

export const fiatCurrencies: FiatCurrency[] = [
  createFiatCurrency("PHP"),
  createFiatCurrency("USD"),
  createFiatCurrency("EUR")
];
