package coreJava;
import java.util.*;

import org.testng.annotations.Test;
public class SampleArrayDeque {

	/******* ArrayDeque as stack ***********/
	@Test
	public void test(){
		 Deque<String> stack = new ArrayDeque<String>();
		    
		   // To push item to stack		
		 	stack.push("first");
		    stack.push("second");
		    stack.push("Third");
		    stack.addFirst("Four");
		    stack.addFirst("Five");
		    
		    //To add item to bottom of the stack
		    stack.addLast("Zero");
		    
		    System.out.println("size()" + stack.size());
		    System.out.println("getFirst()" + stack.getFirst());
		    System.out.println("getLast()" + stack.getLast());
		    System.out.println("pop()" + stack.pop());
		    System.out.println("PollFirst" + stack.pollFirst());
	}
	
	/*******ArrayDeque as a Queue*********/
	@Test
	public void whenOffer_addsAtLast() {
	    Deque<String> queue = new ArrayDeque<String>();
	    queue.offer("first");
	    queue.offer("second");
	    queue.offer("third");
	  
	    System.out.println("getLast()" + queue.getLast());
	    System.out.println("poll()" + queue.poll());	
	    System.out.println("PollFirst" + queue.pollFirst());
	}	
}
