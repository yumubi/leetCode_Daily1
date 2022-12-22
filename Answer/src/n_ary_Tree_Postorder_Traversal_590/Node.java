package n_ary_Tree_Postorder_Traversal_590;

import java.util.*;

public class Node {
    public int val;
    public List<Node> children;

    public Node() {}

    public Node(int _val) {
        val = _val;
    }

    public Node(int _val, List<Node> _children) {
        val = _val;
        children = _children;
    }

    class Solution {
        List<Integer> res = new LinkedList<>();
        public List<Integer> postorder(Node root) {
            dfs(root);
            return res;
        }

        void dfs(Node root) {
            if(root == null) return;
            for(int i = 0; i < root.children.size(); i++)
                dfs(root.children.get(i));
            res.add(root.val);
        }

        /**
         * 递归
         * @param root
         * @return
         */
        public List<Integer> postorder1(Node root) {
            List<Integer> res = new LinkedList<>();
            helper(root, res);
            return res;
        }

        public void helper(Node root, List<Integer> res) {
            if(root == null) return;
            for(Node ch : root.children) helper(ch, res);
            res.add(root.val);
        }


        /**
         * 迭代
         * 在这里的栈模拟中比较难处理的在于从当前节点 u 的子节点 v_1
         * 返回时，此时需要处理节点 u 的下一个节点 v_2
         * 此时需要记录当前已经遍历完成哪些子节点，才能找到下一个需要遍历的节点。在二叉树树中因为只有左右两个子节点，因此比较方便处理，
         * 在 N 叉树中由于有多个子节点，因此使用哈希表记录当前节点 u 已经访问过哪些子节点。
         *
         * 每次入栈时都将当前节点的 u 的第一个子节点压入栈中，直到当前节点为空节点为止。
         * 每次查看栈顶元素 p，如果节点 p 的子节点已经全部访问过，则记录当前节点的值，并将节点 p 的从栈中弹出，并从哈希表中移除，
         * 表示该以该节点的子树已经全部遍历过；如果当前节点 p 的子节点还有未遍历的，则将当前节点的 p 的下一个未访问的节点压入到栈中，重复上述的入栈操作

         * @param root
         * @return
         */
        public List<Integer> postorder2(Node root) {
            List<Integer> res = new ArrayList<Integer>();
            if(root == null) return res;
            Map<Node, Integer> map = new HashMap<Node, Integer>();
            Deque<Node> stack = new ArrayDeque<Node>();
            Node node = root;
            while(!stack.isEmpty() || node != null) {
                while(node != null) {
                    stack.push(node);
                    List<Node> children = node.children;
                    if(children != null && children.size() > 0) {
                        map.put(node, 0);
                        node = children.get(0);
                    } else node = null;
                }
                node = stack.peek();
                int index = map.getOrDefault(node, -1) + 1;
                List<Node> children = node.children;
                if(children != null && children.size() > index) {
                    map.put(node, index);
                    node = children.get(index);
                } else {
                    res.add(node.val);
                    stack.pop();
                    map.remove(node);
                    node = null;
                }
            }
            return res;
        }


