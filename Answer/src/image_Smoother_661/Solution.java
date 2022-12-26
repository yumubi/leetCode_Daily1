package image_Smoother_661;

import org.junit.Test;

import java.util.Arrays;

public class Solution {

    /**
     * 数组越界，但一直找不到哪里越界了
     * @param img
     * @return
     */
    public int[][] imageSmoother(int[][] img) {
        int[][] ret = new int[img.length][img[0].length];
        for(int i = 0; i < img.length; i++) {
            for(int j = 0; i < img[0].length; j++) {
                ret[i][j] = avg(img, i, j);
            }
        }
        return ret;
    }

    public int avg(int[][] img, int i, int j) {
        int sum = 0;
        int cell = 9;
        aux(img, i, j, -1, -1, cell, sum);
        aux(img, i, j, -1, 0, cell, sum);
        aux(img, i, j, -1, 1, cell, sum);
        aux(img, i, j, 0, -1, cell, sum);
        aux(img, i, j, 0, 0, cell, sum);
        aux(img, i, j, 0, 1, cell, sum);
        aux(img, i, j, 1, -1, cell, sum);
        aux(img, i, j, 1, 0, cell, sum);
        aux(img, i, j, 1, 1, cell, sum);

        return sum / cell;

    }

    public void aux(int[][] img, int i, int j, int x, int y, int cell, int sum) {
        if( (i + x) < 0 || (i + x) >= img.length || (j + y) < 0 || (j + y) >= img[0].length) cell--;
        else {
            int a = i + x;
            int b = j + y;
            sum += img[a][b];
        }
    }


    /**
     * 遍历
     * 时间复杂度：O(mnC^2)，其中 m 为给定矩阵的行数，n 为给定矩阵的列数，C=3 为过滤器的宽高。
     * 我们需要遍历整个矩阵以计算每个位置的值，计算单个位置的值的时间复杂度为 O(C^2)
     * 空间复杂度：O(1)。注意返回值不计入空间复杂度。
     * @param img
     * @return
     */
    public int[][] imageSmoother01(int[][] img) {
        int m = img.length, n = img[0].length;
        int[][] ret = new int[m][n];
        for(int i = 0; i < m; i++) {
            for(int j = 0; j < n; j++) {
                int num = 0, sum = 0;
                for(int x = i - 1; x <= i + 1; x++) {
                    for(int y = j - 1; y <= j + 1; j++) {
                        if(x >= 0 && x < m && y >= 0 && y < n) {
                            num++;
                            sum += img[x][y];
                        }
                    }
                }
                ret[i][j] = sum / num;
            }
        }
        return ret;
    }



    public int[][] imageSmoother02(int[][] img) {
        int m = img.length, n = img[0].length;
        int[][] ans = new int[m][n];
        int[][] dirs = new int[][]{ {0, 0}, {1, 0}, {-1, 0}, {0, 1}, {0, -1}, {-1, -1}, {-1, 1}, {1, -1}, {1, 1} };
        for(int i = 0; i < m; i++) {
            for(int j = 0; j < n; j++) {
                int tot = 0, cnt = 0;
                for(int[] di : dirs) {
                    int nx = i + di[0], ny = j + di[1];
                    if(nx < 0 || nx >= m || ny < 0 || ny >= n) continue;
                    tot += img[nx][ny];
                    cnt++;
                }
                ans[i][j] = tot / cnt;
            }
        }
        return ans;
    }



    @Test
    public void test() {
        int[][] img = new int[][] {{100,200,100}, {200,50,200}, {100,200,100}};
        System.out.println(Arrays.toString(imageSmoother(img)));
    }



}
