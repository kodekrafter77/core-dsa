import java.util.*;

public class WordBreak {

    public static boolean wordBreak(String s, List<String> wordDict) {
        // Set<String> words = new HashSet<>(wordDict);
        // Map<String, Boolean> memo = new HashMap<>();
        // return canBreak(s, words, memo);

        Set<String> dict = new HashSet<>(wordDict);
        int n = s.length();

        // 1. Define DP array. dp[i] = can s.substring(0, i) be broken down?
        boolean[] dp = new boolean[n + 1];

        // 2. Set the base case.
        dp[0] = true;

        // 3. Outer loop to solve for each dp[i] from 1 to n.
        for (int i = 1; i <= n; i++) {
            // 4. Inner loop to check all possible split points j.
            for (int j = 0; j < i; j++) {
                // 5. The translated condition:
                // Check if the first part (dp[j]) is valid...
                // ...AND the second part (substring from j to i) is a word.
                if (dp[j] && dict.contains(s.substring(j, i))) {
                    dp[i] = true;
                    break; // Optimization: we found a way, so we can stop checking splits for i.
                }
            }
        }

        // The final answer is the answer for the entire string of length n.
        return dp[n];
    }

    private static boolean canBreak(String s, Set<String> words, Map<String, Boolean> memo) {
        if (s.isEmpty()) return true; // empty string trivially matches

        if (memo.containsKey(s)) return memo.get(s);

        for (int i = 1; i <= s.length(); ++i) {
            String prefix = s.substring(0, i);
            if (words.contains(prefix)) {
                String restOfS = s.substring(i);
                if (canBreak(restOfS, words, memo)) {
                    memo.put(s, true);
                    return true;
                }
            }
        }
        memo.put(s, false);
        return false;
    }


    public static void main(String[] args) {
        System.out.println(wordBreak("leetcode", Arrays.asList("leet", "code"))); // true

    }
}