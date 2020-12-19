package algorithms.tree;

public class Tree34_longestUnivaluePath {
	
	int ans = 0;
	
	public int longestUniversalValuePath(TreeNode root) {
		ans = 0;
		arrowLength(root);
		return ans;
		
	}

	public int arrowLength(TreeNode node) {
		if(node == null) return 0;
		
		int left = arrowLength(node.left);
		int right = arrowLength(node.right);
		
		int leftArrow =0 , rightArrow = 0;

		if(node.left != null && node.left.data == node.data) {
			leftArrow += left + 1;
		}
		if(node.right != null && node.right.data == node.data) {
			rightArrow += right + 1;
		}
		
		ans = Math.max(ans, leftArrow + rightArrow);
		
		return Math.max(leftArrow, rightArrow);
	}
}
