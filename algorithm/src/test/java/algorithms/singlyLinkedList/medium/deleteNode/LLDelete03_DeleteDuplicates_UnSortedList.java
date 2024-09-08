package algorithms.singlyLinkedList.medium.deleteNode;

import algorithms.singlyLinkedList.base.ListNode;

import java.util.HashMap;

/**
 * https://leetcode.com/problems/remove-duplicates-from-an-unsorted-linked-list/description/
 * Given the head of a linked list, find all the values that appear more than once in the list and delete the nodes that have any of those values.
 * Return the linked list after the deletions.
 *
 * Example 1:
 * Input: head = [1,2,3,2]
 * Output: [1,3]
 * Explanation: 2 appears twice in the linked list, so all 2's should be deleted. After deleting all 2's, we are left with [1,3].
 *
 * Example 2:
 * Input: head = [2,1,1,2]
 * Output: []
 * Explanation: 2 and 1 both appear twice. All the elements should be deleted.

 * Example 3:
 * Input: head = [3,2,2,1,3,2,4]
 * Output: [1,4]
 * Explanation: 3 appears twice and 2 appears three times. After deleting all 3's and 2's, we are left with [1,4].
 *
 * Constraints:
 * The number of nodes in the list is in the range [1, 105]
 * 1 <= Node.val <= 105
 */
public class LLDelete03_DeleteDuplicates_UnSortedList {
    public ListNode deleteDuplicatesUnsorted(ListNode head) {
        HashMap<Integer, Integer> mapCounter = new HashMap<>();
        ListNode currentNode = head;

        while(currentNode != null){
            mapCounter.put(currentNode.value, mapCounter.getOrDefault(currentNode.value, 0)+1);
            currentNode = currentNode.next;
        }

        ListNode sentinal = new ListNode(0);
        currentNode = sentinal;

        while(head != null){
            if(mapCounter.get(head.value) == 1){
                currentNode.next = head;
                currentNode = currentNode.next;
            }
            head = head.next;
        }
        if(currentNode != null)
            currentNode.next = null;
        return sentinal.next;
    }
}
