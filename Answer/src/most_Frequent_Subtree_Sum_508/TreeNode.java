package most_Frequent_Subtree_Sum_508;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
           List<Integer> ret = new ArrayList<Integer>();
           Map<Integer,Integer> map = new HashMap<Integer, Integer>();
           public int[] findFrequentTreeSum(TreeNode root) {
                dfs(root);
                int cnt = Integer.MIN_VALUE;
                for(Integer sum : map.keySet()) {
                    if(map.get(sum) > cnt) {
                        cnt = map.get(sum);
                        ret.clear();
                        ret.add(sum);
                    } else if(map.get(sum) == cnt) ret.add(sum);
                }
                return ret.stream().mapToInt(Integer :: intValue).toArray();
           }

           int dfs(TreeNode root) {
               if(root == null) return 0;
               int left = dfs(root.left);
               int right = dfs(root.right);
               int sum = root.val + left + right;
               map.put(sum, map.getOrDefault(sum, 0) + 1);
               return sum;
           }

           Map<Integer, Integer> map01 = new HashMap<>();
           int max = 0;
           public int[] findFrequentTreeSum01(TreeNode root) {
               dfs01(root);
               List<Integer> list = new ArrayList<>();
               for(int k : map.keySet()) {
                   if(map.get(k) == max) list.add(k);
               }
               int n = list.size();
               int[] ans = new int[n];
               for(int i = 0; i < n; i++) ans[i] = list.get(i);
               return ans;
           }
           int dfs01(TreeNode root) {
               if(root == null) return 0;
               int cur = root.val + dfs01(root.left) + dfs01(root.right);
               map.put(cur, map.getOrDefault(cur, 0) + 1);
               max = Math.max(max, map.get(cur));
               return cur;
           }


           /**
            * 时间复杂度：O(n)，其中 n是二叉树的结点个数。深度优先搜索的时间为 O(n)
            * 空间复杂度：O(n)。哈希表和递归的栈空间均为 O(n)
            */
           Map<Integer, Integer> cnt02 = new HashMap<Integer, Integer>();
           int maxCnt02 = 0;
           public int[] findFrequentTreeSum02(TreeNode root) {
               dfs02(root);
               //return cnt02.entrySet().stream().filter(e -> e.getValue() == maxCnt02).mapToInt(Map.Entry::getKey).toArray();
               List<Integer> list = new ArrayList<Integer>();
               for(Map.Entry<Integer, Integer> entry : cnt02.entrySet()) {
                   int s = entry.getKey(), c = entry.getValue();
                   if(c == maxCnt02) list.add(s);
               }
               int[] ans = new int[list.size()];
               for(int i = 0; i < list.size(); i++) {
                   ans[i] = list.get(i);
               }
               return ans;
           }
           public int dfs02(TreeNode node) {
               if (node == null) return 0;
               int sum = node.val + dfs02(node.left) + dfs02(node.right);
                cnt02.put(sum, cnt02.getOrDefault(sum, 0) + 1);
                maxCnt02 = Math.max(maxCnt02, cnt02.get(sum));
                return sum;
           }



       }
}
