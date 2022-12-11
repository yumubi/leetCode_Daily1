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


}
