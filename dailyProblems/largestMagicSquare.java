class Solution {
    public int largestMagicSquare(int[][] grid) {
        int m = grid.length, n = grid[0].length;

        // Prefix sums
        int[][] row = new int[m][n + 1];
        int[][] col = new int[m + 1][n];

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                row[i][j + 1] = row[i][j] + grid[i][j];
                col[i + 1][j] = col[i][j] + grid[i][j];
            }
        }

        // Try largest size first
        for (int k = Math.min(m, n); k > 1; k--) {
            for (int i = 0; i + k <= m; i++) {
                for (int j = 0; j + k <= n; j++) {
                    if (isMagic(grid, row, col, i, j, k)) {
                        return k;
                    }
                }
            }
        }

        return 1; // every 1x1 is magic
    }

    private boolean isMagic(int[][] grid, int[][] row, int[][] col,
                            int x, int y, int k) {

        int target = row[x][y + k] - row[x][y];

        // Check rows
        for (int i = x; i < x + k; i++) {
            if (row[i][y + k] - row[i][y] != target)
                return false;
        }

        // Check columns
        for (int j = y; j < y + k; j++) {
            if (col[x + k][j] - col[x][j] != target)
                return false;
        }

        // Check diagonals
        int d1 = 0, d2 = 0;
        for (int i = 0; i < k; i++) {
            d1 += grid[x + i][y + i];
            d2 += grid[x + i][y + k - 1 - i];
        }

        return d1 == target && d2 == target;
    }
}
