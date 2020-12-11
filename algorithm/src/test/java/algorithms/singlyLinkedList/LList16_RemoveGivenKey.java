package algorithms.singlyLinkedList;

import org.testng.annotations.Test;

public class LList16_RemoveGivenKey {
	@Test
	public void test(){
		SinglyLinkedList list = new SinglyLinkedList();
//		list.push(new Node(10));
//		list.push(new Node(20));
//		list.push(new Node(30));
//		list.push(new Node(40));
//		list.push(new Node(50));
//		list.push(new Node(60));
//		list.push(new Node(60));
//		list.push(new Node(80));
//		list.push(new Node(90));
//		list.push(new Node(100));
		
		list.push(new ListNode(60));
		list.push(new ListNode(60));
		list.push(new ListNode(60));
		list.push(new ListNode(60));
		list.push(new ListNode(60));
		list.push(new ListNode(60));
		list.push(new ListNode(60));
		list.push(new ListNode(60));
		list.push(new ListNode(60));
		list.push(new ListNode(60));
		
		
		
		ListNode currentNode = list.head;
		while(currentNode != null){
			System.out.print(currentNode.value + " -->");
			currentNode = currentNode.next;			
		}
		System.out.print("NULL");
		System.out.println();
		
		//removeGivenKey(list.head, 40);
		//removeGivenKey(list.head, 50);
		
		//list.head = removeGivenKey(list.head, 60);
		//list.head = removeGivenKey(list.head, 600);
		
		list.head = removeElements(list.head, 60);
		//list.head = removeElements(list.head, 600);
		
		currentNode = list.head;
		while(currentNode != null){
			System.out.print(currentNode.value + " -->");
			currentNode = currentNode.next;			
		}
		System.out.print("NULL");
		System.out.println();
	}
	
	//Solution 1: Recursive
	public ListNode removeElements(ListNode head, int val) {
        if (head == null) return null;
        head.next = removeElements(head.next, val);
        return head.value == val ? head.next : head;
	}
	
	//Solution 2
	public ListNode removeGivenKey(ListNode head, int key){
		if(head == null){
			return null;
		}		
		else{
			ListNode previousNode = head;
			ListNode currentNode = head;
			while(currentNode != null){
				if(currentNode.value == key){
					if(currentNode == previousNode){
						ListNode oldNode = currentNode;
						previousNode = currentNode = currentNode.next;						 
						head = currentNode;
						oldNode.next = null;
					}else{
						ListNode oldNode = currentNode;						
						previousNode.next = currentNode.next;
						currentNode = currentNode.next;
						oldNode.next = null;
					}
				}else{
					previousNode = currentNode;
					currentNode = currentNode.next;
				}
			}
			return head;
		}
	}
	

}
