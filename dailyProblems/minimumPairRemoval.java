public import java.util.*;

class Solution {
    class Node {
        long val;
        int id; 
        int version; // Incremented every time the node is modified
        Node prev, next;

        Node(long val, int id) {
            this.val = val;
            this.id = id;
            this.version = 0;
        }
    }

    class Pair implements Comparable<Pair> {
        long sum;
        Node left, right;
        int leftVer, rightVer;

        Pair(Node left, Node right) {
            this.sum = left.val + right.val;
            this.left = left;
            this.right = right;
            this.leftVer = left.version;
            this.rightVer = right.version;
        }

        @Override
        public int compareTo(Pair other) {
            if (this.sum != other.sum) return Long.compare(this.sum, other.sum);
            // Tie-breaker: leftmost pair based on current structure
            return Integer.compare(this.left.id, other.left.id);
        }
    }

    public int minimumPairRemoval(int[] nums) {
        int n = nums.length;
        if (n < 2) return 0;

        Node[] nodeArray = new Node[n];
        int inversions = 0;

        for (int i = 0; i < n; i++) {
            nodeArray[i] = new Node(nums[i], i);
        }

        PriorityQueue<Pair> pq = new PriorityQueue<>();
        for (int i = 0; i < n; i++) {
            if (i > 0) {
                nodeArray[i].prev = nodeArray[i - 1];
                if (nodeArray[i - 1].val > nodeArray[i].val) inversions++;
            }
            if (i < n - 1) {
                nodeArray[i].next = nodeArray[i + 1];
                pq.add(new Pair(nodeArray[i], nodeArray[i + 1]));
            }
        }

        if (inversions == 0) return 0;

        int ops = 0;
        while (!pq.isEmpty()) {
            Pair best = pq.poll();
            Node L = best.left;
            Node R = best.right;

            // STRICT VALIDATION: Are these nodes still adjacent and unchanged?
            if (L.next != R || R.prev != L || L.version != best.leftVer || R.version != best.rightVer) {
                continue;
            }

            // Update inversion count before the merge
            if (L.prev != null && L.prev.val > L.val) inversions--;
            if (L.val > R.val) inversions--;
            if (R.next != null && R.val > R.next.val) inversions--;

            // Merge logic
            L.val = L.val + R.val;
            L.version++; // Mark L as updated
            L.next = R.next;
            if (R.next != null) {
                R.next.prev = L;
            }

            // Re-check inversions after merge
            if (L.prev != null && L.prev.val > L.val) inversions++;
            if (L.next != null && L.val > L.next.val) inversions++;

            ops++;
            if (inversions == 0) return ops;

            // Add new neighbors to PQ with current versions
            if (L.prev != null) pq.add(new Pair(L.prev, L));
            if (L.next != null) pq.add(new Pair(L, L.next));
        }

        return ops;
    }
} {
    
}
