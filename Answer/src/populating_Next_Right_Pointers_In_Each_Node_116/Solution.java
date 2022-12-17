package populating_Next_Right_Pointers_In_Each_Node_116;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;

public class Solution {
    /**
     * ac
     * @param root
     * @return
     */
    public Node connect(Node root) {
        if(root == null) return root;
        Deque<Node> queue = new ArrayDeque<Node>();
        Node root1 = root;
        queue.offer(root1);
        while(!queue.isEmpty()) {
            int size = queue.size();
            for(int i = 0; i < size; i++) {
                Node node = queue.poll();
                if( i == size - 1) node.next = null;
                else node.next = queue.peek();
                if(node.left != null) queue.offer(node.left);
                if(node.right != null) queue.offer(node.right);
            }
        }
        return root;
    }

    /**
     * bfs
     * 时间复杂度：O(N)。每个节点会被访问一次且只会被访问一次，即从队列中弹出，并建立 next 指针。
     * 空间复杂度：O(N)。这是一棵完美二叉树，它的最后一个层级包含 N/2 个节点。广度优先遍历的复杂度取决于一个层级上的最大元素数量。这种情况下空间复杂度为 O(N)。
     * @param root
     * @return
     */
    public Node connect1(Node root) {
        if(root == null) return root;
        Queue<Node> queue = new LinkedList<Node>();
        queue.add(root);

        while(!queue.isEmpty()) {
            int size = queue.size();
            for(int i = 0; i < size; i++) {
                Node node = queue.poll();
                if(i < size - 1) node.next = queue.peek();
                if(node.left != null) queue.add(node.left);
                if(node.right != null) queue.add(node.right);
            }
        }
        return root;
    }


    /**
     *使用已建立的next指针----注意是完美二叉树
     * 一棵树中，存在两种类型的 next 指针。
     * 第一种情况是连接同一个父节点的两个子节点。
     * 它们可以通过同一个节点直接访问到，因此执行下面操作即可完成连接。
     * node.left.next = node.right
     *
     * 第二种情况在不同父亲的子节点之间建立连接，这种情况不能直接连接。
     * node.right.next = node.next.left
     *
     *
     * 如果每个节点有指向父节点的指针，可以通过该指针找到 next 节点。如果不存在该指针，则按照下面思路建立连接
     *      第 N 层节点之间建立 next 指针后，再建立第N+1 层节点的 next 指针。可以通过 next 指针访问同一层的所有节点，
     *      因此可以使用第 N 层的 next 指针，为第 N+1 层节点建立 next 指针。
     *
     * 从根节点开始，由于第 0 层只有一个节点，所以不需要连接，直接为第 1 层节点建立 next 指针即可。该算法中需要注意的一点是，当
     * 我们为第 N层节点建立 next 指针时，处于第N−1 层。当第 N 层节点的next 指针全部建立完成后，移至第 N层，建立第N+1 层节点的 next指针。
     * 遍历某一层的节点时，这层节点的 next 指针已经建立。因此我们只需要知道这一层的最左节点，就可以按照链表方式遍历，不需要使用队列。
     *leftmost = root
     * while (leftmost.left != null) {
     *     head = leftmost
     *     while (head.next != null) {
     *         1) Establish Connection 1
     *         2) Establish Connection 2 using next pointers
     *         head = head.next
     *     }
     *     leftmost = leftmost.left
     * }
     *
     * 完成当前层的连接后，进入下一层重复操作，直到所有的节点全部连接。进入下一层后需要更新最左节点，然后从新的最左节点开始遍历该层所有节点。
     * 因为是完美二叉树，因此最左节点一定是当前层最左节点的左孩子。如果当前最左节点的左孩子不存在，说明已经到达该树的最后一层，完成了所有节点的连接
     *
     *间复杂度：O(N)，每个节点只访问一次。
     *空间复杂度：O(1)，不需要存储额外的节点。
     * @param root
     * @return
     */
    public Node connect2(Node root) {
        if(root == null) return root;
        //从根节点开始
        Node leftmost = root;
        while(leftmost.left != null) {
            //遍历这一层节点组织成的链表，为下一层节点更新next
            Node head = leftmost;
            while(head != null) {
                //connection 1
                head.left.next = head.right;
                //connection 2
                if(head.next != null) head.right.next = head.next.left;
//指针后移
                head = head.next;
            }
            //去下一层最左的节点
            leftmost = leftmost.left;
        }
        return root;
    }



