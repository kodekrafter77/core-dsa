import java.util.*;

public class DiameterOfBinaryTree {

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
    

    public static int diameterOfBinaryTree(TreeNode root) {
      int[] result = getDiameterInfo(root);
      return result[1];
    }

    private static int[] getDiameterInfo(TreeNode node) {
      if (node == null) return new int[] {0, 0};
      int[] leftInfo = getDiameterInfo(node.left);
      int[] rightInfo = getDiameterInfo(node.right);

      int leftHeight = leftInfo[0];
      int rightHeight = rightInfo[0];
      int maxDiameterFromChildren = Math.max(leftInfo[1], rightInfo[1]);

      int currentHeight = Math.max(leftHeight, rightHeight) + 1;
      int diameterThruNode = leftHeight + rightHeight;
      int overAllMaxDiameter = Math.max(maxDiameterFromChildren, diameterThruNode);

      return new int[] {currentHeight, overAllMaxDiameter};
    } 

    public static void main(String[] args) {
      TreeNode root = new TreeNode(1, new TreeNode(2, new TreeNode(4), new TreeNode(5)), new TreeNode(3));
      System.out.println(diameterOfBinaryTree(root));
    }
}