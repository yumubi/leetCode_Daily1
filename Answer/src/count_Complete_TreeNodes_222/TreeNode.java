package count_Complete_TreeNodes_222;

import java.util.LinkedList;
import java.util.Queue;

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
            * 因此从根节点出发，每次访问左子节点，直到遇到叶子节点，该叶子节点即为完全二叉树的最左边的节点，经过的路径长度即为最大层数 h。
            * 当 0≤i<h 时，第 i 层包含 2^i个节点，最底层包含的节点数最少为 1，最多为 2^h
            *
            * 当最底层包含 1 个节点时，完全二叉树的节点个数是2^h
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
            * 因此总时间复杂度是 O(h^2)。由于完全二叉树满足 2^h <= n < 2^{h+1}
            * 因此有 O(h)=O(logn)，O(h^2)=O(log^2 n)
            * 空间复杂度：O(1)。只需要维护有限的额外空间。

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

//                    1            h = 0
//                   / \
//                  2   3          h = 1
//                 / \  /
//                4  5 6           h = 2
//           现在这个树中的值都是节点的编号，最底下的一层的编号是[2^h ，2^h - 1]，现在h = 2，也就是4, 5, 6, 7。
//                   4, 5, 6, 7对应二进制分别为 100 101 110 111 不看最左边的1，从第二位开始，0表示向左，1表示向右，正好可以表示这个节点相对于根节点的位置。
//           比如4的 00 就表示从根节点 向左 再向左。6的 10 就表示从根节点 向右 再向左
//
//           那么想访问最后一层的节点就可以从节点的编号的二进制入手。从第二位开始的二进制位表示了最后一层的节点相对于根节点的位置。
//           那么就需要一个bits = 2^(h - 1) 上面例子中的bits就是2，对应二进制为010。这样就可以从第二位开始判断。（树三层高，需要向左或向右走两次才能到叶子）
//           比如看5这个节点存不存在，先通过位运算找到编号为5的节点相对于根节点的位置。010 & 101 发现第二位是0，说明从根节点开始，第一步向左走。
//           之后将bit右移一位，变成001。001 & 101 发现第三位是1，那么第二步向右走。
//           最后bit为0，说明已经找到编号为5的这个节点相对于根节点的位置，看这个节点是不是空，不是说明存在，exist返回真
//           编号为5的节点存在，说明总节点数量一定大于等于5。所以二分那里low = mid
//
//           再比如看7存不存在，010 & 111 第二位为1，第一部从根节点向右；001 & 111 第三位也为1，第二步继续向右。
//           然后判断当前节点是不是null，发现是null，exist返回假。
//           编号为7的节点不存在，说明总节点数量一定小于7。所以high = mid - 1



           //dfs
//           public int countNodes2(TreeNode root) {
//               if(root == null) return 0;
//               return countNodes2(root.left) + countNodes2(root.right) + 1;
//           }

           /**
            * 完全二叉树的定义：它是一棵空树或者它的叶子节点只出在最后两层，若最后一层不满则叶子节点只在最左侧。
            * 再来回顾一下满二叉的节点个数怎么计算，如果满二叉树的层数为h，则总节点数为：2^h - 1.
            * 那么我们来对 root 节点的左右子树进行高度统计，分别记为 left 和 right，有以下两种结果：
            * left == right。这说明，左子树一定是满二叉树，因为节点已经填充到右子树了，左子树必定已经填满了。
            * 所以左子树的节点总数我们可以直接得到，是 2^left - 1，加上当前这个 root 节点，则正好是 2^left。再对右子树进行递归统计。
            * left != right。说明此时最后一层不满，但倒数第二层已经满了，可以直接得到右子树的节点个数。
            * 同理，右子树节点 +root 节点，总数为 2^right。再对左子树进行递归查找。
            * @param root
            * @return
            */
           public int countNodes2(TreeNode root) {
               if(root == null) return 0;
               int left = countLevel(root.left);
               int right = countLevel(root.right);
               if(left == right) return countNodes2(root.right) + (1 << left);
               else return countNodes2(root.left) + (1 << right);
           }
           private int countLevel(TreeNode root) {
               int level = 0;
               while(root != null) {
                   level++;
                   root = root.left;
               }
               return level;
           }



           public int countNodes3(TreeNode root) {
               if(root == null) return 0;
               int count = 0;
               Queue<TreeNode> list = new LinkedList<>();
               list.add(root);
               while(!list.isEmpty()) {
                   TreeNode node = list.poll();
                   count++;
                   if(node.left != null) list.add(node.left);
                   if(node.right != null) list.add(node.right);
               }
               return count;
           }


           //根据完全二叉树的特性，从左子树找树的高度
           //这里最小高度为1，
           //先计算树的高度height，然后计算右子树的高度
           public int countNodes4(TreeNode root) {
               int height = treeHeight(root);
               if(height == 0 || height == 1) return height;
               //如果右子树高度为树的高度减一，说明左子树是满二叉树，左子树可以通过公式计算，只需要递归右子树即可
               //注意这里的计算，左子树的数量实际上是（1<< (height - 1)) - 1,加上根节点即为1 << (height - 1)
               if(treeHeight(root.right) == height - 1) return (1 << (height - 1)) + countNodes4(root.right);
               else return (1 << (height- 2)) + countNodes4(root.left);
           }

           private int treeHeight(TreeNode root) {
               return root == null ? 0 : 1 + treeHeight(root.left);
           }

//
//           public int countNodes5(TreeNode root) {
//               int count = 0, height = treeHeight(root);
//               while(root != null) {
//                   if(treeHeight(root.right) == height - 1) {
//                       count += 1 << height - 1;
//                       root = root.right;
//                   } else {
//                       count += 1 << height - 2;
//                       root = root.left;
//                   }
//                   height--;
//               }
//               return count;
//           }

//           public int countNodes(TreeNode root) {
//               if (root == null)
//                   return 0;
//               //计算高度，注意这里不是树的实际高度
//               int height = treeHeight(root);
//               if (treeHeight(root.left) == height) {//左子树是满二叉树，通过公式计算
//                   return (1 << height) + countNodes(root.right);
//               } else {//右子树是满二叉树，通过公式计算
//                   return (1 << height - 1) + countNodes(root.left);
//               }
//           }
//
//           //计算树的高度，注意这个结果不是树的实际高度，如果树是满二叉树，他就是树的
//           //高度，如果不是满二叉树，他就是树的高度减1
//           private int treeHeight(TreeNode root) {
//               return root == null ? 0 : 1 + treeHeight(root.right);//注意这里遍历的是树的右结点
//           }





       }
}
