package offer_27;

import com.sun.source.tree.Tree;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class TreeNode {
    int val;
       TreeNode left;
       TreeNode right;
       TreeNode(int x) { val = x; }

    class Solution {
        /**
         * 还是不会递归
         *
         * @param root
         * @return
         */
           public TreeNode mirrorTree(TreeNode root) {
                if(root == null) return null;
                root.left = mirrorTree(root.right); //卧槽，忘记了，不能直接将镜像反转的子树赋值给root.left
               // --这样会影响root.right = mirrorTree(root.left)的调用
               //在递归右子节点 “root.left = mirrorTree(root.right)
               // ;root.left=mirrorTree(root.right);” 执行完毕后，
               // root.leftroot.left 的值已经发生改变，
               // 此时递归左子节点 mirrorTree(root.left)mirrorTree(root.left) 则会出问题。
                return root;
           }

        /**
         * 递归（后序遍历）
         * @param root
         * @return
         */
           public TreeNode mirrorTree1(TreeNode root) {
               if(root == null) return null;
               TreeNode left = mirrorTree1(root.left);
               TreeNode right = mirrorTree1(root.right);
               root.left = right;
               root.right = left;
               return root;
           }

        /**
         * bfs
         * @param root
         * @return
         */
        public TreeNode mirrorTree2(TreeNode root) {
               if(root == null) return null;
               final Queue<TreeNode> queue = new LinkedList<>();
               queue.add(root);
               while(!queue.isEmpty()) {
                   TreeNode node = queue.poll();
                   //交换node节点的两个子节点
                   TreeNode left = node.left;
                   node.left = node.right;
                   //如果当前节点的左/右子树不为空，就把左子树
                   //节点加入到队列中
                   if(node.left != null) queue.add(node.left);
                   if(node.right != null) queue.add(node.right);
               }
               return root;
           }

        /**
         * dfs
         * @param root
         * @return
         */
        public TreeNode mirror3(TreeNode root) {
            if(root == null) return null;
            Stack<TreeNode> stack = new Stack<>() {{ add(root); }};
            while(!stack.isEmpty()) {
                TreeNode node = stack.pop();
                if(node.left != null) stack.add(node.left);
                if(node.right != null) stack.add(node.right);
                TreeNode tmp = node.left;
                node.left = node.right;
                node.right = tmp;
            }
            return root;
        }

        /**
         * dfs中序遍历
         * @param root
         * @return
         */
        public TreeNode mirror4(TreeNode root) {
            if(root == null) return null;
            Stack<TreeNode> stack = new Stack<>();
            TreeNode node = root;
            while(node != null || !stack.isEmpty()) {
                while(node != null) {
                    stack.push(node);
                    node = node.left;
                }
                if(!stack.isEmpty()) {
                    node = stack.pop();
                    //子节点交换
                    TreeNode temp = node.left;
                    node.left = node.right;
                    node.right = temp;
                    //注意这里以前时node.right, 因为上面已经交换了，所以这里要改为node.left
                    node = node.left;
                }
            }
            return root;
        }





    }
}
