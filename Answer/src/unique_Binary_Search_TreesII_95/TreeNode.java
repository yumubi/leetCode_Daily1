package unique_Binary_Search_TreesII_95;

import java.util.LinkedList;
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
           /**
            * todo 这题得好好研究
            * fw依旧不会写，先超哥题解
            *
            * 回溯
            * 二叉搜索树关键的性质是根节点的值大于左子树所有节点的值，小于右子树所有节点的值，且左子树和右子树也同样为二叉搜索树。因此在生成所有可行的二叉搜索树的时候，
            * 假设当前序列长度为 n，如果我们枚举根节点的值为 i，那么根据二叉搜索树的性质我们可以知道左子树的节点值的集合为 [1…i−1]，
            * 右子树的节点值的集合为 [i+1…n]。而左子树和右子树的生成相较于原问题是一个序列长度缩小的子问题，因此我们可以想到用回溯的方法来解决这道题目。
            *
            * 我们定义 generateTrees(start, end) 函数表示当前值的集合为 [start,end]，返回序列 [start,end] 生成的所有可行的二叉搜索树。
            * 按照上文的思路，我们考虑枚举[start,end] 中的值 i 为当前二叉搜索树的根，那么序列划分为了 [start,i−1] 和 [i+1,end] 两部分。
            * 我们递归调用这两部分，即 generateTrees(start, i - 1) 和 generateTrees(i + 1, end)，获得所有可行的左子树和可行的右子树，
            * 那么最后一步我们只要从可行左子树集合中选一棵，再从可行右子树集合中选一棵拼接到根节点上，并将生成的二叉搜索树放入答案数组即可。
            * 递归的入口即为 generateTrees(1, n)，出口为当 start>end 的时候，当前二叉搜索树为空，返回空节点即可。
            *
            * 时间复杂度：整个算法的时间复杂度取决于「可行二叉搜索树的个数」，而对于 n个点生成的二叉搜索树数量等价于数学上第 n个「卡特兰数」，用 G(n)
            *
            * @param n
            * @return
            */
           public List<TreeNode> generateTrees(int n) {
                if(n == 0) return new LinkedList<TreeNode>();
                return generateTrees(1, n);
           }

           public List<TreeNode> generateTrees(int start, int end) {
               List<TreeNode> allTrees = new LinkedList<TreeNode>();
               if(start > end) {
                   allTrees.add(null);
                   return allTrees;
               }
               //枚举可行根节点
               for(int i = start; i <= end; i++) {
                   //获得所有可行的左子树的集合
                   List<TreeNode> leftTrees = generateTrees(start, i - 1);
                   List<TreeNode> rightTrees = generateTrees(i + 1, end);
                   //从左子树集合中选出一课左子树，从右子树集合中选出一个右子树，拼接到根节点
                   for(TreeNode left : leftTrees) {
                       for(TreeNode right : rightTrees) {
                           TreeNode currTree = new TreeNode(i);
                           currTree.left = left;
                           currTree.right = right;
                           allTrees.add(currTree);
                       }
                   }
               }

               return allTrees;
           }


//           public List<TreeNode> generateTrees1(int n) {
//
//
//
//
//           }



       }

}
