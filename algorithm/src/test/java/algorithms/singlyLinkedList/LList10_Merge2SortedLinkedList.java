package algorithms.singlyLinkedList;

import org.testng.annotations.*;

public class LList10_Merge2SortedLinkedList {
	
	@Test
	public void test(){
		
		SinglyLinkedList list1 = new SinglyLinkedList();
		list1.push(new Node(10));		
		list1.push(new Node(30));
		list1.push(new Node(40));		
		list1.push(new Node(60));		
		list1.push(new Node(90));
		
		
		SinglyLinkedList list2 = new SinglyLinkedList();		
		list2.push(new Node(20));		
		list2.push(new Node(50));		
		list2.push(new Node(70));
		list2.push(new Node(80));		
		list2.push(new Node(100));
		
		Node newHead = mergeLists(list1.head, list2.head);
		
		while(newHead != null){
			System.out.print(newHead.value + " --> ");
			newHead = newHead.next;
		}
		System.out.print("NULL");
		System.out.println();
	}
	
	private Node mergeLists(Node list1, Node list2){
		
		Node tempNode = new Node(0);
		Node newHead = tempNode;
		
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
