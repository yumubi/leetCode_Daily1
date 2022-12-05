package validate_BST_98;

import java.util.Deque;
import java.util.LinkedList;

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
           boolean isValid = true;
           long prev = Long.MIN_VALUE;
           public boolean isValidBST(TreeNode root) {
               inorder(root);
               return isValid;
           }

           public void inorder(TreeNode root) {
               if(!isValid) return;
               if(root == null) return;
               inorder(root.left);
               if(root.val <= prev) {
                   isValid = false;
                   return;
               }
               prev = root.val;
               inorder(root.right);
           }


           /**
            * 中序遍历
            * 在中序遍历的时候实时检查当前节点的值是否大于前一个中序遍历到的节点的值
            * @param root
            * @return
            */
           public boolean isValidBST1(TreeNode root) {
               Deque<TreeNode> stack = new LinkedList<TreeNode>();
               double inorder = - Double.MAX_VALUE;
               while (!stack.isEmpty() || root != null) {
                   while(root != null) {
                       stack.push(root);
                       root = root.left;
                   }
                   root = stack.pop();
                   if(root.val <= inorder) return false;
                   inorder = root.val;
                   root = root.right;
               }
               return true;
           }


           /**
            * 递归函数 helper(root, lower, upper) 来递归判断，函数表示考虑以 root 为根的子树，判断子树中所有节点的值是否都在 (l,r)(l,r) 的范围内（注意是开区间）。
            * 如果 root 节点的值 val 不在 (l,r)的范围内说明不满足条件直接返回，否则我们要继续递归调用检查它的左右子树是否满足，如果都满足才说明这是一棵二叉搜索树。
            * 那么根据二叉搜索树的性质，在递归调用左子树时，我们需要把上界 upper 改为 root.val，即调用 helper(root.left, lower, root.val)，
            * 因为左子树里所有节点的值均小于它的根节点的值。同理递归调用右子树时，我们需要把下界 lower 改为 root.val，即调用 helper(root.right, root.val, upper)。
            * 函数递归调用的入口为 helper(root, -inf, +inf)， inf 表示一个无穷大的值
            * @param root
            * @return
            */
           public boolean isValidBST2(TreeNode root) {
               return isValidBST2(root, Long.MIN_VALUE, Long.MIN_VALUE);
           }
           public boolean isValidBST2(TreeNode node, long lower, long upper) {
               if(node == null) return true;
               if(node.val <= lower || node.val >= upper) return false;
               return isValidBST2(node.left, lower, node.val) && isValidBST2(node.right, node.val, upper);
           }

           /**
            * 中序递归
            */
           TreeNode pre;
           public boolean isValidBST3(TreeNode root) {
               if(root == null) return true;
               if(!isValidBST3(root.left)) return false;
               if(pre != null && pre.val >= root.val) return false;
               pre = root;
               if(!isValidBST3(root.right)) return false;
               return true;
           }







       }
}
