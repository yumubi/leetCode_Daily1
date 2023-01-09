package lowest_Common_Ancestor_Of_Deepest_leaves_1123;

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

           //todo不会

           /**
            * 类似于前序遍历，从根节点开始，分别求左右子树的高度left，和right。
            * 情况1：left=right 那么两边子树的最深高度相同，返回本节点
            * 情况2：left<right 说明最深节点在右子树，直接返回右子树的递归结果
            * 情况2：left>right 说明最深节点在左子树，直接返回右子树的递归结果
            * @param root
            * @return
            */
           public TreeNode lcaDeepestLeaves(TreeNode root) {
                if(root == null) return null;
                int left = dfs(root.left);
                int right = dfs(root.right);
                if(left == right) return root;
                else if(left < right) return lcaDeepestLeaves(root.right);
                return lcaDeepestLeaves(root.left);

           }
           int dfs(TreeNode node) {
               if(node == null) return 0;
               return 1 + Math.max(dfs(node.right), dfs(node.left));
           }






       }


}
