package self_Dividing_Numbers_728;

import java.util.ArrayList;
import java.util.List;

public class Solution {
    public List<Integer> selfDividingNumbers(int left, int right) {
            List<Integer> ret = new ArrayList<>();
            for(int i = left; i <= right; i++) {
                if(isSelfDividingNum(i)) ret.add(i);
            }
            return ret;
    }

    public boolean isSelfDividingNum(int num) {
        int Num = num;
        while (true) {
            if(num == 0) return true;
            int n = num % 10;
            if(n == 0) return false;
            if(Num % n == 0) num /= 10;
            else return false;
        }
    }


    /**
     * 直接判断
     * 遍历范围 [left,right] 内的所有整数，分别判断每个整数是否为自除数。
     * 根据自除数的定义，如果一个整数不包含 00 且能被它包含的每一位数整除，则该整数是自除数。
     * 判断一个整数是否为自除数的方法是遍历整数的每一位，判断每一位数是否为 0 以及是否可以整除该整数。
     * 遍历整数的每一位的方法是，每次将当前整数对 10 取模即可得到当前整数的最后一位，
     * 然后将整数除以 10。重复该操作，直到当前整数变成 0 时即遍历了整数的每一位。
     * 时间复杂度：O(nlog right)，其中 n 是范围内的整数个数，right 是范围内的最大整数。对于范围内的每个整数，需要 O(log right) 的时间判断是否为自除数。
     * 空间复杂度：O(1)。除了返回值以外，使用的额外空间为 O(1)。
     * @param left
     * @param right
     * @return
     */
    public List<Integer> selfDividingNumbers01(int left, int right) {
        List<Integer> ans = new ArrayList<>();
        for(int i = left; i <= right; i++) {
            if(isSelfDividing(i)) ans.add(i);
        }
        return ans;
    }
    public boolean isSelfDividing(int num) {
        int temp = num;
        while (temp > 0) {
            int digit = temp % 10;
            if(digit == 0 || num % digit != 0) return false;
            temp /= 10;
        }
        return true;
    }

    /**
     * 模拟
     * 时间复杂度：令 n = right - left + 1 复杂度为 O(n ∗ log right)
     * 空间复杂度：O(1)
     * @param left
     * @param right
     * @return
     */
    public List<Integer> selfDividingNumbers02(int left, int right) {
        List<Integer> ans = new ArrayList<>();
        out:for(int i = left; i <= right; i++) {
            int cur = i;
            while (cur != 0) {
                int t = cur % 10;
                if(t == 0 || i % t != 0) continue out;
                cur /= 10;
            }
            ans.add(i);
        }
        return ans;
    }




    /**
     * 打表+二分
     * 利用数据范围只有 1e4，我们可以打表预处理出所有的自除数，通过二分找到 [left,right] 范围内的最小自除数，
     * 再从前往后找到所有合法的自除数。
     * 时间复杂度：令 m 为范围在[left,right] 之间的自除数的数量 n=right−left+1。复杂度为O(log m + n)，对于本题，m 上界为 339
     * 空间复杂度：O(m)
     * @param left
     * @param right
     * @return
     */
    static List<Integer> list = new ArrayList<>();
    static {
        out:for(int i = 1; i <= 10000; i++) {
            int cur = i;
            while (cur != 0) {
                int u = cur % 10;
                if (u == 0 || i % u != 0) continue out;
                cur /= 10;
            }
            list.add(i);
        }
    }

    public List<Integer> selfDividingNumbers03(int left, int right) {
        List<Integer> ans = new ArrayList<>();
        int l = 0, r = list.size() - 1;
        while (l < r) {
            int mid = l + r >> 1;
            if(list.get(mid) >= left) r = mid;
            else l = mid + 1;
        }
        while (r < list.size() && list.get(r) <= right) ans.add(list.get(r++));
        return ans;
    }


    //打表+哈希
    /**
     * 在打表预处理了所有范围内的自除数的基础上，我们可以干脆将索引也预处理出来，从而避免二分操作。
     * 其中 hash[x] 的含义为值不超过 x 的最大自除数在 list 中的下标。
     * 时间复杂度：n = right - left + 1。复杂度为O(n)
     * 空间复杂度：O(C)
     * 最后
     */
    static List<Integer> list04 = new ArrayList<>();
    static int[] hash = new int[10010];
    static {
        for(int i = 1; i <= 10000; i++) {
            int cur= i;
            boolean ok = true;
            while (cur != 0 && ok) {
                int u = cur % 10;
                if(u == 0 || i % u != 0) ok = false;
                cur /= 10;
            }
            if(ok) list04.add(i);
            hash[i] = list04.size() - 1;
        }
    }

    public List<Integer> selfDividingNumbers04(int left, int right) {
        List<Integer> ans = new ArrayList<>();
        int idx = list04.get(hash[left]) == left ? hash[left] : hash[left] + 1;
        while (idx < list04.size() && list04.get(idx)<= right) ans.add(list04.get(idx++));
        return ans;
    }


    /**
     * 先将数字转为字符串再提取每一位的代码
     * @param left
     * @param right
     * @return
     */
        private static List<Integer> selfDividingNumbers05(int left, int right) {
            List<Integer> result = new ArrayList<>();
            int flag = 0; //代表是自除数
            for (int i = left; i <= right; i++){
                String str = String.valueOf(i); //将i转为字符串
                int len = str.length();
                for (int j = 0; j < len; j++){
                    int k = Integer.parseInt(String.valueOf(str.charAt(j)));
                    if (k == 0 || (i % k != 0)){
                        flag = 1;
                        break;
                    }
                }
                if (flag == 0){
                    result.add(i);
                }
                flag = 0;
            }
            return result;
        }




}
