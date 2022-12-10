package flatten_BinaryTree_To_LinkedList_114;

import java.util.ArrayList;
import java.util.Deque;
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
           List<TreeNode> res = new LinkedList<>();
           public void flatten(TreeNode root) {
               if(root == null) return;
               dfs(root);
               TreeNode node = root;
               for(int i = 1; i < res.size(); i++) {
                   node.right = res.get(i);
                   node.left = null;
                   node = node.right;
               }
           }
           public void dfs(TreeNode root) {
               if(root == null) return;
               res.add(root);
               dfs(root.left);
               dfs(root.right);
           }


           /**
            * 前序遍历
            * 将二叉树展开为单链表之后，单链表中的节点顺序即为二叉树的前序遍历访问各节点的顺序。因此，可以对二叉树进行前序遍历
            * 获得各节点被访问到的顺序。由于将二叉树展开为链表之后会破坏二叉树的结构，因此在前序遍历结束之后更新每个节点的左右子节点的信息，将二叉树展开为单链表。
            * @param root
            */
           public void flatten1(TreeNode root) {
               List<TreeNode> list = new ArrayList<>();
               preorderTraversal(root, list);
               int size = list.size();
               for(int i = 1; i < size; i++) {
                   TreeNode prev = list.get(i - 1), curr = list.get(i);
                   prev.left = null;
                   prev.right = curr;
               }
           }
           public void preorderTraversal(TreeNode root, List<TreeNode> list) {
               if (root != null) {
                   list.add(root);
                   preorderTraversal(root.left, list);
                   preorderTraversal(root.right, list);
               }
           }


           public void flatten2(TreeNode root) {
               List<TreeNode> list = new ArrayList<>();
               Deque<TreeNode> stack = new LinkedList<>();
               TreeNode node = root;
               while(node != null || !stack.isEmpty()) {
                   while(node != null) {
                       list.add(node);
                       stack.push(node);
                       node = node.left;
                   }
                   node = stack.pop();
                   node = node.right;
               }

               int size = list.size();
               for(int i = 1; i < size; i++) {
                   TreeNode prev = list.get(i - 1), curr = list.get(i);
                   prev.left = null;
                   prev.right = curr;
               }
           }


           /**
            * 前序遍历和展开同步进行
            *
            * 使用方法一的前序遍历，由于将节点展开之后会破坏二叉树的结构而丢失子节点的信息，因此前序遍历和展开为单链表分成了两步。
            * 能不能在不丢失子节点的信息的情况下，将前序遍历和展开为单链表同时进行？
            * 之所以会在破坏二叉树的结构之后丢失子节点的信息，是因为在对左子树进行遍历时，没有存储右子节点的信息，
            * 在遍历完左子树之后才获得右子节点的信息。只要对前序遍历进行修改，在遍历左子树之前就获得左右子节点的信息，并存入栈内，
            * 子节点的信息就不会丢失，就可以将前序遍历和展开为单链表同时进行。
            * 该做法不适用于递归实现的前序遍历，只适用于迭代实现的前序遍历。修改后的前序遍历的具体做法是，
            * 每次从栈内弹出一个节点作为当前访问的节点，获得该节点的子节点，如果子节点不为空，则依次将右子节点和左子节点压入栈内（注意入栈顺序）。
            *
            * 展开为单链表的做法是，维护上一个访问的节点 prev，每次访问一个节点时，令当前访问的节点为 curr，将 prev 的左子节点设为 null 以及将 prev 的右子节点设为 curr，
            * 然后将 curr 赋值给 prev，进入下一个节点的访问，直到遍历结束。需要注意的是，初始时 prev 为 null，只有在 prev 不为 null 时才能对 prev 的左右子节点进行更新。
            *
            * @param root
            */
           public void flatten3(TreeNode root) {
               if(root == null) return;
               Deque<TreeNode> stack = new LinkedList<TreeNode>();
               stack.push(root);
               TreeNode prev = null;
               while(!stack.isEmpty()) {
                   TreeNode curr = stack.pop();
                   if(prev != null) {
                       prev.left = null;
                       prev.right = curr;
                   }
                   TreeNode left = curr.left, right = curr.right;
                   if(right != null) stack.push(right);
                   if(left != null) stack.push(left);
                   prev = curr;
               }
           }

           /**
            * 寻找前驱节点
            * 前两种方法都借助前序遍历，前序遍历过程中需要使用栈存储节点。有没有空间复杂度是 O(1) 的做法呢？
            * 注意到前序遍历访问各节点的顺序是根节点、左子树、右子树。如果一个节点的左子节点为空，则该节点不需要进行展开操作。
            * 如果一个节点的左子节点不为空，则该节点的左子树中的最后一个节点被访问之后，该节点的右子节点被访问。
            * 该节点的左子树中最后一个被访问的节点是左子树中的最右边的节点，也是该节点的前驱节点。因此，问题转化成寻找当前节点的前驱节点。
            * 具体做法是，对于当前节点，如果其左子节点不为空，则在其左子树中找到最右边的节点，作为前驱节点，将当前节点的右子节点赋给前驱节点的右子节点，
            * 然后将当前节点的左子节点赋给当前节点的右子节点，并将当前节点的左子节点设为空。对当前节点处理结束后，继续处理链表中的下一个节点，直到所有节点都处理结束。
            * @param root
            */
           public void flatten4(TreeNode root) {
               TreeNode curr = root;
               while(curr != null) {
                   if(curr.left != null) {
                       TreeNode next = curr.left;
                       TreeNode predecessor = next;
                       while(predecessor.right != null) predecessor = predecessor.right;
                       predecessor.right = curr.right;
                       curr.left = null;
                       curr.right = next;
                   }
                   curr = curr.right;
               }
           }


           /**
            * 将左子树插入到右子树的地方
            * 将原来的右子树接到左子树的最右边节点
            * 考虑新的右子树的根节点，一直重复上边的过程，直到新的右子树为 null
            *
            *     1
            *    / \
            *   2   5
            *  / \   \
            * 3   4   6
            *
            * //将 1 的左子树插入到右子树的地方
            *     1
            *      \
            *       2         5
            *      / \         \
            *     3   4         6
            * //将原来的右子树接到左子树的最右边节点
            *     1
            *      \
            *       2
            *      / \
            *     3   4
            *          \
            *           5
            *            \
            *             6
            *
            *  //将 2 的左子树插入到右子树的地方
            *     1
            *      \
            *       2
            *        \
            *         3       4
            *                  \
            *                   5
            *                    \
            *                     6
            *
            *  //将原来的右子树接到左子树的最右边节点
            *     1
            *      \
            *       2
            *        \
            *         3
            *          \
            *           4
            *            \
            *             5
            *              \
            *               6
            *
            *   ......
            *
            *
            * @param root
            *
            * 首先我们需要找出左子树最右边的节点以便把右子树接过来。
            */
           public void flatten01(TreeNode root) {
                 while(root != null) {
                     //左子树为null,直接考虑下一个节点
                     if(root.left == null) root = root.right;
                     else {//找左子树最右边的节点
                         TreeNode pre = root.left;
                         while(pre.right != null) pre = pre.right;
                         //将原来的右子树接到左子树的最右边节点
                         pre.right = root.left;
                         root.left = null;
                         //考虑下一个节点
                         root = root.right;

                     }
                 }
           }

           /**
            *
            * @param root
            */
           public void flatten02(TreeNode root) {

















           }







       }
}
