import java.util.*;

public class DistinctSubsequences {

     public static int numDistinct(String s, String t) {
        int n = s.length();
        int m = t.length();

        long[] dp = new long[m + 1];

        dp[0] = 1;

        for (int i = 1; i <= n; ++i) {
            for (int j = m; j >= 1; --j) {
                if (s.charAt(i - 1) == t.charAt(j - 1)) {
                    dp[j] += dp[j - 1];
                }
            }
        }
        return (int) dp[m];
    }

    public static int numDistinctDP1(String s, String t) {
        int sLen = s.length();
        int tLen = t.length();

        // dp[i][j] = # of subsequences of s.substring(0, j) that equal t.substring(0, i)
        // Using long to prevent overflow, but the final answer fits in int.
        long[][] dp = new long[tLen + 1][sLen + 1];

        // Base Case: An empty target string can be formed in one way (by deleting all chars)
        // from any prefix of s.
        for (int j = 0; j <= sLen; j++) {
            dp[0][j] = 1;
        }

        // Fill the DP table
        for (int i = 1; i <= tLen; i++) {
            for (int j = 1; j <= sLen; j++) {
                // Get the characters for the current subproblems
                char tChar = t.charAt(i - 1);
                char sChar = s.charAt(j - 1);

                if (tChar == sChar) {
                    // If chars match, we have two choices:
                    // 1. Don't use sChar: The number of ways is the same as without it (dp[i][j-1]).
                    // 2. Use sChar: The number of ways is how many ways we could form the rest of t
                    //    from the rest of s (dp[i-1][j-1]).
                    dp[i][j] = dp[i][j - 1] + dp[i - 1][j - 1];
                } else {
                    // If chars don't match, sChar is useless for matching tChar.
                    // The number of ways is the same as if we didn't have sChar.
                    dp[i][j] = dp[i][j - 1];
                }
            }
        }

        return (int) dp[tLen][sLen];
    }

    private static int dp(String s, String t, int i, int j, Integer[][] memo) {
        if (j == t.length())
            return 1; // all characters are matched
        if (i == s.length())
            return 0; // no characters left to match
        if (memo[i][j] != null)
            return memo[i][j];
        int res = 0;
        if (s.charAt(i) == t.charAt(j)) {
            res = dp(s, t, i + 1, j, memo) + dp(s, t, i + 1, j + 1, memo);
        } else {
            res = dp(s, t, i + 1, j, memo);
        }
        memo[i][j] = res;
        return memo[i][j];
    }

    public static void main(String[] args) {
        System.out.println(numDistinct("rabbbit", "rabbit"));
        System.out.println(numDistinct("babgbag", "bag"));
    }
}