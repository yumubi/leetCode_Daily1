package deepest_Leaves_Sum_1302;

import java.util.*;

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
           public int deepestLeaveSum(TreeNode root) {
               Deque<TreeNode> queue = new ArrayDeque<>();
               queue.addLast(root);
               List<TreeNode> lastLevel = new ArrayList<>();
               int sum = 0;
               while (!queue.isEmpty()) {
                   int sz = queue.size();
                   for(int i = 0; i < sz; i++) {
                       TreeNode node = queue.pollFirst();
                       lastLevel.add(node);
                       if(node.left != null) queue.addLast(node.left);
                       if(node.right != null) queue.addLast(node.right);
                   }
                   if(queue.size() != 0) lastLevel.clear();
               }
               for (TreeNode node : lastLevel) {
                   sum += node.val;
               }
               return sum;
           }


           /**
            * dfs
            * 由于层数最深的节点一定是叶节点，因此只要找到所有层数最深的节点并计算节点值之和即可。
            * 可以使用深度优先搜索实现。从根节点开始遍历整个二叉树，遍历每个节点时需要记录该节点的层数
            * 规定根节点在第 0 层。遍历过程中维护最大层数与最深节点之和。
            * 对于每个非空节点，执行如下操作。
            * 1.判断当前节点的层数与最大层数的关系：
            *       如果当前节点的层数大于最大层数，则之前遍历到的节点都不是层数最深的节点，因此用当前节点的层数更新最大层数，并将最深节点之和更新为当前节点值；
            *       如果当前节点的层数等于最大层数，则将当前节点值加到最深节点之和。
            * 2.对当前节点的左右子节点继续深度优先搜索。
            * 遍历结束之后，即可得到层数最深叶子节点的和
            * 时间复杂度：O(n)，其中 n 是二叉树的节点数。深度优先搜索需要遍历每个节点一次。
            * 空间复杂度：O(n)，其中 n 是二叉树的节点数。空间复杂度主要取决于递归调用栈的深度，为二叉树的深度，最坏情况下二叉树的深度是 O(n)。
            */
           int maxLevel = -1;
           int sum = 0;
           public int deepestLevelSum01(TreeNode root) {
               dfs(root, 0);
               return sum;
           }
           public void dfs(TreeNode node, int level) {
               if(node == null) return;
               if(level > maxLevel) {
                   maxLevel = level;
                   sum = node.val;
               } else if(level == maxLevel) sum += node.val;
               dfs(node.left, level + 1);
               dfs(node.right, level + 1);
           }

           /**
            * 计算最深节点之和也可以使用广度优先搜索实现。使用广度优先搜索时，对二叉树层序遍历，此时不需要维护最大层数，
            * 只需要确保每一轮遍历的节点是同一层的全部节点，则最后一轮遍历的节点是全部最深节点。
            * 初始时，将根节点加入队列，此时队列中只有一个节点，是同一层的全部节点。每一轮遍历时，
            * 首先得到队列中的节点个数 size，从队列中取出 size 个节点，则这 size 个节点是同一层的全部节点，记为第 x 层。
            * 遍历时，第 x层的每个节点的子节点都在第 x+1 层，将子节点加入队列，则该轮遍历结束之后，
            * 第 x 层的节点全部从队列中取出，第x+1 层的节点全部加入队列，队列中的节点是同一层的全部节点。因此该方法可以确保每一轮遍历的节点是同一层的全部节点。
            * 遍历过程中，分别计算每一层的节点之和，则遍历结束时的节点之和即为层数最深叶子节点的和。
            * 时间复杂度：O(n)，其中 n 是二叉树的节点数。广度优先搜索需要遍历每个节点一次。
            * 空间复杂度：O(n)，其中 n 是二叉树的节点数。空间复杂度主要取决于队列空间，队列中的节点个数不超过 n 个。
            * @param root
            * @return
            */
           public int deepestLevelSum02(TreeNode root) {
               int sum = 0;
               Queue<TreeNode> queue = new ArrayDeque<TreeNode>();
               queue.offer(root);
               while(!queue.isEmpty()) {
                   sum = 0;
                   int size = queue.size();
                   for(int i = 0; i < size; i++) {
                       TreeNode node = queue.poll();
                       sum += node.val;
                       if(node.left != null) queue.offer(node.left);
                       if(node.right != null) queue.offer(node.right);
                   }
               }
               return sum;
           }

           /**
            * bfs
            * 使用 BFS 进行树的遍历，处理过程中记录最大深度 depth 以及使用哈希表记录每层元素和。
            * 时间复杂度：O(n)
            * 空间复杂度：O(n)
            * @param root
            * @return
            */
           public int deepestLevelSum03(TreeNode root) {
               Map<Integer, Integer> map = new HashMap<>();
               Deque<TreeNode> d = new ArrayDeque<>();
               d.addLast(root);
               int depth = 0;
               while (!d.isEmpty()) {
                   int sz = d.size();
                   while (sz-- > 0) {
                       TreeNode node = d.pollFirst();
                       map.put(depth, map.getOrDefault(depth, 0) + node.val);
                       if(node.left != null) d.addLast(node.left);
                       if(node.right != null) d.addLast(node.right);
                   }
                   depth++;
               }
               return map.get(depth - 1);
           }


           /**
            * dfs
            * 使用 DFS 进行树的遍历，处理过程中记录最大深度 depth 以及使用哈希表记录每层元素和。
            *  时间复杂度：O(n)
            *  空间复杂度：O(n)
            */
           Map<Integer, Integer> map = new HashMap<>();
           int max;
           public int deepestLeavelSum04(TreeNode root) {
               dfs04(root, 0);
               return map.get(max);
           }
           void dfs04(TreeNode root, int depth) {
               if(root == null) return;
               max = Math.max(max, depth);
               map.put(depth, map.getOrDefault(depth, 0) + root.val);
               dfs04(root.left, depth + 1);
               dfs04(root.right, depth + 1);
           }


       }


}
