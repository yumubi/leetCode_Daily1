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









    @Test
    public void test() {
        String s = "aabbaabb";
        System.out.println(repeatedSubstringPattern(s));
    }

}
