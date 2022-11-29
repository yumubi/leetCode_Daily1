package offer_44;


import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

public class TreeNode {
    int val;
       TreeNode left;
       TreeNode right;
       TreeNode(int x) { val = x; }

    class Solution {
        /**
         * ac
         * @param root
         * @return
         */
           public int numColor(TreeNode root) {
               Set<Integer> set = new HashSet<>();
               dfs(root, set);
               return set.size();
           }
           public void dfs(TreeNode root, Set<Integer> set) {
               if(root == null) return;
               set.add(root.val);
               dfs(root.left, set);
               dfs(root.right, set);
           }
    }


    public int numColor1(TreeNode root) {
           Set<Integer> color = new HashSet<>();
           Deque<TreeNode> deque = new LinkedList<>();
           deque.offer(root);

           while(!deque.isEmpty()) {
               int size = deque.size();
               for(int i = 0; i < size; i++) {//while(!deque.isEmpty() )
                   TreeNode node = deque.poll();
                   color.add(node.val);
                   if(node.left != null) deque.offer(node.left);
                   if(node.right != null) deque.offer(node.right);
               }
           }
           return color.size();
    }

    /**
     * dfs+Set
     */
    Set<Integer> color = new HashSet<>();
    public int numberColor2(TreeNode root) {
           dfs2(root);
           return color.size();
    }
    public void dfs2(TreeNode root) {
        if(root == null) return;
        color.add(root.val);
        dfs2(root.left);
        dfs2(root.right);

    }


}
