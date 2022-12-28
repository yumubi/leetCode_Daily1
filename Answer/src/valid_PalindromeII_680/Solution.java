package valid_PalindromeII_680;

public class Solution {
    public boolean validPalindrome(String s) {
        int n = s.length();
        boolean deleted = false;
        int left = 0, right = n - 1;
        while(left <= right) {
            if(s.charAt(left) != s.charAt(right)) {
                if(s.charAt(left) != s.charAt(right - 1) && s.charAt(left + 1) != s.charAt(right)) return false;
                else if(s.charAt(left) == s.charAt(right - 1) && s.charAt(left + 1) == s.charAt(right)) {
                    return validPalindrome(s.substring(left, right)) || validPalindrome(s.substring(left + 1, right + 1));
                }
                else if(s.charAt(left) == s.charAt(right - 1)) {
                    if(deleted) return false;
                    right--;
                    deleted = true;
                }
                else {
                    if(deleted) return false;
                    left++;
                    deleted = true;
                }
            }
            left++;
            right--;
        }
        return true;
    }


    /**
     * 贪心
     * 虑最朴素的方法：首先判断原串是否是回文串，如果是，就返回 true；如果不是，则枚举每一个位置作为被删除的位置，再判断剩下的字符串是否是回文串。
     * 这种做法的渐进时间复杂度是 O(n^2)的，会超出时间限制。
     * 我们换一种想法。首先考虑如果不允许删除字符，如何判断一个字符串是否是回文串。常见的做法是使用双指针。
     * 定义左右指针，初始时分别指向字符串的第一个字符和最后一个字符，每次判断左右指针指向的字符是否相同，如果不相同，则不是回文串；
     * 如果相同，则将左右指针都往中间移动一位，直到左右指针相遇，则字符串是回文串。
     * 在允许最多删除一个字符的情况下，同样可以使用双指针，通过贪心实现。初始化两个指针low 和 high 分别指向字符串的第一个字符和最后一个字符。
     * 每次判断两个指针指向的字符是否相同，如果相同，则更新指针，将 low 加 1，high 减 1，然后判断更新后的指针范围内的子串是否是回文字符串。
     * 如果两个指针指向的字符不同，则两个字符中必须有一个被删除，此时我们就分成两种情况：即删除左指针对应的字符，留下子串 s[low+1:high]，
     * 或者删除右指针对应的字符，留下子串 s[low:high−1]。当这两个子串中至少有一个是回文串时，就说明原始字符串删除一个字符之后就以成为回文串。
     * 时间复杂度：O(n)，其中 n 是字符串的长度。判断整个字符串是否是回文字符串的时间复杂度是 O(n)，遇到不同字符时，判断两个子串是否是回文字符串的时间复杂度也都是 O(n)
     * 空间复杂度：O(1)。只需要维护有限的常量空间。
     * @param s
     * @return
     */
    public boolean validPalindrome01(String s) {
        int low = 0, high = s.length() - 1;
        while (low < high) {
            char c1 = s.charAt(low), c2 = s.charAt(high);
            if (c1 == c2) {
                ++low;
                --high;
            } else {
                return validPalindrome01(s, low, high - 1) || validPalindrome01(s, low + 1, high);
            }
        }
        return true;
    }
        public boolean validPalindrome01(String s, int low, int high) {
            for(int i = low, j = high; i < j; ++i, --j) {
                char c1 = s.charAt(i), c2 = s.charAt(j);
                if(c1 != c2) {
                    return false;
                }
            }
            return true;
        }



    int del = 0;  //记录删除的字符次数
    public boolean validPalindrome02(String s) {
        int i = 0,j = s.length()-1;
        while(i < j){
            if(s.charAt(i) == s.charAt(j)){
                i++;
                j--;
            }else{
                //不相等的话，若没有删除字符，则删除左边或右边的字符再判断；若删除过一次，则不是回文串
                if(del == 0){
                    del++;
                    return validPalindrome02(s.substring(i,j)) || validPalindrome02(s.substring(i+1,j+1));
                }
                return false;
            }
        }
        return true;
    }

}
