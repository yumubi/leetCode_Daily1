package prime_Number_Of_Set_Bits_In_Binary_Representation_762;

public class Solution {
    public int countPrimeSetBits(int left, int right) {
        int cnt = 0;
        for (int i = left; i <= right; i++) {
            int bits = Integer.bitCount(i);
            if(isPrime(bits)) cnt++;
        }
        return cnt;
    }
    public boolean isPrime(int x) {
       if(x < 2) return false;
       for(int i = 2; i * i <= x; ++i) {
           if(x % i == 0) return false;
       }
       return true;
    }


    /**
     * 时间复杂度：O( (right−left) sqrt(log right)。二进制中 1 的个数为O(log right)，
     * 判断值为 x 的数是否为质数的时间为 O(x)。
     * 空间复杂度：O(1)。我们只需要常数的空间保存若干变量。
     * @param left
     * @param right
     * @return
     */
    public int countPrimeSetBits01(int left, int right) {
        int ans = 0;
        for(int x = left; x <= right; ++x) {
            if(isPrime(Integer.bitCount(x))) ++ans;
        }
        return ans;
    }


    /**
     * 判断质数优化
     * 注意到 right≤10^6<2^20，因此二进制中 1 的个数不会超过 19，而不超过 19 的质数只有2,3,5,7,11,13,17,19
     * 我们可以用一个二进制数 mask=665772=10100010100010101100_2来存储这些质数，
     * 其中 mask 二进制的从低到高的第 i 位为 1 表示 i 是质数，为 0 表示 i 不是质数。
     * 设整数 x 的二进制中 1 的个数为 c，若 mask 按位与 2^c不为 0，则说明 c 是一个质数。
     * 时间复杂度：O(right−left)。
     * 空间复杂度：O(1)。我们只需要常数的空间保存若干变量。
     * @param left
     * @param right
     * @return
     */
    public int countPrimeSetBits02(int left, int right) {
        int ans = 0;
        for(int x = left; x <= right; ++x) {
            if( ((1 << Integer.bitCount(x) & 665772)) != 0) ++ans;
        }
        return ans;
    }

}