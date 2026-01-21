import java.util.*;

class Solution {
    public int[] minBitwiseArray(List<Integer> nums) {
        int n = nums.size();
        int[] ans = new int[n];
        
        for (int i = 0; i < n; i++) {
            int target = nums.get(i);
            
            // Since nums[i] are primes, 2 is the only even number.
            // x OR (x+1) is always odd for x >= 0, except for x=-1.
            if (target == 2) {
                ans[i] = -1;
            } else {
                // Find the first 0 bit starting from position 1.
                // We want to flip the 1-bit that is just to the right of 
                // the rightmost sequence of 1s.
                for (int j = 1; j < 31; j++) {
                    if (((target >> j) & 1) == 0) {
                        // Found the first 0 bit at position j.
                        // The bit at (j-1) is the one to flip.
                        ans[i] = target ^ (1 << (j - 1));
                        break;
                    }
                }
            }
        }
        return ans;
    }
}