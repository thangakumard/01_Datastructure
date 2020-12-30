package algorithms.array.easy;

import java.util.HashMap;
import java.util.Set;

import org.testng.annotations.Test;

public class Array15_OccurringOddNumberOfTime {
	@Test
	//Time complexity is O(n^2)
	public void approach1(){
		int[] a = {1, 2, 3, 2, 3, 1, 3};
		int count = 0;
		
		for(int i=0; i < a.length; i++){
			
			count = 0;
			for(int j=0; j < a.length; j++){
				
				if(a[i] == a[j]){
					count++;
				}
			}
			if(count % 2 == 1){
				System.out.println("Odd occourence is :" + a[i]);
				break;
			}
				
		}
	}
	
		@Test
		//Time complexity O(n). But it requires extra space for hashing
		public void approach2(){
			
			System.out.println("Approach 2 :");
			int[] a = {1, 2, 3, 2, 3, 1, 3};
			HashMap<Integer, Integer> hash = new HashMap<Integer,Integer>();
			
			for(int i=0; i < a.length; i++){
				
				if(hash.containsKey(a[i])){
					hash.put(a[i], (hash.get(a[i]))+1);					
				}
				else
					hash.put(a[i], 1);
			}
			
			Set<Integer> key = hash.keySet();
			
			for(Integer j:key){
				if(hash.get(j) % 2 == 1){
					System.out.println(j);
					break;
				}
			}
		}
		
		@Test
		//Time complexity O(n).
		public void approach3(){
			System.out.println("Approach 3 :");
			int[] a = {1, 2, 3, 2, 3, 1, 3};			
			int x = a[0];
			
			for(int i=1; i < a.length; i++){
				x = x ^ a[i];
			}
			
			System.out.println("Odd occourence is :" + x);			
		}
}
