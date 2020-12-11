package algorithms.singlyLinkedList;
import org.testng.annotations.*;

public class LList11_DeleteNode {

	@Test
	public void Test(){
		
		SinglyLinkedList list1 = new SinglyLinkedList();
		list1.push(new ListNode(10));		
		list1.push(new ListNode(30));
		list1.push(new ListNode(40));		
		list1.push(new ListNode(60));		
		list1.push(new ListNode(90));
		list1.push(new ListNode(20));
		list1.push(new ListNode(50));
		/*********** DELETE A NODE AT GIVEN INDEX ***********/
		ListNode currentNode = deleteNodeAt(list1.head, 7);		
						
		while(currentNode != null){
			System.out.print(currentNode.value + "-->");
			currentNode = currentNode.next;
		}
		System.out.print("NULL");
		System.out.println();	
		
		
		/*********** DELETE HEAD NODE ***********/
		list1.head = currentNode = deletedHeadNode(list1.head);
		
		while(currentNode != null){
			System.out.print(currentNode.value + "-->");
			currentNode = currentNode.next;
		}
		System.out.print("NULL");
		System.out.println();
		
		/*********** DELETE TAIL NODE ***********/
		currentNode = deleteTailNode(list1.head);
		
		while(currentNode != null){
			System.out.print(currentNode.value + "-->");
			currentNode = currentNode.next;
		}
		System.out.print("NULL");
		System.out.println();
	}
	
	private ListNode deletedHeadNode(ListNode head){
		if(head != null){		
			ListNode old_Head = head;
			head = head.next;
			old_Head.next = null;
			System.out.println("DELETED NODE IS : " + old_Head.value);
		}
		return head;
	}
	
	private ListNode deleteTailNode(ListNode head){
		if(head != null){
			if(head.next != null)
			{
				ListNode tail = head;
				ListNode previousToTail = null;
				
				while(tail.next != null){
					previousToTail = tail;
					tail = tail.next;
				}				
				if(previousToTail != null){					
					previousToTail.next = null;
				}
				System.out.println("DELETED NODE IS : " + tail.value);
				return head;
			}
			else{
				return null;
			}
		}		
		return head;
	}
	
	private ListNode deleteNodeAt(ListNode head,int index){

		if(noOfNodes(head) < index || index <= 0){ 
			System.out.println("INVALID POSITION !");
			return null;
		}
		ListNode currentNode = head;
		int count = 1;
		
		if(index == 1){
			ListNode oldHead = head;
			head = head.next;
			oldHead.next = null; // IT IS IMPORTANT TO MAKE 'NULL' THE NEXT OF DELETED NODE
			System.out.println("DELETED NODE IS : " + currentNode.value);
			return head;
		}		
		while(count < index-1 && currentNode != null){
			currentNode = currentNode.next;
			count++;
		}
		
		ListNode temp = currentNode.next;		
		currentNode.next = temp.next;
		temp.next = null; // IT IS IMPORTANT TO MAKE 'NULL' FOR THE NEXT OF DELETED NODE
		System.out.println("DLEETED NODE IS : " + temp.value);
		return head;		
			
	}
	
	private int noOfNodes(ListNode head){		
		if(head == null){
			return 0;
		}		
		int count = 0;
		while(head != null){
			count++;
			head = head.next;
		}		
		return count;
	}
	
}
