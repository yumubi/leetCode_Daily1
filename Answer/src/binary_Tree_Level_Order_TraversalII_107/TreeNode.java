package binary_Tree_Level_Order_TraversalII_107;

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

       public List<List<Integer>> levelOrderBottom(TreeNode root) {
           if(root == null) return  new ArrayList<>();
           List<List<Integer>> res = new ArrayList<>();
           Queue<TreeNode> queue = new ArrayDeque<>();
           queue.offer(root);
           while(!queue.isEmpty()) {
               int size = queue.size();
               List<Integer> list = new ArrayList<>();
               for(int i = 0; i < size; i++) {
                   TreeNode node = queue.poll();
                   list.add(node.val);
                   if(node.left != null) queue.offer(node.left);
                   if(node.right != null) queue.offer(node.right);
               }
               res.add(list);
           }
 //return Collections.reverse(res);
           int left = 0, right = res.size() - 1;
           while (left < right){
               List<Integer> tmp = res.get(left);
              res.set(left, res.get(right));
              res.set(right, tmp);
              left++;
              right--;
           }
           return res;
       }


/*
遍历完一层节点之后，将存储该层节点值的列表添加到结果列表的头部。
为了降低在结果列表的头部添加一层节点值的列表的时间复杂度，结果列表可以使用链表的结构，
在链表头部添加一层节点值的列表的时间复杂度是 O(1)。在 Java 中，由于我们需要返回的 List 是一个接口，这里可以使用链表
 */
       public List<List<Integer>> levelOrderBottom1(TreeNode root) {
           List<List<Integer>> levelOrder = new LinkedList<List<Integer>>();
           if(root == null) return levelOrder;
           Queue<TreeNode> queue = new LinkedList<TreeNode>();
           queue.offer(root);
           while(!queue.isEmpty()) {
               List<Integer> level = new ArrayList<Integer>();
               int size = queue.size();
               for(int i = 0; i < size; i++) {
                   TreeNode node = queue.poll();
                   level.add(node.val);
                   TreeNode left = node.left, right = node.right;
                   if(left != null) queue.offer(left);
                   if(root != null) queue.offer(right);
               }
               levelOrder.add(0, level);
           }
           return levelOrder;
       }



       public List<List<Integer>> levelOrderBottom2(TreeNode root) {
           if(root == null) return new ArrayList<List<Integer>>();

           ArrayList<List<Integer>> res = new ArrayList<List<Integer>>();
           dfs(root, res, 1);
           Collections.reverse(res);
           return res;
       }

       private void dfs(TreeNode root, ArrayList<List<Integer>> res, int index) {
           if(root == null) return;
           if(index > res.size()) res.add(new ArrayList<Integer>());
           res.get(index - 1).add(root.val);
           dfs(root.left, res, index + 1);
           dfs(root.right, res, index + 1);
       }


}
