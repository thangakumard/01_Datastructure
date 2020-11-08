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
		
		list.push(new Node(60));
		list.push(new Node(60));
		list.push(new Node(60));
		list.push(new Node(60));
		list.push(new Node(60));
		list.push(new Node(60));
		list.push(new Node(60));
		list.push(new Node(60));
		list.push(new Node(60));
		list.push(new Node(60));
		
		
		
		Node currentNode = list.head;
		while(currentNode != null){
			System.out.print(currentNode.value + " -->");
			currentNode = currentNode.next;			
		}
		System.out.print("NULL");
		System.out.println();
		
		//removeGivenKey(list.head, 40);
		//removeGivenKey(list.head, 50);
		list.head = removeGivenKey(list.head, 60);
		list.head = removeGivenKey(list.head, 600);
		
		
		currentNode = list.head;
		while(currentNode != null){
			System.out.print(currentNode.value + " -->");
			currentNode = currentNode.next;			
		}
		System.out.print("NULL");
		System.out.println();
	}
	
	public Node removeGivenKey(Node head, int key){
		if(head == null){
			return null;
		}		
		else{
			Node previousNode = head;
			Node currentNode = head;
			while(currentNode != null){
				if(currentNode.value == key){
					if(currentNode == previousNode){
						Node oldNode = currentNode;
						previousNode = currentNode = currentNode.next;						 
						head = currentNode;
						oldNode.next = null;
					}else{
						Node oldNode = currentNode;						
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
