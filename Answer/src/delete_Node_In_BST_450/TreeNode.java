package delete_Node_In_BST_450;

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
            * 不知道哪里错了
            * @param root
            * @param key
            * @return
            */
           public TreeNode deleteNode(TreeNode root, int key) {
               if(root == null) return null;
               dfs(root, key);
               return root;
           }

           public void dfs(TreeNode  node, int key) {
               if( node == null) return;
               if( node.val == key) {

                   if( node.left == null &&  node.right == null) {
                        node = null;
                   }
                   else if( node.left == null)  node =  node.right;
                   else if( node.right == null)  node =  node.left;
                   else {
                       TreeNode succeed =  node.left;
                       while (succeed.right != null) {
                            succeed = succeed.right;
                        }
                       succeed.right =  node.right;
                        node =  node.left;
                       System.out.println( node.val);
                   }
                   return;
               }
               dfs( node.left, key);
               dfs( node.right, key);
           }


           /**
            * 二叉搜索树有以下性质：
            * 左子树的所有节点（如果有）的值均小于当前节点的值；
            * 右子树的所有节点（如果有）的值均大于当前节点的值；
            * 左子树和右子树均为二叉搜索树。
            * 二叉搜索树的题目往往可以用递归来解决。此题要求删除二叉树的节点，函数 deleteNode 的输入是二叉树的根节点root 和一个整数key，
            * 输出是删除值为 key 的节点后的二叉树，并保持二叉树的有序性。可以按照以下情况分类讨论：
            * root 为空，代表未搜索到值为 key 的节点，返回空。
            * root.val>key，表示值为 key 的节点可能存在于 root 的左子树中，需要递归地在 root.left 调用 deleteNode，并返回 root。
            * root.val<key，表示值为 key 的节点可能存在于 root 的右子树中，需要递归地在 root.right 调用 deleteNode，并返回 root。
            * root.val=key，root 即为要删除的节点。此时要做的是删除 root，并将它的子树合并成一棵子树，保持有序性，并返回根节点。根据 root 的子树情况分成以下情况讨论：
            * root 为叶子节点，没有子树。此时可以直接将它删除，即返回空。
            * root 只有左子树，没有右子树。此时可以将它的左子树作为新的子树，返回它的左子节点。
            * root 只有右子树，没有左子树。此时可以将它的右子树作为新的子树，返回它的右子节点。
            * root 有左右子树，这时可以将 root 的后继节点（比 root 大的最小节点，即它的右子树中的最小节点，记为 successor）作为新的根节点替代 root，
            * 并将 successor 从 root 的右子树中删除，使得在保持有序性的情况下合并左右子树。
            * 简单证明，successor 位于 root 的右子树中，因此大于 root 的所有左子节点；successor 是 root 的右子树中的最小节点，
            * 因此小于 root 的右子树中的其他节点。以上两点保持了新子树的有序性。
            * 在代码实现上，我们可以先寻找 successor，再删除它。successor 是 root 的右子树中的最小节点，可以先找到 root 的右子节点，
            * 再不停地往左子节点寻找，直到找到一个不存在左子节点的节点，这个节点即为 successor。然后递归地在 root.right 调用 deleteNode 来删除 successor。
            * 因为 successor 没有左子节点，因此这一步递归调用不会再次步入这一种情况。然后将 successor 更新为新的root 并返回。
            *
            * 时间复杂度：O(n)，其中 n 为 root 的节点个数。最差情况下，寻找和删除 successor 各需要遍历一次树。
            * 空间复杂度：O(n)，其中 n 为root 的节点个数。递归的深度最深为 O(n)。
            * @param root
            * @param key
            * @return
            */
           public TreeNode deleteNode01(TreeNode root, int key) {
               if(root == null) return null;
               if(root.val > key) {
                   root.left = deleteNode(root.left, key);
                   return root;
               }
               if(root.val < key) {
                   root.right = deleteNode(root.right, key);
                   return root;
               }
               if(root.val == key) {
                   if(root.left == null && root.right == null) {
                       return null;
                   }
                   if(root.right == null) {
                       return root.left;
                   }
                   if(root.left == null) {
                       return root.right;
                   }
                   TreeNode successor = root.right;
                   while(successor.left != null) {
                       successor = successor.left;
                   }
                   root.right = deleteNode(root.right, successor.val);
                   successor.right = root.right;
                   successor.left = root.left;
                   return successor;
               }
               return root;
           }


           /**
            * 迭代写法
            * 方法一的递归深度最多为 n，而大部分是由寻找值为key 的节点贡献的，
            * 而寻找节点这一部分可以用迭代来优化。寻找并删除successor 时，也可以用一个变量保存它的父节点，从而可以节省一步递归操作。
            * 时间复杂度：O(n)，其中 n 为 root 的节点个数。最差情况下，需要遍历一次树。
            * 空间复杂度：O(1)。使用的空间为常数
            * @param root
            * @param key
            * @return
            */
           public TreeNode deleteNode02(TreeNode root, int key) {
               TreeNode cur = root, curParent = null;
               while(cur != null && cur.val != key) {
                   curParent = cur;
                   if(cur.val > key) {
                       cur = cur.left;
                   } else {
                       cur = cur.right;
                   }
               }
               if(cur == null) return root;
               if(cur.left == null && cur.right == null) {
                   cur = null;
               } else if(cur.right == null) cur = cur.left;
               else if(cur.left == null) cur = cur.right;
               else {
                   TreeNode successor = cur.right, successorParent = cur;
                   while(successor.left != null) {
                       successorParent = successor;
                       successor = successor.left;
                   }
                   if(successorParent.val == cur.val) {
                       successorParent.right = successor.right;
                   } else {
                       successorParent.left = successor.right;
                   }
                   successor.right = cur.right;
                   successor.left = cur.left;
                   cur = successor;
               }
               if(curParent == null) return cur;
               else {
                   if(curParent.left != null && curParent.left.val == key) {
                       curParent.left = cur;
                   } else {
                       curParent.right = cur;
                   }
                   return root;
               }
           }


           /**
            * 利用题目本身的函数签名的含义，也就是「在以 root 为根的子树中，删除值为 key 的节点，并返回删除节点后的树的根节点」，我们可以用「递归」来做。
            * 起始先对边界情况进行处理，当 root 为空（可能起始传入的 root 为空，也可能是递归过程中没有找到值为 key 的节点时，导致的 root 为空），
            * 我们无须进行任何删除，直接返回 null 即可。
            * 根据当前 root.val 与 key 的大小关系，进行分情况讨论：
            * 若有 root.val < key，说明待删除的节点必然不是当前节点，以及不在当前节点的左子树中，我们将删除动作「递归」到当前节点的右子树，
            * 并将删除（可能进行）之后的新的右子树根节点，重新赋值给 root.right，即有 root.right = deleteNode(root.right, key)；
            * 若有 root.val > key，说明待删除的节点必然不是当前节点，以及不在当前节点的右子树，我们将删除节点「递归」到当前节点的左子树，
            * 并将删除（可能进行）之后的新的左子树根节点，重新赋值给 root.left，即有 root.left = deleteNode(root.left, key)；
            * 若有 root.val = key，此时找到了待删除的节点，我们根据左右子树的情况，进行进一步分情况讨论：
            * 若左/右子树为空，我们直接返回右/左子树节点即可（含义为直接将右/左子树节点搬到当前节点的位置）
            * 若左右子树均不为空，我们有两种选择：
            * 从「当前节点的左子树」中选择「值最大」的节点替代 root 的位置，确保替代后仍满足 BST 特性；
            * 从「当前节点的右子树」中选择「值最小」的节点替代 root 的位置，确保替代后仍满足 BST 特性；
            * 我们以「从当前节点的左子树中选择值最大的节点」为例子，我们通过树的遍历，找到其位于「最右边」的节点，记为 tt（tt 作为最右节点，必然有 t.right = null），
            * 利用原本的 root 也是合法 BST，原本的 root.right 子树的所有及节点，必然满足大于 t.val，我们可以直接将 root.right 接在 t.right 上，
            * 并返回我们重接后的根节点，也就是 root.left。而「从当前节点的右子树中选择值最小的节点」，同理。

            *
            * @param key
            * @return
            */
           public TreeNode deleteNode03(TreeNode root, int key) {
               if(root == null) return null;
               if(root.val == key) {
                   if(root.left == null) return root.right;
                   if(root.right == null) return root.left;
                   TreeNode t = root.left;
                   while(t.right != null) t = t.right;
                   t.right = root.right;
                   return root.left;
               } else if(root.val < key) root.right = deleteNode03(root.right, key);
               else root.left = deleteNode03(root.left, key);
               return root;
           }
