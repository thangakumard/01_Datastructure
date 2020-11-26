package algorithms.singlyLinkedList;

import java.util.*;

import org.testng.annotations.Test;

public class LList19_removeZeroSumSublists {
	
	@Test
	public void test() {
		SinglyLinkedList list = new SinglyLinkedList();
		list.push(new Node(10));
		list.push(new Node(-3));
		list.push(new Node(1));
		list.push(new Node(-1));
		list.push(new Node(3));
		list.head = removeZeroSumSublists(list.head);
		
		Node currentNode = list.head;
		while(currentNode != null){
			System.out.print(currentNode.value + " -->");
			currentNode = currentNode.next;			
		}
	}
	
	public Node removeZeroSumSublists(Node head) {
        int prefix = 0;
        Node dummy = new Node(0);
        dummy.next = head;
        Map<Integer, Node> seen = new HashMap<>();
        seen.put(0, dummy);
        Node i = dummy;
        while (i != null) {
            prefix += i.value;
            seen.put(prefix, i);
            i = i.next;
        }
        prefix = 0;
        i = dummy;
        while (i != null) {
            prefix += i.value;
            i.next = seen.get(prefix).next;
            i = i.next;
        }
        return dummy.next;
    }

}
