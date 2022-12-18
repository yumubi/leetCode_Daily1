package longest_Common_Ancestor_Of_A_BST_235;

import java.util.ArrayList;
import java.util.List;

public class TreeNode {

    int val;
       TreeNode left;
       TreeNode right;
       TreeNode(int x) { val = x;
       }

    class Solution {
        public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
            if(p.val > q.val) return  lowestCommonAncestor(root, q, p);
            if(q.val < root.val) return lowestCommonAncestor(root.left, p, q);
            else if(p.val > root.val) return lowestCommonAncestor(root.right, p, q);
            else return root;
        }


        /**
         * 一次遍历
         * 我们从根节点开始遍历；
         * 如果当前节点的值大于 p 和 q 的值，说明 p 和 q 应该在当前节点的左子树，因此将当前节点移动到它的左子节点；
         * 如果当前节点的值小于 p 和 q 的值，说明 p 和 q 应该在当前节点的右子树，因此将当前节点移动到它的右子节点；
         * 如果当前节点的值不满足上述两条要求，那么说明当前节点就是「分岔点」。此时，p 和 q 要么在当前节点的不同的子树中，要么其中一个就是当前节点。
         * 可以发现，如果我们将这两个节点放在一起遍历，我们就省去了存储路径需要的空间。
         *
         * 时间复杂度：O(n)，其中 n 是给定的二叉搜索树中的节点个数。分析思路与方法一相同。
         * 空间复杂度：O(1)。
         *
         * @param root
         * @param p
         * @param q
         * @return
         */
        public TreeNode lowestCommonAncestor1(TreeNode root, TreeNode p, TreeNode q) {
            TreeNode ancestor = root;
            while(true) {
                if(p.val < ancestor.val && q.val < ancestor.val) ancestor = ancestor.left;
                else if(p.val > ancestor.val && q.val > ancestor.val) ancestor = ancestor.right;
                else break;
            }
            return ancestor;
        }


        /**
         * 两次遍历
         * 时间复杂度：O(n)，其中 n 是给定的二叉搜索树中的节点个数。上述代码需要的时间与节点 p 和 q 在树中的深度线性相关，
         * 而在最坏的情况下，树呈现链式结构，p 和 q 一个是树的唯一叶子结点，一个是该叶子结点的父节点，此时时间复杂度为 Θ(n)。
         * 空间复杂度：O(n)，我们需要存储根节点到 p 和 q 的路径。和上面的分析方法相同，在最坏的情况下，路径的长度为 Θ(n)，因此需要 Θ(n) 的空间。
         * @param root
         * @param p
         * @param q
         * @return
         */
        public TreeNode lowestCommonAncestor2(TreeNode root, TreeNode p, TreeNode q) {
            List<TreeNode> path_p = getPath(root, p);
            List<TreeNode> path_q = getPath(root, q);
            TreeNode ancestor = null;
            for(int i = 0; i < path_p.size() && i < path_q.size() ; ++i) {
                if(path_p.get(i) == path_q.get(i))
                ancestor = path_p.get(i);
                else break;
            }
            return ancestor;
        }

        public List<TreeNode> getPath(TreeNode root, TreeNode target) {
            List<TreeNode> path = new ArrayList<TreeNode>();
            TreeNode node = root;
            while(node != target) {
                path.add(node);
                if(target.val < node.val) node = node.left;
                else node = node.right;
            }
            path.add(node);
            return path;
        }



        public TreeNode lowestCommonAncestor3(TreeNode root, TreeNode p, TreeNode q) {
            //如果根节点和p,q的差相乘是正数，说明这两个差值要么都是正数要么都是负数，也就是说
            //他们肯定都位于根节点的同一侧，就继续往下找
            while ((root.val - p.val) * (root.val - q.val) > 0)
                root = p.val < root.val ? root.left : root.right;
            //如果相乘的结果是负数，说明p和q位于根节点的两侧，如果等于0，说明至少有一个就是根节点
            return root;
        }
//
//        public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
//            //如果小于等于0，说明p和q位于root的两侧，直接返回即可
//            if ((root.val - p.val) * (root.val - q.val) <= 0)
//                return root;
//            //否则，p和q位于root的同一侧，就继续往下找
//            return lowestCommonAncestor(p.val < root.val ? root.left : root.right, p, q);
//        }

        /**
         * 一般二叉树的解法
         * @param cur
         * @param p
         * @param q
         * @return
         */
        public TreeNode lowestCommonAncestor4(TreeNode cur, TreeNode p, TreeNode q) {
            if(cur == null || cur == p || cur == q) return cur;
            TreeNode left = lowestCommonAncestor(cur.left, p, q);
            TreeNode right = lowestCommonAncestor(cur.right, p, q);
            //如果left为空，说明这两个节点在cur节点的右子树上，我们只需要返回右子树查找的结果杰克
            if(left == null) return right;
            if(right == null) return left;
            //如果left和right都不为空，说明这两个节点一个在cur的左子树上一个在cur的右子树上。我们只需要返回cur节点即可
            return cur;
        }





    }




}
