import java.util.*;

public class LongestRepeatingSubstring {

    public static int longestRepeatingSubstring(String s) {
        if (s == null || s.length() == 0) return 0;
        int left = 0;
        int right = s.length();

        int res = 0;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (check(s, mid)) {
                res = mid;
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        return res;
    }

// Tougher for interview. Use check1() for interviews
    private static boolean check(String s, int k) {
        if (k == 0) return true;
        if (k > s.length()) return false;

        // Use two prime numbers for the hash calculation
        long prime = 31;
        long mod = 1_000_000_007;
        
        // Pre-calculate (prime^k) % mod for removing the leftmost character
        long power = 1;
        for (int i = 0; i < k; i++) {
            power = (power * prime) % mod;
        }

        // --- Calculate the hash of the first substring ---
        long currentHash = 0;
        for (int i = 0; i < k; i++) {
            currentHash = (currentHash * prime + s.charAt(i)) % mod;
        }

        // Store the hash of the first substring
        HashSet<Long> seenHashes = new HashSet<>();
        seenHashes.add(currentHash);

        // --- Roll the hash for the rest of the string ---
        for (int i = k; i < s.length(); i++) {
            char charLeaving = s.charAt(i - k);
            char charEntering = s.charAt(i);

            // The rolling hash formula
            currentHash = (currentHash * prime - charLeaving * power + charEntering) % mod;
            
            // Handle negative results from the modulo operation
            if (currentHash < 0) {
                currentHash += mod;
            }

            // If we've seen this hash before, we've found a repeat.
            if (seenHashes.contains(currentHash)) {
                // Note: In a real-world scenario, you might double-check the actual strings
                // to guard against rare hash collisions, but it's usually omitted in interviews.
                return true;
            }
            seenHashes.add(currentHash);
        }

        return false;
    }

    private static boolean check1(String s, int k) {
        // A substring of length 0 is not a valid repeating substring in this context.
        if (k == 0) {
            return true; // Or false, the binary search range should handle this.
        }
        
        HashSet<String> seen = new HashSet<>();
        
        // Iterate through the string to create all substrings of length k
        for (int i = 0; i <= s.length() - k; i++) {
            String substring = s.substring(i, i + k);
            
            // If the set already contains this substring, we've found a repeat.
            if (seen.contains(substring)) {
                return true;
            }
            
            // Otherwise, add it to the set.
            seen.add(substring);
        }
        
        // If we finish the loop, no repeating substring of length k was found.
        return false;
    }

    public static void main(String[] args) {
        System.out.println(longestRepeatingSubstring("abcd")); // 0
        System.out.println(longestRepeatingSubstring("aabcaabdaab")); // 3
        System.out.println(longestRepeatingSubstring("aaaaa")); // 4
    }
}