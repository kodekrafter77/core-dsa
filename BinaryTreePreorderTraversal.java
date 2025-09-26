import java.util.*;

public class BinaryTreePreorderTraversal {

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


    public static List<Integer> preorderTraversal(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        if (root == null) {
            return res;
        }

        Deque<TreeNode> stack = new ArrayDeque<>();
        stack.push(root);

        while (!stack.isEmpty()) {
            TreeNode currentNode = stack.pop();
            // 1. Process current node
            res.add(currentNode.val);

            // 2. Push right child first, so left is processed first
            if (currentNode.right != null) {
                stack.push(currentNode.right);
            }
            
            // 3. Push left child last
            if (currentNode.left != null) {
                stack.push(currentNode.left);
            }
        }
        return res;
    }

    private static void dfs(TreeNode node, List<Integer> res) {
        if (node != null) {
            res.add(node.val);
            dfs(node.left, res);
            dfs(node.right, res);
        }
    }

    public static void main(String[] args) {
        System.out.println(preorderTraversal(new TreeNode(1, null, new TreeNode(2, new TreeNode(3), null)))); // [1, 2, 3]

        TreeNode root = new TreeNode(1, new TreeNode(2), new TreeNode(3));
        root.left.left = new TreeNode(4);
        root.left.right = new TreeNode(5, new TreeNode(6), new TreeNode(7));
        root.right.right = new TreeNode(8, new TreeNode(9), null);
        System.out.println(preorderTraversal(root)); // [1,2,4,5,6,7,3,8,9]
    }
}