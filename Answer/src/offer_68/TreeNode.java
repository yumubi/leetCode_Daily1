package offer_68;

import java.util.ArrayList;
import java.util.List;

public class TreeNode {
           int val;
       TreeNode left;
       TreeNode right;
       TreeNode(int x) { val = x; }

    class Solution {
            public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
                if( (p.val - root.val) * (q.val - root.val) <= 0) return root;
                else {
                    if(p.val < root.val) return lowestCommonAncestor(root.left, p, q);
                    else return lowestCommonAncestor(root.right, p, q);
                }
            }


        /**
         * 两次遍历
         * 注意到题目中给出的是一棵「二叉搜索树」，因此我们可以快速地找出树中的某个节点以及从根节点到该节点的路径，例如我们需要找到节点 p：
         * 我们从根节点开始遍历；
         *      如果当前节点就是 p，那么成功地找到了节点；
         *      如果当前节点的值大于 p 的值，说明 p 应该在当前节点的左子树，因此将当前节点移动到它的左子节点；
         *      如果当前节点的值小于 p 的值，说明 pp应该在当前节点的右子树，因此将当前节点移动到它的右子节点处。
         *对于节点 q 同理。在寻找节点的过程中，我们可以顺便记录经过的节点，这样就得到了从根节点到被寻找节点的路径。
         * 当我们分别得到了从根节点到 p 和 q 的路径之后，我们就可以很方便地找到它们的最近公共祖先了。
         * 显然，p 和 q 的最近公共祖先就是从根节点到它们路径上的「分岔点」，也就是最后一个相同的节点
         * 。因此，如果我们设从根节点到 p 的路径为数组 path_p，从根节点到 q 的路径为数组 \path_q，那么只要找出最大的编号 i，其满足
         * path_p[i]=path_q[i]
         * 那么对应的节点就是「分岔点」，即 p 和 q 的最近公共祖先就是 path_p[i]（或path_q[i]）。
         *时间复杂度：O(n)，其中 n 是给定的二叉搜索树中的节点个数。上述代码需要的时间与节点 p 和 q 在树中的深度线性相关，
         * 而在最坏的情况下，树呈现链式结构，p 和 q 一个是树的唯一叶子结点，一个是该叶子结点的父节点，此时时间复杂度为 Θ(n)。
         * 空间复杂度：O(n)，我们需要存储根节点到 pp 和 qq 的路径。和上面的分析方法相同，在最坏的情况下，路径的长度为 Θ(n)，因此需要 Θ(n) 的空间。
         * @param root
         * @param p
         * @param q
         * @return
         */
            public TreeNode lowestCommonAncestor1(TreeNode root, TreeNode p, TreeNode q) {
                List<TreeNode> path_p = getPath(root, p);
                List<TreeNode> path_q = getPath(root, q);
                TreeNode ancestor = null;
                for(int i = 0; i < path_p.size() && i < path_q.size(); ++i) {
                   if(path_p.get(i) == path_q.get(i)) ancestor = path_p.get(i);
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


        /**
         * 一次遍历
         * @param root
         * @param p
         * @param q
         * @return
         */
            public TreeNode lowestCommonAncestor2(TreeNode root, TreeNode p, TreeNode q) {
                TreeNode ancestor = root;
                while(true) {
                    if(p.val < ancestor.val && q.val < ancestor.val) ancestor = ancestor.left;
                    else if(p.val > ancestor.val && q.val > ancestor.val) ancestor = ancestor.right;
                    else break;
                }
                return ancestor;
            }


        /**
         * 迭代
         * @param root
         * @param p
         * @param q
         * @return
         */
            public TreeNode lowestCommonAncestor3(TreeNode root, TreeNode p, TreeNode q) {
                while (root != null) {
                    if(root.val < p.val && root.val < q.val) root = root.right;
                    else if(root.val > p.val && root.val > q.val) root = root.left;
                    else break;
                }
                return root;
            }

            //优化：若可保证 p.val < q.valp.val<q.val ，则在循环中可减少判断条件。
            public TreeNode lowestCommonAncestor4(TreeNode root, TreeNode p, TreeNode q) {
                if(p.val > q.val) {
                    TreeNode tmp = p;
                    p = q;
                    q = tmp;
                }
                while(root != null) {
                    if(root.val < p.val) root = root.right;
                    else if(root.val > q.val) root = root.left;
                    else break;
                }
                return root;
            }

            public TreeNode lowestCommonAncestor5(TreeNode root, TreeNode p, TreeNode q) {
                if(root.val < p.val && root.val < q.val) return lowestCommonAncestor5(root.right, p, q);
                if(root.val > p.val && root.val > q.val) return lowestCommonAncestor5(root.left, p, q);
                return root;
            }


    }
}
