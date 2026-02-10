import java.util.*;

class Solution {

    static class TrieNode {
        Map<Character, TrieNode> children = new HashMap<>();
        List<Integer> wordIds = new ArrayList<>();
    }

    TrieNode root = new TrieNode();

    void insert(String s, int id) {
        TrieNode node = root;
        for (char c : s.toCharArray()) {
            node = node.children.computeIfAbsent(c, k -> new TrieNode());
        }
        node.wordIds.add(id);
    }

    public long minimumCost(
            String source,
            String target,
            String[] original,
            String[] changed,
            int[] cost) {
        int n = source.length();
        final long INF = (long) 1e18;

        // 1️⃣ Map each unique string to an id
        Map<String, Integer> idMap = new HashMap<>();
        int id = 0;

        for (int i = 0; i < original.length; i++) {
            if (!idMap.containsKey(original[i]))
                idMap.put(original[i], id++);
            if (!idMap.containsKey(changed[i]))
                idMap.put(changed[i], id++);
        }

        int m = idMap.size();
        long[][] dist = new long[m][m];

        for (int i = 0; i < m; i++) {
            Arrays.fill(dist[i], INF);
            dist[i][i] = 0;
        }

        // 2️⃣ Fill direct conversion costs
        for (int i = 0; i < original.length; i++) {
            int u = idMap.get(original[i]);
            int v = idMap.get(changed[i]);
            dist[u][v] = Math.min(dist[u][v], cost[i]);
        }

        // 3️⃣ Floyd–Warshall
        for (int k = 0; k < m; k++) {
            for (int i = 0; i < m; i++) {
                if (dist[i][k] == INF)
                    continue;
                for (int j = 0; j < m; j++) {
                    if (dist[k][j] == INF)
                        continue;
                    dist[i][j] = Math.min(dist[i][j], dist[i][k] + dist[k][j]);
                }
            }
        }

        // 4️⃣ Build Trie on original strings
        for (String s : idMap.keySet()) {
            insert(s, idMap.get(s));
        }

        // 5️⃣ DP
        long[] dp = new long[n + 1];
        Arrays.fill(dp, INF);
        dp[n] = 0;

        for (int i = n - 1; i >= 0; i--) {

            // Case 1: characters already match
            if (source.charAt(i) == target.charAt(i)) {
                dp[i] = dp[i + 1];
            }

            // Case 2: try substring conversions
            TrieNode node = root;
            for (int j = i; j < n; j++) {
                char c = source.charAt(j);
                node = node.children.get(c);
                if (node == null)
                    break;

                for (int srcId : node.wordIds) {
                    // String srcStr = source.substring(i, j + 1);
                    String tgtStr = target.substring(i, j + 1);

                    Integer tgtId = idMap.get(tgtStr);
                    if (tgtId == null)
                        continue;

                    long convCost = dist[srcId][tgtId];
                    if (convCost == INF)
                        continue;

                    dp[i] = Math.min(dp[i], convCost + dp[j + 1]);
                }
            }
        }

        return dp[0] == INF ? -1 : dp[0];
    }
}
