package perfect_Number_507;

import java.util.ArrayList;
import java.util.List;

public class Solution {

    public boolean checkPerfectNumber(int num) {
        List<Integer> divisors = new ArrayList<>();
        int sqrt = (int)Math.sqrt(num);
        for(int i = 2; i <= sqrt; i++) {
            if(num % i == 0) {
                divisors.add(i);
                divisors.add(num / i);
            }
        }
        for (Integer divisor : divisors ) {
            num -= divisor;
        }
        return num == 1;
    }


    /**
     * 枚举
     * 时间复杂度：O(sqrt{num})
     * 空间复杂度：O(1)。
     * @param num
     * @return
     */
    public boolean checkPerfectNumber1(int num) {
       if(num == 1) return false;
       int sum = 1;
       for(int d = 2; d * d <= num; ++d) {
           if(num % d == 0) {
               sum += d;
               if(d * d < num) sum += num /d;
           }
       }
       return sum == num;
    }

    /**
     * 同时为避免使用 sqrt 库函数和溢出 使用 i <= num / i作为上界判断。
     * 卧槽，这个num / i 实在是太庙了
     * @param num
     * @return
     */
    public boolean checkPerfectNumber2(int num) {
        if(num == 1) return false;
        int ans = 1;
        for(int i = 2; i <= num / i; i++) {
            if(num % 2 == 0) {
                ans += i;
                if(i * i != num) ans += num / i;
            }
        }
        return ans == num;
    }

    /**根据欧几里得-欧拉定理，每个偶完全数都可以写成
     * 2^(p−1) * (2^(p) - 1)
     *  p 为素数且 2^p−1 为素数。
     *  由于目前奇完全数还未被发现，因此题目范围 [1,10^8]内的完全数都可以写成上述形式。
     * @param num
     * @return
     */
    public boolean checkPerfectNumber4(int num) {
        return num == 6 || num == 28 || num == 496 || num == 8128 || num == 33550336;
    }


}
