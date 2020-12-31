package algorithms.linkedHashMap;

import java.util.HashMap;

import org.testng.annotations.Test;

public class LRUCache_usingMapAndNode {

	class LRUCache {

	    class Node{
	        int key;
	        int value;
	        Node next;
	        Node prev;
	        
	        public Node(int k, int v){
	            key = k;
	            value = v;
	        }
	        
	        public Node(){
	            key =0;
	            value = 0;
	        }
	    }
	    
	    private final int CAPACITY;
	    private int count = 0;
	    Node head = null;
	    Node tail = null;
	    HashMap<Integer, Node> nodeMap = new HashMap<Integer,Node>();
	    
	    public LRUCache(int capacity) {
	        CAPACITY = capacity;
	        this.head = new Node();
	        this.tail = new Node();
	        head.next = tail;
	        tail.prev = head;
	    }
	    
	    public int get(int key) {
	        if(nodeMap.containsKey(key)){
	            Node currentNode = nodeMap.get(key);
	            update(currentNode); //Pending
	            return currentNode.value;
	        }
	        return -1;
	    }
	    
	    public void put(int key, int value) {
	        Node currentNode = nodeMap.get(key);
	        if(currentNode == null){
	            Node new_node = new Node(key, value);
	            nodeMap.put(key, new_node);
	            add(new_node);
	            count++;
	        }else{
	            currentNode.value = value;
	            update(currentNode);
	        }
	        
	        if(count > CAPACITY){
	            Node node_to_Delete = tail.prev;
	            nodeMap.remove(node_to_Delete.key);
	            remove(node_to_Delete);
	            count--;
	        }
	    }
	    
	    private void add(Node currentNode){
	        Node after = head.next;
	        
	        head.next = currentNode;
	        currentNode.next = after;
	        
	        after.prev = currentNode;
	        currentNode.prev = head;
	    }
	    
	    private void remove(Node currentNode){
	        Node prev = currentNode.prev;
	        Node next = currentNode.next;
	        
	        prev.next = next;
	        next.prev = prev;
	    }
	    
	    private void update(Node currentNode){
	        remove(currentNode);
	        add(currentNode);
	    }
	}

	
	@Test
	private void testLRU() {
		LRUCache lruCahche = new LRUCache(3);
		System.out.println(lruCahche.get(1));
		lruCahche.put(1,1);
		lruCahche.put(2,2);
		lruCahche.put(3,3);
		lruCahche.put(4,4);
		System.out.println(""+lruCahche.get(4));
		
	}
}
