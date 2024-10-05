package algorithms.singlyLinkedList.medium.merge;
import org.testng.annotations.Test;

import algorithms.singlyLinkedList.base.ListNode;
import algorithms.singlyLinkedList.base.SinglyLinkedList;

public class LList08_IntersectionOfTwoList {

	@Test
	public void Test(){
		
		SinglyLinkedList list1 = new SinglyLinkedList();
		list1.push(new ListNode(1));
		list1.push(new ListNode(2));
		list1.push(new ListNode(3));
		list1.push(new ListNode(4));
		list1.push(new ListNode(6));
		list1.push(new ListNode(9));
		list1.push(new ListNode(10));
		
		SinglyLinkedList list2 = new SinglyLinkedList();
		list2.push(new ListNode(10));
		list2.push(new ListNode(20));

		list2.head.next.next = list1.head.next.next.next; 
		
		System.out.println("INTERSECTION IS : " + getIntersectionNode_01(list1.head, list2.head).value);
	}

	/**
	 * Time: O(N)
	 * Space: O(1)
	 */
	public ListNode getIntersectionNode_01(ListNode headA, ListNode headB) {
		ListNode pA = headA;
		ListNode pB = headB;
		while(pA != pB){
			pA = pA != null ? pA.next : headB;
			pB = pB != null ? pB.next : headA;
		}
		return pA;
	}
	
	private ListNode getIntersectionNode_02(ListNode nodeA, ListNode nodeB){
		
		if(nodeA == null || nodeB == null)
			return null;
		int x = getLength(nodeA);
		int y = getLength(nodeB);
		
		int z = Math.abs(x - y);
		
		while(z > 0){
			if(x > y){
				nodeA = nodeA.next;
			}
			else{
				nodeB = nodeB.next;
			}
			z--;
		}
		
		while(nodeA != null && nodeB != null && nodeA != nodeB){
			nodeA = nodeA.next;
			nodeB = nodeB.next;
		}
		
		return nodeA;
	}
	
	private int getLength(ListNode head){
		int n =0;
		while(head != null){
			head = head.next;
			n++;
		}
		return n;
	}
	
}
