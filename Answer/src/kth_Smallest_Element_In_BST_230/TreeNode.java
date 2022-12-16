package kth_Smallest_Element_In_BST_230;

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
           List<Integer> list = new ArrayList<Integer>();
           public int kthSmallest(TreeNode root, int k) {
               dfs(root);
               return list.get(k - 1);
           }
           public void dfs(TreeNode root) {
               if(root == null) return;

               dfs(root.left);
               list.add(root.val);
               dfs(root.right);
           }
       }


}
