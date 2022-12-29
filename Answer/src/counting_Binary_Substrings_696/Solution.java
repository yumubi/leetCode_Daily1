package counting_Binary_Substrings_696;

import java.util.ArrayList;
import java.util.List;

public class Solution {

    /**
     * 超时了
     * @param s
     * @return
     */
    public int countBinarySubstrings(String s) {
        int cnt = 0;
        for(int i = 0; i < s.length() - 1; i++) {
            char start = s.charAt(i);
            int firstCnt = 0, secondCnt = 0;
            boolean flag = false;
            for(int j = i; j < s.length(); j++) {
                if(s.charAt(j) == start && flag == false) firstCnt++;
                else if(s.charAt(j) == start && flag == true) break;
                else if(flag == false && s.charAt(j) != start) {
                    flag = true;
                    secondCnt = 1;
                }
                else secondCnt++;
                if(secondCnt == firstCnt) {
                    cnt++;
                    break;
                }
            }
        }

        return cnt;
    }

    /**
     * 按照字符分组
     * 我们可以将字符串 s 按照 0 和 1 的连续段分组，存在 counts 数组中，例如 s=00111011，可以得到这样的 counts 数组：counts={2,3,1,2}。
     * 这里 counts 数组中两个相邻的数一定代表的是两种不同的字符。假设 counts 数组中两个相邻的数字为 u 或者 v，它们对应着 u 个 0 和 v 个 1，或者 u 个1 和 v 个 0。
     * 它们能组成的满足条件的子串数目为 min{u,v}，即一对相邻的数字对答案的贡献。
     * 我们只要遍历所有相邻的数对，求它们的贡献总和，即可得到答案。
     * 这个实现的时间复杂度和空间复杂度都是 O(n)
     * @param s
     * @return
     */
    public int countBinarySubstrings01(String s) {
        List<Integer> counts = new ArrayList<Integer>();
        int ptr = 0, n = s.length();
        while(ptr < n) {
            char c = s.charAt(ptr);
            int count = 0;
            while(ptr < n && s.charAt(ptr) == c) {
                ++ptr;
                ++count;
            }
            counts.add(count);
        }
        int ans = 0;
        for(int i = 1; i < counts.size(); ++i) {
            ans += Math.min(counts.get(i), counts.get(i - 1));
        }
        return ans;
    }


    /**
     * 对于某一个位置 i，其实我们只关心 i−1 位置的 counts 值是多少，
     * 所以可以用一个 last 变量来维护当前位置的前一个位置，这样可以省去一个 counts 数组的空间。
     * 时间复杂度：O(n)
     * 空间复杂度：O(1)
     * @param s
     * @return
     */
    public int countBinarySubstrings02(String s) {
        int ptr = 0, n = s.length(), last = 0, ans = 0;
        while(ptr < n) {
            char c = s.charAt(ptr);
            int count = 0;
            while(ptr < n && s.charAt(ptr) == c) {
                ++ptr;
                ++count;
            }
            ans += Math.min(count, last);
            last = count;
        }
        return ans;
    }


}
