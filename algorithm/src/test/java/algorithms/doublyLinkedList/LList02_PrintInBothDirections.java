package algorithms.doublyLinkedList;

public class LList02_PrintInBothDirections {
	
	public void printForward(DoublyLinkedList list){
		if(list != null){
			Node currentNode = list.head;
			while(currentNode != null){
				System.out.print(currentNode.data + "-- >");
				currentNode = currentNode.next;
			}
			System.out.print("NULL");
			System.out.println();
		}
		else{
			System.out.println("List is empty !");
		}
	}
	
	public void printBackword(DoublyLinkedList list){
		if(list != null){
			Node currentNode = list.tail;
			while(currentNode != null){
				System.out.print(currentNode.data + "-- >");
				currentNode = currentNode.previous;
			}
			System.out.print("NULL");
			System.out.println();
		}
		else{
			System.out.println("List is empty !");
		}
	}

}
