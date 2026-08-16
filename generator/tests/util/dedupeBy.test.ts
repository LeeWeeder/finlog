import { describe, expect, it } from "vitest";
import { dedupeBy } from "../../src/util/dedupeBy.js";

describe("dedupeBy", () => {
  it("returns correct deduped list", () => {
    const input = [
      {
        p1: "USD"
      },
      {
        p1: "USD"
      },
      {
        p1: "PHP"
      }
    ]

    const result = dedupeBy(input, c => c.p1);
    expect(result).toHaveLength(2);
    expect(result).toEqual([{ p1: "USD" }, { p1: "PHP" }]);
  });
});