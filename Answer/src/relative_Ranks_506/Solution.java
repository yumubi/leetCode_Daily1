package relative_Ranks_506;

import org.junit.Test;

import java.util.*;

public class Solution {
    /**
     * 代码太丑了
     * @param score
     * @return
     */
    public String[] findRelativeRanks(int[] score) {
        HashMap<Integer, String> map = new HashMap<>();
        int len = score.length;
        String[] strs = new String[len];
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        for(int i = 0; i < score.length; i++) pq.offer(score[i]);
        for(int i = 0; i < len; i++) {
            int ele = pq.poll();
            if(pq.size() > 2) map.put(ele, String.valueOf(pq.size() + 1));
            if(pq.size() == 0) map.put(ele, "Gold Medal");
            if(pq.size() == 1) map.put(ele, "Silver Medal");
            if(pq.size() == 2) map.put(ele, "Bronze Medal");

        }
        for(int i = 0; i < len; i++) {
            strs[i] = map.get(score[i]);
        }
        return strs;
    }

    /**
     * 排序
     * 题目要求找到每个运动员的相对名次，并同时给前三名标记为 {"Gold Medal", "Silver Medal", "Bronze Medal"}，其余的运动员则标记为其相对名次。
     * 将所有的运动员按照成绩的高低进行排序，然后将按照名次进行标记即可。
     * @param score
     * @return
     */
    public String[] findRelativeRanks1(int[] score) {
        int n = score.length;
        String[] desc ={"Gold Medal", "Silver Medal", "Bronze Medal"};
        int[][] arr = new int[n][2];

        for(int i = 0; i < n; ++i) {
            arr[i][0] = score[i];
            arr[i][1] = i;
        }
        Arrays.sort(arr, (a, b) -> b[0] - a[0]);
        String[] ans = new String[n];
        for(int i = 0; i < n; ++i) {
            if(i >= 3) ans[arr[i][1]] = Integer.toString(i + 1);
            else ans[arr[i][1]] = desc[i];
        }
        return ans;
    }


    /**
     * 根据题意，我们可以先对 score 数组进行拷贝并排序，利用分数各不相同，
     * 对排序数组中分值进行名次编号（存入哈希表），再利用名次编号构造答案。
     * 时间复杂度：拷贝 score 数组的复杂度为 O(n)；对拷贝数组进行排序的复杂度为 OO(nlogn)；构造哈希表的复杂度为O(n)；
     * 利用哈希表构造答案复杂度为 O(n)。整体复杂度为O(nlogn)
     * 空间复杂度：O(n)
     */
    String[] ss = new String[]{"Gold Medal", "Silver Medal", "Bronze Medal"};
    public String[] findRelativeRanks2(int[] score) {
        int n = score.length;
        String[] ans = new String[n];
        int[] clone = score.clone();
        Arrays.sort(clone);
        Map<Integer, Integer> map = new HashMap<>();
        for(int i = n - 1; i >= 0; i--) map.put(clone[i], n - 1 - i);
        for(int i =0; i < n; i++) {
            int rank = map.get(score[i]);
            ans[i] = rank < 3 ? ss[rank] : String.valueOf(rank + 1);
        }
        return ans;
    }


    /**
     *Pq
     * @param score
     * @return
     */
    public String[] findRelativeRanks3(int[] score) {
        //大根堆
          PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(e -> -e[0]));
          for(int i = 0; i < score.length; i++) pq.offer(new int[]{score[i], i});
          String[] answer = new String[score.length], medal = new String[] {"Gold Medal", "Silver Medal", "Bronze Medal"};
          for(int x = 1; !pq.isEmpty(); x++) answer[pq.poll()[1]] = x <= medal.length ? medal[x - 1] : String.valueOf(x);
          return answer;
    }


    public String[] findRelativeRanks(int[] nums) {
        int n = nums.length;
        int[] array = new int[n];
        System.arraycopy(nums, 0, array, 0, n);
        Arrays.sort(array);
        String[] result = new String[n];
        for(int i = 0; i < n; i++) {
            int index = n - Arrays.binarySearch(array, nums[i]);
            switch (index) {
                case 1:
                    result[i] = "Gold Medal";
                    break;
                case 2:
                    result[i] = "Silver Medal";
                    break;
                case 3:
                    result[i] = "Bronze Medal";
                    break;
                default:
                    result[i] = String.valueOf(index);
            }
        }
        return result;
    }












    @Test
    public void test() {
        int[] s = {5, 4, 3, 2, 1};
        System.out.println(Arrays.toString(findRelativeRanks(s)));

























    }
}
