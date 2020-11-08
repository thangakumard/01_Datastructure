package myInterviews;
import org.testng.annotations.Test;

import algorithms.tree.BinaryTree;
import algorithms.tree.Node;

/********
 * 
 * @author THANGAKUMAR
 * Find the sortest distance between two given nodes in binary tree
 */
public class amazon04_shortestDistance {

	@Test
	public void Test(){
		
		int[] input = {1,2,3,4,5,6,7,8,9,10};
		
		BinaryTree tree = new BinaryTree();
		tree.root = buildBST(input, 0, input.length-1);
		int a = distanceBetweenNodes(tree.root,1,4);
		System.out.println(" distanceBetweenNodes :" +  a);
	}
	
	private Node buildBST(int[] input,int start, int end){
		if(end < start)
			return null;
		int mid = (start+ end)/2;
		Node root = new Node(input[mid]);
		root.left = buildBST(input, start, mid-1);
		root.right = buildBST(input, mid+1,end);
		return root;
	}
	
	private int distanceBetweenNodes(Node root, int x, int y){
		
		int xDistanceFromRoot =0, yDistanceFromRoot = 0, LCADistanceFromRoot = 0;
		xDistanceFromRoot = distanceFromRoot(root, x, 0);
		yDistanceFromRoot = distanceFromRoot(root, y, 0);
		int lca = getLCA(root, x, y);
		LCADistanceFromRoot = distanceFromRoot(root, lca, 0);
		int distance = xDistanceFromRoot + yDistanceFromRoot - (2 * LCADistanceFromRoot);
		return distance;
	}
	
	private int distanceFromRoot(Node root, int value, int distance){
		if(root == null)
			return -1;
		if(root.data == value)
			return distance;
		if(root.data > value){
			return distanceFromRoot(root.left, value, distance+1);
		}
		else if(root.data < value){
			return distanceFromRoot(root.right, value, distance+1);
		}
		return -1;
	}
	
	private int getLCA(Node root, int x, int y){
		if(root == null)
			return -1;
		
		if(root.data > Math.max(x, y)){
			return getLCA(root.left, x, y);
		}
		else if(root.data < Math.min(x, y)){
			return getLCA(root.right, x, y);
		}		
		return root.data;
	}
}
