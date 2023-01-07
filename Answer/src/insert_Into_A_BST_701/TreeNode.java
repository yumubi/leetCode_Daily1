package insert_Into_A_BST_701;

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
           public TreeNode insertIntoBST(TreeNode root, int val) {
               if (root == null) return new TreeNode(val);
               if (val > root.val) {
                   TreeNode right = insertIntoBST(root.right, val);
                   root.right = right;
               } else {
                   TreeNode left = insertIntoBST(root.left, val);
                   root.left = left;
               }
               return root;
           }


           /**
            * 模拟
            * 当将val 插入到以root 为根的子树上时，根据 val 与root.val 的大小关系，就可以确定要将val 插入到哪个子树中。
            * 如果该子树不为空，则问题转化成了将 val 插入到对应子树上。
            * 否则，在此处新建一个以 val 为值的节点，并链接到其父节点 root 上。
            * 时间复杂度：O(N)，其中 N 为树中节点的数目。最坏情况下，我们需要将值插入到树的最深的叶子结点上，而叶子节点最深为 O(N)。
            * 空间复杂度：O(1)。我们只使用了常数大小的空间。
            * @param root
            * @param val
            * @return
            */
           public TreeNode insertIntoBST01(TreeNode root, int val) {
               if (root == null) return new TreeNode(val);
               TreeNode pos = root;
               while (pos != null) {
                   if (val < pos.val) {
                       if (pos.left == null) {
                           pos.left = new TreeNode(val);
                           break;
                       } else pos = pos.left;
                   } else {
                       if (pos.right == null) {
                           pos.right = new TreeNode(val);
                           break;
                       } else pos = pos.right;
                   }
               }
               return root;
           }




       }
}
