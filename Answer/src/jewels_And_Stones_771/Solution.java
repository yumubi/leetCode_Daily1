package jewels_And_Stones_771;

import java.util.HashSet;
import java.util.Set;

public class Solution {
    public int numJewelsInStones(String jewels, String stones) {
        int num = 0;
        Set<Character> set = new HashSet<>();
        for(int i = 0; i < jewels.length(); i++)
            set.add(jewels.charAt(i));
        for(int i = 0; i < stones.length(); i++) {
            if(set.contains(stones.charAt(i))) num++;
        }
        return num;
    }

    /**
     * 暴力
     * 暴力法的思路很直观，遍历字符串stones，对于 stones 中的每个字符，遍历一次字符串 jewels，如果其和 jewels 中的某一个字符相同，则是宝石。
     * 时间复杂度：O(mn)，其中 m 是字符串 jewels 的长度，n 是字符串 stones 的长度。遍历字符串 stones 的时间复杂度是 O(n)，对于 stones 中的每个字符，
     * 需要遍历字符串 jewels 判断是否是宝石，时间复杂度是 O(m)，因此总时间复杂度是 O(mn)。
     * 空间复杂度：O(1)。只需要维护常量的额外空间。
     * @param jewels
     * @param stones
     * @return
     */
    public int numJewelsInStones01(String jewels, String stones) {
        int jewelsCount = 0;
        int jewelsLength = jewels.length(), stonesLength = stones.length();
        for(int i = 0; i < stonesLength; i++) {
            char stone = stones.charAt(i);
            for(int j = 0; j < jewelsLength; j++) {
                char jewel = jewels.charAt(j);
                if(stone == jewel) {
                    jewelsCount++;
                    break;
                }
            }
        }
        return jewelsCount;
    }


    /**
     * 哈希集合
     * 方法一中，对于字符串 stones 中的每个字符，都需要遍历一次字符串 jewels，导致时间复杂度较高。如果使用哈希集合存储字符串jewels 中的宝石，则可以降低判断的时间复杂度。
     * 遍历字符串 jewels，使用哈希集合存储其中的字符，然后遍历字符串 stones，对于其中的每个字符，如果其在哈希集合中，则是宝石。
     * 时间复杂度：O(m+n)，其中 m 是字符串 jewels 的长度，n 是字符串 stones 的长度。遍历字符串 jewels 将其中的字符存储到哈希集合中，时间复杂度是 O(m)，
     * 然后遍历字符串 stones，对于 stones 中的每个字符在 O(1) 的时间内判断当前字符是否是宝石，时间复杂度是 O(n)，因此总时间复杂度是 O(m+n)。
     * 空间复杂度：O(m)，其中 m 是字符串 jewels 的长度。使用哈希集合存储字符串 \jewels 中的字符。
     * @param jewels
     * @param stones
     * @return
     */
    public int numJewelsInStones02(String jewels, String stones) {
        int jewelsCount = 0;
        Set<Character> jewelsSet = new HashSet<Character>();
        int jewelsLength = jewels.length(), stonesLength = stones.length();
        for(int i = 0; i < jewelsLength; i++) {
            char jewel = jewels.charAt(i);
            jewelsSet.add(jewel);
        }
        for(int i = 0; i < stonesLength; i++) {
            char stone = stones.charAt(i);
            if(jewelsSet.contains(stone)) jewelsCount++;
        }
        return jewelsCount;
    }

}
