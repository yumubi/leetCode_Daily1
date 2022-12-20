package lowest_Common_Ancestor_Of_A_Binary_Tree_236;

import java.util.ArrayList;
import java.util.List;

public class TreeNode {
    int val;
       TreeNode left;
       TreeNode right;
       TreeNode(int x) { val = x; }


    class Solution {
        /**
         * 不知道哪里错了
         * @param root
         * @param p
         * @param q
         * @return
         */
        public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
            List< TreeNode> path_p = new ArrayList< TreeNode>();
            List< TreeNode> path_q = new ArrayList< TreeNode>();
            dfs(root, path_p, p);
            dfs(root, path_q, q);
            int len = Math.min(path_p.size(), path_q.size());
            for(int i = 0; i < len; i++) {
                if(path_p.get(i).val != path_q.get(i).val) return path_q.get(i - 1);
            }
            if(path_p.size() == len) return path_q.get(len);
            else return path_p.get(len);
        }
        void dfs(TreeNode root, List< TreeNode> path, TreeNode target) {
            if(root == null) return;
            path.add(root);
            if(root.val == target.val) return;
            dfs(root.left, path, target);
            dfs(root.right, path, target);
        }


        private TreeNode ans;
        public Solution() {
            this.ans = null;
        }
        private boolean dfs01(TreeNode root, TreeNode p, TreeNode q) {
            if(root == null) return false;
            boolean lson = dfs01(root.left, p, q);
            boolean rson = dfs01(root.right, p, q);
            if((lson && rson) || (root.val == p.val || root.val == q.val) || (lson || rson)) {
                ans = root;
            }
            return lson || rson || (root.val == p.val || root.val == q.val);
        }
        public TreeNode lowestCommonAncestor01(TreeNode root, TreeNode p, TreeNode q) {
            this.dfs01(root, p, q);
            return this.ans;
        }
    }
}
