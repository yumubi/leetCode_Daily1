package kth_Smallest_Element_In_BST_230;

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
           List<Integer> list = new ArrayList<Integer>();

           public int kthSmallest(TreeNode root, int k) {
               dfs(root);
               return list.get(k - 1);
           }

           public void dfs(TreeNode root) {
               if (root == null) return;

               dfs(root.left);
               list.add(root.val);
               dfs(root.right);
           }


           /**
            * 中序遍历
            * 结点的左子树只包含小于当前结点的数。
            * 结点的右子树只包含大于当前结点的数。
            * 所有左子树和右子树自身必须也是二叉搜索树。
            * <p>
            * 时间复杂度：O(H+k)，其中 H 是树的高度。在开始遍历之前，我们需要 O(H) 到达叶结点。当树是平衡树时，时间复杂度取得最小值 O(logN+k)；
            * 当树是线性树（树中每个结点都只有一个子结点或没有子结点）时，时间复杂度取得最大值 O(N+k)。
            * 空间复杂度：O(H)，栈中最多需要存储 H 个元素。当树是平衡树时，空间复杂度取得最小值 O(logN)；当树是线性树时，空间复杂度取得最大值 O(N)
            *
            * @param root
            * @param k
            * @return
            */
           public int kthSmallest1(TreeNode root, int k) {
               Deque<TreeNode> stack = new ArrayDeque<>();
               while (root != null || !stack.isEmpty()) {
                   while (root != null) {
                       stack.push(root);
                       root = root.left;
                   }
                   root = stack.pop();
                   --k;
                   if (k == 0) break;
                   root = root.right;
               }
               return root.val;
           }

           /**
            * 记录下以每个结点为根结点的子树的结点数，并在查找第 kk 小的值时，使用如下方法搜索：
            * 令 node 等于根结点，开始搜索。
            * 对当前结点node 进行如下操作：
            * 如果 node 的左子树的结点数 left 小于 k−1，则第 k 小的元素一定在 node 的右子树中，令 node 等于其的右子结点，k 等于 k−left−1，并继续搜索；
            * 如果 node 的左子树的结点数 left 等于k−1，则第 k 小的元素即为 node ，结束搜索并返回 node 即可；
            * 如果 node 的左子树的结点数 left 大于 k-1则第 k 小的元素一定在 node 的左子树中，令 node 等于其左子结点，并继续搜索。
            * <p>
            * <p>
            * 时间复杂度：预处理的时间复杂度为 O(N)，其中 N 是树中结点的总数；我们需要遍历树中所有结点来统计以每个结点为根结点的子树的结点数。
            * 搜索的时间复杂度为 O(H)，其中 H是树的高度；当树是平衡树时，时间复杂度取得最小值 O(logN)；当树是线性树时，时间复杂度取得最大值 O(N)。
            * 空间复杂度：O(N)，用于存储以每个结点为根结点的子树的结点数。
            *
            * @param root
            * @param k
            * @return
            */
           public int kthSmallest2(TreeNode root, int k) {

               MyBst myBst = new MyBst(root);
               return myBst.kthSmallest(k);

           }

           class MyBst {
               TreeNode root;
               Map<TreeNode, Integer> nodeNum;

               public MyBst(TreeNode root) {
                   this.root = root;
                   this.nodeNum = new HashMap<TreeNode, Integer>();
                   countNodeNum(root);
               }

               public int kthSmallest(int k) {
                   TreeNode node = root;
                   while (node != null) {
                       int left = getNodeNum(node.left);
                       if (left < k - 1) {
                           node = node.right;
                           k -= left + 1;
                       } else if (left == k - 1) break;
                       else node = node.left;
                   }
                   return node.val;
               }


               //统计以node为根节点的子树的节点数
               private int countNodeNum(TreeNode node) {
                   if (node == null) return 0;
                   nodeNum.put(node, 1 + countNodeNum(node.left) + countNodeNum(node.right));
                   return nodeNum.get(node);
               }

               //获取以node为根节点的子树的节点数
               private int getNodeNum(TreeNode node) {
                   return nodeNum.getOrDefault(node, 0);
               }
           }


           // TODO: 17/12/2022 平衡二叉搜索树 AVL


           /**
            * 树的遍历+排序
            */
           List<Integer> list4 = new ArrayList<>();

           public int kthSmallest4(TreeNode root, int k) {
               dfs(root);
               Collections.sort(list4);
               return list.get(k - 1);
           }

           void dfs4(TreeNode root) {
               if (root == null) return;
               list4.add(root.val);
               dfs(root.left);
               dfs(root.right);
           }

           /**
            * 树的遍历+优先队列
            * 由于我们返回的是第 k 小的数，因此我们可以构建一个容量为 k 的大根堆。
            * 根据大根堆的元素个数和当前节点与堆顶元素的关系来分情况讨论：
            * 大根堆元素不足 k 个：直接将当前节点值放入大根堆；
            * 大根堆元素为 k 个，根据堆顶元素和当前节点值的大小关系进一步分情况讨论：
            *       如果当前节点值元素大于堆顶元素，说明当前节点值不可能在第 k 小的范围内，直接丢弃；
            *       如果当前节点值元素小于堆顶元素，说明当前节点值可能在第 k 小的范围内，先 poll 一个再 add 进去。
            * 时间复杂度：树的遍历时间复杂度为 O(n)；使用优先队列（堆）复杂度为 O(nlogk)。整体复杂度为 O(nlogk)
            * 空间复杂度：空间多少取决于 d 和 q 使用的容量，q 最多不超过 k 个元素，复杂度为O(k)，d 最多不超过二叉树的一层，复杂度为O(n)。整体复杂度为O(n+k)
            * @param root
            * @param k
            * @return
            */
           public int kthSmallest5(TreeNode root, int k) {
               PriorityQueue<Integer> q = new PriorityQueue<>((a, b) -> (b - a));
               Deque<TreeNode> d = new ArrayDeque<>();
               d.addLast(root);
               while (!d.isEmpty()) {
                   TreeNode node = d.pollFirst();
                   if (q.size() < k) {
                       q.add(node.val);
                   } else if (q.peek() > node.val) {
                       q.poll();
                       q.add(node.val);
                   }
                   if (node.left != null) d.addLast(node.left);
                   if (node.right != null) d.addLast(node.right);
               }
               return q.peek();
           }


           /**
            * 中序遍历
            * @param root
            * @param k
            * @return
            */
           public int kthSmallest6(TreeNode root, int k) {
               Deque<TreeNode> d = new ArrayDeque<>();
               while(root != null || !d.isEmpty()) {
                   while(root != null) {
                       d.addLast(root);
                       root = root.left;
                   }
                   root = d.pollLast();
                   if(--k == 0) return root.val;
                   root = root.right;
               }
               return -1;
           }