        /**
         * 迭代优化
         * 在后序遍历中，我们会先从左向右依次后序遍历每个子节点为根的子树，再遍历根节点本身。此时利用栈先进后出的原理，依次从右向左将子节点入栈，
         * 这样出栈的时候即可保证从左向右依次遍历每个子树。参考方法二的原理，可以提前将后续需要访问的节点压入栈中。
         * 首先把根节点入栈，因为根节点是前序遍历中的第一个节点。随后每次我们找到栈顶节点 u，如果当前节点的子节点没有遍历过，
         * 则应该先把 u的所有子节点从右向左逆序压入栈中，这样出栈的节点则是顺序从左向右的，同时对节点 u进行标记，表示该节点的子节点已经全部入栈；
         * 如果当前节点 u为叶子节点或者当前节点的子节点已经全部遍历过，则从栈中弹出节点 u，并记录节点 u 的值。例如 u 的子节点从左到右为 v_1, v_2, v_3v
         * 入栈的顺序应当为 v_3, v_2, v_1这样就保证了下一个遍历到的节点（即 u 的左侧第一个孩子节点 v_1出现在栈顶的位置。
         * 此时，访问第一个子节点 v_1时，仍然按照此方法则会先访问 v_1的左侧第一个孩子节点。
         * @param root
         * @return
         */
        public List<Integer> postorder3(Node root) {
            List<Integer> res = new ArrayList<>();
            if(root == null) return res;
            Deque<Node> stack = new ArrayDeque<Node>();
            // 创建一个 HashSet 来统计 每个节点的子节点是否完全被遍历过一次
            Set<Node> visited = new HashSet<>();

            stack.push(root);
            while(!stack.isEmpty()) {
                Node node =stack.peek();
                /* 如果当前节点为叶子节点或者当前节点的子节点已经遍历过 */
                // 如果 其没有 children 序列
                // 或者 其子序列已经被遍历过一次
                // 就进行统计
                // 并将其移除
                if(node.children.size() == 0 || visited.contains(node)) {
                    stack.pop();
                    res.add(node.val);
                    continue;
                }
                // 如果其子节点序列不为空
                // 或者仍未访问过，则将其子节点序列压入栈中
                // 等待下一次迭代
                // 由于我们统计是从左到右顺序统计，因此我们在压入栈中的时候要倒序压入，以便后续迭代
                for(int i = node.children.size() - 1; i >= 0; --i ) {
                    stack.push(node.children.get(i));
                }
                // 此节点其子节点已经被遍历过了，因此放入 HashSet 中进行标记
                visited.add(node);
            }
            return res;

        }


        /**
         *利用前序遍历反转 todo
         * 在前序遍历中，我们会先遍历节点本身，然后从左向右依次先序遍历该每个以子节点为根的子树，
         * 而在后序遍历中，需要先从左到右依次遍历每个以子节点为根的子树，然后再访问根节点。
         * 例如：当前的节点为 u，它的从左至右子节点依次为 v_1, v_2, v_3
         * 此时我们可以知道它的前序遍历结果为：
         *[u,v1,children(v1),v2,children(v2),v3,children(v3)]
         * 后序遍历结果为:
         * [children(v1),v1,children(v2),v2,children(v3),v3,u]
         * 其中children(v_k) 表示以 v_k为根节点的子树的遍历结果（不包括 v_k）。
         * 仔细观察可以知道，将前序遍历中子树的访问顺序改为从右向左可以得到如下访问顺序：
         *将上述的结果进行反转，得到:
         * 刚好与后续遍历的结果相同。此时我们可以利用前序遍历，只不过前序遍历中对子节点的遍历顺序是从左向右，而这里是从右向左。
         * 因此我们可以使用和 N 叉树的前序遍历相同的方法，使用一个栈来得到后序遍历。
         * 我们首先把根节点入栈。当每次我们从栈顶取出一个节点 u 时，就把 u 的所有子节点顺序推入栈中。例如 u 的子节点从左到右为 v_1, v_2, v_3
         * 那么推入栈的顺序应当为 v_1, v_2, v_3，这样就保证了出栈顺序是从右向左，下一个遍历到的节点（即 u 的右侧第一个子节点 v_3出现在栈顶的位置。
         * 在遍历结束之后，我们把遍历结果进行反转，就可以得到后序遍历。
         * @return
         */
        public List<Integer> postorder4(Node root) {
            List<Integer> res = new ArrayList<>();
            if(root == null) return res;

            Deque<Node> stack = new ArrayDeque<>();
            stack.push(root);
            while(!stack.isEmpty()) {
                Node node = stack.pop();
                res.add(node.val);
                for(Node item : node.children) stack.push(item);
            }
            Collections.reverse(res);
            return res;
        }


        /**
         * 针对本题，使用「栈」模拟递归过程。
         * 迭代过程中记录 (cnt = 当前节点遍历过的子节点数量, node = 当前节点) 二元组，每次取出栈顶元素，
         * 如果当前节点已经遍历完所有的子节点（当前遍历过的子节点数量为 cnt = 子节点数量cnt=子节点数量），则将当前节点的值加入答案。
         * 否则更新当前元素遍历过的子节点数量，并重新入队，即将 (cnt+1,node) 入队，以及将下一子节点 (0,node.children[cnt]) 进行首次入队
         * 时间复杂度：O(n)O(n)
         * 空间复杂度：O(n)O(n)。
         * @param root
         * @return
         */
        public List<Integer> postorder5(Node root) {
            List<Integer> ans = new ArrayList<>();
            Deque<Object[]> d = new ArrayDeque<>();
            d.addLast(new Object[]{0, root});
            while(!d.isEmpty()) {
                Object[] poll = d.pollLast();
                Integer cnt = (Integer) poll[0];
                Node t = (Node) poll[1];
                if(t == null) continue;
                if(cnt == t.children.size()) ans.add(t.val);
                if(cnt < t.children.size()) {
                    d.addLast(new Object[]{cnt + 1, t});
                    d.addLast(new Object[]{0, t.children.get(cnt)});
                }
            }
            return ans;
        }



