package tree;

public class Node {

	public int data, height;
	public Node left, right;
	
	public Node(int item){
		data = item;
		left = right = null;
	}
}
