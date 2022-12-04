package faced_04_02;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class TreeNode {
        int val;
       TreeNode left;
       TreeNode right;
       TreeNode(int x) { val = x; }

    class Solution {
        /**
         * todo
         * tnnd,不会写
         * @param nums
         * @return
         */
           public TreeNode sortedArrayToBST(int[] nums) {
               int len = nums.length;
               int mid = len / 2;
               TreeNode root = new TreeNode(nums[mid]);
               int lo = len - 1, hi = len + 1;
               while(lo > 0 && hi < len) {
                    TreeNode prevRootL = root;
                    TreeNode prevRootR = root;

               }
               return root;
           }


        /**
         * 选择中间位置左边的数字作为根节点，则根节点的下标为 mid=(left+right)/2，此处的除法为整数除法。
         * 如果二叉搜索树中有 n 个节点，则二叉搜索树的最小高度是 ⌊logn⌋，最大高度是 n - 1。为了使二叉搜索树的高度最小，左右子树的节点数应尽可能接近，
         * 因此我们选择中间数字作为二叉搜索树的根节点，这样分给左右子树的数字个数相同或只相差 1，可以使得二叉搜索树的高度最小。
         * 如果数组长度是奇数，则根节点的选择是唯一的，如果数组长度是偶数，则可以选择中间位置左边的数字作为根节点或者选择中间位置右边的数字作为根节点，
         * 选择不同的数字作为根节点则创建的最小高度二叉搜索树也是不同的
         *
         * 确定最小高度二叉搜索树的根节点之后，其余的数字分别位于最小高度二叉搜索树的左子树和右子树中，
         * 左子树和右子树分别也是最小高度二叉搜索树，因此可以通过递归的方式创建最小高度二叉搜索树。
         * 递归的基准情形是最小高度二叉搜索树不包含任何数字，此时最小高度二叉搜索树为空。
         *在给定中序遍历序列数组的情况下，每一个子树中的数字在数组中一定是连续的，因此可以通过数组下标范围确定子树包含的数字，
         * 下标范围记为[left,right]。对于整个中序遍历序列，下标范围从 left=0 到 right=nums.length−1。当 left>right 时，最小高度二叉搜索树为空。
         *
         *
         *中序遍历，总是选择中间位置左边的数字作为根节点
         * 时间复杂度：O(n)，其中 n 是数组的长度。每个数字只访问一次。
         * 空间复杂度：O(logn)，其中 n 是数组的长度。空间复杂度不考虑返回值，因此空间复杂度主要取决于递归栈的深度，递归栈的深度是 O(logn)
         * @param nums
         * @return
         */
           public TreeNode sortedArrayToBST1(int[] nums) {
                return helper1(nums, 0, nums.length - 1);
           }
           public TreeNode helper1(int[] nums, int left, int right) {
               if(left > right) return null;
               int mid = (left + right) / 2;
               TreeNode root = new TreeNode(nums[mid]);
               root.left = helper1(nums, left, mid - 1);
               root.right = helper1(nums, mid + 1, right);
               return root;
           }


        /**
         * 中序遍历，总是选择中间位置右边的数字作为根节点
         * @param nums
         * @return
         */
           public TreeNode sortedArrayToBST2(int[] nums) {
               return helper2(nums, 0, nums.length - 1);
           }
           public TreeNode helper2(int[] nums, int left, int right) {
               if(left > right) return null;
               int mid = (left + right + 1) / 2;
               TreeNode root = new TreeNode(nums[mid]);
               root.left = helper2(nums, left, mid - 1);
               root.right = helper2(nums, mid + 1, right);
               return root;
           }


        /**
         * 中序遍历，选择任意一个中间位置数字作为根节点
         */
        Random rand = new Random();
//           public TreeNode sortedArrayToBST3(int[] nums) {
//
//           }
           public TreeNode helper3(int[] nums, int left, int right) {
               if(left > right) return null;
               int mid = (left + right + rand.nextInt(2)) / 2;
               TreeNode root = new TreeNode(nums[mid]);
               root.left = helper3(nums, left, mid - 1);
               root.right = helper3(nums, mid + 1, right);
               return root;
           }


        /**
         * f里面的搜索区间，如果判断条件是if(left>right)说明搜索区间就是[left,right]，
         * 如果判断条件是if(left==right)那么搜索区间就是[left,right)
         * @param nums
         * @return
         */
           public TreeNode sortedArrayToBST4(int[] nums) {
               if(nums.length == 0) return null;
               Queue<int[]> rangeQueue = new LinkedList<>();
               Queue<TreeNode> nodeQueue = new LinkedList<>();
               int lo = 0;
               int hi = nums.length - 1;
               int mid = (lo + hi) >> 1;
               TreeNode node = new TreeNode(nums[mid]);
               rangeQueue.add(new int[]{lo, mid - 1});
               rangeQueue.add(new int[]{mid + 1, hi});
               nodeQueue.add(node);
               nodeQueue.add(node);
               while (!rangeQueue.isEmpty()) {
                   int[] range = rangeQueue.poll();
                   TreeNode currentNode = nodeQueue.poll();
                   lo = range[0];
                   hi = range[1];
                   if(lo > hi) continue;
                   mid = (lo + hi) >> 1;
                   int midValue = nums[mid];
                   TreeNode newNode = new TreeNode(midValue);
                   if(midValue > currentNode.val) currentNode.right = newNode;
                   else currentNode.left = newNode;
                   if(lo < hi) {
                       rangeQueue.add(new int[]{lo, mid - 1});
                       rangeQueue.add(new int[]{mid + 1, hi});
                       nodeQueue.add(newNode);
                       nodeQueue.add(newNode);
                   }
               }
               return node;
           }


           public TreeNode sortedArrayToBST5(int[] nums) {
               return helper5(nums, 0, nums.length);
           }
           private TreeNode helper5(int[] nums, int left, int right) {
               if(left == right) return null;
               int mid = left + (right - left) / 2;
               TreeNode node = new TreeNode(nums[mid]);
               node.left = helper5(nums, left, mid);
               node.right = helper1(nums, mid + 1, right);
               return node;
           }





    }
}
