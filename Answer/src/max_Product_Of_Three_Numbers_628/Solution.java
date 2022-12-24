package max_Product_Of_Three_Numbers_628;

import org.junit.Test;

import java.util.Arrays;

public class Solution {
    public int maximunProduct(int[] nums) {
        Arrays.sort(nums);
        int len = nums.length;
        int negativeCnt = 0;
        for(int i = 0; i < len; i++) {
            if(nums[i] < 0) negativeCnt++;
        }
        if(negativeCnt >= 2 && negativeCnt != len
                && (nums[0] * nums[1] > nums[len - 3] * nums[len - 2]))
            return nums[0] * nums[1] * nums[len - 1];
        return nums[len - 1] * nums[len - 2] * nums[len - 3];
    }

    /**
     * 排序
     * 如果数组中全是非负数，则排序后最大的三个数相乘即为最大乘积；如果全是非正数，则最大的三个数相乘同样也为最大乘积。
     * 如果数组中有正数有负数，则最大乘积既可能是三个最大正数的乘积，也可能是两个最小负数（即绝对值最大）与最大正数的乘积。
     * 综上，我们在给数组排序后，分别求出三个最大正数的乘积，以及两个最小负数与最大正数的乘积，二者之间的最大值即为所求答案。
     * @param nums
     * @return
     */
    public int maximumProduct01(int[] nums) {
        Arrays.sort(nums);
        int n = nums.length;
        return Math.max(nums[0] * nums[1] * nums[n - 1], nums[n - 3] * nums[n -2] * nums[n -1]);
    }

    /**
     * 线性扫描
     *
     * @param nums
     * @return
     */
    public int maximumProduct02(int[] nums) {
        int min1 = Integer.MAX_VALUE, min2 = Integer.MAX_VALUE;
        int max1 = Integer.MIN_VALUE, max2 = Integer.MIN_VALUE, max3 = Integer.MIN_VALUE;
        for(int x : nums) {
            if(x < min1) {
                min2 = min1;
                min1 = x;
            } else if(x < min2) min2 = x;

            if(x > max1) {
                max3 = max2;
                max2 = max1;
                max1 = x;
            } else if(x > max2) {
                max3 = max2;
                max2 = x;
            } else if(x > max3) max3 = x;
        }
        return Math.max(min1 * min2 * max1, max1 * max2 * max3);
    }


    @Test
    public void test() {
        int[] nums = new int[]{-710,-107,-851,657,-14,-859,278,-182,-749,718,-640,127,-930,-462,694,969,143,309,904,-651,160,451,-159,-316,844,-60,611,-169,-73,721,-902,338,-20,-890,-819,-644,107,404,150,-219,459,-324,-385,-118,-307,993,202,-147,62,-94,-976,-329,689,870,532,-686,371,-850,-186,87,878,989,-822,-350,-948,-412,161,-88,-509,836,-207,-60,771,516,-287,-366,-512,509,904,-459,683,-563
                ,-766,-837,-333,93,893,303,908,532,-206,990,280,826,-13,115,-732,525,-939,-787};
        Arrays.sort(nums);
        System.out.println(Arrays.toString(nums));
        System.out.println(-976 * -948 * 993);
    }
}
