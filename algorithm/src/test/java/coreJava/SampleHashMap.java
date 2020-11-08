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
	 * 
	 * The HashMap class does not maintain the order of the elements.
	 * This means that It might not return the elements in the same order they were inserted into it.
	 * If the application needs the elements to be returned in the same order they were inserted, LinkedHashMap should be used.
	 */
	
	
	@Test
	public void test(){
		HashMap<Integer, Boolean> testmap = new HashMap<Integer,Boolean>();
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
		
		/*********getOrDEfault(k,v)************/
		
		System.out.println("mapData.getOrDefault(10, \"From Default value\") :" + mapData.getOrDefault(10, "From Default value"));
		
		
		/***********putAll()******************/
		// Creating an empty HashMap 
	    HashMap<Integer, String> hash_map = new HashMap<Integer, String>(); 
	  
	    // Mapping string values to int keys  
	    hash_map.put(10, "Geeks"); 
	    hash_map.put(15, "4"); 
	    hash_map.put(20, "Geeks"); 
	    hash_map.put(30, "Welcomes"); 
	    hash_map.put(40, "You"); 
	  
	    // Displaying the HashMap 
	    System.out.println("Initial Mappings are: " + hash_map); 
	  
	    // Creating a new hash map and copying 
	    HashMap<Integer, String> new_hash_map = new HashMap<Integer, String>(); 
	    new_hash_map.putAll(hash_map); 
	  
	    // Displaying the final HashMap 
	    System.out.println("The new map looks like this: " + new_hash_map); 
	    
	    /***********putIfAbsent()******************/
	    hash_map.putIfAbsent(25, "From putIfAbsent"); 
	    
	    System.out.println("After putIfAbsent:\n "
                + hash_map); 
	    
	    /*************merge()*****************/
	    // create a HashMap and add some values 
        HashMap<Integer, String> 
            map1 = new HashMap<>(); 
        map1.put(1, "L"); 
        map1.put(2, "M"); 
        map1.put(3, "N"); 
  
        HashMap<Integer, String> 
            map2 = new HashMap<>(); 
        map2.put(1, "B"); 
        map2.put(2, "G"); 
        map2.put(3, "R"); 
  
        // print map details 
        System.out.println("HashMap1: "
                           + map1.toString()); 
  
        System.out.println("HashMap2: "
                           + map2.toString()); 
  
        // provide value for new key which is absent 
        // using computeIfAbsent method 
        map2.forEach( 
            (key, value) 
                -> map1.merge( 
                    key, 
                    value, 
                    (v1, v2) 
                        -> v1.equalsIgnoreCase(v2) 
                               ? v1 
                               : v1 + ", " + v2)); 
  
        // print new mapping 
        System.out.println("New HashMap: " + map1);
        System.out.println("*******************************");
	
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
	     System.out.println("**************");
	     
	     HashMap<Character, Integer> aMap = new HashMap<Character, Integer>();
	     aMap.put('A', 1);
	     aMap.put('B', 2);
	     aMap.put('C', 3);
	     for(Map.Entry<Character, Integer> entry : aMap.entrySet()) {
	    	 
	     }
	     
	      
	      
	}
	
}
