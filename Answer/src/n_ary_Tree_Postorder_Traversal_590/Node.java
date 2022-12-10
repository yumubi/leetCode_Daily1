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
         * 每次入栈时都将当前节点的 uu 的第一个子节点压入栈中，直到当前节点为空节点为止。
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


        public List<Integer> postorder(Node root) {
            List<Integer> res = new ArrayList<>();
            if(root == null) return res;
            Deque<Node> stack = new ArrayDeque<Node>();
            Set<Node> visited = new HashSet<>();

            stack.push(root);
            while(!stack.isEmpty()) {
                Node node =stack.peek();
                if(node.children.size() == 0 || visited.contains(node)) {
                    stack.pop();
                    res.add(node.val);
                    continue;;
                }
                for(int i = node.children.size() - 1; i >= 0; --i ) {
                    stack.push(node.children.get(i));
                }
                visited.add(node);
            }
            return res;

        }















    }
}
