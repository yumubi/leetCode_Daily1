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


}
