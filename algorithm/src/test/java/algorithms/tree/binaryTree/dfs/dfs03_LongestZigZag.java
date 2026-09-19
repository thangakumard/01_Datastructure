package algorithms.tree.binaryTree.dfs;

import algorithms.tree.TreeNode;

public class dfs03_LongestZigZag {
    int pathLength = 0;

    private void dfs(TreeNode node, boolean goLeft, int step) {
        if (node == null)
            return;
        pathLength = Math.max(pathLength, step);

        if (goLeft) {
            dfs(node.left, false, step + 1);
            dfs(node.right, true, 1);
        } else {
            dfs(node.left, false, 1);
            dfs(node.right, true, step + 1);
        }
    }

    public int longestZigZag(TreeNode root) {
        dfs(root, true, 0);
        return pathLength;
    }
}
