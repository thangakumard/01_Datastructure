package algorithms.priorityQueue;

import algorithms.singlyLinkedList.base.ListNode;

import java.util.Comparator;
import java.util.PriorityQueue;

/***
 * https://leetcode.com/problems/merge-k-sorted-lists/
 *
 * You are given an array of k linked-lists lists, each linked-list is sorted in ascending order.
 * Merge all the linked-lists into one sorted linked-list and return it.
 *
 * Example 1:
 * Input: lists = [[1,4,5],[1,3,4],[2,6]]
 * Output: [1,1,2,3,4,4,5,6]
 * Explanation: The linked-lists are:
 * [
 *   1->4->5,
 *   1->3->4,
 *   2->6
 * ]
 * merging them into one sorted list:
 * 1->1->2->3->4->4->5->6
 *
 * Example 2:
 * Input: lists = []
 * Output: []
 *
 * Example 3:
 * Input: lists = [[]]
 * Output: []
 *
 * Constraints:
 * k == lists.length
 * 0 <= k <= 104
 * 0 <= lists[i].length <= 500
 * -104 <= lists[i][j] <= 104
 * lists[i] is sorted in ascending order.
 * The sum of lists[i].length will not exceed 104.
 */
public class PriorityQueue04_mergeKLists {
    public ListNode mergeKLists(ListNode[] lists) {
        if(lists == null || lists.length == 0) return null;

        PriorityQueue<ListNode> queue = new PriorityQueue<>(
                new Comparator<ListNode>() {
                    public int compare(ListNode l1, ListNode l2){
                        if(l1.value > l2.value){
                            return 1;
                        }else if (l1.value == l2.value){
                            return 0;
                        }else{
                            return -1;
                        }
                    }
                }
        );

        for(ListNode node: lists){
            if(node != null){
                queue.add(node);
            }
        }

        ListNode sentinal = new ListNode(0);
        ListNode head = sentinal;

        while (!queue.isEmpty()) {
            head.next = queue.poll();
            head = head.next;
            if(head.next != null){
                queue.add(head.next);
            }
        }
        return sentinal.next;
    }
}
