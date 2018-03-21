package singlyLinkedList;

import org.testng.annotations.Test;

public class LList04_ReverseLinkedList {
	
	@Test
	public void ReverseTheList(){
	
		SinglyLinkedList list = new SinglyLinkedList();
		list.push(new Node(10));
		list.push(new Node(9));
		list.push(new Node(8));
		list.push(new Node(7));
		list.push(new Node(6));
		list.push(new Node(5));
		
	    
        System.out.println("Given Linked list");
        printList(list.head);
        list.head = reverse(list.head);
        System.out.println(" ");
        System.out.println("Reversed linked list ");
        printList(list.head);		
	}
	
	public Node reverse(Node node){
		Node prev = null;
		Node next = null;
		Node CurrentNode = node;
		
		while(CurrentNode != null){			
			next = CurrentNode.next;
			CurrentNode.next = prev;
			prev = CurrentNode;
			CurrentNode = next;			
		}		
		node = prev;	
		
        printList(node);

		return node;
	}
	
	 // prints content of double linked list
    void printList(Node node) {
        while (node != null) {
            System.out.print(node.value + " ");
            node = node.next;
        }
    }

}
