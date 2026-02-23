public class binaryGap {
    public int findBinaryGap(int n) {
        int lastIndex = -1;
        int maxDistance = 0;
        int currentIndex = 0;

        while (n > 0) {
            if ((n & 1) == 1) { // if current bit is 1
                if (lastIndex != -1) {
                    maxDistance = Math.max(maxDistance, currentIndex - lastIndex);
                }
                lastIndex = currentIndex;
            }
            n = n >> 1; // shift right
            currentIndex++;
        }

        return maxDistance;
    }

}
