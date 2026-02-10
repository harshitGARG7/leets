import java.util.Set;
import java.util.HashSet;

class Solution {
    public int longestBalancedSubarray(int[] nums) {
        int n = nums.length;
        int ans = 0;

        for (int l = 0; l < n; l++) {
            Set<Integer> seen = new HashSet<>();
            int balance = 0;

            for (int r = l; r < n; r++) {
                if (!seen.contains(nums[r])) {
                    seen.add(nums[r]);
                    if (nums[r] % 2 == 0) balance++;
                    else balance--;
                }

                if (balance == 0) {
                    ans = Math.max(ans, r - l + 1);
                }
            }
        }
        return ans;
    }
}
