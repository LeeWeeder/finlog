import { describe, expect, it } from "vitest";
import { isBlank } from "../../src/util/isBlank.js";

describe("isBlank", () => {
  it.each([
    "",
    "     "
  ]) ("returns true if value=%p", (value) => {
    expect(isBlank(value)).toBe(true);
  });
  
  it.each([
    "BTC",
    "BITCOIN",
    "      BITOINC",
    "BITCOIN        "
  ]) ("returns false if value=%p", (value) => {
    expect(isBlank(value)).toBe(false);
  });
});