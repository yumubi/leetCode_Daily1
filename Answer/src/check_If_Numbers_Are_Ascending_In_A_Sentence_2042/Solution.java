package check_If_Numbers_Are_Ascending_In_A_Sentence_2042;

public class Solution {
    public boolean areNumbersAscending(String s) {
       int prevNum = -1;
        String[] strs = s.split(" ");
        for(String ss : strs) {
            boolean isDigit = true;
           for(int i = 0; i < ss.length(); i++) {
               if(ss.charAt(i) >= 97) {
                   isDigit = false;
                   break;
               }
           }
           if(isDigit) {
               int n = Integer.valueOf(ss);
               if(n <= prevNum) return false;
               else prevNum = n;
           }
        }
        return true;
    }


    /**
     * 直接遍历
     * 题目要求检查给定的字符串 s 中 token 为数字时是否从左到右严格递增，根据题意可知相邻的 token 之间由空格分割，我们按照要求依次取出字符串中的每个token，
     * 如果当前的token 由数字组成，将该 token 转换为十进制数cur，设前一个数字token 转换后的整数 pre，检验过程如下:
     * 如果 cur 大于 pre，则认为当前的token 满足递增要求，更新 pre 为 cur，并检测下一个数字 token 是否满足递增；
     * 如果 cur 小于或者等于 pre，则认为不满足递增要求，返回 false；
     * 由于题目中的每个数字 token 转换后的十进制数均为正整数且小于 100，因此我们可以初始化 pre 等于 0，我们依次检测每个为数字的 token 是否满足题目要求即可。
     * 时间复杂度：O(n)，其中 n 表示字符串的长度。我们只需遍历一遍字符串即可。
     * 空间复杂度：O(1)。仅用到若干额外变量。
     * @param s
     * @return
     */
    public boolean areNumbersAscending01(String s) {
        int pre = 0, pos = 0;
        while (pos < s.length()) {
            if(Character.isDigit(s.charAt(pos))) {
                int cur = 0;
                while (pos < s.length() && Character.isDigit(s.charAt(pos))) {
                    cur = cur * 10 + s.charAt(pos) - '0';
                    pos++;
                }
                if(cur <= pre) return false;
                pre = cur;
            } else pos++;
        }
        return false;
    }


}
