package next_Greater_ElementI_496;

public class Solution {
    public int[] nextGreaterElement(int[] nums1, int[] nums2) {
        for (int i = 0; i < nums1.length; i++)
                nums1[i] = findNext(nums2, i, nums1[i]);
        return nums1;
    }
        public int findNext(int[] coll, int idx, int num1) {
            for(int i = idx; i < coll.length; i++)
                if(coll[i] > num1) return coll[i];
            return -1;
        }
}
