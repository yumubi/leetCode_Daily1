package license_Key_Formatting_482;

import org.junit.Test;

public class Solution {
    /**
     * ww 超过5%
     * @param s
     * @param k
     * @return
     */
    public String licenseKeyFormatting(String s, int k) {
        String[] strs = s.split("-");
        StringBuilder sb = new StringBuilder();
        for(String ss : strs) {
            System.out.println(ss);
            sb.append(ss.toUpperCase());
        }
        int size = sb.length();
        int offset = size % k;
        while(offset < size) {
            if(offset == 0) offset += k;
            if(offset >= size) break;
            sb.insert(offset, '-');
            size++;
            offset += (k + 1);
        }
        return sb.toString();
    }


    /**
     * 首先我们取出所有不为破折号的字符，题目要求对取出的字符进行重新分组，使得每个分组恰好包含 k 个字符，
     * 且必须满足第一个分组包含的字符个数必须小于等于 k，但至少要包含 1 个字符。设已经取出的字符的总数为 n，只需满足第一个分组包含的字符数目刚好等于 n mod k，
     * 剩余的分组包含的字符数目刚好等于 k。
     * 我们可以从字符串 s 的末尾开始往前取出字符构建新的字符串 ans。每次取出字符时首先判断该字符是否为破折号，
     * 如果为破折号则跳过；否则将当前的字符计数 cnt 加 1，同时检查如果当前字符为小写字母则将其转化为大写字母，将当前字符加入到字符串 ans 的末尾。
     * 对字符进行计数时，每隔 k 个字符就在字符串 \ans 中添加一个破折号。特殊情况需要处理，字符串 ans 的最后一个字符为破折号则将其去掉。
     * 我们对已经构建的字符串ans 进行反转即为返回结果。
     * @param s
     * @param k
     * @return
     */
    public String licenseKeyFormatting1(String s, int k) {
        StringBuilder ans = new StringBuilder();
        int cnt = 0;
        for(int i = s.length() - 1; i >= 0; i--) {
            if(s.charAt(i) != '-') cnt++;
            ans.append(Character.toUpperCase(s.charAt(i)));
            if(cnt % k == 0) ans.append("-");
        }
        if(ans.length() >  0 && ans.charAt(ans.length() - 1) == '-') ans.deleteCharAt(ans.length() - 1);
        return ans.reverse().toString();
    }



    @Test
    public void test() {
        String s = "a-a-a-a-";
        int k = 1;
        System.out.println(licenseKeyFormatting(s, k));
    }
}
