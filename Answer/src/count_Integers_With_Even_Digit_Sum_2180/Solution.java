package count_Integers_With_Even_Digit_Sum_2180;

public class Solution {
    public int countEven(int num) {
        int cnt = 0;
        for(int i = num; i > 0; i--)
            if(numSumEven(i)) cnt++;
        return cnt;
    }

    public boolean numSumEven(int n) {
        int sum = 0;
        while (n > 0) {
            sum += n % 10;
            n /= 10;
        }
        if((sum & 1) == 0) return true;
        else return false;
    }

    /**
     * 暴力枚举
     * 时间复杂度：O(num×log num)。我们总共需要枚举num 个正整数，而判断每个正整数的各位数字之和是否为偶数需要 O(log num) 的时间复杂度。
     * 空间复杂度：O(1)
     * @param num
     * @return
     */
        public int countEven01(int num) {
            int res = 0;
            for (int i = 1; i <= num; i++) {
                int x = i, sum = 0;
                while (x != 0) {
                    sum += x % 10;
                    x /= 10;
                }
                if (sum % 2 == 0) {
                    res++;
                }
            }
            return res;
        }





}
