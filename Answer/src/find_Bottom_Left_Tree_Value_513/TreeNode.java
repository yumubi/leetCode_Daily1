package find_Bottom_Left_Tree_Value_513;

import java.util.ArrayDeque;
import java.util.Deque;
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
            int ret = 0;
            int maxDepth = -1;
            public int findBottomLeftValue(TreeNode root) {
                dfs(root, 0);
                return ret;
            }
            public void dfs(TreeNode node, int depth) {
                if(node == null) return;
                dfs(node.left, depth + 1);
                if(depth > maxDepth) {
                    maxDepth = depth;
                    ret = node.val;
                }
                dfs(node.right, depth + 1);
            }


           /**
            * 使用height 记录遍历到的节点的高度，curVal 记录高度在 curHeight 的最左节点的值。在深度优先搜索时，我们先搜索当前节点的左子节点，
            * 再搜索当前节点的右子节点，然后判断当前节点的高度 height 是否大于 curHeight，如果是，那么将 curVal 设置为当前结点的值，curHeight 设置为 height。
            * 因为我们先遍历左子树，然后再遍历右子树，所以对同一高度的所有节点，最左节点肯定是最先被遍历到的。
            * 时间复杂度：O(n)，其中 n 是二叉树的节点数目。需要遍历 n 个节点。
            * 空间复杂度：O(n)。递归栈需要占用 O(n) 的空间。
            */
           int curVal = 0;
            int curHeight = 0;

            public int findBottomLeftValue01(TreeNode root) {
                int curHeight = 0;
                dfs01(root, 0);
                return curVal;
            }
            public void dfs01(TreeNode root, int height) {
                if(root == null) return;
                height++;
                dfs(root.left, height);
                dfs(root.right, height);
                if(height > curHeight) {
                    curHeight = height;
                    curVal = root.val;
                }
            }


           /**
            * bfs
            * 使用广度优先搜索遍历每一层的节点。在遍历一个节点时，需要先把它的非空右子节点放入队列，然后再把它的非空左子节点放入队列，
            * 这样才能保证从右到左遍历每一层的节点。广度优先搜索所遍历的最后一个节点的值就是最底层最左边节点的值。
            * 时间复杂度：O(n)，其中 n 是二叉树的节点数目。
            * 空间复杂度：O(n)。如果二叉树是满完全二叉树，那么队列 q 最多保存 ⌈n/2⌉ 个节点。
            * @param root
            * @return
            */
            public int findBottomLeftValue02(TreeNode root) {
                int ret = 0;
                Queue<TreeNode> queue = new ArrayDeque<TreeNode>();
                queue.offer(root);
                while(!queue.isEmpty()) {
                    TreeNode p = queue.poll();
                    if(p.right != null) {
                        queue.offer(p.right);
                    }
                    if(p.left != null) {
                        queue.offer(p.left);
                    }
                    ret = p.val;
                }
                return ret;
            }

           /**
            * 使用 BFS 进行「层序遍历」，每次用当前层的首个节点来更新 ans，当 BFS 结束后，ans 存储的是最后一层最靠左的节点。
            * 时间复杂度：O(n)
            * 空间复杂度：最坏情况下所有节点都在同一层，复杂度为 O(n)
            * @param root
            * @return
            */
            public int findBottomLeftValue03(TreeNode root) {
                Deque<TreeNode> d = new ArrayDeque<>();
                d.addLast(root);
                int ans = 0;
                while(!d.isEmpty()) {
                    int sz = d.size();
                    ans = d.peek().val;
                    while(sz-- > 0) {
                        TreeNode poll = d.pollLast();
                        if(poll.left != null) d.addLast(poll.left);
                        if(poll.right != null) d.addLast(poll.right);
                    }
                }
                return ans;
            }


           /**
            * 同理，可以使用 DFS 进行树的遍历，每次优先 DFS 当前节点的左子树，每次第一次搜索到当前深度 depth 时，必然是当前深度的最左节点，此时用当前节点值来更新 ans。
            * 时间复杂度：O(n)
            * 空间复杂度：最坏情况下退化成链，递归深度为 nn。复杂度为 O(n)
            */
           int max, ans;
            public int findBottomLeftValue04(TreeNode root) {
                dfs04(root, 1);
                return ans;
            }
            void dfs04(TreeNode root, int depth) {
                if(root == null) return;
                if(depth > max) {
                    max = depth;
                    ans = root.val;
                }
                dfs04(root.left, depth + 1);
                dfs04(root.right, depth + 1);
            }

           /**
            * 用队列存储节点，先进先出
            * 从右往左遍历，也就是在往队列中添加数据时，先添加右子节点，再添加左子节点
            * 当队列为空时，循环结束，最后一个遍历到的节点就是最左边的节点
            * 返回最左边节点的值
            * @param root
            * @return
            */
               public int findBottomLeftValue05(TreeNode root) {
                   Queue<TreeNode> queue = new LinkedList<>();
                   queue.offer(root);
                   TreeNode node = null;
                   while(!queue.isEmpty()){
                       node = queue.poll();
                       // 先右后左
                       if(node.right != null){
                           queue.offer(node.right);
                       }
                       if(node.left != null){
                           queue.offer(node.left);
                       }
                   }
                   return node.val;
               }




       }
}
