package robot_Return_To_Origin_657;

import java.util.HashMap;

public class Solution {
    public boolean judgeCircle(String moves) {
        int Xpos = 0;
        int Ypos = 0;
        for(int i = 0; i < moves.length(); i++) {
            if(moves.charAt(i) == 'U') Ypos += 1;
            if(moves.charAt(i) == 'D') Ypos -= 1;
            if(moves.charAt(i) == 'U') Xpos += 1;
            if(moves.charAt(i) == 'U') Xpos -= 1;
        }
        return Xpos == 0 && Ypos == 0;
    }

    /**
     * 模拟
     * 时间复杂度：O(N)，其中 N 表示moves 指令串的长度。我们只需要遍历一遍字符串即可。
     * 空间复杂度：O(1)。我们只需要常数的空间来存放若干变量。
     * @param moves
     * @return
     */
    public boolean judgeCircle01(String moves) {
        int x = 0, y = 0;
        int length = moves.length();
        for(int i = 0; i < length; i++) {
            char move = moves.charAt(i);
            if(move == 'U') y--;
            else if(move == 'D') y++;
            else if(move == 'L') x--;
            else if(move == 'R') x++;
        }
        return x == 0 && y == 0;


//        int[] cnt = new int[26];
//        for(char c : moves.toCharArray()) cnt[c-'A']++;
//        return cnt['L'-'A']==cnt['R'-'A'] && cnt['U'-'A']==cnt['D'-'A'];


    }


    public boolean judgeCircle02(String moves) {
        HashMap<Character, Integer> map = new HashMap<Character, Integer>();
        map.put('U', 0);
        map.put('D', 0);
        map.put('L', 0);
        map.put('R', 0);
        for(int i = 0; i < moves.length(); i++) {
            char m = moves.charAt(i);
            int count = map.get(m) + 1;
            map.put(m, count);
        }
        return map.get('U').equals(map.get('D')) && map.get('L').equals(map.get('R'));
    }


}
