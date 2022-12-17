package binary_Tree_Right_Side_View_199;

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
           /**
            * 越界了
            */
           List<List<Integer>> ret = new LinkedList<List<Integer>>();
           public List<Integer> rightSideView(TreeNode root) {
               if(root == null) return new ArrayList<>();
               bfs(root, 0);
              List<Integer> res = new ArrayList<>();
              for(List<Integer> list : ret) {
                  res.add(list.get(list.size() - 1));
              }
              return res;
           }

           public void bfs(TreeNode root, int depth) {
               if(root == null) return;
               if(depth + 1 > ret.size()) {
                   List<Integer> res = new ArrayList<>();
                   ret.add(res);
               }
               ret.get(depth).set(0, root.val);
               bfs(root.left, depth + 1);
               bfs(root.right, depth + 1);
           }


           /**
            * 我们对树进行深度优先搜索，在搜索过程中，我们总是先访问右子树。那么对于每一层来说，我们在这层见到的第一个结点一定是最右边的结点。
            * 存储在每个深度访问的第一个结点，一旦我们知道了树的层数，就可以得到最终的结果数组。
            * 时间复杂度 :O(n)。深度优先搜索最多访问每个结点一次，因此是线性复杂度。
            * 空间复杂度 : O(n)。最坏情况下，栈内会包含接近树高度的结点数量，占用 O(n) 的空间。
            * @param root
            * @return
            */
           public List<Integer> rightSideView1(TreeNode root) {
               Map<Integer, Integer> rightmostValueAtDepth1 = new HashMap<Integer, Integer>();
               int max_depth = -1;
               Deque<TreeNode> nodeStack  = new ArrayDeque<TreeNode>();
               Deque<Integer> depthStack = new ArrayDeque<Integer>();

               nodeStack.push(root);
               depthStack.push(0);

               while (!nodeStack.isEmpty()) {
                   TreeNode node = nodeStack.pop();
                   int depth = depthStack.pop();

                   if(node != null) {
                       //维护二叉树的最大深度
                       max_depth = Math.max(max_depth, depth);
                       //如果不存在对应深度的节点才加入
                       if(!rightmostValueAtDepth1.containsKey(depth)) rightmostValueAtDepth1.put(depth, node.val);
                       nodeStack.push(node.left);
                       nodeStack.push(node.right);
                       depthStack.push(depth + 1);
                       depthStack.push(depth + 1);
                   }
               }

               List<Integer> rightView = new ArrayList<>();
               for(int depth = 0; depth <= max_depth; depth++) rightView.add(rightmostValueAtDepth1.get(depth));
               return rightView;
           }


           /**
            * bfs
            * 时间复杂度 : O(n)。 每个节点最多进队列一次，出队列一次，因此广度优先搜索的复杂度为线性。
            * 空间复杂度 : O(n)。每个节点最多进队列一次，所以队列长度最大不不超过 n，所以这里的空间代价为 O(n)。

            * @param root
            * @return
            */
           public List<Integer> rightSideView2(TreeNode root) {
               Map<Integer, Integer> rightmostValueAtDepth = new HashMap<Integer, Integer>();
               int max_depth = -1;
               Queue<TreeNode> nodeQueue = new ArrayDeque<TreeNode>();
               Queue<Integer> depthQueue = new ArrayDeque<Integer>();
               nodeQueue.add(root);
               depthQueue.add(0);

               while(!nodeQueue.isEmpty()) {
                   TreeNode node = nodeQueue.remove();
                   int depth = depthQueue.remove();

                   if(node != null) {
                       max_depth = Math.max(max_depth, depth);
//由于每一层最后一个访问到的节点才是我们想要的答案，因此不断更新对应深度的信息即可
                       rightmostValueAtDepth.put(depth, node.val);

                       nodeQueue.add(node.left);
                       nodeQueue.add(node.right);
                       depthQueue.add(depth + 1);
                       depthQueue.add(depth + 1);
                   }
               }

               List<Integer> rightView = new ArrayList<Integer>();
               for(int depth = 0;  depth <= max_depth; depth++)
                   rightView.add(rightmostValueAtDepth.get(depth));
               return rightView;
           }


           /**
            * dfs
            */
           List<Integer> res = new ArrayList<>();
           public List<Integer> rightSideView3(TreeNode root) {
               dfs4(root, 0);
               return res;
           }
           private void dfs4(TreeNode root, int depth) {
               if(root == null) return;
               //先访问当前节点，在递归的访问右子树和左子树
               if(depth == res.size()) res.add(root.val);//如果当前节点所在深度还没有出现在res里，说明该深度下当前节点是第一个被访问的节点，因此将当前节点加入到res
               depth++;
               dfs4(root.right, depth);
               dfs4(root.left, depth);
           }


           public List<Integer> rightSideView4(TreeNode root){
               List<Integer> res = new ArrayList<>();
               if(root == null) return res;
               Queue<TreeNode> queue = new LinkedList<>();
               queue.offer(root);
               while(!queue.isEmpty()) {
                   int size = queue.size();
                   for(int i = 0; i < size; i++) {
                       TreeNode node = queue.poll();
                       if(node.left != null) queue.offer(node.left);
                       if(node.right != null) queue.offer(node.right);
                       if(i == size - 1) res.add(node.val);//将当前层的最后一个节点放入结果列表
                   }
               }
               return res;
           }




           public List<Integer> rightSideView01(TreeNode root){
               List<Integer> res = new ArrayList<>();
               dfs01(root, res, 0);
               return res;
           }
           public void dfs01(TreeNode curr, List<Integer> res, int level) {
               if(curr == null) return;
               if(level == res.size()) res.add(curr.val);
               dfs01(curr.right, res, level + 1);
               dfs01(curr.left, res, level + 1);
           }

           public List<Integer> rightSideView02(TreeNode root){
               List<Integer> res = new ArrayList<>();
               if(root == null) return res;
               Stack<TreeNode> stackNode = new Stack<>();
               Stack<Integer> stackLevel = new Stack<>();
               stackNode.add(root);
               stackLevel.add(0);
               while(!stackNode.empty()) {
                   TreeNode node = stackNode.pop();
                   int level = stackLevel.pop();
                   if(res.size() == level) res.add(node.val);
                   if(node.left != null) {
                       stackNode.push(node.left);
                       stackLevel.push(level + 1);
                   }
                   if(node.right != null) {
                       stackNode.push(node.right);
                       stackLevel.push(level + 1);
                   }
               }
               return res;
           }

           public List<Integer> rightSideView03(TreeNode root) {
               List<Integer> res = new ArrayList<>();
               if(root == null) return res;
               Queue<TreeNode> queue = new LinkedList<>();
               queue.offer(root);
               while(!queue.isEmpty()) {
                   int count = queue.size();
                   while(count-- > 0) {
                       TreeNode curr = queue.poll();
                       if(count == 0) res.add(curr.val);
                       if(curr.left != null) queue.offer(curr.left);
                       if(curr.right != null) queue.offer(curr.right);
                   }
               }
               return res;
           }







       }
}
