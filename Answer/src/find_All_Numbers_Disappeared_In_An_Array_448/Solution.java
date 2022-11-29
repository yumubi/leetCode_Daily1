package find_All_Numbers_Disappeared_In_An_Array_448;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Solution {
    public List<Integer> findDisappearedNumbers(int[] nums) {
      int[] bucket = new int[nums.length];
      Arrays.fill(bucket, 1);
      for(int i = 0; i < nums.length; i++) {
          bucket[nums[i] - 1]--;
      }
      List<Integer> res = new ArrayList<>();
      for (int i = 0; i < bucket.length; i++) {
          if(bucket[i] == 1) res.add(i + 1);
      }
      return res;
    }

    /**
     * 原地哈希，妙啊
     * 我们可以用一个哈希表记录数组 nums 中的数字，由于数字范围均在 [1,n] 中，记录数字后我们再利用哈希表检查 [1,n] 中的每一个数是否出现，从而找到缺失的数字。
     *
     * 由于数字范围均在 [1,n] 中，我们也可以用一个长度为 n 的数组来代替哈希表。这一做法的空间复杂度是 O(n) 的。我们的目标是优化空间复杂度到 O(1)。
     * 注意到 nums 的长度恰好也为 n，能否让nums 充当哈希表呢？
     * 由于 nums 的数字范围均在[1,n] 中，我们可以利用这一范围之外的数字，来表达「是否存在」的含义。
     * 具体来说，遍历 nums，每遇到一个数 x，就让 nums[x−1] 增加 n。由于 nums 中所有数均在 [1,n] 中，
     * 增加以后，这些数必然大于 n。最后我们遍历 nums，若 nums[i] 未大于 n，就说明没有遇到过数 i+1。这样我们就找到了缺失的数字。
     * 注意，当我们遍历到某个位置时，其中的数可能已经被增加过，因此需要对 n 取模来还原出它本来的值
     *
     * 时间复杂度：O(n)。其中 n是数组 nums 的长度。
     * 空间复杂度：O(1)。返回值不计入空间复杂度。
     *
     *
     * 这题用鸽笼原理实现，由题意可得，1~n的位置表示1~n个笼子，如果出现过，相应的“鸽笼”就会被占掉，
     * 我们将数字置为负数表示被占掉了。 最后再遍历一遍，如果“鸽笼”为正数就是没出现的数字。
     * @param nums
     * @return
     */
    public List<Integer> findDisappearedNumbers1(int[] nums) {
        int n = nums.length;
        for(int num : nums) {
            int x = (num - 1) % n;//得到num值对应的下标
            nums[x] += n;//num-1下标位置的数+n放入nums数组中
        }
        List<Integer> ret = new ArrayList<Integer>();
        for(int i = 0; i < n; i++) {
            //因为值是[1,n]而第一个for循环经过if判断后筛选得到
            //的是有问题的下标即从0开始但值从1，所以下面用i+1
            if(nums[i] <= n) ret.add(i + 1);//i位置上的值i+1未曾出现过
        }
        return ret;
    }

    /**
     * 交换法
     * 遍历数组，将每个数字交换到它理应出现的位置上，下面情况不用换：
     * 当前数字本就出现在理应的位置上，跳过，不用换。
     * 当前数字理应出现的位置上，已经存在当前数字，跳过，不用换。
     * 再次遍历，如果当前位置没对应正确的数，则将对应的 5、6 加入 res。
     * @param nums
     * @return
     */
    public List<Integer> findDisappearedNumbers2(int[] nums) {
        List<Integer> res = new ArrayList<>();
        int i = 0;
        while (i < nums.length) {
            if (nums[i] == i + 1) {
                i++;
                continue;
            }
            int ideaIdx = nums[i] - 1;
            if (nums[i] == nums[ideaIdx]) {
                i++;
                continue;

            }
            int tmp = nums[i];
            nums[i] = nums[ideaIdx];
            nums[ideaIdx] = tmp;
        }
            for (int j = 0; j < nums.length; j++)
                if (nums[j] != j + 1) res.add(j + 1);
        return res;
    }

    /**
     * 暴力哈希
     * @param nums
     * @return
     */
    public List<Integer> findDisappearedNumbers3(int[] nums) {
        List<Integer> res = new ArrayList<>();
        HashSet<Integer> set = new HashSet<>();
        for(int i = 0; i < nums.length; i++) set.add(nums[i]);
        for(int i = 1; i <= nums.length; i++) {
            if(set.add(i)) {
                res.add(i);
            }
        }
        return res;
    }


    /**
     * 按照桶排序思路进行预处理：保证 1 出现在 nums[0] 的位置上，2 出现在 nums[1] 的位置上，…，n 出现在 nums[n - 1] 的位置上。不在 [1, n] 范围内的数不用动。
     * 例如样例中 [4,3,2,7,8,2,3,1] 将会被预处理成 [1,2,3,4,3,2,7,8]。
     * 遍历 nums，将不符合 nums[i] != i + 1 的数字加入结果集 ~

     * @param nums
     * @return
     */
    public List<Integer> findDisappearedNumbers4(int[] nums) {
        int n = nums.length;
        for(int i = 0; i < n; i++) {
            while(nums[i] != i + 1 && nums[nums[i] - 1] != nums[i]) swap(nums, i, nums[i] - 1);
        }
        List<Integer> ans = new ArrayList<>();
        for(int i = 0; i < n; i++) if(nums[i] != i + 1) ans.add(i + 1);
        return ans;
    }
    void swap(int[] nums, int a, int b) {
        int c = nums[a];
        nums[a] = nums[b];
        nums[b] = c;
    }


    @Test
    public void test() {
        int[] nums = {4,3,2,7,8,2,3,1};
        System.out.println(findDisappearedNumbers1(nums).toString());
    }
}
