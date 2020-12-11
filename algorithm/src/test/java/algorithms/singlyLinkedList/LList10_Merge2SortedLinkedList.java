package algorithms.singlyLinkedList;

import org.testng.annotations.*;

public class LList10_Merge2SortedLinkedList {
	
	@Test
	public void test(){
		
		SinglyLinkedList list1 = new SinglyLinkedList();
		list1.push(new ListNode(10));		
		list1.push(new ListNode(30));
		list1.push(new ListNode(40));		
		list1.push(new ListNode(60));		
		list1.push(new ListNode(90));
		
		
		SinglyLinkedList list2 = new SinglyLinkedList();		
		list2.push(new ListNode(20));		
		list2.push(new ListNode(50));		
		list2.push(new ListNode(70));
		list2.push(new ListNode(80));		
		list2.push(new ListNode(100));
		
		ListNode newHead = mergeLists(list1.head, list2.head);
		
		while(newHead != null){
			System.out.print(newHead.value + " --> ");
			newHead = newHead.next;
		}
		System.out.print("NULL");
		System.out.println();
	}
	
	private ListNode mergeLists(ListNode list1, ListNode list2){
		
		ListNode tempNode = new ListNode(0);
		ListNode newHead = tempNode;
		
		while(list1 != null && list2 != null){
			
			if(list1.value < list2.value){
				newHead.next = list1;
				list1 = list1.next;
			}
			else{
				newHead.next = list2;
				list2 = list2.next;
			}
			newHead =  newHead.next;
		}
		
		if(list1 != null){
			newHead.next = list1;
		}
		if(list2 != null){
			newHead.next = list2;
		}
		
		
		return tempNode.next;		
	}

}
