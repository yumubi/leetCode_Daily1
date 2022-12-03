package offer_59;

import java.util.PriorityQueue;

public class KthLargest {
    //下面的过不了，测试恶心人
//    ["KthLargest","add","add","add","add","add"]
//            [[2,[0]],[-1],[1],[-2],[-4],[3]]
//    PriorityQueue<Integer> pq;
//
//    public KthLargest(int k, int[] nums) {
//        pq = new PriorityQueue<Integer>(k);
//        if(nums.length == 0) pq.add(Integer.MIN_VALUE);
//        else
//        {
//            for(int i = 0; i < k; i++) pq.offer(nums[i]);
//            for(int j = k; j < nums.length; j++) add(nums[j]);
//        }
//    }
//
//    public int add(int val) {
//        if(val > pq.peek()) {
//            pq.poll();
//            pq.offer(val);
//        }
//        return pq.peek();
//    }


    /**
     * 我们可以使用一个大小为 k 的优先队列来存储前 k大的元素，其中优先队列的队头为队列中最小的元素，也就是第 k大的元素。
     * 在单次插入的操作中，我们首先将元素 val 加入到优先队列中。如果此时优先队列的大小大于 k，
     * 我们需要将优先队列的队头元素弹出，以保证优先队列的大小为 k。
     */
    PriorityQueue<Integer> pq;
    int k;
    public KthLargest(int k, int[] nums) {
        this.k = k;
        pq = new PriorityQueue<Integer>();
        for(int x : nums) add(x);
    }
    public int add(int val){
        pq.offer(val);
        if(pq.size() > k) {
            pq.poll();
        }
        return pq.peek();
    }






}
