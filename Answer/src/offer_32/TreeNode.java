package offer_32;

import java.util.*;

public class TreeNode {
    int val;
       TreeNode left;
       TreeNode right;
       TreeNode(int x) { val = x; }

    class Solution {
           public List<List<Integer>> levelOrder(TreeNode root) {
               List<List<Integer>> res = new ArrayList<>();
               Queue<TreeNode> queue = new ArrayDeque<TreeNode>();
               if(root == null) return res;
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
               return res;
           }


           public List<List<Integer>> levelOrder1(TreeNode root) {
               List<List<Integer>> ret = new ArrayList<List<Integer>>();
               if(root == null) return ret;

               Queue<TreeNode> queue = new LinkedList<TreeNode>();
               queue.offer(root);
               while(!queue.isEmpty()) {
                   List<Integer> level = new ArrayList<Integer>();
                   int currentLevelSize = queue.size();
                   for(int i = 1; i <= currentLevelSize; ++i) {
                       TreeNode node = queue.poll();
                       level.add(node.val);
                       if(node.left != null) queue.offer(node.left);
                       if(node.right != null) queue.offer(node.right);
                   }
                   ret.add(level);
               }
               return ret;
           }




//           public List<List<Integer>> levelOrder2(TreeNode node) {
//
//           }


    }
}
