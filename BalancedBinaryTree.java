import java.util.*;

public class BalancedBinaryTree {

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

    public static boolean isBalanced(TreeNode root) {
        return checkBalance(root) != -1;
    }

    private static int checkBalance(TreeNode root) {
        if (root == null) return 0;
        
        int leftHeight = checkBalance(root.left);
        if (leftHeight == -1) return -1; // Early termination
        
        int rightHeight = checkBalance(root.right);
        if (rightHeight == -1) return -1; // Early termination
        
        if (Math.abs(leftHeight - rightHeight) > 1) {
            return -1; // Unbalanced
        }
        
        return Math.max(leftHeight, rightHeight) + 1; // Correct height calculation
    }


    public static void main(String[] args) {
        TreeNode root = new TreeNode(3, new TreeNode(9), new TreeNode(20, new TreeNode(15), new TreeNode(7)));
        System.out.println(isBalanced(root)); // true
    }
}