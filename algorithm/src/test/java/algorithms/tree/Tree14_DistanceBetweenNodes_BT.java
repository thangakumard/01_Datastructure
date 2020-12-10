package algorithms.tree;

import org.testng.annotations.Test;

public class Tree14_DistanceBetweenNodes_BT {

	@Test
	public void test(){
		int[] input = {1,2,3,4,5,6,7,8,9,10};
		
		BinaryTree tree = new BinaryTree();
		tree.root = buildBST(input, 0, input.length-1);

		int a = distanceBetweenTreeNodes(tree.root,1,4);
		
		System.out.println(" distanceBetweenTreeNodes :" +  a);
	}
	
	/**
	 * Distance b/w TreeNodes = Distance(root, n1) + Distance (root + n2) - 2 * Distance(root , lca)	 
	 */
	int distanceBetweenTreeNodes(TreeNode root, int TreeNode1, int TreeNode2){	
		
		int lca = lca(root, TreeNode1, TreeNode2).data;		
		
		int a = distanceFromRoot(root, TreeNode1, 0);
		int b = distanceFromRoot(root, TreeNode2, 0);
		int c = (2 * distanceFromRoot(root,lca,0));		
		int distance = a + b - c; 			
		
		return distance;
	}
	
	int distanceFromRoot(TreeNode TreeNode,int n,int distance){		
		if(TreeNode.data == n)
			return distance;
		if(TreeNode.data > n){
			return distanceFromRoot(TreeNode.left, n, distance+1);
		}
		if(TreeNode.data < n){
			return distanceFromRoot(TreeNode.right, n, distance+1);
		}		
		return distance;
	}
	
	TreeNode lca(TreeNode TreeNode, int n1, int n2){		
		if(TreeNode == null)
			return null;
		
		if(TreeNode.data == n1 || TreeNode.data == n2) {
			return TreeNode;
		}
		
		TreeNode.left = lca(TreeNode.left, n1, n2);
		TreeNode.right = lca(TreeNode.right, n1, n2);
		
		if(TreeNode.left  != null && TreeNode.right != null) {
			return TreeNode;
		}
		return TreeNode.left != null ? TreeNode.left : TreeNode.right;
	}
	
	
TreeNode buildBST(int[] input, int left, int right){
		
		if(left > right)
			return null;
		
		int middle = (left + right)/2;
		
		TreeNode TreeNode = new TreeNode(input[middle]);
		
		TreeNode.left = buildBST(input, left, middle-1);
		TreeNode.right = buildBST(input, middle+1, right);
		
		return TreeNode;		
	}

void inOrderTraversal(TreeNode TreeNode){
	if(TreeNode == null)
		return;
	
	System.out.println(TreeNode.data);
	inOrderTraversal(TreeNode.left);
	inOrderTraversal(TreeNode.right);
}

}
