import java.util.*;

public class BinaryTreePathSumII {

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
    

    public static List<List<Integer>> pathSum(TreeNode root, int targetSum) {
        List<List<Integer>> res = new ArrayList<>();
        dfs(root, targetSum, new ArrayList<>(), res);
        return res;
    }

    private static void dfs(TreeNode node, int target, List<Integer> path, List<List<Integer>> res) {
        if (node == null) return;
        path.add(node.val);
        if (node.left == null && node.right == null && target - node.val == 0) {
            res.add(new ArrayList<>(path));
        } else  {
            dfs(node.left, target - node.val, path, res);
            dfs(node.right, target - node.val, path, res);
        }
        path.remove(path.size() - 1);
    }

    public static void main(String[] args) {
        TreeNode root = new TreeNode(5, new TreeNode(4), new TreeNode(8));
        TreeNode left = root.left;
        TreeNode right = root.right;
        left.left = new TreeNode(11, new TreeNode(7), new TreeNode(2));
        right.left = new TreeNode(13);
        right.right = new TreeNode(4, new TreeNode(5), new TreeNode(1));
        System.out.println(pathSum(root, 22)); // [[5,4,11,2],[5,8,4,5]]
    }
}