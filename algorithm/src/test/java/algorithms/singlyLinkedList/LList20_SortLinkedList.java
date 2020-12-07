package algorithms.singlyLinkedList;

import org.testng.annotations.Test;

public class LList20_SortLinkedList {
	
	@Test
	public void test() {
		SinglyLinkedList list = new SinglyLinkedList();
		list.push(new Node(1));
		list.push(new Node(9));
		list.push(new Node(8));
		list.push(new Node(10));
		
		sortList(list.head);	
		}

    public Node sortList(Node head) {
        if (head == null || head.next == null)
            return head;
        Node mid = getMid(head);
        Node left = sortList(head);
        Node right = sortList(mid);
        return merge(left, right);
    }

    Node merge(Node list1, Node list2) {
        Node dummyHead = new Node(0);
        Node tail = dummyHead;
        while (list1 != null && list2 != null) {
            if (list1.value < list2.value) {
                tail.next = list1;
                list1 = list1.next;
                tail = tail.next;
            } else {
                tail.next = list2;
                list2 = list2.next;
                tail = tail.next;
            }
        }
        tail.next = (list1 != null) ? list1 : list2;
        return dummyHead.next;
    }

    Node getMid(Node head) {
        Node midPrev = null;
        while (head != null && head.next != null) {
            midPrev = (midPrev == null) ? head : midPrev.next;
            head = head.next.next;
        }
        Node mid = midPrev.next;
        midPrev.next = null;
        return mid;
    }
}