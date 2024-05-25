package algorithms.singlyLinkedList.deleteNode;

import algorithms.singlyLinkedList.base.ListNode;
import algorithms.singlyLinkedList.base.SinglyLinkedList;
import org.testng.annotations.Test;

public class Delete05_DeleteMiddleNode {

    @Test
    public void Test(){
        SinglyLinkedList list1 = new SinglyLinkedList();
        list1.push(new ListNode(10));
        list1.push(new ListNode(20));
        list1.push(new ListNode(30));
        list1.push(new ListNode(40));
        list1.push(new ListNode(50));
        list1.push(new ListNode(60));
        list1.push(new ListNode(70));

        deleteMiddle(list1.head);

        printList(list1.head);
    }
    public ListNode deleteMiddle(ListNode head) {

        if(head == null || head.next == null) return null; //*** IMPORTANT To cover the test case with single node [1]

        ListNode sentinel = new ListNode(0);
        sentinel.next = head;

        ListNode fast = head;
        ListNode slow = head;
        ListNode prev = head;

        while(fast != null && fast.next != null){
            fast = fast.next.next;
            prev = slow;
            slow = slow.next;
        }

        prev.next = slow.next;
        return sentinel.next;
    }

    void printList(ListNode node) {
        while (node != null) {
            System.out.print(node.value + " ");
            node = node.next;
        }
    }
}
