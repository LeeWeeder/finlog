export function dedupeBy<T, K>(items: T[], keyFn: (item: T) => K): T[] {
  const map = new Map<K, T>();
  for (const item of items) {
    const key = keyFn(item);
    if (!map.has(key)) map.set(key, item);
  }

  return [...map.values()];
}
