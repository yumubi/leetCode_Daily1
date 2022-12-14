package Maximum_Width_Of_Binary_Tree_662;

import java.util.ArrayDeque;
import java.util.Deque;
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
           //εε­θΆι
           public int widthOfBinaryTree(TreeNode root) {
               Deque<TreeNode> queue = new ArrayDeque<TreeNode>();
               int maxWidth = -1;
               queue.offer(root);
               while (!queue.isEmpty()) {
                   int size = queue.size();
                   boolean startCnt = false;
                   int left = -1, right = -1;
                   for(int i = 0; i < size; i++) {
                       TreeNode node = queue.poll();
                       if (startCnt == false && node.val != -101) {
                           left = i;
                           startCnt = true;
                       }
                       if (startCnt && node.val != -101) right = i;
                       if (node.left == null && node.right == null) {
                           if (left == -1)
                               continue;
                           else {
                               queue.offer(new TreeNode(-101));
                               queue.offer(new TreeNode(-101));
                           }
                       } else {
                           if (node.left != null) queue.offer(node.left);
                           else queue.offer(new TreeNode(-101));
                           if (node.right != null) queue.offer(node.right);
                           else queue.offer(new TreeNode(-101));
                       }
                   }
                   int width = right - left;
                   if(width > maxWidth) maxWidth = width;
               }
               return maxWidth + 1;
           }


           /**
            * dfs
            * ζη§δΈθΏ°ζΉζ³ηΌε·οΌε―δ»₯η¨ζ·±εΊ¦δΌεζη΄’ζ₯ιεγιεζΆε¦ζζ―εθ?Ώι?ε·¦ε­θηΉοΌεθ?Ώι?ε³ε­θηΉοΌζ―δΈε±ζεθ?Ώι?ε°ηθηΉδΌζ―ζε·¦θΎΉηθηΉοΌε³ζ―δΈε±ηΌε·ηζε°εΌοΌ
            * ιθ¦θ?°ε½δΈζ₯θΏθ‘εη»­ηζ―θΎγδΈζ¬‘ζ·±εΊ¦δΌεζη΄’δΈ­οΌιθ¦ε½εθηΉε°ε½εθ‘ζε·¦θΎΉθηΉηε?½εΊ¦οΌδ»₯εε―Ήε­θηΉθΏθ‘ζ·±εΊ¦δΌεζη΄’οΌζ±εΊζε€§ε?½εΊ¦οΌεΉΆθΏεζε€§ε?½εΊ¦γ
            * ζΆι΄ε€ζεΊ¦οΌO(n)οΌεΆδΈ­ n ζ―δΊεζ ηθηΉδΈͺζ°γιθ¦ιεζζθηΉγ
            * η©Ίι΄ε€ζεΊ¦οΌO(n)γιε½ηζ·±εΊ¦ζε€δΈΊ O(n)
            */
           Map<Integer, Integer> levelMin = new HashMap<Integer, Integer>();
           public int widthOfBinaryTree02(TreeNode root) {
                return dfs(root, 1, 1);
           }
           public int dfs(TreeNode node, int depth, int index) {
               if(node == null) return 0;
               levelMin.putIfAbsent(depth, index);//ζ―δΈε±ζεθ?Ώι?ηθηΉδΌζ―ζε·¦θΎΉηθηΉοΌε³ζ―ε±ηΌε·ηζε°εΌ
               return Math.max(index - levelMin.get(depth) + 1,
                       Math.max(dfs(node.left, depth + 1, index * 2), dfs(node.right, depth + 1, index * 2 + 1)));
           }

       }
}
