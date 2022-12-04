package faced_17_12;

import java.util.Deque;
import java.util.LinkedList;

public class TreeNode {
    int val;
       TreeNode left;
       TreeNode right;
       TreeNode(int x) { val = x; }


    class Solution {
        TreeNode dummyRoot = new TreeNode(-1);
        TreeNode node = dummyRoot;
           public TreeNode convertBiNode(TreeNode root) {
                dfs(root);
                return dummyRoot.right;
           }

           public void dfs(TreeNode root) {
               if(root == null) return;
               dfs(root.left);
               node.right = root;
               root.left = null;
               node = node.right;
               dfs(root.right);
           }

        /**
         * 递归
         *递归之后的prev指针要返回，因为JAVA中是没有引用传递的，左子树递归回来之后，当前的prev指针没有发生改变，
         * 还是外部传进来的那个哨兵节点，这个时候一旦进入右子树，之前的prev.right指针将会被重置。也就是说，root的左子树操作全部失效了。
         * @param root
         * @return
         */
        public TreeNode convertBiNode1(TreeNode root) {
               TreeNode head = new TreeNode(0);//单链表的头指针的哨兵
               inorder(root, head);
               return head.right;
           }
           private TreeNode inorder(TreeNode root, TreeNode prev) {
               if(root != null) {
                   prev = inorder(root.left, prev);
                   root.left = null;
                   prev.right = root;
                   prev = root;
                   prev = inorder(root.right, prev);
               }
               return prev;
           }

        /**
         * BST中序遍历是有序的，我们只要对树进行中序遍历即可，利用一个前驱节点prev，记录上一个被处理的节点，
         * 当前节点被遍历到的时候，将prev.right指向当前节点node，然后node.left置空，prev指针后移到node,最后node进入右子树即可
         * @param root
         * @return
         */
           public TreeNode convertBiNode2(TreeNode root) {
            TreeNode head = new TreeNode(0);
            TreeNode prev = head;
            TreeNode node = root;
               Deque<TreeNode> stack = new LinkedList<>();
               while(node != null || !stack.isEmpty()) {
                   if(node != null) {
                       stack.push(node);
                       node = node.left;
                   } else {
                       node = stack.pop();
                       node.left = null;
                       prev.right = node;
                       prev = node;
                       node = node.right;
                   }
               }
               return head.right;
           }




    }
}
