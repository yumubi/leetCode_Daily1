package set_Mismatch_645;

import java.util.*;

public class Solution {
    public int[] findErrorNums(int[] nums) {
        Set<Integer> set = new HashSet<>();
        int same = 0, miss = 1;
        for(int i = 0; i < nums.length; i++) {
            if(!set.add(nums[i])) same = nums[i];
        }
        for(int i = 1; i <= nums.length; i++) {
            if(!set.contains(i)) miss = i;
        }
        return new int[]{same, miss};
    }

    /**
     * 哈希表
     * 重复的数字在数组中出现 2 次，丢失的数字在数组中出现 0 次，其余的每个数字在数组中出现 1 次。因此可以使用哈希表记录每个元素在数组中出现的次数，
     * 然后遍历从 1 到 n 的每个数字，分别找到出现 2 次和出现 0 次的数字，即为重复的数字和丢失的数字。
     * 间复杂度：O(n)，其中 n 是数组 nums 的长度。需要遍历数组并填入哈希表，然后遍历从 1 到 n 的每个数寻找错误的集合。
     * 空间复杂度：O(n)，其中 n 是数组 nums 的长度。需要创建大小为 O(n) 的哈希表。
     * @param nums
     * @return
     */
    public int[] findErrorNums01(int[] nums) {
        int[] errorNums = new int[2];
        int n = nums.length;
        Map<Integer,Integer> map = new HashMap<Integer, Integer>();
        for(int num : nums) map.put(num, map.getOrDefault(num, 0) + 1);
        for(int i = 1; i <= n; i++) {
            int count = map.getOrDefault(i, 0);
            if(count == 2) errorNums[0] = i;
            else if(count == 0) errorNums[1] = i;
        }
        return errorNums;
    }


    /**
     * 排序
     * 将数组排序之后，比较每对相邻的元素，即可找到错误的集合。
     * 寻找重复的数字较为简单，如果相邻的两个元素相等，则该元素为重复的数字。
     * 寻找丢失的数字相对复杂，可能有以下两种情况：
     * 如果丢失的数字大于 1 且小于 n，则一定存在相邻的两个元素的差等于 2，这两个元素之间的值即为丢失的数字；
     * 如果丢失的数字是 1 或 n，则需要另外判断。
     *
     * 为了寻找丢失的数字，需要在遍历已排序数组的同时记录上一个元素，然后计算当前元素与上一个元素的差。考虑到丢失的数字可能是 1，因此需要将上一个元素初始化为 0。
     * 当丢失的数字小于 n 时，通过计算当前元素与上一个元素的差，即可得到丢失的数字；
     * 如果 nums[n−1] ！= n，则丢失的数字是 n 。
     *
     * 时间复杂度：O(nlogn)，其中 n 是数组nums 的长度。排序需要O(nlogn) 的时间，遍历数组找到错误的集合需要 O(n) 的时间，因此总时间复杂度是 O(nlogn)。
     * 空间复杂度：O(logn)，其中 n 是数组 nums 的长度。排序需要 O(logn) 的空间
     * @param nums
     * @return
     */
    public int[] findErrorNums02(int[] nums) {
        int[] errorNums = new int[2];
        int n = nums.length;
        Arrays.sort(nums);
        int prev = 0;
        for(int i = 0; i < n; i++) {
            int curr = nums[i];
            if(curr == prev) {
                errorNums[0] = prev;
            } else if(curr - prev > 1) {
                errorNums[1] = prev + 1;
            }
            prev = curr;
        }
        if(nums[n - 1] != n) errorNums[1] = n;
        return errorNums;
    }


