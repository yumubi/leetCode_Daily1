package reversing_StringII_541;

import org.junit.Test;

public class Solution {
    public String reverseStr(String s, int k) {
        int cnt = -1;
        char[] chars = s.toCharArray();
        for(int i = 0; i < chars.length; i++) {
            cnt = (cnt + 1) % (2 * k);
            if(cnt == k - 1) reverse(chars, i - k + 1, i);
        }
        if( cnt < k - 1)  reverse(chars, chars.length - 1 - cnt, chars.length - 1);
        return String.valueOf(chars);
    }
    public void reverse(char[] chars, int start, int end) {
        while (start <= end) {
            char tmp = chars[start];
            chars[start] = chars[end];
            chars[end] = tmp;
            start++;
            end--;
        }
    }


    /**
     * 反转每个下标从 2k的倍数开始的，长度为 k的子串。若该子串长度不足 k，则反转整个子串。
     * @param s
     * @param k
     * @return
     */
    public String reverseStr1(String s, int k) {
        int n = s.length();
        char[] arr = s.toCharArray();
        for(int i = 0; i < n; i += 2 * k) reverse1(arr, i, Math.min(i + k, n) - 1);
        return new String(arr);
    }
    public void reverse1(char[] arr, int left, int right) {
        while (left < right) {
            char tmp = arr[left];
            arr[left] = arr[right];
            arr[right] = tmp;
            left++;
            right--;
        }
    }


    /**
     * 模拟
     * 使用 l 和 r 两个指针分别圈出每次需要翻转的“理论”范围，每次翻转完更新 l 和 r，
     * 同时注意范围 [l, r] 内不足 k 的情况（将 r 与真实边界 n - 1取个 min）。
     * 时间复杂度：O(n)
     * 空间复杂度：Java 中 String 属于不可变，复杂度为 O(n)
     * @param s
     * @param k
     * @return
     */
    public String reverseStr2(String s, int k) {
        char[] cs = s.toCharArray();
        int n = s.length();
        for(int l = 0; l < n; l = l + 2 * k) {
            int r = l + k - 1;
            reverse2(cs, l, Math.min(r, n - 1));
        }
        return String.valueOf(cs);
    }
    void reverse2(char[] cs, int l, int r) {
        while (l < r) {
            char c = cs[l];
            cs[l] = cs[r];
            cs[r] = c;
            l++;
            r--;
        }
    }







        @Test
        public void test() {
            String s = "hyzqyljrnigxvdtneasepfahmtyhlohwxmkqcdfehybknvdmfrfvtbsovjbdhevlfxpdaovjgunjqlimjkfnqcqnajmebeddqsgl";
            System.out.println(s);
            int k = 39;
            System.out.println(reverseStr(s, k));
            System.out.println("fdcqkmxwholhytmhafpesaentdvxginrjlyqzyhehybknvdmfrfvtbsovjbdhevlfxpdaovjgunjqllgsqddebemjanqcqnfkjmi");
        }
}
