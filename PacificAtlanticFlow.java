import java.util.*;

public class PacificAtlanticFlow {
    
    public static List<List<Integer>> pacificAtlantic(int[][] heights) {
        // Handle edge case of an empty grid
        if (heights == null || heights.length == 0 || heights[0].length == 0) {
            return new ArrayList<>();
        }

        int numRows = heights.length;
        int numCols = heights[0].length;

        // Two boolean matrices to store which cells can reach each ocean
        boolean[][] pacificReachable = new boolean[numRows][numCols];
        boolean[][] atlanticReachable = new boolean[numRows][numCols];

        // Two queues for our multi-source BFS traversals
        Queue<int[]> pacificQueue = new LinkedList<>();
        Queue<int[]> atlanticQueue = new LinkedList<>();

        // --- Step 1: Seed the queues with all border cells ---
        for (int r = 0; r < numRows; r++) {
            // Left border (Pacific)
            pacificQueue.offer(new int[]{r, 0});
            pacificReachable[r][0] = true;

            // Right border (Atlantic)
            atlanticQueue.offer(new int[]{r, numCols - 1});
            atlanticReachable[r][numCols - 1] = true;
        }
        for (int c = 0; c < numCols; c++) {
            // Top border (Pacific)
            pacificQueue.offer(new int[]{0, c});
            pacificReachable[0][c] = true;

            // Bottom border (Atlantic)
            atlanticQueue.offer(new int[]{numRows - 1, c});
            atlanticReachable[numRows - 1][c] = true;
        }

        // --- Step 2: Run BFS from each ocean's border ---
        // This will find all cells that can be reached by flowing "uphill" from the oceans.
        bfs(heights, pacificQueue, pacificReachable);
        bfs(heights, atlanticQueue, atlanticReachable);

        // --- Step 3: Find the intersection of the two reachable sets ---
        List<List<Integer>> result = new ArrayList<>();
        for (int r = 0; r < numRows; r++) {
            for (int c = 0; c < numCols; c++) {
                // If a cell can reach both oceans, add it to the result.
                if (pacificReachable[r][c] && atlanticReachable[r][c]) {
                    result.add(Arrays.asList(r, c));
                }
            }
        }

        return result;
    }

    /**
     * Helper method to perform a multi-source BFS traversal.
     */
    private static void bfs(int[][] heights, Queue<int[]> queue, boolean[][] reachable) {
        int numRows = heights.length;
        int numCols = heights[0].length;
        int[][] directions = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};

        while (!queue.isEmpty()) {
            int[] cell = queue.poll();
            int r = cell[0];
            int c = cell[1];

            for (int[] dir : directions) {
                int newR = r + dir[0];
                int newC = c + dir[1];

                // --- Validation for neighbors ---
                // 1. Check if the neighbor is in bounds.
                // 2. Check if we have already visited this neighbor.
                // 3. Check the "uphill" flow condition: neighbor's height must be >= current cell's height.
                if (newR >= 0 && newR < numRows && newC >= 0 && newC < numCols &&
                    !reachable[newR][newC] &&
                    heights[newR][newC] >= heights[r][c]) {
                    
                    reachable[newR][newC] = true;
                    queue.offer(new int[]{newR, newC});
                }
            }
        }
    }

    public static void main(String[] args) {
        int[][] heights = {{1,2,2,3,5},{3,2,3,4,4},{2,4,5,3,1},{6,7,1,4,5},{5,1,1,2,4}};
        System.out.println(pacificAtlantic(heights));
    }
}
