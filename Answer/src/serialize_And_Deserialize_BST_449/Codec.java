//package serialize_And_Deserialize_BST_449;
//
//
//import java.util.*;
//
//public class Codec {
//
//
//    /**
//     * 后序遍历
//     * 给定一棵二叉树的「先序遍历」和「中序遍历」可以恢复这颗二叉树。给定一棵二叉树的「后序遍历」和「中序遍历」也可以恢复这颗二叉树。
//     * 而对于二叉搜索树，给定「先序遍历」或者「后序遍历」，对其经过排序即可得到「中序遍历」。因此，仅对二叉搜索树做「先序遍历」或者「后序遍历」，
//     * 即可达到序列化和反序列化的要求。此题解采用「后序遍历」的方法。
//     * 序列化时，只需要对二叉搜索树进行后序遍历，再将数组编码成字符串即可。
//     * 反序列化时，需要先将字符串解码成后序遍历的数组。在将后序遍历的数组恢复成二叉搜索树时，不需要先排序得到中序遍历的数组再根据中序和后序遍历的数组来恢复二叉树，
//     * 而可以根据有序性直接由后序遍历的数组恢复二叉搜索树。
//     * 后序遍历得到的数组中，根结点的值位于数组末尾，左子树的节点均小于根节点的值，右子树的节点均大于根节点的值，
//     * 可以根据这些性质设计递归函数恢复二叉搜索树。
//     *
//     * 时间复杂度：O(n)，其中 n 是树的节点数。serialize 需要 O(n) 时间遍历每个点。deserialize 需要 O(n) 时间恢复每个点。
//     * 空间复杂度：O(n)，其中 n 是树的节点数。serialize 需要 O(n) 空间用数组保存每个点的值，递归的深度最深也为 O(n)。
//     * deserialize 需要 O(n) 空间用数组保存每个点的值，递归的深度最深也为 O(n)。
//     * @param root
//     * @return
//     */
//    // Encodes a tree to a single string.
//    public String serialize(TreeNode root) {
//        List<Integer> list = new ArrayList<Integer>();
//        postOrder(root, list);
//        String str = list.toString();
//        return str.substring(1, str.length() - 1);
//    }
//
//
//    // Decodes your encoded data to tree.
//    public TreeNode deserialize(String data) {
//            if(data.isEmpty()) return null;
//            String[] arr = data.split(", ");
//            Deque<Integer> stack = new ArrayDeque<Integer>();
//            int length = arr.length;
//            for(int i = 0; i < length; i++) {
//                stack.push(Integer.parseInt(arr[i]));
//            }
//            return construct(Integer.MIN_VALUE, Integer.MAX_VALUE, stack);
//    }
//
//
//    private void postOrder(TreeNode root, List<Integer> list) {
//        if(root == null) return;
//        postOrder(root.left, list);
//        postOrder(root.right, list);
//        list.add(root.val);
//    }
//
//    private TreeNode construct(int lower, int upper, Deque<Integer> stack) {
//        if(stack.isEmpty() || stack.peek() < lower || stack.peek() > upper) return null;
//        int val = stack.pop();
//        TreeNode root = new TreeNode(val);
//        root.right = construct(val, upper, stack);
//        root.left = construct(lower, val, stack);
//        return root;
//    }
//
//
////分治思想，举个栗子，如下BST：
////        5
////      /    \
////    2       7
////  /  \      / \
//// 1   3   6    9
////                / \
////              8    10
////前序遍历的结果为：5,2,1,3,7,6,9,8,10
////根据前序遍历的结果，重构二叉树(每次取当前部分的首位，取不到首位就返回进入下一个位置，顺序：左→右)：
//// step1：
////             5
////        /          \
////    {2,1,3}    {7,,6,9,8,10}
////step2：
////             5
////        /          \
////     2              7
////   /                   \
//// {1,3}              {6,9,8,10}
////step3:（此处是关键，{1,3}，{6,9}在一次迭代中就能获得）
////             5
////        /          \
////     2              7
////   /  \             /  \
//// 1    3          6,   9
////                            \
////                           {8,10}
////step4：
////...............
//
//
//    /**
//     * 「前序遍历 + BST 特性」：
//     * 序列化：对 BST 进行「前序遍历」，并跳过空节点，节点值通过 , 进行分割，假设最终序列化出来的字符串是 s。
//     * 之所以使用「前序遍历」是为了方便反序列化：首先对于某个子树而言，其必然是连续存储，也就是必然能够使用 s[l,r] 所表示处理，同时首位元素必然是该子树的头结点；
//     * 反序列化：将 s 根据分隔符 , 进行分割，假设分割后数组 s 长度为 n，那么ss[0,n−1] 代表完整的子树，我们可以利用「二叉树」特性递归构建，
//     * 设计递归函数 TreeNode dfs2(int l, int r, Sring[] ss)，其含义为利用 ss[l,r] 连续段构造二叉树，并返回头结点：
//     * ss[l] 为头结点，其值为 t，在 [l,r] 范围内找到第一个比 t 大的位置 j：
//     * ss[l] 的左子树的所有值均比 t 小，且在 s 中连续存储，我们可以递归处理 [l+1,j−1] 构建左子树；
//     * ss[l] 的右子树的所有值均比 t 大，且在 s 中连续存储，我们可以递归处理 [j,r] 构建右子树。
//     *
//     * 时间复杂度：令节点数量为 n，序列化的复杂度为O(n)；反序列时由于存在「找第一个比头结点值大的位置」操作，每个节点可能被扫描多次，
//     * 扫描次数与当前节点所在的深度相关，最坏情况下为一条往左下方的链，复杂度为 O(n^2)
//     * 空间复杂度：O(n)
//     */
//
//    public class Codec {
//        public String serialize(TreeNode root) {
//            if (root == null) return null;
//            List<String> list = new ArrayList<>();
//            dfs1(root, list);
//            int n = list.size();
//            StringBuilder sb = new StringBuilder();
//            for (int i = 0; i < n; i++) {
//                sb.append(list.get(i));
//                if (i != n - 1) sb.append(",");
//            }
//            return sb.toString();
//        }
//        void dfs1(TreeNode root, List<String> list) {
//            if (root == null) return ;
//            list.add(String.valueOf(root.val));
//            dfs1(root.left, list);
//            dfs1(root.right, list);
//        }
//        public TreeNode deserialize(String s) {
//            if (s == null) return null;
//            String[] ss = s.split(",");
//            return dfs2(0, ss.length - 1, ss);
//        }
//        TreeNode dfs2(int l, int r, String[] ss) {
//            if (l > r) return null;
//            int j = l + 1, t = Integer.parseInt(ss[l]);
//            TreeNode ans = new TreeNode(t);
//            while (j <= r && Integer.parseInt(ss[j]) <= t) j++;
//            ans.left = dfs2(l + 1, j - 1, ss);
//            ans.right = dfs2(j, r, ss);
//            return ans;
//        }
//    }
//
//    /**
//     * 二分优化
//     * 在解法一中的「反序列操作」操作的瓶颈在于需要「找第一个比头结点值大的位置」。
//     * 假设连续段 s[l,r] 代表某棵子树的话，由于我们是采用「前序遍历」的方式生成 s，因此头结点必然是s[l]，
//     * 而对于头结点的左右子树，必然是连续两段（先左再右）的形式存储在[l+1,r] 中，同时由于该子树是 BST，
//     * 因此这连续两段必然满足「前一段（左子树）小于 t」和「后一段（右子树）大于 t」。
//     * 即具有「二段性」，因此「找第一个比头结点值大的位置」可用「二分」实现。
//     *
//     * 时间复杂度：令节点数量为 n，序列化的复杂度为O(n)；反序列时由于存在「找第一个比头结点值大的位置」操作，最坏情况下为一条往左下方的链，
//     * 该操作采用「二分」，复杂度为 O(logn)，整体复杂度为 O(nlogn)
//     * 空间复杂度：O(n)
//     */
//    public class Codec {
//        public String serialize(TreeNode root) {
//            if (root == null) return null;
//            List<String> list = new ArrayList<>();
//            dfs1(root, list);
//            int n = list.size();
//            StringBuilder sb = new StringBuilder();
//            for (int i = 0; i < n; i++) {
//                sb.append(list.get(i));
//                if (i != n - 1) sb.append(",");
//            }
//            return sb.toString();
//        }
//        void dfs1(TreeNode root, List<String> list) {
//            if (root == null) return ;
//            list.add(String.valueOf(root.val));
//            dfs1(root.left, list);
//            dfs1(root.right, list);
//        }
//        public TreeNode deserialize(String s) {
//            if (s == null) return null;
//            String[] ss = s.split(",");
//            return dfs2(0, ss.length - 1, ss);
//        }
//        TreeNode dfs2(int l, int r, String[] ss) {
//            if (l > r) return null;
//            int ll = l + 1, rr = r, t = Integer.parseInt(ss[l]);
//            while (ll < rr) {
//                int mid = ll + rr >> 1;
//                if (Integer.parseInt(ss[mid]) > t) rr = mid;
//                else ll = mid + 1;
//            }
//            if (Integer.parseInt(ss[rr]) <= t) rr++;
//            TreeNode ans = new TreeNode(t);
//            ans.left = dfs2(l + 1, rr - 1, ss);
//            ans.right = dfs2(rr, r, ss);
//            return ans;
//        }
//    }
//}
//
//
//
//public class Codec {
//
//    //把树转化为字符串（使用BFS遍历）
//    public String serialize(TreeNode root) {
//        //边界判断，如果为空就返回一个字符串"#"
//        if (root == null)
//            return "#";
//        //创建一个队列
//        Queue<TreeNode> queue = new LinkedList<>();
//        StringBuilder res = new StringBuilder();
//        //把根节点加入到队列中
//        queue.add(root);
//        while (!queue.isEmpty()) {
//            //节点出队
//            TreeNode node = queue.poll();
//            //如果节点为空，添加一个字符"#"作为空的节点
//            if (node == null) {
//                res.append("#,");
//                continue;
//            }
//            //如果节点不为空，把当前节点的值加入到字符串中，
//            //注意节点之间都是以逗号","分隔的，在下面把字符
//            //串还原二叉树的时候也是以逗号","把字符串进行拆分
//            res.append(node.val + ",");
//            //左子节点加入到队列中（左子节点有可能为空）
//            queue.add(node.left);
//            //右子节点加入到队列中（右子节点有可能为空）
//            queue.add(node.right);
//        }
//        return res.toString();
//    }
//
//    //把字符串还原为二叉树
//    public TreeNode deserialize(String data) {
//        //如果是"#"，就表示一个空的节点
//        if (data == "#")
//            return null;
//        Queue<TreeNode> queue = new LinkedList<>();
//        //因为上面每个节点之间是以逗号","分隔的，所以这里
//        //也要以逗号","来进行拆分
//        String[] values = data.split(",");
//        //上面使用的是BFS，所以第一个值就是根节点的值，这里创建根节点
//        TreeNode root = new TreeNode(Integer.parseInt(values[0]));
//        queue.add(root);
//        for (int i = 1; i < values.length; i++) {
//            //队列中节点出栈
//            TreeNode parent = queue.poll();
//            //因为在BFS中左右子节点是成对出现的，所以这里挨着的两个值一个是
//            //左子节点的值一个是右子节点的值，当前值如果是"#"就表示这个子节点
//            //是空的，如果不是"#"就表示不是空的
//            if (!"#".equals(values[i])) {
//                TreeNode left = new TreeNode(Integer.parseInt(values[i]));
//                parent.left = left;
//                queue.add(left);
//            }
//            //上面如果不为空就是左子节点的值，这里是右子节点的值，注意这里有个i++，
//            if (!"#".equals(values[++i])) {
//                TreeNode right = new TreeNode(Integer.parseInt(values[i]));
//                parent.right = right;
//                queue.add(right);
//            }
//        }
//        return root;
//    }
//}
//
//
//
//class Codec {
//
//    //把树转化为字符串（使用DFS遍历，也是前序遍历，顺序是：根节点→左子树→右子树）
//    public String serialize(TreeNode root) {
//        //边界判断，如果为空就返回一个字符串"#"
//        if (root == null)
//            return "#";
//        return root.val + "," + serialize(root.left) + "," + serialize(root.right);
//    }
//
//    //把字符串还原为二叉树
//    public TreeNode deserialize(String data) {
//        //把字符串data以逗号","拆分，拆分之后存储到队列中
//        Queue<String> queue = new LinkedList<>(Arrays.asList(data.split(",")));
//        return helper(queue);
//    }
//
//    private TreeNode helper(Queue<String> queue) {
//        //出队
//        String sVal = queue.poll();
//        //如果是"#"表示空节点
//        if ("#".equals(sVal))
//            return null;
//        //否则创建当前节点
//        TreeNode root = new TreeNode(Integer.valueOf(sVal));
//        //分别创建左子树和右子树
//        root.left = helper(queue);
//        root.right = helper(queue);
//        return root;
//    }
//}
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//// Your Codec object will be instantiated and called as such:
//// Codec ser = new Codec();
//// Codec deser = new Codec();
//// String tree = ser.serialize(root);
//// TreeNode ans = deser.deserialize(tree);
//// return ans;
//
