import java.util.*;

public class BinaryTreeLCA {
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
    
   public static TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        // 1. Base Case: If we find p, q, or null, we return it.
        if (root == null || root == p || root == q) {
            return root;
        }

        // 2. Recurse: Search in the left and right subtrees.
        TreeNode left = lowestCommonAncestor(root.left, p, q);
        TreeNode right = lowestCommonAncestor(root.right, p, q);

        // 3. Check Results:
        // If both are non-null, this root is the LCA.
        if (left != null && right != null) {
            return root;
        }

        // Otherwise, the LCA is whichever side was not null.
        // Your line `return left == null ? right : left;` is a very clean
        // way to write this!
        return left != null ? left : right;
    }

    public static void main(String[] args) {
        TreeNode root = new TreeNode(3, new TreeNode(5), new TreeNode(1));
        TreeNode left = root.left;
        TreeNode right = root.right;
        left.left = new TreeNode(6);
        left.right = new TreeNode(2, new TreeNode(7), new TreeNode(4));
        right.left = new TreeNode(0, new TreeNode(8), null);

        TreeNode lcaNode = lowestCommonAncestor(root, root.left, root.right);
        System.out.println(lcaNode.val);
    }
}