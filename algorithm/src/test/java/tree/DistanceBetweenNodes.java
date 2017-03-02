package tree;

import org.testng.annotations.Test;

public class DistanceBetweenNodes {

	@Test
	public void test(){
		int[] input = {1,2,3,4,5,6,7,8,9,10};
		
		BinaryTree tree = new BinaryTree();
		tree.root = buildBST(input, 0, input.length-1);

		int a = distanceBetweenNodes(tree.root,1,4);
		
		System.out.println(" distanceBetweenNodes :" +  a);
	}
	
	/**
	 * Distance b/w nodes = Distance(root, n1) + Distance (root + n2) - 2 * Distance(root , lca)	 
	 */
	int distanceBetweenNodes(Node root, int node1, int node2){	
		
		int lca = lca(root, node1, node2).data;		
		
		int a = distanceFromRoot(root, node1, 0);
		int b = distanceFromRoot(root, node2, 0);
		int c = (2 * distanceFromRoot(root,lca,0));		
		int distance = a + b - c; 			
		
		return distance;
	}
	
	int distanceFromRoot(Node node,int n,int distance){		
		if(node.data == n)
			return distance;
		if(node.data > n){
			return distanceFromRoot(node.left, n, distance+1);
		}
		if(node.data < n){
			return distanceFromRoot(node.right, n, distance+1);
		}		
		return distance;
	}
	
	Node lca(Node node, int n1, int n2){		
		if(node == null)
			return null;
		
		if(node.data > n1 && node.data > n2)
			return lca(node.left, n1, n2);
		
		if(node.data < n1 && node.data < n2)
			return lca(node.right, n1, n2);
		
		return node;
	}
	
	
Node buildBST(int[] input, int left, int right){
		
		if(left > right)
			return null;
		
		int middle = (left + right)/2;
		
		Node node = new Node(input[middle]);
		
		node.left = buildBST(input, left, middle-1);
		node.right = buildBST(input, middle+1, right);
		
		return node;		
	}

void inOrderTraversal(Node node){
	if(node == null)
		return;
	
	System.out.println(node.data);
	inOrderTraversal(node.left);
	inOrderTraversal(node.right);
}

}
