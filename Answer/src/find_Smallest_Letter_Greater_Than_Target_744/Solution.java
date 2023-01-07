package find_Smallest_Letter_Greater_Than_Target_744;

public class Solution {
    public char nextGreatestLetter(char[] letters, char target) {
        int ans = 0;
        int left = 0, right = letters.length, n = letters.length - 1;
        while (left < right) {
           int mid = left + right >> 1;
           if( (int)letters[mid] > target ) {
               right = mid;
           } else if( (int)letters[mid] < target) {
               left = mid + 1;
           } else {
               ans = mid;
               break;
           }
           ans = left;
       }
        if(ans == n) {
            if(letters[ans] == target) return letters[0];
            else return letters[n];
        }
        else if(ans > n) return letters[0];
        while (letters[ans] == target && ans < n) {
            ans++;
        }
        if(letters[ans] == target) return letters[0];
        return letters[ans];
    }

    /**
     * 二分
     * 给定的数组「有序」，找到比 target 大的最小字母，容易想到二分。
     * 唯一需要注意的是，二分结束后需要再次 check，如果不满足，则取数组首位元素。
     * 时间复杂度：O(log n)
     * 空间复杂度：O(1)
     * @param letters
     * @param target
     * @return
     */
    public char nextGreatestLetter01(char[] letters, char target) {
        int n = letters.length;
        int l = 0, r = n - 1;
        while (l < r) {
            int mid = l + r >> 1;
            if(letters[mid] > target) r = mid;
            else l = mid + 1;
        }
        return letters[r] > target ? letters[r] : letters[0];
    }

    /**
     * 二分
     * 首先比较目标字母和列表中的最后一个字母，当目标字母大于或等于列表中的最后一个字母时，答案是列表的首个字母。
     * 当目标字母小于列表中的最后一个字母时，列表中一定存在比目标字母大的字母，可以使用二分查找得到比目标字母大的最小字母。
     * 初始时，二分查找的范围是整个列表的下标范围。每次比较当前下标处的字母和目标字母，如果当前下标处的字母大于目标字母，
     * 则在当前下标以及当前下标的左侧继续查找，否则在当前下标的右侧继续查找
     * 复杂度分析
     * 时间复杂度：O(log n)，其中 n 是列表letters 的长度。二分查找的时间复杂度是  O(log n)。
     * 空间复杂度：O(1)O(1)。

     * @param letters
     * @param target
     * @return
     */
    public char nextGreatestLetter02(char[] letters, char target) {
        int length = letters.length;
        if(target >= letters[length - 1]) return letters[0];
        int low = 0, high = length - 1;
        while (low < high) {
            int mid = (high - low) / 2 + low;
            if(letters[mid] > target) high = mid;
            else low = mid + 1;
        }
        return letters[low];
    }

}
