package singlyLinkedList;

import org.testng.annotations.Test;

public class LList03_PrintNodesInReverse {

	@Test
	public void test()
	{
		SinglyLinkedList list = new SinglyLinkedList();
		list.push(new Node(1));
		list.push(new Node(2));
		list.push(new Node(3));
		list.push(new Node(4));
		list.push(new Node(5));
		list.push(new Node(6));
		list.push(new Node(7));
		
		PrintInReverse(list.head);
	}
	
	
	public void PrintInReverse(Node node)
	{
		if(node == null)
			return;
		
		PrintInReverse(node.next);
		System.out.println(node.value);
	}
}
