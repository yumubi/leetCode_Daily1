package unique_Morse_Code_Words_804;

import java.util.HashSet;
import java.util.Set;

public class Solution {
    public int uniqueMorseRepresentations(String[] words) {
        String[] table = {".-","-...","-.-.","-..",".","..-.","--.","....","..",".---",
                "-.-",".-..","--","-.","---",".--.","--.-",".-.","...","-","..-","...-",
                ".--","-..-","-.--","--.."};
        Set<String> set = new HashSet<>();
        for (String word : words) {
            char[] chars = word.toCharArray();
            StringBuilder sb = new StringBuilder();
            for (Character ch : chars) {
                sb.append(table[ch - 'a']);
            }
            set.add(sb.toString());
        }
        return set.size();
    }

    public static final  String[] MORSE = {".-","-...","-.-.","-..",".","..-.","--.","....","..",".---",
            "-.-",".-..","--","-.","---",".--.","--.-",".-.","...","-","..-","...-",
            ".--","-..-","-.--","--.."};

    /**
     * 哈希表
     * 时间复杂度：O(S)，其中 S 是数组 words 中所有单词的长度之和。
     * 空间复杂度：O(S)，其中 S 是数组 words 中所有单词的长度之和。
     * @param words
     * @return
     */
    public int uniqueMorseRepresentation01(String[] words) {
        Set<String> seen = new HashSet<>();
        for(String word : words) {
            StringBuilder code = new StringBuilder();
            for(int i = 0; i < word.length(); i++) {
                char c = word.charAt(i);
                code.append(MORSE[c - 'a']);
            }
            seen.add(code.toString());
        }
        return seen.size();

    }


}
