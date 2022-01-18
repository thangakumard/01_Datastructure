package coreJava.collections.map;
import java.util.Map;
import java.util.TreeMap;

import org.testng.annotations.Test;


public class Map04_TreeMap {

	
	@Test
	public void TreeMapMethods(){
		TreeMap<Integer, Integer> treeMap1 = new TreeMap<Integer,Integer>();
		
		treeMap1.put(1, 100);
		treeMap1.put(5, 300);
		treeMap1.put(4, 400);
		treeMap1.put(2, 500);
		
		for(Map.Entry<Integer,Integer> entry: treeMap1.entrySet()){
			System.out.println("Key :" + entry.getKey() + "Value :" + entry.getValue());
		}
		
		//This is equvalent to
		
		System.out.println("************ Using forEach in Java 1.8 ************");
		
		treeMap1.forEach((k, v)->{
			System.out.println("Key :" + k + "Value :" + v);
		});
		
		System.out.println("First value in the tree :" + treeMap1.firstKey());
		
		System.out.println("Last value in the tree :" + treeMap1.lastKey());
		
		System.out.println("treeMap1.get(10) : " + treeMap1.get(10));
		
		System.out.println("treeMap1.getOrDefault(10,10000) : " + treeMap1.getOrDefault(10, 10000));

		System.out.println("floor key: " + treeMap1.floorKey(3));

		System.out.println("ceilingKey : " + treeMap1.ceilingKey(3));
	}
}
