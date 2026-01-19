class Solution {
    public int maxSideLength(int[][] mat, int threshold) {
        int m = mat.length, n = mat[0].length;

        // Prefix sum array
        int[][] prefix = new int[m + 1][n + 1];

        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                prefix[i][j] = mat[i - 1][j - 1]
                        + prefix[i - 1][j]
                        + prefix[i][j - 1]
                        - prefix[i - 1][j - 1];
            }
        }

        int low = 0, high = Math.min(m, n);

        while (low < high) {
            int mid = low + (high - low + 1) / 2;

            if (existsSquare(prefix, m, n, mid, threshold)) {
                low = mid;
            } else {
                high = mid - 1;
            }
        }

        return low;
    }

    private boolean existsSquare(int[][] prefix, int m, int n, int k, int threshold) {
        for (int i = 0; i + k <= m; i++) {
            for (int j = 0; j + k <= n; j++) {
                int sum = prefix[i + k][j + k]
                        - prefix[i][j + k]
                        - prefix[i + k][j]
                        + prefix[i][j];

                if (sum <= threshold) {
                    return true;
                }
            }
        }
        return false;
    }
}
