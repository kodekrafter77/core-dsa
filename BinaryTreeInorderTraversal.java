import java.util.*;

public class BinaryTreeInorderTraversal {

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

    public static List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        // dfs(root, res);
        Deque<TreeNode> stack = new ArrayDeque<>();
        TreeNode node = root;

        while (node != null || !stack.isEmpty()) {
            while (node != null) {
                stack.push(node);
                node = node.left;
            }
            node = stack.pop();
            res.add(node.val);
            node = node.right;
        }
        return res;
    }

    private static void dfs(TreeNode root, List<Integer> res) {
        if (root != null) {
            dfs(root.left, res);
            res.add(root.val);
            dfs(root.right, res);
        }
    }


    public static void main(String[] args) {
        System.out.println(inorderTraversal(new TreeNode(1, null, new TreeNode(2, new TreeNode(3), null)))); // [1, 3, 2]

        TreeNode root = new TreeNode(1, new TreeNode(2), new TreeNode(3));
        root.left.left = new TreeNode(4);
        root.left.right = new TreeNode(5, new TreeNode(6), new TreeNode(7));
        root.right.right = new TreeNode(8, new TreeNode(9), null);
        System.out.println(inorderTraversal(root)); // [4,2,6,5,7,1,3,9,8]

    }
}