package algorithms.singlyLinkedList.medium.swap;

import algorithms.singlyLinkedList.base.ListNode;

/**
 * https://leetcode.com/problems/swap-nodes-in-pairs/description/
 * Given a linked list, swap every two adjacent nodes and return its head. You must solve the problem without modifying the values in the list's nodes (i.e., only nodes themselves may be changed.)
 *
 * Example 1:
 * Input: head = [1,2,3,4]
 * Output: [2,1,4,3]
 *
 * Example 2:
 * Input: head = []
 * Output: []
 *
 * Example 3:
 * Input: head = [1]
 * Output: [1]
 *
 * Constraints:
 * The number of nodes in the list is in the range [0, 100].
 * 0 <= Node.val <= 100
 */
public class swap01_SwapInPairs {
    public ListNode swapPairs(ListNode head) {
        ListNode sentinal = new ListNode(0);
        sentinal.next = head;

        ListNode prevNode = sentinal;

        while (head != null && head.next != null) {
            ListNode firstNode = head;
            ListNode secondNode = head.next;

            //swapping
            prevNode.next = secondNode;
            firstNode.next = secondNode.next;
            secondNode.next = firstNode;

            prevNode = firstNode;
            head = firstNode.next;
        }
        return sentinal.next;
    }
}
