package algorithms.singlyLinkedList.deleteNode;

import algorithms.singlyLinkedList.base.ListNode;

/**
 * https://leetcode.com/problems/remove-duplicates-from-sorted-list-ii/description/
 * Given the head of a sorted linked list, delete all nodes that have duplicate numbers, leaving only distinct numbers from the original list. Return the linked list sorted as well.
 *
 * Example 1:
 * Input: head = [1,2,3,3,4,4,5]
 * Output: [1,2,5]
 *
 * Example 2:
 * Input: head = [1,1,1,2,3]
 * Output: [2,3]
 *
 * Constraints:
 * The number of nodes in the list is in the range [0, 300].
 * -100 <= Node.val <= 100
 * The list is guaranteed to be sorted in ascending order.
 */
public class Delete04_RemoveDuplicatesSortedList_II {

    /**
     * Time O(N)
     * Space O(1)
     */
    public ListNode deleteDuplicates(ListNode head) {
        ListNode sentinal = new ListNode(0);
        ListNode currentNode = sentinal;
        ListNode prev = null;

        while(head != null){
            if(head.next == null  || head.value != head.next.value){
                if(prev == null || prev.value != head.value){
                    currentNode.next = head;
                    currentNode = currentNode.next;
                }
            }
            prev = head;
            head = head.next;
        }
        currentNode.next = null;
        return sentinal.next;
    }
}
