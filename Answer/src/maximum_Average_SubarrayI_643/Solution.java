package maximum_Average_SubarrayI_643;

public class Solution {
    /**
     * 超时
     *
     * @param nums
     * @param k
     * @return
     */
    public double findMaxAverage(int[] nums, int k) {
        int left = 0, right = k - 1;
        double maxAvg = Integer.MIN_VALUE;
        double avg = 0;
        while (right < nums.length) {
            for (int i = left; i <= right; i++) {
                avg += nums[i];
            }
            avg /= k;
            if (avg > maxAvg) maxAvg = avg;
            left++;
            right++;
            avg = 0;
        }
        return maxAvg;
    }


    /**
     * 滑动窗口
     *
     * sum_i=sum_i−1−nums[i−k]+nums[i]
     * 上述过程可以看成维护一个长度为 k 的滑动窗口。当滑动窗口从下标范围 [i−k,i−1] 移动到下标范围 [i−k+1,i] 时，nums[i−k] 从窗口中移出，nums[i] 进入到窗口内。
     * 利用上述关系，可以在O(1) 的时间内通过 sum_i−1得到 sum_i
     * 时间复杂度：O(n)，其中 n 是数组 nums 的长度。遍历数组一次。
     * 空间复杂度：O(1)
     * @param nums
     * @param k
     * @return
     */
    public double findMaxAverage01(int[] nums, int k) {
        int sum = 0;
        int n = nums.length;
        for (int i = 0; i < k; i++) {
            sum += nums[i];
        }
        int maxSum = sum;
        for (int i = k; i < n; i++) {
            sum = sum - nums[i - k] + nums[i];
            maxSum = Math.max(maxSum, sum);
        }
        return 1.0 * maxSum / k;
    }


    /**
     * 滑动窗口模板
     * 初始化将滑动窗口压满，取得第一个滑动窗口的目标值
     * 继续滑动窗口，每往前滑动一次，需要删除一个和添加一个元素
     *
     * 时间复杂度：每个元素最多滑入和滑出窗口一次。复杂度为 O(n)
     * 空间复杂度：O(1)
     * @param nums
     * @param k
     * @return
     */
    public double findMaxAverage02(int[] nums, int k) {
        double ans = 0, sum = 0;
        for(int i = 0; i < k; i++) sum += nums[i];
        ans = sum / k;
        for(int i = k ; i < nums.length; i++) {
            sum = sum + nums[i] - nums[i - k];//int add = num[i], del = nums[i - k];
            ans = Math.max(ans, sum / k);
        }
        return ans;
    }

    /**
     * 前缀和
     * 求区间的和可以用 preSum。preSum 方法还能快速计算指定区间段 i ~ j 的元素之和。
     * 它的计算方法是从左向右遍历数组，当遍历到数组的 i 位置时，preSum 表示 i 位置左边的元素之和。
     * 假设数组长度为 N，我们定义一个长度为 N+1 的 preSum 数组，preSum[i] 表示该元素左边所有元素之和（不包含当前元素）。
     * 然后遍历一次数组，累加区间 [0, i) 范围内的元素，可以得到 preSum 数组。
     * @param k
     * @return
     */
    public double findMaxAverage03(int[] nums, int k) {
        int n = nums.length;
        int[] preSum = new int[n + 1];
        for(int i = 1; i <= n; i++) {
            preSum[i] = preSum[i - 1] + nums[i - 1];
        }
        double ret = Integer.MIN_VALUE;
        for(int i = 1; i <= n - k + 1; i++) {
            ret = Math.max(ret, (preSum[i + k - 1] - preSum[i - 1]) * 1.0 / k);
        }
        return ret;
    }




}
