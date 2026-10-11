import {
	createExecutionContext,
	waitOnExecutionContext,
} from "cloudflare:test";
import { env, exports } from "cloudflare:workers";
import { describe, it, expect } from "vitest";
import worker from "../src/index";
import currencies from "../../shared/currencies.json"

// For now, you'll need to do something like this to get a correctly-typed
// `Request` to pass to `worker.fetch()`.
const IncomingRequest = Request<unknown, IncomingRequestCfProperties>;

describe("currencies endpoint", () => {
	it("responds with the current currencies.json (unit style)", async () => {
		const request = new IncomingRequest("http://example.com/currencies");
		// Create an empty context to pass to `worker.fetch()`.
		const ctx = createExecutionContext();
		const response = await worker.fetch(request, env, ctx);
		// Wait for all `Promise`s passed to `ctx.waitUntil()` to settle before running test assertions
		await waitOnExecutionContext(ctx);
		expect(await response.json()).toEqual(currencies);
	});

	it("responds with current currencies.json (integration style)", async () => {
		const response = await exports.default.fetch("https://example.com/currencies");
		expect(await response.json()).toEqual(currencies);
	});
});
