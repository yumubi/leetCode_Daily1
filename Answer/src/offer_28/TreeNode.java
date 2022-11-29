package offer_28;

import java.util.*;

public class TreeNode {
    int val;
       TreeNode left;
       TreeNode right;
       TreeNode(int x) { val = x; }

    class Solution {
        boolean flag = true;
        public boolean isSymmetric(TreeNode root) {
            if(root == null || (root.left == null && root.right == null)) return true;
            dfs(root.left, root.right);
            return flag;
        }
        public void dfs(TreeNode nodeL, TreeNode nodeR) {
            if(nodeL == null && nodeR == null) return;
            if(nodeL == null || nodeR == null || nodeL.val != nodeR.val) {
                flag = false;
                return;
            }


            dfs(nodeL.left, nodeR.right);
            dfs(nodeL.right, nodeR.left);
        }

        /**
         * 通过「同步移动」两个指针的方法来遍历这棵树，p 指针和 q 指针一开始都指向这棵树的根，
         * 随后 p 右移时，q 左移，p 左移时，q 右移。每次检查当前 p 和 q 节点的值是否相等，如果相等再判断左右子树是否对称。
         * @param root
         * @return
         */
        public boolean isSymmetric1(TreeNode root) {
            return check(root, root);
        }
        public boolean check(TreeNode p, TreeNode q) {
            if(p == null && q == null) return true;
            if(p == null || q == null) return false;
            return p.val == q.val && check(p.left, q.right) && check(p.right, q.right);
        }


        /**
         * 首先我们引入一个队列，这是把递归程序改写成迭代程序的常用方法。初始化时我们把根节点入队两次。
         * 每次提取两个结点并比较它们的值（队列中每两个连续的结点应该是相等的，而且它们的子树互为镜像），
         * 然后将两个结点的左右子结点按相反的顺序插入队列中。
         * 当队列为空时，或者我们检测到树不对称（即从队列中取出两个不相等的连续结点）时，该算法结束。


         //注意： ArrayDeque不论是offer还是add，最终都调用的都是addLast，
         这个方法入参为空会报NullPointerException；LinkedList的offer和add最终都调用的linkLast
         这个方法没有对入参做空校验；
         * @param root
         * @return
         */
        public boolean isSymmetric2(TreeNode root) {
            return check2(root, root);
        }
        public boolean check2(TreeNode u, TreeNode v) {
            Queue<TreeNode> q = new LinkedList<TreeNode>();
            q.offer(u);
            q.offer(v);
            while(!q.isEmpty()) {
                u = q.poll();
                v = q.poll();
                if(u == null && v == null) continue;
                if(u == null || v == null || u.val != v.val) return false;
                q.offer(u.left);
                q.offer(v.right);

                q.offer(u.right);
                q.offer(v.left);
            }
            return true;
        }


        /**
         * 局部检查(层序遍历)
         * 我们使用 0x3f3f3f3f 作为无效值，并建立占位节点 emptyNode 用来代指空节点（emptyNode.val = 0x3f3f3f3f）。
         * 一个朴素的做法是：使用「层序遍历」的方式进行「逐层检查」，对于空节点使用 emptyNode 进行代指，同时确保不递归 emptyNode 对应的子节点。
         * 具体做法如下：
         * 1.起始时，将 root 节点入队；
         * 2.从队列中取出节点，检查节点是否为 emptyNode 节点来决定是否继续入队
         *    当不是 emptyNode 节点时，将其左/右儿子进行入队，如果没有左/右儿子，则用 emptyNode 代替入队；
         *    当是 emptyNode 节点时，则忽略；
         * 3.在进行流程 2 的同时使用「临时列表」记录当前层的信息，并检查当前层是否符合 “对称” 要求；
         * 4.循环流程 2 和 3，直到整个队列为空。


         */
        int INF = 0x3f3f3f3f;
        TreeNode emptyNode = new TreeNode(INF);
        boolean isSymmetric3(TreeNode root) {
            if(root == null) return true;
            Deque<TreeNode> d = new ArrayDeque<>();
            d.add(root);
            while(!d.isEmpty()) {
                // 每次循环都将下一层拓展完并存到「队列」中
                // 同时将该层节点值依次存入到「临时列表」中
                int size = d.size();
                List<Integer> list = new ArrayList<>();
                while(size-- > 0) {
                    TreeNode poll = d.pollFirst();
                    if(!poll.equals(emptyNode)) {
                        d.addLast(poll.left != null ? poll.left : emptyNode);
                        d.addLast(poll.right != null ? poll.right : emptyNode);
                    }
                    list.add(poll.val);
                }
                // 每一层拓展完后，检查一下存放当前层的该层是否符合「对称」要求
                if(!check3(list)) return false;
            }
            return true;
        }

//使用双指针来检查某层是否符合对称要求
        boolean check3(List<Integer> list) {
            int l = 0, r = list.size() - 1;
            while(l < r) {
                if(!list.get(l).equals(list.get(r))) return false;
                l++;
                r--;
            }
            return true;
        }


