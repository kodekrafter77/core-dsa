import java.util.*;

public class BinaryTreeLevelOrderTraversal {

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
    
    public static List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> res = new ArrayList<>();

        if (root == null) return res;

        Deque<TreeNode> queue = new ArrayDeque<>();

        queue.offer(root);

        while (!queue.isEmpty()) {
            int size = queue.size();
            List<Integer> currLevelItems = new ArrayList<>();
            for (int level = 0; level < size; ++level) {
                TreeNode node = queue.poll();
                if (node != null) {
                    currLevelItems.add(node.val);
                    if (node.left != null) queue.offer(node.left);
                    if (node.right != null) queue.offer(node.right);
                }
            }
            res.add(currLevelItems);
        }

        return res;
    }

    public static void main(String[] args) {
        TreeNode root = new TreeNode(3, new TreeNode(9), new TreeNode(20, new TreeNode(15), new TreeNode(7)));
        System.out.println(levelOrder(root)); // [[3],[9,20],[15,7]]
    }
}