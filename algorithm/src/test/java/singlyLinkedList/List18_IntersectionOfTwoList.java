package singlyLinkedList;
import org.testng.annotations.Test;

public class List18_IntersectionOfTwoList {

	
	@Test
	public void Test(){
		
		SinglyLinkedList list1 = new SinglyLinkedList();
		list1.push(new Node(1));
		list1.push(new Node(2));
		list1.push(new Node(3));
		list1.push(new Node(4));
		list1.push(new Node(6));
		list1.push(new Node(9));
		list1.push(new Node(10));
		
		SinglyLinkedList list2 = new SinglyLinkedList();
		list2.push(new Node(10));
		list2.push(new Node(20));

		list2.head.next.next = list1.head.next.next.next; 
		
		System.out.println("INTERSECTION IS : " + getIntersection(list1.head, list2.head).value);
	}
	
	private Node getIntersection(Node nodeA, Node nodeB){
		
		if(nodeA == null || nodeB == null)
			return null;
		int x = getLength(nodeA);
		int y = getLength(nodeB);
		
		int z = Math.abs(x - y);
		
		while(z > 0){
			if(x > y){
				nodeA = nodeA.next;
			}
			else{
				nodeB = nodeB.next;
			}
			z--;
		}
		
		while(nodeA != null && nodeB != null && nodeA != nodeB){
			nodeA = nodeA.next;
			nodeB = nodeB.next;
		}
		
		return nodeA;
	}
	
	private int getLength(Node head){
		int n =0;
		while(head != null){
			head = head.next;
			n++;
		}
		return n;
	}
	
}
