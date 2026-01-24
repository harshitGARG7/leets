import java.util.Arrays;

class Solution {
    public int minPairSum(int[] nums) {
        Arrays.sort(nums);
        int maxSum = 0;
        int l = 0, r = nums.length - 1;

        while (l < r) {
            maxSum = Math.max(maxSum, nums[l] + nums[r]);
            l++;
            r--;
        }

        return maxSum;
    }
}
