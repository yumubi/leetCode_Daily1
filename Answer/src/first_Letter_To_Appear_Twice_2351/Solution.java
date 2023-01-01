package first_Letter_To_Appear_Twice_2351;

import java.util.HashSet;
import java.util.Set;

public class Solution {
    public char repeatedCharacter(String s) {
        Set<Character> set = new HashSet<>();
        int len = s.length();
        for(int i = 0; i < len; i++) {
            if(!set.add(s.charAt(i))) return s.charAt(i);
        }
        return s.charAt(1);
    }

    /**
     * 我们可以使用一个哈希表记录每个字母是否出现过。
     * 具体地，我们对字符串 s 进行一次遍历。当遍历到字母 c 时，
     * 如果哈希表中包含 c，我们返回 c 作为答案即可；否则，我们将 c 加入哈希表。
     * 时间复杂度：O(n)，其中 n 是字符串 s 的长度。
     * 空间复杂度：O(∣Σ∣)，其中 Σ 是字符集，在本题中字符串只包含小写字母，因此 ∣Σ∣=26。即为哈希表需要使用的空间。
     * @param s
     * @return
     */
    public char repeatedCharacter01(String s) {
        Set<Character> seen = new HashSet<Character>();
        for(int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            if(!seen.add(ch)) return ch;
        }
        //impossible
        return ' ';
    }

    /**
     * 注意到字符集的大小为 26，因此我们可以使用一个 32 位的二进制数 seen 完美地存储哈希表。
     * 如果 seen 的第 i(0≤i<26) 位是 1，说明第 i 个小写字母已经出现过。
     * 具体地，我们对字符串 s进行一次遍历。当遍历到字母 c 时，记它是第 i 个字母，seen 的第 i(0≤i<26) 位是 1，我们返回 c 作为答案即可；
     * 否则，我们将seen 的第 i 位置为 1。
     * 时间复杂度：O(n)，其中 n 是字符串 s 的长度。
     * 空间复杂度：O(1)
     * @param s
     * @return
     */
    public char repeatedCharacter02(String s) {
        int seen = 0;
        for(int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            int x = ch - 'a';
            if( (seen & (1 << x)) != 0 ) return ch;
            seen |= (1 << x);
        }
        return ' ';
    }


}
