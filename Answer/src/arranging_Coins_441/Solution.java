package arranging_Coins_441;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class Solution {
    /**
     *
     * 尝试打表，失败
     * 内存超出
     */
    public int arrangingCoins(int n) {
         List<Integer> table = new ArrayList<>();
            int j = 1;
            int reslut = 0;
            while(reslut <= n) {
                reslut = j * ( j + 1) / 2;
                table.add(reslut);
                j++;
            }
            for(int i = 0; i < table.size(); i++) {
                if(n < table.get(i)) return i - 1;
            }
            return 0;
    }

    /**
     *二分
     * @param n
     * @return
     */
    public int arrangeCoins1(int n) {
        int left = 1, right = n;
        while(left < right) {
            int mid = (right - left + 1) / 2 + left;//先加1再除以2是为了让中间值靠右，因为在后序对右边的值处理是 right = mid - 1
            if( (long)mid * (mid + 1) <= (long) 2 * n) left = mid;
            else right = mid - 1;
        }
        return left;
    }

    /**
     *对于 n个硬币而言，最多凑成的行数不会超过 n 行，当且仅当 n=1 的时候可以取到。
     * 假设最大能够凑成的行数为 x，那么以 x 为分割点的数轴上具有二段性，使用 n个硬币如果能够凑成 x 行，
     * 那么必然也能够凑成少于等于 x 行的阶梯硬币，必然不能凑成大于 x 行的阶梯硬币。
     * 对于 x 行阶梯硬币所需要的硬币数量，还是可以利用等差数列求和公式直接算出。
     * @param n
     * @return
     */
    public int arrangeCoin2(int n) {
        long l = 1, r = n;
        while(l < r) {
            long mid = l + r + 1 >> 1;//左移右移运算符结合性为从左到右，优先级最低
            if(mid * (mid + 1) / 2 <= n) l = mid;
            else r = mid - 1;
        }
        return (int) r;
    }


    /**
     * x<=⌊( −1+sqrt(1+8n) ) /  2⌋
     * @param n
     * @return
     */
    public int arrangeCoin3(int n) {
        return (int) ((Math.sqrt(1 + 8.0 * n) - 1) / 2);
    }





    @Test
    public void test() {
        System.out.println(arrangingCoins(6));
    }


}
