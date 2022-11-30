package offer_55_1;

public class TreeNode {
    int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) { val = x; }


    class Solution {
            boolean balanced = true;
            public boolean isBalanced(TreeNode root) {
                depth(root);
                return balanced;
            }

            public int depth(TreeNode root) {
                if(root == null) return 0;
                int left = depth(root.left);
                int right = depth(root.right);
                if( left == -1 || right == -1 || Math.abs(left - right) > 1) {
                    balanced = false;
                    return -1;
                }
                return Math.max(left, right) + 1;

//                if(node == null || !balanced) return 0;
//                int left = deepTree(node.left);
//                int right = deepTree(node.right);
//                if(Math.abs(left - right) > 1) balanced = false;
//                return Math.max(left, right) + 1;
            }


        /**
         * 自顶向下
         * @param root
         * @return
         */
            public boolean isBalanced1(TreeNode root) {
                if(root == null) return true;
                return Math.abs(height(root.left) - height(root.right)) <= 1 && isBalanced1(root.left) && isBalanced1(root.right);
            }

            public int height(TreeNode root) {
                if(root == null) return 0;
                return Math.max(height(root.left), height(root.right) + 1);
            }


            public boolean isBalanced2(TreeNode root) {
                return height2(root) >= 0;
            }
            public int height2(TreeNode root) {
                if(root == null) return 0;
                int leftHeight = height2(root.left);
                int rightHeight = height2(root.right);
                if(leftHeight == -1 || rightHeight == -1 || Math.abs(leftHeight - rightHeight) > 1) return -1;
                else return Math.max(leftHeight, rightHeight) + 1;
            }

















    }
}
