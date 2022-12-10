package n_ary_Tree_Preorder_Traversal_589;

import java.util.*;

public class Solution {
        List<Integer> res = new ArrayList<>();
        public List<Integer> preorder(Node root) {
            dfs(root);
            return res;
        }

        void dfs(Node root) {
            if(root == null) return;
            res.add(root.val);
            for(int i = 0; i < root.children.size(); i++) dfs(root.children.get(i));
        }

    /**
     * 时间复杂度：O(m)，其中 m 为 N叉树的节点。每个节点恰好被遍历一次。
     * 空间复杂度：O(m)，递归过程中需要调用栈的开销，平均情况下为 O(logm)，最坏情况下树的深度为 m−1，此时需要的空间复杂度为O(m)。
     * @param root
     * @return
     */
        public List<Integer> preorder1(Node root) {
            List<Integer> res = new ArrayList<>();
                helper(root, res);
                return res;
        }
        public void helper(Node root, List<Integer> res) {
            if(root == null) return;
            res.add(root.val);
            for(Node ch : root.children) helper(ch, res);
        }


    /**
     * 在这里的栈模拟中比较难处理的在于从当前节点 uu 的子节点 v_1,返回时，此时需要处理节点 u的下一个节点 v_2
     *此时需要记录当前已经遍历完成哪些子节点，才能找到下一个需要遍历的节点。在二叉树树中因为只有左右两个子节点，因此比较方便处理，
     * 在 N叉树中由于有多个子节点，因此使用哈希表记录当前节点 u 已经访问过哪些子节点。
     * 每次入栈时都将当前节点的 u的第一个子节点压入栈中，直到当前节点为空节点为止。
     * 每次查看栈顶元素 p，如果节点 p 的子节点已经全部访问过，则将节点 p 的从栈中弹出，并从哈希表中移除，表示该以该节点的子树已经全部遍历过；
     * 如果当前节点 p的子节点还有未遍历的，则将当前节点的 p的下一个未访问的节点压入到栈中，重复上述的入栈操作。
     * @return
     */
        public List<Integer> preorder2(Node root) {
            List<Integer> res = new ArrayList<Integer>();
            if(root == null) return res;
            Map<Node, Integer> map = new HashMap<Node, Integer>();
            Deque<Node> stack = new ArrayDeque<Node>();
            Node node = root;
            while(!stack.isEmpty() || node != null) {
                while(node != null) res.add(node.val);
                stack.push(node);
                List<Node> chileren = node.children;
                if(chileren != null && chileren.size() > 0) {
                    map.put(node, 0);
                    node = chileren.get(0);
                } else node = null;
            }
            node = stack.peek();
            int index = map.getOrDefault(node, -1) + 1;
            List<Node> children = node.children;
            if(children != null && children.size() > index) {
                map.put(node, index);
                node = children.get(index);
            } else {
                stack.pop();
                map.remove(node);
                node = null;
            }
            return res;
        }


    /**
     * 迭代优化
     * 在前序遍历中，我们会先遍历节点本身，然后从左向右依次先序遍历该每个以子节点为根的子树，此时利用栈先进后出的原理，
     * 依次从右向左将子节点入栈，这样出栈的时候即可保证从左向右依次遍历每个子树。参考方法二的原理，可以提前将后续需要访问的节点压入栈中，
     * 这样就可以避免记录每个节点的子节点访问数量。
     * 首先把根节点入栈，因为根节点是前序遍历中的第一个节点。随后每次我们从栈顶取出一个节点 u，它是我们当前遍历到的节点，
     * 并把 u的所有子节点从右向左逆序压入栈中，这样出栈的节点则是顺序从左向右的。例如 u 的子节点从左到右为 v_1, v_2, v_3那么入栈的顺序应当为 v_3, v_2, v_1v
     * 这样就保证了下一个遍历到的节点（即 u 的左侧第一个孩子节点 v_1出现在栈顶的位置。此时，访问第一个子节点 v_1时，仍然按照此方法则会先访问 v_1的左侧第一个孩子节点。
     * @param root
     * @return
     */
    public List<Integer> preorder3(Node root) {
            List<Integer> res = new ArrayList<>();
            if(root == null) return res;
            Deque<Node> stack = new ArrayDeque<Node>();
            stack.push(root);
            while(!stack.isEmpty()) {
                Node node = stack.pop();
                res.add(node.val);
                for(int i = node.children.size() - 1; i >= 0; --i) stack.push(node.children.get(i));
            }
            return res;

        }