    /**
     * 位运算
     * 使用位运算，可以达到 O(n) 的时间复杂度和 O(1) 的空间复杂度。
     * 重复的数字在数组中出现 2 次，丢失的数字在数组中出现 0 次，其余的每个数字在数组中出现 1 次。由此可见，重复的数字和丢失的数字的出现次数的奇偶性相同，
     * 且和其余的每个数字的出现次数的奇偶性不同。如果在数组的 n 个数字后面再添加从 1 到 n 的每个数字，得到 2n 个数字，
     * 则在 2n 个数字中，重复的数字出现 3 次，丢失的数字出现 1 次，其余的每个数字出现 2 次。根据出现次数的奇偶性，可以使用异或运算求解。
     *
     * 用 x 和 y分别表示重复的数字和丢失的数字。考虑上述 2n 个数字的异或运算结果 xor，由于异或运算 ⊕ 满足交换律和结合律，
     * 且对任何数字 a 都满足 a⊕a=0 和 0⊕a=a，因此 xor=x⊕x⊕x⊕y=x⊕y，即 x 和 y 的异或运算的结果。
     * 由于 x != y，因此xor != 0，令 lowbit=xor&(−xor)，则 lowbit 为x 和 y 的二进制表示中的最低不同位，可以用 lowbit 区分 x 和 y。
     *
     * 得到 lowbit 之后，可以将上述 2n 个数字分成两组，第一组的每个数字 a 都满足 a&lowbit=0，第二组的每个数字 b 都满足 b&lowbit!=0。
     * 创建两个变量 num1和num2，初始值都为0，然后再次遍历上述2n 个数字，对于每个数字 a，如果 a&lowbit=0，则令 num1=num1⊕a，
     * 否则令 num2=num2⊕a。
     * 遍历结束之后，num1为第一组的全部数字的异或结果，num2为第二组的全部数字的异或结果。由于同一个数字只可能出现在其中的一组，
     * 且除了 x 和 y 以外，每个数字一定在其中的一组出现 2 次，因此num1和 num2分别对应 x 和 y 中的一个数字，但是具体对应哪个数还未知。
     * 为了知道 num1和 num2分别对应 x 和 y 中的哪一个数字，只需要再次遍历数组 nums 即可。如果数组中存在元素等于 num1，
     * 则有 x=num1和 y=num2，否则有 x=num2和 y=num1
     * 时间复杂度：O(n)，其中 n 是数组 nums 的长度。整个过程需要对数组 nums 遍历 3 次，以及遍历从 1 到 n 的每个数 2 次。
     * 空间复杂度：O(1)。只需要常数的额外空间。
     * @param nums
     * @return
     */
    public int[] findErrorNums03(int[] nums) {
        int n = nums.length;
        int xor = 0;
        for(int num : nums) xor ^= num;
        for(int i = 1; i <= n; i++) xor ^= i;

        int lowbit = xor & (-xor);

        int num1 = 0, num2 = 0;
        for(int num : nums) {
            if( (num & lowbit) == 0) {
                num1 ^= num;
            } else {
                num2 ^= num;
            }
        }
        for(int i = 1; i <= n; i++) {
            if( (i & lowbit) == 0) {
                num1 ^= i;
            } else {
                num2 ^= i;
            }
        }

        for(int num : nums) {
            if(num == num1) {
                return new int[]{num1, num2};
            }
        }
        return new int[]{num2, num1};
    }


    /**
     * 一个朴素的做法是，使用「哈希表」统计每个元素出现次数，然后在 [1,n] 查询每个元素的出现次数。
     * 在「哈希表」中出现 2 次的为重复元素，未在「哈希表」中出现的元素为缺失元素。
     * 由于这里数的范围确定为 [1,n]，我们可以使用数组来充当「哈希表」，以减少「哈希表」的哈希函数执行和冲突扩容的时间开销。
     * 时间复杂度：O(n)
     * 空间复杂度：O(n)
     * @param nums
     * @return
     */
    public int[] findErrorNums04(int[] nums) {
        int n = nums.length;
        int[] cnts = new int[n + 1];
        for(int x : nums) cnts[x]++;
        int[] ans = new int[2];
        for(int i = 1; i <= n; i++) {
            if(cnts[i] == 0) ans[1] = i;
            if(cnts[i] == 2) ans[0] = i;
        }
        return ans;
    }


    /**
     * 我们还可以利用数值范围为[1,n]，只有一个数重复和只有一个缺失的特性，进行「作差」求解。
     * 令 [1,n] 的求和为tot，这部分可以使用「等差数列求和公式」直接得出：tot = n(1+n) / 2；
     * 令数组 nums 的求和值为 sum，由循环累加可得；
     * 令数组 sums 去重求和值为 set，由循环配合「哈希表/数组」累加可得。
     * 最终答案为 (重复元素, 缺失元素) = (sum-set, tot-set)
     * 时间复杂度：O(n)
     * 空间复杂度：O(n)
     * @param nums
     * @return
     */
    public int[] findErrorNums05(int[] nums) {
        int n = nums.length;
        int[] cnts = new int[n + 1];
        int tot = (1 + n) * n / 2;
        int sum = 0, set = 0;
        for(int x : nums) {
            sum += x;
            if(cnts[x] == 0) set += x;
            cnts[x] = 1;
        }
        return new int[]{sum - set, tot - set};
}


    /**
     * 因为值的范围在 [1,n]，我们可以运用「桶排序」的思路，根据 nums[i]=i+1 的对应关系使用 O(n) 的复杂度将每个数放在其应该落在的位置里。
     * 然后线性扫描一遍排好序的数组，找到不符合 nums[i]=i+1 对应关系的位置，从而确定重复元素和缺失元素是哪个值。
     * 时间复杂度：O(n)
     * 空间复杂度：O(1)
     * @param nums
     * @return
     */
    public int[] findErrorNums06(int[] nums) {
        int n = nums.length;
        for(int i = 0; i < n; i++) {
            while (nums[i] != i + 1 && nums[nums[i] - 1] != nums[i]) {
                swap(nums, i, nums[i] - 1);
            }
        }
        int a = -1, b = -1;
        for(int i = 0; i < n; i++) {
            if(nums[i] != i + 1) {
                a = nums[i];
                b = i == 0 ? 1 : nums[i - 1] + 1;
            }
        }
        return new int[]{a, b};
    }
    void swap(int[] nums, int i, int j) {
        int tmp = nums[i];
        nums[i] = nums[j];
        nums[j] = tmp;
    }


    /**
     * 一行流
     * @param nums
     * @return
     */
    public int[] findErrorNums07(int[] nums) {
        return new int[] {
                Arrays.stream(nums).sum() - Arrays.stream(nums).distinct().sum(),
                (1 + nums.length) * nums.length / 2 - Arrays.stream(nums).distinct().sum()
        };
    }

}
