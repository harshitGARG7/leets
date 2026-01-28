import java.util.*;

class Solution {

    static class State {
        int r, c, t;
        long cost;

        State(int r, int c, int t, long cost) {
            this.r = r;
            this.c = c;
            this.t = t;
            this.cost = cost;
        }
    }

    public int minCostTele(int[][] grid, int k) {
        int m = grid.length, n = grid[0].length;
        long INF = Long.MAX_VALUE / 4;

        long[][][] dist = new long[k + 1][m][n];
        for (int t = 0; t <= k; t++)
            for (int i = 0; i < m; i++)
                Arrays.fill(dist[t][i], INF);

        // All cells sorted by value ASC
        List<int[]> cells = new ArrayList<>();
        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++)
                cells.add(new int[] { grid[i][j], i, j });
        cells.sort(Comparator.comparingInt(a -> a[0]));

        int[] ptr = new int[k + 1]; // teleport pointers per layer

        PriorityQueue<State> pq = new PriorityQueue<>(Comparator.comparingLong(a -> a.cost));

        dist[0][0][0] = 0;
        pq.add(new State(0, 0, 0, 0));

        while (!pq.isEmpty()) {
            State cur = pq.poll();
            int r = cur.r, c = cur.c, t = cur.t;
            long cost = cur.cost;

            if (cost != dist[t][r][c])
                continue;

            // Normal moves
            if (r + 1 < m) {
                long nc = cost + grid[r + 1][c];
                if (nc < dist[t][r + 1][c]) {
                    dist[t][r + 1][c] = nc;
                    pq.add(new State(r + 1, c, t, nc));
                }
            }
            if (c + 1 < n) {
                long nc = cost + grid[r][c + 1];
                if (nc < dist[t][r][c + 1]) {
                    dist[t][r][c + 1] = nc;
                    pq.add(new State(r, c + 1, t, nc));
                }
            }

            // Teleport (each cell once per t)
            if (t < k) {
                while (ptr[t] < cells.size() && cells.get(ptr[t])[0] <= grid[r][c]) {
                    int x = cells.get(ptr[t])[1];
                    int y = cells.get(ptr[t])[2];
                    if (cost < dist[t + 1][x][y]) {
                        dist[t + 1][x][y] = cost;
                        pq.add(new State(x, y, t + 1, cost));
                    }
                    ptr[t]++;
                }
            }
        }

        long ans = INF;
        for (int t = 0; t <= k; t++)
            ans = Math.min(ans, dist[t][m - 1][n - 1]);

        return (int) ans;
    }
}
