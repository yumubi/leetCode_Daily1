package binary_Tree_Level_Order_Traversal_102;

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
            * 超时，不知道哪里错了
            * @param root
            * @return
            */
           public List<List<Integer>> levelOrder(TreeNode root) {
               List<List<Integer>> res = new ArrayList<>();
               Queue<TreeNode> queue = new ArrayDeque<>();
               if(root == null) return res;
               queue.offer(root);
               while (!queue.isEmpty()) {
                   int size = queue.size();
                   List<Integer> level = new ArrayList<>();
                   for(int i = 0; i < size; i++) {
                       TreeNode node = queue.poll();
                       level.add(node.val);
                       if(node.left != null) queue.offer(node.left);
                       if(node.right != null) queue.offer(node.right);
                   }
                   res.add(level);
               }
               return res;
           }


           public List<List<Integer>> levelOrder1(TreeNode root) {
               List<List<Integer>> ret = new ArrayList<List<Integer>>();
               if(root == null) return ret;

               Queue<TreeNode> queue = new LinkedList<TreeNode>();
               queue.offer(root);
               while(!queue.isEmpty()) {
                   List<Integer> level = new ArrayList<>();
                   int currentLevelSize = queue.size();
                   for(int i = 1; i <= currentLevelSize; ++i) {
                       TreeNode node = queue.poll();
                       level.add(node.val);
                       if(node.left != null) queue.add(node.left);
                       if(node.right != null) queue.add(node.right);
                   }
                   ret.add(level);
               }
               return ret;
           }


           public List<List<Integer>> levelOrder2(TreeNode root) {
               if(root == null) return new ArrayList<List<Integer>>();

               List<List<Integer>> res = new ArrayList<List<Integer>>();
               dfs(1, root, res);
               return res;
           }

           void dfs(int index, TreeNode root, List<List<Integer>> res) {
               //假设res是[ [1],[2,3] ]， index是3，就再插入一个空list放到res中
               if(res.size() < index) res.add(new ArrayList<Integer>());

               //将当前节点的值加入到res中，index代表当前层，假设index是3，节点值是99
               //res是[ [1],[2,3] [4] ]，加入后res就变为 [ [1],[2,3] [4,99] ]
               res.get(index--).add(root.val);
               //递归的处理左子树，右子树，同时将层数index+1
               if(root.left != null) dfs(index + 1, root.left, res);
               if(root.right != null) dfs(index + 1, root.right, res);
           }




       }
}
