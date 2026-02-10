import java.util.ArrayList;
import java.util.List;

class Solution {
    public TreeNode balanceBST(TreeNode root) {
        List<Integer> inorder = new ArrayList<>();
        inorderTraversal(root, inorder);
        return buildBalancedBST(inorder, 0, inorder.size() - 1);
    }

    private void inorderTraversal(TreeNode root, List<Integer> inorder) {
        if (root == null)
            return;
        inorderTraversal(root.left, inorder);
        inorder.add(root.val);
        inorderTraversal(root.right, inorder);
    }

    private TreeNode buildBalancedBST(List<Integer> arr, int left, int right) {
        if (left > right)
            return null;

        int mid = left + (right - left) / 2;
        TreeNode root = new TreeNode(arr.get(mid));
        root.left = buildBalancedBST(arr, left, mid - 1);
        root.right = buildBalancedBST(arr, mid + 1, right);
        return root;
    }
}
