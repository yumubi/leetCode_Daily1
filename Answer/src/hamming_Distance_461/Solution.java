package hamming_Distance_461;

public class Solution {
    public int hammingDistance(int x, int y) {
        int n = x ^ y;
        return Integer.bitCount(n);
    }

    /**
     * 移位实现计数
     * 时间复杂度：O(logC)，其中 C 是元素的数据范围，在本题中 logC=log2^31
     * 空间复杂度：O(1)
     * @param x
     * @param y
     * @return
     */
    public int hammingDistance1(int x, int y) {
        int s = x ^ y, ret = 0;
        while(s != 0) {
            ret += s & 1;
            s >>= 1;
        }
        return ret;
    }

    /**
     * BK算法
     * @param x
     * @param y
     * @return
     */
    public int hammingDistance2(int x, int y) {
        int s = x ^ y, ret = 0;
        while(s != 0) {
            s &= s - 1;
            ret++;
        }
        return ret;
    }

    /**
     * 逐位比较
     * 本身不改变 x 和 y，每次取不同的偏移位进行比较，不同则加一
     * 循环固定取满 32
     * 时间复杂度：O(C)，C 固定为 32
     * 空间复杂度：O(1)
     * @param x
     * @param y
     * @return
     */
    public int hammingDistance3(int x, int y) {
        int ans = 0;
        for(int i = 0; i < 32; i++) {
            int a = (x >> i) & 1, b = (y >> i)  & 1;
            ans += a ^ b;
        }
        return ans;
    }


    /**
     * 右移统计
     * 每次都统计当前 x 和 y 的最后一位，统计完则将 x 和 y 右移一位。
     * 当 x 和 y 的最高一位 1 都被统计过之后，循环结束。
     * @param x
     * @param y
     * @return
     */
    public int hammingDistance4(int x, int y) {
        int ans = 0;
        while((x | y) != 0) {
            int a = x & 1, b = y & 1;
            ans += a ^ b;
            x >>= 1;
            y >>= 1;
        }
        return ans;
    }

    /**
     * lowbit
     * 熟悉树状数组的同学都知道，lowbit 可以快速求得 x 二进制表示中最低位 1 表示的值。
     * 因此我们可以先将 x 和 yy进行异或，再统计异或结果中 1 的个数。
     * @param x
     * @return
     */
    int lowbit(int x) {
        return x & -x;
    }
    public int hammingDistance5(int x, int y) {
        int ans = 0;
        for(int i = x ^ y; i > 0; i -= lowbit(i)) ans++;
        return ans;
    }
}
