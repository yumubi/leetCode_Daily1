package set_Mismatch_645;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Solution {
    public int[] findErrorNums(int[] nums) {
        Set<Integer> set = new HashSet<>();
        int same = 0, miss = 1;
        for(int i = 0; i < nums.length; i++) {
            if(!set.add(nums[i])) same = nums[i];
        }
        for(int i = 1; i <= nums.length; i++) {
            if(!set.contains(i)) miss = i;
        }
        return new int[]{same, miss};
    }

    /**
     * 哈希表
     * 重复的数字在数组中出现 2 次，丢失的数字在数组中出现 0 次，其余的每个数字在数组中出现 1 次。因此可以使用哈希表记录每个元素在数组中出现的次数，
     * 然后遍历从 1 到 n 的每个数字，分别找到出现 2 次和出现 0 次的数字，即为重复的数字和丢失的数字。
     * 间复杂度：O(n)，其中 n 是数组 nums 的长度。需要遍历数组并填入哈希表，然后遍历从 1 到 n 的每个数寻找错误的集合。
     * 空间复杂度：O(n)，其中 n 是数组 nums 的长度。需要创建大小为 O(n) 的哈希表。
     * @param nums
     * @return
     */
    public int[] findErrorNums01(int[] nums) {
        int[] errorNums = new int[2];
        int n = nums.length;
        Map<Integer,Integer> map = new HashMap<Integer, Integer>();
        for(int num : nums) map.put(num, map.getOrDefault(num, 0) + 1);
        for(int i = 1; i <= n; i++) {
            int count = map.getOrDefault(i, 0);
            if(count == 2) errorNums[0] = i;
            else if(count == 0) errorNums[1] = i;
        }
        return errorNums;
    }


}
