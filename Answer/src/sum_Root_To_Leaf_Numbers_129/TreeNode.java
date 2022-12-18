package sum_Root_To_Leaf_Numbers_129;

import java.util.*;

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
           /**
            * 不会
            *
            * @param root
            * @return
            */
           public int sumNumbers(TreeNode root) {
               if (root == null) return 0;
               int reslut = 0;
               ArrayDeque<TreeNode> stk = new ArrayDeque<>();
               TreeNode node = root;
               int path = 0;
               while (!stk.isEmpty() || node != null) {
                   while (node != null) {
                       path = path * 10 + node.val;
                       stk.push(node);
                       node = node.left;
                   }
                   node = stk.pop();
                   if (node.right == null) reslut += path;
                   node = node.right;
                   if (node != null) path /= 10;

               }
               return reslut;
           }


           /**
            * dfs
            * 深度优先搜索是很直观的做法。从根节点开始，遍历每个节点，如果遇到叶子节点，则将叶子节点对应的数字加到数字之和。如果当前节点不是叶子节点，
            * 则计算其子节点对应的数字，然后对子节点递归遍历。
            * 间复杂度：O(n)，其中 n 是二叉树的节点个数。对每个节点访问一次。
            * 空间复杂度：O(n)，其中 n 是二叉树的节点个数。空间复杂度主要取决于递归调用的栈空间，递归栈的深度等于二叉树的高度，
            * 最坏情况下，二叉树的高度等于节点个数，空间复杂度为 O(n)。
            *
            * @param root
            * @return
            */
           public int sumNumbers1(TreeNode root) {
               return dfs1(root, 0);
           }

           public int dfs1(TreeNode root, int preSum) {
               if (root == null) return 0;
               int sum = preSum * 10 + root.val;
               if (root.left == null && root.right == null) return sum;
               else return dfs1(root.left, sum) + dfs1(root.right, sum);
           }

           /**
            * bfs
            * 使用广度优先搜索，需要维护两个队列，分别存储节点和节点对应的数字。
            * 初始时，将根节点和根节点的值分别加入两个队列。每次从两个队列分别取出一个节点和一个数字，进行如下操作：
            * 如果当前节点是叶子节点，则将该节点对应的数字加到数字之和；
            * 如果当前节点不是叶子节点，则获得当前节点的非空子节点，并根据当前节点对应的数字和子节点的值计算子节点对应的数字，然后将子节点和子节点对应的数字分别加入两个队列。
            * 搜索结束后，即可得到所有叶子节点对应的数字之和。
            * 时间复杂度：O(n)，其中 n 是二叉树的节点个数。对每个节点访问一次。
            * 空间复杂度：O(n)，其中 n 是二叉树的节点个数。空间复杂度主要取决于队列，每个队列中的元素个数不会超过 n。

            * @param root
            * @return
            */
           public int sumNumbers2(TreeNode root) {
               if (root == null) return 0;
               int sum = 0;
               Queue<TreeNode> nodeQueue = new LinkedList<TreeNode>();
               Queue<Integer> numQueue = new LinkedList<Integer>();
               nodeQueue.offer(root);
               numQueue.offer(root.val);
               while(!nodeQueue.isEmpty()) {
                   TreeNode node = nodeQueue.poll();
                   int num = numQueue.poll();
                   TreeNode left = node.left, right = node.right;
                   if(left == null && right == null) sum += num;
                   else {
                       if(left != null) {
                           nodeQueue.offer(left);
                           numQueue.offer(num * 10 + left.val);
                       }
                       if(right != null) {
                           nodeQueue.offer(right);
                           numQueue.offer(num * 10 + root.val);
                       }
                   }
               }
               return sum;
           }


           //具有返回值的dfs方法
           //沿着dfs方向只要到达叶子结点，即可知道从root到此叶子结点的路径数字。想要取得路径数字的和，可以在返回时由上一级结点求和。
           // 这样的过程具有后序遍历的特点（在返回过程中具有后序特点，在求代表当前结点的路径部分数字num时具有前序特点），
           // 于是定义int dfs(node, num)由int num来维护路径数字，叶子结点返回路径数字num，非叶子结点返回它以下能达到的路径的数字的和。
           // 本方法的特点是到达叶子结点后递归地将路径数字（叶子）或路径数字和（非叶子）向上返回，返回到根结点时，在根结点处取得最终的总和。
           public int sumNumbers3(TreeNode root) {
               return dfs3(root, 0);
           }
           private int dfs3(TreeNode node, int num) {
               if(node == null) return 0;
               num = num * 10 + node.val;
               if(node.left == null && node.right == null) return num;
               return dfs3(node.left, num) + dfs3(node.right, num);//其他节点返回儿子代表的 路径数字的和
           }

           //无需返回值的dfs方法
           //只关心在叶子节点处的累加，#1 #2 #3的位置是任意的，即前中后序都可以
           //也可以设置一个类变量sum来实时地累计路径数字num，这样就可以不必通过dfs的返回值来得到sum。具体做法相比前一做法更直观，
           // dfs(node, num)的两个参数，node表示当前结点，num表示node代表的数字。dfs处理node时，先通过num = num * 10 + node.val给出node代表的数字，
           // 然后检查node是否为叶子结点，若是，则立即累计num到sum中。由于dfs过程只关心在叶子结点处的累计，因此采用前序、中序或后序遍历都是可行的。
           int sum = 0;
           public int sumNumbers4(TreeNode root) {
               dfs4(root, 0);
               return sum;
           }
           private void dfs4(TreeNode node, int num) {
               if(node == null) return;
               num = num * 10 + node.val;
               if(node.left == null && node.right == null) {//#1
                   sum += num;
               }
               dfs4(node.left, num);//#2
               dfs4(node.right, num);//#3
           }


           /**
            * 虽然回溯过程也是dfs的过程，但其「回溯」或者说其「撤销」后退的思想与前述不同，因此作为另一种解法以视区分。
            * 能够用回溯的做法解决本题也是比较显然的，目标是寻找每一个叶子结点，在寻找过程中维护一个路径数字num，一个路径数字总和sum。
            * dfs到叶子结点时，立即累计num到sum。当前结点的子空间探索结束后，num需要撤销node带来的贡献，撤销动作为num = (num - node.val) / 10。
            * 注意：在典型的回溯问题中，探索一个结点的子空间通常以for（多叉树回溯形式）来完成，但本题的树是二叉树，
            * 且回溯条件是左右儿子皆空（即当前结点为叶子结点），因此将dfs(node.left)与dfs(node.right)依次写出即相当于完成了当前node子空间的探索。
            */
           int sum5 = 0, num5 = 0;
           public int sumNumbers5(TreeNode root) {
               dfs5(root);
               return sum5;
           }
           private void dfs5(TreeNode node) {
               if(node == null) return;
               num5 = num5 * 10 + node.val;
               if(node.left == null && node.right == null) sum5 += num5;
               dfs5(node.left);
               dfs5(node.right);
               num5 = (num5 - node.val) / 10;
           }


           /**
            *
            * 要注意回溯和递归要永远在一起，一个递归，对应一个回溯，是一对一的关系
            *
            *                 // 中
            * if (cur->left) { // 左 （空节点不遍历）
            *     path.push_back(cur->left->val);
            *     traversal(cur->left);    // 递归
            *     path.pop_back();         // 回溯
            * }
            * if (cur->right) { // 右 （空节点不遍历）
            *     path.push_back(cur->right->val);
            *     traversal(cur->right);   // 递归
            *     path.pop_back();         // 回溯
            * }
            */
           List<Integer> path6 = new ArrayList<>();
           int res6 = 0;
           public int sumNumbers6(TreeNode root) {
               if(root == null) return 0;
               path6.add(root.val);
               recur(root);
               return res6;
           }
           public void recur(TreeNode root) {
               if(root.left == null && root.right == null) {
                   //当是叶子结点的的时候，开始处理
                   res6 += listToInt(path6);
                   return;
               }
               if(root.left != null) {
                   //注意有回溯
                   path6.add(root.left.val);
                   recur((root.left));
                   path6.remove(path6.size() - 1);
               }
               if(root.right != null) {
                   //注意有回溯
                   path6.add(root.right.val);
                   recur((root.right));
                   path6.remove(path6.size() - 1);
               }
               return;
           }

           public int listToInt(List<Integer> path6) {
               int sum = 0;
               for(Integer num : path6)
                   sum = sum * 10 + num;
               return sum;
           }


           public int sumNumbers7(TreeNode root) {
               if(root == null) return 0;
               Stack<TreeNode> nodeStack = new Stack<>();
               Stack<Integer> valueStack = new Stack<>();
               int res = 0;
               nodeStack.add(root);
               valueStack.add(root.val);
               while(!nodeStack.isEmpty()) {
                   TreeNode node = nodeStack.pop();
                   int value = valueStack.pop();
                   if(node.left == null && node.right == null) {
                       res += value;
                   } else {
                       if(node.right != null) {
                           nodeStack.push(node.right);
                           valueStack.push(value * 10 + node.right .val);
                       }
                       if(node.left != null) {
                           nodeStack.push(node.left);
                           valueStack.push(value * 10 + node.left .val);
                       }

                   }
               }
               return res;


//
//               public int sumNumbers(TreeNode root) {
//                   return dfs(root, 0);
//               }
//
//               private int dfs(TreeNode root, int sum) {
//                   //终止条件的判断
//                   if (root == null)
//                       return 0;
//                   //计算当前节点的值
//                   sum = sum * 10 + root.val;
//                   //如果当前节点是叶子节点，说明找到了一条完整路径，直接
//                   //返回这条路径的值即可
//                   if (root.left == null && root.right == null)
//                       return sum;
//                   //如果当前节点不是叶子结点，返回左右子节点的路径和
//                   return dfs(root.left, sum) + dfs(root.right, sum);
//               }



//
//               class Solution {
//
//                   private int result = 0;
//
//                   public int sumNumbers(TreeNode root) {
//                       if (root != null) {
//                           dfs(root, new StringBuilder());
//                       }
//                       return result;
//                   }
//
//                   private void dfs(TreeNode root, StringBuilder path) {
//                       if (root == null) {
//                           return;
//                       }
//                       // 将当前节点值加入路径中
//                       path.append(root.val);
//                       // 递归终点, 如果已经到达叶节点, 将路径形成的数字累加入和
//                       if (root.left == null && root.right == null) {
//                           result += Integer.parseInt(path.toString());
//                           return;
//                       }
//                       if (root.left != null) {
//                           final int n = path.length();
//                           dfs(root.left, path);
//                           // 回溯操作, 弹出当前节点值
//                           path.setLength(n);
//                       }
//                       if (root.right != null) {
//                           final int n = path.length();
//                           dfs(root.right, path);
//                           // 回溯操作
//                           path.setLength(n);
//                       }
//                   }
//               }
//
//












           }



       }
}
