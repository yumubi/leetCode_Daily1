package longest_Harmoninous_Subsequence_594;

import org.junit.Test;

public class Solution {
    public int findHS(int[] nums) {
        if(nums.length == 1) return 0;
        int left = 0;
        int cnt = 0;
        boolean position = false;
        boolean negative = false;
        for(int i = 1; i < nums.length; i++) {
            if(nums[i] == left) cnt++;
            else if(nums[i] - left == 1) {
                position = true;
                if(!negative) cnt++;
            } else if(nums[i] - left == -1) {
                negative = true;
                if(!position) cnt++;
            } else {
                position = false;
                negative = false;
                cnt = 0;
                left++;
            }
        }
        return cnt;
    }


    @Test
    public void test() {
        
    }

}
[1,3,2,2,5,2,3,7]