    public Node connect3(Node root) {
        if(root == null) return root;
        Node pre = root;
        //循环条件是当前节点的left不为空，当只有根节点或者所有叶子节点都出串联完后循环就退出了
        while(pre.left != null) {
            Node tmp = pre;
            while(tmp != null) {
                //将tmp的左右节点都串联起来
                //外层循环已经判断了当前节点的left不为空
                tmp.left.next = tmp.right;
                //下一个不为空说明上一层已经帮我们完成串联了
                if(tmp.next != null) tmp.right.next = tmp.next.left;
                //继续右边遍历
                tmp = tmp.next;
            }
            //从下一层的最左边开始遍历
            pre = pre.left;
        }
        return root;
    }


    /**
     * 递归
     * https://leetcode.cn/problems/populating-next-right-pointers-in-each-node/solution/dong-hua-yan-shi-san-chong-shi-xian-116-tian-chong/
     * @param root
     * @return
     */
    public Node connect4(Node root) {
        dfs4(root);
        return root;
    }
    void dfs4(Node root) {
        if(root == null) return;
        Node left = root.left;
        Node right = root.right;
        while(left != null) {
            left.next = right;
            left = left.right;
            right = right.left;
        }
        //递归的调用左右节点，完成同样的纵深串联
        dfs4(root.left);
        dfs4(root.right);
    }


    public Node connect5(Node root) {
        if(root == null) return root;
        //cur我们把它看作是每一层的链表
        Node cur = root;
        while(cur != null) {
            //遍历当前层的时候，为了方便操作在下一层前面添加一个哑节点（这里是访问当前层的节点，然后把下一层的节点穿起来）
            Node dummy = new Node(0);
            //pre表示下一层节点的前一个节点
            Node pre = dummy;

            //然后开始遍历当前层的链表,注意是完美二叉树，有左子节点就一定有右子节点
            while(cur != null && cur.left != null) {
                pre.next = cur.left;
                pre = pre.next;

                //pre节点的next指向当前接节点的右子节点
                pre.next = cur.right;
                pre = pre.next;
                //继续访问这一行的下一个节点
                cur = cur.next;
            }
            //把下一层串联成一个链表后，让他赋值给cur，后续继续循环，知道cur为空为止
            cur = dummy.next;
        }
        return root;
    }

    public Node connect6(Node root) {
        if(root == null) return null;
        Node pre = root;
        Node cur = null;
        while(pre.left != null) {
            //遍历当前这一层的节点，然后把他们的下一层连接起来
            cur = pre;
            //cur不为空，就表示这一层还没遍历完，就继续循环
            while(cur != null) {
                //让下一层的左子节点指向右子节点
                cur.left.next = cur.right;
                //如果cur.next不为空，就表示还没遍历到这一层
                //最后的那个节点的右子节点，就让前一个节点的右子节点指向下一个节点的左子节点
                if(cur.next != null) cur.right.next = cur.next.left;
                //然后继续连接下一个节点的子节点
                cur = cur.next;
            }
            //继续下一层
            pre = pre.left;
        }
        return root;
    }

    public Node connect7(Node root) {
        dfs7(root, null);
        return root;
    }
    private void dfs7(Node curr, Node next) {
        if(curr == null) return;
        curr.next = next;
        dfs7(curr.left, curr.right);
        dfs7(curr.right, curr.next == null ? null : curr.next.left);
    }



}
