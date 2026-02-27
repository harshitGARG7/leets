class Solution {
    public int[] sortByBits(int[] arr) {
        Arrays.sort(arr);
        Arrays.sort(arr, (a, b) -> 
            Integer.bitCount(a) - Integer.bitCount(b)
        );
        return arr;
    }
}