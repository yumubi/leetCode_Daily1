package counting_Words_With_A_Given_Prefix_2185;

public class Solution {
    //时间复杂度：O(n×m)，其中 n 是输入 words 的长度，m 是输入pref 的长度。
    //空间复杂度：O(1)，仅需要常数空间。
    public int prefixCount(String[] words, String pref) {
        int count = 0;
        for (String word : words) {
            if(word.startsWith(pref)) count++;
        }
        return count;
    }

    /**
     * 从前往后，逐一字符比较。
     * 时间复杂度：O(nm)
     * 空间复杂度：O(1)
     * @param words
     * @param pref
     * @return
     */
    public int prefixCount01(String[] words, String pref) {
        int ans = 0, m = pref.length(), i;
        for(String s : words) {
            for(i = 0; i < s.length() && i < m && s.charAt(i) == pref.charAt(i); i++) {
                if(i == m) ans++;
            }
        }
        return ans;
    }


}
