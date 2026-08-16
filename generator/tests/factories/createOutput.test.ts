import { describe, expect, it } from "vitest";
import { createOutput } from "../../src/factories/createOutput.js";
import type { CryptoCurrency } from "../../src/types/crypto.js";
import type { FiatCurrency } from "../../src/types/fiat.js";

describe("createOutput", () => {
  it("throws if fiatCurrencies is empty", () => {
    expect(() => createOutput(
      {
        schemaVersion: 1,
        fiatCurrencies: [],
        cryptoCurrencies: [
          { code: "BTC", name: "Bitcoin" }
        ]
      })).toThrow();
  });

  it("throws if cryptoCurrencies is empty", () => {
    expect(() => createOutput(
      {
        schemaVersion: 1,
        fiatCurrencies: [
          { code: "USD", name: "US Dollars", digits: 2 }
        ],
        cryptoCurrencies: []
      })).toThrow();
  });

  it.each([
    0,
    -1
  ])("throws if schemaVersion is less than or equal to 0", (version) => {
    expect(() => createOutput(
      {
        schemaVersion: version,
        fiatCurrencies: [
          { code: "USD", name: "US Dollars", digits: 2 }
        ],
        cryptoCurrencies: [
          { code: "BTC", name: "Bitcoin" }
        ]
      }
    ));
  });

  it("dedupes cryptoCurrencies by code+name, not code alone", () => {
    const crypto: CryptoCurrency[] = [
      { code: "BTC", name: "Bitcoin" },
      { code: "BTC", name: "Bitcoin One" },
      { code: "BTC1", name: "Bitcoin" }
    ];

    const result = createOutput({
      schemaVersion: 1,
      cryptoCurrencies: crypto,
      fiatCurrencies: [
        { code: "USD", name: "US Dollars", digits: 2 }
      ]
    });

    expect(result.cryptoCurrencies).toHaveLength(3);
  })

  it("dedupes fiatCurrencies by code only", () => {
    const fiat: FiatCurrency[] = [
      { code: "BTC", name: "Bitcoin", digits: 2 },
      { code: "BTC", name: "Bitcoin One", digits: 2 },
      { code: "BTC1", name: "Bitcoin", digits: 2 }
    ];

    const result = createOutput({
      schemaVersion: 1,
      fiatCurrencies: fiat,
      cryptoCurrencies: [
        { code: "USD", name: "US Dollars" }
      ]
    });

    expect(result.fiatCurrencies).toHaveLength(2);
  });

  it("generates correct generateAt date if date is given", () => {
    const date = new Date();

    const result = createOutput({
      schemaVersion: 1,
      fiatCurrencies: [
        { code: "BTC1", name: "Bitcoin", digits: 2 }
      ],
      cryptoCurrencies: [
        { code: "USD", name: "US Dollars" }
      ],
      generatedAt: date
    });

    expect(result.generatedAt).toBe(date.toISOString());
  });
});