package shortest_Distance_To_A_Character_821;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

public class Solution {
    public int[] shortestToChar(String s, char c) {
        int[] answers = new int[s.length()];
        char[] chars = s.toCharArray();
        for(int i = 0; i < chars.length; i++) {
            char ch = chars[i];
            int left = i, right = i;
            while (left >= 0 && right < chars.length) {
                if(chars[left] == c || chars[right] == c)
                {
                    answers[i] = i - left;
                    break;
                }
                    left--;
                    right++;

            }
            if(left < 0) {
                for(int j = right; j < chars.length; j++) {
                    if(chars[j] == c) {
                        answers[i] = j - i;
                       break;
                    }
                }
            }
            if(right >= chars.length) {
                for(int j = left; j >= 0; j--){
                    if(chars[j] == c) {
                        answers[i] = i - j;
                        break;
                    }
                }
            }
        }
        return answers;
    }

    /**
     * 两次遍历
     * 问题可以转换成，对 s 的每个下标 i，求
     * s[i] 到其左侧最近的字符 c 的距离
     * s[i] 到其右侧最近的字符 c 的距离
     * 这两者的最小值。
     * 对于前者，我们可以从左往右遍历 s，若 s[i]=c 则记录下此时字符 c 的的下标 idx。遍历的同时更新 answer[i]=i−idx。
     * 对于后者，我们可以从右往左遍历 s，若 s[i]=c 则记录下此时字符 c 的的下标 idx。遍历的同时更新 answer[i]=min(answer[i],idx−i)。
     * 代码实现时，在开始遍历的时候 idx 可能不存在，为了简化逻辑，我们可以用 −n 或 2n 表示，这里 n 是 s 的长度。
     * 时间复杂度：O(n)，其中 n 是字符串 s 的长度。
     * 空间复杂度：O(1)。返回值不计入空间复杂度。
     * @param s
     * @param c
     * @return
     */
    public int[] shortestToChar01(String s, char c) {
        int n = s.length();
        int[] ans = new int[n];
        for(int i = 0, idx = -n; i < n; ++i) {
            if(s.charAt(i) == c) idx = i;
            ans[i] = i - idx;
        }
        for(int i = n - 1, idx = 2 * n; i >= 0; --i) {
            if(s.charAt(i) == c) idx = i;
            ans[i] = Math.min(ans[i], idx - i);
        }
        return ans;
    }

    /**
     * 直接遍历
     * 根据题意进行模拟即可：两次遍历，第一次找到每个 i 左边最近的 c，第二次找到每个 i 右边最近的 c。
     *  时间复杂度：O(n)，其中 n 是字符串 s 的长度。
     *  空间复杂度：O(1)。返回值不计入空间复杂度。
     * @param s
     * @param c
     * @return
     */
    public int[] shortestToChar02(String s, char c) {
        int n = s.length();
        int[] ans = new int[n];
        Arrays.fill(ans, n + 1);
        for(int i = 0, j = -1; i < n; i++) {
            if(s.charAt(i) == c) j = i;
            if(j != -1) ans[i] = i - j;
        }
        for(int i = n - 1, j = -1; i >= 0; i--) {
            if(s.charAt(i) ==c ) j = i;
            if(j != -1) ans[i] = Math.min(ans[i], j - i);
        }
        return ans;
    }

    /**
     * BFS
     * 起始令所有的 ans[i]=−1，然后将所有的 c 字符的下标入队，并更新 ans[i] = 0
     * 然后跑一遍 BFS 逻辑，通过 ans[i] 是否为 −1 来判断是否重复入队。
     * 时间复杂度：O(n)
     * 空间复杂度：O(n)
     * @param s
     * @param c
     * @return
     */
    public int[] shortestToChar03(String s, char c) {
        int n = s.length();
        int[] ans = new int[n];
        Arrays.fill(ans, -1);
        Deque<Integer> d = new ArrayDeque<>();
        for(int i = 0; i < n; i++) {
            if(s.charAt(i) == c) {
                d.addLast(i);
                ans[i] = 0;
            }
        }
        int[] dirs = new int[]{-1, 1};
        while (!d.isEmpty()) {
            int t = d.pollLast();
            for(int di : dirs) {
                int ne = t + di;
                if(ne >= 0 && ne < n && ans[ne] == -1) {
                    ans[ne] = ans[t] + 1;
                    d.addLast(ne);;
                }
            }
        }
        return ans;
    }

}
