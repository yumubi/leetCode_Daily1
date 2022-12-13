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
//          Queue<Integer> list = new LinkedList<>();
//          public BSTIterator(TreeNode root) {
//              dfs(root);
//          }
//
//          public int next(){
//             if(!list.isEmpty()) return list.poll();
//             return Integer.MIN_VALUE;
//          }
//
//          public boolean hasNext() {
//                return !list.isEmpty();
//          }
//
//
//          void dfs(TreeNode root) {
//              if(root == null) return;
//              dfs(root.left);
//              list.add(root.val);
//              dfs(root.right);
//          }

//我们可以直接对二叉搜索树做一次完全的递归遍历，获取中序遍历的全部结果并保存在数组中。随后，我们利用得到的数组本身来实现迭代器。
            private int idx;
            private List<Integer> arr;

            public BSTIterator(TreeNode root) {
                idx = 0;
                arr = new ArrayList<Integer>();
                inorderTraversal(root, arr);
            }

            public int next() {
                return arr.get(idx++);
            }
            public boolean hasNext(){
                return idx < arr.size();
            }

            private void inorderTraversal(TreeNode root, List<Integer> arr){
                if(root == null) return;
                inorderTraversal(root.left, arr);
                arr.add(root.val);
                inorderTraversal(root.right, arr);
            }



        }
}
