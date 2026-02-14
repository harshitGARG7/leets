class Solution {
    public double champagneTower(int poured, int query_row, int query_glass) {
        
        double[][] tower = new double[101][101];
        tower[0][0] = poured;
        
        for (int row = 0; row < 100; row++) {
            for (int col = 0; col <= row; col++) {
                
                if (tower[row][col] > 1.0) {
                    double overflow = (tower[row][col] - 1.0) / 2.0;
                    
                    tower[row + 1][col] += overflow;
                    tower[row + 1][col + 1] += overflow;
                    
                    tower[row][col] = 1.0;  // cap at full
                }
            }
        }
        
        return Math.min(1.0, tower[query_row][query_glass]);
    }
}
