package coreJava.collections.map;
import java.util.Map;
import java.util.TreeMap;

import org.testng.annotations.Test;

/***
 * Java TreeMap class is a red-black tree based implementation.
 * It provides an efficient means of storing key-value pairs in SORTED (ascending) order .
 *
 * The important points about Java TreeMap class are:
 *
 * Java TreeMap contains values based on the key. It implements the NavigableMap interface and extends AbstractMap class.
 * Java TreeMap contains only unique elements.
 * Java TreeMap cannot have a null key but can have multiple null values.
 * Java TreeMap is non synchronized.
 * Java TreeMap maintains ascending order.
 */

public class Map04_TreeMap {

	
	@Test
	public void TreeMapMethods(){
		TreeMap<Integer, Integer> treeMap1 = new TreeMap<Integer,Integer>();
		
		treeMap1.put(10, 100);
		treeMap1.put(5, 300);
		treeMap1.put(4, 400);
		treeMap1.put(2, 500);
		treeMap1.put(1, 12);
		
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

		System.out.println("floor key of key 3: " + treeMap1.floorKey(3));

		System.out.println("ceiling Key of key 3: " + treeMap1.ceilingKey(3));
	}
}
