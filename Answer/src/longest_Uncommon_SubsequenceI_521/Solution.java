package longest_Uncommon_SubsequenceI_521;


public class Solution {
    /**
     * 字符串的子序列的长度不会超过该字符串的长度。若子序列的长度等于字符串的长度，那么子序列就是该字符串。
     * 若两字符串不相同，那么我们可以选择较长的字符串作为最长特殊序列，显然它不会是较短的字符串的子序列。
     * 特别地，当两字符串长度相同时（但不是同一字符串），我们仍然可以选择其中的一个字符串作为最长特殊序列，它不会是另一个字符串的子序列。
     * 若两字符串相同，那么任一字符串的子序列均会出现在两个字符串中，此时应返回 -1。
     * 时间复杂度：O(n)，其中 n 是字符串 a 的长度。当两字符串长度不同时，时间复杂度为 O(1)；
     * 当字符串长度相同时，时间复杂度为 O(n)。因此时间复杂度为 O(n)。
     * 空间复杂度：O(1)
     * @param a
     * @param b
     * @return
     */
    public int findLUSLength(String a, String b) {
            return !a.equals(b) ? Math.max(a.length(), b.length()) : -1;
    }
}
