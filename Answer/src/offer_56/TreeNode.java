package offer_56;

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
           List<Integer> res = new ArrayList<>();
           public boolean findTarget(TreeNode root, int k) {
               InorderTravsersal(root);
               int arr[] = res.stream().mapToInt(i -> i).toArray();
               int lo = 0, hi = arr.length - 1;
               while(lo < hi) {
                   int add = arr[lo] + arr[hi];
                   if(add == k) return true;
                   if(add < k) lo++;
                   else hi--;
               }
               return false;
           }

           public void InorderTravsersal(TreeNode root) {
               if(root == null) return;
               InorderTravsersal(root.left);
               res.add(root.val);
               InorderTravsersal(root.right);
           }


           /**
            * dfs + 哈希表
            * 我们可以使用深度优先搜索的方式遍历整棵树，用哈希表记录遍历过的节点的值。
            * 对于一个值为 x 的节点，我们检查哈希表中是否存在 k - x即可。如果存在对应的元素，那么我们就可以在该树上找到两个节点的和为 k；否则，我们将 x 放入到哈希表中。
            * 如果遍历完整棵树都不存在对应的元素，那么该树上不存在两个和为 k 的节点。
            * 时间复杂度：O(n)，其中 n为二叉搜索树的大小。我们需要遍历整棵树一次。
            * 空间复杂度：O(n)，其中 n为二叉搜索树的大小。主要为哈希表的开销，最坏情况下我们需要将每个节点加入哈希表一次。
            */
           Set<Integer> set = new HashSet<Integer>();
           public boolean findTarget1(TreeNode root, int k) {
               if(root == null) return false;
               if(set.contains(k - root.val)) return true;
               set.add(root.val);
               return findTarget1(root.left, k) || findTarget1(root.right, k);
           }


           /**
            * bfs + hashset
            * 我们可以使用广度优先搜索的方式遍历整棵树，用哈希表记录遍历过的节点的值。
            *
            * 具体地，我们首先创建一个哈希表和一个队列，将根节点加入队列中，然后执行以下步骤：
            * 从队列中取出队头，假设其值为 x；
            * 检查哈希表中是否存在 k - x，如果存在，返回 \True；
            * 否则，将该节点的左右的非空子节点加入队尾；
            * 重复以上步骤，直到队列为空；
            * 如果队列为空，说明树上不存在两个和为 kk 的节点，返回 False。
            * @param root
            * @param k
            * @return
            */
           public boolean findTarget2(TreeNode root, int k) {
               Set<Integer> set = new HashSet<Integer>();
               Queue<TreeNode> queue = new ArrayDeque<>();
               queue.offer(root);
               while (!queue.isEmpty()) {
                   TreeNode node = queue.poll();
                   if(set.contains(k - node.val)) return true;
                   set.add(node.val);
                   if(node.left != null) queue.offer(node.left);
                   if(node.right != null) queue.offer(node.right);
               }
               return false;
           }

           /**
            * 中序遍历 + dfs + 双指针
            */
           List<Integer> list = new ArrayList<Integer>();
           public boolean findTarget3(TreeNode root, int k) {
                inorderTraversal3(root);
                int left = 0, right = list.size() - 1;
                while(left < right) {
                    if(list.get(left) + list.get(right) == k) return true;
                    if(list.get(left) + list.get(right) < k) left++;
                    else right--;
                }
                return false;
           }

           public void inorderTraversal3(TreeNode node) {
               if(node == null) return;
               inorderTraversal3(node.left);
               list.add(node.val);
               inorderTraversal3(node.right);
           }

           /**
            * 中序遍历 + 迭代 + 双指针
            * 对于每个指针新建一个栈。初始，我们让左指针移动到树的最左端点，并将路径保存在栈中，
            * 接下来我们可以依据栈来 O(1) 地计算出左指针的下一个位置。右指针也是同理。
            * @param root
            * @param k
            * @return
            */
           public boolean findTarget4(TreeNode root, int k) {
                    TreeNode left = root, right = root;
                    Deque<TreeNode> leftStack = new ArrayDeque<>();
                    Deque<TreeNode> rightStack = new ArrayDeque<>();
                    leftStack.push(left);
                    while(left.left != null) {
                        leftStack.push(left.left);
                        left = left.left;
                    }
                    rightStack.push(right);
                    while(right.right != null) {
                        rightStack.push(right.right);
                        right = right.right;
                    }
                    while (left != right) {
                        if(left.val + right.val == k) return true;
                        if(left.val + right.val < k) left = getLeft(leftStack);
                        else right = getRight(rightStack);
                    }
                    return false;
           }

           public TreeNode getLeft(Deque<TreeNode> stack) {
               TreeNode root = stack.pop();
               TreeNode node = root.right;
               while(node != null) {
                   stack.push(node);
                   node = node.left;
               }
               return root;
           }

           public TreeNode getRight(Deque<TreeNode> stack) {
               TreeNode root = stack.pop();
               TreeNode node = root.left;
               while(node != null) {
                   stack.push(node);
                   node = node.right;
               }
               return root;
           }


           /**
            * 双指针+ 中序遍历
            * @param root
            * @param k
            * @return
            */

           public boolean findTarget5(TreeNode root, int k) {
               Deque<TreeNode> ld = new ArrayDeque<>(), rd = new ArrayDeque<>();
               TreeNode temp = root;
               while(temp != null) {
                   ld.addLast(temp);
                   temp = temp.left;
               }
               temp = root;
               while(temp != null) {
                   ld.addLast(temp);
                   temp = temp.right;
               }
               TreeNode l = ld.peekLast(), r = rd.peekLast();
               while(l.val < r.val) {
                   int t = l.val + r.val;
                   if(t == k) return true;
                   if(t < k) l = getNext(ld, true);
                   else r = getNext(rd, false);
               }
               return false;
           }

           TreeNode getNext(Deque<TreeNode> d, boolean isLeft) {
               TreeNode node = isLeft ? d.pollLast().right : d.pollLast().left;
               while(node != null) {
                   d.addLast(node);
                   node = isLeft ? node.left : node.right;
               }
               return d.peekLast();
           }

       }
}
