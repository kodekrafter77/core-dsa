import java.util.*;

public class PalindromePartitioning {

    public static List<List<String>> partition(String s) {
        List<List<String>> results = new ArrayList<>();
        int n = s.length();
        boolean[][] dp = new boolean[n][n];
       for (int j = 0; j < n; j++) {
            for (int i = 0; i <= j; i++) {
                // Check if outer characters match AND the inner part is a palindrome.
                // The (j - i <= 2) handles base cases for lengths 1, 2, and 3.
                if (s.charAt(i) == s.charAt(j) && (j - i <= 2 || dp[i + 1][j - 1])) {
                    dp[i][j] = true;
                }
            }
        }
        backtrack(s, 0, new ArrayList<>(), dp, results);
        return results;
    }

    private static void backtrack(String s, int start, List<String> currentPartition, boolean[][] dp, List<List<String>> results) {
        // Base case: if we've reached the end of the string
        if (start == s.length()) {
            results.add(new ArrayList<>(currentPartition));
            return;
        }

        // Recursive step: try every possible "cut"
        for (int i = start; i < s.length(); i++) {
            String sub = s.substring(start, i + 1);
            // if (isPalindrome(sub)) {
            if (dp[start][i]) {
                // Choose
                currentPartition.add(sub);
                // Explore
                backtrack(s, i + 1, currentPartition,dp,  results);
                // Unchoose (backtrack)
                currentPartition.remove(currentPartition.size() - 1);
            }
        }
    }

    // Your helper function
    private static boolean isPalindrome(String s) {
        int left = 0;
        int right = s.length() - 1;
        while (left < right) {
            if (s.charAt(left) != s.charAt(right)) {
                return false;
            }
            left++;
            right--;
        }
        return true;
    }

    public static void main(String[] args) {
        System.out.println(partition("aab"));
        System.out.println(partition("a"));
        System.out.println(partition("malayalam"));
    }
}