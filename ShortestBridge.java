import java.util.*;

public class ShortestBridge {

    private static int[][] directions = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
    private static Queue<int[]> queue;
    private static int n;

    public static int shortestBridge(int[][] grid) {
        n = grid.length;
        queue = new LinkedList<>();
        boolean foundFirstIsland = false;

        // --- Step 1: Find and collect the first island using DFS ---
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 1) {
                    dfs(grid, i, j);
                    foundFirstIsland = true;
                    break;
                }
            }
            if (foundFirstIsland) {
                break;
            }
        }

        // --- Step 2: Find the shortest path using a multi-source BFS ---
        int bridgeLength = 0;
        while (!queue.isEmpty()) {
            int levelSize = queue.size();
            for (int i = 0; i < levelSize; i++) {
                int[] cell = queue.poll();
                int r = cell[0];
                int c = cell[1];

                for (int[] dir : directions) {
                    int newR = r + dir[0];
                    int newC = c + dir[1];

                    if (newR >= 0 && newR < n && newC >= 0 && newC < n) {
                        if (grid[newR][newC] == 1) {
                            // We've reached the second island!
                            return bridgeLength;
                        }
                        if (grid[newR][newC] == 0) {
                            // Expand our search into the water
                            grid[newR][newC] = 2; // Mark as visited water
                            queue.offer(new int[]{newR, newC});
                        }
                    }
                }
            }
            bridgeLength++; // Increment the bridge length after exploring one full level
        }

        return -1; // Should not be reached given the problem constraints
    }

    /**
     * Helper DFS to find all cells of the first island.
     * It changes the island's '1's to '2's to mark them as visited
     * and adds them to the queue for the BFS step.
     */
    private static void dfs(int[][] grid, int r, int c) {
        if (r < 0 || r >= n || c < 0 || c >= n || grid[r][c] != 1) {
            return;
        }

        grid[r][c] = 2; // Mark as visited part of the first island
        queue.offer(new int[]{r, c});

        for (int[] dir : directions) {
            dfs(grid, r + dir[0], c + dir[1]);
        }
    }

    public static void main(String[] args) {
        int[][] grid =  {{1,1,1,1,1},{1,0,0,0,1},{1,0,1,0,1},{1,0,0,0,1},{1,1,1,1,1}};
        System.out.println(shortestBridge(grid));
    }
}