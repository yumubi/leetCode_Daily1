package reshape_The_Matrix_566;

public class Solution {
    /**
     * ac
     * @param mat
     * @param r
     * @param c
     * @return
     */
    public int[][] matrixReshape(int[][] mat, int r, int c){
            int m = mat.length;
            int n = mat[0].length;
            if(r * c != m * n) return mat;
            int idx = 0;
            int[][] matrix = new int[r][c];
            for(int i = 0; i < m; i++) {
                for(int j = 0; j < n; j++) {
                    matrix[idx / c][idx % c] = mat[i][j];
                    idx++;
                }
            }
            return matrix;
    }


    public int[][] matrixReshape1(int[][] nums, int r, int c) {
        
    }

}
