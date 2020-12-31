package algorithms.deque;

import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

import org.testng.annotations.Test;

public class LRUCache {

	private Deque<Integer> doublyQueue = new LinkedList<Integer>();
	private HashSet<Integer> cacheSet = new HashSet<Integer>();
	private final int CACHE_SIZE = 5;
	private static final int MAX_ENTRIES = 5;

	@Test
	public void testLRUCache() {
		@SuppressWarnings("serial")
		LinkedHashMap<Integer, String> lhm = new LinkedHashMap<Integer,
			      String>(MAX_ENTRIES + 1, .75F, false) {
			         protected boolean removeEldestEntry(Map.Entry<Integer, String> eldest) {
			            return size() > MAX_ENTRIES;
			         }
			      };
			      lhm.put(0, "H");
			      lhm.put(1, "E");
			      lhm.put(2, "L");
			      lhm.put(3, "L");
			      lhm.put(4, "O");
			      lhm.put(1, "O");
			      lhm.put(0, "O");

			      System.out.println("" + lhm);
	}
	
//	public Deque<Integer> getLRUCache(int page){
//		
//		if(doublyQueue.contains(page)){
//			
//			
//			
//			doublyQueue.offerLast(page);
//			
//		}else {
//			if(doublyQueue.size() == CACHE_SIZE) {
//				doublyQueue.pollFirst();
//			}
//			doublyQueue.offerLast(page);
//		}
//		
//		return doublyQueue;
//	}
	
	
}

