package sum_Root_To_Leaf_Numbers_129;

import java.util.ArrayDeque;
import java.util.LinkedList;
import java.util.Queue;

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
            * 不会
            *
            * @param root
            * @return
            */
           public int sumNumbers(TreeNode root) {
               if (root == null) return 0;
               int reslut = 0;
               ArrayDeque<TreeNode> stk = new ArrayDeque<>();
               TreeNode node = root;
               int path = 0;
               while (!stk.isEmpty() || node != null) {
                   while (node != null) {
                       path = path * 10 + node.val;
                       stk.push(node);
                       node = node.left;
                   }
                   node = stk.pop();
                   if (node.right == null) reslut += path;
                   node = node.right;
                   if (node != null) path /= 10;

               }
               return reslut;
           }


           /**
            * dfs
            * 深度优先搜索是很直观的做法。从根节点开始，遍历每个节点，如果遇到叶子节点，则将叶子节点对应的数字加到数字之和。如果当前节点不是叶子节点，
            * 则计算其子节点对应的数字，然后对子节点递归遍历。
            * 间复杂度：O(n)，其中 n 是二叉树的节点个数。对每个节点访问一次。
            * 空间复杂度：O(n)，其中 n 是二叉树的节点个数。空间复杂度主要取决于递归调用的栈空间，递归栈的深度等于二叉树的高度，
            * 最坏情况下，二叉树的高度等于节点个数，空间复杂度为 O(n)。
            *
            * @param root
            * @return
            */
           public int sumNumbers1(TreeNode root) {
               return dfs1(root, 0);
           }

           public int dfs1(TreeNode root, int preSum) {
               if (root == null) return 0;
               int sum = preSum * 10 + root.val;
               if (root.left == null && root.right == null) return sum;
               else return dfs1(root.left, sum) + dfs1(root.right, sum);
           }

           /**
            * bfs
            * 使用广度优先搜索，需要维护两个队列，分别存储节点和节点对应的数字。
            * 初始时，将根节点和根节点的值分别加入两个队列。每次从两个队列分别取出一个节点和一个数字，进行如下操作：
            * 如果当前节点是叶子节点，则将该节点对应的数字加到数字之和；
            * 如果当前节点不是叶子节点，则获得当前节点的非空子节点，并根据当前节点对应的数字和子节点的值计算子节点对应的数字，然后将子节点和子节点对应的数字分别加入两个队列。
            * 搜索结束后，即可得到所有叶子节点对应的数字之和。
            * 时间复杂度：O(n)，其中 n 是二叉树的节点个数。对每个节点访问一次。
            * 空间复杂度：O(n)，其中 n 是二叉树的节点个数。空间复杂度主要取决于队列，每个队列中的元素个数不会超过 n。

            * @param root
            * @return
            */
           public int sumNumbers2(TreeNode root) {
               if (root == null) return 0;
               int sum = 0;
               Queue<TreeNode> nodeQueue = new LinkedList<TreeNode>();
               Queue<Integer> numQueue = new LinkedList<Integer>();
               nodeQueue.offer(root);
               numQueue.offer(root.val);
               while(!nodeQueue.isEmpty()) {
                   TreeNode node = nodeQueue.poll();
                   int num = numQueue.poll();
                   TreeNode left = node.left, right = node.right;
                   if(left == null && right == null) sum += num;
                   else {
                       if(left != null) {
                           nodeQueue.offer(left);
                           numQueue.offer(num * 10 + left.val);
                       }
                       if(right != null) {
                           nodeQueue.offer(right);
                           numQueue.offer(num * 10 + root.val);
                       }
                   }
               }
               return sum;
           }
       }
}
