package offer_68_1;

import java.util.*;

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
               List<TreeNode> pList = new ArrayList<>();
               List<TreeNode> qList = new ArrayList<>();
               dfs(root, pList, p);
               dfs(root, qList, q);
               int idx = 0;
               TreeNode ancestor = root;
               while(true) {
                   if(pList.get(idx).equals(qList.get(idx))) return ancestor;
                   ancestor = qList.get(idx);
                   idx++;
               }
           }

           public void dfs(TreeNode root, List<TreeNode> list, TreeNode target) {
               if(root == null) return;
               if(root.val == target.val) {
                   list.add(root);
                   return;
               }
               list.add(root);
               dfs(root.left, list, target);
               dfs(root.right, list, target);
           }

        /**
         * 1. 若树里面存在p，也存在q，则返回他们的公共祖先。
         * 2. 若树里面只存在p，或只存在q，则返回存在的那一个。
         * 3. 若树里面即不存在p，也不存在q，则返回null。
         *
         * 我们递归遍历整棵二叉树，定义 fx表示x节点的子树中是否包含p节点或q节点，如果包含为 true，否则为 false。
         * 那么符合条件的最近公共祖先x一定满足如下条件
         * (f_lson && f_rson) ∣∣ (x=p∣∣x=q) && (f_lson) ∣∣ f_rson)
         * 其中 lson和rson 分别代表 x节点的左孩子和右孩子。初看可能会感觉条件判断有点复杂，
         * f_lson && f_rson说明左子树和右子树均包含p节点或 q节点，如果左子树包含的是 p节点，那么右子树只能包含q节点，
         *   反之亦然，因为 p节点和 q节点都是不同且唯一的节点，因此如果满足这个判断条件即可说明 x就是我们要找的最近公共祖先。
         *   再来看第二条判断条件，这个判断条件即是考虑了 x恰好是 p节点或 q节点且它的左子树或右子树有一个包含了另一个节点的情况，
         *   因此如果满足这个判断条件亦可说明 x就是我们要找的最近公共祖先。
         *你可能会疑惑这样找出来的公共祖先深度是否是最大的。其实是最大的，因为我们是自底向上从叶子节点开始更新的，
         * 所以在所有满足条件的公共祖先中一定是深度最大的祖先先被访问到，且由于 f_x本身的定义很巧妙，在找到最近公共祖先 x 以后，f_x
         * 按定义被设置为 true ，即假定了这个子树中只有一个 p节点或 q节点，因此其他公共祖先不会再被判断为符合条件。
         */
        private TreeNode ans;
           public Solution() {
               this.ans = null;
           }
        // TODO: 2/12/2022  待研究

           private boolean dfs1(TreeNode root, TreeNode p, TreeNode q) {
               if(root == null) return false;
               boolean lson = dfs1(root.left, p, q);
               boolean rson = dfs1(root.right, p, q);
               if(lson && rson || ((root.val == p.val || root.val == q.val) && (lson || rson))) ans = root;
               return lson || rson || root.val == p.val || root.val == q.val;
           }

           public TreeNode lowestCommonAncestor1(TreeNode root, TreeNode p, TreeNode q) {
               this.dfs1(root, p, q);
               return this.ans;
           }


        /**
         * 存储父节点
         * 我们可以用哈希表存储所有节点的父节点，然后我们就可以利用节点的父节点信息从 p 结点开始不断往上跳，并记录已经访问过的节点，
         * 再从q节点开始不断往上跳，如果碰到已经访问过的节点，那么这个节点就是我们要找的最近公共祖先。
         * 算法
         * 从根节点开始遍历整棵二叉树，用哈希表记录每个节点的父节点指针。
         * 从 p 节点开始不断往它的祖先移动，并用数据结构记录已经访问过的祖先节点。
         * 同样，我们再从 q 节点开始不断往它的祖先移动，如果有祖先已经被访问过，即意味着这是 p 和 q 的深度最深的公共祖先，即 LCA 节点
         *
         * 时间复杂度：O(N)，其中 N是二叉树的节点数。二叉树的所有节点有且只会被访问一次，
         * 从 p 和 q 节点往上跳经过的祖先节点个数不会超过 N，因此总的时间复杂度为 O(N)。
         *
         * 空间复杂度：O(N) ，其中 N 是二叉树的节点数。递归调用的栈深度取决于二叉树的高度，
         * 二叉树最坏情况下为一条链，此时高度为 N，因此空间复杂度为 O(N)，
         * 哈希表存储每个节点的父节点也需要 O(N) 的空间复杂度，因此最后总的空间复杂度为 O(N)。
         */
        Map<Integer, TreeNode> parent = new HashMap<Integer, TreeNode>();
           Set<Integer> visited = new HashSet<Integer>();

           public void dfs2(TreeNode root) {
               if(root.left != null){
               parent.put(root.left.val, root);
               dfs2(root.left);
           }
               if(root.right != null) {
                   parent.put(root.right.val, root);
                   dfs2(root.right);
               }
    }

    public TreeNode lowestCommonAncestor2(TreeNode root, TreeNode p, TreeNode q) {
                    dfs2(root);
                    while (p != null) {
                        visited.add(p.val);
                        p = parent.get(p.val);
                    }
                    while(q != null) {
                        if(visited.contains(q.val)) return q;
                        q = parent.get(q.val);
                    }
                    return null;
           }


           public TreeNode lowestCommonAncestor3(TreeNode root, TreeNode p, TreeNode q) {
               //记录遍历到的每个节点的父节点
               Map<TreeNode, TreeNode> parent = new HashMap<>();
               Queue<TreeNode> queue = new LinkedList<>();
               parent.put(root, null); //根节点没有父节点,所以为空
               queue.add(root);
               //直到两个节点都找到为止
               while (!parent.containsKey(p) || !parent.containsKey(q)) {
                   //队列是一边进一边出，
                   TreeNode node = queue.poll();

                   if(node.left != null) {
                       //左子节点不为空，记录他的父节点
                       parent.put(node.left, node);
                       queue.add(node.left);
                   }

                   if(node.right != null) {
                       parent.put(node.right, node);
                       queue.add(node.right);
                   }
               }
               Set<TreeNode> ancestor = new HashSet<>();
               //记录下p和他的祖先节点，从p节点一直到根节点
               while(p != null) {
                   ancestor.add(p);
                   p = parent.get(p);
               }
               //查看p和他的祖先节点是否包含q，如果不包含再查看是否包含q的父节点
               while(!ancestor.contains(q)) q = parent.get(q);
               return q;
           }

           public TreeNode lowestCommonAncestor4(TreeNode cur, TreeNode p, TreeNode q) {
               if(cur == null || cur == p || cur == q) return cur;
               TreeNode left = lowestCommonAncestor4(cur.left, p, q);
               TreeNode right = lowestCommonAncestor4(cur.right, p, q);
               //left为空，说明这两个节点在cur在右子树上，只需要返回右子树的查找结果即可
               if(left == null) return right;
               if(right == null) return left;
               //如果left right都不为空，则这两个节点一个在cur左子树，一个在cur右子树上,只需要返回cur节点即可
               return cur;
           }




        /**
         * 定义延拓法:
         * lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q)
         * 这个函数题目给出的的定义为以root为根节点的的二叉树中p与q的公共祖先节点
         * 题目中给出的案例中root树中都会有pq节点
         * 但是递归过程中子树可能不存在某个节点或者两个节点都不存在的情况
         * 现在我们对lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q)定义进行延拓
         * 记为helper(TreeNode root, TreeNode p, TreeNode q)
         * 规定root中若只存在p与q其中一个节点时,返回存在的那个节点;若p与q都不在root中返回null
         * 若两个节点p与q均存在就遵循题目的定义
         * 而题目给出的测试的案例均是root中p与q均存在,充其量就是我们延拓函数的一个特例罢了
         * 满足所有测试案例
         */
        public TreeNode lowestCommonAncestor5(TreeNode root, TreeNode p, TreeNode q) {
            return helper(root, p, q);
        }

        /*
        延拓函数:针对特例会有对应的返回
        */
        private TreeNode helper(TreeNode root, TreeNode p, TreeNode q) {
            // 根节点都为空,那么root为根的树中必定没有p与q
            // 或者这么说,p与q均为null满足为null的子节点,也返回null
            if(root == null) {
                return null;
            }
            // 最近公共祖先的情况1:p或q直接为root,那么p或q就是所求
            // 这里具体又可以分为几种情况:(设定p == root, q == root同理)
            // 1.p与q均存在于root中,结论显然成立,返回p
            // 2.p存在但q不存在root中,根据helper()定义,返回存在的那个节点,返回p
            if(root == p) {
                return p;
            }
            // 同理
            if(root == q) {
                return q;
            }
            // 递归求出root左右子树中p与q的最近公共祖先(经helper()延拓后的结果)
            TreeNode left = helper(root.left, p, q);
            TreeNode right = helper(root.right, p, q);
            // 最近公共祖先的情况2:p与q分居root异侧
            // 这种情况就是左右两边各有一个p或q,root必定为所求
            if(left != null && right != null) {
                return root;
            }
            // left == null && right != null时,左边没有 右边有 的情况
            // 最近公共祖先就是右边找到的那个(注意是已经递归出栈将祖先找出来了!)
            if(left == null) {
                return right;
            }
            // 同理
            if(right == null) {
                return left;
            }
            // left == null && right == null时
            // root两边都找不到,root本身也不是p或q,root又不为null,根据helper()定义返回null
            return null;
        }



    }


}
