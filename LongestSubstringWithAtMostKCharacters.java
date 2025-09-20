import java.util.*;

public class LongestSubstringWithAtMostKCharacters {

    public static int lengthOfLongestSubstringKDistinct(String s, int k) {
    if (s == null || s.length() == 0 || k == 0) {
        return 0;
    }
    
    int maxLength = 0;
    Map<Character, Integer> window = new HashMap<>();
    int left = 0;

    for (int right = 0; right < s.length(); right++) {
        // 1. EXPAND the window by adding the rightmost character.
        char rightChar = s.charAt(right);
        window.put(rightChar, window.getOrDefault(rightChar, 0) + 1);

        // 2. SHRINK the window from the left if it's now invalid.
        // The window is invalid if it has more than k distinct characters.
        while (window.size() > k) {
            char leftChar = s.charAt(left);
            // Decrement the count of the character leaving the window.
            window.put(leftChar, window.get(leftChar) - 1);
            // If the count is zero, remove it from the map completely.
            if (window.get(leftChar) == 0) {
                window.remove(leftChar);
            }
            // Move the left pointer to shrink the window.
            left++;
        }

        // 3. UPDATE the result. The window is guaranteed to be valid here.
        maxLength = Math.max(maxLength, right - left + 1);
    }

    return maxLength;
}

    public static void main(String[] args) {
        System.out.println(lengthOfLongestSubstringKDistinct("eceba", 2)); // 3
        System.out.println(lengthOfLongestSubstringKDistinct("aa", 1)); // 2
    }
}