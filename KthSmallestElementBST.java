import java.util.*;

public class KthSmallestElementBST {

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

    static int count;
    static int res;
    
    public static int kthSmallest(TreeNode root, int k) {
        count = 0;
        res = -1;
        inorderTraverse(root, k);
        return res;
    }

    private static void inorderTraverse(TreeNode node, int k) {
        if (node == null) return;
        inorderTraverse(node.left, k);
        ++count;
        if (count == k) {
            res = node.val;
        }
        inorderTraverse(node.right, k);
    }

    public static TreeNode buildTree(Integer[] levelOrder) {
        if (levelOrder == null || levelOrder.length == 0 || levelOrder[0] == null) {
            return null;
        }

        TreeNode root = new TreeNode(levelOrder[0]);
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        int i = 1;

        while (!queue.isEmpty() && i < levelOrder.length) {
            TreeNode current = queue.poll();

            // Process left child
            if (levelOrder[i] != null) {
                current.left = new TreeNode(levelOrder[i]);
                queue.offer(current.left);
            }
            i++;

            // Process right child
            if (i < levelOrder.length && levelOrder[i] != null) {
                current.right = new TreeNode(levelOrder[i]);
                queue.offer(current.right);
            }
            i++;
        }

        return root;
    }

    // Example of how to use it
    public static void main(String[] args) {
        Integer[] treeValues = {5, 3, 6, 2, 4, null, null, 1};
        TreeNode root = buildTree(treeValues);
        
        System.out.println(kthSmallest(root, 3)); // 3
    }
}