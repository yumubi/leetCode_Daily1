package offer_52;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
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

        class Solution {

            public TreeNode increasingBST(TreeNode root) {
                List<TreeNode> res = new ArrayList<>();
                InorderTravsersal(root, res);

                TreeNode dummyRoot = new TreeNode(-1);
                TreeNode idx = dummyRoot;
                for (TreeNode node : res) {
                    node.left = null;
                    node.right = null;
                    idx.right = node;
                    idx = idx.right;
                }
                return dummyRoot.right;
            }

            public void InorderTravsersal(TreeNode root, List<TreeNode> res) {
                if(root == null) return;
                InorderTravsersal(root.left, res);
                res.add(root);
                InorderTravsersal(root.right, res);
            }

            /**
             * 中序遍历后生成新的树
             * 先对输入的二叉搜索树执行中序遍历，将结果保存到一个列表中；
             * 然后根据列表中的节点值，创建等价的只含有右节点的二叉搜索树，其过程等价于根据节点值创建一个链表。
             * @param root
             * @return
             */
            public TreeNode increasingBST1(TreeNode root) {
                List<Integer> res = new ArrayList<Integer>();
                inorder(root, res);
                TreeNode dummyNode = new TreeNode(-1);
                TreeNode currNode = dummyNode;
                for(int value : res) {
                    currNode.right = new TreeNode(value);
                    currNode = currNode.right;
                }
                return dummyNode.right;
            }

            public void inorder(TreeNode node, List<Integer> res) {
                if(node == null) return;
                inorder(node.left, res);
                res.add(node.val);
                inorder(node.right, res);
            }


            /**
             * 在中序遍历的时候，修改节点指向就可以实现。具体地，当我们遍历到一个节点时，
             * 把它的左孩子设为空，并将其本身作为上一个遍历到的节点的右孩子
             */
            private TreeNode resNode;

            public TreeNode increasingBST2(TreeNode root) {
                TreeNode dummyNode = new TreeNode(-1);
                resNode = dummyNode;
                inorder2(root);
                return dummyNode.right;
            }

            public void inorder2(TreeNode node) {
                if(node == null) return;
                inorder2(node.left);
                //在中序遍历过程中修改节点指向
                resNode.right = node;
                node.left = null;
                resNode = node;
                inorder2(node.right);
            }

            /**
             * 由于二叉搜索树的特性,我们在dfs时便可以顺序输出
             * 所以我们可以在dfs时便修改节点的左右指针, 使其符合题目要求
             * 但是从小到大进行修改时头结点的值需要额外开辟空间保存
             * 但如果是从大到小dfs, 便不需要了, 遍历到最后自然是头结点.
             *
             * dfs优化前
             */
            TreeNode head = null, pre = null;
            public void dfs(TreeNode node) {
                if(node == null) return;
                dfs(node.left);
                if(pre != null) pre.right = node;
                else head = node;
                pre = node;
                dfs(node.right);
                node.left = null;
            }

            // TODO: 2/12/2022
            public void dfsPlus(TreeNode node) {
                if(node == null) return;
                dfsPlus(node.right);
                node.right = head;
                head = node;
                dfs(node.left);
                node.left = null;
            }
            public TreeNode increasingBST3(TreeNode root) {
                dfs(root);
                return head;
            }


            public TreeNode increasingBST4(TreeNode root) {
                Deque<TreeNode> stack = new ArrayDeque<TreeNode>();
                TreeNode node = root, head = null, pre = null;
                while (node != null || !stack.isEmpty()) {
                    while (node != null) {
                        stack.addLast(node);
                        node = node.left;
                    }
                    node = stack.removeLast();
                    if(pre != null) pre.right = node;
                    else head = node;
                    pre = node;
                    node.left = null;
                    node = node.right;
                }
                return head;
            }

            public TreeNode increasingBST5(TreeNode root) {
                Deque<TreeNode> stack = new ArrayDeque<>();
                TreeNode node = root, head = null;
                while (node != null || !stack.isEmpty()) {
                    while (node != null) {
                        stack.addLast(node);
                        node = node.right;
                    }
                    node = stack.removeLast();
                    node.right = head;
                    head = node;
                    node = node.left;
                    head.left = null;
                }
                return head;
            }


            /**
             *中序遍历递归
             */
            List<TreeNode> traversal = new ArrayList<TreeNode>();
            public TreeNode increasingBST6(TreeNode root){
                inorder(root);
                int size = traversal.size();
                for(int i = 0; i < size; i++) {
                    TreeNode node = traversal.get(i);
                    node.left = null;
                    node.right = i == size - 1 ? null : traversal.get(i + 1);
                }
                return traversal.get(0);
            }

            public void inorder(TreeNode node) {
                if(node == null) return;
                inorder(node.left);
                traversal.add(node);
                inorder(node.right);

            }


            /**
             * 中序遍历迭代
             * @param root
             * @return
             */
            public TreeNode increasingBST7(TreeNode root) {
                List<TreeNode> traversal = new ArrayList<>();
                Deque<TreeNode> stack = new ArrayDeque<TreeNode>();
                TreeNode node = root;
                while(! stack.isEmpty() || node != null) {
                    while(node != null) {
                        stack.push(node);
                        node = node.left;
                    }
                    node = stack.pop();
                    traversal.add(node);
                    node = node.right;
                }
                int size = traversal.size();
                for(int i = 0; i < size; i++) {
                    node = traversal.get(i);
                    node.left = null;
                    node.right = i == size - 1 ? null : traversal.get(i + 1);
                }
                return traversal.get(0);
            }



            public TreeNode increasingBST8(TreeNode root) {
                Result list = helper(root);
                return list.first;
            }
            private Result helper(TreeNode root) {
                if(root == null) return new Result(null, null);
                Result leftResult = helper(root.left);
                Result rightResult = helper(root.right);

                if(leftResult.last != null) {
                    leftResult.last.right = root;
                }
                root.left = null;
                root.right = rightResult.first;
                TreeNode first = null, last = null;
                if(leftResult.first != null) first = leftResult.first;
                else first = root;
                if(rightResult.last != null) last = rightResult.last;
                else last = root;
                return new Result(first, last);
            }

            class Result {
                TreeNode first, last;
                public Result(TreeNode first, TreeNode last) {
                    this.first = first;
                    this.last = last;
                }
            }

            /**
             * ，将二叉搜索树的根结点作为哑结点的右子结点，则哑结点没有左子结点，只有右子结点。从哑结点开始遍历，
             * 则遍历到的结点（即当前结点）一定没有左子结点。如果当前结点有右子结点，则执行如下操作。
             *      如果右子结点没有左子结点，则将当前结点移动到右子结点，继续对剩下的结点重新排列。
             *      如果右子结点有左子结点，则找到右子结点的前驱结点(可能是左子节点的右子节点），将右子结点作为前驱结点的右子结点，将右子结点的左子结点作为当前结点的右子结点。
             *重复上述操作，直到当前结点的右子结点为空，此时二叉搜索树中的结点排列完毕，哑结点的右子结点为新的根结点，返回新的根结点。
             *
             *
             * @param root
             * @return
             */
            public TreeNode increasingBST9(TreeNode root) {
                TreeNode dummyRoot = new TreeNode(0, null, root);
                TreeNode node = dummyRoot;
                while(node.right != null) {
                    TreeNode right = node.right;
                    if(right.left != null) {
                        TreeNode predecessor = right.left;
                        while(predecessor.right != null) predecessor = predecessor.right;
                        predecessor.right = right;
                        TreeNode next = right.left;
                        right.left = null;
                        node.right = next;
                    } else node = node.right;
                }
                return dummyRoot.right;
            }
















        }
}
