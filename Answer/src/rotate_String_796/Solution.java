package rotate_String_796;

public class Solution {
    public boolean rotateString(String s, String goal) {
        if(s.length() != goal.length()) return false;
        StringBuilder sb = new StringBuilder(s + s);
        if(sb.toString().contains(goal)) return true;
        return false;
    }


    /**
     * 首先，如果 s 和 goal 的长度不一样，那么无论怎么旋转，s 都不能得到 goal，
     * 返回 false。在长度一样（都为 n）的前提下，假设 s 旋转 i 位，则与 goal 中的某一位字符 goal[j] 对应的原 s 中的字符应该为 s[(i+j)mod n]。
     * 在固定 i 的情况下，遍历所有 j，若对应字符都相同，则返回 true。
     * 否则，继续遍历其他候选的 i。若所有的 i 都不能使 s 变成goal，则返回 false。
     * 时间复杂度：O(n^2)，其中 n 是字符串 s 的长度。我们需要双重循环来判断。
     * 空间复杂度：O(1)。仅使用常数空间
     * @param s
     * @param goal
     * @return
     */
    public boolean rotateString01(String s, String goal) {
        int m = s.length(), n = goal.length();
        if(m != n) return false;
        for(int i = 0; i < n; i++) {
            boolean flag = true;
            for(int j = 0; j < n; j++) {
                if( (s.charAt(i + j) % n) != goal.charAt(j)){
                    flag = false;
                    break;
                }
            }
            if(flag) return true;
        }
        return false;
    }


    /**
     * 首先，如果 s 和 goal 的长度不一样，那么无论怎么旋转，s 都不能得到 goal，返回 false。
     * 字符串 s+s 包含了所有 s 可以通过旋转操作得到的字符串，只需要检查 goal 是否为 s+s 的子字符串即可
     * 时间复杂度：O(n)，其中 n 是字符串 s 的长度。KMP 算法搜索子字符串的时间复杂度为 O(n)，其他搜索子字符串的方法会略有差异。
     * 空间复杂度：O(n)，其中 n 是字符串 s 的长度。KMP 算法搜索子字符串的空间复杂度为 O(n)，其他搜索子字符串的方法会略有差异
     * @param s
     * @param goal
     * @return
     */
    public boolean rotateString02(String s, String goal) {
        return s.length() == goal.length() && (s + s).contains(goal);
    }


    public boolean rotateString03(String s, String goal) {
        if(s.length() != goal.length()) return false;
        String str = new StringBuilder().append(s).append(s).toString();
        return indexOf(str, goal) == -1 ? false : true;
    }
    public int indexOf(String str, String pattern) {
        if(pattern.length() == 0) return 0;
        int m = str.length();
        int n = pattern.length();

        str = " " + str;
        pattern = " " + pattern;
        int[] next = new int[n + 1];
        for(int i = 2, j = 0; i < n + 1; i++) {
            while (j > 0 && pattern.charAt(i) != pattern.charAt(j + 1)) j = next[j];
            if(pattern.charAt(j) == pattern.charAt(j + 1)) j++;
            next[i] = j;
        }

        for(int i = 1, j = 0; i < m + 1; i++) {
            while (j > 0 && str.charAt(i) != pattern.charAt(j + 1)) j = next[j];
            if(str.charAt(i) == pattern.charAt(j + 1)) j++;
            if(j == n) return i - n;
        }
        return -1;
    }

//-------------------------------------------------------------------------------------
    // TODO: 10/1/2023 KMP模板

        static int N = 10010;
        int[]ne = new int[N];
        public int kmp(String str, String pattern) {
            if(pattern.length() == 0)return 0;
            int m = str.length();
            int n = pattern.length();
            str = " "+str;
            pattern = " "+pattern;
            for (int i = 2 , j = 0 ; i <= n;i++){
                while(j > 0 && pattern.charAt(i) != pattern.charAt(j+1))j = ne[j];
                if (pattern.charAt(i) == pattern.charAt(j+1))j++;
                ne[i] = j;
            }

            for (int i = 1 ,j = 0 ; i<= m;i++){
                while(j > 0 && str.charAt(i) != pattern.charAt(j+1))j = ne[j];
                if (str.charAt(i) == pattern.charAt(j+1))j++;
                if (j == n){
                    j = ne[j];
                    // 一般用于书写匹配成功后的逻辑
                    return i - n ;
                }
            }
            return -1;

        }
        //----------------------------------------------------------------------------------------------


}
