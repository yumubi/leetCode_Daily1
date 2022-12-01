package number_Complement_476;

import org.junit.Test;

public class Solution {
    /**
     * my answer
     * @param num
     * @return
     */
    public int findComplement(int num) {
        int n = num;
        int cnt = 0;
        while( n > 0) {
            n >>>= 1;
            cnt++;
        }
        return  (~num) & ((1 << cnt) - 1);
    }


    /**
     * 根据题目的要求，我们需要将 num 二进制表示的每一位取反。然而在计算机存储整数时，并不会仅仅存储有效的二进制位。
     * 例如当 num=5 时，它的二进制表示为 (101)2而使用 32位整数存储时的结果为：(0000~0000~0000~0000~0000~0000~0000~0101)2
     * 因此我们需要首先找到 num 二进制表示最高位的那个 1，再将这个 1 以及更低的位进行取反。
     * 如果 num 二进制表示最高位的 1 是第 i(0≤i≤30) 位，那么一定有：
     * 2^i <=num < 2^{i+1}
     * 因此我们可以使用一次遍历，在 [0,30] 中找出 i 的值。
     * 在这之后，我们就可以遍历 num 的第0∼i 个二进制位，将它们依次进行取反。
     * 我们也可以用更高效的方式，构造掩码 mask=2^(i+1)−1，它是一个 i+1 位的二进制数，并且每一位都是 1。
     * 我们将 num 与 \tmask 进行异或运算，即可得到答案。
     * 细节
     * 当i=30 时，构造 mask=2^(i+1)−1 的过程中需要保证不会产生整数溢出。
     * @param num
     * @return
     */
    public int findComplement1(int num) {
        int highbit = 0;
        for(int i = 1; i <= 30; ++i) {
            if(num >= 1 << i) highbit = i;
            else break;
        }
        int mask = highbit == 30 ? 0x7fffffff : (1 << (highbit + 1)) - 1;
        return num ^ mask;
    }


    /**
     * 遍历
     * 返回对 num的二进制表示取反的数，注意 num的二进制表示是不包含前导零的。
     * 因此主要问题求得 num 最高位 1 的位置。
     * 一个简单的做法是：先对 num 进行「从高到低」的检查，找到最高位 1 的位置 s，然后再对 num进行遍历，将低位到 s 位的位置执行逐位取反操作。
     * @param num
     * @return
     */
    public int findComplement2(int num) {
        int s = -1;
        for(int i = 31; i >= 0; i--) {
            if( ( (num >> i) & 1) != 0 ) {
                s = i;
                break;
            }
        }
        int ans = 0;
        for(int i = 0; i < s; i++) {
            if(((num >> i) & 1 ) == 0) ans |= (1 << i);
        }
        return ans;
    }

    // TODO: 1/12/2022 lowbit待研究
    /**
     * lowbit
     * 通过解法一我们发现，如果 num 的二进制表示中最高位 1 的位置为 s 的话，那么实际上我们只需要对num 的前 s −1 位进行取反即是答案（第s位的取反结果始终为 0）。
     * 因此我们可以先使用 lowbit 操作来得到 num 二进制表示中最高位 1 的位置为 1，其余位为 0 时所代表的数字 x。
     * 然后 x - 1 即是二进制表示中前s−1 位均为 1，其余位为 0 的数字，将其与 num 的取反数执行「按位与」操作，即可达到「仅对 numnum 的前 s−1 位进行取反」的效果。
     * @param num
     * @return
     */
    public int findComplement3(int num) {
        int x = 0;
        for(int i = num; i != 0; i -= i & -i) x = i;
        return ~num & (x - 1);
    }




    @Test
    public void test() {
        System.out.println(findComplement(4));
    }
}
