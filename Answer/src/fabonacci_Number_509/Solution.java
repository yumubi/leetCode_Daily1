package fabonacci_Number_509;

import java.util.HashMap;
import java.util.Map;

public class Solution {
    public int fib(int n) {
        if(n == 0) return 0;
        int[] dp = new int[n + 1];
        dp[0] = 0;
        dp[1] = 1;
        for(int i = 2; i < n + 1; i++)
            dp[i] = dp[i - 1] + dp[i - 2];
        return dp[n];
    }

    public int fib1(int n) {
        int first = 0;
        int second = 1;
        while (n-- > 0) {
            int temp = first + second;
            first = second;
            second = temp;
        }
        return first;
    }

    public int fib2(int n) {
        return fib2(n, new HashMap());
    }

    /**
     * memo递归
     * @param n
     * @param map
     * @return
     */
    public int fib2(int n, Map<Integer, Integer> map) {
        if(n < 2) return n;
        if(map.containsKey(n)) return map.get(n);
        int first = fib2(n - 1, map);
        int second = fib2(n - 2, map);
        int res = first + second;
        map.put(n, res);
        return res;
    }


    /**
     * 矩阵快速幂
     * @param n
     * @return
     */
        public int fib3(int n) {
            if (n < 2) {
                return n;
            }
            int[][] q = {{1, 1}, {1, 0}};
            int[][] res = pow(q, n - 1);
            return res[0][0];
        }

        public int[][] pow(int[][] a, int n) {
            int[][] ret = {{1, 0}, {0, 1}};
            while (n > 0) {
                if ((n & 1) == 1) {
                    ret = multiply(ret, a);
                }
                n >>= 1;
                a = multiply(a, a);
            }
            return ret;
        }

        public int[][] multiply(int[][] a, int[][] b) {
            int[][] c = new int[2][2];
            for (int i = 0; i < 2; i++) {
                for (int j = 0; j < 2; j++) {
                    c[i][j] = a[i][0] * b[0][j] + a[i][1] * b[1][j];
                }
            }
            return c;
        }



}
