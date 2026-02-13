class Solution {

    public int longestBalanced(String s) {
        int n = s.length();
        char[] arr = s.toCharArray();

        // 1️⃣ Single character case
        int best = longestSameChar(arr);

        // 2️⃣ Two character cases
        best = Math.max(best, longestTwoChar(arr, 'a', 'b'));
        best = Math.max(best, longestTwoChar(arr, 'a', 'c'));
        best = Math.max(best, longestTwoChar(arr, 'b', 'c'));

        // 3️⃣ Three character case
        best = Math.max(best, longestThreeChar(arr));

        return best;
    }

    // Longest substring with only one distinct character
    private int longestSameChar(char[] arr) {
        int maxLen = 1, run = 1;

        for (int i = 1; i < arr.length; i++) {
            if (arr[i] == arr[i - 1]) {
                run++;
            } else {
                run = 1;
            }
            maxLen = Math.max(maxLen, run);
        }
        return maxLen;
    }

    // Longest balanced substring using exactly two characters
    private int longestTwoChar(char[] arr, char x, char y) {
        int n = arr.length;
        int offset = n;
        int diff = offset;
        int lastInvalid = -1;
        int ans = 0;

        int[] firstSeen = new int[2 * n + 1];
        Arrays.fill(firstSeen, Integer.MIN_VALUE);
        firstSeen[diff] = -1;

        for (int i = 0; i < n; i++) {
            char ch = arr[i];

            if (ch != x && ch != y) {
                diff = offset;
                lastInvalid = i;
                firstSeen[diff] = i;
                continue;
            }

            diff += (ch == x) ? 1 : -1;

            if (firstSeen[diff] < lastInvalid) {
                firstSeen[diff] = i;
            } else {
                ans = Math.max(ans, i - firstSeen[diff]);
            }
        }
        return ans;
    }

    // Longest balanced substring using all three characters
    private int longestThreeChar(char[] arr) {
        long base = (long) 2e18;
        long state = base;

        Map<Long, Integer> seen = new HashMap<>();
        seen.put(state, -1);

        int ans = 0;

        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == 'a') state += 1_000_001L;
            else if (arr[i] == 'b') state -= 1_000_000L;
            else state -= 1L;

            if (seen.containsKey(state)) {
                ans = Math.max(ans, i - seen.get(state));
            } else {
                seen.put(state, i);
            }
        }
        return ans;
    }
}
