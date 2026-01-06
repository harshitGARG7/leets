import java.util.*;

class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    TreeNode() {
    }

    TreeNode(int val) {
        this.val = val;
    }

    TreeNode(int val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }
}

class Solution {
    public int maxLevelSum(TreeNode root) {
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);

        int maxSum = Integer.MIN_VALUE;
        int level = 0;
        int answerLevel = 0;

        while (!queue.isEmpty()) {
            int size = queue.size();
            int currentSum = 0;
            level++;

            for (int i = 0; i < size; i++) {
                TreeNode node = queue.poll();
                currentSum += node.val;

                if (node.left != null)
                    queue.offer(node.left);
                if (node.right != null)
                    queue.offer(node.right);
            }

            if (currentSum > maxSum) {
                maxSum = currentSum;
                answerLevel = level;
            }
        }

        return answerLevel;
    }
}
