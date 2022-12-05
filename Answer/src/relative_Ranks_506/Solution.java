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


    public String[] findRelativeRanks4(int[] nums) {
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

    /**
     * 使用 TreeMap 来实现对成绩得到排序，key 存储成绩，value 存储成绩在数组中的下标。TreeMap 是按照升序进行排序的
     * ，所以在遍历集合时，通过计算可以得出当前成绩的排名。
     * @param nums
     * @return
     */
    public String[] findRelativeRanks5(int[] nums) {
        int n = nums.length;
        String[] result = new String[n];
        // key 为成绩，value 为成绩在数组中的下标，TreeMap 是按照升序进行排序的
        Map<Integer, Integer> map = new TreeMap<>();
        for(int i = 0; i < n; i++) map.put(nums[i], i);
        int count = 0;
        for(Map.Entry<Integer, Integer> set : map.entrySet()) {
            int ranking = n - count++;
            switch (ranking) {
                case 1:
                    result[set.getValue()] = "Golde Medal";
                    break;
                case 2:
                    result[set.getValue()] = "Silver Medal";
                    break;
                case 3:
                    result[set.getValue()] = "Bronze Medal";
                    break;
                default:
                    result[set.getValue()] = String.valueOf(ranking);
            }
        }
        return result;
    }

    /**
     * todo
     *计数排序
     首先寻找数组中最大的值（成绩最高的），创建一个 int[] array = new int[max + 1]; 的数组用来实现计数排序。
     array 数组的下标对应成绩，值为该成绩所在的原数组的下标。由于 array 数组的值默认为 0，所以在存储成绩的下标时，应对下标加 1，取时减 1 即可
     * @param nums
     * @return
     */
    public String[] findRelativeRanks6(int[] nums) {
        int n  = nums.length;
        String[] result = new String[n];
        int max = 0;
        // 找出找出最高的成绩
        for(int num : nums) {
            if(max < num) max = num;
        }
        // 下标为成绩，值为成绩在 nums 数组的下标
        int[] array = new int[max + 1];
        for(int i =0; i < n; i++) array[nums[i]] = i + 1;
        // 记录当前成绩的排名
        int count  = 1;
        for(int i = array.length - 1; i >= 0; i--) {
            if(array[i] != 0) {
                // 根据排名进行赋值
                switch (count) {
                    case 1:
                        result[array[i] - 1] = "Gold Medal";
                        break;
                    case 2:
                        result[array[i] - 1] = "Silver Medal";
                        break;
                    case 3:
                        result[array[i] - 1] = "Bronze Medal";
                        break;
                    default:
                        result[array[i] - 1] = String.valueOf(count);
                }
                count++;
            }
        }
            return result;
    }


    private static final Map<Integer, String> RANKING = new HashMap<>();
    static {
        RANKING.put(1, "Gold Medal");
        RANKING.put(2, "Silver Medal");
        RANKING.put(3, "Bronze Medal");
    }

    public String[] findRelativeRanks7(int[] score) {
        PriorityQueue<int[]> heap = new PriorityQueue<>((a, b) -> b[1] - a[1]);;
        for(int i = 0; i < score.length; i++) heap.offer(new int[]{i, score[i]});
        String[] ans = new String[score.length];
        int rank = 1;
        while (!heap.isEmpty()) {
            int[] element = heap.poll();
            ans[element[0]] = RANKING.getOrDefault(rank, String.valueOf(rank++));
        }
        return ans;
    }












    @Test
    public void test() {
        int[] s = {5, 4, 3, 2, 1};
        System.out.println(Arrays.toString(findRelativeRanks(s)));

























    }
}
