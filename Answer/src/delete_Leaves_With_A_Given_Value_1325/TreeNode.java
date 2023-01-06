package delete_Leaves_With_A_Given_Value_1325;

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

           /**
            * 不知道为什么root没有被改变
            */
           private TreeNode root;
           public TreeNode removeLeafNode(TreeNode root, int target) {
               this.root = root;
               dfs(this.root, target);
               return this.root;
           }

           void dfs(TreeNode root, int target) {
               if (root == null) return;
               dfs(root.left, target);
               dfs(root.right, target);
               if (root.left == null && root.right == null && root.val == target) {
                   root = null;
                   return;
               }
           }

           /**
            * 递归
            * 由于我们需要删除所有值为 target 的叶子节点，那么我们的操作顺序应当从二叉树的叶子节点开始，逐步向上直到二叉树的根为止。
            * 因此我们可以使用递归的方法遍历整颗二叉树，并在回溯时进行删除操作。这样对于二叉树中的每个节点，
            * 它的子节点一定先于它被操作。这其实也就是二叉树的后序遍历。
            * 具体地，当我们回溯到某个节点 u 时，
            * 如果 u 的左右孩子均不存在（这里有两种情况，一是节点 u 的孩子本来就不存在，二是节点 u 的孩子变成了叶子节点并且值为 target，导致其被删除），
            * 并且值为 target，那么我们要删除节点 u，递归函数的返回值为空节点；如果节点 u 不需要被删除，那么递归函数的返回值为节点 u 本身。
            * 时间复杂度：O(N)，其中 N 是二叉树的节点个数。
            * 空间复杂度：O(H)，其中 H 是二叉树的高度。
            * @param root
            * @param target
            * @return
            */
               public TreeNode removeLeafNodes01(TreeNode root, int target) {
                   if(root == null) return root;
                   root.left = removeLeafNodes01(root.left, target);
                   root.right = removeLeafNodes01(root.right, target);
                   if(root.left == null && root.right == null && root.val == target) return null;
                   return root;
               }



       }
}
