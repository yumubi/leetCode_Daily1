package unique_BST_96;

import java.util.HashMap;
import java.util.Map;

public class Solution {
    /**
     * damieda，muli
     *
     * dp解法
     * 给定一个有序序列 1⋯n，为了构建出一棵二叉搜索树，我们可以遍历每个数字 i，将该数字作为树根，将 1⋯(i−1) 序列作为左子树，将 (i+1)⋯n 序列作为右子树。
     * 接着我们可以按照同样的方式递归构建左子树和右子树。
     * 在上述构建的过程中，由于根的值不同，因此我们能保证每棵二叉搜索树是唯一的。
     * 由此可见，原问题可以分解成规模较小的两个子问题，且子问题的解可以复用。因此，我们可以想到使用动态规划来求解本题。
     * 题目要求是计算不同二叉搜索树的个数。为此，我们可以定义两个函数：
     * 1. G(n): 长度为 n的序列能构成的不同二叉搜索树的个数。
     * 2. F(i,n): 以 i 为根、序列长度为 n 的不同二叉搜索树个数 (1≤i≤n)。
     * G(n) 是我们求解需要的函数。
     * 不同的二叉搜索树的总数 G(n)，是对遍历所有 i(1≤i≤n) 的F(i,n) 之和。
     * 对于边界情况，当序列长度为 11（只有根）或为 00（空树）时，只有一种情况，即G(0)=1,G(1)=1
     *
     *
     *创建以 3 为根、长度为 7 的不同二叉搜索树，整个序列是 [1,2,3,4,5,6,7]，
     * 我们需要从左子序列 [1, 2] 构建左子树，从右子序列 [4,5,6,7] 构建右子树，然后将它们组合（即笛卡尔积）。
     * 对于这个例子，不同二叉搜索树的个数为 F(3,7)。我们将 [[1,2] 构建不同左子树的数量表示为 G(2),
     * 从 [4, 5, 6, 7]构建不同右子树的数量表示为 G(4)，注意到 G(n) 和序列的内容无关，只和序列的长度有关。
     * 于是，F(3,7)=G(2)⋅G(4)。 因此，我们可以得到以下公式：
     * F(i,n)=G(i−1)⋅G(n−i)
     *
     * 时间复杂度 : O(n^2)其中 n 表示二叉搜索树的节点个数。G(n) 函数一共有 n 个值需要求解，每次求解需要 O(n) 的时间复杂度，因此总时间复杂度为 O(n^2)
     * 空间复杂度 : O(n)。我们需要 O(n) 的空间存储 G数组。
     * @param n
     * @return
     */
    public int numTrees(int n) {
        int[] G = new int[n + 1];
        G[0] = 1;
        G[1] = 1;
        for(int i = 2; i <= n; ++i){
            for(int j = 1; j <= i; ++j) {
                G[i] += G[j - 1] * G[i - j];
            }
        }
        return G[n];
    }


    /**
     * 卡特兰数
     * @param n
     * @return
     */
    public int numTrees1(int n) {
        long C = 1;
        for(int i = 0; i < n; ++i) C = C * 2 * (2 * i + 1) / (i + 2);
        return (int)C;
    }

    /**
     * 常规递归
     * @param n
     * @return
     */
    public int numTrees2(int n) {
        //如果只有0，或者1个节点，则可能的子树情况为1种
        if(n == 0 || n == 1) return 1;
        int count = 0;
        for(int i = 1; i <= n; i++) {
            //当用i这个节点当做根节点时

            //左边有多少种子树
            int leftNum = numTrees2(i - 1);
            int rightNum = numTrees2(n - i);
            //乘起来就是当前节点的子树个数
            count += leftNum * rightNum;
        }
        return count;
    }

    /**
     * 带有memo的递归解法
     */
    Map<Integer, Integer> map = new HashMap<>();
    public int numTrees3(int n) {
        if(n == 0 || n == 1) return 1;
        if(map.containsKey(n)) return map.get(n);
        int count = 0;
        for(int i = 1; i <= n; i++) {
            int leftNum = numTrees3(i - 1);
            int rightNum = numTrees3(n - i);
            count += leftNum * rightNum;
        }
        map.put(n, count);
        return count;
    }




}
