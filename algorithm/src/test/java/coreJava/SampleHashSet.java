package coreJava;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Spliterator;

import org.testng.annotations.Test;

public class SampleHashSet {
	
	@Test
	public void test(){
		
		/********************************
		ArrayList maintains the order of the object in which they are inserted 
		while HashSet is an unordered collection and doesn’t maintain any order.
		************************************/

		class User {
			public int userId = 0;
			public String userName = "";
		}

		User u1 = new User();
		u1.userId = 1;
		u1.userName = "user1";

		User u2 = new User();
		u2.userId = 2;
		u2.userName = "user2";

		User u3 = new User();
		u1.userId = 1;
		u1.userName = "user1";

		HashSet<User> setUser = new HashSet<>();
		setUser.add(u1);
		setUser.add(u2);
		setUser.add(u3);

		//Hash Set handles unique value objects
		for(User u : setUser){
			System.out.println(u.userName);
		}

		//Method 1: add(E e)
		HashSet<String> hset = new HashSet<String>();
		 
	     //add elements to HashSet
	     hset.add("AA");
	     hset.add("BB");
	     hset.add("CC");
	     hset.add("DD");
	     
	 
	     // Displaying HashSet elements[
	     System.out.println("HashSet String Collection contains: ");
	     for(String temp : hset){
	        System.out.println(temp);
	     }	     
	     
	     System.out.println("*******************");
	     
	     HashSet<Object> hsetObj = new HashSet<Object>();
		 
	     //add elements to HashSet
	     hsetObj.add(null);
	     hsetObj.add(null); // This record will be ignored and it will returns false
	     hsetObj.add("BB");
	     hsetObj.add("CC");
	     hsetObj.add("DD");
	 
	     // Displaying HashSet elements
	     System.out.println("HashSet Object collection contains: ");
	     for(Object temp : hsetObj){
	        System.out.println(temp);
	     }
	     
	   //Method 2: clear()
	    hset.clear(); // Removes all the elements from the set
	    
	    //Method 3: clone() //Returns a shallow copy of this HashSet instance: the elements themselves are not cloned.
	     hset.add("AA");
	     hset.add("BB");
	     HashSet hsetCloned = new HashSet();
	     hsetCloned = (HashSet) hset.clone();
	     // Displaying HashSet elements of hsetCloned
	     System.out.println("*******************");
	     System.out.println("From the cloned object: ");
	     for(Object temp : hsetCloned){
	        System.out.println(temp);
	     }	
	    
	   //Method 4: contains(Object o)
	     /********* get "CC" object from hsetObj ******/
	     
	     if(hsetObj.contains("CC")){
	    	 for(Object obj: hsetObj){
	    		 if(obj == "CC"){
	    			 System.out.println("Here return the object "+ obj);
	    		 }
	    	 }
	     }
	     
	   //Method 5: isEmpty()
	     System.out.println("*******************");
	     System.out.println("hset.isEmpty() :"+ hset.isEmpty());
	     
	   //Method 6: iterator()
	     Iterator value = hset.iterator();
	     System.out.println("Hashset using Iterator:");
	     while(value.hasNext()) {
	    	 System.out.println(value.next());
	     }
	     //Method 7: remove(Object o)
	     hset.remove("AA");
	     hset.remove("XYZ");
	     System.out.println("After removing AA from the set:");
	     for(Object temp : hset){
		        System.out.println(temp);
		     }
	     //Method 8: Size()
	     System.out.println("Hashset Size:" + hset.size());

	     //Method 9: spliterator()
	     Spliterator<String> words = hset.spliterator();
	     System.out.println("spliterator()");
	     words.forEachRemaining((w) -> System.out.println(w)) ;
	     
	     
	     /*********** SET methods ***********/
	     //Set to Array
	     String[] arrWords = new String[hset.size()];
	     arrWords = hset.toArray(arrWords);
	     
	   //Set to ArrayList
	     List<String> aList1 = new ArrayList<String>(hset); 
	     
	     List<String> aList2 = new ArrayList<String>(); 
	     aList2.addAll(hset); 
	     
	     HashSet<String> setFromList = new HashSet<String>(aList1);
	     
	     /**** Compare 2 sets */
	  // Creating object of Set 
	        Set<String> arrset1 = new HashSet<String>(); 
	  
	        // Populating arrset1 
	        arrset1.add("A"); 
	        arrset1.add("B"); 
	        arrset1.add("C"); 
	        arrset1.add("D"); 
	        arrset1.add("E"); 
	  
	        // print arrset1 
	        System.out.println("First Set: "
	                           + arrset1); 
	  
	        // Creating another object of Set 
	        Set<String> arrset2 = new HashSet<String>(); 
	  
	        // Populating arrset2 
	        arrset2.add("A"); 
	        arrset2.add("B"); 
	        arrset2.add("C"); 
	        arrset2.add("D"); 
	        arrset2.add("E"); 
	  
	        // print arrset2 
	        System.out.println("Second Set: "
	                           + arrset2); 
	  
	        // comparing first Set to another 
	        // using equals() method 
	        System.out.println("Are First Set and Second Set are equal : " + arrset1.equals(arrset2)); 
	        
	        /**** Sort Sets */
	        List<String> list = new ArrayList<>(arrset1);
	        Collections.sort(list);
	     
	        /****** ArrayList to Set ****/
	        List<Integer> lstNumbers = new ArrayList<Integer>();
	        lstNumbers.add(10);
	        lstNumbers.add(20);
	        lstNumbers.add(40);
	        lstNumbers.add(100);
	        lstNumbers.add(50);
	        lstNumbers.add(50);
	        lstNumbers.add(50);
	        HashSet<Integer> setNumbers = new HashSet<Integer>();
	        setNumbers.addAll(lstNumbers);
	        
	        Iterator nums = setNumbers.iterator();
		    System.out.println("ArrayList to HashSet:");
		    while(nums.hasNext()) {
		    	 System.out.println(nums.next());
		    }
		    
		    /****** Array to Set ****/
		    Integer[] arrNumbers = new Integer[] {10,10,20,20,30,30};
		    HashSet<Integer> setNumArr= new HashSet<>();
		    setNumArr.addAll(Arrays.asList(arrNumbers));
	        
	        Iterator nums1 = setNumArr.iterator();
		    System.out.println("Array to HashSet:");
		    while(nums1.hasNext()) {
		    	 System.out.println(nums1.next());
		    }
	}

}
