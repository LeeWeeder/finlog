import type { CryptoCurrency } from "../types/crypto.js";
import type { FiatCurrency } from "../types/fiat.js";
import type { Output } from "../types/output.js";
import { dedupeBy } from "../util/dedupeBy.js";

export function createOutput(
  {
    schemaVersion,
    fiatCurrencies,
    cryptoCurrencies,
    generatedAt = new Date()
  }: {
    schemaVersion: number,
    fiatCurrencies: FiatCurrency[],
    cryptoCurrencies: CryptoCurrency[],
    generatedAt?: Date
  }
): Output {
  if (schemaVersion <= 0) {
    throw new Error("schemaVersion can't be less than or equal to 0");
  }

  if (fiatCurrencies.length == 0) {
    throw new Error("fiatCurrencies can't be empty");
  }

  if (cryptoCurrencies.length == 0) {
    throw new Error("cryptoCurrencies can't be empty");
  }

  return {
    schemaVersion,
    generatedAt: generatedAt.toISOString(),
    fiatCurrencies: dedupeBy(fiatCurrencies, c => c.code),
    cryptoCurrencies: dedupeBy(cryptoCurrencies, c => `${c.code}|${c.name.toLowerCase()}`)
  };
}