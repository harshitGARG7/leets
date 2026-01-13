class Solution {
    public double separateSquares(int[][] squares) {
        double totalArea = 0;
        double low = Double.MAX_VALUE;
        double high = Double.MIN_VALUE;

        for (int[] s : squares) {
            int y = s[1], l = s[2];
            totalArea += (double) l * l;
            low = Math.min(low, y);
            high = Math.max(high, y + l);
        }

        double target = totalArea / 2.0;

        // Binary search
        for (int iter = 0; iter < 100; iter++) { // enough for precision
            double mid = (low + high) / 2.0;
            double areaBelow = 0;

            for (int[] s : squares) {
                int y = s[1], l = s[2];
                if (mid <= y) continue;
                else if (mid >= y + l) areaBelow += (double) l * l;
                else areaBelow += (mid - y) * l;
            }

            if (areaBelow < target) low = mid;
            else high = mid;
        }

        return low;
    }
}
