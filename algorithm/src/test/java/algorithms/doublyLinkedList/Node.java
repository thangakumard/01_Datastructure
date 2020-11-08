package algorithms.doublyLinkedList;

public class Node {

	
	public int data;
	public Node next;
	public Node previous;
	
	public Node(int data){
		this.data = data;
	}
	public Node(int data, Node previous, Node next){
		this.data = data;
		this.next = next;
		this.previous = previous;
	}
}
