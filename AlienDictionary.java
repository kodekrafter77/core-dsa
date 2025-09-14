import java.util.*;

public class AlienDictionary {

     public static String alienOrder(String[] words) {
         // --- Step 1: Build the Graph and In-Degree Map using Arrays for efficiency ---

        // Adjacency list using an array of lists.
        List<Character>[] adj = new List[26];
        // In-degree count using a simple int array.
        int[] inDegree = new int[26];
        // Use a boolean array to track which characters are part of the alphabet.
        boolean[] exists = new boolean[26];
        Arrays.fill(inDegree, -1); // Use -1 to indicate a char doesn't exist.

        // 1a: Initialize the data structures for all unique characters present.
        for (String word : words) {
            for (char c : word.toCharArray()) {
                int index = c - 'a';
                if (inDegree[index] == -1) { // First time seeing this char
                    inDegree[index] = 0;
                    adj[index] = new ArrayList<>();
                }
            }
        }

        // 1b: Find the dependency rules by comparing adjacent words.
        for (int i = 0; i < words.length - 1; i++) {
            String word1 = words[i];
            String word2 = words[i + 1];

            if (word1.length() > word2.length() && word1.startsWith(word2)) {
                return ""; // Invalid order
            }

            for (int j = 0; j < Math.min(word1.length(), word2.length()); j++) {
                char char1 = word1.charAt(j);
                char char2 = word2.charAt(j);

                if (char1 != char2) {
                    // We found a rule: char1 -> char2
                    adj[char1 - 'a'].add(char2);
                    inDegree[char2 - 'a']++;
                    break;
                }
            }
        }

        // --- Step 2: Perform Topological Sort (Kahn's Algorithm using BFS) ---

        Queue<Character> queue = new ArrayDeque<>();
        int charCount = 0;
        // 2a: Seed the queue with all existing characters that have an in-degree of 0.
        for (int i = 0; i < 26; i++) {
            if (inDegree[i] != -1) {
                charCount++;
                if (inDegree[i] == 0) {
                    queue.offer((char)('a' + i));
                }
            }
        }

        StringBuilder result = new StringBuilder();
        while (!queue.isEmpty()) {
            char currentChar = queue.poll();
            result.append(currentChar);

            int currentIndex = currentChar - 'a';
            if (adj[currentIndex] != null) {
                for (char neighbor : adj[currentIndex]) {
                    int neighborIndex = neighbor - 'a';
                    inDegree[neighborIndex]--;
                    if (inDegree[neighborIndex] == 0) {
                        queue.offer(neighbor);
                    }
                }
            }
        }

        // --- Step 3: Final Validation (Check for Cycles) ---
        if (result.length() < charCount) {
            return "";
        }

        return result.toString();
    }

    public static void main(String[] args) {
        System.out.println(alienOrder(new String[]{"wrt","wrf","er","ett","rftt"}));

        System.out.println(alienOrder(new String[]{"z", "x"}));

        System.out.println(alienOrder(new String[]{"z", "x", "z"}));
    }
}