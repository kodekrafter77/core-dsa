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
        if (root == null) return new TreeNode[] {null, null};
        if (root.val > target) {
            TreeNode[] splitFromLeft = splitBST(root.left, target);
            root.left = splitFromLeft[1];
            return new TreeNode[] {splitFromLeft[0], root};
        } else {
            TreeNode[] splitFromRight = splitBST(root.right, target);
            root.right = splitFromRight[0];
            return new TreeNode[] {root, splitFromRight[1]};
        }
    }

    public static void main(String[] args) {
        SplitBST splitBst = new SplitBST();
        TreeNode root = new TreeNode(4, new TreeNode(2, new TreeNode(1), new TreeNode(3)), new TreeNode(6, new TreeNode(5), new TreeNode(7)));
        TreeNode[] splits = splitBst.splitBST(root, 2);
    }
}