    /**
     * 栈模拟递归
     * 迭代过程中记录 (node = 当前节点, cnt = 当前节点遍历过的子节点数量) 二元组，每次取出栈顶元素，
     * 如果当前节点是首次出队（当前遍历过的子节点数量为 cnt = 0），则将当前节点的值加入答案，然后更新当前元素遍历过的子节点数量，并重新入队
     * 即将 (node, cnt + 1)入队，以及将下一子节点 (node.children[cnt],0) 进行首次入队。
     * 时间复杂度：O(n)
     * 空间复杂度：O(n)
     * @param root
     * @return
     */
    public List<Integer> preorder01(Node root) {
        List<Integer> ans = new ArrayList<>();
        Deque<Object[]> d = new ArrayDeque<>();
        d.addLast(new Object[]{root, 0});
        while (!d.isEmpty()) {
            Object[] poll = d.pollLast();
            Node t = (Node)poll[0];
            Integer cnt = (Integer)poll[1];
            if (t == null) continue;
            if (cnt == 0) ans.add(t.val);
            if (cnt < t.children.size()) {
                d.addLast(new Object[]{t, cnt + 1});
                d.addLast(new Object[]{t.children.get(cnt), 0});
            }
        }
        return ans;
    }

    /**
     * 另外一种「递归」转「迭代」的做法，是直接模拟系统执行「递归」的过程，这是一种更为通用的做法。
     * 由于现代编译器已经做了很多关于递归的优化，现在这种技巧已经无须掌握。
     * 在迭代过程中记录当前栈帧位置状态 loc，在每个状态流转节点做相应操作。

     * @param root
     * @return
     */
    public List<Integer> preorder02(Node root) {
        List<Integer> ans = new ArrayList<>();
        Deque<Object[]> d = new ArrayDeque<>();
        d.addLast(new Object[]{0, root});
        while(!d.isEmpty()) {
            Object[] poll = d.pollLast();
            Integer loc = (Integer) poll[0];
            Node t = (Node) poll[1];
            if(t == null) continue;
            if(loc == 0) {
                ans.add(t.val);
                d.addLast(new Object[]{1, t});
            }
            else if(loc == 1) {
                int n = t.children.size();
                for(int i = n - 1; i >= 0; i--) d.addLast(new Object[]{0, t.children.get(i)});
            }
        }
        return ans;
    }


    public List<Integer> preorder03(Node root) {
        List<Integer> res = new ArrayList<>();
        Deque<Node> stack = new LinkedList<>();
        if(root != null) stack.push(root);
        while(!stack.isEmpty()) {
            //弹出父节点，并把值加入结果
            Node node = stack.pop();
            res.add(node.val);
            //从右到左进栈，出栈就是左右
            for(int i = node.children.size() - 1; i >= 0; --i)
                stack.push(node.children.get(i));
        }
        return res;
    }

    /**
     * N叉树的前序遍历代码操作的顺序是，根节点—>子节点（从右到左进栈，出来的顺序为左到右），对应得到的遍历结果就是 根节点+子节点（从左到右）。
     * N叉树的后序遍历代码操作的顺序是，根节点->子节点（从左到右进栈，出来的顺序为右到左），对应得到的遍历结果就是 根节点+子节点（从右到左）。
     * 再把这个结果反转一下，遍历结果就是 子节点（从左到右）+ 根节点。
     * 代码中用 for(int i = 0; i < node.children.size(); ++ i) 来控制从左到右的进栈顺序。
     * 代码里用头插法进行反转，list.add(0, node.val) ，也可以在最后用函数反转。
     * @param root
     * @return
     */
    public List<Integer> postorder(Node root) {
        //把前序遍历的   根节点--子节点（从左到右）  改一下，变成  根节点--子节点（从右到左） ，然后再反转一下变成子节点（从左到右）-根节点
        List<Integer> res = new ArrayList<>();
        Deque<Node> stack = new LinkedList<>();
        if(root != null) stack.push(root);
        while(!stack.isEmpty()) {
            Node node = stack.pop();
            res.add(0, node.val);
            for(int i = 0; i < node.children.size(); i++)
                stack.push(node.children.get(i));
        }
        return res;
    }


}
