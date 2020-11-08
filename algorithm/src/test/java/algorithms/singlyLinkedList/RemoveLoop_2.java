package algorithms.singlyLinkedList;

import org.testng.Assert;
import org.testng.annotations.Test;

/*Method 2 (Better Solution)
This method is also dependent on Floyd�s Cycle detection algorithm.
1) Detect Loop using Floyd�s Cycle detection algo and get the pointer to a loop node.
2) Count the number of nodes in loop. Let the count be k.
3) Fix one pointer to the head and another to kth node from head.
4) Move both pointers at the same pace, they will meet at loop starting node.
5) Get pointer to the last node of loop and make next of it as NULL.
http://www.geeksforgeeks.org/detect-and-remove-loop-in-a-linked-list/
*/

public class RemoveLoop_2 {
	
	@Test
	public void testRemoveLoop2(){
		
		SinglyLinkedList list = new SinglyLinkedList();
		list.push(new Node(1));
		list.push(new Node(2));
		list.push(new Node(3));
		list.push(new Node(4));
		list.push(new Node(5));
		list.push(new Node(6));
		
		list.head.next.next.next.next.next.next = list.head.next;	
		
		findLoop(list);
		
		Node currentNode = list.head;
		while(currentNode != null)
		{
			System.out.println(currentNode.value);
			currentNode = currentNode.next;
		}
		
		Assert.assertTrue(true);
	}
	
	
	 // Function that detects loop in the list
	public boolean findLoop(SinglyLinkedList list){
		
		Node slow = list.head.next;
		Node fast = list.head.next.next;		
		
        // If slow and fast meet at same point then loop is present
		while(slow != null && fast !=null)
		{
			if(slow == fast){
				removeLoop(slow, list.head);
				return true;
			}
			slow = slow.next;
			fast = fast.next.next;
		}		
		return false;
	}
	
    // Function to remove loop
	public void removeLoop(Node point, Node head){
		Node ptr1 = point;
		Node ptr2 = point;
		
        // Count the number of nodes in loop
		int k =1;
		while(ptr1.next != ptr2){			
			ptr1 = ptr1.next;	
			k++;
		}
		
        // Fix one pointer to head
		ptr1 = head;
		
        // And the other pointer to k nodes after head
		ptr2 = head;
		for(int i=0; i < k; i++){
			ptr2 = ptr2.next;
		}
		

        /*  Move both pointers at the same pace,
         they will meet at loop starting node */		
		while(ptr1 != ptr2){
			ptr1 = ptr1.next;
			ptr2 = ptr2.next;
		}
		
		 // Get pointer to the last node
        ptr2 = ptr2.next;
        while (ptr2.next != ptr1) {
            ptr2 = ptr2.next;
        }
		
		/* Set the next node of the loop ending node
        to fix the loop */
		ptr2.next = null;	
		
	}

}
