package offer_54;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class TreeNode {

    int val;
    TreeNode left;
    TreeNode right;
    TreeNode(int x) { val = x; }


    class Solution {
        List<Integer> res = new ArrayList<>();
        public int kthLargest(TreeNode root, int k) {
            dfs(root);
            int n = res.get(res.size() - k);
            return n;
        }
        public void dfs(TreeNode root) {
            if(root == null) return;
            dfs(root.left);
            res.add(root.val);
            dfs(root.right);
        }

        /**
         * 找到答案直接返回
         */
        int count = 0;
        int ans = 0;
        public int kthLargest1(TreeNode root, int k) {
          dfs1(root, k);
          return ans;
        }
        public void dfs1(TreeNode root, int k) {
          if(root.right != null) dfs1(root.right, k);

          if(++count == k) {
              ans = root.val;
              return;
          }
          if(root.left != null) dfs1(root.left, k);
        }

        /**
         * 迭代
         * @param root
         * @param k
         * @return
         */
        public int kthLargest2(TreeNode root, int k) {
            Stack<TreeNode> stack = new Stack<>();
            TreeNode cur = root;
            int cnt = 0;
            while(cur != null || !stack.isEmpty()) {
                while(cur != null) {
                    stack.push(cur);
                    cur = cur.right;
                }
                cur = stack.pop();
                cnt++;
                if(cnt == k) return cur.val;
                cur = cur.left;
            }
            return 0;
        }
    }


}
