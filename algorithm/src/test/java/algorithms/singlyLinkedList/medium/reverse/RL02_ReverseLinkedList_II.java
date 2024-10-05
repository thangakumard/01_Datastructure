package algorithms.singlyLinkedList.medium.reverse;

import algorithms.singlyLinkedList.base.ListNode;

/***
 * https://leetcode.com/problems/reverse-linked-list-ii/
 *
 *Given the head of a singly linked list and two integers left and right where left <= right,
 *reverse the nodes of the list from position left to position right, and return the reversed list.
 *
 * Example 1:
 * Input: head = [1,2,3,4,5], left = 2, right = 4
 * Output: [1,4,3,2,5]
 * Example 2:
 * Input: head = [5], left = 1, right = 1
 * Output: [5]
 *
 * Constraints:
 *
 * The number of nodes in the list is n.
 * 1 <= n <= 500
 * -500 <= Node.val <= 500
 * 1 <= left <= right <= n
 */
public class RL02_ReverseLinkedList_II {

    /**
     * Time: O(n)
     * Space: O(1)
     */
    public ListNode reverseBetween(ListNode head, int left, int right) {
        if(head == null || left == right ) return head;

        ListNode sentinel  = new ListNode(0);
        sentinel.next = head;
        ListNode prev = sentinel;

        for(int i=0; i < left-1; i++){
            prev = prev.next;
        }

        ListNode current = prev.next;
        ListNode next;
        for(int i=0; i < right - left; i++){
            next = current.next;
            current.next = next.next;

            next.next = prev.next;
            prev.next = next;
        }

        return sentinel.next;
    }
}
