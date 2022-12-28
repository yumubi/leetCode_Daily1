//package maximum_Binary_Tree_654;
//
//import java.util.ArrayDeque;
//import java.util.Arrays;
//import java.util.Deque;
//
//public class TreeNode {
//    int val;
//       TreeNode left;
//       TreeNode right;
//       TreeNode() {}
//       TreeNode(int val) { this.val = val; }
//       TreeNode(int val, TreeNode left, TreeNode right) {
//           this.val = val;
//           this.left = left;
//           this.right = right;
//       }
//
//       class Solution {
//           public TreeNode constructMaximumBinaryTree(int[] nums) {
//               TreeNode root = new TreeNode();
//               partition(nums, 0, nums.length, root);
//               return root;
//           }
//
//
//           public void partition(int[] nums, int start, int end, TreeNode root) {
//               if(start > end) return;
//               int max = nums[start];
//               int idx = start;
//               for(int i = start; i < end; i++) {
//                   if (nums[i] > max) {
//                       max = nums[i];
//                       idx = i;
//                   }
//               }
//
//               root.val = max;
//               int startL = start, endL = idx, startR = idx + 1, endR = end;
//
//              if(startL < endL) {
//                  root.left = new TreeNode();
//                  partition(nums, startL, endL, root.left);
//              }
//              if(startR < endR) {
//                  root.right = new TreeNode();
//                  partition(nums, startR, endR, root.right);
//              }
//           }
//
//
//           /**
//            * 递归
//            * 我们用递归函数 construct(nums,left,right) 表示对数组nums 中从 nums[left] 到 nums[right] 的元素构建一棵树。
//            * 我们首先找到这一区间中的最大值，记为 nums 中从nums[best]，这样就确定了根节点的值。随后我们就可以进行递归：
//            * 左子树为 construct(nums,left,best−1)；
//            * 右子树为 construct(nums,best+1,right)。
//            * 当递归到一个无效的区间（即 left>right）时，便可以返回null
//            * 时间复杂度：O(n^2)其中 n 是数组nums 的长度。在最坏的情况下，数组严格递增或递减，需要递归 n 层，
//            * 第 i(0≤i<n) 层需要遍历 n−i 个元素以找出最大值，总时间复杂度为 O(n^2)
//            * 空间复杂度：O(n)，即为最坏情况下需要使用的栈空间。
//            * @param nums
//            * @return
//            */
//           public TreeNode constructMaximumBinaryTree01(int[] nums) {
//               return construct(nums, 0, nums.length - 1);
//           }
//           public TreeNode construct(int[] nums, int left, int right) {
//               if(left > right) return null;
//               int best = left;
//               for(int i = left + 1; i <= right; i++) {
//                   if(nums[i] > nums[best]) best = i;
//               }
//               TreeNode node = new TreeNode(nums[best]);
//               node.left = construct(nums, left, best - 1);
//               node.right = construct(nums, best + 1, right);
//               return node;
//           }
//
//
//           public TreeNode constructMaximumBinaryTree02(int[] nums) {
//               int n = nums.length;
//               Deque<Integer> stack = new ArrayDeque<Integer>();
//               int[] left = new int[n];
//               int[] right = new int[n];
//               Arrays.fill(left, - 1);
//               Arrays.fill(right, - 1);
//               TreeNode[] tree = new TreeNode[n];
//               for(int i = 0; i < n; i++) {
//                   tree[i] = new TreeNode(nums[i]);
//                   while(!stack.isEmpty() && nums[i] > nums[stack.peek()]) {
//                       right[stack.pop()] = i;
//                   }
//                   if(!stack.isEmpty()) {
//                       left[i] = stack.peek();
//                   }
//                   stack.push(i);
//               }
//               TreeNode root = null;
//               for(int i = 0; i < n; i++) {
//                   if(left[i] == -1 && right[i] == -1) {
//                       root = tree[i];
//                   } else if(right[i] == -1 || left[i] != -1 && nums[left[i]] < nums[right[i]]) {
//                       tree[left[i]].right = tree[i];
//                   } else {
//                       tree[right[i]].left = tree[i];
//                   }
//               }
//           }
//
//
//
//
//
//
//       }
//
//
//}
