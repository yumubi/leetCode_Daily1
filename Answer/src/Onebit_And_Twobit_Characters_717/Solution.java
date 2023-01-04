package Onebit_And_Twobit_Characters_717;

import org.junit.Test;

import java.util.Arrays;

public class Solution {
    public boolean isOneBitCharacter(int[] bits) {
        int n = bits.length;
        int idx = 0;
        while (idx < n - 1) {
            if(idx == n - 2 && bits[idx] == 1) return false;
            if(bits[idx] == 0) idx++;
            else idx += 2;
        }
        return true;
    }


    /**
     * 正序遍历
     * 根据题意，第一种字符一定以 00 开头，第二种字符一定以 11 开头。
     * 我们可以对bits 数组从左到右遍历。当遍历到 bits[i] 时，如果 bits[i]=0，说明遇到了第一种字符，将 i 的值增加 1；
     * 如果bits[i]=1，说明遇到了第二种字符，可以跳过bits[i+1]（注意题目保证 bits 一定以 0 结尾，所以 bits[i] 一定不是末尾比特，
     * 因此bits[i+1] 必然存在），将 i 的值增加 2。
     * 上述流程也说明bits 的编码方式是唯一确定的，因此若遍历到 i=n−1，那么说明最后一个字符一定是第一种字符。
     * 时间复杂度：O(n)，其中 n 是数组bits 的长度。
     * 空间复杂度：O(1)。
     * @param bits
     * @return
     */
    public boolean isOneBitCharacter01(int[] bits) {
        int n = bits.length, i = 0;
        while (i < n - 1) i += bits[i] + 1;
        return i == n - 1;
    }


    /**
     * 倒序遍历
     * 根据题意，0 一定是一个字符的结尾。
     * 我们可以找到 bits 的倒数第二个 0 的位置，记作 i（不存在时定义为 -1），那么 bits[i+1] 一定是一个字符的开头，
     * 且从 bits[i+1] 到 bits[n−2] 的这 n−2−i 个比特均为 1。
     * 如果 n−2−i 为偶数，则这些比特 1 组成了(n−2−i) / 2个第二种字符，所以 bits 的最后一个比特 0 一定组成了第一种字符。
     * 如果 n−2−i 为奇数，则这些比特 1 的前 n−3−i 个比特组成了 (n-3-i) / 2个第二种字符，多出的一个比特 1 和 bits 的最后一个比特 0 组成第二种字符。
     * 由于n−i 和 n−2−i 的奇偶性相同，我们可以通过判断 n−i 是否为偶数来判断最后一个字符是否为第一种字符，
     * 若为偶数则返回 true，否则返回 false。
     * 时间复杂度：O(n)，其中 n 是数组 bits 的长度。
     * 空间复杂度：O(1)。
     *
     *
     * 逆序遍历优势在于不用遍历完全部数组。关键点是只有 “0”，“10”，“11” 三种编码。 首先，观察数组最后一位，
     * 题干中说了，肯定是 0，但是我们不能确定是 “0” 还是 “10”，所以采取的策略是：看看这个 0 前面有多少个连续的 1，为什么是连续呢 ？ 因为我们只要碰到 0 时，
     * 不管是 “0” 也好， “10” 也好，都代表着一个字符的结尾。注意：没有 “1” 这个编码！我们只需看看连续的 1 个数是奇数还是偶数，
     * 如果是偶数，代表这些连续的 1 都组成了“11”，如果是奇数，代表最后一个“1”落单了，由于没有 “1” 这个编码，所以只能和最后一位 “0” 进行配对了。
     * @param bits
     * @return
     */
    public boolean isOneBitCharacter02(int[] bits) {
        //两个0之间最多存在一个10，那么只需要看1的个数的奇偶性就行了
        int n = bits.length, i = n - 2;
        while (i >= 0 && bits[i] == 1) --i;
        return (n - i) % 2 == 0;
    }

    /**
     * 正则表达式：由题意可知，数组有若干10/11/0后边跟个0：
     * @param bits
     * @return
     */
    public boolean isOneBitCharacter03(int[] bits) {
        return Arrays.toString(bits).replaceAll("[\\[\\]\\s,]","").matches("(10|11|0)*0$");
    }




    @Test
    public void test() {
        int[] nums = new int[]{1, 1, 1, 0};
        System.out.println(isOneBitCharacter(nums));
    }
}
