import java.util.*;

public class RegularExpressionMatching {

    public static boolean isMatch(String s, String p) {
        int sLen = s.length();
        int pLen = p.length();
        boolean[][] dp = new boolean[sLen + 1][pLen + 1];

        // Step 2: Initialize the base case
        dp[sLen][pLen] = true;

        // Step 3: Iterate backwards
        for (int i = sLen; i >= 0; i--) {
            for (int j = pLen - 1; j >= 0; j--) {
                // Step 4: Calculate firstMatch
                boolean firstMatch = (i < sLen && (p.charAt(j) == s.charAt(i) || p.charAt(j) == '.'));

                if (j + 1 < pLen && p.charAt(j + 1) == '*') {
                    // Step 5: The '*' Case
                    dp[i][j] = dp[i][j + 2] || (firstMatch && dp[i + 1][j]);
                } else {
                    // Step 6: The "Not a *" Case
                    dp[i][j] = firstMatch && dp[i + 1][j + 1];
                }
            }
        }
        
        // Step 7: The final answer
        return dp[0][0];
    }

    public static boolean isMatchMemo(String s, String p) {
        int n = s.length();
        int m = p.length();
        Boolean[][] memo = new Boolean[n + 1][m + 1];
        return isMatch(s, p, memo, 0, 0);
    }

    private static boolean isMatch(String s, String p, 
        Boolean[][] memo, int i, int j) {

        if (memo[i][j] != null) return memo[i][j];

        // Base case: Exhausted pattern string
        if (j == p.length()) return i == s.length(); // text length should also have been exhausted

        boolean res = false;

        boolean firstMatch = (i < s.length() && (p.charAt(j) == s.charAt(i) || p.charAt(j) == '.'));

        if ((j + 1) < p.length() && p.charAt(j + 1) == '*') {
            // Case 1: Solve for zero occurences for the character before *
            boolean zeroOccurence = isMatch(s, p, memo, i, j + 2);
            // Case 2: Solve for one or more occurences for the character before *
            boolean moreOccurences = firstMatch && isMatch(s, p, memo, i + 1, j);
            res = zeroOccurence || moreOccurences;
        } else {
            // the next pattern char is not a *
            res = firstMatch && isMatch(s, p, memo, i + 1, j + 1);
        }
        memo[i][j] = res;
        return res; 
    }

    public static void main(String[] args) {
        System.out.println(isMatch("aa", "a"));  // false
        System.out.println(isMatch("aa", "a*")); // true
        System.out.println(isMatch("ab", ".*")); // true
        System.out.println(isMatch("", ".*"));   // true
    }
}