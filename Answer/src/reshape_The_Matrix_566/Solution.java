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

    /**
     * 使用 idxidx 记录新矩阵当前分配到的位置（一维），利用通用的一维转二维方式对应回行列坐标即可。
     * @param nums
     * @param r
     * @param c
     * @return
     */
    public int[][] matrixReshape1(int[][] nums, int r, int c) {
//        int m = nums.length;
//        int n = nums[0].length;
//        if(m * n != r * c) return nums;
//        int[][] ans = new int[r][c];
//        for(int x = 0; x < m * n; ++x) ans[x / c][x % c] = nums[x / n][x % n];
//        return ans;
                int or = nums.length, oc = nums[0].length;
                if (or * oc != r * c) return nums;
                int[][] ans = new int[r][c];
                for (int i = 0, idx = 0; i < or; i++) {
                    for (int j = 0; j < oc; j++, idx++) {
                        ans[idx / c][idx % c] = nums[i][j];
                    }
                }
                return ans;
            }

}
