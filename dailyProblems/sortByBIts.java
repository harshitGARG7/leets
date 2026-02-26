import java.util.*;

class Solution {
    public int[] sortByBits(int[] arr) {
        Integer[] nums = Arrays.stream(arr).boxed().toArray(Integer[]::new);
        
        Arrays.sort(nums, (a, b) -> {
            int bitCompare = Integer.bitCount(a) - Integer.bitCount(b);
            if (bitCompare != 0) return bitCompare;
            return a - b;
        });
        
        for (int i = 0; i < arr.length; i++) {
            arr[i] = nums[i];
        }
        
        return arr;
    }
}