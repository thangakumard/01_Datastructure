package algorithms.tree.BinarySearchTree.DivideAndConquer;

import algorithms.tree.TreeNode;

public class DAC01_SortedArrayToBST {
    public TreeNode sortedArrayToBST(int[] nums) {
        return buildBST(nums, 0, nums.length-1);
    }

    private TreeNode buildBST(int[] input, int left, int right){
        if(left > right)
            return null;
        int middle = left + (right-left)/2;
        TreeNode treeNode = new TreeNode(input[middle]);

        treeNode.left = buildBST(input, left, middle-1);
        treeNode.right = buildBST(input, middle+1, right);

        return treeNode;
    }
}
