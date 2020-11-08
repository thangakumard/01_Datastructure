package coreJava.collections.deque_list;
import java.util.LinkedList;
import java.util.Queue;
import org.testng.annotations.Test;

import algorithms.tree.Node;

public class List01_LinkedList {

	@Test
	public void Queue_LinkedList(){
				//With Objects
				Queue<Node> nodeQue = new LinkedList<Node>();
				nodeQue.offer(new Node(10));
				nodeQue.offer(new Node(20));
				nodeQue.offer(new Node(30));
				nodeQue.offer(null); // Will throw Null pointer exception.But linkedList will not throw exception for null values
				nodeQue.offer(new Node(40));
				
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
