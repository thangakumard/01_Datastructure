package algorithms.singlyLinkedList.rabbit;

import algorithms.singlyLinkedList.base.ListNode;

public class Rabbit02_hasCycle {

    public boolean hasCycle(ListNode head) {
        ListNode slow = head, fast = head;

        while(fast != null && fast.next != null){
            fast = fast.next.next;
            slow = slow.next;
            if(slow == fast) return true; //IMPORTANT to keep at the end to cover the input with 2 nodes [1,2]
        }

        return false;
    }
}
