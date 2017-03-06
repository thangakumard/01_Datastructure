package coreJava;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.testng.annotations.Test;

import cucumber.deps.com.thoughtworks.xstream.core.MapBackedDataHolder;

public class SampleHashMap {

	@Test
	public void test(){
		HashMap<Integer, String> mapData = new HashMap<Integer,String>();
		
		/********* Insert a record *********/
		mapData.put(1, "one");
		mapData.put(2, "Two");
		mapData.put(3, "Three");
		mapData.put(4, "Four");
		mapData.put(5, "Five");
		mapData.put(6, "Six");
		
		/********** Access a key value ******/
		System.out.println(mapData.get(1));
		
		/******** To update a key value *******/
		//any one of the below statement can be used to update a key
		mapData.put(1, "Seven");
		mapData.replace(2, "Nine");
		
		/********* To Remove a key*******/
		mapData.remove(6);
		
		/******** Access All the keys ******/
		Set<Integer> lstIds = mapData.keySet();
		for(Integer I:lstIds){
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