        public List<Integer> postorder6(Node root) {
            List<Integer> ans = new ArrayList<>();
            Deque<Object[]> d = new ArrayDeque<>();
            d.addLast(new Object[]{0, root});
            while(!d.isEmpty()) {
                Object[] poll = d.pollLast();
                Integer loc = (Integer) poll[0];
                Node t = (Node)poll[1];
                if(t == null) continue;
                if(loc == 0) {
                    d.addLast(new Object[]{1, t});
                    int n = t.children.size();
                    for(int i = n - 1; i >= 0; i--)
                        d.addLast(new Object[]{0, t.children.get(i)});
                } else if(loc == 1) {
                    ans.add(t.val);
                }
            }
            return ans;
        }

       // 二叉树与N叉树的唯一区别仅仅在于 孩子结点的数量，在二叉树的后序非递归算法中，我们使用了一个变量 prev 来记录上一个遍历的结点，
        // 这个变量的作用是用于区分栈顶结点的右子树是否已经遍历过，回忆二叉树的前序和中序遍历，栈中存放的结点是 未访问的 和 访问过左子树的 结点，
        // 而后序遍历栈中同时存放了 访问过左子树和访问过右子树的 结点，所以需要一个标志即 prev 来区分这两种结点

        /**
         * 现在，从二叉树拓展到N叉树，孩子结点的数量不再是只有两个了，栈中可能会同时存在 访问过一个孩子结点的、访问过两个孩子结点的、访问过三个孩子结点的…… 的结点，
         * 这个时候仅仅一个标志已经无法帮助我们区分这些不同访问层次的结点(后面仔细想想一个标志也可以实现，但是需要遍历孩子数组检索遍历到哪个孩子结点了，
         * 一方面会增加编程难度，另一方面如果N很大还会增大时间复杂度)，
         * 所以相对应的对于每一个结点，我们都要设置一个标志来记录该结点有多少个孩子结点已经遍历过了。
         * 由此，我们很容易就能由二叉树的后序非递归得到N叉树的后序非递归算法：
         * 对于访问二叉树的左孩子结点，我们改为访问N叉树的第一个孩子结点
         * 对于访问二叉树的右孩子结点，我们取得标志(即已访问过的孩子数)然后访问下一个孩子结点
         * 对于判断是否访问过右孩子结点，我们根据标志判断孩子结点是否全部访问过
         * 时间复杂度: O(n×N) ， 每个结点都会入栈出栈 孩子数量 次，满N叉树下复杂度达到O(n×N) ，其中 n 为树中结点的数量
         * 空间复杂度: O(n)，N叉树为链表状时空间复杂度最大
         * @param root
         * @return
         */

    public List<Integer> postorder7(Node root) {
            List<Integer> value = new ArrayList<>();
            //child记录对应节点已经遍历过的孩子数量
            Deque<Node> s = new ArrayDeque<>();
            Deque<Integer> chlid = new ArrayDeque<>();
            Node cur = root;
            int next;
            while(!s.isEmpty() || cur != null) {
                while(cur != null) {
                    s.addLast(cur);
                    chlid.add(0);
                    if(cur.children.size() > 0) {
                        cur = cur.children.get(0);
                    } else cur = null;
                }
                cur = s.pollLast();
                next = chlid.pollLast();
                if(cur.children.size() == 0 || next >= cur.children.size()) {
                    value.add(cur.val);
                    cur = null;
                } else {
                    s.addLast(cur);
                    chlid.addLast(next + 1);
                    if(next + 1 < cur.children.size())
                        cur = cur.children.get(next + 1);
                    else cur = null;
                }
            }
            return value;
    }











    }
}
