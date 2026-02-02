import java.util.*;

class Solution {

    long sumSmall = 0;
    int smallSize = 0, largeSize = 0;

    public long minimumCost(int[] nums, int k, int dist) {
        int n = nums.length;
        int need = k - 1;

        PriorityQueue<Integer> small = new PriorityQueue<>(Collections.reverseOrder());
        PriorityQueue<Integer> large = new PriorityQueue<>();
        Map<Integer, Integer> delayed = new HashMap<>();

        long ans = Long.MAX_VALUE;

        for (int i = 1; i < n; i++) {
            add(nums[i], small, large);

            if (i > dist + 1) {
                remove(nums[i - (dist + 1)], small, large, delayed);
            }

            balance(small, large, delayed, need);

            if (i >= dist && smallSize == need) {
                ans = Math.min(ans, nums[0] + sumSmall);
            }
        }
        return ans;
    }

    private void add(int x,
                     PriorityQueue<Integer> small,
                     PriorityQueue<Integer> large) {
        if (small.isEmpty() || x <= small.peek()) {
            small.offer(x);
            sumSmall += x;
            smallSize++;
        } else {
            large.offer(x);
            largeSize++;
        }
    }

    private void remove(int x,
                        PriorityQueue<Integer> small,
                        PriorityQueue<Integer> large,
                        Map<Integer, Integer> delayed) {
        delayed.put(x, delayed.getOrDefault(x, 0) + 1);

        if (!small.isEmpty() && x <= small.peek()) {
            sumSmall -= x;
            smallSize--;
        } else {
            largeSize--;
        }

        prune(small, delayed);
        prune(large, delayed);
    }

    private void balance(PriorityQueue<Integer> small,
                         PriorityQueue<Integer> large,
                         Map<Integer, Integer> delayed,
                         int need) {

        // shrink small if too big
        while (smallSize > need) {
            prune(small, delayed);
            if (small.isEmpty()) break;

            int x = small.poll();
            sumSmall -= x;
            smallSize--;
            large.offer(x);
            largeSize++;
        }

        // grow small if too small
        while (smallSize < need) {
            prune(large, delayed);
            if (large.isEmpty()) break;

            int x = large.poll();
            sumSmall += x;
            largeSize--;
            small.offer(x);
            smallSize++;
        }
    }

    private void prune(PriorityQueue<Integer> pq,
                       Map<Integer, Integer> delayed) {
        while (!pq.isEmpty()) {
            int x = pq.peek();
            Integer cnt = delayed.get(x);
            if (cnt == null) break;

            if (cnt == 1) delayed.remove(x);
            else delayed.put(x, cnt - 1);

            pq.poll();
        }
    }
}
