import java.util.*;

class Solution {
    public int longestBalanced(int[] nums) {
        int n = nums.length;
        if (n == 0) return 0;

        // Tree stores the min and max balance (EvenCount - OddCount) for each range
        int[] minTree = new int[4 * n];
        int[] maxTree = new int[4 * n];
        int[] lazy = new int[4 * n];
        
        // prevMap stores the last seen index of each number
        Map<Integer, Integer> prevMap = new HashMap<>();
        int maxLen = 0;

        for (int i = 0; i < n; i++) {
            int val = nums[i];
            int lastPos = prevMap.getOrDefault(val, -1);
            
            // Range update: [lastPos + 1, i]
            // Even numbers add 1 to balance, Odd numbers subtract 1
            int delta = (val % 2 == 0) ? 1 : -1;
            update(1, 0, n - 1, lastPos + 1, i, delta, minTree, maxTree, lazy);
            
            prevMap.put(val, i);

            // Find the leftmost starting index j where balance is 0
            int leftmostJ = findLeftmostZero(1, 0, n - 1, 0, i, minTree, maxTree, lazy);
            if (leftmostJ != -1) {
                maxLen = Math.max(maxLen, i - leftmostJ + 1);
            }
        }
        return maxLen;
    }

    private void push(int v, int[] minTree, int[] maxTree, int[] lazy) {
        if (lazy[v] != 0) {
            lazy[2 * v] += lazy[v];
            minTree[2 * v] += lazy[v];
            maxTree[2 * v] += lazy[v];
            
            lazy[2 * v + 1] += lazy[v];
            minTree[2 * v + 1] += lazy[v];
            maxTree[2 * v + 1] += lazy[v];
            
            lazy[v] = 0;
        }
    }

    private void update(int v, int s, int e, int l, int r, int delta, int[] minTree, int[] maxTree, int[] lazy) {
        if (l > e || r < s) return;
        if (l <= s && e <= r) {
            lazy[v] += delta;
            minTree[v] += delta;
            maxTree[v] += delta;
            return;
        }
        push(v, minTree, maxTree, lazy);
        int mid = s + (e - s) / 2;
        update(2 * v, s, mid, l, r, delta, minTree, maxTree, lazy);
        update(2 * v + 1, mid + 1, e, l, r, delta, minTree, maxTree, lazy);
        minTree[v] = Math.min(minTree[2 * v], minTree[2 * v + 1]);
        maxTree[v] = Math.max(maxTree[2 * v], maxTree[2 * v + 1]);
    }

    private int findLeftmostZero(int v, int s, int e, int l, int r, int[] minTree, int[] maxTree, int[] lazy) {
        // Pruning: if 0 is not in [min, max], it's impossible for this range
        if (l > e || r < s || minTree[v] > 0 || maxTree[v] < 0) {
            return -1;
        }
        if (s == e) {
            return s;
        }
        push(v, minTree, maxTree, lazy);
        int mid = s + (e - s) / 2;
        
        // Search left child first to get the smallest index (max length)
        int res = findLeftmostZero(2 * v, s, mid, l, r, minTree, maxTree, lazy);
        if (res == -1) {
            res = findLeftmostZero(2 * v + 1, mid + 1, e, l, r, minTree, maxTree, lazy);
        }
        return res;
    }
}