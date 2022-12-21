package lowest_Common_Ancestor_Of_A_Binary_Tree_236;

import java.util.*;

// TODO: 21/12/2022 TARGIN离线算法
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


        /**
         * 递归
         * 我们递归遍历整棵二叉树，定义 f_x表示 x 节点的子树中是否包含 p 节点或 q 节点，如果包含为 true，否则为 false。
         * 那么符合条件的最近公共祖先 x 一定满足如下条件：
         * (flson&&frson) ∣∣ (( x=p ∣∣ x = q)&&(flson ∣∣ frson))
         * 其中 lson 和 rson 分别代表 x 节点的左孩子和右孩子。初看可能会感觉条件判断有点复杂，我们来一条条看
         * flson&&frson
         *说明左子树和右子树均包含 p 节点或 q 节点，如果左子树包含的是 p 节点，那么右子树只能包含 q 节点，
         * 反之亦然，因为 p 节点和 q 节点都是不同且唯一的节点，因此如果满足这个判断条件即可说明 x 就是我们要找的最近公共祖先
         * 。再来看第二条判断条件，这个判断条件即是考虑了 x 恰好是 p 节点或 q节点且它的左子树或右子树有一个包含了另一个节点的情况，
         * 因此如果满足这个判断条件亦可说明 x 就是我们要找的最近公共祖先。
         * 因为我们是自底向上从叶子节点开始更新的，所以在所有满足条件的公共祖先中一定是深度最大的祖先先被访问到，且由于 f_x本身的定义很巧妙，
         * 在找到最近公共祖先 x 以后，f_x按定义被设置为 true ，即假定了这个子树中只有一个 p 节点或 q 节点，因此其他公共祖先不会再被判断为符合
         * 时间复杂度：O(N)，其中 N 是二叉树的节点数。二叉树的所有节点有且只会被访问一次，因此时间复杂度为 O(N)。
         * 空间复杂度：O(N) ，其中 N 是二叉树的节点数。递归调用的栈深度取决于二叉树的高度，二叉树最坏情况下为一条链，此时高度为 N，因此空间复杂度为 O(N)
         * */
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

        /**
         * 存储父节点
         * 们可以用哈希表存储所有节点的父节点，然后我们就可以利用节点的父节点信息从 p 结点开始不断往上跳，并记录已经访问过的节点，
         * 再从 q 节点开始不断往上跳，如果碰到已经访问过的节点，那么这个节点就是我们要找的最近公共祖先。
         * 从根节点开始遍历整棵二叉树，用哈希表记录每个节点的父节点指针。
         * 从 p 节点开始不断往它的祖先移动，并用数据结构记录已经访问过的祖先节点。
         * 同样，我们再从 q 节点开始不断往它的祖先移动，如果有祖先已经被访问过，即意味着这是 p 和 q 的深度最深的公共祖先，即 LCA 节点。
         *
         * 时间复杂度：O(N)，其中 N 是二叉树的节点数。二叉树的所有节点有且只会被访问一次，从 p 和 q 节点往上跳经过的祖先节点个数不会超过 N，因此总的时间复杂度为 O(N)。
         * 空间复杂度：O(N) ，其中 N 是二叉树的节点数。递归调用的栈深度取决于二叉树的高度，二叉树最坏情况下为一条链，
         * 此时高度为 N，因此空间复杂度为 O(N)，哈希表存储每个节点的父节点也需要O(N) 的空间复杂度，因此最后总的空间复杂度为 O(N)。
         */
        Map<Integer, TreeNode> parent = new HashMap<Integer, TreeNode>();
        Set<Integer> visited = new HashSet<Integer>();

        private void dfs02(TreeNode root) {
            if(root.left  != null) {
                parent.put(root.left.val, root);
                dfs02(root.left);
            }
            if(root.right  != null) {
                parent.put(root.right.val, root);
                dfs02(root.right);
            }
        }
        public TreeNode lowestCommonAncestor02(TreeNode root, TreeNode p, TreeNode q) {
            dfs02(root);
                while(p != null) {
                    visited.add(p.val);
                    p = parent.get(p.val);
                }
                while(q != null) {
                    if(visited.contains(q.val)) return q;
                    q = parent.get(q.val);
                }
                return null;
            }

        /**
         * bfs
         * 从两个节点往上找，每个节点都往上走，一直走到根节点，那么根节点到这两个节点的连线肯定有相交的地方，如果是从上往下走，
         * 那么最后一次相交的节点就是他们的最近公共祖先节点。没必要遍历所有的结点，我们一层一层的遍历（也就是BFS），只需要这两个节点都遍历到就可以了
         *
         * @param root
         * @param p
         * @param q
         * @return
         */
            public TreeNode lowestCommonAncestor03(TreeNode root, TreeNode p, TreeNode q) {
                Map<TreeNode, TreeNode> parent = new HashMap<>();
                Queue<TreeNode> queue = new LinkedList<>();
                parent.put(root, null);
                queue.add(root);
                while(!parent.containsKey(p) || !parent.containsKey(q)) {
                    TreeNode node = queue.poll();
                    if(node.left != null) {
                        //左子节点不为空，记录下他的父节点
                        parent.put(node.left, node);
                        queue.add(node.left);
                    }
                    if(node.right != null) {
                        parent.put(node.right, node);
                        queue.add(node.right);
                    }
                }
                Set<TreeNode> ancestors = new HashSet<>();
                //记录下p和他的祖先节点，从p节点开始一直到根节点
                while(p != null) {
                    ancestors.add(p);
                    p = parent.get(p);
                }
                //查看p和他的祖先节点是否包含q节点，如果不包含再看是否包含q的父节点.....
                while(!ancestors.contains(q))
                    q = parent.get(q);
                return q;
            }

        /**
         * 1.终止条件：
         *      1-当越过叶节点，则直接返回 null ；
         *      2-当 root 等于 p,q ，则直接返回root ；
         * 2.递推工作：
         *      1-开启递归左子节点，返回值记为 left ；
         *      2-开启递归右子节点，返回值记为 right ；
         * 3.返回值： 根据 left 和 right ，可展开为四种情况；
         *      1-当 left 和 right 同时为空 ：说明 root 的左 / 右子树中都不包含 p,q ，返回 null ；
         *      2-当 left 和 right 同时不为空 ：说明 p,q 分列在root 的 异侧 （分别在 左 / 右子树），因此 root 为最近公共祖先，返回 root ；
         *      3-当 left 为空 ，right 不为空 ：p,q 都不在 root 的左子树中，直接返回 right 。具体可分为两种情况：
         *          1--p,q 其中一个在 root 的 右子树 中，此时 right 指向 p（假设为 p ）；
         *          2--p,q 两节点都在root 的 右子树 中，此时的 right 指向 最近公共祖先节点 ；
         * 4. 当 left 不为空 ， right 为空 ：与情况 3. 同理；
         * @param cur
         * @param p
         * @param q
         * @return
         */
            public TreeNode lowestCommonAncestors04(TreeNode root, TreeNode p, TreeNode q) {
                if(root == null || root == p || root == q)
                    return root;
                TreeNode left = lowestCommonAncestors04(root.left, p, q);
                TreeNode right = lowestCommonAncestors04(root.right, p, q);
                //如果left为空，说明这两个节点在cur节点的右子树上，我们只需要返回右子树查找的结果即可
                if(left == null) return right;
                if(right == null) return left;
                //如果left和right都不为空，说明这两个节点一个在cur的左子树上，一个在cur的右子树上，我们只需要返回cur节点即可
                return root;


//
//                if(root == null || root == p || root == q) return root;
//                TreeNode left = lowestCommonAncestor(root.left, p, q);
//                TreeNode right = lowestCommonAncestor(root.right, p, q);
//                if(left == null && right == null) return null; // 1.
//                if(left == null) return right; // 3.
//                if(right == null) return left; // 4.
//                return root; // 2. if(left != null and right != null)


            }

//        public class Solution {//所有的递归的返回值有4种可能性，null、p、q、公共祖先
//            public TreeNode LowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
//                if (root == null) {//当遍历到叶结点后就会返回null
//                    return root;
//                }
//                if (root == p || root == q) {//当找到p或者q的是时候就会返回pq
//                    return root;/*当然，值得一提的是，如果公共祖先是自己（pq），并不需要寻找另外
//                     一个，我们在执行前序遍历会先找上面的，后找下面的，我们会直接返回公共祖先。*/
//                }
//                TreeNode left = LowestCommonAncestor(root.left, p, q);//返回的结点进行保存，可能是null
//                TreeNode right = LowestCommonAncestor(root.right, p, q);//也可能是pq，还可能是公共祖先
//                if (left != null && right != null) {
//                    return root;//如果左右都存在，就说明pq都出现了，这就是，公共祖先，此时不用考虑公共祖先是自己的情况，因为上面已经做过判断了。
//                } else if (left != null) {//否则我们返回已经找到的那个值（存储在left，与right中），p或者q
//                    return left;//还有一种可能就是，由下面返回的公共祖先，并将这个值一路返回到最表层
//                } else if (right != null) {
//                    return right;
//                }
//                return null;
//            }
//        }





    }
}
