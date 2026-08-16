import { describe, expect, it } from "vitest";
import { createFiatCurrency } from "../../src/factories/createFiatCurrency.js";

describe("createFiatCurrency", () => {
  it.each([
    "US D",
    "123",
    "UDDF",
    "ABC"
  ]) ("throws if currency code is invalid", (code) => {
    expect(() => createFiatCurrency(code)).toThrow();
  });

  it.each([
    "USD",
    " usd ",
    " uSd"
  ]) ("returns correct fiat currency", (code) => {
    const result = createFiatCurrency(code);

    expect(result.code).toBe("USD");
    expect(result.name).toBe("US Dollar");
    expect(result.digits).toBe(2);
  });
});