package algorithms.singlyLinkedList;
import org.testng.annotations.Test;

public class List18_IntersectionOfTwoList {

	
	@Test
	public void Test(){
		
		SinglyLinkedList list1 = new SinglyLinkedList();
		list1.push(new ListNode(1));
		list1.push(new ListNode(2));
		list1.push(new ListNode(3));
		list1.push(new ListNode(4));
		list1.push(new ListNode(6));
		list1.push(new ListNode(9));
		list1.push(new ListNode(10));
		
		SinglyLinkedList list2 = new SinglyLinkedList();
		list2.push(new ListNode(10));
		list2.push(new ListNode(20));

		list2.head.next.next = list1.head.next.next.next; 
		
		System.out.println("INTERSECTION IS : " + getIntersection(list1.head, list2.head).value);
	}
	
	private ListNode getIntersection(ListNode nodeA, ListNode nodeB){
		
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
	
	private int getLength(ListNode head){
		int n =0;
		while(head != null){
			head = head.next;
			n++;
		}
		return n;
	}
	
}
