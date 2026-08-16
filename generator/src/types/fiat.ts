import type { Currency } from "./currency.js";

export interface FiatCurrency extends Currency {
  readonly digits: number;
  readonly code: string;
  readonly name: string;
}

