import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;

class LFUCache {
    private final Map<Integer, Integer> values;      // key -> value
    private final Map<Integer, Integer> counts;      // key -> frequency count
    private final Map<Integer, LinkedHashSet<Integer>> freqs; // frequency -> keys in LRU order
    private final int capacity;
    private int minFrequency;

    public LFUCache(int capacity) {
        this.capacity = capacity;
        this.minFrequency = 0;
        this.values = new HashMap<>();
        this.counts = new HashMap<>();
        this.freqs = new HashMap<>();
        this.freqs.put(1, new LinkedHashSet<>()); // Initialize for frequency 1
    }

    public int get(int key) {
        if (!values.containsKey(key)) {
            return -1;
        }
        updateFrequency(key);
        return values.get(key);
    }

    public void put(int key, int value) {
        if (capacity <= 0) {
            return;
        }

        if (values.containsKey(key)) {
            values.put(key, value); // Update existing key's value
            updateFrequency(key);   // and update its frequency
            return;
        }

        // --- Handle new key insertion ---
        if (values.size() >= capacity) {
            // Evict the LFU item
            int keyToEvict = freqs.get(minFrequency).iterator().next();
            freqs.get(minFrequency).remove(keyToEvict);
            values.remove(keyToEvict);
            counts.remove(keyToEvict);
        }

        // Add the new item
        values.put(key, value);
        counts.put(key, 1);
        minFrequency = 1; // A new item always has the lowest frequency
        freqs.get(1).add(key);
    }

    private void updateFrequency(int key) {
        int oldCount = counts.get(key);
        int newCount = oldCount + 1;

        // Move key from old frequency list to new one
        freqs.get(oldCount).remove(key);
        counts.put(key, newCount);

        // Create new frequency list if it doesn't exist
        freqs.computeIfAbsent(newCount, k -> new LinkedHashSet<>());
        freqs.get(newCount).add(key);

        // Update minFrequency if the old frequency list is now empty
        if (freqs.get(oldCount).isEmpty() && oldCount == minFrequency) {
            minFrequency++;
        }
    }
}
