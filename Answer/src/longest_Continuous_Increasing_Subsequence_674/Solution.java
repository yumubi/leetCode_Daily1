package longest_Continuous_Increasing_Subsequence_674;

import java.util.Arrays;

public class Solution {
    public int findLengthOfLCIS(int[] nums) {
        int n = nums.length;
        if(n == 1) return 1;
        int maxLen = 1;
        int left = 0, right = 1;
        int prev = nums[left];
        while(right < n) {
            if(prev >= nums[right]) {
                if( (right - left) > maxLen) maxLen = right - left;
                left = right;
            }
            prev = nums[right];
            right++;
        }
        maxLen = (right - left) > maxLen ? (right - left) : maxLen;
        return maxLen;
    }


    /**
     * 贪心
     * 于下标范围[l,r] 的连续子序列，如果对任意l≤i<r 都满足 nums[i]<nums[i+1]，则该连续子序列是递增序列。
     * 假设数组 nums 的长度是 n，对于 <l≤r<n−1，如果下标范围 [l,r] 的连续子序列是递增序列，则考虑 nums[l−1] 和 nums[r+1]。
     * 如果 nums[l−1]<nums[l]，则将 nums[l−1] 加到 nums[l] 的前面，可以得到更长的连续递增序列.
     * 如果 nums[r+1]>nums[r]，则将 nums[r+1] 加到 nums[r] 的后面，可以得到更长的连续递增序列。
     * 基于上述分析可知，为了得到最长连续递增序列，可以使用贪心的策略得到尽可能长的连续递增序列。做法是使用记录当前连续递增序列的开始下标和结束下标，
     * 遍历数组的过程中每次比较相邻元素，根据相邻元素的大小关系决定是否需要更新连续递增序列的开始下标。
     * 具体而言，令 start 表示连续递增序列的开始下标，初始时start=0，然后遍历数组 nums，进行如下操作
     *
     * 如果下标 i>0 且 nums[i]≤nums[i−1]，则说明当前元素小于或等于上一个元素，因此 nums[i−1] 和 nums[i] 不可能属于同一个连续递增序列，
     * 必须从下标 i处开始一个新的连续递增序列，因此令 start=i。如果下标 i=0 或 nums[i]>nums[i−1]，则不更新 start 的值。
     * 此时下标范围 [start,i] 的连续子序列是递增序列，其长度为 i−start+1，使用当前连续递增序列的长度更新最长连续递增序列的长度。
     * 时间复杂度：O(n)，其中 n 是数组 nums 的长度。需要遍历数组一次。
     * 空间复杂度：O(1)。额外使用的空间为常数。
     *
     * @param nums
     * @return
     */
    public int findLengthOFLCIS01(int[] nums) {
        int ans = 0;
        int n = nums.length;
        int start = 0;
        for(int i = 0; i < n; i++) {
            if(i > 0 && nums[i] <= nums[i - 1]) {
                start = i;
            }
            ans = Math.max(ans, i - start + 1);
        }
        return ans;
    }


    /**
     * dp
     * 确定dp数组以及下标的含义：
     * dp[i]：以下标 i 为结尾的数组的连续递增的子序列长度为 ddp[i]。
     * 注意这里的定义，一定是以下标 i 为结尾，并不是说一定以下标 0 为起始位置。
     * 确定递推公式
     * 当nums[i]>nums[i−1] 时：nums[i] 可以接在 nums[i−1] 之后（此题要求严格连续递增），此情况下最长上升子序列长度为 dp[i−1]+1 ；
     * 当nums[i]<=nums[i−1] 时：nums[i] 无法接在 nums[i−1] 之后，此情况上升子序列不成立，跳过,相当于dp[i] = 1。
     * 上述所有 1. 情况 下计算出的 dp[i−1]+1 的最大值，为直到 i 的最长上升子序列长度（即 dp[i] ）。
     * 转移方程： dp[i] = dp[i-1] + 1。
     * 初始状态：
     *dp[i] 所有元素置 1，含义是每个元素都至少可以单独成为子序列，此时长度都为 1。
     * 确定遍历顺序
     * 从递推公式上可以看出， dp[i] 依赖 dp[i−1]，所以一定是从前向后遍历。
     * @param nums
     * @return
     */
    public int findLengthOfLCIS02(int[] nums) {
        if(nums.length <= 1) return nums.length;
        int result = 0;
        int[] dp = new int[nums.length + 1];
        Arrays.fill(dp, 1);
        for(int i = 1; i < nums.length; i++) {
            if(nums[i - 1] < nums[i]) {
                //不连续递增子序列的跟前0-i个状态有关，连续递增子序列只跟前一个状态有关
                dp[i] = dp[i - 1] + 1;
            }
            if(result < dp[i]) result = dp[i];
        }
        return result;
    }

    public int findLengthOfLCIS03(int[] nums) {
        int dp0 = 1;
        int dp1 = 1;
        int max = 1;
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] > nums[i - 1]) {
                dp1 = dp0 + 1;
                dp0 = dp1;
            } else {
                dp0 = 1;
                dp1 = 1;
            }
            max = Math.max(max, dp1);
        }
        return max;
    }
}
