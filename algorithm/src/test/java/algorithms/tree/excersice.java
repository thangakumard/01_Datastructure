package algorithms.tree;

import org.testng.annotations.Test;

public class excersice {

	@Test
	public void test()
	{
		/* creating a binary tree and entering 
    the TreeNodes 
    				10
    		20				30
    	4		5
		 */
		BinaryTree tree = new BinaryTree();
		tree.root = new TreeNode(10);
		tree.root.left = new TreeNode(20);
		tree.root.right = new TreeNode(30);
		tree.root.left.left = new TreeNode(4);
		tree.root.left.right = new TreeNode(5);
		
		
		int distance = distanceBetween2(tree.root, 4, 30);
		
		
		System.out.println("LCA is : " + distance);
	}

	private TreeNode getLCA(TreeNode root, int a, int b){
		  if(root == null)
		    return null;
		  if(root.data > Math.max(a,b)){
		    return getLCA(root.left, a, b);
		  }
		   if(root.data < Math.min(a,b)){
		    return getLCA(root.right, a, b);
		  }
		  return root;
		  
		}
		  
		private int distanceFromLCA(TreeNode lca, int x, int distance){
		    if(lca.data == x){
		      return distance;
		    }
		    if(lca.data > x){
		      return distanceFromLCA(lca.left, x , 1+distance);
		    }
		    if(lca.data < x){
		      return distanceFromLCA(lca.right, x , 1+distance);
		    }
		    return distance+1;
		  }
		// Returns minimum distance beween a and b. 
		// This function assumes that a and b exist 
		// in BST. 
		private int distanceBetween2(TreeNode root, int a, int b) 
		{ 
			if (root == null) 
				return 0; 

			//Get LCA of a and b
		  	TreeNode lca = getLCA(root,a,b);
		  
		    //Distance of a from LCA
			int x1 = distanceFromLCA(lca, a, 0);
		  
		    //Distance of b from LCA
			int y1 = distanceFromLCA(lca, b, 0);
		  
			return x1 + y1; 
		} 


	
}
