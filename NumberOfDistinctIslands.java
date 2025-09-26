import java.util.*;

public class NumberOfDistinctIslands {
    
    public static int numDistinctIslands(int[][] grid) {
         if (grid == null || grid.length == 0) {
            return 0;
        }

        Set<String> distinctShapes = new HashSet<>();
        int numRows = grid.length;
        int numCols = grid[0].length;

        for (int r = 0; r < numRows; r++) {
            for (int c = 0; c < numCols; c++) {
                if (grid[r][c] == 1) {
                    StringBuilder path = new StringBuilder();
                    // We start the path with an arbitrary character, e.g., 'S' for start.
                    dfs(grid, r, c, path, "S");
                    distinctShapes.add(path.toString());
                }
            }
        }
       // System.out.println(distinctShapes);
        return distinctShapes.size();
    }

    private static void dfs(int[][] grid, int r, int c, StringBuilder path, String direction) {
        // Your DFS implementation here.
        // 1. Base Case: Check if the current cell (r, c) is out of bounds or is water.
        //    If so, just return.
        if (r < 0 || r >= grid.length || c < 0 || c >= grid[0].length || grid[r][c] == 0) return;

        // 2. Mark the current cell as visited (grid[r][c] = 0).
        grid[r][c] = 0; // visited

        // 3. Append the direction of the move that brought you here to the path.
        path.append(direction);

        // 4. Make recursive calls for all 4 neighbors (up, down, left, right),
        //    passing the appropriate direction character for each.
        dfs(grid, r + 1, c, path, "D");
        dfs(grid, r, c + 1, path, "R");
        dfs(grid, r - 1, c, path, "U");
        dfs(grid, r, c - 1, path, "L");

        // 5. Append a "backtrack" character to the path to mark the end of this branch.
        path.append("B");
    }

    public static void main(String[] args) {
        int[][] grid = {{1,1,0,0,0},{1,1,0,0,0},{0,0,0,1,1},{0,0,0,1,1}};
        System.out.println(numDistinctIslands(grid)); // 1
        int[][] grid1 = {{1,1,0,1,1},{1,0,0,0,0},{0,0,0,0,1},{1,1,0,1,1}};
        System.out.println(numDistinctIslands(grid1)); // 3
    }
}