package Maximum_Width_Of_Binary_Tree_662;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

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
           //内存超限
           public int widthOfBinaryTree(TreeNode root) {
               Deque<TreeNode> queue = new ArrayDeque<TreeNode>();
               int maxWidth = -1;
               queue.offer(root);
               while (!queue.isEmpty()) {
                   int size = queue.size();
                   boolean startCnt = false;
                   int left = -1, right = -1;
                   for(int i = 0; i < size; i++) {
                       TreeNode node = queue.poll();
                       if (startCnt == false && node.val != -101) {
                           left = i;
                           startCnt = true;
                       }
                       if (startCnt && node.val != -101) right = i;
                       if (node.left == null && node.right == null) {
                           if (left == -1)
                               continue;
                           else {
                               queue.offer(new TreeNode(-101));
                               queue.offer(new TreeNode(-101));
                           }
                       } else {
                           if (node.left != null) queue.offer(node.left);
                           else queue.offer(new TreeNode(-101));
                           if (node.right != null) queue.offer(node.right);
                           else queue.offer(new TreeNode(-101));
                       }
                   }
                   int width = right - left;
                   if(width > maxWidth) maxWidth = width;
               }
               return maxWidth + 1;
           }


           /**
            * dfs
            * 按照上述方法编号，可以用深度优先搜索来遍历。遍历时如果是先访问左子节点，再访问右子节点，每一层最先访问到的节点会是最左边的节点，即每一层编号的最小值，
            * 需要记录下来进行后续的比较。一次深度优先搜索中，需要当前节点到当前行最左边节点的宽度，以及对子节点进行深度优先搜索，求出最大宽度，并返回最大宽度。
            * 时间复杂度：O(n)，其中 n 是二叉树的节点个数。需要遍历所有节点。
            * 空间复杂度：O(n)。递归的深度最多为 O(n)
            */
           Map<Integer, Integer> levelMin = new HashMap<Integer, Integer>();
           public int widthOfBinaryTree02(TreeNode root) {
                return dfs(root, 1, 1);
           }
           public int dfs(TreeNode node, int depth, int index) {
               if(node == null) return 0;
               levelMin.putIfAbsent(depth, index);//每一层最先访问的节点会是最左边的节点，即每层编号的最小值
               return Math.max(index - levelMin.get(depth) + 1,
                       Math.max(dfs(node.left, depth + 1, index * 2), dfs(node.right, depth + 1, index * 2 + 1)));
           }

       }
}
