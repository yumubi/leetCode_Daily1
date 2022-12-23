package verify_Preorder_Serialization_Of_A_Binary_Tree_331;

import java.util.Deque;
import java.util.LinkedList;

public class Solution {
    //不会写


    //我们可以定义一个概念，叫做槽位。一个槽位可以被看作「当前二叉树中正在等待被节点填充」的那些位置。
    //二叉树的建立也伴随着槽位数量的变化。每当遇到一个节点时：
    //如果遇到了空节点，则要消耗一个槽位；
    //如果遇到了非空节点，则除了消耗一个槽位外，还要再补充两个槽位。
    //使用栈来维护槽位的变化。栈中的每个元素，代表了对应节点处剩余槽位的数量，而栈顶元素就对应着下一步可用的槽位数量。
    // 当遇到空节点时，仅将栈顶元素减 1；当遇到非空节点时，将栈顶元素减 1 后，再向栈中压入一个 2。无论何时，如果栈顶元素变为 0，就立刻将栈顶弹出。
    // 遍历结束后，若栈为空，说明没有待填充的槽位，因此是一个合法序列；否则若栈不为空，则序列不合法。此外，在遍历的过程中，若槽位数量不足，则序列不合法。
    //时间复杂度：O(n)，其中 n 为字符串的长度。我们每个字符只遍历一次，同时每个字符对应的操作都是常数时间的。
    //空间复杂度：O(n)。此为栈所需要使用的空间。
    public boolean isValidSerialization(String preorder) {
        int n = preorder.length();
        int i = 0;
        Deque<Integer> stack = new LinkedList<Integer>();
        stack.push(1);
        while (i < n) {
            if (stack.isEmpty()) return false;
            if (preorder.charAt(i) == ',') i++;
            else if (preorder.charAt(i) == '#') {
                int top = stack.pop() - 1;
                if (top > 0) stack.push(top);
                i++;
            } else {/// 读一个数字
                while (i < n && preorder.charAt(i) != ',') i++;
                int top = stack.pop() - 1;
                if (top > 0) stack.push(top);
                stack.push(2);
            }

        }
        return stack.isEmpty();
    }

    /**
     * 计数
     * 如果把栈中元素看成一个整体，即所有剩余槽位的数量，也能维护槽位的变化。
     * 因此，我们可以只维护一个计数器，代表栈中所有元素之和，其余的操作逻辑均可以保持不变。
     * 时间复杂度：O(n)，其中 n 为字符串的长度。我们每个字符只遍历一次，同时每个字符对应的操作都是常数时间的。
     * 空间复杂度：O(1)
     *
     * @param preorder
     * @return
     */
    public boolean isValidSerialization01(String preorder) {
        int n = preorder.length();
        int i = 0;
        int slots = 1;
        while (i < n) {
            if (slots == 0) return false;
            if (preorder.charAt(i) == ',') i++;
            else if (preorder.charAt(i) == '#') {
                slots--;
                i++;
            } else {
                while (i < n && preorder.charAt(i) != ',') i++;
                slots++;//slots = slots - 1 + 2;
            }
        }
        return slots == 0;
    }


    /**
     * 令非空节点数量为 m，空节点数量为 n，入度和出度仍然使用 in 和 out 代表。
     *
     * @param s
     * @return
     */
    public boolean isValidSerialization02(String s) {
        String[] ss = s.split(",");
        int n = ss.length;
        int in = 0, out = 0;
        for (int i = 0, m = 0; i < n; i++) {
            if (i != n - 1 && !check(m, in, out)) return false;
        }
        return false;
    }

    boolean check(int m, int in, int out) {
        boolean a = (in <= 2 * m - 1), b = (out <= 2 * m);
        return a && b;
    }

    /**
     * 非空节点数量 >= 空节点数量 在遍历没结束前恒成立：m>=n
     * 之后我们再采用一个技巧，就是遍历过程中每遇到一个「非空节点」就增加两个「出度」和一个「入度」，
     * 每遇到一个「空节点」只增加一个「入度」
     * 时间复杂度：O(n)
     * 空间复杂度：O(n)
     *
     * @param s
     * @return
     */
    public boolean isValidSerialization03(String s) {
        String[] ss = s.split(",");
        int n = ss.length;
        int in = 0, out = 0;
        for (int i = 0; i < n; i++) {
            if (!ss[i].equals("#")) out += 2;
            if (i != 0) in++;
            if (i != n - 1 && out <= in) return false;
        }
        return in == out;
    }

    int idx;
    String[] c;

    public boolean isValidSerialization04(String preorder) {
        c = preorder.split(",");
        idx = 0;
        dfs();
        return idx == c.length - 1;
    }

    public void dfs(){
        // 终止条件
        if(idx >= c.length) return;
        String cur = c[idx];
        if(cur.equals("#")){
            return ;
        }
        // 递归左子节点
        idx++;
        dfs();
        // 递归右子节点
        idx++;
        dfs();
    }


    /**
     * 首先想一想什么时候会失效(错误序列化)？
     * 存在根(子树)，但缺失左子树/右子树/两者。 如"9", "9, #";
     * 存在根，成功序列化左/右子树，但后续存在多余枝。 如："9,#,#,1"
     * 什么时候序列化是正确的？
     * 当我们正常前序遍历(完整的前/左/右)，索引到达末尾时
     * @param preorder
     * @return
     */
        public boolean isValidSerialization05(String preorder) {
            //分割字符串，为空则无树。
            String[] split = preorder.split(",");
            if (split == null) return false;

            //若能前序遍历到末尾，则序列化正确。
            //若endIndex > split.length,则错误情况2.  若endIndex == -1；则错误情况1
            int endIndex = preorderTree(split, 0);
            return endIndex == split.length;
        }

        /**
         * @Description 前序遍历树，若无法正常遍历，返回-1；
         * @date  2020/10/16
         * @Param split: 树
         * @Param index: 下一个遍历点 索引
         * @return: int 下一个遍历点 索引
         */
        private int preorderTree(String[] split, int index) {
            //特判 索引失效。
            if (index >= split.length || index < 0) return -1;

            //该子树为空，返回下一棵子树索引。
            if (split[index].equals("#")) {
                return index + 1;
            }

            //存在主节点。遍历左右枝桠。
            int leftIndex = preorderTree(split, index + 1);
            int rightIndex = preorderTree(split, leftIndex);

            return rightIndex;
        }


}


