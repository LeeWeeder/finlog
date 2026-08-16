import { createCryptoCurrency } from "../factories/createCryptoCurrency.js";
import type { CryptoCurrency } from "../types/crypto.js";

export const cryptoCurrencies: CryptoCurrency[] = [
  createCryptoCurrency({code: "BTC", name: "Bitcoin"}),
  createCryptoCurrency({code: "ETH", name: "Ethereum"})
];