//
//           int k, ans;
//           public int kthSmallest(TreeNode root, int _k) {
//               k = _k;
//               dfs(root);
//               return ans;
//           }
//           void dfs(TreeNode root) {
//               if (root == null || k <= 0) return ;
//               dfs(root.left);
//               if (--k == 0) ans = root.val;
//               dfs(root.right);
//           }


           // TODO: 17/12/2022 morris遍历
           public int kthSmallest7(TreeNode root, int k) {
               TreeNode cur = root;
               int num = 0;
               int res = -1;
               while (cur != null) {
                   // 情况 1
                   if (cur.left == null) {
                       num++;
                       if (num == k) {
                           res = cur.val;
                           break;
                       }
                       cur = cur.right;
                   } else {
                       // 找左子树最右边的节点
                       TreeNode pre = cur.left;
                       while (pre.right != null && pre.right != cur) {
                           pre = pre.right;
                       }
                       // 情况 2.1
                       if (pre.right == null) {
                           pre.right = cur;
                           cur = cur.left;
                       }
                       // 情况 2.2
                       if (pre.right == cur) {
                           pre.right = null; // 这里可以恢复为 null
                           num++;
                           if (num == k) {
                               res = cur.val;
                               break;
                           }
                           cur = cur.right;
                       }
                   }

               }
               return res;
           }


           /**
            * 分治法
            * 我们只需要先计算左子树的节点个数，记为 n，然后有三种情况。
            *
            * n 加 1 等于 k，那就说明当前根节点就是我们要找的。
            * n 加 1 小于 k，那就说明第 k 小的数一定在右子树中，我们只需要递归的在右子树中寻找第 k - n - 1 小的数即可。
            * n 加 1 大于 k，那就说明第 k 小个数一定在左子树中，我们只需要递归的在左子树中寻找第 k 小的数即可。

             * @param root
            * @param k
            * @return
            */
           public int kthSmallest8(TreeNode root, int k) {
               int n = nodeCount(root.left);
               if(n + 1 == k) return root.val;
               else if(n + 1 < k) return kthSmallest8(root.right, k - n - 1);
               else return kthSmallest8(root.left, k);
           }

           private int nodeCount(TreeNode root) {
               if(root == null) return 0;
               return 1 + nodeCount(root.left) + nodeCount(root.right);
           }


       }
}
