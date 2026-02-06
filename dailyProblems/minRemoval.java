import java.util.*;

class Solution {
    public int minRemoval(int[] nums, int k) {
        int n = nums.length;
        Arrays.sort(nums);

        int maxLen = 1; // at least 1 element is always balanced
        int l = 0;

        for (int r = 0; r < n; r++) {
            // shrink window until condition is satisfied
            while ((long) nums[r] > (long) k * nums[l]) {
                l++;
            }
            maxLen = Math.max(maxLen, r - l + 1);
        }

        return n - maxLen;
    }
}
