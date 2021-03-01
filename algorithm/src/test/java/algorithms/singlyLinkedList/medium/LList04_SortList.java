package algorithms.singlyLinkedList.medium;

import org.testng.annotations.Test;

import algorithms.singlyLinkedList.ListNode;
import algorithms.singlyLinkedList.SinglyLinkedList;

public class LList04_SortList {

	@Test
	public void Test() {
		SinglyLinkedList list1 = new SinglyLinkedList();
		list1.push(new ListNode(9));
		list1.push(new ListNode(8));
		list1.push(new ListNode(1));
		list1.push(new ListNode(2));
		list1.push(new ListNode(5));

		ListNode head = sortList(list1.head);
		System.out.println("Sorted List is :");
		printList(head);
	}

    public ListNode sortList(ListNode head) {
        if (head == null || head.next == null)
            return head;
        ListNode mid = getMid(head);
        ListNode left = sortList(head);
        ListNode right = sortList(mid);
        return merge(left, right);
    }

    ListNode merge(ListNode list1, ListNode list2) {
        ListNode dummyHead = new ListNode(0);
        ListNode currentNode = dummyHead;
        while (list1 != null && list2 != null) {
            if (list1.value < list2.value) {
            	currentNode.next = list1;
                list1 = list1.next;
                currentNode = currentNode.next;
            } else {
            	currentNode.next = list2;
                list2 = list2.next;
                currentNode = currentNode.next;
            }
        }
        currentNode.next = (list1 != null) ? list1 : list2;
        return dummyHead.next;
    }

    ListNode getMid(ListNode head) {
        ListNode slow = null;
        while (head != null && head.next != null) {
        	slow = (slow == null) ? head : slow.next;
            head = head.next.next;
        }
        ListNode mid = slow.next;
        slow.next = null; // IMPORTANT TO MAKE slow.next = null to split the linked list
        return mid;
    }
    
    
    void printList(ListNode node) {
		while (node != null) {
			System.out.print(node.value + "->");
			node = node.next;
		}
	}
}

