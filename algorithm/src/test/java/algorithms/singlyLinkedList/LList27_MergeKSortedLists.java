package algorithms.singlyLinkedList;

import java.util.PriorityQueue;

import org.testng.annotations.Test;

public class LList27_MergeKSortedLists {

	@Test
	private void test() {
		
		SinglyLinkedList list1 = new SinglyLinkedList();
		list1.push(new ListNode(10));
		list1.push(new ListNode(30));
		list1.push(new ListNode(50));
		list1.push(new ListNode(70));
		list1.push(new ListNode(90));
		
		SinglyLinkedList list2 = new SinglyLinkedList();
		list2.push(new ListNode(20));
		list2.push(new ListNode(40));
		list2.push(new ListNode(60));
		list2.push(new ListNode(80));
		list2.push(new ListNode(100));
		
		ListNode[] lists = new ListNode[2];
		lists[0] = list1.head;
		lists[1] = list2.head;
		
		ListNode result = mergeKLists(lists);
		
		
		
		System.out.println(" ");
        System.out.println("linked list after removal: ");
        printList(result);	
		
	}
	
	public ListNode mergeKLists(ListNode[] lists) {
        PriorityQueue<Integer> minHeap = new PriorityQueue<Integer>();
        for(ListNode head: lists){
            while(head != null){
                minHeap.add(head.value);
                head = head.next;
            }
        }
        
        ListNode dummy = new ListNode(-1);
        ListNode head = dummy;
        
        while(!minHeap.isEmpty()){
            head.next = new ListNode(minHeap.remove());
            head = head.next;
        }
        
        return dummy.next;
    }
	
	void printList(ListNode node) {
        while (node != null) {
            System.out.print(node.value + " ");
            node = node.next;
        }
    }
}
