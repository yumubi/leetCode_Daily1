package degree_Of_An_Array_697;

import java.util.HashMap;
import java.util.Map;

public class Solution {
    public int findShortestSubArray(int[] nums) {
        if(nums.length == 1) return 1;
        int[] bits = new int[50000];
        for(int i = 0; i < nums.length; i++)
            bits[nums[i]]++;
        int max = 0;
        int ret = nums.length;
        for(int i = 0; i < bits.length; i++) {
            if(bits[i] > max) max = bits[i];
        }
        for(int i = 0; i < bits.length; i++) {
            if(bits[i] == max) {
                int len = getLen(i, nums);
                if(len < ret) ret = len;
            }
        }
        return ret;
    }

    public int getLen(int marjority, int[] nums) {
        int start = -1, end = -1;
        for(int i = 0; i < nums.length; i++) {
            if(nums[i] == marjority && start == -1) start = i;
            if(nums[i] == marjority) end = i;
        }
        return end - start + 1;
    }


    /**
     * 哈希表
     * 记原数组中出现次数最多的数为 x，那么和原数组的度相同的最短连续子数组，必然包含了原数组中的全部 x，且两端恰为 x 第一次出现和最后一次出现的位置。
     * 因为符合条件的 x 可能有多个，即多个不同的数在原数组中出现次数相同。所以为了找到这个子数组，
     * 我们需要统计每一个数出现的次数，同时还需要统计每一个数第一次出现和最后一次出现的位置。
     * 在实际代码中，我们使用哈希表实现该功能，每一个数映射到一个长度为 3 的数组，数组中的三个元素分别代表这个数出现的次数、
     * 这个数在原数组中第一次出现的位置和这个数在原数组中最后一次出现的位置。当我们记录完所有信息后，我们需要遍历该哈希表，找到元素出现次数最多，且前后位置差最小的数。
     * 时间复杂度：O(n)，其中 n 是原数组的长度，我们需要遍历原数组和哈希表各一次，它们的大小均为 O(n)。
     * 空间复杂度：O(n)，其中 n 是原数组的长度，最坏情况下，哈希表和原数组等
     * @param nums
     * @return
     */
    public int findShortestSubArray01(int[] nums) {
        Map<Integer, int[]> map = new HashMap<Integer, int[]>();
        int n = nums.length;
        for(int i = 0; i < n; i++) {
            if(map.containsKey(nums[i])) {
                map.get(nums[i])[0]++;
                map.get(nums[i])[2] = i;
            }
            else map.put(nums[i], new int[]{1, i, i});
        }
        int maxNum = 0, minLen = 0;
        for(Map.Entry<Integer, int[]> entry : map.entrySet()) {
            int[] arr = entry.getValue();
            if(maxNum < arr[0]) {
                maxNum = arr[0];
                minLen = arr[2] - arr[1] + 1;
            }
            else if(maxNum == arr[0]) {
                if(minLen > arr[2] - arr[1] + 1) {
                    minLen = arr[2] - arr[1] + 1;
                }
            }
        }
        return minLen;
    }


}
