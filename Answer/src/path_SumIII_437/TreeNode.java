package path_SumIII_437;

import java.util.*;

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
             * dfs
             * 穷举所有的可能，我们访问每一个节点 node，检测以 node 为起始节点且向下延深的路径有多少种。我们递归遍历每一个节点的所有可能的路径，
             * 然后将这些路径数目加起来即为返回结果。
             * 我们首先定义 rootSum(p,val) 表示以节点 p 为起点向下且满足路径总和为 val 的路径数目。
             * 我们对二叉树上每个节点 p 求出 rootSum(p,targetSum)，然后对这些路径数目求和即为返回结果。
             * 我们对节点 p 求 rootSum(p,targetSum) 时，以当前节点 p 为目标路径的起点递归向下进行搜索。假设当前的节点 p 的值为val，
             * 我们对左子树和右子树进行递归搜索，对节点 p 的左孩子节点 pl求出rootSum(pl,targetSum−val)，
             * 以及对右孩子节点pr求出rootSum(pr,targetSum−val)。
             * 节点 p 的rootSum(p,targetSum) 即等于rootSum(pl,targetSum−val) 与 rootSum(pr,targetSum−val) 之和
             * 同时我们还需要判断一下当前节点 p的值是否刚好等于 targetSum。
             * 我们采用递归遍历二叉树的每个节点 p，对节点 p 求 rootSum(p,val)，然后将每个节点所有求的值进行相加求和返回。
             *
             * 时间复杂度：O(N^2)，其中 N 为该二叉树节点的个数。对于每一个节点，求以该节点为起点的路径数目时，
             * 则需要遍历以该节点为根节点的子树的所有节点，因此求该路径所花费的最大时间为 O(N)，我们会对每个节点都求一次以该节点为起点的路径数目，因此时间复杂度为 O(N^2)
             * 空间复杂度：O(N)，考虑到递归需要在栈上开辟空间。
             * @param root
             * @param targetSum
             * @return
             */
            public int pathSum(TreeNode root, int targetSum) {
                if(root == null) return 0;
                int ret = rootSum(root, targetSum);
                ret += pathSum(root.left, targetSum);
                ret += pathSum(root.right, targetSum);
                return ret;
            }

            public int rootSum(TreeNode root, int targetSum) {
                int ret = 0;
                if(root == null) return 0;
                int val = root.val;
                if(val == targetSum) ret++;
                ret += rootSum(root.left, targetSum - val);
                ret += rootSum(root.right, targetSum - val);
                return ret;
            }

            /**
             * 前缀和
             * 我们仔细思考一下，解法一中应该存在许多重复计算。我们定义节点的前缀和为：由根结点到当前结点的路径上所有节点的和。我们利用先序遍历二叉树，
             * 记录下根节点 root 到当前节点 p 的路径上除当前节点以外所有节点的前缀和，
             * 在已保存的路径前缀和中查找是否存在前缀和刚好等于当前节点到根节点的前缀和 curr 减去targetSum。
             * 对于空路径我们也需要保存预先处理一下，此时因为空路径不经过任何节点，因此它的前缀和为 00。
             *
             * 假设根节点为root，我们当前刚好访问节点node，则此时从根节点root 到节点node 的路径（无重复节点）刚好为 root→p1→p2→…→pk→node，
             * 此时我们可以已经保存了节点 p_1, p_2, p_3,…,pk的前缀和，并且计算出了节点 node 的前缀和。
             * 假设当前从根节点 root 到节点 node 的前缀和为curr，则此时我们在已保存的前缀和查找是否存在前缀和刚好等于curr−targetSum。
             * 假设从根节点 root 到节点node的路径中存在节点pi到根节点 root 的前缀和为curr−targetSum，则节点pi+1到 node 的路径上所有节点的和一定为 targetSum。
             * 我们利用深度搜索遍历树，当我们退出当前节点时，我们需要及时更新已经保存的前缀和。
             *
             * 时间复杂度：O(N)，其中 N 为二叉树中节点的个数。利用前缀和只需遍历一次二叉树即可。
             * 空间复杂度：O(N)
             *
             * @param root
             * @param targetSum
             * @return
             */
            public int pathSum01(TreeNode root, int targetSum) {
                Map<Long, Integer> prefix = new HashMap<Long, Integer>();
                prefix.put(0L, 1);
                return dfs01(root, prefix, 0, targetSum);
            }
            public int dfs01(TreeNode root, Map<Long, Integer> prefix, long curr, int targetSum) {
                if(root == null) return 0;
                int ret = 0;
                curr += root.val;
                ret = prefix.getOrDefault(curr - targetSum, 0);
                prefix.put(curr, prefix.getOrDefault(curr, 0) + 1);
                ret += dfs01(root.left, prefix, curr, targetSum);
                ret += dfs01(root.right, prefix, curr, targetSum);
                prefix.put(curr, prefix.getOrDefault(curr, 0) - 1);

                return ret;
            }


            /**
             * 树的遍历+dfs
             * 一个朴素的做法是搜索以每个节点为根的（往下的）所有路径，并对路径总和为 targetSum 的路径进行累加统计。
             * 使用 dfs1 来搜索所有节点，复杂度为 O(n)；在 dfs1 中对于每个当前节点，使用 dfs2 搜索以其为根的所有（往下的）路径，同时累加路径总和为 targetSum 的所有路径，
             * 复杂度为 O(n) O(n^2)数据范围为 10^3可以过。
             * 间复杂度：O(n^2)
             * 空间复杂度：忽略递归带来的额外空间开销，复杂度为 O(1)
             */
            int ans, t;
            public int pathSum02(TreeNode root, int _t) {
                t = _t;
                dfs1(root);
                return ans;
            }
            void dfs1(TreeNode root) {
                if(root == null) return;
                dfs2(root, root.val);
                dfs1(root.left);
                dfs1(root.right);
            }
            void dfs2(TreeNode root, long val) {
                if(val == t) ans++;
                if(root.left != null) dfs2(root.left, val + root.left.val);
                if(root.right != null) dfs2(root.right, val + root.right.val);
            }


            /**
             * 树的遍历+前缀和
             * 在「解法一」中，我们统计的是以每个节点为根的（往下的）所有路径，也就是说统计的是以每个节点为「路径开头」的所有合法路径。
             * 本题的一个优化切入点为「路径只能往下」，因此如果我们转换一下，统计以每个节点为「路径结尾」的合法数量的话，配合原本就是「从上往下」进行的数的遍历（最完整的路径必然是从原始根节点到当前节点的唯一路径），
             * 相当于只需要在完整路径中找到有多少个节点到当前节点的路径总和为targetSum。
             * 于是这个树上问题彻底转换一维问题：求解从原始起点（根节点）到当前节点 b 的路径中，有多少节点 a 满足 sum[a...b]=targetSum，
             * 由于从原始起点（根节点）到当前节点的路径唯一，因此这其实是一个「一维前缀和」问题。
             *
             * 具体的，我们可以在进行树的遍历时，记录下从原始根节点root 到当前节点cur 路径中，从root 到任意中间节点 x 的路径总和，
             * 配合哈希表，快速找到满足以cur为「路径结尾」的、使得路径总和为 targetSum 的目标「路径起点」有多少个。
             * 一些细节：由于我们只能统计往下的路径，但是树的遍历会同时搜索两个方向的子树。因此我们应当在搜索完以某个节点为根的左右子树之后，
             * 应当回溯地将路径总和从哈希表中删除，防止统计到跨越两个方向的路径。
             *
             *
             *      HashMap存的是什么
             * HashMap的key是前缀和， value是该前缀和的节点数量，记录数量是因为有出现复数路径的可能。
             * 拿图说明：
             * 下图树中，前缀和为1的节点有两个: 1, 0
             * 所以路径和为2的路径数就有两条: 0 --> 2, 2
             *       1
             *      /
             *     0
             *    /
             *   2
             *
             *      恢复状态的意义
             *由于题目要求：路径方向必须是向下的（只能从父节点到子节点）
             * 当我们讨论两个节点的前缀和差值时，有一个前提：
             * 一个节点必须是另一个节点的祖先节点
             * 换句话说，当我们把一个节点的前缀和信息更新到map里时，它应当只对其子节点们有效。
             * 举个例子，下图中有两个值为2的节点（A, B)。
             *        0
             *      /  \
             *     A:2  B:2
             *    / \    \
             *   4   5    6
             *  / \   \
             * 7   8   9
             *当我们遍历到最右方的节点6时，对于它来说，此时的前缀和为2的节点只该有B, 因为从A向下到不了节点6(A并不是节点6的祖先节点)。
             * 状态恢复代码的作用就是： 在遍历完一个节点的所有子节点后，将其从map中除去。
             *
             */
            Map<Long, Integer> map = new HashMap<>();
            int ans03, t03;
            public int pathSum03(TreeNode root, int _t) {
                if(root == null) return 0;
                t03 = _t;
                map.put(0L, 1);
                dfs03(root, root.val);
                return ans03;
            }
            void dfs03(TreeNode root, long val) {
                if(map.containsKey(val - t03)) ans += map.get(val - t03);
                map.put(val, map.getOrDefault(val, 0) + 1);
                if(root.left != null) dfs03(root.left, val + root.left.val);
                if(root.right != null) dfs03(root.right, val + root.right.val);
                map.put(val, map.getOrDefault(val, 0) - 1);
            }


            /**
             * bfs + dfs
             *
             */
            int count, num, targetSum;
            public int pathSum04(TreeNode root, int targetSum) {
                if(root == null) return 0;
                this.targetSum = targetSum;
                Queue<TreeNode> q = new ArrayDeque<>();
                q.add(root);
                while(!q.isEmpty()) {
                    TreeNode head = q.remove();
                    check(head, 0);//考察以当前结点为起始的满足要求的路径数量
                    if(head.left != null) q.add(head.left);
                    if(head.right != null) q.add(head.right);
                }
                return count;
            }
            private void check(TreeNode node, int sum) {
                if(node == null) return;
                sum = sum + node.val;
                if(sum == targetSum) count++;//一旦满足，立即累计
                check(node.left, sum);
                check(node.right, sum);
            }


            /**
             * dfs + dfs(不带返回值)
             * @param root
             * @param targetSum
             * @return
             */
            public  int pathSum05(TreeNode root, int targetSum) {
                if(root == null) return 0;
                this.targetSum = targetSum;
                dfs05(root);
                return count;
            }
            private void dfs05(TreeNode node) {
                if(node == null) return;
                check(node, 0);
                dfs05(node.left);
                dfs05(node.right);
            }

            public int pathSum06(TreeNode root, int targetSum) {
                if(root == null) return 0;
                int count = nodeSum(root, targetSum);
                return count + pathSum06(root.left, targetSum) + pathSum06(root.right, targetSum);
            }
            private int nodeSum(TreeNode node, int targetSum) {
                if(node == null) return 0;
                int count = 0, val = node.val;
                if(val == targetSum) count++;
                return count + nodeSum(node.left, targetSum - val) + nodeSum(node.right, targetSum - val);
            }



        }
}
