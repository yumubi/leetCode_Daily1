package binary_Search_Tree_Iterator_173;

import java.util.ArrayList;
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


        class BSTIterator {
//          Queue<Integer> list = new LinkedList<>();
//          public BSTIterator(TreeNode root) {
//              dfs(root);
//          }
//
//          public int next(){
//             if(!list.isEmpty()) return list.poll();
//             return Integer.MIN_VALUE;
//          }
//
//          public boolean hasNext() {
//                return !list.isEmpty();
//          }
//
//
//          void dfs(TreeNode root) {
//              if(root == null) return;
//              dfs(root.left);
//              list.add(root.val);
//              dfs(root.right);
//          }

//我们可以直接对二叉搜索树做一次完全的递归遍历，获取中序遍历的全部结果并保存在数组中。随后，我们利用得到的数组本身来实现迭代器。
            private int idx;
            private List<Integer> arr;

            public BSTIterator(TreeNode root) {
                idx = 0;
                arr = new ArrayList<Integer>();
                inorderTraversal(root, arr);
            }

            public int next() {
                return arr.get(idx++);
            }
            public boolean hasNext(){
                return idx < arr.size();
            }

            private void inorderTraversal(TreeNode root, List<Integer> arr){
                if(root == null) return;
                inorderTraversal(root.left, arr);
                arr.add(root.val);
                inorderTraversal(root.right, arr);
            }


//            private TreeNode cur;
//            private Deque<TreeNode> stack;
//
//            public BSTIterator(TreeNode root) {
//                cur = root;
//                stack = new LinkedList<>();
//            }
//            public int next() {
//                while(cur != null) {
//                    stack.push(cur);
//                    cur = cur.left;
//                }
//                    cur = stack.pop();
//                    int ret = cur.val;
//                    cur = cur.right;
//                    return ret;
//        }
//        public boolean hasNext() {
//            return cur != null || !stack.isEmpty();
//            }


//
//            中序遍历的基本逻辑是：处理左子树 -> 处理当前节点 -> 处理右子树。
//            其中迭代做法是利用「栈」进行处理：
//            1. 先将当前节点的所有左子树压入栈，压到没有为止
//            2.将最后一个压入的节点弹出（栈顶元素），加入答案
//            3.将当前弹出的节点作为当前节点，重复步骤一


//            List<Integer> ans = new ArrayList<>();
//            Deque<TreeNode> d = new ArrayDeque<>();
//            public List<Integer> inorderTravsersal(TreeNode root){
//                while(root != null || !d.isEmpty()) {
//                    //步骤1
//                    while(root != null) {
//                        d.addLast(root);
//                        root = root.left;
//                    }
//                    //步骤二
//                    root = d.pollLast();
//                    ans.add(root.val);
//
//                    //步骤三
//                    root = root.right;
//                }
//                return ans;
//            }


//            首先因为 next() 方法中我们需要输出一个值，执行的的是「步骤 2」的逻辑，同时我们需要在其前后添加「步骤 1」和「步骤 3」。
//            另外，我们还有一个 hasNext() 要处理，显然 hasNext() 应该对应我们的栈是否为空。
//            为此，我们需要确保每次输出之后「步骤 1」被及时执行。
//            综上，我们应该在初始化时，走一遍「步骤 1」，然后在 next() 方法中走「步骤 2」、「步骤 3」和「步骤 1」。
//
//            时间复杂度：由于每个元素都是严格「进栈」和「出栈」一次，复杂度为均摊 O(1)
//            空间复杂度：栈内最多保存与深度一致的节点数量，复杂度为 O(h)

//
//            Deque<TreeNode> d = new ArrayDeque<>();
//            public BSTIterator(TreeNode root) {
//                //步骤1
//                dfsLeft(root);
//            }
//            public int next() {
//                //步骤2
//                TreeNode root = d.pollLast();
//                int ans = root.val;
//                //步骤3
//                root = root.right;
//                //步骤1
//                dfsLeft(root);
//                return ans;
//            }
//            void dfsLeft(TreeNode root) {
//                while (root != null) {
//                    d.addLast(root);
//                    root = root.left;
//                }
//            }
//
//
//            public boolean hasNext() {
//                return !d.isEmpty();
//            }









        }
}
