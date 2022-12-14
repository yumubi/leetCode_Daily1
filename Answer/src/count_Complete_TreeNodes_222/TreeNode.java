package count_Complete_TreeNodes_222;

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
           int cnt = 0;
           public int countNodes(TreeNode root) {
                if(root == null) return 0;
                dfs(root);
                return cnt;
           }

           void dfs(TreeNode root) {
               if(root == null) return;
               cnt++;
               dfs(root.left);
               dfs(root.right);
           }


           /**
            * 二分查找+位运算
            * 对于任意二叉树，都可以通过广度优先搜索或深度优先搜索计算节点个数，时间复杂度和空间复杂度都是 O(n)，
            * 其中 n 是二叉树的节点个数。这道题规定了给出的是完全二叉树，因此可以利用完全二叉树的特性计算节点个数。
            *
            * 规定根节点位于第 0层，完全二叉树的最大层数为h。根据完全二叉树的特性可知，完全二叉树的最左边的节点一定位于最底层，
            * 因此从根节点出发，每次访问左子节点，直到遇到叶子节点，该叶子节点即为完全二叉树的最左边的节点，经过的路径长度即为最大层数 hh。
            * 当 0≤i<h 时，第 i 层包含 2^i个节点，最底层包含的节点数最少为 1，最多为 2^h
            *
            * 当最底层包含 11 个节点时，完全二叉树的节点个数是2^h
            * 当最底层包含 2^h个节点时，完全二叉树的节点个数是2^(h+1) - 1
            *
            * 因此对于最大层数为 h 的完全二叉树，节点个数一定在 [2^h,2^{h+1}-1]的范围内，可以在该范围内通过二分查找的方式得到完全二叉树的节点个数
            * 具体做法是，根据节点个数范围的上下界得到当前需要判断的节点个数 k，如果第 k个节点存在，则节点个数一定大于或等于 k，
            * 如果第 k个节点不存在，则节点个数一定小于 k，由此可以将查找的范围缩小一半，直到得到节点个数
            *
            * 如何判断第 k 个节点是否存在呢？如果第 k 个节点位于第 h 层，则 k 的二进制表示包含 h+1 位，其中最高位是 1，
            * 其余各位从高到低表示从根节点到第 k个节点的路径，0 表示移动到左子节点，1 表示移动到右子节点。
            * 通过位运算得到第 k 个节点对应的路径，判断该路径对应的节点是否存在，即可判断第 k个节点是否存在。
            *
            *
            * 时间复杂度：O(log^2 n)其中 n 是完全二叉树的节点数。
            * 首先需要 O(h) 的时间得到完全二叉树的最大层数，其中 h 是完全二叉树的最大层数。
            * 使用二分查找确定节点个数时，需要查找的次数为 O(log 2^h)=O(h)，
            * 每次查找需要遍历从根节点开始的一条长度为 h 的路径，需要 O(h) 的时间，因此二分查找的总时间复杂度是 O(h^2)
            * 因此总时间复杂度是 O(h^2)。由于完全二叉树满足 2^h  n < 2^{h+1}
            *  ，因此有 O(h)=O(\log n)O(h)=O(logn)，O(h^2)=O(\log^2 n)O(h
            * 2
            *  )=O(log
            * 2
            *  n)。
            *
            * 空间复杂度：O(1)。只需要维护有限的额外空间。
            *

            * @param root
            * @return
            */
           public int countNodes1(TreeNode root) {
               if(root == null) return 0;
               int level = 0;
               TreeNode node = root;
               while(node.left != null) {
                   level++;
                   node = node.left;
               }

               int low = 1 << level, high = (1 << (level + 1)) - 1;
               while(low < high) {
                   int mid = (high - low + 1) / 2 + low;
                   if(exists(root, level, mid)) {
                       low = mid;
                   } else {
                       high = mid - 1;
                   }
               }
               return low;
           }


           public boolean exists(TreeNode root, int level, int k) {
               int bits = 1 << (level - 1);
               TreeNode node = root;
               while(node != null && bits > 0) {
                   if((bits & k) == 0) node = node.left;
                   else node = node.right;
                   bits >>= 1;
               }
               return node != null;
           }
       }
}
