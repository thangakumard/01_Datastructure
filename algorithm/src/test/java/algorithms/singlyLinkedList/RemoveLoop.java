package algorithms.singlyLinkedList;
import org.testng.Assert;
import org.testng.annotations.Test;

/*Detect and Remove Loop in a Linked List
Write a function detectAndRemoveLoop() that checks whether a given Linked List contains loop and if loop is present then removes the loop and returns true. 
And if the list doesn�t contain loop then returns false. Below diagram shows a linked list with a loop. 
detectAndRemoveLoop() must change the below list to 1->2->3->4->5->NULL.

http://www.geeksforgeeks.org/detect-and-remove-loop-in-a-linked-list/
*/

/******************Floyd�s Cycle detection algorithm ********************/

public class RemoveLoop {
	
	@Test
	public void testRemoveLoop(){
		
		SinglyLinkedList list = new SinglyLinkedList();
		list.push(new ListNode(1));
		list.push(new ListNode(2));
		list.push(new ListNode(3));
		list.push(new ListNode(4));
		list.push(new ListNode(5));
		list.push(new ListNode(6));
		
		list.head.next.next.next.next.next.next = list.head.next;	
		
		findAndDeleteLoop(list.head);
		ListNode currentNode = list.head;
		while(currentNode != null)
		{
			System.out.println(currentNode.value);
			currentNode = currentNode.next;
		}
		
		Assert.assertTrue(true);
	}
	
	
	public int findAndDeleteLoop(ListNode node){
		
		ListNode slow = node; ListNode fast = node;
		
		while(slow != null && fast != null && fast.next != null){			
			slow = slow.next;
			fast = fast.next.next;		
			
			if(slow == fast){
				removeLoop(slow , node);
				return 1;
			}
		}		
		return 0;			
	}	
	
	public void removeLoop(ListNode loop, ListNode head){
		
		ListNode ptr1 = null, ptr2 = null;
		
		ptr1 = head;
		
		while(true){
			
			ptr2 = loop;
			while(ptr2.next != loop && ptr2.next != ptr1){
				ptr2 = ptr2.next;
			}
			
			if(ptr2.next == ptr1){
				break;
			}
			
			ptr1 = ptr1.next;
		
		}
		
		ptr2.next = null;
	}	
	
	

}
