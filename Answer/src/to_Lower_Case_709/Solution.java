package to_Lower_Case_709;

public class Solution {
    public String toLowerCase(String s) {
        //return s.toLowerCase();
        //时间复杂度：O(n)，其中 n 是字符串 s 的长度。
        //空间复杂度：O(1)，不考虑返回值的空间占用。
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < s.length(); i++) {
            Character ch = s.charAt(i);
            sb.append(Character.toUpperCase(ch));
        }
        return sb.toString();
    }

    /**
     * 方法二的主要目的是，带领读者一步一步设计一个高效的大写字母转小写字母的 API。
     * 我们可以想到的最简单的方法是使用一个哈希映射，哈希映射中包含 26 个键值对 (A,a),(B,b),⋯,(Z,z)。对于每个待转换的字符 ch，
     * 如果它出现在是哈希映射中（即 ch 是哈希映射中的一个键），那么 ch 是大写字母，我们获取 ch 在哈希映射中的值即可得到对应的小写字母；
     * 如果它没有出现在哈希映射中，那么 ch 是其它字符，我们无需进行转换。
     * 然而这种方法需要一定量的辅助空间，不够简洁。一种更好的方法是观察小写字母和大写字母的 ASCII 码表示：
     * 大写字母 A-Z 的ASCII 码范围为 [65,90]：
     * 小写字母 a-z 的 ASCII 码范围为 [97,122]。
     * 因此，如果我们发现 ch 的 ASCII 码在 [65,96] 的范围内，那么我们将它的 ASCII 码增加 32，即可得到对应的小写字母。
     * 近而我们可以发现，由于[65,96] 对应的二进制表示为 [(01000001)_2, (01011010)_2]
     * 32对应的二进制表示为 (00100000)_2
     * 而对于 [(01000001)_2, (01011010)_2]内的所有数，表示 32 的那个二进制位都是 0，
     * 因此可以对 ch 的 ASCII 码与32 做按位或运算，替代与32 的加法运算。
     *
     * 时间复杂度：O(n)，其中 n 是字符串 s 的长度。
     * 空间复杂度：O(1)，不考虑返回值的空间占用。
     * @param s
     * @return
     */
    public String toLowerCase01(String s) {
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            if(ch >= 65 && ch <= 90) ch |= 32;
            sb.append(ch);
        }
        return sb.toString();
    }
}
