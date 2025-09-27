import java.util.*;

public class BinaryTreePathSum {

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
    

    public static boolean hasPathSum(TreeNode root, int targetSum) {
        return dfs(root, targetSum);
    }

    private static boolean dfs(TreeNode node, int target) {
        if (node == null) return false;
        if (node.left == null && node.right == null) return target - node.val == 0;
        return dfs(node.left, target - node.val) || dfs(node.right, target - node.val);
    }

   

    public static void main(String[] args) {
        TreeNode root = new TreeNode(5, new TreeNode(4), new TreeNode(8));
        TreeNode left = root.left;
        TreeNode right = root.right;
        left.left = new TreeNode(11, new TreeNode(7), new TreeNode(2));
        right.left = new TreeNode(13);
        right.right = new TreeNode(4, null, new TreeNode(1));

        System.out.println(hasPathSum(root, 22)); // true
    }
}