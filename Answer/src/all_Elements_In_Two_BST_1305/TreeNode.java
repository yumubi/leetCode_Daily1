package all_Elements_In_Two_BST_1305;

import java.util.ArrayList;
import java.util.Collections;
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
           public List<Integer> getAllElements(TreeNode root1, TreeNode root2) {
               List<Integer> list1 = new ArrayList<>();
                inorderTraversal(root1, list1);
                inorderTraversal(root2, list1);
               Collections.sort(list1);
               return list1;
           }

           public void  inorderTraversal(TreeNode root, List<Integer> list) {
               if(root == null) return;
                inorderTraversal(root.left, list);
               list.add(root.val);
                inorderTraversal(root.right, list);
           }


           /**
            * 中序遍历+归并
            * 根据上述定义，我们可以用中序遍历访问二叉搜索树，即按照访问左子树——根节点——右子树的方式遍历这棵树，
            * 而在访问左子树或者右子树的时候也按照同样的方式遍历，直到遍历完整棵树。遍历结束后，就得到了一个有序数组。
            * 由于整个遍历过程天然具有递归的性质，我们可以直接用递归函数来模拟这一过程。具体描述见 94. 二叉树的中序遍历 的 官方题解。
            * 中序遍历这两棵二叉搜索树，可以得到两个有序数组。然后可以使用双指针方法来合并这两个有序数组，
            * 这一方法将两个数组看作两个队列，每次从队列头部取出比较小的数字放到结果中（头部相同时可任取一个）。
            * 时间复杂度：O(n+m)，其中 n 和 m 分别为两棵二叉搜索树的节点个数。
            * 空间复杂度：O(n+m)。存储数组以及递归时的栈空间均为 O(n+m)。
            * @param root1
            * @param root2
            * @return
            */
           public List<Integer> getAllElements01(TreeNode root1, TreeNode root2) {
               List<Integer> nums1 = new ArrayList<Integer>();
               List<Integer> nums2 = new ArrayList<Integer>();
               inorder(root1, nums1);
               inorder(root2, nums2);

               List<Integer> merged = new ArrayList<Integer>();
               int p1 = 0, p2 = 0;
               while (true) {
                   if(p1 == nums1.size()) {
                       merged.addAll(nums2.subList(p2, nums2.size()));
                       break;
                   }
                   if(p2 == nums2.size()) {
                       merged.addAll(nums1.subList(p1, nums1.size()));
                       break;
                   }
                   if(nums1.get(p1) < nums2.get(p2)) merged.add(nums1.get(p1++));
                   else merged.add(nums2.get(p2++));
               }
               return merged;
           }
           public void inorder(TreeNode node, List<Integer> res) {
               if(node != null) {
                   inorder(node.left, res);
                   res.add(node.val);
                   inorder(node.right, res);
               }
           }


           /**
            * 中序遍历+归并
            * 利用 BST 中序遍历的有序性质，我们可以先对两棵树进行中序遍历，从而将树的结构转换为线性结构。
            * 将两个有序序列合并成一个有序序列则是利用了经典的「归并排序」。
            * 时间复杂度：令 n 和 m 分别为两棵树的节点数量，跑中序遍历的复杂度为 O(n+m)，构建答案复杂度为 O(max(m,n))。整体复杂度为 O(n+m)
            * 空间复杂度：O(n + m)
            */
           int INF = 0x3f3f3f3f;
           public List<Integer> getALlElements02(TreeNode root1, TreeNode root2) {
               List<Integer> ans = new ArrayList<>();
               List<Integer> l1 = new ArrayList<>(), l2 = new ArrayList<>();
               dfs(root1, l1);
               dfs(root2, l2);
               int n = l1.size(), m = l2.size(), i = 0, j = 0;
               while (i < n || j < m) {
                   int a = i < n ? l1.get(i) : INF, b = j < m ? l2.get(j) : INF;
                   if(a <= b) {
                       ans.add(a);
                       i++;
                   } else {
                       ans.add(b);
                       j++;
                   }
               }
               return ans;
           }
           void dfs(TreeNode root, List<Integer> list) {
               if(root == null) return;
               dfs(root.left, list);
               list.add(root.val);
               dfs(root.right, list);
           }


       }

}
