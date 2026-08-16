import type { CryptoCurrency } from "../types/crypto.js";
import { isBlank } from "../util/isBlank.js";

export function createCryptoCurrency(
  {
    code, name
  } : { code: string, name: string }
): CryptoCurrency {
  if (isBlank(code)) {
    throw new Error("Code can't be empty");
  }

  if (isBlank(name)) {
    throw new Error("Name can't be empty");
  }

  return { code: code.trim().toUpperCase(), name: name.trim() };
}