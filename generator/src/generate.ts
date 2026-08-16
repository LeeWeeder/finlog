import { dirname, join } from "path";
import { writeFileSync } from "fs";
import { fileURLToPath } from "url";
import { fiatCurrencies } from "./data/fiat.js";
import { cryptoCurrencies } from "./data/crypto.js";
import { createOutput } from "./factories/createOutput.js";

const output = createOutput({
  schemaVersion: 1,
  fiatCurrencies: fiatCurrencies,
  cryptoCurrencies: cryptoCurrencies
})

const __filename = fileURLToPath(import.meta.url);
const __dirname = dirname(__filename);

const json = JSON.stringify(output, null, 2);
const outputPath = join(__dirname, "../../shared/currencies.json");

writeFileSync(outputPath, json, "utf-8");

console.log(`Wrote ${outputPath}`);