package binary_Search_Tree_Iterator_173;

import java.util.ArrayList;
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


        class BSTIterator {
          List<Integer> list = new ArrayList<>();
          public BSTIterator(TreeNode root) {
              dfs(root);
          }

          public int next(){

          }

          public boolean hasNext() {

          }


          void dfs(TreeNode root) {
              if(root == null) return;
              dfs(root.left);
              list.add(root.val);
              dfs(root.right);
          }



        }
}