//时间复杂度：O(h)，其中 h 为树的深度
//空间复杂度：忽略递归带来的额外空间消耗，复杂度为 O(1)
           public TreeNode deleteNode04(TreeNode root, int key) {
               if(root == null) return null;
               if(root.val == key) {
                   if(root.left == null) return root.right;
                   if(root.right == null) return root.left;
                   TreeNode t = root.right;
                   while(t.left != null) t = t.left;
                   t.left = root.left;
                   return root.right;
               } else if(root.val < key) root.right = deleteNode04(root.right, key);
               else root.left = deleteNode04(root, key);
               return root;
           }


           /**
            * 二叉搜索树的特点是左子树的值都比他小，右子树的值都比他大，删除一个节点之后我们还要保证二叉搜索树的这个特点不变。
            * 如果要删除一个结点，我们先要找到这个节点，然后才能删除，但这里要分几种情况。
            * 如果要删除的节点是叶子节点，我们直接删除即可。
            * 如果删除的结点不是叶子节点，并且有一个子节点为空，我们直接返回另一个不为空的子节点即可。
            * 如果删除的结点不是叶子节点，并且左右子树都不为空，我们可以用左子树的最大值替换掉要删除的节点或者用右子树的最小值替换掉要删除的节点都是可以的。
            * @param root
            * @param key
            * @return
            */
           public TreeNode deleteNode05(TreeNode root, int key) {
               if (root == null)
                   return null;
               //通过递归的方式要先找到要删除的结点
               if (key < root.val) {
                   //要删除的节点在左子树上
                   root.left = deleteNode05(root.left, key);
               } else if (key > root.val) {
                   //要删除的节点在右子树上
                   root.right = deleteNode05(root.right, key);
               } else {
                   //找到了要删除的节点。
                   //如果左子树为空，我们只需要返回右子树即可
                   if (root.left == null)
                       return root.right;
                   //如果右子树为空，我们只需要返回左子树即可
                   if (root.right == null)
                       return root.left;
                   //说明两个子节点都不为空，我们可以找左子树的最大值，
                   //也可以找右子树的最小值替换

                   //这里是用右子树的最小值替换
                   //TreeNode minNode = findMin(root.right);
                   //root.val = minNode.val;
                   //root.right = deleteNode(root.right, root.val);

                   //这里是用左子树的最大值替换
                   TreeNode maxNode = findMax(root.left);
                   root.val = maxNode.val;
                   root.left = deleteNode05(root.left, root.val);
               }
               return root;
           }

           //    找右子树的最小值
           //    private TreeNode findMin(TreeNode node) {
           //        while (node.left != null)
           //            node = node.left;
           //        return node;
           //    }

           //找左子树的最大值
           private TreeNode findMax(TreeNode node) {
               while (node.right != null)
                   node = node.right;
               return node;
           }


           /**
            * 上面节点删除的时候我们使用左子树的最大值或者右子树的最小值替换都是可以的。其实我们还可以改一下，如果要删除结点左右子树只要有一个为空，我们就返回另一棵子树，
            * 如果都不为空，我们可以让左子树成为右子树最小结点的左子树或者让右子树成为左子树最大结点的右子树，我们来看下代码。

            * @param root
            * @param key
            * @return
            */
           public TreeNode deleteNode06(TreeNode root, int key) {
               if (root == null)
                   return null;
               if (root.val > key) {
                   //要删除的节点在左子树上
                   root.left = deleteNode06(root.left, key);
               } else if (root.val < key) {
                   //要删除的节点在右子树上
                   root.right = deleteNode06(root.right, key);
               } else {
                   //找到要删除的结点之后
                   if (root.left == null)
                       return root.right;
                   if (root.right == null)
                       return root.left;

            /*
            //左右子树都不为空，找到要删除结点右子树的最小值
            TreeNode rightSmallest = root.right;
            while (rightSmallest.left != null)
                rightSmallest = rightSmallest.left;
            //这个最小值对应的节点一定是没有左子树的，
            // 如果有他肯定不是最小的，然后让删除结点的
            //左子树成为这个最小值的左子树
            rightSmallest.left = root.left;
            //直接返回要删除结点的右子树
            return root.right;
             */

                   //左右子树都不为空，找到要删除结点左子树的最大值
                   TreeNode leftBig = root.left;
                   while (leftBig.right != null)
                       leftBig = leftBig.right;
                   //这个最大值对应的节点一定是没有右子树的，
                   // 如果有他肯定不是最大的，然后让删除结点的
                   //右子树成为这个最大值的右子树
                   leftBig.right = root.right;
                   //直接返回要删除结点的左子树
                   return root.left;
               }
               return root;
           }




               public TreeNode deleteNode07(TreeNode root, int key) {
                   if (root == null) {
                       return null;
                   }
                   if (key < root.val) {
                       // 待删除节点在左子树中
                       root.left = deleteNode07(root.left, key);
                       return root;
                   } else if (key > root.val) {
                       // 待删除节点在右子树中
                       root.right = deleteNode07(root.right, key);
                       return root;
                   } else {
                       // key == root.val，root 为待删除节点
                       if (root.left == null) {
                           // 返回右子树作为新的根
                           return root.right;
                       } else if (root.right == null) {
                           // 返回左子树作为新的根
                           return root.left;
                       } else {
                           // 左右子树都存在，返回后继节点（右子树最左叶子）作为新的根
                           TreeNode successor = min(root.right);
                           successor.right = deleteMin(root.right);
                           successor.left = root.left;
                           return successor;
                       }
                   }
               }

               private TreeNode min(TreeNode node) {
                   if (node.left == null) {
                       return node;
                   }
                   return min(node.left);
               }

               private TreeNode deleteMin(TreeNode node) {
                   if (node.left == null) {
                       return node.right;
                   }
                   node.left = deleteMin(node.left);
                   return node;
               }
           }



}
