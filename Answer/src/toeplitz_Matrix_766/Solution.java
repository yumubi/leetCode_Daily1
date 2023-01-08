package toeplitz_Matrix_766;

import org.junit.Test;

public class Solution {
    /**
     * 不会
     * @param matrix
     * @return
     */
    public boolean isToeplitzMatrix(int[][] matrix) {
        int x = matrix.length - 1;
        int y = matrix[0].length - 1;

        for(int i = x; i >= 0; i--) {
            int std = matrix[i][0];
            int t = i;
            for(int j = 0; j <= x - i; j++) {
                int cur = matrix[t++][j];
                if(cur != std) return false;
            }
        }
        for(int j = 2; j <= y; j++) {
            int std = matrix[0][j];
            int t = j;
            for(int i = 0; i <= y - j; i++) {
                if(matrix[i][t++] != std) return false;
            }
        }
        return true;
    }


    /**
     * 按 格子 遍历
     * 根据定义，当且仅当矩阵中每个元素都与其左上角相邻的元素（如果存在）相等时，
     * 该矩阵为托普利茨矩阵。因此，我们遍历该矩阵，将每一个元素和它左上角的元素相比对即可。
     *
     * 对于一个合格的「托普利茨矩阵」而言，每个格子总是与其左上角格子相等（如果有的话）。
     * 我们以「格子」为单位进行遍历，每次与左上角的格子进行检查即可。
     * 这样我们每对一个格子进行判断，都要读 matrix 上的两个格子的值（即非边缘格子其实会被读取两次）
     *
     * 时间复杂度：O(mn)，其中 m 为矩阵的行数，n 为矩阵的列数。矩阵中每个元素至多被访问两次。
     * 空间复杂度：O(1)，我们只需要常数的空间保存若干变量。
     * @param matrix
     * @return
     */
    public boolean isToeplitzMatrix01(int[][] matrix) {
        int m = matrix.length, n = matrix[0].length;
        for(int i = 1; i < m; i++) {
            for(int j = 1; j < n; j++) {
                if(matrix[i][j] != matrix[i - 1][j - 1]) return false;
            }
        }
        return true;
    }

    /**
     * 按 线 遍历
     * 如果稍微增加一点点难度：限制每个格子只能被读取一次呢？
     * 这时候我们也可以按照「线」为单位进行检查。
     * 当一条完整的斜线值都相等，我们再对下一条斜线做检查。
     * 这样对于每个格子，我们都是严格只读取一次（
     * 如果整个矩阵是存在磁盘中，且不考虑操作系统的按页读取等机制，那么 IO 成本将下降为原来的一半。
     * 时间复杂度：O(n * m)
     * 空间复杂度：O(1)
     * @param matrix
     * @return
     */
    public boolean isToeplitzMatrix02(int[][] matrix) {
            int m = matrix.length, n = matrix[0].length;
            int row = m, col = n;
            while (col-- > 0) {
                for(int i = 0, j = col, val = matrix[i++][j++]; i < m && j < n; i++, j++) if(matrix[i][j] != val) return false;
            }
            while (row-- > 0) {
                for(int i = row, j = 0, val = matrix[i++][j++]; i < m && j < n; i++, j++) {
                    if(matrix[i][j] != val) return false;
                }
            }
            return true;
    }

    @Test
    public void test() {
        int[][] matrix = new int[][]{{1,2},{2,2}};
        System.out.println(isToeplitzMatrix(matrix));
    }


}
