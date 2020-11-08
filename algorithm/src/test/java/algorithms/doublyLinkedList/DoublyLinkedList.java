package algorithms.doublyLinkedList;

public class DoublyLinkedList {	
	
	public Node head = null;
	public Node tail = null;
	public int length = 0;
	
	public DoublyLinkedList(){
		this.head = null;
		this.tail = null;
		this.length = 0;
	}
	
	public boolean isEmpty(){
		return length == 0;
	}
	
	public int getLength(){
		return length;				
	}
	
}
