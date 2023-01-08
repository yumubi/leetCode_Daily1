package count_Good_Nodes_In_Binary_Tree_1448;

import java.util.ArrayDeque;
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
           int ret = 0;
           public int goodNodes(TreeNode root) {
                dfs(root, Integer.MIN_VALUE);
                return ret;
           }

           void dfs(TreeNode root, int pathMax) {
               if(root == null) return;
               if(root.val >= pathMax) {
                   pathMax = root.val;
                   ret++;
               }
               dfs(root.left, pathMax);
               dfs(root.right, pathMax);
           }

           /**
            * dfs
            * 一棵树的好节点数目，等于左右子树的好节点数目之和，如果根节点是好节点，那么再加1 。 递归的过程中，不断更新max即可。
            * 时间复杂度：O(n)，其中 n 是二叉树的结点数。每个结点都被访问一次。
            * 空间复杂度：O(n)，其中 n 是二叉树的结点数。空间复杂度主要是栈空间，取决于二叉树的高度，最坏情况下二叉树的高度是 O(n)。
            */
           private int nodeNum = 0;
           public int goodNodes01(TreeNode root) {
               recur(root, Integer.MIN_VALUE);
               return nodeNum;
           }
           public void recur(TreeNode node, int max) {
               if(node == null) return;
               if(node.val >= max) {
                   nodeNum++;
                   max = node.val;
               }
               recur(node.left, max);
               recur(node.right, max);
           }


           /**
            * bfs
            * 也可以使用广度优先搜索判断二叉树中的每个结点是否是好结点。从根结点开始遍历全部结点，访问结点的顺序为层数递增的顺序，
            * 遍历过程中，对于每个结点，得到该结点对应的从根结点到该结点的路径上的最大结点值，判断该结点是否是好结点。
            * 为了记录每个结点对应的从根结点到该结点的路径上的最大结点值，需要使用两个队列，分别存储结点和路径上的最大结点值，
            * 这两个队列分别为结点队列和最大值队列，初始时将根结点和根结点值分别入结点队列和最大值队列。
            * 每次将一个结点和一个最大值分别从结点队列和最大值队列出队列，如果当前结点值等于路径上的最大结点值，则将好结点的数目加 1。对
            * 于当前结点的非空子结点，计算从根结点到子结点的路径上的最大结点值，然后将子结点和子结点对应的最大结点值分别入结点队列和最大值队列。
            * 遍历结束之后，即可得到二叉树中好结点的数目。
            * 时间复杂度：(n)，其中 n 是二叉树的结点数。每个结点都被访问一次。
            * 空间复杂度：O(n)，其中 n 是二叉树的结点数。空间复杂度主要是队列空间，队列内元素个数不超过 n。
            * @param root
            * @return
            */
           public int goodNodes02(TreeNode root) {
               int count = 0;
               Queue<TreeNode> nodeQueue = new ArrayDeque<TreeNode>();
               Queue<Integer> maxNode = new ArrayDeque<Integer>();
               nodeQueue.offer(root);
               maxNode.offer(root.val);
               while (!nodeQueue.isEmpty()) {
                   TreeNode node = nodeQueue.poll();
                   int maxValue = maxNode.poll();
                   if(node.val == maxValue) count++;
                   TreeNode left = node.left, right = node.right;
                   if(left != null) {
                       nodeQueue.offer(left);
                       maxNode.offer(Math.max(left.val, maxValue));
                   }
                   if(right != null) {
                       nodeQueue.offer(right);
                       maxNode.offer(Math.max(right.val, maxValue));
                   }
               }
               return count;
           }

       }

}
