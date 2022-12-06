//package student_Attendance_RecordI_551;
//
//public class Solution {
//    public boolean checkRecord(String s) {
//        int Absent = 0;
//        boolean preLate = false;
//        int lateCnt = 0;
//        for(int i = 0; i < s.length(); i++) {
//            if(Absent >= 2 || lateCnt >= 3) return false;
//            if(s.charAt(i) == 'A') Absent++;
//            if(s.charAt(i) == 'L') {
//                lateCnt++;
//                preLate = true;
//            }
//            if(preLate == true && s.charAt(i) != 'L') {
//                lateCnt = 0;
//                preLate = false;
//            }
//        }
//        if(lateCnt >= 3 || Absent >= 2) return false;
//        return true;
//    }
//
//    public boolean checkRecord1(String s) {
//
//    }
//
//
//}
