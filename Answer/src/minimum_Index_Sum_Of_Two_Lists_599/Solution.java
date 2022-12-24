package minimum_Index_Sum_Of_Two_Lists_599;

import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Solution {

    public String[] findRestaurant(String[] list1, String[] list2) {
        HashMap<String, Integer> map1 = new HashMap<String, Integer>();
        int cnt = 0;
        for(String s : list1)
            map1.put(s, cnt++);
        int minIndex = Integer.MAX_VALUE;
        HashMap<String, Integer> map2 = new HashMap<String, Integer>();
        for(int i = 0; i < list2.length; i++) {
            if (map1.containsKey(list2[i])) {
                if ((map1.get(list2[i]) + i) <= minIndex) {
                    minIndex = map1.get(list2[i]) + i;
                    map2.put(list2[i], minIndex);
                }
            }
        }
        List<String> list = new ArrayList<String>();
        for(String s : map2.keySet())
            if(map2.get(s) == minIndex)
                list.add(s);
        return list.toArray(String[]:: new);
    }


    /**
     * 哈希表
     * 使用一个哈希表记录 list1中每个餐厅对应的索引下标，然后遍历 list2
     *如果 list2中的餐厅存在于哈希表中，那么说明该餐厅是两人共同喜爱的，计算它的索引和。
     * 如果该索引和比最小索引和小，则清空结果，将该餐厅加入结果中，该索引和作为最小索引和；
     * 如果该索引和等于最小索引和，则直接将该餐厅加入结果中。
     *
     * @param list1
     * @param list2
     * @return
     */
    public String[] findRestaurant01(String[] list1, String[] list2) {
        Map<String, Integer> index = new HashMap<String, Integer>();
        for(int i = 0; i < list1.length; i++) index.put(list1[i], i);

        List<String> ret = new ArrayList<String>();
        int indexSum = Integer.MAX_VALUE;
        for(int i = 0; i < list2.length; i++) {
            if(index.containsKey(list2[i])) {
                int j = index.get(list2[i]);
                if(i + j < indexSum) {
                    ret.clear();
                    ret.add(list2[i]);
                    indexSum = i + j;
                } else if(i + j == indexSum) ret.add(list2[i]);
                //else break;优化
            }
        }
        return ret.toArray(new String[ret.size()]);
    }

    //时间复杂度：O(n + m)
    //空间复杂度：O(n)
    //Q0: for 循环里的 ans.clear() 这个函数也是 O(n) 复杂度吧，为什么合起来还是 O(n) ?
    //A0: 在 ArrayList 源码中的 clear 实现会为了消除容器对对象的强引用，遍历容器内的内容并置空来帮助 GC
    //但不代表这会导致复杂度上界变成 n^2
    //不会导致复杂度退化的核心原因是：由于 clear 导致的循环计算量总共必然不会超过 n。因为最多只有 n 个元素在 ans 里面，
    // 且同一元素不会被删除多次（即每个元素对 clear 的贡献不会超过 1）。
    //如果有同学还是觉得不好理解，可以考虑一种极端情况：clear 操作共发生 n 次，但发生 n 次的前提条件是每次 ans 中只有 1 位元素，
    // 此时由 clear 操作带来的额外计算量为最大值 n。
    //因此这里的 clear 操作对复杂度影响是「累加」，而不是「累乘」，即复杂度仍为 O(n)

    //Q1: 判断 list[i] 是否在哈希中的操作，复杂度是多少？
    //A1: 在 Java 的 HashMap 实现中，当键值对中的键数据类型为 String 时，会先计算一次（之后使用缓存）该字符串的 HashCode，
    // 计算 HashCode 的过程需要遍历字符串，因此该操作是与字符串长度相关的（对于本题字符串长度不超过 30），
    // 然后根据 HashCode「近似」O(1)定位到哈希桶位置并进行插入/更新。
    //因此在 Java 中，该操作与「当前的字符串长度」相关，而与「当前容器所包含元素多少」无关。
    public String[] findRestaurant02(String[] list1, String[] list2) {
        int n = list1.length, m = list2.length;
        Map<String, Integer> map = new HashMap<>();
        for(int i = 0; i < n; i++) map.put(list1[i], i);
        List<String> ans = new ArrayList<>();
        int min = 3000;
        for(int i = 0; i < m; i++) {
            String s = list2[i];
            if(!map.containsKey(s)) continue;
            if(i + map.get(s) < min) {
                ans.clear();
                min = i + map.get(s);
                ans.add(s);
            } else if(i + map.get(s) == min)
                ans.add(s);
        }
        return ans.toArray(new String[ans.size()]);
    }


    public String[] findRestaurant03(String[] list1, String[] list2) {
        return IntStream.range(0, list1.length + list2.length)//产生0-(n1 + n2 - 1)的int流
                .boxed()//mapToObj(Integer::valueOf)转为Integer
                //对相同的String进行groupBy,生成map<String, List<Integer>>
                .collect(Collectors.groupingBy(i -> i < list1.length ? list1[i] : list2[i - list1.length]))
                .entrySet()//list<Map.Entry>
                //重新转为流Map.Entry<String, List<Integer>>
                .stream()
                .filter(i -> i.getValue().size() == 2)
                //再进行groupBy,生成TreeMap<Integer, List<String>>
                .collect(Collectors.groupingBy(i -> i.getValue().get(0) + i.getValue().get(1), TreeMap::new, Collectors.mapping(Map.Entry::getKey, Collectors.toList())))
                //输出第一个
                .firstEntry()
                .getValue()
                .toArray(new String[0]);
    }




    @Test
    public void test() {
        String[] list1 = {"Shogun", "Tapioca Express", "Burger King", "KFC"};
        String[] list2 = {"KFC", "Shogun", "Burger King"};
        System.out.println(Arrays.toString(findRestaurant(list1, list2)));
    }
}
