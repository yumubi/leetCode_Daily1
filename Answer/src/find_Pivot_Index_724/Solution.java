package find_Pivot_Index_724;

import org.junit.Test;

import java.util.Arrays;

public class Solution {
    public int pivotIndex(int[] nums) {
        if(nums.length == 1) {
            if(nums[0] == 0) return 0;
            else return -1;
        }
       int[] prefix = new int[nums.length + 1];
       prefix[0] = 0;
       for(int i = 1; i < prefix.length; i++) prefix[i]= prefix[i - 1] + nums[i - 1];
      int sum = prefix[nums.length];

       if(sum - nums[0] == 0) return 0;
       for(int i = 1; i < prefix.length - 1; i++) {
               int tmp = sum - nums[i];
           if((tmp & 1) == 1) continue;
           tmp = tmp / 2;

           if(prefix[i] == tmp) return i;
       }
       return -1;
    }

    /**
     * 前缀和
     * 记数组的全部元素之和为 total，当遍历到第 i 个元素时，设其左侧元素之和为sum，则其右侧元素之和为 total−nums_i−sum。
     * 左右侧元素相等即为sum = total−nums_i−sum，即 2×sum+nums_i=total。
     * 当中心索引左侧或右侧没有元素时，即为零个项相加，这在数学上称作「空和」。在程序设计中我们约定「空和是零」。
     * 时间复杂度：O(n)，其中 n 为数组的长度。
     * 空间复杂度：O(1)。
     * @param nums
     * @return
     */
    public int pivotIndex01(int[] nums) {
        int total = Arrays.stream(nums).sum();
        int sum = 0;
        for(int i = 0; i < nums.length; i++) {
            if(2 * sum + nums[i] == total) return i;
            sum += nums[i];
        }
        return -1;
    }

    /**
     * 这是一道前缀和的裸题。
     * 只需要用两个数组，前后处理两遍前缀和，再对两个前缀和数组的相同下标进行判别即可。
     * 为了简化数组越界的判断，我们通常会给前缀和数组多预留一位作为哨兵。
     * 这里由于要求前后前缀和。所以我们直接多开两位。
     * 时间复杂度：对数组进行线性扫描。复杂度为 O(n)
     * 空间复杂度：使用了前缀和数组。复杂度为O(n)
     * @param nums
     * @return
     */
    public int pivotIndex02(int[] nums) {
        int n = nums.length;
        int[] s1 = new int[n + 2], s2 = new int[n + 2];
        for(int i = 1; i <= n; i++) s1[i] = s1[i - 1] + nums[i - 1];
        for(int i = n; i >= 1; i--) s2[i] = s2[i + 1] + nums[i - 1];
        for(int i = n; i <= n; i++) if(s1[i] == s2[i]) return i - 1;
        return -1;
    }


    /**
     * 空间优化（常数级别的优化）
     * 当然，我们也可以只处理一遍前缀和。
     * 然后在判定一个下标是否为”中心索引“的时候，利用前缀和计算左侧值和右侧值。
     * 但这只是常数级别的优化，并不影响其时空复杂度。
     * 时间复杂度：对数组进行线性扫描。复杂度为 O(n)
     * 空间复杂度：使用了前缀和数组。复杂度为O(n)
     * @param nums
     * @return
     */
    public int pivotIndex03(int[] nums) {
        int n = nums.length;
        int[] sum = new int[n + 1];
        for(int i = 1; i <= n; i++) sum[i] = sum[i - 1] + nums[i - 1];
        for(int i = 1; i <= n; i++) {
            int left = sum[i - 1], right = sum[n] - sum[i];
            if(left == right) return i - 1;
        }
        return -1;
    }

    /**
     * 空间优化（优化至常数级别）
     * 可以不使用额外空间。
     * 先求一遍总和 total，再使用 sum 记录当前遍历位置的左侧总和。
     * 对于中心索引必然有：sum = total - sum - nums[i] （左边值 = 右边值）
     * 时间复杂度：对数组进行线性扫描。复杂度为 O(n)
     * 空间复杂度：O(1)
     * @param nums
     * @return
     */
    public int pivotIndex04(int[] nums) {
        int n = nums.length;
        int total = 0, sum = 0;
        for(int i = 0; i < n; i++) total += nums[i];
        for(int i = 0; i < n; i++) {
            if(sum == total - sum - nums[i]) return i;
            sum += nums[i];
        }
        return -1;
    }




    @Test
    public void test() {
        int[] nums = new int[]{2, 1, -1};
        System.out.println(pivotIndex(nums));
    }
}
