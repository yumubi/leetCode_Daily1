package count_Complete_TreeNodes_222;

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
           int cnt = 0;
           public int countNodes(TreeNode root) {
                if(root == null) return 0;
                dfs(root);
                return cnt;
           }

           void dfs(TreeNode root) {
               if(root == null) return;
               cnt++;
               dfs(root.left);
               dfs(root.right);
           }


       }
}
