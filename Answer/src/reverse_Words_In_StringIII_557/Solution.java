package reverse_Words_In_StringIII_557;

import org.junit.Test;

public class Solution {
        public String reverseWords(String s) {
            int ptr = 0;
            char[] chars = s.toCharArray();
            for (int i = 0; i < chars.length; i++) {
                if (s.charAt(i) == ' ') {
                    reverse(chars, ptr, i - 1);
                    ptr = i + 1;
                }

            }
            reverse(chars, ptr, chars.length - 1);
            return String.valueOf(chars);
        }
            void reverse(char[] ch, int start, int end) {
                while (start < end) {
                    char tmp = ch[start];
                    ch[start] = ch[end];
                    ch[end] = tmp;
                    start++;
                    end--;
                }
            }

    /**
     * 开辟一个新字符串。然后从头到尾遍历原字符串，直到找到空格为止，此时找到了一个单词，并
     * 能得到单词的起止位置。随后，根据单词的起止位置，可以将该单词逆序放到新字符串当中。
     * 如此循环多次，直到遍历完原字符串，就能得到翻转后的结果。
     * @param s
     * @return
     */
    public String reverseWords1(String s) {
            StringBuffer ret = new StringBuffer();
            int length = s.length();
            int i = 0;
            while (i < length) {
                int start = i;
                while (i < length && s.charAt(i) != ' ') i++;
                for(int p = start; p < i; p++) {
                    ret.append(s.charAt(start + i - 1 - p));
                }
                while (i < length && s.charAt(i) == 'i') {
                    i++;
                    ret.append(' ');
                }
            }
            return ret.toString();
        }



            @Test
            public void test() {
                String s = "Let's take LeetCode contest";
                System.out.println(reverseWords(s));
            }

}
