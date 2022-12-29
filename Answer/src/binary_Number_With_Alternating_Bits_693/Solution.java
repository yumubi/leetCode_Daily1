package binary_Number_With_Alternating_Bits_693;

import org.junit.Test;

public class Solution {
    public boolean hasAlternatingBits(int n) {
        boolean isOdd = (n & 1) == 1;
        while(n > 0) {
            n >>= 1;
            if(n == 0) return true;
            boolean signal = (n & 1) == 1;
            isOdd = !isOdd;
            if(signal != isOdd) return false;
        }
        return true;
    }


    /**
     * 模拟
     * 从最低位至最高位，我们用对 2 取模再除以 2 的方法，
     * 依次求出输入的二进制表示的每一位，并与前一位进行比较。如果相同，则不符合条件；如果每次比较都不相同，则符合条件。
     * 时间复杂度：O(logn)。输入 n 的二进制表示最多有O(logn) 位。
     * 空间复杂度：O(1)。使用了常数空间来存储中间变量。
     * @param n
     * @return
     */
    public boolean hasAlternatingBits02(int n) {
        int prev = 2;
        while(n != 0) {
            int cur = n % 2;
            if(cur == prev) return false;
            prev = cur;
            n /= 2;
        }
        return true;


//        int cur = -1;
//        while (n != 0) {
//            int u = n & 1;
//            if ((cur ^ u) == 0) return false;
//            cur = u; n >>= 1;
//        }
//        return true;

    }


    /**
     * 对输入 n 的二进制表示右移一位后，得到的数字再与 n 按位异或得到 a。当且仅当输入 n 为交替位二进制数时，a 的二进制表示全为 1（不包括前导 0）。
     * 这里进行简单证明：当 a 的某一位为 1 时，当且仅当 n 的对应位和其前一位相异。当 a 的每一位为 1 时，当且仅当 n 的所有相邻位相异，即 n 为交替位二进制数。
     * 将 a 与 a+1 按位与，当且仅当 a 的二进制表示全为 1 时，结果为 0。这里进行简单证明：
     * 当且仅当 a 的二进制表示全为 1 时，a+1 可以进位，并将原最高位置为 0，按位与的结果为 0。否则，不会产生进位，两个最高位都为 1，相与结果不为 0。
     *
     * 另外一种更为巧妙的方式是利用交替位二进制数性质。
     * 当给定值 n 为交替位二进制数时，将 n 右移一位得到的值 m 仍为交替位二进制数，且与原数 n 错开一位，两者异或能够得到形如 0000...1111的结果 x，
     * 此时对 x 执行加法（进位操作）能够得到形如 0000...10000的结果，将该结果与 x 执行按位与后能够得到全 0 结果。
     * 结合上述两步，可以判断输入是否为交替位二进制数。
     *
     *
     * 时间复杂度：O(1)。仅使用了常数时间来计算。
     * 空间复杂度：O(1)。使用了常数空间来存储中间变量
     * @param n
     * @return
     */
    public boolean hasAlternatingBits03(int n) {
        int a = n^ (n >> 1);
        return (a & (a + 1)) == 0 ;
    }



    public boolean hasAlternatingBits04(int n) {
        //调用库函数
        char[] bits = Integer.toBinaryString(n).toCharArray();
        for (int i = 0; i < bits.length - 1; i++) {
            if (bits[i] == bits[i + 1]) {
                return false;
            }
        }
        return true;
    }



    @Test
    public void test() {
        int n = 5;
        System.out.println(hasAlternatingBits(n));
    }
}
