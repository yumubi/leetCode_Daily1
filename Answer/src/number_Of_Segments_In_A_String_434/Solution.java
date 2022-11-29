package number_Of_Segments_In_A_String_434;


import org.junit.Test;

public class Solution {
    public int countSegments(String s) {
        int cnt = 0;
        int idx = 0;
        s = s.trim();
        while(idx < s.length()) {
            while( idx < s.length() && s.charAt(idx) == ' ') idx++;
            cnt++;
            while(idx < s.length() && s.charAt(idx) != ' ') idx++;
        }
        return cnt;
    }

    /**
     * 计算字符串中单词的数量，就等同于计数单词的第一个下标的个数。
     * 因此，我们只需要遍历整个字符串，统计每个单词的第一个下标的数目即可。
     * 满足单词的第一个下标有以下两个条件：
     * 该下标对应的字符不为空格；  该下标为初始下标或者该下标的前下标对应的字符为空格；
     * 另一种方法直接使用语言内置的 split 函数可直接分离出字符串中的每个单词，在此我们不再详细展开。
     * @param s
     * @return
     */
    public int countSegments1(String s) {
        int segmentCount = 0;
        for(int i = 0; i < s.length(); i++) {
            if( (i == 0 || s.charAt(i - 1) == ' ') && s.charAt(i) != ' ') segmentCount++;
        }
        return segmentCount;
    }


    /**
     * 我们可以从前往后处理字符串 s 并进行计数，对于是空格的字符进行跳过（不计数），
     * 而对于非空格字符，则在遍历完一个完整单词（连续一段）后进行一次计数。
     * @param s
     * @return
     */
    public int countSegments2(String s) {
        int n = s.length();
        int ans = 0;
        for(int i = 0; i < n;) {
            if(s.charAt(i) == ' ' && i++ >= 0) continue;
            while(i < n && s.charAt(i) != ' ') i++;
            ans++;
        }
        return ans;
    }

    /**
     * 单词以一个或多个空位为分解，但最后一个单词后面可能没有空格，所以将字符串s后添加一个空格。
     * 遍历字符串，判断满足 s[i] == ' ' && s[i - 1] != ' 's的数量即可。
     * @param s
     * @return
     */
    public int countSegemts3(String s) {
        s += " ";
        char[] ch = s.toCharArray();
        int res = 0;
        for(int i = 1; i < ch.length; i++) {
            if(ch[i] == ' ' && ch[i - 1] != ' ') res++;
        }
        return res;
    }

    /**
     * 连续空格会切出空串
     * @param s
     * @return
     */
    public int countSegments4(String s) {
       int count = 0;
       for(String word : s.split(" "))
           if(! "".equals(word)) count++;
       return count;
    }





}
