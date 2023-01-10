package count_Nodes_Equal_To_Average_Of_Subtree_2265;

import java.util.ArrayDeque;
import java.util.Deque;

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
           int ans = 0;
           public int averageOfSubtree(TreeNode root) {
                dfs(root);
                return ans;
           }

           int[] dfs(TreeNode node) {
               if(node == null) return new int[]{0, 0};
               int[] left = dfs(node.left);
               int[] right = dfs(node.right);
               int[] ret = new int[]{left[0] + right[0] + node.val, left[1] + right[1] + 1};
               int avg = ret[0] / ret[1];
               if(node.val == avg) ans++;
               return ret;
           }


           /**
            * dfs递归
            * dfs 保存 总和、节点数量, 自底向上递归, 算是DFS或者树形DP
            */
           int cnt;
           public int averageOfSubtree01(TreeNode root) {
               dfs01(root);
               return cnt;
           }
           private int[] dfs01(TreeNode root) {
               if(root == null) return new int[]{0, 0};
               int[] l = dfs01(root.left);
               int[] r= dfs01(root.right);
               int sum = (l[0] + r[0] + root.val), nodeNum = (l[1] + r[1] + 1), avg = sum / nodeNum;
               if(avg == root.val) cnt++;
               return new int[]{sum, nodeNum};
           }

           /**
            * 树形dp 需要子节点传递给当前节点什么信息，就创建什么数据结构
            */
           private int ans03;
           public int averageOfSubtree03(TreeNode root) {
               ans03 = 0;
               postOrder(root);
               return ans03;
           }
           private Info postOrder(TreeNode root) {
               if(root == null) return new Info(0, 0);
               Info leftInfo = postOrder(root.left);
               Info rightInfo = postOrder(root.right);
               int sum = leftInfo.sum + rightInfo.sum + root.val;
               int cnt = leftInfo.cnt + rightInfo.cnt + 1;
               if(sum / cnt == root.val) ans03++;
               return new Info(sum, cnt);
           }


           /**
            * bfs
            * @param root
            * @return
            */
           public int averageOfSubtree04(TreeNode root) {
               Deque<TreeNode> dq = new ArrayDeque<>();
               if(root == null) return 0;
               dq.offer(root);
               int resCnt = 0;
               while (!dq.isEmpty()) {
                   int size = dq.size();
                   while (size-- > 0) {
                       TreeNode node = dq.poll();
                       if(node.left != null) dq.offer(node.left);
                       if(node.right != null) dq.offer(node.right);
                       int[] tmp = getSubCntAndSum(node);
                       int cnt = tmp[0];
                       int sum = tmp[1];

                       if(sum / cnt == node.val) resCnt++;
                   }
               }
               return resCnt;
           }


           int[] getSubCntAndSum(TreeNode root) {
               Deque<TreeNode> dq = new ArrayDeque<>();
               if(root == null) return new int[]{0, 1};
               int cnt = 0;
               int sum = 0;
               dq.offer(root);
               while (!dq.isEmpty()) {
                   int size = dq.size();
                   cnt += size;
                   while (size-- > 0) {
                       TreeNode node = dq.poll();
                       sum += node.val;
                       if(node.left != null) dq.offer(node.left);
                       if(node.right != null) dq.offer(node.right);
                   }
               }
               return new int[]{cnt, sum};
           }


       }


       class Info {
           int sum, cnt;
           public Info(int s, int c) {
               sum = s;
               cnt = c;
           }
       }



}
