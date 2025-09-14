public class SplitBST {
    static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode() {}
        TreeNode(int val) { this.val = val; }
        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }

        public String toString() {
            return "" + this.val;
        }
    }

    public TreeNode[] splitBST(TreeNode root, int target) {
        // Base Case: An empty tree splits into two empty trees.
        if (root == null) {
            return new TreeNode[]{null, null};
        }

        // Case 1: The root's value is greater than the target.
        if (root.val > target) {
            // The root and its entire right subtree belong in the "greater" part.
            // We need to split the left subtree to find out what might also belong
            // in the "greater" part and what belongs in the "smaller" part.
            TreeNode[] splitFromLeft = splitBST(root.left, target);
            
            // The "smaller" part from the left split is our final "smaller" tree.
            TreeNode smallerEqualTree = splitFromLeft[0];
            
            // The "greater" part from the left split becomes the new left child of our root.
            TreeNode greaterTree = root;
            greaterTree.left = splitFromLeft[1];
            
            return new TreeNode[]{smallerEqualTree, greaterTree};
        } 
        // Case 2: The root's value is less than or equal to the target.
        else {
            // The root and its entire left subtree belong in the "smaller" part.
            // We need to split the right subtree.
            TreeNode[] splitFromRight = splitBST(root.right, target);
            
            // The "smaller" part from the right split becomes the new right child of our root.
            TreeNode smallerEqualTree = root;
            smallerEqualTree.right = splitFromRight[0];
            
            // The "greater" part from the right split is our final "greater" tree.
            TreeNode greaterTree = splitFromRight[1];
            
            return new TreeNode[]{smallerEqualTree, greaterTree};
        }
    }

    public static void main(String[] args) {
        SplitBST splitBst = new SplitBST();
        TreeNode root = new TreeNode(4, new TreeNode(2, new TreeNode(1), new TreeNode(3)), new TreeNode(6, new TreeNode(5), new TreeNode(7)));
        TreeNode[] splits = splitBst.splitBST(root, 2);
    }
}