package algorithms.tree;

import org.testng.annotations.Test;

public class Tree14_DistanceBetweenNodes_BST {

	@Test
	public void test(){
		int[] input = {1,2,3,4,5,6,7,8,9,10};
		
		BinaryTree tree = new BinaryTree();
		tree.root = buildBST(input, 0, input.length-1);

		int a = distanceBetweenNodes(tree.root,1,4);
		
		System.out.println(" distanceBetweenNodes :" +  a);
	}
	
	private Node buildBST(int[] input,int i, int j) {
		if(i > j)
			return null;
		int middle = (i+j)/2;
		Node root = new Node(middle);
		root.left = buildBST(input, i, middle-1);
		root.right = buildBST(input, middle+1, j);
		
		return root;
		
	}
	
	private int distanceBetweenNodes(Node root, int n1, int n2) {
		
		//Get LCA
		Node lca = getLCA(root, n1, n2);
		System.out.println("LCA :" + lca.data);
		
		//Distance of n1 from LCA
		int distance_n1 = distancefromLCA(lca, n1, 0);
		System.out.println("distance_n1 :" + distance_n1);
		
		//Distance of n2 from LCA
		int distance_n2 = distancefromLCA(lca, n2, 0);
		System.out.println("distance_n2 :" + distance_n2);
		
		//Distance = Distance of n1 from LCA + Distance of n2 from LCA
		int distance = distance_n1 + distance_n2;
		
		return distance;
	}
	
	private Node getLCA(Node root, int n1,int n2) {
		if(root == null)
			return null;
		
		if(root.data > Math.max(n1, n2)) {
			return getLCA(root.left, n1, n2);
		}
		
		if(root.data < Math.min(n1, n2)) {
			return getLCA(root.right, n1, n2);
		}
		return root;
	}
	
	private int distancefromLCA(Node lca, int n1, int distance) {
		if(lca == null) return distance;
		if(lca.data == n1 )
			return distance;
		if(lca.data > n1) {
			return distancefromLCA(lca.left, n1, distance+1);
		}
		if(lca.data < n1) {
			return distancefromLCA(lca.right, n1, distance+1);
		}
		return distance+1;
	}
}
