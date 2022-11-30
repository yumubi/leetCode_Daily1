package offer_55;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class TreeNode {
    int val;
       TreeNode left;
       TreeNode right;
       TreeNode(int x) { val = x; }


    class Solution {
           int ans = 0;
           public int maxDepth(TreeNode root) {
               if(root == null) return 0;
               int left = maxDepth(root.left);
               int right = maxDepth(root.right);
               ans = Math.max(left, right) + 1;
               return ans;
           }

        /**
         * bfs
         * @param root
         * @return
         */
        public int maxDepth1(TreeNode root) {
                if(root == null) return 0;
            Queue<TreeNode> queue = new LinkedList<TreeNode>();
            queue.offer(root);
            int ans = 0;
            while(!queue.isEmpty()) {
                int size = queue.size();
                while(size > 0) {
                    TreeNode node = queue.poll();
                    if(node.left != null) queue.offer(node.left);
                    if(node.right != null) queue.offer(node.right);
                    size--;
                }
                ans++;
            }
            return ans;
        }


        public int maxDepth2(TreeNode root) {
            if(root == null) return 0;
            Stack<TreeNode> stack = new Stack<>();
            Stack<Integer> level = new Stack<>();
            stack.push(root);
            level.push(1);
            int max = 0;
            while(!stack.isEmpty()) {
                TreeNode node = stack.pop();
                int tmp = level.pop();
                max = Math.max(tmp, max);
                if(node.left != null) {
                    stack.push(node.left);
                    level.push(tmp + 1);
                }
                if(node.right != null) {
                    stack.push(node.right);
                    level.push(tmp + 1);
                }
            }
            return max;
        }




//
//        public int maxDepth3(TreeNode root) {
//            if (root == null) {
//                return 0;
//            }
//            Stack<Pair<TreeNode, Integer>> stack = new Stack<>();
//            stack.add(new Pair<>(root, 1));
//            int h = 0;
//            while (!stack.isEmpty()) {
//                Pair<TreeNode, Integer> pair = stack.pop();
//
//                h = Math.max(pair.getValue(), h);
//
//                if (pair.getKey().right != null) {
//                    stack.push(new Pair<>(pair.getKey().right, pair.getValue() + 1));
//                }
//                if (pair.getKey().left != null) {
//                    stack.push(new Pair<>(pair.getKey().left, pair.getValue() + 1));
//                }
//            }
//            return h;
//        }


    }
}
