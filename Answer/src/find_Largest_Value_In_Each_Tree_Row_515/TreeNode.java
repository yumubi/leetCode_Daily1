package find_Largest_Value_In_Each_Tree_Row_515;

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
           public List<Integer> largestValues(TreeNode root) {
               if(root == null) return new ArrayList<>();
               Queue<TreeNode> queue = new ArrayDeque<>();
               List<Integer> ret = new ArrayList<>();
               queue.offer(root);
               while(!queue.isEmpty()) {
                   int size = queue.size();
                   int max = Integer.MIN_VALUE;
                   for(int i = 0; i < size; i++) {
                       TreeNode node = queue.poll();
                       if(node.val > max) max = node.val;
                       if(node.left != null) {
                           queue.offer(node.left);
                       }
                       if(node.right != null) {
                           queue.offer(node.right);
                       }
                   }
                   ret.add(max);
               }
               return ret;
           }

           /**
            * dfs
            * 我们用树的「先序遍历」来进行「深度优先搜索」处理，并用 curHeight 来标记遍历到的当前节点的高度。
            * 当遍历到 curHeight 高度的节点就判断是否更新该层节点的最大值。
            * 时间复杂度：O(n)，其中 n 为二叉树节点个数。二叉树的遍历中每个节点会被访问一次且只会被访问一次。
            * 空间复杂度：O(height)。其中 height 表示二叉树的高度。递归函数需要栈空间，
            * 而栈空间取决于递归的深度，因此空间复杂度等价于二叉树的高度。
            * @param root
            * @return
            */
           public List<Integer> largestValues01(TreeNode root) {
               if(root == null) return new ArrayList<Integer>();
               List<Integer> res = new ArrayList<Integer>();
               dfs01(res, root, 0);
               return res;
           }
           public void dfs01(List<Integer> res, TreeNode root, int curHeight) {
               if(curHeight == res.size()) res.add(root.val);
               else res.set(curHeight, Math.max(res.get(curHeight), root.val));
               if(root.left != null) dfs01(res, root.left, curHeight + 1);
               if(root.right != null) dfs01(res, root.right, curHeight + 1);
           }

           /**
            * bfs
            * 我们也可以用「广度优先搜索」的方法来解决这道题目。「广度优先搜索」中的队列里存放的是「当前层的所有节点」。
            * 每次拓展下一层的时候，不同于「广度优先搜索」的每次只从队列里拿出一个节点，我们把当前队列中的全部节点拿出来进行拓展，
            * 这样能保证每次拓展完的时候队列里存放的是下一层的所有节点，即我们是一层一层地进行拓展，然后每一层我们用 maxVal 来标记该层节点的最大值。
            * 当该层全部节点都处理完后，maxVal 就是该层全部节点中的最大值
            * 时间复杂度：O(n)，其中 n为二叉树节点个数，每一个节点仅会进出队列一次。
            * 空间复杂度：O(n)，存储二叉树节点的空间开销。
            * @param root
            * @return
            */
           public List<Integer> largestValues02(TreeNode root) {
               if(root == null) return new ArrayList<Integer>();
               List<Integer> res = new ArrayList<Integer>();
               Queue<TreeNode> queue = new ArrayDeque<TreeNode>();
               queue.offer(root);
               while(!queue.isEmpty()) {
                   int len = queue.size();
                   int maxVal = Integer.MIN_VALUE;
                   while(len > 0) {
                       len--;
                       TreeNode t = queue.poll();
                       maxVal = Math.max(maxVal, t.val);
                       if(t.left != null) queue.offer(t.left);
                       if(t.right != null) queue.offer(t.right);
                   }
                   res.add(maxVal);
               }
               return res;
           }


           /**
            * 用 BFS 进行层序遍历，单次 BFS 逻辑将整一层的元素进行出队，维护当前层的最大值，并将最大值加入答案。
            * 时间复杂度：O(n)
            * 空间复杂度：O(n)
            * @param root
            * @return
            */
           public List<Integer> largestValue03(TreeNode root) {
               List<Integer> ans = new ArrayList<>();
               if(root == null) return ans;
               Deque<TreeNode> d = new ArrayDeque<>();
               d.addLast(root);
               while (!d.isEmpty()) {
                   int sz = d.size(), max = d.peek().val;
                   while(sz-- > 0) {
                       TreeNode node = d.pollFirst();
                       max = Math.max(max, node.val);
                       if(node.left != null) d.addLast(node.left);
                       if(node.right != null) d.addLast(node.right);
                   }
                   ans.add(max);
               }
               return ans;
           }


           /**
            * 同理，可以借助 DFS 进行求解，在 DFS 整棵树时，同时传递一个当前层深度 depth，
            * 使用「哈希表」维护所有深度的最大节点值，同时使用变量 max 记录最大深度。
            * 结束 DFS 后，使用哈希表构造答案
            * 时间复杂度：O(n)
            * 空间复杂度：O(n)
            */
           int max = 0;
           Map<Integer, Integer> map = new HashMap<>();
           public List<Integer> largestValues04(TreeNode root) {
               List<Integer> ans = new ArrayList<>();
               dfs02(root, 1);
               for(int i = 1; i <= max; i++) ans.add(map.get(i));
               return ans;
           }
           void dfs02(TreeNode node, int depth) {
               if(node == null) return;
               max = Math.max(max, depth);
               map.put(depth, Math.max(map.getOrDefault(depth, Integer.MIN_VALUE), node.val));
               dfs02(node.left, depth + 1);
               dfs02(node.right, depth + 1);
           }

           public List<Integer> largestValues05(TreeNode root) {
               List<Integer> res = new ArrayList<>();
               helper(root, res, 1);
               return res;
           }

           //level表示的是第几层，集合res中的第一个数据表示的是
           // 第一层的最大值，第二个数据表示的是第二层的最大值……
           private void helper(TreeNode root, List<Integer> res, int level) {
               if (root == null)
                   return;
               //如果走到下一层了直接加入到集合中
               if (level == res.size() + 1) {
                   res.add(root.val);
               } else {
                   //注意：我们的level是从1开始的，也就是说root
                   // 是第一层，而集合list的下标是从0开始的，
                   // 所以这里level要减1。
                   // Math.max(res.get(level - 1), root.val)表示的
                   // 是遍历到的第level层的root.val值和集合中的第level
                   // 个元素的值哪个大，就要哪个。
                   res.set(level - 1, Math.max(res.get(level - 1), root.val));
               }
               //下面两行是DFS的核心代码
               helper(root.left, res, level + 1);
               helper(root.right, res, level + 1);
           }



           // 单层循环写法
           //通过一层循环实现层序遍历，每处理一个结点都以size是否已减到0来判断当前层是否处理结束，size == 0时，size重新赋为q.size()即可。本质上与双层循环一致。
               public List<Integer> largestValues06(TreeNode root) {
                   if(root == null) return new ArrayList<>();
                   Queue<TreeNode> q = new ArrayDeque<>();
                   List<Integer> res = new ArrayList<>();
                   q.add(root);
                   int size = q.size(), max = Integer.MIN_VALUE;
                   while(!q.isEmpty()){
                       TreeNode cur = q.remove();
                       size--;
                       max = Math.max(max, cur.val);
                       if(cur.left != null) q.add(cur.left);
                       if(cur.right != null) q.add(cur.right);
                       if(size == 0){ // 判断本层是否已完成处理
                           size = q.size();
                           res.add(max);
                           max = Integer.MIN_VALUE;
                       }
                   }
                   return res;
               }


           /**
            * 一个树的问题能用BFS解决，通常也能用DFS解决，反之亦然。本题也很容易用DFS解决，维护类变量res（List）保存每一层（与下标对应）的最大值。
            * 在dfs的递归过程中，遇到一个结点node，就根据其层号信息，更新res对应下标的值为当前值与node.val中的更大者。因此dfs需传入层号信息，
            * 并在递归儿子时将层号加1。dfs方法按如下过程构建：
            * dfs方法形式为void dfs(node, depth)，res在dfs过程中不断更新，dfs无需返回值。
            * 基准情形为node = null，直接return。
            * 在递归dfs之前判断当前res大小是否为层号depth，是则说明首次进入该层，应向res中增加这一层的max，初始max可以设置为当前结点的node.val，
            * 也可以设置为Integer.MIN_VALUE。
            * 对node的左右儿子调用dfs。
            * 确定每一层的最大结点与遍历的顺序无关，因此前序、中序、后序dfs都可以。
            * 时间复杂度：O(n)，n为结点总数，每个结点都会被处理一次。
            * 空间复杂度：O(n)，为递归深度，当树为链状时为最坏情形。
            */
           // 前中后序遍历都可以，即#1, #2, #3三行顺序是任意的
               List<Integer> res;
               public List<Integer> largestValues07(TreeNode root) {
                   this.res = new ArrayList<>();
                   dfs(root, 0);
                   return res;
               }
               private void dfs(TreeNode node, int depth){
                   if(node == null) return;
                   if(res.size() == depth) { // 表示首次进入该层，该层最大值预设为node.val
                       res.add(node.val);
                   }
                   res.set(depth, Math.max(res.get(depth), node.val)); // #1 利用当前结点的层号信息，更新所属层的最大值
                   dfs(node.left, depth + 1); // #2
                   dfs(node.right, depth + 1); // #3
               }





       }



}
