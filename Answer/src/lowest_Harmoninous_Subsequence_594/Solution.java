package lowest_Harmoninous_Subsequence_594;

import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;

public class Solution {

    /**
     * 我是sb
     * @param nums
     * @return
     */
    public int findHS(int[] nums) {
        int maxHS = 0;
        for(int i = 0; i < nums.length; i++) {
            int add = search(nums, nums[i], i, 0, 1);
            int sub = search(nums, nums[i], i, 0, -1);
            if(add > maxHS) maxHS = add;
            if(sub > maxHS) maxHS = sub;
        }
        return maxHS;
    }

    public int search(int[] nums, int origin, int position,int cnt, int delta) {
        int secondNum = origin + delta;
        boolean changed = false;
        for(int i = position; i < nums.length; i++) {
            if(nums[i] == origin) cnt++;
            else if(nums[i] == secondNum) {
                changed = true;
                cnt++;
            }
            else continue;
        }
        if(changed == false || cnt == 1) cnt = 0;
        return cnt;
    }

    /**
     * 枚举
     * 我们可以枚举数组中的每一个元素，对于当前枚举的元素 x，它可以和x+1 组成和谐子序列。我们只需要在数组中找出等于 x 或 x+1 的元素个数，
     * 就可以得到以 x 为最小值的和谐子序列的长度。
     * 实际处理时，我们可以将数组按照从小到大进行排序，我们只需要依次找到相邻两个连续相同元素的子序列，检查该这两个子序列的元素的之差是否为 1。
     * 利用类似与滑动窗口的做法，begin 指向第一个连续相同元素的子序列的第一个元素，end 指向相邻的第二个连续相同元素的子序列的末尾元素，
     * 如果满足二者的元素之差为 1，则当前的和谐子序列的长度即为两个子序列的长度之和，等于end−begin+1。
     *
     * 时间复杂度：O(NlogN)，其中 N 为数组的长度。我们首先需要对数组进行排序，花费的时间复杂度为 O(NlogN)，
     * 我们需要利用双指针遍历数组花费的时间为 O(2N)，总的时间复杂度 T(N)=O(NlogN)+O(2N)=O(NlogN)。
     * 空间复杂度：O(1)，需要常数个空间保存中间变量。
     * @param nums
     * @return
     */
    public int findHS01(int[] nums) {
        Arrays.sort(nums);
        int begin = 0;
        int res = 0;
        for(int end = 0; end < nums.length; end++) {
            while(nums[end] - nums[begin] > 1) {
                begin++;
            }
            if(nums[end] - nums[begin] == 1) {
                res = Math.max(res, end - begin + 1);
            }
        }
        return res;



//        Arrays.sort(nums);
//        int n = nums.length, ans = 0;
//        for(int i = 0, j = 0; j < n; j++) {
//            while(i < j && nums[j] - nums[i] > 1) i++;
//            if(nums[j] - nums[i] == 1) ans = Math.max(ans, j - i + 1);
//        }
//        return ans;
    }

    /**
     * 哈希表
     * 在方法一中，我们枚举了 x 后，遍历数组找出所有的 x 和 x+1的出现的次数。我们也可以用一个哈希映射来存储每个数出现的次数，
     * 这样就能在 O(1) 的时间内得到 x 和 x+1 出现的次数。
     * 我们首先遍历一遍数组，得到哈希映射。随后遍历哈希映射，设当前遍历到的键值对为(x,value)，那么我们就查询 x+1 在哈希映射中对应的统计次数，
     * 就得到了 x 和 x+1 出现的次数，和谐子序列的长度等于 x 和 x+1 出现的次数之和。
     * 时间复杂度：O(N)，其中 N 为数组的长度。
     * 空间复杂度：O(N)，其中 N 为数组的长度。数组中最多有 N 个不同元素，因此哈希表最多存储 N 个数据。
     * @param nums
     * @return
     */
    public int findHS02(int[] nums) {
        HashMap<Integer, Integer> cnt = new HashMap<>();
        int res = 0;
        for(int num : nums) {
            cnt.put(num, cnt.getOrDefault(num, 0) + 1);
        }
        for(int key : cnt.keySet()) {
            if(cnt.containsKey(key + 1)) {
                res = Math.max(res, cnt.get(key) + cnt.get(key + 1));
            }
        }
        return res;
    }




    @Test
    public void test() {
        int[] nums = {1,1,1,1};
        System.out.println(findHS(nums));
    }

}
//[1,3,2,2,5,2,3,7]