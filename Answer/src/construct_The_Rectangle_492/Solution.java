package construct_The_Rectangle_492;

import org.junit.Test;

import java.util.Arrays;

public class Solution {
    public int[] constructRectangle(int area) {
            int lo = (int)Math.sqrt(area);
            int hi = lo;
            while( lo > 0 && hi <= area && lo <= hi){
                int mul = lo * hi;
            if(mul == area) return new int[]{hi, lo};
            else if(mul < area) hi++;
            else lo--;
        }
        return new int[] {hi, lo};
    }


    /**
     * L⋅ W=area，这也意味着 0area 可以被 W 整除；
     * L≥W， 可得 W⋅W≤L⋅W=area，从而有 W≤⌊sqrt(area)⌋；
     * 这意味着 W应取满足 area 可以被 W 整除且 W≤⌊sqrt(area)⌋ 的最大值。
     * 我们可以初始化 W=sqrt(area)⌋，不断循环判断 area 能否被 W 整除，如果可以则跳出循环，否则将 W 减一后继续循环。
     * @param area
     * @return
     */
    public int[] constructRectangle1(int area) {
        int w = (int)Math.sqrt(area);
        while(area % w != 0) --w;
        return new int[]{area / w,  w};
    }

    /**
     * 根据题意，从 sqrt{area}开始往后模拟，遇到第一个能够被整除的数值，则返回该答案。
     * @param area
     * @return
     */
    public int[] constructRectangle2(int area) {
        for(int i = (int)(Math.sqrt(area)); ; i--)
            if(area % i == 0) return new int[]{area / i, i};
    }



    @Test
    public void test() {
        System.out.println(Arrays.toString(constructRectangle(4)));

    }
}
