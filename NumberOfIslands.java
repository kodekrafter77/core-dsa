import java.util.*;

public class NumberOfIslands {
  public static int numIslands(char[][] grid) {
        if (grid == null || grid.length == 0) {
            return 0;
        }

        int numRows = grid.length;
        int numCols = grid[0].length;
        int islandCount = 0;

        for (int r = 0; r < numRows; r++) {
            for (int c = 0; c < numCols; c++) {
                if (grid[r][c] == '1') {
                    islandCount++;
                    bfs(grid, r, c);
                }
            }
        }
        return islandCount;
    }

    private static void bfs(char[][] grid, int r, int c) {
        int numRows = grid.length;
        int numCols = grid[0].length;
        
        Queue<int[]> queue = new LinkedList<>();
        
        queue.offer(new int[]{r, c});
        grid[r][c] = '0'; // Mark as visited IMMEDIATELY

        int[][] directions = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};

        while (!queue.isEmpty()) {
            int[] currentCell = queue.poll();
            int row = currentCell[0];
            int col = currentCell[1];

            for (int[] dir : directions) {
                int newRow = row + dir[0];
                int newCol = col + dir[1];

                if (newRow >= 0 && newRow < numRows && 
                    newCol >= 0 && newCol < numCols && 
                    grid[newRow][newCol] == '1') { // Explicit check for '1'
                    
                    queue.offer(new int[]{newRow, newCol});
                    grid[newRow][newCol] = '0'; // Mark as visited BEFORE enqueuing
                }
            }
        }
    }

    public static void main(String[] args) {
        char[][] grid = {
          {'1','1','1','1','0'},
          {'1','1','0','1','0'},
          {'1','1','0','0','0'},
          {'0','0','0','0','0'}
        };

        System.out.println(numIslands(grid));

        char[][] grid1 =  {
          {'1','1','0','0','0'},
          {'1','1','0','0','0'},
          {'0','0','1','0','0'},
          {'0','0','0','1','1'}
        };

        System.out.println(numIslands(grid1));
    }
}