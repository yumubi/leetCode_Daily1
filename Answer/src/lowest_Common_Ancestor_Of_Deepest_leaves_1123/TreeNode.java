package lowest_Common_Ancestor_Of_Deepest_leaves_1123;

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

           //todo不会

           /**
            * 类似于前序遍历，从根节点开始，分别求左右子树的高度left，和right。
            * 情况1：left=right 那么两边子树的最深高度相同，返回本节点
            * 情况2：left<right 说明最深节点在右子树，直接返回右子树的递归结果
            * 情况2：left>right 说明最深节点在左子树，直接返回右子树的递归结果
            * @param root
            * @return
            */
           public TreeNode lcaDeepestLeaves(TreeNode root) {
                if(root == null) return null;
                int left = dfs(root.left);
                int right = dfs(root.right);
                if(left == right) return root;
                else if(left < right) return lcaDeepestLeaves(root.right);
                return lcaDeepestLeaves(root.left);

           }
           int dfs(TreeNode node) {
               if(node == null) return 0;
               return 1 + Math.max(dfs(node.right), dfs(node.left));
           }


           /**
            *
            * dfs
            * 相当于求最后一层所有叶子节点的公共祖先
            * 求后序遍历，代码结构有点类似于求最大深度，只不过要想办法保存最近的节点，和返回深度
            * 首先定义一个点来保存最近公共祖先，定义一个pre来保存上一次得到的最近公共祖先的深度。
            * 在递归过程中，带一个参数level表示当前遍历到的节点的深度
            *
            * 如果node为空，返回当前深度。
            * 如果不为空，则当前节点的逻辑为：
            * 分别求左子树和右子树的最大深度，left和right
            * 1.left=right 如果相同，并且当前深度大于上一次的最大深度，说明当前节点为最新的最近公共祖先，上一次的没有当前这个深，将当前节点保存在结果中，并将深度pre更新。
            * 2.left不等于right 则直接返左右子树的最大深度
            */
           TreeNode res = null;
           int pre = 0;
           public TreeNode lcaDeepestLeaves02(TreeNode root) {
               dfs02(root, 1);
               return res;
           }
           int dfs02(TreeNode node, int depth) {
               if(node == null) return depth;
               int left = dfs02(node.left, depth + 1);
               int right = dfs02(node.right, depth + 1);
               if(left == right && left >= pre) {
                   res = node;
                   pre = left;
               }
               return Math.max(left, right);
           }

           /**
            * 最大深度 + 最大公共祖先
            * 分析题意需要找到最深叶节点的最近公共祖先，最深的叶节点可能有多个，则这个公共祖先应该是这些叶子节点的公共的祖先，
            * 则可以直接先算出这颗树的树高，然后使用BFS，因为已经知道了树高所以到达最后一层的时候需要将所有的叶子节点全部保存起来，
            * 并且求它们的公共祖先。
            * @return
            */
           public TreeNode lcaDeepestLeaves03(TreeNode root) {
               if(root.left == null && root.right == null) return root;
               int depth = maxDepth(root);
               List<TreeNode> list = new ArrayList<>();
               Queue<TreeNode> q = new ArrayDeque<>();
               q.add(root);
               int dd = 1;
               while (!q.isEmpty()) {
                   int size = q.size();
                   //还没到最后一层，常规的出入队操作
                   if(dd != depth) {
                       for(int i = 0; i < size; i++) {
                           TreeNode curNode = q.poll();
                           if(curNode.left != null) q.add(curNode.left);
                           if(curNode.right != null) q.add(curNode.right);
                       }
                       dd++;
                   } else {//到了最后一层，需要收集叶子节点
                       for(int i = 0; i < size; i++) list.add(q.poll());
                   }
               }
               int size = list.size();
               //求每个叶子节点公共祖先
               TreeNode ans = list.get(0);
               for(int i = 1; i < size; i++) {
                   ans = LCA(root, ans, list.get(i));
               }
               return ans;
           }

           public int maxDepth(TreeNode root) {
               if(root == null) return 0;
               int left = maxDepth(root.left);
               int right = maxDepth(root.right);
               return Math.max(left, right) + 1;
           }
           public TreeNode LCA(TreeNode root, TreeNode p, TreeNode q) {
               if(root == null || root == p || root == q) return root;
               TreeNode left = LCA(root.left, p, q);
               TreeNode right = LCA(root.right, p, q);
               if(left != null && root != null) return root;
               return left != null ? left : right;
           }
       }


    /**
     * bfs由于所有最深结点的深度相同，因此每个最深结点到最近公共祖先的距离相同。只要定位到所有最深结点，即可找到最近公共祖先。
     * 为了定位到所有最深结点，可以使用层序遍历。从根结点开始依次遍历每一层的结点，在层序遍历的过程中需要区分不同结点所在的层，
     * 确保每一轮访问的结点为同一层的全部结点。遍历每一层结点之前首先得到当前层的结点数，即可确保每一轮访问的结点为同一层的全部结点。
     * 层序遍历访问的最后一层结点即为所有最深结点。
     * 定位到所有最深结点之后，从最深结点向根结点移动，即每次从当前结点移动到父结点。
     * 由于每个最深结点到最近公共祖先的距离相同，因此每个最深结点将同时移动到最近公共祖先。
     * 使用哈希集合存储每次移动之后的结点集合，每次移动之后，结点数量一定不变或减少，
     * 当只剩下一个结点时，该结点即为最近公共祖先。
     * 时间复杂度：O(n)，其中 n 是二叉树的结点数。层序遍历访问每个结点一次，需要 O(n) 的时间，
     * 从所有最深结点移动到最近公共祖先的时间不超过 O(n)，因此总时间复杂度是 O(n)。
     * 空间复杂度：O(n)，其中 n 是二叉树的结点数。空间复杂度主要是队列空间和哈希集合，队列内元素个数不超过 n，哈希集合内元素个数不超过 n
     *
     * @param root
     * @return
     */
    public TreeNode lcaDeepestLeaves04(TreeNode root) {
            Map<TreeNode, TreeNode> parentMap = new HashMap<TreeNode, TreeNode>();
            List<TreeNode> deepest = new ArrayList<TreeNode>();
            Queue<TreeNode> queue = new ArrayDeque<TreeNode>();
            queue.offer(root);
            while (!queue.isEmpty()) {
                deepest.clear();
                int size = queue.size();
                for (int i = 0; i < size; i++) {
                    TreeNode node = queue.poll();
                    deepest.add(node);
                    TreeNode left = node.left, right = node.right;
                    if (left != null) {
                        parentMap.put(left, node);
                        queue.offer(left);
                    }
                    if (right != null) {
                        parentMap.put(right, node);
                        queue.offer(right);
                    }
                }
            }
            Set<TreeNode> nodes = new HashSet<TreeNode>(deepest);
            while (nodes.size() > 1) {
                Set<TreeNode> parents = new HashSet<TreeNode>();
                for (TreeNode node : nodes) {
                    parents.add(parentMap.get(node));
                }
                nodes = parents;
            }
            return nodes.iterator().next();
        }





}
