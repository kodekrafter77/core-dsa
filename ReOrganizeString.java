import java.util.*;

public class ReOrganizeString {

   public static String reorganizeString(String s) {
        Map<Character, Integer> freqMap = new HashMap<>();
        for (char c : s.toCharArray()) {
            freqMap.put(c, freqMap.getOrDefault(c, 0) + 1);
        }

        // --- FIX #1: Correct impossibility check ---
        int maxCharCount = 0;
        for (int count : freqMap.values()) {
            maxCharCount = Math.max(maxCharCount, count);
        }
        if (maxCharCount > (s.length() + 1) / 2) {
            return "";
        }

        PriorityQueue<Map.Entry<Character, Integer>> maxHeap = 
            new PriorityQueue<>((a, b) -> b.getValue() - a.getValue());
        maxHeap.addAll(freqMap.entrySet());

        StringBuilder res = new StringBuilder();
        Map.Entry<Character, Integer> prevEntry = null;

        while (!maxHeap.isEmpty()) {
            var currEntry = maxHeap.poll();
            res.append(currEntry.getKey());
            
            // Decrement the count of the current char and hold it as prevEntry
            currEntry.setValue(currEntry.getValue() - 1);
            
            // Add the PREVIOUS char back to the heap now
            if (prevEntry != null && prevEntry.getValue() > 0) {
                maxHeap.offer(prevEntry);
            }
            
            // The current char becomes the previous char for the next iteration
            prevEntry = currEntry;
        }
        
        // --- FIX #2: Final length check ---
        return res.length() == s.length() ? res.toString() : "";
    }

    public static void main(String[] args) {
        System.out.println(reorganizeString("aab")); // aba
        System.out.println(reorganizeString("vvvlo"));
    }
}