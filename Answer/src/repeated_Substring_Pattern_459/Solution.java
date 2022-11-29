package repeated_Substring_Pattern_459;

import org.junit.Test;

import java.util.List;

public class Solution {
    /**
     * 屎山代码，还不知道哪里错了
     * @param s
     * @return
     */
    public boolean repeatedSubstringPattern(String s) {
        int len = s.length();
        char[] chars = s.toCharArray();
        if((len & 1) == 1) {
            for (int n = 3; n < len; n += 2) {
                if (len % n == 0) {
                    int L = len / n;
                    String str = String.copyValueOf(chars);
                    while(str != "") {
                        if(str.startsWith(s.substring(0, L))) str.replace(s.substring(0, L), "");
                        else break;
                    }
                    if(str == "") return true;
                    else break;
                }
            }
        }

        if((len & 1) == 0) {
            for (int n = 2; n < len; n++) {
                if (len % n == 0) {
                    int L = len / n;
                    String str = String.copyValueOf(chars);
                    while(str != "") {
                        if(str.startsWith(s.substring(0, L))) str.replace(s.substring(0, L), "");
                        else break;
                    }
                    if(str == "") return true;
                    else break;
                }
            }
        }

        return false;
    }

    /**
     * 枚举
     * 如果一个长度为 n 的字符串 s 可以由它的一个长度为 n'的子串 s'重复多次构成，则
     *  n 一定是 n'的倍数；
     *  s'一定是 s 的前缀；
     *  对于任意的 i∈[n′,n)，有 s[i]=s[i−n′]。
     * 也就是说，s 中长度为 n'的前缀就是 s'，并且在这之后的每一个位置上的字符 s[i]，都需要与它之前的第 n'个字符 s[i-n']相同。
     * 因此，我们可以从小到大枚举 n'，并对字符串 s 进行遍历，进行上述的判断。
     * 注意到一个小优化是，因为子串至少需要重复一次，所以 n'
     * 不会大于 n 的一半，我们只需要在 [1, n / 2]的范围内枚举 n'即可。
     *
     * 时间复杂度：O(n^2)其中 n 是字符串 s 的长度。
     * 枚举 i的时间复杂度为 O(n)，遍历 s 的时间复杂度为 O(n)，相乘即为总时间复杂度
     * 空间复杂度：O(1)
     * @param s
     * @return
     */
    public boolean repeatedSubstringPattern1(String s) {
        int n = s.length();
        for(int i = 1; i * 2 <= n; ++i) {
            if(n % i == 0) {
                boolean match = true;
                for(int j = i; j < n; ++j) {
                    if(s.charAt(j) != s.charAt(j - i)) {
                        match = false;
                        break;
                    }
                }
                if(match) return true;
            }
        }
        return false;
    }

    /**
     * 字符串匹配
     * 我们可以把字符串 s 写成s's'...s's'的形式 ，总计 n / n′个 s'但我们如何在不枚举 n'的情况下，
     * 判断 s是否能写成上述的形式呢
     * 如果我们移除字符串 s 的前 n'个字符（即一个完整的 s'），再将这些字符保持顺序添加到剩余字符串的末尾，那么得到的字符串仍然是 s。
     * 由于 1 <= n' < n，那么如果将两个 s 连在一起，并移除第一个和最后一个字符，那么得到的字符串一定包含 s，即 s 是它的一个子串。
     * 因此我们可以考虑这种方法：我们将两个 s 连在一起，并移除第一个和最后一个字符。如果 s 是该字符串的子串，那么 s 就满足题目要求
     *
     * 注意到我们证明的是如果 s 满足题目要求，那么 s 有这样的性质，而我们使用的方法却是如果 s 有这样的性质，
     * 那么 s 满足题目要求。因此，只证明了充分性是远远不够的，我们还需要证明必要性。
     * 从位置 1 开始查询，并希望查询结果不为位置 nn，这与移除字符串的第一个和最后一个字符是等价的。
     * @param s
     * @return
     */
    public boolean repeatedSubstringPattern2(String s) {
        return (s + s).indexOf(s, 1) != s.length();
    }













    @Test
    public void test() {
        String s = "aabbaabb";
        System.out.println(repeatedSubstringPattern(s));
    }

}
