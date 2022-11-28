package number_Of_Segments_In_A_String_434;


import org.junit.Test;

public class Solution {
    public int countSegemts(String s) {
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

    @Test
    public void test() {
        String s = "    ";
        System.out.println(s.trim().length());
        System.out.println(countSegemts(s));
    }
}
