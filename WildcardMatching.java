import java.util.*;

public class WildcardMatching {

    public static boolean isMatch(String s, String p) {
        int sLen = s.length();
        int pLen = p.length();
        boolean[][] dp = new boolean[sLen + 1][pLen + 1];

        // Step 2: Initialize the base case
        dp[sLen][pLen] = true;

        // Step 3: Iterate backwards
        for (int i = sLen; i >= 0; i--) {
            for (int j = pLen - 1; j >= 0; j--) {
                char pChar = p.charAt(j);
                boolean firstMatch = (i < sLen && (pChar == '?' || pChar == s.charAt(i)));

                if (pChar == '*') {
                    // Step 4: The '*' Case
                    // Choice 1: '*' matches empty (dp[i][j+1])
                    // Choice 2: '*' matches char (dp[i+1][j])
                    dp[i][j] = dp[i][j + 1] || (i < sLen && dp[i + 1][j]);
                } else {
                    // Step 5: The '?' / Letter Case
                    dp[i][j] = firstMatch && dp[i + 1][j + 1];
                }
            }
        }
        
        // Step 6: The final answer
        return dp[0][0];    
    }

    public static boolean isMatchMemo(String s, String p) {
        int n = s.length();
        int m = p.length();
        Boolean[][] memo = new Boolean[n + 1][m + 1];
        return matches(s, p, memo, 0, 0);
    }

    private static boolean matches(String s, String p, Boolean[][] memo, int i, int j) {

        if (memo[i][j] != null) return memo[i][j];

        if (j == p.length()) return i == s.length();

        boolean res = false;

        char pChar = p.charAt(j);
        boolean firstMatch = (i < s.length() && (pChar == '?' || pChar == s.charAt(i)));

        if (pChar == '*') {
            boolean emptyMatch = matches(s, p, memo, i, j + 1);
            
            boolean matchChars = false;

            if (i < s.length()) matchChars = matches(s, p, memo, i + 1, j);
            res = emptyMatch || matchChars;
        } else {
            res = firstMatch && matches(s, p, memo, i + 1, j + 1);
        }

        memo[i][j] = res;
        return res;
    }

    public static void main(String[] args) {
        System.out.println(isMatch("aa","a")); // false
        System.out.println(isMatch("aa","*")); // true
        System.out.println(isMatch("cb","?a"));// false
    }
}