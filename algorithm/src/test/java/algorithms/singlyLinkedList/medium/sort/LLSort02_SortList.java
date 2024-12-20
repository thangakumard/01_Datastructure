package algorithms.singlyLinkedList.medium.sort;

import algorithms.singlyLinkedList.base.ListNode;

/**
 * https://leetcode.com/problems/sort-list/
 * Given the head of a linked list, return the list after sorting it in ascending order.
 *
 * Example 1:
 * Input: head = [4,2,1,3]
 * Output: [1,2,3,4]
 *
 * Example 2:
 * Input: head = [-1,5,3,4,0]
 * Output: [-1,0,3,4,5]
 *
 * Example 3:
 * Input: head = []
 * Output: []
 *
 * Constraints:
 * The number of nodes in the list is in the range [0, 5 * 104].
 * -105 <= Node.val <= 105
 * Follow up: Can you sort the linked list in O(n logn) time and O(1) memory (i.e. constant space)?
 */
public class LLSort02_SortList {
    public ListNode sortList(ListNode head) {
        if (head == null || head.next == null) return head;

        ListNode middle = getMiddleNode(head);
        ListNode left = sortList(head);
        ListNode right = sortList(middle);
        return mergeSort(left, right);
    }

    private ListNode getMiddleNode(ListNode node){
        ListNode slow = null, fast = node;
        while(fast != null && fast.next != null){
            fast = fast.next.next;
            slow = (slow == null) ? node : slow.next;
        }
        ListNode mid = slow.next;
        slow.next = null;
        return mid;
    }

    private ListNode mergeSort(ListNode list1, ListNode list2){
        ListNode sentinel = new ListNode(0);
        ListNode currentNode = sentinel;

        while(list1 != null && list2 != null){
            if(list1.value < list2.value){
                currentNode.next = list1;
                list1 = list1.next;
                currentNode = currentNode.next;
            }else{
                currentNode.next = list2;
                list2 = list2.next;
                currentNode = currentNode.next;
            }
        }
        currentNode.next = list1 != null ? list1 : list2;
        return sentinel.next;
    }
}
