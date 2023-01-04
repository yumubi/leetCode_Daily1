package all_Elements_In_Two_BST_1305;

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
           public List<Integer> getAllElements(TreeNode root1, TreeNode root2) {
               List<Integer> list1 = new ArrayList<>();
               inorderTraversal(root1, list1);
               inorderTraversal(root2, list1);
               Collections.sort(list1);
               return list1;
           }

           public void inorderTraversal(TreeNode root, List<Integer> list) {
               if (root == null) return;
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
            *
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
                   if (p1 == nums1.size()) {
                       merged.addAll(nums2.subList(p2, nums2.size()));
                       break;
                   }
                   if (p2 == nums2.size()) {
                       merged.addAll(nums1.subList(p1, nums1.size()));
                       break;
                   }
                   if (nums1.get(p1) < nums2.get(p2)) merged.add(nums1.get(p1++));
                   else merged.add(nums2.get(p2++));
               }
               return merged;
           }

           public void inorder(TreeNode node, List<Integer> res) {
               if (node != null) {
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
                   if (a <= b) {
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
               if (root == null) return;
               dfs(root.left, list);
               list.add(root.val);
               dfs(root.right, list);
           }


           public List<Integer> getAllElements03(TreeNode root1, TreeNode root2) {
               List<Integer> mList1 = new ArrayList<>();
               List<Integer> mList2 = new ArrayList<>();
               inorderTraversal(root1, mList1);
               inorderTraversal(root2, mList2);
               int index1 = 0;
               int index2 = 0;
               List<Integer> res = new ArrayList<>();
               while (index1 < mList1.size() && index2 < mList2.size()) {
                   // 哪个小，先取哪个
                   while (index1 < mList1.size() && index2 < mList2.size()) {
                       if (mList1.get(index1) < mList2.get(index2)) {
                           res.add(mList1.get(index1++));
                       } else {
                           res.add(mList2.get(index2++));
                       }
                   }
               }
               // 如果mList1添加完了，就把mList2全部加进来
               if (index1 == mList1.size()) {
                   res.addAll(mList2.subList(index2, mList2.size()));
               }
               // 同上
               if (index2 == mList2.size()) {
                   res.addAll(mList1.subList(index1, mList1.size()));
               }
               return res;
           }


           // 二叉树的中序遍历，非递归方式
//    public void inorderTraversal(TreeNode node, List<Integer> mList) {
//        Stack<TreeNode> stack = new Stack<>();
//        while (node != null || !stack.isEmpty()) {
//            while (node != null) {
//                stack.push(node);
//                node = node.left;
//            }
//            node = stack.pop();
//            mList.add(node.val);
//            node = node.right;
//        }
//    }

           // Morris 中序遍历
//    public void inorderTraversal(TreeNode node, List<Integer> mList) {
//        //首先把根节点赋值给cur
//        TreeNode cur = node;
//        //如果cur不为空就继续遍历
//        while (cur != null) {
//            if (cur.left == null) {
//                //如果当前节点cur的左子节点为空，就访问当前节点cur，
//                //接着让当前节点cur指向他的右子节点
//                mList.add(cur.val);
//                cur = cur.right;
//            } else {
//                TreeNode pre = cur.left;
//                //查找pre节点，注意这里有个判断就是pre的右子节点不能等于cur
//                while (pre.right != null && pre.right != cur)
//                    pre = pre.right;
//                //如果pre节点的右指针指向空，我们就让他指向当前节点cur，
//                //然后当前节点cur指向他的左子节点
//                if (pre.right == null) {
//                    pre.right = cur;
//                    cur = cur.left;
//                } else {
//                    //如果pre节点的右指针不为空，那么他肯定是指向cur的,
//                    //表示cur的左子节点都遍历完了，我们需要让pre的右
//                    //指针指向null，目的是把树给还原，然后再访问当前节点
//                    //cur，最后再让当前节点cur指向他的右子节点。
//                    pre.right = null;
//                    mList.add(cur.val);
//                    cur = cur.right;
//                }
//            }
//        }
//    }


           private void addItem(TreeNode root, Deque<TreeNode> stack, List<Integer> ls) {
               while (root != null && !stack.isEmpty()) {
                   while (root != null) {
                       stack.push(root);
                       root = root.left;
                   }
                   root = stack.pop();
                   ls.add(root.val);
                   root = root.right;
               }
           }

           public List<Integer> getAllElements05(TreeNode root1, TreeNode root2) {
               Deque<TreeNode> stack1 = new LinkedList<>();
               Deque<TreeNode> stack2 = new LinkedList<>();
               List<Integer> reslut = new LinkedList<>();
               while (root1 != null || !stack1.isEmpty() && (root2 != null || !stack2.isEmpty())) {
                   while (root1 != null) {
                       stack1.push(root1);
                       root1 = root1.left;
                   }
                   while (root2 != null) {
                       stack2.push(root2);
                       root2 = root2.left;
                   }
                   if (stack1.peek().val > stack2.peek().val) {
                       root2 = stack2.pop();
                       reslut.add(root2.val);
                       root2 = root2.right;
                   } else {
                       root1 = stack1.pop();
                       reslut.add(root1.val);
                       root1 = root1.right;
                   }
               }
               this.addItem(root1, stack1, reslut);
               this.addItem(root2, stack2, reslut);
               return reslut;

           }
       }

       class BSTIterator {
           TreeNode root;
           Deque<TreeNode> stack = new ArrayDeque<>();
           public BSTIterator(TreeNode root) {
               this.root = root;
               while (root != null) {
                   stack.push(root);
                   root = root.left;
               }
           }

           public boolean hasNext() {
               return stack.size() > 0;
           }

           public int peek() {
               TreeNode cur = stack.peekLast();
               return cur.val;
           }
           public int next() {
               root = stack.pollLast();
               int val = root.val;
               root = root.right;
               while (root != null) {
                   stack.push(root);
                   root = root.left;
               }
               return val;
           }
       }

       public List<Integer> getAllElements06(TreeNode root1, TreeNode root2) {
           List<Integer> ans = new ArrayList<>();
           BSTIterator it1 = new BSTIterator(root1);
           BSTIterator it2 = new BSTIterator(root2);
           while (it1.hasNext() && it2.hasNext()) {
               int val1 = it1.peek(), val2 = it2.peek();
               if(val1 <= val2) {
                   ans.add(val1);
                   it1.next();
               } else {
                   ans.add(val2);
                   it2.next();
               }
           }
           while (it1.hasNext()) ans.add(it1.next());
           while (it2.hasNext()) ans.add(it2.next());
           return ans;
       }


    /**
     * 1、边迭代，边归并，迭代的同时进行归并
     * 借助Morris遍历的思路，两棵树做中序迭代的同时，进行merge：边迭代，边归并
     * 时间：O(n+m)，时间跑的还不错，
     * 额外空间：O(1)
     * @param root1
     * @param root2
     * @return
     */
    // 两棵树做中序迭代的同时，进行merge：边迭代，边归并
    public static List<Integer> getAllElements07(TreeNode root1, TreeNode root2) {
        ArrayList<Integer> ans = new ArrayList<>();
        TreeNode nextNode1 = getNext(root1, null), nextNode2 = getNext(root2, null);
        while (nextNode1 != null && nextNode2 != null) {
            if (nextNode1.val < nextNode2.val) {
                ans.add(nextNode1.val);
                nextNode1 = getNext(root1, nextNode1);
            } else {
                ans.add(nextNode2.val);
                nextNode2 = getNext(root2, nextNode2);
            }
        }
        while (nextNode1 != null) {
            ans.add(nextNode1.val);
            nextNode1 = getNext(root1, nextNode1);
        }
        while (nextNode2 != null) {
            ans.add(nextNode2.val);
            nextNode2 = getNext(root2, nextNode2);
        }
        return ans;
    }

    // 迭代器：获取root这棵树的下一个节点，当前节点为curNode
    private static TreeNode getNext(TreeNode root, TreeNode curNode) {
        if (curNode == null) { // 第一次执行迭代
            // 沿着左树，一直往左撸，
            // 并将沿途节点node的前驱节点(左树的最右节点)右指针指向node
            TreeNode node = root;
            while (node != null && node.left != null) {
                TreeNode preNode = findPreNode(node); // 找到node的前驱节点
                preNode.right = node; // 右指针指向node
                node = node.left; // 一直往左撸
            }
            return node; // 撸到底，就是第一个节点
        } else { // 当前不是第一次执行迭代， 3种情况：
            // 1）curNode.right==null，迭代到头了，返回null
            // 2）当前节点curNode.right被修改指到了上面，此时curNode.right即为next返回，注意：返回前需要恢复原树
            // 3）当前节点curNode.right非空，并且没有往上指，是一个正常的右子树节点，找到此时右树最左节点即为next返回
            TreeNode right = curNode.right;
            if (right == null) return null; // 情况1）

            TreeNode preNode = findPreNode(right);
            if (preNode == curNode) { // 情况2）此时的right节点是从孩子节点指上来的，此时right即为next，需要恢复原树
                curNode.right = null; // 恢复
                return right;
            } else { // 情况3）right节点是第一次到达，是一个正常的右子树节点，找到此时右树最左节点即为next返回
                TreeNode node = right;
                while (node.left != null) {
                    // 沿着左树，一直往左撸，
                    // 并将沿途节点node的前驱节点(左树的最右节点)右指针指向node
                    preNode = findPreNode(node);
                    preNode.right = node;
                    node = node.left;
                }
                return node;
            }
        }
    }

    // 找到node的前驱节点
    private static TreeNode findPreNode(TreeNode node) {
        TreeNode cur = node.left;
        if (cur == null) return null;
        while (cur.right != null && cur.right != node) cur = cur.right;
        return cur;
    }



}
