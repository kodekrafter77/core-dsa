import java.util.*;

public class BinaryTreePostorderTraversal {

    static class TreeNode {
        int val;
        TreeNode left, right;
    
        public TreeNode(int val) {
            this(val, null, null);
        }
    
        public TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    
        @Override
        public String toString() {
            return this.val + "";
        }
    }
    

    public static List<Integer> postorderTraversal(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        if (root == null) return res;
        Deque<TreeNode> stack1 = new ArrayDeque<>();
        Deque<TreeNode> stack2 = new ArrayDeque<>();

        stack1.push(root);

        while (!stack1.isEmpty()) {
            TreeNode node = stack1.pop();
            stack2.push(node);

            if (node.left != null) stack1.push(node.left);
            if (node.right != null) stack1.push(node.right);
        }

        while (!stack2.isEmpty()) res.add(stack2.pop().val);

        return res;
    }

    public static void main(String[] args) {
        System.out.println(postorderTraversal(new TreeNode(1, null, new TreeNode(2, new TreeNode(3), null)))); // [3, 2, 1]

        TreeNode root = new TreeNode(1, new TreeNode(2), new TreeNode(3));
        root.left.left = new TreeNode(4);
        root.left.right = new TreeNode(5, new TreeNode(6), new TreeNode(7));
        root.right.right = new TreeNode(8, new TreeNode(9), null);
        System.out.println(postorderTraversal(root)); // [4,6,7,5,2,9,8,3,1]

    }
}