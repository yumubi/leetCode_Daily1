package detect_Capital_520;

public class Solution {
    public boolean detectCapitalUse(String word) {
        if(word.length() == 1) return true;
        boolean fisrtCap = word.charAt(0) < 'a';
            if(fisrtCap == false) {
                for(int i = 0; i < word.length(); i++)
                if(word.charAt(i) < 'a') return false;
                return true;
        }

        for(int i = 1; i < word.length() - 1; i++) {
            boolean iscap1 = word.charAt(i) < 'a';
            boolean iscap2 = word.charAt(i + 1) < 'a';
           if (iscap1^iscap2) return false;
        }
        return true;
    }



    public boolean detectCapitalUse1(String word) {
        //若第一个字母为小写，则需要额外判断第二个字母是否为小写
        if(word.length() >= 2 && Character.isLowerCase(word.charAt(0)) && Character.isUpperCase(word.charAt(1))) return false;
        //无论第一个字母是否大写，其他字母必须与第二个字母大小相同
        for(int i = 2; i < word.length(); ++i) {
            if(Character.isLowerCase(word.charAt(i)) ^ Character.isLowerCase(word.charAt(1))) return false;
        }
        return true;
    }





    public boolean detectCapitalUse2(String word) {
        if(word.toUpperCase().equals(word)) return true;
        if(word.toLowerCase().equals(word)) return true;
        int n = word.length(), idx = 1;
        if(Character.isUpperCase(word.charAt(0)))
            while (idx < n && Character.isLowerCase(word.charAt(idx))) idx++;
        return idx == n;
    }

    public boolean detectCapitalUse3(String word) {
        int n = word.length(), cnt = 0;
        for(int i = 0; i < n; i++) {
            if(Character.isUpperCase(word.charAt(i))) cnt++;
        }
        return cnt == n || cnt == 0 || (cnt == 1 && Character.isUpperCase(word.charAt(0)));
    }

























}
