package coreJava;

import java.util.HashSet;

import org.testng.annotations.Test;

public class SampleHashSet {
	
	@Test
	public void test(){
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
	     
	     /********* get "CC" object from hsetObj ******/
	     
	     if(hsetObj.contains("CC")){
	    	 for(Object obj: hsetObj){
	    		 if(obj == "CC"){
	    			 System.out.println("Here return the object "+ obj);
	    		 }
	    	 }
	     }
	     
	}

}
