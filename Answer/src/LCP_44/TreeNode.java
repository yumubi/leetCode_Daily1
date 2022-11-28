package LCP_44;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TreeNode {
    int val;
       TreeNode left;
       TreeNode right;
       TreeNode(int x) { val = x; }

    class Solution {
           public int numColor(TreeNode root) {
               Set<Integer> set = new HashSet<>();
               dfs(root, set);
               return set.size();
           }
           public void dfs(TreeNode root, Set<Integer> set) {
               if(root == null) return;
               set.add(root.val);
               dfs(root.left, set);
               dfs(root.right, set);
           }
    }


}