        /**
         * 整体检查（递归）
         * 在「层序遍历」解法中，我们利用了 “对称” 定义对每层进行检查。
         * 本质上这是利用 “对称” 定义进行多次「局部」检查。
         * 事实上，我们还可以利用 “对称” 定义在「全局」层面上进行检查。
         * 我们如何定义两棵子树 a 和 b 是否 “对称” ？
         * 当且仅当两棵子树符合如下要求时，满足 “对称” 要求：
         * 两棵子树根节点值相同；
         * 两颗子树的左右子树分别对称，包括：
         *    a 树的左子树与 b 树的右子树相应位置的值相等
         *    a 树的右子树与 b 树的左子树相应位置的值相等
         *具体的，我们可以设计一个递归函数 check ，传入待检测的两颗子树的头结点 a 和 b（对于本题都传入 root 即可），
         * 在单次查询中有如下显而易见的判断子树是否 “对称” 的 Base Case：
         * a 和 b 均为空节点：符合 “对称” 要求；
         * a 和 b 其中一个节点为空，不符合 “对称” 要求；
         * a 和 b 值不相等，不符合 “对称” 要求；
         * 其他情况，我们则要分别检查 a 和 b 的左右节点是否 “对称” ，即递归调用 check(a.left, b.right) 和 check(a.right, b.left)。


         * @param root
         * @return
         */
        public boolean isSymmetric4(TreeNode root) {
            return check4(root, root);
        }
        boolean check4(TreeNode a, TreeNode b) {
            if (a == null && b == null) return true;
            if (a == null || b == null) return false;
            if (a.val != b.val) return false;
            return check(a.left, b.right) && check(a.right, b.left);
        }


        /**
         * 在翻转时创建一个新的树，而不是原地修改旧树，将新的翻转后的树与原树比较即可
         * @param root
         * @return
         */
        public boolean isSymmetric5(TreeNode root) {
            // 最开始的思路 翻转成对称树 再比较
            // key point: 一定不能再原来的树上翻转！！！要新建一个树翻转！
            TreeNode tmp = root;
            TreeNode root2 = mirror(tmp);
            return isEquals(root2, root);
        }
        private TreeNode mirror(TreeNode root) {
            if(root == null) return null;
            TreeNode newRoot = new TreeNode(root.val);
            TreeNode tmp = root.left;
            newRoot.left = mirror(root.right);
            newRoot.right = mirror(tmp);
            return newRoot;
        }

        private boolean isEquals(TreeNode root1, TreeNode root2) {
            if(root1 == null && root2 == null) return true;
            if(root1 == null || root2 == null) return false;
            return root1.val == root2.val && isEquals(root1.left, root2.left)
                    && isEquals(root1.right, root2.right);
        }


        /**
         * bfs
         * @param root
         * @return
         */
        public boolean isSymmetric6(TreeNode root) {
            //队列
            Queue<TreeNode> queue = new LinkedList<>();
            if (root == null)
                return true;
            //左子节点和右子节点同时入队
            queue.add(root.left);
            queue.add(root.right);
            //如果队列不为空就继续循环
            while (!queue.isEmpty()) {
                //每两个出队
                TreeNode left = queue.poll(), right = queue.poll();
                //如果都为空继续循环
                if (left == null && right == null)
                    continue;
                //如果一个为空一个不为空，说明不是对称的，直接返回false
                if (left == null ^ right == null)
                    return false;
                //如果这两个值不相同，也不是对称的，直接返回false
                if (left.val != right.val)
                    return false;
                //这里要记住入队的顺序，他会每两个两个的出队。
                //左子节点的左子节点和右子节点的右子节点同时
                //入队，因为他俩会同时比较。
                //左子节点的右子节点和右子节点的左子节点同时入队，
                //因为他俩会同时比较
                queue.add(left.left);
                queue.add(right.right);
                queue.add(left.right);
                queue.add(right.left);
            }
            return true;
        }



    }
}
