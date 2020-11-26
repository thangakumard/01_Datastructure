package coreJava.collections.list;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import org.testng.annotations.Test;

/********
 * Reference : https://docs.oracle.com/javase/7/docs/api/java/util/ArrayList.html
 * Resizable-array implementation of the List interface. 
 * Implements all optional list operations, and permits all elements, including null. 
 * In addition to implementing the List interface, this class provides methods to manipulate the size of the array that is used internally to store the list. 
 * (This class is roughly equivalent to Vector, except that it is unsynchronized.)
 *
 */
public class List01_ArrayList {

  @Test
	public void arrayListSample(){
       List<Integer> list1 = new ArrayList<Integer>();
       //ADD
       list1.add(10);
       list1.add(20);
       list1.add(30);
       list1.add(40);      
       
       //ADD ALL
       List<Integer> list2 = new ArrayList<Integer>();
       list2.add(100);
       list2.add(200);
       list2.add(300);
       list2.add(400);
       
       list1.addAll(list2);
       
       //ADD ALL -BY INDEX
       List<Integer> list3 = new ArrayList<Integer>();
       list3.add(1000);
       list3.add(2000);
       list3.add(3000);
       list3.add(4000);
       list3.addAll(0,list3);
       
       //SIZE OF LIST
       System.out.println("list1.size() : " + list1.size());
       
     //REMOVE BY INDEX
       list1.remove(0);
       
       //REMOVE BY OBJECT
       list1.remove(list1.get(1)); // REMOVE BY OBJECT
       
       //REMOVE ALL
       List<Integer> listToRemove = new ArrayList<Integer>();
       listToRemove.add(30);
       listToRemove.add(40);
       list1.remove(listToRemove);
       
       //ISEMPTY
       System.out.println("Is list1 isempty :" + list1.isEmpty());
       
       //ITERATE
       System.out.println("Iterator loops elements in list1 :");
       Iterator<Integer> i = list1.iterator();
       while(i.hasNext()){
    	   System.out.print(i.next() + ", ");
       }
       System.out.println("");
       
       //FOR EACH
       System.out.println("for loops elements in list1 :");
       for(int item: list1){
    	   System.out.print(item + ", ");
       }
       System.out.println("");
       
       //CONTAINTS
       System.out.println("list1.contains(100) :" + list1.contains(100));
       
       //CONTAINTS ALL
       System.out.println("list1.contains(100) :" + list1.containsAll(list2));
       
       //INDEX OF
       System.out.println("list1.indexOf(200) :" + list1.indexOf(200));
       
       //LAST INDEX OF
       System.out.println("list1.lastIndexOf(40):" + list1.lastIndexOf(40));
       
       //GET
       System.out.println("To get index value of required index : " + list1.get(0));
       
       //EQUALS
       List<Integer> list4 = new ArrayList<Integer>();
       list4.add(100);
       list4.add(200);
       list4.add(300);
       list4.add(400);
       System.out.println("list2.equals(list3) :" + list2.equals(list4));
       
       //RETAIN ALL -> Keeps the items in the list2, if it present in list1. Removes all other elements
       list1.retainAll(list2);
       
       //SET => To update an element in the required index
       list1.set(1,23);
       System.out.println("Update/Set the 1st index value as 23 :" + list1.indexOf(1));

       //SUBLIST => To use portion of the list
       List<Integer> subList1 = list3.subList(1, 3);
       System.out.println("Portion of list3 :");
       for(int item: subList1){
    	   System.out.print(item + ", ");
       }
       System.out.println("");
       
       //ARRAY TO ARRAYLIST
       System.out.println("Convert Array to ArrayList :");
       int[] array2 = new int[] {-1,-2,-3,-4};
       List negativeList = Arrays.asList(array2);  //???????????????
       for(Object item: negativeList){
    	   System.out.println("Arrays.asList :");
    	   System.out.print( item + ", ");
       }
       System.out.println("");
       //to get integer value from the converted list
       int val = (int) negativeList.get(0);
       
       //ARRAYLIST TO ARRAY
       System.out.println("Convert ArrayList To Array:");       
	   int[] myArray = list3.stream().mapToInt(arr -> arr).toArray();
       for(int item: myArray){
    	   System.out.print(item + ", ");
       }
       System.out.println("");
       
       //LIST OF LIST
       List<List<Integer>> listOfList = new ArrayList<>();
       listOfList.add(list2);
       
       //***************************** COLLECTION METHODS ********************************
       //SORT
       Collections.sort(list1);
       System.out.println("list1 after Collections.sort(list1):");
       for(int item: list1){
    	   System.out.print(item + ", ");
       }
       System.out.println("");
       
       //REVERSE
       Collections.reverse(list1);
       System.out.println("list1 after Collections.reverse(list1):");
       for(int item: list1){
    	   System.out.print(item + ", ");
       }
       System.out.println("");
  }
}
