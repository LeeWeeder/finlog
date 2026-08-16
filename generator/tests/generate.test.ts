import { execSync } from "node:child_process";
import { existsSync, readFileSync } from "node:fs";
import { dirname, join } from "node:path";
import { fileURLToPath } from "node:url";
import { beforeAll, describe, expect, it } from "vitest";

const __dirname = dirname(fileURLToPath(import.meta.url));
const OUTPUT_PATH = join(__dirname, "../../shared/currencies.json");

describe("generate", () => {
  beforeAll(() => {
    execSync("npm run generate", { stdio: "pipe" });
  });

  it("creates a currencies.json file on project root shared dir", () => {
    expect(existsSync(OUTPUT_PATH)).toBe(true);
  });

  it("produces valid, parseable JSON", () => {
    const raw = readFileSync(OUTPUT_PATH, "utf-8");
    expect(() => JSON.parse(raw)).not.toThrow();
  });
});