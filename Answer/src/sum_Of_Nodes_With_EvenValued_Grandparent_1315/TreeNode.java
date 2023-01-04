package sum_Of_Nodes_With_EvenValued_Grandparent_1315;

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
           int sum = 0;
           public int sumEvenGrandparent(TreeNode root) {

                dfs(root);
                return sum;
           }

           void dfs(TreeNode root) {
               if(root == null) return;
               if(root.val % 2 == 0 && hasGrandson(root)) {
                   if(root.left != null) {
                       if(root.left.left != null) sum += root.left.left.val;
                       if(root.left.right != null) sum += root.left.right.val;
                   }
                   if(root.right != null) {
                       if(root.right.left != null) sum += root.right.left.val;
                       if(root.right.right != null) sum += root.right.right.val;
                   }
               }
               dfs(root.left);
               dfs(root.right);
           }

           public boolean hasGrandson(TreeNode root) {
               if(root.left != null) {
                   if(root.left.left != null || root.left.right != null) return true;
               }
               if(root.right != null) {
                   if(root.right.left != null || root.right.right != null) return true;
               }
               return false;
           }


           /**
            * dfs
            */
           int ans = 0;
           public int sumEnenGrandparent01(TreeNode root) {
               if(root.left != null) {
                   dfs01(root, root.left, root.left.left);
                   dfs01(root, root.left, root.left.right);
               }
               if(root.right != null) {
                   dfs01(root, root.right, root.right.left);
                   dfs01(root, root.right, root.right.right);
               }
               return ans;
           }
           public void dfs01(TreeNode grandparent, TreeNode parent, TreeNode node) {
               if(node == null) return;
               if(grandparent.val % 2 == 0) ans += node.val;
               dfs01(parent, node, node.left);
               dfs01(parent, node, node.right);
           }

       }
}
