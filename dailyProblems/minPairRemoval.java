import java.util.List;
import java.util.ArrayList;

class Solution {
    public int minimumPairRemoval(int[] nums) {
        List<Integer> arr = new ArrayList<>();
        for (int x : nums)
            arr.add(x);

        int operations = 0;

        while (!isNonDecreasing(arr)) {
            int minSum = Integer.MAX_VALUE;
            int idx = 0;

            // Find leftmost adjacent pair with minimum sum
            for (int i = 0; i < arr.size() - 1; i++) {
                int sum = arr.get(i) + arr.get(i + 1);
                if (sum < minSum) {
                    minSum = sum;
                    idx = i;
                }
            }

            // Merge the pair
            arr.set(idx, minSum);
            arr.remove(idx + 1);

            operations++;
        }

        return operations;
    }

    private boolean isNonDecreasing(List<Integer> arr) {
        for (int i = 1; i < arr.size(); i++) {
            if (arr.get(i) < arr.get(i - 1)) {
                return false;
            }
        }
        return true;
    }
}
