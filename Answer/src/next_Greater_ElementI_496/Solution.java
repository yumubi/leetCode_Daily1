package next_Greater_ElementI_496;

import org.junit.Test;

import java.util.*;

public class Solution {
    /**
     * 祭拜百分5
     * @param nums1
     * @param nums2
     * @return
     */
    public int[] nextGreaterElement(int[] nums1, int[] nums2) {
        for (int i = 0; i < nums1.length; i++)
                nums1[i] = findNext(nums2, nums1[i]);
        return nums1;
    }
        public int findNext(int[] coll, int num1) {
        int idx = 0;
            for(int i = 0; i < coll.length; i++) {
                if(coll[i] == num1) idx = i;
            }
            if(idx == coll.length - 1) return -1;
            else for(int i = idx + 1; i < coll.length; i++)
                    if(coll[i] > num1) return coll[i];
            return -1;
        }


    /**
     * 暴力
     *时间复杂度：O(mn)
     * 空间复杂度：O(1)
     * @param nums1
     * @param nums2
     * @return
     */
        public int[] nextGreaterElement1(int[] nums1, int[] nums2) {
            int m = nums1.length, n = nums2.length;
            int[] res = new int[m];
            for(int i = 0; i < m; ++i) {
                int j = 0;
                while(j < n && nums2[j] != nums1[i]) ++j;
                int k = j + 1;
                while (k < n && nums2[k] < nums2[j]) ++k;
                res[i] = k < n ? nums2[k] : -1;
            }
            return res;
        }


    /**
     * 单调栈+哈希表
     * 可以先预处理 nums2，使查询 nums1中的每个元素在 nums2中对应位置的右边的第一个更大的元素值时不需要再遍历 nums2
     *于是，我们将题目分解为两个子问题：
     * 第 1 个子问题：如何更高效地计算 nums2中每个元素右边的第一个更大的值；
     * 第 2个子问题：如何存储第 1 个子问题的结果。
     *
     * 可以使用单调栈来解决第 1 个子问题。倒序遍历 nums2，并用单调栈中维护当前位置右边的更大的元素列表，从栈底到栈顶的元素是单调递减的。
     * 具体地，每次我们移动到数组中一个新的位置 i，就将当前单调栈中所有小于 nums2[i] 的元素弹出单调栈，
     * 当前位置右边的第一个更大的元素即为栈顶元素，如果栈为空则说明当前位置右边没有更大的元素。随后我们将位置 i 的元素入栈
     *
     * nums2是没有重复元素的，所以我们可以使用哈希表来解决第 2 个子问题，将元素值与其右边第一个更大的元素值的对应关系存入哈希表。
     * 因为在这道题中我们只需要用到 nums2中元素的顺序而不需要用到下标，所以栈中直接存储nums2中元素的值即可。
     *
     * 时间复杂度：O(m + n) 我们需要遍历 nums2以计算 nums2中每个元素右边的第一个更大的值；需要遍历 nums1以生成查询结果。
     * 空间复杂度：O(n)，用于存储哈希表。

     * @param nums1
     * @param nums2
     * @return
     */
        public int[] nextGreaterElement2(int[] nums1, int[] nums2) {
            Map<Integer, Integer> map = new HashMap<Integer, Integer>();
            Deque<Integer> stack = new ArrayDeque<Integer>();
            for(int i = nums2.length - 1; i >= 0; --i) {
                int num = nums2[i];
                while (!stack.isEmpty() && num >= stack.peek()) stack.pop();
                map.put(num, stack.isEmpty() ? -1 : stack.peek());
                stack.push(num);
            }
            int[] res = new int[nums1.length];
            for(int i = 0; i < nums1.length; ++i) res[i] = map.get(nums1[i]);
            return res;
        }

    /**
     * 宫水三叶的单调栈
     * 当题目出现「找到最近一个比其大的元素」的字眼时，自然会想到「单调栈」。
     * 具体的，由于我们目标是找到某个数其在 nums2 的右边中第一个比其大的数，因此我们可以对 nums2 进行逆序遍历。
     * 我们在遍历 nums2 时，实时维护一个单调栈，当我们遍历到元素 nums2[i] 时，可以先将栈顶中比 nums2[i] 小的元素出栈，最终结果有两种可能：
     *      栈为空，说明 nums2[i] 之前（右边）没有比其大的数；
     *      栈不为空， 此时栈顶元素为 nums2[i] 在 nums2 中（右边）最近的比其大的数。
     *再利用数组中数值各不相同，在遍历 nums2 的同时，使用哈希表记录每个 nums2[i] 对应目标值是多少即可。
     *
     * 这里的「倒序遍历」是为了「正向解决问题」。由于我们找的是某个数右边第一个比其大的数，因此我们倒序遍历，可以确保需要找的数已经被处理过。
     *

     * @param nums1
     * @param nums2
     * @return
     */
        public int[] nextGreaterElement3(int[] nums1, int[] nums2) {
            int n = nums1.length, m = nums2.length;
            Deque<Integer> d = new ArrayDeque<>();
            Map<Integer, Integer> map = new HashMap<>();
            for(int i = m - 1; i >= 0; i--) {
                int x = nums2[i];
                while(!d.isEmpty() && d.peekLast() <= x) d.pollLast();
                map.put(x, d.isEmpty() ? -1 : d.peekLast());
                d.addLast(x);
            }
            int[] ans = new int[n];
            for(int i = 0; i < n; i++) ans[i] = map.get(nums1[i]);
            return ans;
        }

    /**
     * 单调栈的正序遍历
     * @param nums1
     * @param nums2
     * @return
     */
        public int[] nextGreaterElement4(int[] nums1, int[] nums2) {
            int n = nums1.length, m = nums2.length;
            Deque<Integer> d = new ArrayDeque<>();
            Map<Integer, Integer> map = new HashMap<>();
            for(int i : nums2) {
                while (!d.isEmpty() && d.peekLast() < i) map.put(d.pollLast(), i);
                d.addLast(i);
            }
            int[] ans = new int[n];
            for(int i = 0; i < n; i++) ans[i] = map.getOrDefault(nums1[i], -1);
            return ans;
        }

    /**
     * 单调栈中存放的元素为下标而不是值
     * @param nums1
     * @param nums2
     * @return
     */
    public int[] nextGreaterElement5(int[] nums1, int[] nums2) {
            HashMap<Integer, Integer> map = new HashMap<>();
            Deque<Integer> stack = new LinkedList<>();
            for(int i = 0; i < nums2.length; i++) {
                while (!stack.isEmpty() && nums2[stack.peek()] < nums2[i]) {
                    int j = stack.pop();
                    map.put(nums2[j], nums2[i]);//此时nums2[j] < nums2[i]
                }
                stack.push(i);//下标入栈
            }
            int[] ans = new int[nums1.length];
            for(int i = 0; i < nums1.length; i++) ans[i] = map.getOrDefault(nums2[i], -1);
            return ans;
        }



        @Test
        public void test() {

        }
}
