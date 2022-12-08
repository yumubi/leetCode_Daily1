package array_Partition_561;

import java.util.Arrays;

public class Solution {
    /*        排序
 *假设排完序的结果为a1<=b1<=a2<=b2<=...<=an<=bn
 * 那么a1应该跟谁一组呢？
 * a1作为全局最小值，无论跟谁一组a1都会被累加进答案，相反，a1的搭档会被永久排除。
 * 既然如此，莫不如排除一个较小的数，即给a1找一个“最小的搭档”b1。
 * 当a1、b1被处理之后，a2同理分析。
 * 所以，最终选择a1,a2,...,an会得到最好的结果。
     */
    public int arrayPairNum(int[] nums){
        Arrays.sort(nums);
        int sum = 0;
        for(int i = 0; i < nums.length; i += 2) sum += nums[i];
        return sum;
    }

}
