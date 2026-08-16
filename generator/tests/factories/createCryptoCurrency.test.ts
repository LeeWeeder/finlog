import { describe, expect, it } from "vitest";
import { createCryptoCurrency } from "../../src/factories/createCryptoCurrency.js";

describe("createCryptoCurrency", () => {
  it.each([
    "BTC",
    "btc",
    "bTc"
  ])("uppercase the code", (code) => {
    const result = createCryptoCurrency({ code, name: "Bitcoin" })
    expect(result.code).toBe("BTC");
  });

  it.each([
    ["BTC", ""],
    ["", "Bitcoin"],
    ["", ""]
  ])("throws when code=%p name=%p", (code, name) => {
    expect(() => createCryptoCurrency({code, name})).toThrow();
  });

  it.each([
    ["BTC", "bitcoin   "],
    ["btc  ", "bitcoin   "],
    ["  btc", "   bitcoin"],
    [" \n btc \n\r ", " \n  bitcoin \n\r  "],
    ["btc \n  ", "bitcoin  \n "],
    [" \r\n btc", "  \r\n bitcoin"]
  ]) ("returns correct crypto currency", (code, name) => {
    const result = createCryptoCurrency({code, name});
    expect(result.code).toBe("BTC");
    expect(result.name).toBe("bitcoin");
  });
});