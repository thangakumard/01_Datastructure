package algorithms.singlyLinkedList.medium.deleteNode;

import algorithms.singlyLinkedList.base.ListNode;

/***
 * https://leetcode.com/problems/remove-nth-node-from-end-of-list/
 * Given the head of a linked list, remove the nth node from the end of the list and return its head.
 * Example 1:
 * Input: head = [1,2,3,4,5], n = 2
 * Output: [1,2,3,5]

 * Example 2:
 * Input: head = [1], n = 1
 * Output: []

 * Example 3:
 * Input: head = [1,2], n = 1
 * Output: [1]
 *
 * Constraints:
 * The number of nodes in the list is sz.
 * 1 <= sz <= 30
 * 0 <= Node.val <= 100
 * 1 <= n <= sz
 *
 * Follow up: Could you do this in one pass?
 */
public class DN02_removeNthFromEnd {
    public ListNode removeNthFromEnd(ListNode head, int n) {
        if(head == null || n == 0) return head;

        ListNode sentinal = new ListNode(0);
        sentinal.next = head;

        ListNode slow = sentinal, fast = head; //IMPORTANT to start slow from sentinal fast from head*/

        for(int i=0; i<n; i++){
            fast = fast.next;
        }

        while(fast != null){
            slow = slow.next;
            fast = fast.next;
        }

        slow.next = slow.next.next;
        return sentinal.next;
    }
}
