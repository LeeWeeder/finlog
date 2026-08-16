import type { CryptoCurrency } from "./crypto.js";
import type { FiatCurrency } from "./fiat.js";

export interface Output {
  readonly schemaVersion: number;
  readonly generatedAt: string;
  readonly fiatCurrencies: FiatCurrency[];
  readonly cryptoCurrencies: CryptoCurrency[];
}
