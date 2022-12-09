package convert_Sorted_List_To_BST_109;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class Solution {
    /**
     * ac
     * @param head
     * @return
     */
    public TreeNode sortedListToBST(ListNode head) {
        if(head == null) return null;
        List<Integer> list = new ArrayList<>();
        ListNode ptr = head;
        while(ptr != null) {
            list.add(ptr.val);
            ptr = ptr.next;
        }
        int len = list.size();
        TreeNode root =  partition(head, 1, len, list);
        return root;
    }

    public TreeNode partition(ListNode head, int left, int right, List<Integer> list) {
        if (left > right) return null;
        int mid = left + (right - left + 1) / 2;
        TreeNode node = new TreeNode(list.get(mid - 1));
        node.left = partition(head, left, mid - 1, list);
        node.right = partition(head, mid + 1, right, list);
        return node;
    }


    /**
     * 分治
     * 将给定的有序链表转换为二叉搜索树的第一步是确定根节点。由于我们需要构造出平衡的二叉树，
     * 因此比较直观的想法是让根节点左子树中的节点个数与右子树中的节点个数尽可能接近。这样一来，左右子树的高度也会非常接近，可以达到高度差绝对值不超过 1 的题目要求
     *
     * 这里对于中位数的定义为：如果链表中的元素个数为奇数，那么唯一的中间值为中位数；如果元素个数为偶数，那么唯二的中间值都可以作为中位数，而不是常规定义中二者的平均值
     *
     * 具体地，设当前链表的左端点为 left，右端点 right，包含关系为「左闭右开」，
     * 即 left 包含在链表中而 right 不包含在链表中。我们希望快速地找出链表的中位数节点 mid。
     *找出链表中位数节点的方法多种多样，其中较为简单的一种是「快慢指针法」。
     * 初始时，快指针 fast 和慢指针 slow 均指向链表的左端点 left。我们将快指针 fast 向右移动两次的同时，将慢指针 slow 向右移动一次，
     * 直到快指针到达边界（即快指针到达右端点或快指针的下一个节点是右端点）。此时，慢指针对应的元素就是中位数
     * 在找出了中位数节点之后，我们将其作为当前根节点的元素，并递归地构造其左侧部分的链表对应的左子树，以及右侧部分的链表对应的右子树
     *
     * 时间复杂度：O(nlogn)，其中 n 是链表的长度。
     * 设长度为 n 的链表构造二叉搜索树的时间为 T(n)，递推式为 T(n)=2⋅T(n/2)+O(n)，根据主定理，T(n)=O(nlogn)。
     * 空间复杂度：O(logn)，这里只计算除了返回答案之外的空间。平衡二叉树的高度为 O(logn)，即为递归过程中栈的最大深度，也就是需要的空间。
     *
     * @param head
     * @return
     */
    public TreeNode sortedListToBST1(ListNode head) {
        return buildTree(head, null);
    }
    public TreeNode buildTree(ListNode left, ListNode right) {
        if(left == right) return null;
        ListNode mid = getMedian(left, right);
        TreeNode root = new TreeNode(mid.val);
        root.left = buildTree(left, mid);
        root.right = buildTree(mid.next, right);
        return root;
    }
    public ListNode getMedian(ListNode left, ListNode right) {
        ListNode fast = left;
        ListNode slow = left;
        while(fast != right && fast.next != right) {
            fast = fast.next;
            fast = fast.next;
            slow = slow.next;
        }
        return slow;
    }


    /**
     * 分治法+中序遍历
     * 方法一的时间复杂度的瓶颈在于寻找中位数节点。由于构造出的二叉搜索树的中序遍历结果就是链表本身，
     * 因此我们可以将分治和中序遍历结合起来，减少时间复杂度。
     * 具体地，设当前链表的左端点编号为 left，右端点编号为 right，包含关系为「双闭」，即 left 和 right 均包含在链表中。
     * 链表节点的编号为 [0, n)。中序遍历的顺序是「左子树 - 根节点 - 右子树」，
     * 那么在分治的过程中，我们不用急着找出链表的中位数节点，而是使用一个占位节点，等到中序遍历到该节点时，再填充它的值。
     * 如果 left>right，那么遍历到的位置对应着一个空节点，否则对应着二叉搜索树中的一个节点。
     * 这样一来，我们其实已经知道了这棵二叉搜索树的结构，并且题目给定了它的中序遍历结果，
     * 那么我们只要对其进行中序遍历，就可以还原出整棵二叉搜索树了。
     *
     * 时间复杂度：O(n)，其中 n是链表的长度。
     * 设长度为 n 的链表构造二叉搜索树的时间为 T(n)，递推式为 T(n)=2⋅T(n/2)+O(1)，根据主定理，T(n)=O(n)。
     * 空间复杂度：O(logn)，这里只计算除了返回答案之外的空间。平衡二叉树的高度为 O(logn)，即为递归过程中栈的最大深度，也就是需要的空间。
     */
    ListNode globalHead;
    public TreeNode sortedListToBST2(ListNode head) {
        globalHead = head;
        int length = getLength(head);
        return buildTree2(0, length - 1);
    }

    public int getLength(ListNode head) {
        int ret = 0;
        while(head != null) {
            ++ret;
            head = head.next;
        }
        return ret;
    }
    public TreeNode buildTree2(int left, int right) {
        if(left > right) return null;
        int mid = (left + right + 1) / 2;
        TreeNode root = new TreeNode();
        root.left = buildTree2(left, mid - 1);
        root.val = globalHead.val;
        globalHead = globalHead.next;
        root.right = buildTree2(mid + 1, right);
        return root;
    }


    public TreeNode sortedListToBST3(ListNode head) {
        //边界条件判断
        if(head == null) return null;
        if(head.next == null) return new TreeNode(head.val);

        //通过快慢指针找到链表的中间节点slow，pre为中间节点的前一个节点
        ListNode slow = head, fast = head, pre = null;
        while(fast != null && fast.next != null) {
            pre = slow;
            slow = slow.next;
            fast = fast.next.next;
        }
        //链表断开为两部分，一部分为node左子节点，另一部分为node右子节点
        pre.next = null;
        pre.next = null;
        TreeNode node = new TreeNode(slow.val);
        node.left = sortedListToBST3(head);
        node.right = sortedListToBST3(slow.next);
        return node;
    }

    public TreeNode sortedListTOBST4(ListNode head) {
        List<Integer> list = new ArrayList<>();
        while(head != null) {
            list.add(head.val);
            head = head.next;
        }
        return sortedListToBSTHelper(list, 0, list.size() - 1);
    }

    TreeNode sortedListToBSTHelper(List<Integer> list, int left, int right) {
        if(left > right) return null;
        int mid = left + (right - left) / 2;
        TreeNode root = new TreeNode(list.get(mid));
        root.left = sortedListToBSTHelper(list, left, mid - 1);
        root.right = sortedListToBSTHelper(list, mid + 1, right);
        return root;
    }




    ListNode cur = null;

    public TreeNode sortedListToBST5(ListNode head) {
        cur = head;
        int end = 0;
        while(head != null) {
            end++;
            head = head.next;
        }

        return sortedArrayToBSTHelper5(0, end);
    }
    private TreeNode sortedArrayToBSTHelper5(int start, int end) {
        if(start == end) return null;
        int mid = (start + end) >>> 1;
        //遍历左子树并将根节点返回
        TreeNode left = sortedArrayToBSTHelper5(start, mid);
        //遍历当前根节点并进行赋值
        TreeNode root = new TreeNode(cur.val);
        root.left = left;
        cur = cur.next;//指针后移，进行下一次的赋值
        TreeNode right = sortedArrayToBSTHelper5(mid + 1, end);
        root.right = right;
        return root;
    }









    @Test
    public void test() {
        ListNode head = new ListNode(-10);
        ListNode node1 = new ListNode(-3);
        ListNode node2 = new ListNode(0);
        ListNode node3 = new ListNode(5);
        ListNode node4 = new ListNode(9);
        head.next = node1;
        node1.next = node2;
        node2.next = node3;
        node3.next = node4;
        sortedListToBST(head);
    }




}
