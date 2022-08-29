package coreJava.collections.map;

import java.util.*;

import org.testng.annotations.Test;

public class Map01_HashMap {
	
	@Test
	private void test() {
		iterate_map();
	}
	
	public  void iterate_map() 
    { 
        Map<String,String> gfg = new HashMap<String,String>(); 
      
        // enter name/url pair 
        gfg.put("GFG", "geeksforgeeks.org"); 
        gfg.put("Practice", "practice.geeksforgeeks.org"); 
        gfg.put("Code", "code.geeksforgeeks.org"); 
        gfg.put("Quiz", "quiz.geeksforgeeks.org"); 
          
        //OPTION : 1
        // using for-each loop for iteration over Map.entrySet() 
        for (Map.Entry<String,String> entry : gfg.entrySet())  {
            System.out.println("Key = " + entry.getKey() + 
                             ", Value = " + entry.getValue()); 
        }
        
        System.out.println("*******************");
        
        //OPTION : 2
        // using keySet() for iteration over keys 
        for (String name : gfg.keySet())  
            System.out.println("key: " + name); 
          
        System.out.println("*******************");
       
        //OPTION : 3
        // using values() for iteration over keys 
        for (String url : gfg.values())  
            System.out.println("value: " + url); 
        
        System.out.println("*******************");
        
        // OPTION : 4
        // using iterators 
        Iterator<Map.Entry<String, String>> itr = gfg.entrySet().iterator(); 
          
        while(itr.hasNext()) 
        { 
             Map.Entry<String, String> entry = itr.next(); 
             System.out.println("Key = " + entry.getKey() +  
                                 ", Value = " + entry.getValue()); 
        } 
        
        // OPTION : 5
        // forEach(action) method to iterate map 
        gfg.forEach((k,v) -> System.out.println("Key = "
                + k + ", Value = " + v)); 
    }

    @Test
    public void GetValueTest(){
        Map<String,String> mapInput = new HashMap<>();
        System.out.println(mapInput.getOrDefault("one", "NOT FOUND"));
        System.out.println(" mapInput.containsKey(\"one\") : " + mapInput.containsKey("one"));

        System.out.println(mapInput.computeIfAbsent("one", (x) -> "NOT FOUND"));
        System.out.println(" mapInput.containsKey(\"one\") : " + mapInput.containsKey("one"));
    }

}
