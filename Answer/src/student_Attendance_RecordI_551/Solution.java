package student_Attendance_RecordI_551;

public class Solution {
    public boolean checkRecord(String s) {
        int Absent = 0;
        boolean preLate = false;
        int lateCnt = 0;
        for(int i = 0; i < s.length(); i++) {
            if(Absent >= 2 || lateCnt >= 3) return false;
            if(s.charAt(i) == 'A') Absent++;
            if(s.charAt(i) == 'L') {
                lateCnt++;
                preLate = true;
            }
            if(preLate == true && s.charAt(i) != 'L') {
                lateCnt = 0;
                preLate = false;
            }
        }
        if(lateCnt >= 3 || Absent >= 2) return false;
        return true;
    }

    /**
     * 遍历过程中，记录缺勤次数和连续迟到次数，根据遍历到的字符更新缺勤次数和连续迟到次数：
     *      如果遇到 ‘A’，即缺勤，则将缺勤次数加 1，否则缺勤次数不变；
     *      如果遇到 ‘L’，即迟到，则将连续迟到次数加 1，否则将连续迟到次数清零。
     * @param s
     * @return
     */
    public boolean checkRecord1(String s) {
        int absents = 0, lates = 0;
        int n = s.length();
        for (int i = 0; i < n; i++) {
            char c = s.charAt(i);
            if (c == 'A') {
                absents++;
                if (absents >= 2) return false;
            }
            if (c == 'L') {
                lates++;
                if (lates >= 3) return false;
            } else lates = 0;
        }
        return true;
    }


//
//        int absent = 0;
//        int late = 0;
//        for(char c: s){
//            if(c == 'A') ++absent;
//            if(c == 'L') ++late;
//            else late = 0; //如果不是连续3天L就会被置0了
//            if( absent > 1 || late > 2) return false;
//        }
//        return true;

// return s.indexOf("A") == s.lastIndexOf("A") && !s.contains("LLL");


        public boolean checkRecord2(String s) {
            int n = s.length();
            char[] cs = s.toCharArray();
            for(int i = 0, cnt = 0; i < n;) {
                char c = cs[i];
                if(c == 'A') {
                    cnt++;
                    if(cnt >= 2) return false;
                } else if(c == 'L') {
                    int j = i;
                    while (j < n && cs[j] == 'L') j++;
                    int len = j - i;
                    if(len >= 3) return false;
                    i = j;
                    continue;
                }
                i++;
            }
            return true;
    }







}
