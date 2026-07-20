package algorithms.singlyLinkedList.medium.deleteNode;

import algorithms.singlyLinkedList.base.ListNode;

/**
 * https://leetcode.com/problems/remove-duplicates-from-sorted-list-ii/description/
 * Given the head of a sorted linked list, delete all nodes that have duplicate numbers, leaving only distinct numbers from the original list. Return the linked list sorted as well.
 *
 * Example 1:
 * Input: head = [1,2,3,3,4,4,5]
 * Output: [1,2,5]
 * Example 2:
 * Input: head = [1,1,1,2,3]
 * Output: [2,3]
 *
 *
 * Constraints:
 *
 * The number of nodes in the list is in the range [0, 300].
 * -100 <= Node.val <= 100
 * The list is guaranteed to be sorted in ascending order.
 */

/**
 * Time Complexity: O(n)
 * ===============
 * Single pass through the linked list
 * Each node is visited at most once
 * n is the number of nodes
 *
 * Space Complexity: O(1)
 * ================
 * Only using constant extra space for pointers (dummy, prev, curr)
 * Not counting the output list
 */
public class LLDelete02_DeleteDuplicatesSortedList_II {
    public ListNode deleteDuplicates(ListNode head) {
        ListNode sentinel = new ListNode(0);
        ListNode fast = head;
        ListNode slow = sentinel;
        slow.next = fast;

        while(fast != null){
            while(fast.next != null && fast.val == fast.next.val){
                fast = fast.next;
            }
            if(slow.next != fast){
                slow.next = fast.next;
                fast = slow.next;
            }else{
                slow = slow.next;
                fast = fast.next;
            }

        }
        return sentinel.next;
    }
}
