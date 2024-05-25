package algorithms.singlyLinkedList.deleteNode;

import java.util.*;

import org.testng.annotations.Test;

import algorithms.singlyLinkedList.base.ListNode;
import algorithms.singlyLinkedList.base.SinglyLinkedList;

public class Delete02_RemoveDuplicates_UnSortedList {

	@Test
	public void test(){
		SinglyLinkedList list = new SinglyLinkedList();
		list.push(new ListNode(10));
		list.push(new ListNode(20));
		list.push(new ListNode(10));
		list.push(new ListNode(20));
		list.push(new ListNode(10));
				
		
		ListNode currentNode = list.head;
		while(currentNode != null){
			System.out.print(currentNode.value + " -->");
			currentNode = currentNode.next;
		}
		System.out.println("NULL");
		System.out.println();
		
		currentNode = removeDuplicatesFromUnsortedList_set(list);		
		while(currentNode != null){
			System.out.print(currentNode.value + " -->");
			currentNode = currentNode.next;
		}
		System.out.println("NULL");
		System.out.println();
	}
	
	/*
	 * Time complexity O(n)
	 */
	public ListNode removeDuplicatesFromUnsortedList_set(SinglyLinkedList list) {
	
		ListNode sentinel = new ListNode(0);
        sentinel.next = list.head;
        
        ListNode current = list.head; // will be used for outer loop
        Set<Integer> set = new HashSet<>();
        
        if(!set.contains(current.value)){
    		set.add(current.value);
        }

        while (current.next != null) {
        	 if(!set.contains(current.next.value)){
        		 set.add(current.value);
        		 current = current.next;
        	 }else {
        		 current.next = current.next.next;
        	 }
        }
        return sentinel.next;
	}
	
	/*
	 * Time complexity O(n ^ 2)
	 * Space Complexity O(n)
	 */
	public ListNode removeDuplicatesFromUnsortedList(SinglyLinkedList list) {
		ListNode sentinel = new ListNode(0);
        sentinel.next = list.head;
        
        ListNode current = list.head; // will be used for outer loop
		ListNode compare = null;     // will be used for inner loop

        while (current != null && current.next != null) {
            compare = current;
            while (compare.next != null) {
                if (current.value == compare.next.value) { //check if duplicate
                    compare.next = compare.next.next;
                } else {
                    compare = compare.next;
                }
            }
            current = current.next;
        }
        return sentinel.next;
    }
}
