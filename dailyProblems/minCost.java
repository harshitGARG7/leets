import java.util.*;

class Solution {
    public int minCost(int n, int[][] edges) {
        List<List<int[]>> graph = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
        }

        // Build graph
        for (int[] e : edges) {
            int u = e[0], v = e[1], w = e[2];
            graph.get(u).add(new int[]{v, w});        // normal edge
            graph.get(v).add(new int[]{u, 2 * w});    // reversed edge
        }

        // Dijkstra
        long[] dist = new long[n];
        Arrays.fill(dist, Long.MAX_VALUE);
        dist[0] = 0;

        PriorityQueue<long[]> pq = new PriorityQueue<>(
            (a, b) -> Long.compare(a[0], b[0])
        );
        pq.offer(new long[]{0, 0}); // {cost, node}

        while (!pq.isEmpty()) {
            long[] cur = pq.poll();
            long cost = cur[0];
            int node = (int) cur[1];

            if (cost > dist[node]) continue;
            if (node == n - 1) return (int) cost;

            for (int[] nxt : graph.get(node)) {
                int nei = nxt[0];
                long w = nxt[1];
                long newCost = cost + w;

                if (newCost < dist[nei]) {
                    dist[nei] = newCost;
                    pq.offer(new long[]{newCost, nei});
                }
            }
        }

        return -1;
    }
}
