package algorithms;

import java.util.HashMap;
import org.testng.annotations.*;
public class LRUCache{
	@Test
	public void Test(){
		LRU cache= new LRU(0);
		cache.set(1,100);
		cache.set(2,200);
		cache.set(3,300);
		cache.set(3,400);
		
		System.out.println(cache.get(1));
		System.out.println(cache.get(3));
	}
}

class LRUNode{
	
	int value = 0;
	int key =0;
	LRUNode next = null;
	LRUNode pre = null;
	public LRUNode(int key, int val){
		this.value = val;
		this.key = key;
	}
}

class LRU {

	int capacity;
    HashMap<Integer, LRUNode> map = new HashMap<Integer, LRUNode>();
    LRUNode head=null;
    LRUNode end=null;
 
    public LRU(int capacity) {
        this.capacity = capacity;
    }
 
    public int get(int key) {
        if(map.containsKey(key)){
            LRUNode n = map.get(key);
            remove(n);
            setHead(n);
            return n.value;
        }
 
        return -1;
    }
 
    public void remove(LRUNode n){
        if(n.pre!=null){
            n.pre.next = n.next;
        }else{
            head = n.next;
        }
 
        if(n.next!=null){
            n.next.pre = n.pre;
        }else{
            end = n.pre;
        }
 
    }
 
    public void setHead(LRUNode n){
        n.next = head;
        n.pre = null;
 
        if(head!=null)
            head.pre = n;
 
        head = n;
 
        if(end ==null)
            end = head;
    }
 
    public void set(int key, int value) {
        if(map.containsKey(key)){
            LRUNode old = map.get(key);
            old.value = value;
            remove(old);
            setHead(old);
        }else{
            LRUNode created = new LRUNode(key, value);
            if(map.size()>=capacity){
                map.remove(end.key);
                remove(end);
                setHead(created);
 
            }else{
                setHead(created);
            }    
 
            map.put(key, created);
        }
    }
	
}
