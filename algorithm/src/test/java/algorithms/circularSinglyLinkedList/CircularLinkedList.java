package algorithms.circularSinglyLinkedList;
import org.testng.annotations.*;

public class CircularLinkedList {
	
	private Node last;
	private int length;
	
	public CircularLinkedList(){
		last = null;
		length = 0;
	}
	
	public int length(){
		return length;
	}
	
	public boolean isEmpty(){
		return length == 0;
	}
	
	@Test
	public void test(){
		createCircularLinkedList();
		insertAtBinning(100);
		insertAtBinning(200);
		insertAtEnd(300);
		insertAtEnd(400);
		printCircularLinkedList();
	}
	
	public void createCircularLinkedList(){
		Node first = new Node(10);
		Node second = new Node(20);
		Node third = new Node(30);
		Node fourth = new Node(40);
		Node fifth = new Node(50);
		
		first.next = second;length++;
		second.next = third;length++;
		third.next = fourth;length++;
		fourth.next = fifth;length++;
		fifth.next = first;length++;
		
		last = fifth;	
	}
	
	public void insertAtBinning(int value){
		Node temp = new Node(value);
		if(last == null){
			last = temp;
		}else{
			temp.next = last.next;
		}
		last.next = temp;
		length++;
	}
	
	public void insertAtEnd(int value){
		Node temp = new Node(value);
		if(last == null){
			last = temp;
			last.next = last;
		}else{
			temp.next = last.next;	
			last.next = temp;
			last = temp;
		}
		length++;
	}
	
	public void printCircularLinkedList(){
		if(last == null){
			System.out.println("LIST IS EMPTY !");
		}
		else{
			Node first = last.next;
			while(first != last){
				System.out.print(first.value + "--> ");
				first = first.next;
			}
			System.out.print(first.value + "--> ...");
			System.out.println();
		}
	}
	
	

}
