package offer_56;

import java.util.ArrayList;
import java.util.List;

public class TreeNode {
    int val;
       TreeNode left;
       TreeNode right;
       TreeNode() {}
       TreeNode(int val) { this.val = val; }
       TreeNode(int val, TreeNode left, TreeNode right) {
           this.val = val;
           this.left = left;
           this.right = right;
       }


       class Solution {
           List<Integer> res = new ArrayList<>();
           public boolean findTarget(TreeNode root, int k) {
               InorderTravsersal(root);
               int arr[] = res.stream().mapToInt(i -> i).toArray();
               int lo = 0, hi = arr.length - 1;
               while(lo < hi) {
                   int add = arr[lo] + arr[hi];
                   if(add == k) return true;
                   if(add < k) lo++;
                   else hi--;
               }
               return false;
           }

           public void InorderTravsersal(TreeNode root) {
               if(root == null) return;
               InorderTravsersal(root.left);
               res.add(root.val);
               InorderTravsersal(root.right);
           }


       }
}
