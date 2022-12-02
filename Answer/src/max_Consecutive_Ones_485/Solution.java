package max_Consecutive_Ones_485;

import org.junit.Test;

public class Solution {
    public int findMaxConsecuiveOnes(int[] nums) {
        int cnt = 0;
        int max = 0;
        for(int i = 0; i < nums.length; i++) {
            if(nums[i] == 1) {
                cnt++;
                if(cnt > max) max = cnt;
            }
            else cnt = 0;
        }
        return max;
    }

    /**
     * 为了得到数组中最大连续 1 的个数，需要遍历数组，并记录最大的连续 1 的个数和当前的连续 1 的个数。
     * 如果当前元素是 1，则将当前的连续 1 的个数加 1，否则，使用之前的连续 1 的个数更新最大的连续 1 的个数，并将当前的连续 1 的个数清零。
     * 遍历数组结束之后，需要再次使用当前的连续 1 的个数更新最大的连续 1 的个数，因为数组的最后一个元素可能是 1，且最长连续 1 的子数组可能出现在数组的末尾，
     * 如果遍历数组结束之后不更新最大的连续 1的个数，则会导致结果错误。
     * @param nums
     * @return
     */
    public int findMaxConsecutiveOnes1(int[] nums) {
        int maxCount = 0, count = 0;
        int n = nums.length;
        for(int i = 0; i < n; i++) {
            if(nums[i] == 1) count++;
            else {
                maxCount = Math.max(maxCount, count);
                count = 0;
            }
        }
        maxCount = Math.max(maxCount, count);
        return maxCount;
    }

    /**
     * 滑动窗口思路：
     * 当输出或比较的结果在原数据结构中是连续排列的时候，可以使用滑动窗口算法求解。
     * 将两个指针比作一个窗口，通过移动指针的位置改变窗口的大小，观察窗口中的元素是否符合题意。
     *
     * 初始窗口中只有数组开头一个元素。
     * 当窗口中所有元素为 1 时，右指针向右移，扩大窗口。
     * 当窗口中存在 0 时，计算连续序列长度，左指针指向右指针。
     * @param nums
     * @return
     */
    public int findMaxConsecutiveOnes2(int[] nums) {
        int length = nums.length;
        int left = 0;
        int right = 0;
        int maxSize = 0;
        while(right <  length) {
            //当窗口中所有元素为1时，右指针向右移动，扩大窗口
            if(nums[right++] == 0) { //注意这里时先取right,在++，遇到nums[right] == 0时，right马上指向了0后面的第一个元素
                //当窗口中存在0时，计算连续序列长度，左指针指向右指针
                maxSize = Math.max(maxSize, right - left - 1);
                left = right;
            }
        }

        //因为最后一次连续序列在循环中无法比较，所以在循环外进行比较
        return Math.max(maxSize, right - left);
    }

    /**
     * dp
     *  1. 定义dp数组以及下标的含义
     *         int[] dp = new int[nums.length+1];
     *         dp[i]:表示以i结尾的最大连续1的个数为dp[i]
     *
     *         2. 推导出递推公式
     *         遍历一个元素，你就判断是不是等于1，如果等于1。就累加它的动态值。即 dp[i-1]+1;
     *         如果不是1，那说明是0. 就 终止了本次的最大连续1个数。即dp[i]=0;
     *
     *         即：if(nums[i]==1){
     *                 dp[i]=dp[i-1]+1;
     *             }
     *
     *         3. 初始化dp数组
     *         由递推公式即 dp[0]=0;
     *
     *         4. 确定遍历
     *             正常遍历顺序
     * @param nums
     * @return
     */
    public int findMaxConsecutiveOnes3(int[] nums) {
        int[] dp = new int[nums.length + 1];
        int res = 0;
        for(int i = 1; i < dp.length; i++) {
            if(nums[i - 1] == 1) {
                dp[i] = dp[i - 1] + 1;
                res = dp[i] > res ? dp[i] : res;
            }
        }
        return res;
    }


    /**
     * 双指针
     * 使用 i 和 j 分别代表连续 1 的左右边界。
     * 起始状态 i == j，当 i 到达第一个 1 的位置时，让 j 不断右移直到右边界。
     * 更新 ans
     * @param nums
     * @return
     */
    public int findMaxConsecutiveOnes4(int[] nums) {
        int n = nums.length;
        int ans = 0;
        for(int i = 0, j = 0; i < n; j = i) {
            if(nums[i] == 1) {
                while(j + 1 < n && nums[j + 1] == 1) j++;
                ans = Math.max(ans, j - i + 1);
                i = j + 1;
            } else i++;
        }
        return ans;
    }






    @Test
    public void test() {
        int[] nums = {1,1,0,1,1,1};
        System.out.println(findMaxConsecutiveOnes2(nums));
    }
}
