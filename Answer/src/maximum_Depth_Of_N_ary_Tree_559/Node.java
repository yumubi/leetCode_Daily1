package maximum_Depth_Of_N_ary_Tree_559;

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
        public int maxDepth(Node root) {
            return depth(root);
        }

        int depth(Node root){
            if(root == null) return 0;
            int max = 0;
            for(int i = 0; i < root.children.size(); i++) {
               int pathDepth=  depth(root.children.get(i));
               if(pathDepth > max) max = pathDepth;
            }
            return max + 1;
        }


        /**
         * dfs
         * @param root
         * @return
         */
        public int maxDepth1(Node root) {
            if(root == null) return 0;
            int maxChildDepth = 0;
            List<Node> children = root.children;
            for(Node child : children) {
                int childDepth = maxDepth(child);
                maxChildDepth = Math.max(maxChildDepth, childDepth);
            }
            return maxChildDepth + 1;


//
//                public int maxDepth(Node root) {
//                    if (root == null) return 0;
//                    int ans = 0;
//                    for (Node node : root.children) {
//                        ans = Math.max(ans, maxDepth(node));
//                    }
//                    return ans + 1;
//                }
//



        }


        /**
         * bfs
         *
         * 时我们广度优先搜索的队列里存放的是「当前层的所有节点」。每次拓展下一层的时候，不同于广度优先搜索的每次只从队列里拿出一个节点，
         * 我们需要将队列里的所有节点都拿出来进行拓展，这样能保证每次拓展完的时候队列里存放的是当前层的所有节点，即我们是一层一层地进行拓展。
         * 最后我们用一个变量 ans 来维护拓展的次数，该 N 叉树的最大深度即为 ans。
         * @param root
         * @return
         */
        public int maxDepth2(Node root) {
            if(root == null) return 0;
            Queue<Node> queue = new LinkedList<Node>();
            queue.offer(root);
            int ans = 0;
            while(!queue.isEmpty()) {
                int size = queue.size();
                while(size > 0) {
                    Node node = queue.poll();
                    List<Node> children = node.children;
                    for(Node child : children) {
                        queue.offer(child);
                    }
                    size--;
                }
                ans++;
            }
            return ans;
        }




//            public int maxDepth(Node root) {
//                if (root == null) return 0;
//                int ans = 0;
//                Deque<Node> d = new ArrayDeque<>();
//                d.addLast(root);
//                while (!d.isEmpty()) {
//                    int size = d.size();
//                    while (size-- > 0) {
//                        Node t = d.pollFirst();
//                        for (Node node : t.children) {
//                            d.addLast(node);
//                        }
//                    }
//                    ans++;
//                }
//                return ans;
//            }


        //dfs迭代
        public int maxDepth3(Node root) {
            if (root == null) {
                return 0;
            }
            LinkedList<Node> stack = new LinkedList<>();
            Set<Node> set = new HashSet<>();
            LinkedList<Integer> level = new LinkedList<>();
            set.add(root); // 用 set 标记已经走过的分支
            stack.addLast(root);
            level.addLast(1); // 记录当前节点对应的深度
            int maxLevel = Integer.MIN_VALUE;
            while (!stack.isEmpty()) {
                Node node = stack.pollLast();
                int curLevel = level.pollLast();
                if(!node.children.isEmpty()) {
                    for (Node child : node.children) {
                        if(!set.contains(child)) {
                            stack.addLast(node);
                            stack.addLast(child);
                            level.addLast(curLevel);
                            level.addLast(curLevel+1);
                            set.add(child);
                            break;
                        }
                    }
                } else {
                    maxLevel =Math.max(maxLevel, curLevel);
                }
            }
            return maxLevel;
        }






    }
}
