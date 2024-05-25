package algorithms.singlyLinkedList;
import algorithms.singlyLinkedList.base.ListNode;
import algorithms.singlyLinkedList.base.SinglyLinkedList;
import org.testng.Assert;
import org.testng.annotations.Test;

/*
 * Given a linked list and a value x, partition it such that all nodes less than x come first, then all nodes with value equal to x and finally nodes with value greater than or equal to x. The original relative order of the nodes in each of the three partitions should be preserved. The partition must work in-place.

Examples:

Input : 1->4->3->2->5->2->3, 
        x = 3
Output: 1->2->2->3->3->4->5

Input : 1->4->2->10 
        x = 3
Output: 1->2->4->10

Input : 10->4->20->10->3 
        x = 3
Output: 3->10->4->20->10 
 */
public class LList05_PartitioningLinkedList {

	@Test
	public void doPartition(){
		SinglyLinkedList list = new SinglyLinkedList();
		list.push(new ListNode(1));
		list.push(new ListNode(5));
		list.push(new ListNode(10));
		list.push(new ListNode(2));
		list.push(new ListNode(8));
		list.push(new ListNode(2));
		list.push(new ListNode(3));
		
		//SinglyLinkedList partitioned = partitionByX(list.head,3);
		
		ListNode partitioned1 = partition(list.head,5);
		
		ListNode currentNode = partitioned1;
		while(currentNode != null){
			System.out.println(currentNode.value);
			currentNode = currentNode.next;
		}
		Assert.assertTrue(true);
	}
	
	private ListNode partition(ListNode head, int x)  
	{  
	    /* Let us initialize start and tail nodes of  
	    new list */
	    ListNode tail = head;  
	  
	    // Now iterate original list and connect nodes  
	    ListNode curr = head;  
	    while (curr != null)  
	    {  
	        ListNode next = curr.next;  
	        if (curr.value < x)  
	        {  
	            /* Insert node at head. */
	            curr.next = head;  
	            head = curr;  
	        }  
	  
	        else // Append to the list of greater values  
	        {  
	            /* Insert node at tail. */
	            tail.next = curr;  
	            tail = curr;  
	        }  
	        curr = next;  
	    }  
	    tail.next = null;  
	  
	    // The head has changed, so we need  
	    // to return it to the user.  
	    return head;  
	}  
	
	public SinglyLinkedList partitionByX(ListNode head,int x){
	
		ListNode currentNode = head;
		SinglyLinkedList leftSide = new SinglyLinkedList();
		ListNode leftLastNode = null;
		SinglyLinkedList rightSide = new SinglyLinkedList();
		ListNode rightLastNode = null;
		SinglyLinkedList equalList = new SinglyLinkedList();
		ListNode equalListLastNode = null;
		String typeOfList = "";
		SinglyLinkedList resultList = new SinglyLinkedList();
		
		while(currentNode != null){			
			if(currentNode.value < x){
				leftLastNode = currentNode;
				leftSide.push(leftLastNode);				
			}				
			else if(currentNode.value > x){
				rightLastNode = currentNode;
				rightSide.push(rightLastNode);
				
			}				
			else{
				equalListLastNode = currentNode;
				equalList.push(equalListLastNode);				
			}	
			currentNode = currentNode.next;
		}
		
		if(leftSide.head != null)
			typeOfList = "left";
		if(equalList.head != null)
			typeOfList += "equal";
		if(rightSide.head != null){
			typeOfList += "right";
		}		
		
		if(typeOfList == "left"){
			resultList.head = leftSide.head;
		}
		else if(typeOfList == "equal"){
			resultList.head = equalList.head;
		}
		else if(typeOfList == "right"){
			resultList.head = rightSide.head;
		}
		else if(typeOfList == "leftequal"){
			leftLastNode.next = equalList.head;
			resultList.head = leftSide.head;
		}
		else if(typeOfList == "leftright"){
			leftLastNode.next = rightSide.head;
			resultList.head = leftSide.head;
		}		
		else if(typeOfList == "equalright"){
			equalListLastNode.next = rightSide.head;
			resultList.head = equalList.head;
		}
		else if(typeOfList.equals("leftequalright")){
			equalListLastNode.next = rightSide.head;
			leftLastNode.next = equalList.head;			
			resultList.head = leftSide.head;
		}
		return resultList;		
	}
}
