package algorithms.singlyLinkedList.medium.swap;

import algorithms.singlyLinkedList.base.ListNode;

public class swap02_SwapKthNode {
    public ListNode swapNodes(ListNode head, int k) {
        if(head == null || head.next == null) return head;
        ListNode sentianl = new ListNode(0);
        sentianl.next = head;

        ListNode slow = sentianl, fast = head, first = sentianl;
        for(int i=0; i < k-1; i++){
            fast = fast.next;
        }
        first = fast;
        while (fast != null) {
            slow = slow.next;
            fast = fast.next;
        }

        int temp = first.value;
        first.value = slow.value;
        slow.value = temp;
        return sentianl.next;
    }
}
