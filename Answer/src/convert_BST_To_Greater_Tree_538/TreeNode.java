package convert_BST_To_Greater_Tree_538;

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
           public TreeNode convertBST(TreeNode root) {
                dfs(root);
                return root;
           }
           public void dfs(TreeNode root) {
               if(root == null) return;
               dfs(root.right);
               sum += root.val;
               root.val = sum;
               dfs(root.left);
           }


           //二叉搜索树是一棵空树，或者是具有下列性质的二叉树：
           //若它的左子树不空，则左子树上所有节点的值均小于它的根节点的值；
           //若它的右子树不空，则右子树上所有节点的值均大于它的根节点的值；
           //它的左、右子树也分别为二叉搜索树。
           //由这样的性质我们可以发现，二叉搜索树的中序遍历是一个单调递增的有序序列。
           // 如果我们反序地中序遍历该二叉搜索树，即可得到一个单调递减的有序序列。

           /**
            * 本题中要求我们将每个节点的值修改为原来的节点值加上所有大于它的节点值之和。这样我们只需要反序中序遍历该二叉搜索树，
            * 记录过程中的节点值之和，并不断更新当前遍历到的节点的节点值，即可得到题目要求的累加树
            * 时间复杂度：O(n)，其中 n 是二叉搜索树的节点数。每一个节点恰好被遍历一次
            * 空间复杂度：O(n)，为递归过程中栈的开销，平均情况下为 O(logn)，最坏情况下树呈现链状，为 O(n)。
            */
           int sum01 = 0;
           public TreeNode convetBST01(TreeNode root) {
               if(root != null) {
                   convertBST(root.right);
                   sum += root.val;
                   root.val = sum;
                   convertBST(root.left);
               }
               return root;
           }


           // TODO: 31/12/2022 Morris遍历

           /**
            * 有一种巧妙的方法可以在线性时间内，只占用常数空间来实现中序遍历。这种方法由 J. H. Morris 在 1979 年的论文「Traversing Binary Trees Simply and Cheaply」中首次提出，因此被称为 Morris 遍历。
            * Morris 遍历的核心思想是利用树的大量空闲指针，实现空间开销的极限缩减。其反序中序遍历规则总结如下：
            * 1.如果当前节点的右子节点为空，处理当前节点，并遍历当前节点的左子节点；
            * 2.如果当前节点的右子节点不为空，找到当前节点右子树的最左节点（该节点为当前节点中序遍历的前驱节点）；
            *   如果最左节点的左指针为空，将最左节点的左指针指向当前节点，遍历当前节点的右子节点；
            *   如果最左节点的左指针不为空，将最左节点的左指针重新置为空（恢复树的原状），处理当前节点，并将当前节点置为其左节点；
            * 3.重复步骤 1 和步骤 2，直到遍历结束。
            * 这样我们利用 Morris 遍历的方法，反序中序遍历该二叉搜索树，即可实现线性时间与常数空间的遍历
            * 时间复杂度：O(n)，其中 n 是二叉搜索树的节点数。没有左子树的节点只被访问一次，有左子树的节点被访问两次。
            * 空间复杂度：O(1)。只操作已经存在的指针（树的空闲指针），因此只需要常数的额外空间。
            * @param root
            * @return
            */
           public TreeNode convertBST02(TreeNode root) {


               int sum = 0;
               TreeNode node = root;

               while(node != null) {
                   if(node.right == null) {
                       sum += node.val;
                       node.val = sum;
                       node = node.left;
                   } else {
                       TreeNode succ = getSuccessor(node);
                       if(succ.left == null) {
                           succ.left = node;
                           node = node.right;
                       }
                       else {
                           succ.left = null;
                           sum += node.val;
                           node.val = sum;
                           node = node.left;
                       }
                   }
               }
               return root;
           }

            public TreeNode getSuccessor(TreeNode node) {
               TreeNode succ = node.right;
               while(succ.left != null && succ.left != node) succ = succ.left;
               return succ;
            }
       }

}
