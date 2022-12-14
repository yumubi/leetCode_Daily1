package binary_Tree_Right_Side_View_199;

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
           public List<Integer> rightSideView(TreeNode root) {
               if(root == null) return new ArrayList<>();
               TreeNode node = root;
               int level = 0;
               List<Integer> res = new ArrayList<>();
               while(node != null) {
                   res.add(node.val);
                   level++;
                   node = node.right;
               }
               int cnt = 0;
               while(root != null) {
                   cnt++;
                   root = root.left;
                   if(cnt > level) res.add(root.val);
               }
               return res;
           }
       }
}
