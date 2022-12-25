package house_RobberIII_337;

import java.util.HashMap;
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

           //不会
           public boolean RobFather = false;
           public int money = 0;

           public int rob(TreeNode root) {
               if (root == null) return 0;
               if(RobFather) {
                   RobFather = false;
                   return rob(root.left) + rob(root.right);
               }
              int robSub = rob(root.left) + rob(root.right);
               if(robSub > root.val) {
                   money += robSub;
                   return money;
               }
               else {
                   money += root.val;
                   RobFather = true;
                   return money;
               }
           }


           /**
            * dp
            * 简化一下这个问题：一棵二叉树，树上的每个点都有对应的权值，每个点有两种状态（选中和不选中），问在不能同时选中有父子关系的点的情况下，能选中的点的最大权值和是多少。
            * 我们可以用f(o) 表示选择 o 节点的情况下，o 节点的子树上被选择的节点的最大权值和；
            * g(o) 表示不选择 o 节点的情况下，o 节点的子树上被选择的节点的最大权值和；l 和 r 代表 o 的左右孩子。
            *
            *
            *       当 o 被选中时，o 的左右孩子都不能被选中，故 o 被选中情况下子树上被选中点的最大权值和为 l 和 r 不被选中的最大权值和相加，即 f(o)=g(l)+g(r)。
            *       当 o 不被选中时，o 的左右孩子可以被选中，也可以不被选中。对于 o 的某个具体的孩子 x，它对 o 的贡献是 x 被选中和不被选中情况下权值和的较大值。
            *       故 g(o)=max{f(l),g(l)}+max{f(r),g(r)}。
            *
            *至此，我们可以用哈希表来存 f 和 g 的函数值，用深度优先搜索的办法后序遍历这棵二叉树，我们就可以得到每一个节点的 f 和 g。
            * 根节点的 f 和 g 的最大值就是我们要找的答案。
            *
            *
            */
           Map<TreeNode, Integer> f = new HashMap<TreeNode, Integer>();
           Map<TreeNode, Integer> g = new HashMap<TreeNode, Integer>();
           public int rob01(TreeNode root) {
               dfs(root);
               return Math.max(f.getOrDefault(root, 0), g.getOrDefault(root, 0));
           }
           public void dfs(TreeNode node) {
               if(node == null) return;
               dfs(node.left);
               dfs(node.right);
               f.put(node, node.val + g.getOrDefault(node.left, 0) + g.getOrDefault(node.right, 0));
               g.put(node, Math.max(f.getOrDefault(node.left, 0), g.getOrDefault(node.left, 0)) +
                       Math.max(f.getOrDefault(node.right, 0), g.getOrDefault(node.right, 0)));
           }

           //假设二叉树的节点个数为 n。
           //我们可以看出，以上的算法对二叉树做了一次后序遍历，时间复杂度是 O(n)；由于递归会使用到栈空间，空间代价是 O(n)，哈希表的空间代价也是 O(n)，故空间复杂度也是 O(n)。
           //我们可以做一个小小的优化，我们发现无论是 f(o) 还是 g(o)，他们最终的值只和 f(l)、g(l)、f(r)、g(r) 有关，
           // 所以对于每个节点，我们只关心它的孩子节点们的 f 和 g 是多少。我们可以设计一个结构，表示某个节点的 f 和 g 值，在
           // 每次递归返回的时候，都把这个点对应的 f 和 g 返回给上一级调用，这样可以省去哈希表的空间。
           //时间复杂度：O(n)。上文中已分析。
           //空间复杂度：O(n)。虽然优化过的版本省去了哈希表的空间，但是栈空间的使用代价依旧是 O(n)，故空间复杂度不变。


           public int rob02(TreeNode root) {
               int[] rootStatus = dfs02(root);
               return Math.max(rootStatus[0], rootStatus[1]);
           }
           public int[] dfs02(TreeNode node) {
               if(node == null) return new int[]{0, 0};
               int[] l = dfs02(node.left);
               int[] r = dfs02(node.right);
               int selected = node.val + l[1] + r[1];
               int notSelected = Math.max(l[0], l[1]) + Math.max(r[0], r[1]);
               return new int[]{selected, notSelected};
           }


           /**
            * 暴力递归
            * 4 个孙子偷的钱 + 爷爷的钱 VS 两个儿子偷的钱 哪个组合钱多，就当做当前节点能偷的最大钱数。这就是动态规划里面的最优子结构
            * 由于是二叉树，这里可以选择计算所有子节点
            * 4 个孙子投的钱加上爷爷的钱如下
            * int method1 = root.val + rob(root.left.left) + rob(root.left.right) + rob(root.right.left) + rob(root.right.right)
            * 两个儿子偷的钱如下
            * int method2 = rob(root.left) + rob(root.right);
            * 挑选一个钱数多的方案则
            * int result = Math.max(method1, method2);
            * @param root
            * @return
            */
           public int rob03(TreeNode root) {
               if(root == null) return 0;
               int money = root.val;
               if(root.left != null) {
                   money += rob03(root.left.left) + rob(root.left.right);
               }
               if(root.right != null) {
                   money += rob03(root.right.left) + rob(root.right.right);
               }
               return Math.max(money, rob(root.left) + rob(root.right));
           }


           /**
            * 记忆化递归
            * 由于二叉树不适合拿数组当缓存，我们这次使用哈希表来存储结果，TreeNode 当做 key，能偷的钱当做 value
            * @param root
            * @return
            */
           public int rob04(TreeNode root) {
               HashMap<TreeNode, Integer> memo = new HashMap<>();
               return robInternal(root, memo);
           }
           public int robInternal(TreeNode root, HashMap<TreeNode, Integer> memo) {
               if(root == null) return 0;
               if(memo.containsKey(root)) return memo.get(root);
               int money = root.val;
               if(root.left != null) {
                   money += robInternal(root.left.left, memo) + robInternal(root.left.right, memo);
               }
               if(root.right != null) {
                   money += robInternal(root.right.left, memo) + robInternal(root.right.right, memo);
               }
               int result = Math.max(money, robInternal(root.left, memo) + robInternal(root.right, memo));
               memo.put(root, result);
               return result;
           }


           /**
            * dp
            * 每个节点可选择偷或者不偷两种状态，根据题目意思，相连节点不能一起偷
            * 当前节点选择偷时，那么两个孩子节点就不能选择偷了
            * 当前节点选择不偷时，两个孩子节点只需要拿最多的钱出来就行(两个孩子节点偷不偷没关系)
            *
            * 我们使用一个大小为 2 的数组来表示 int[] res = new int[2] 0 代表不偷，1 代表偷
            * 任何一个节点能偷到的最大钱的状态可以定义为：
            * 当前节点选择不偷：当前节点能偷到的最大钱数 = 左孩子能偷到的钱 + 右孩子能偷到的钱
            * 当前节点选择偷：当前节点能偷到的最大钱数 = 左孩子选择自己不偷时能得到的钱 + 右孩子选择不偷时能得到的钱 + 当前节点的钱数

            * @param root
            * @return
            */
           public int rob05(TreeNode root) {
               int[] result = robInternal05(root);
               return Math.max(result[0], result[1]);
           }
           public int[] robInternal05(TreeNode root) {
               if(root == null) return new int[2];
               int[] result = new int[2];
               int[] left = robInternal05(root.left);
               int[] right = robInternal05(root.right);

               result[0] = Math.max(left[0], left[1]) + Math.max(right[0], right[1]);
               result[1] = left[0] + right[0] + root.val;

               return result;

           }


       }
}
