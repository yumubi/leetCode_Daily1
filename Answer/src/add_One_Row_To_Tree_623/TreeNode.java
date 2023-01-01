package add_One_Row_To_Tree_623;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

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
           public TreeNode addOneRow(TreeNode root, int val, int depth) {
               if(depth == 1) {
                   TreeNode newRoot = new TreeNode(val);
                   newRoot.left = root;
                   return newRoot;
               }
               int Depth = 1;
               Deque<TreeNode> queue = new ArrayDeque<TreeNode>();
               queue.addLast(root);
               while(!queue.isEmpty()) {
                   int size = queue.size();
                   for(int i = 0; i < size; i++) {

                       TreeNode node = queue.pollFirst();
                       if(node.left != null) queue.addLast(node.left);
                       if(node.right != null) queue.addLast(node.right);
                       if(Depth == depth - 1) {
                           TreeNode left = new TreeNode(val);
                           TreeNode right = new TreeNode(val);
                            if(node.left != null) left.left = node.left;
                            if(node.right != null) right.right = node.right;
                           node.left = left;
                           node.right = right;
                       }
                   }
                   Depth++;
               }
                return root;
           }


           /**
            * dfs
            * 当输入 depth 为 1 时，需要创建一个新的 root，并将原 root 作为新 root 的左子节点。当 depth 为 2 时，
            * 需要在root 下新增两个节点 left 和 right 作为 root 的新子节点，
            * 并把原左子节点作为 left 的左子节点，把原右子节点作为 right 的右子节点。当 depth 大于 2 时，需要继续递归往下层搜索，
            * 并将 depth 减去 1，直到搜索到 depth 为 2。
            * 时间复杂度：O(n)，其中 n 为输入的树的节点数。最坏情况下，需要遍历整棵树。
            * 空间复杂度：O(n)，递归的深度最多为 O(n)。
            * @param root
            * @param val
            * @param depth
            * @return
            */
        public TreeNode addOneRow01(TreeNode root, int val, int depth) {
               if(root == null) return null;
               if(depth == 1) return new TreeNode(val, root, null);
               if(depth == 2) {
                   root.left = new TreeNode(val, root.left, null);
                   root.right = new TreeNode(val, null, root.right);
               } else {
                   root.left = addOneRow01(root.left, val, depth -1);
                   root.right = addOneRow01(root.right, val, depth - 1);
               }
               return root;
        }


           /**
            * 与深度优先搜索类似，我们用广度优先搜索找到要加的一行的上一行，然后对这一行的每个节点 node，
            * 都新增两个节点 left 和 right 作为 node 的新子节点，
            * 并把原左子节点作为left 的左子节点，把原右子节点作为 right 的右子节点。
            * 时间复杂度：O(n)，其中 n 为输入的树的节点数。最坏情况下，需要遍历整棵树。
            * 空间复杂度：O(n)，数组空间开销最多为 O(n)。
            * @param root
            * @param val
            * @param depth
            * @return
            */
        public TreeNode addOneRow02(TreeNode root, int val, int depth) {
            if(depth == 1) return new TreeNode(val, root, null);
            List<TreeNode> curLevel = new ArrayList<TreeNode>();
            curLevel.add(root);
            for(int i = 1; i < depth - 1; i++) {
                List<TreeNode> tmpt = new ArrayList<TreeNode>();
                for(TreeNode node : curLevel) {
                    if(node.left != null) tmpt.add(node.left);
                    if(node.right != null) tmpt.add(node.right);
                }
                curLevel = tmpt;
            }
            for(TreeNode node : curLevel) {
                node.left = new TreeNode(val, node.left, null);
                node.right = new TreeNode(val, null, node.right);
            }
            return root;
        }

           /**
            * bfs
            * 根据 BFS 来做，每次 BFS 将整一层进行拓展，同时记录当前深度，当到达第 depth - 1 层，则进行加点操作
            * 时间复杂度：O(n)
            * 空间复杂度：O(n)
            * @param root
            * @param val
            * @param depth
            * @return
            */
        public TreeNode addOneRow03(TreeNode root, int val, int depth) {
            if(depth == 1) return new TreeNode(val, root, null);
            Deque<TreeNode> d= new ArrayDeque<>();
            d.addLast(root);
            int cur = 1;
            while(!d.isEmpty()) {
                int sz = d.size();
                while (sz-- > 0) {
                    TreeNode t = d.pollLast();
                    if(cur == depth - 1) {
                        TreeNode a = new TreeNode(val), b = new TreeNode(val);
                        a.left = t.left;
                        b.right = t.right;
                        t.left = a;
                        t.right = b;
                    } else {
                        if(t.left != null) d.addLast(t.left);
                        if(t.right != null) d.addLast(t.right);
                    }
                }
                cur++;
            }
            return root;
        }

           /**
            * dfs
            * 同理，使用 DFS 也可进行求解，在 DFS 过程中记录当前深度。
            * 时间复杂度：O(n)
            * 空间复杂度：O(n)
            */
        int d, v;
        public TreeNode addOneRow04(TreeNode root, int val, int depth) {
            d = depth;
            v = val;
            if(d == 1) return new TreeNode(val, root, null);
            dfs(root, 1);
            return root;
        }
        void dfs(TreeNode root, int cur) {
            if(root == null) return;
            if(cur == d - 1) {
                TreeNode a = new TreeNode(v), b = new TreeNode(v);
                a.left = root.left;
                b.right = root.right;
                root.left = a;
                root.right = b;
            } else {
                dfs(root.left, cur + 1);
                dfs(root.right, cur + 1);
            }
        }







       }
}
