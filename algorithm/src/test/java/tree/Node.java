package tree;

public class Node {

	int data, height;
	Node left, right;
	
	public Node(int item){
		data = item;
		left = right = null;
	}
}
