package algorithms.singlyLinkedList;
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
public class PartitioningLinkedList {

	@Test
	public void doPartition(){
		SinglyLinkedList list = new SinglyLinkedList();
		list.push(new ListNode(5));
		list.push(new ListNode(1));
		list.push(new ListNode(4));
		list.push(new ListNode(2));
		list.push(new ListNode(10));
		list.push(new ListNode(3));
		
		SinglyLinkedList partitioned = partitionByX(list.head,3);
		
		ListNode currentNode = partitioned.head;
		while(currentNode != null){
			System.out.println(currentNode.value);
			currentNode = currentNode.next;
		}
		Assert.assertTrue(true);
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
		else if(typeOfList == "left"){
			resultList.head = equalList.head;
		}
		else if(typeOfList == "left"){
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
