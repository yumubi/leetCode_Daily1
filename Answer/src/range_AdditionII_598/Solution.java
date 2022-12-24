package range_AdditionII_598;

public class Solution {
    public int maxCount(int m, int n, int[][] ops) {
        int xmin = m;
        int ymin = n;
        for(int i = 0; i < ops.length; i++) {
            if(ops[i][0] < xmin) xmin = ops[i][0];
            if(ops[i][1] < ymin) ymin = ops[i][1];
        }
        return xmin * ymin;
    }


    /**
     * 维护所有操作的交集
     * 时间复杂度：O(k)，其中 k 是数组 ops 的长度。
     * 空间复杂度：O(1)
     * @param m
     * @param n
     * @param ops
     * @return
     */
    public int maxCount01(int m, int n, int[][] ops) {
        int mina = m, minb = n;
        for(int[] op : ops) {
            mina = Math.min(mina, op[0]);
            minb = Math.min(minb, op[1]);
        }
        return mina * minb;
    }
}
