package keyboard_Row_500;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Solution {

    /**
     * 不行，不会写
     * @param words
     * @return
     */
    public String[] findWords(String[] words) {
        String first = "eiopqrtuwy";
        Set set1 = Stream.of(first).collect(Collectors.toSet());
        String second = "adfghjkls";
        Set set2 = Stream.of(first).collect(Collectors.toSet());
        String third = "bcmnvxz";
        Set set3 = Stream.of(first).collect(Collectors.toSet());
        List<String> strs = new ArrayList<String>();
        for (String s : words) {
            Set set = Stream.of(s.toLowerCase()).distinct().sorted().collect(Collectors.toSet());
            if (set1.containsAll(set) || set2.containsAll(set) || set3.containsAll(set))
                strs.add(s);
        }
            int size = strs.size();
            String[] ss = new String[size];
            for(int i = 0; i < strs.size(); i++) ss[i] = strs.get(i);
            return ss;
    }

    /**
     * 遍历
     * 我们为每一个英文字母标记其对应键盘上的行号，然后检测字符串中所有字符对应的行号是否相同。
     * 我们可以预处理计算出每个字符对应的行号。
     * 遍历字符串时，统一将大写字母转化为小写字母方便计算。
     * @param words
     * @return
     */
    public String[] findWords1(String[] words) {
        List<String> list = new ArrayList<String>();
        String rowIdx = "12210111011122000010020202";
        for(String word : words) {
            boolean isValid = true;
            char idx = rowIdx.charAt(Character.toLowerCase(word.charAt(0)) - 'a');
            for(int i = 1; i < word.length(); ++i) {
                if(rowIdx.charAt(Character.toLowerCase(word.charAt(i)) - 'a') != idx) {
                    isValid = false;
                    break;
                }
            }
            if(isValid) list.add(word);
        }
        String[] ans = new String[list.size()];
        for(int i = 0; i < list.size(); ++i) ans[i] = list.get(i);
        return ans;
    }

    /**
     *先将键盘上的三行字母进行打表分类，
     *依次检查 words 中的单词中的每个字符是否都属于同一编号，
     *若属于同一编号，则将其单词加入答案
     */
    static String[] ss = new String[]{"qwertyuiop", "asdfghjkl", "zxcvbnm"};
    static int[] hash = new int[26];
    static {
        for(int i = 0; i < ss.length; i++) {
            for(char c : ss[i].toCharArray()) hash[c - 'a'] = i;
        }
    }
    public String[] findWords2(String[] words) {
        List<String> list = new ArrayList<>();
        out : for(String w : words) {
            int t = -1;
            for(char c : w.toCharArray()) {
                c = Character.toLowerCase(c);
                if(t == -1) t = hash[c - 'a'];
                else if(t != hash[c - 'a']) continue out;
            }
            list.add(w);
        }
        return list.toArray(new String[list.size()]);
    }


    private static int[] chars = new int[26];
    static {
        // 整理26个字母分别每一行对应1、2、3
        set("qwertyuiop", 1);
        set("asdfghjkl", 2);
        set("zxcbbnm", 3);
    }
    public String[] findWords3(String[] words) {
        List<String> ans = new ArrayList<>();
        for(String word : words) {
            if(check(word)) ans.add(word);
        }
        return ans.toArray(new String[ans.size()]);
    }
    private static void set(String s, int value) {
        for(int i = 0; i < s.length(); i++) chars[index(s.charAt(i))] = value;
    }

    private static int index(char c) {
        if(c >= 'A' && c <= 'Z') return c - 'A';
        else return c - 'a';
    }

    private static boolean check(String s) {
        if(s.isEmpty()) return true;
        int val = chars[index(s.charAt(0))];
        for(int i = 1; i < s.length(); i++) {
            if(chars[index(s.charAt(i))] != val) return false;
        }
        return true;
    }


    // TODO: 3/12/2022  
    public String[] findWords4(String[] words) {
        return Arrays.asList(words)
                .stream()
                .filter(s ->
                        s.toLowerCase().replaceAll("^[qwertyuiop]*$", "").equals("")
                                || s.toLowerCase().replaceAll("^[asdfghjkl]*$", "").equals("")
                                || s.toLowerCase().replaceAll("^[zxcvbnm]*$", "").equals("")
                )
                .collect(Collectors.toList())
                .toArray(new String[0]);
    }

    public String[] findWords5(String[] words) {
        String s1 = "qwertyuiop";
        String s2 = "asdfghjkl";
        String s3 = "zxcvbnm";
        List<String> list = new ArrayList<>();
        for(String word : words) {
            int n1 = 0, n2 = 0, n3 = 0, leng = word.length();
            for(int i = 0; i < leng; i++) {
                if(s1.contains(word.charAt(i) + "")) n1++;
                else if(s2.contains(word.charAt(i) + "")) n2++;
                else n3++;
            }
            if(n1 == leng || n2 == leng || n3 == leng) list.add(word);
        }
        return list.toArray(new String[list.size()]);
    }




    @Test
    public void test() {
        String[] s = {"Hello","Alaska","Dad","Peace"};
        System.out.println(Arrays.toString(s));
    }

}
