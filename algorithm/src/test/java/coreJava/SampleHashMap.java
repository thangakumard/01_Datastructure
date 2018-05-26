package coreJava;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.testng.annotations.Test;


public class SampleHashMap {
	
	/****************
	 * Will not have duplicate key. 
	 * But If second time try to insert the existing key value, it will update the value for that key
	 * 
	 */
	
	
	@Test
	public void test(){
		HashMap<Object, String> mapData = new HashMap<Object,String>();
        HashMap<Integer, String[]> input = new HashMap<Integer,String[]>();
        input.put(1, new String[]{"asdasd","dsfdgfg"});
		/********* Insert a record *********/
		mapData.put(1, "one");
		mapData.put(2, "Two");
		mapData.put(3, "Three");
		mapData.put(4, "Four");
		mapData.put(5, "Five");
		mapData.put(6, "Six");
		mapData.put(7, null);
		mapData.put(null, null);
		mapData.put(null, null);// This record will update the record with "null" key value. 
								// it will not throw any exception
		
		
		/********** Access a key value ******/
		System.out.println(mapData.get(1));
		
		/******** To update a key value *******/
		//any one of the below statement can be used to update a key
		mapData.put(1, "Seven");
		mapData.replace(2, "Nine");
		
		/********* To Remove a key*******/
		mapData.remove(6);
		
		/*********** To check if a key is present *******/
		if(mapData.containsKey(3)){
			System.out.println("Key 3 is present in the collection.");
		}
		
		/******** Access All the keys ******/
		Set<Object> lstIds = mapData.keySet();
		for(Object I:lstIds){
			System.out.println("Key is : " + I + " & Value is : " + mapData.get(I));
		}
		
		System.out.println("**************");
		
		//http://beginnersbook.com/2013/12/hashmap-in-java-with-example/
		 Set set = mapData.entrySet();
	      Iterator iterator = set.iterator();
	      while(iterator.hasNext()) {
	         Map.Entry mentry = (Map.Entry)iterator.next();
	         System.out.print("key is: "+ mentry.getKey() + " & Value is: ");
	         System.out.println(mentry.getValue());
	      }
		/*********************************/
	}
	
}
