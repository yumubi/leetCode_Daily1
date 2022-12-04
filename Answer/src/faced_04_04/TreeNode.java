    package faced_04_04;

    public class TreeNode {
        int val;
       TreeNode left;
       TreeNode right;
        TreeNode(int x) { val = x; }


        class Solution {
            /**
             *ac
             */
            boolean balanced = true;
            public boolean isBalanced(TreeNode root) {
                depth(root);
                return balanced;
            }

            public int depth(TreeNode root) {
                    if(!balanced) return -1;
                    if (root == null) return 0;
                    int left = depth(root.left);
                    int right = depth(root.right);
                    if(Math.abs(left - right) > 1) {
                        balanced = false;
                        return -1;
                    }
                    return left > right ? left + 1 : right + 1;
            }

            /**
             * 递归 自顶向下
             * @param root
             * @return
             */
            public boolean isBalanced1(TreeNode root) {
                if(root == null) return true;
                else {
                    return Math.abs(height(root.left) - height(root.right)) <= 1 && isBalanced1(root.left) && isBalanced1(root.right);
                }
            }

            public int height(TreeNode root) {
                if(root == null) return 0;
                else return Math.max(height(root.left), height(root.right)) + 1;
            }

            /**
             * 递归 自底向上
             * @param root
             * @return
             */
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

            /**
             *
             */
            private static final int UNBALANCED = -1;
            public boolean isBalanced3(TreeNode root) {
                return helper(root) != UNBALANCED;
            }
            public int helper(TreeNode root) {
                if(root == null) return 0;
                int left = helper(root.left);
                if(left == UNBALANCED) return UNBALANCED;
                int right = helper(root.right);
                if(right == UNBALANCED) return UNBALANCED;
                if(Math.abs(left - right) > 1) return UNBALANCED;

                return Math.max(left, right) + 1;
            }






































        }
    }
