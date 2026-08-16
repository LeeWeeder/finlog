import type { Currency } from "./currency.js";

export interface CryptoCurrency extends Currency {
  readonly code: string;
  readonly name: string;
}
