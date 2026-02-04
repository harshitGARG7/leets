import java.util.*;

class Solution {

    static class Triple {
        int p, q;
        long sum;

        Triple(int p, int q, long sum) {
            this.p = p;
            this.q = q;
            this.sum = sum;
        }
    }

    // strictly decreasing segments
    public List<Triple> decompose(int[] nums) {
        int n = nums.length;
        List<Triple> res = new ArrayList<>();

        int l = 0;
        long sum = nums[0];

        for (int i = 1; i < n; i++) {
            if (nums[i - 1] <= nums[i]) { // break decreasing
                if (l < i - 1) {
                    res.add(new Triple(l, i - 1, sum));
                }
                l = i;
                sum = nums[i];
            } else {
                sum += nums[i];
            }
        }

        if (l < n - 1) {
            res.add(new Triple(l, n - 1, sum));
        }

        return res;
    }

    public long maxSumTrionic(int[] nums) {
        int n = nums.length;
        if (n < 4)
            return 0;

        // max increasing ending at i
        long[] maxEndingAt = new long[n];
        for (int i = 0; i < n; i++) {
            maxEndingAt[i] = nums[i];
            if (i > 0 && nums[i - 1] < nums[i] && maxEndingAt[i - 1] > 0) {
                maxEndingAt[i] += maxEndingAt[i - 1];
            }
        }

        // max increasing starting at i
        long[] maxStartingAt = new long[n];
        for (int i = n - 1; i >= 0; i--) {
            maxStartingAt[i] = nums[i];
            if (i < n - 1 && nums[i] < nums[i + 1] && maxStartingAt[i + 1] > 0) {
                maxStartingAt[i] += maxStartingAt[i + 1];
            }
        }

        List<Triple> decSegments = decompose(nums);
        long ans = Long.MIN_VALUE;

        for (Triple t : decSegments) {
            int p = t.p;
            int q = t.q;

            // need INC before and INC after
            if (p > 0 && q < n - 1 &&
                    nums[p - 1] < nums[p] &&
                    nums[q] < nums[q + 1]) {

                long cand = maxEndingAt[p - 1]
                        + t.sum
                        + maxStartingAt[q + 1];

                ans = Math.max(ans, cand);
            }
        }

        return ans == Long.MIN_VALUE ? 0 : ans;
    }
}
