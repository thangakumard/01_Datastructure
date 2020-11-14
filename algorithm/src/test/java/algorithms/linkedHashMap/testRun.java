package algorithms.linkedHashMap;

import org.testng.annotations.Test;

public class testRun {
	
	@Test
	private void testLRU() {
		LRUCache lruCahche = new LRUCache(3);
		System.out.println(lruCahche.get(1));
		lruCahche.put(1,1);
		lruCahche.put(2,2);
		lruCahche.put(3,3);
		lruCahche.put(4,4);
		System.out.println(""+lruCahche.map);
		
	}

}
