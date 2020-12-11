package coreJava.collections.deque_list;
import java.util.LinkedList;
import java.util.Queue;
import org.testng.annotations.Test;

import algorithms.tree.Node;

public class List01_LinkedList {

	@Test
	public void Queue_LinkedList(){
				//With Objects
				Queue<ListNode> nodeQue = new LinkedList<ListNode>();
				nodeQue.offer(new ListNode(10));
				nodeQue.offer(new ListNode(20));
				nodeQue.offer(new ListNode(30));
				nodeQue.offer(null); // Will throw Null pointer exception.But linkedList will not throw exception for null values
				nodeQue.offer(new ListNode(40));
				
				while(!nodeQue.isEmpty()){
					if(nodeQue.peek() != null){
						System.out.println(nodeQue.poll().data);
					}
					else{
						nodeQue.poll();
						System.out.println("null");
					}
				}
	}
}
