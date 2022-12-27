package serialize_And_Deserialize_BST_449;


import java.util.Deque;
import java.util.List;

public class Codec {

    // Encodes a tree to a single string.
    public String serialize(TreeNode root) {

    }




    // Decodes your encoded data to tree.
    public TreeNode deserialize(String data) {



    }


    private void postOrder(TreeNode root, List<Integer> list) {
        if(root == null) return;
        postOrder(root.left, list);
        postOrder(root.right, list);
        list.add(root.val);
    }

    private TreeNode construct(int lower, int upper, Deque<Integer> stack) {
        if(stack.isEmpty() || stack.peek() < lower || stack.peek() > upper) return null;
        int val = stack.pop();
        TreeNode root = new TreeNode(val);
        root.right = construct(val, upper, stack);
        root.left = construct(lower, val, stack);
        return root;
    }











}

// Your Codec object will be instantiated and called as such:
// Codec ser = new Codec();
// Codec deser = new Codec();
// String tree = ser.serialize(root);
// TreeNode ans = deser.deserialize(tree);
// return ans;

