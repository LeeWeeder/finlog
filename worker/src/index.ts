import { Hono } from "hono";
import currencies from "../../shared/currencies.json";

const app = new Hono();

app.get(
  "/currencies",
  (c) => c.json(currencies, 200, {
    'Cache-Control': 'public, max-age=31536000, immutable'
  }));

export default app;