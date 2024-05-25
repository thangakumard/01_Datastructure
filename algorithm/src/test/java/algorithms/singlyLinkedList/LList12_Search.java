package algorithms.singlyLinkedList;

import algorithms.singlyLinkedList.base.ListNode;
import algorithms.singlyLinkedList.base.SinglyLinkedList;
import org.testng.annotations.*;

public class LList12_Search {
	
	@Test
	public void test(){
		SinglyLinkedList list1 = new SinglyLinkedList();
		list1.push(new ListNode(10));
		list1.push(new ListNode(30));
		list1.push(new ListNode(40));		
		list1.push(new ListNode(60));		
		list1.push(new ListNode(90));
		list1.push(new ListNode(20));
		list1.push(new ListNode(50));
		
		ListNode resultNode = search(list1.head, 40);
		if(resultNode != null){
			System.out.println();
			System.out.println("FOUND THE REQUIRED NODE :" + resultNode.value);
			System.out.println();
		}
		else{
			System.out.println();
			System.out.println("NO NODE WITH THE GIVEN VALUE !");
			System.out.println();
		}
		resultNode = search(list1.head, 400);
		if(resultNode != null){
			System.out.println();
			System.out.println("FOUND THE REQUIRED NODE :" + resultNode.value);
			System.out.println();
		}
		else{
			System.out.println();
			System.out.println("NO NODE WITH THE GIVEN VALUE !");
			System.out.println();
		}
		
	}
	
	private ListNode search(ListNode head,int searchValue){
		
		if(head == null){
			return null;
		}
		else{
			ListNode currentNode = head;
			while(currentNode != null){
				if(currentNode.value == searchValue){
					return currentNode;
				}else{
					currentNode = currentNode.next;
				}
			}
		}
		return null;
	}

}
