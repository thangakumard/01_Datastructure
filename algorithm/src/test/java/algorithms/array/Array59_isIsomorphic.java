package algorithms.array;

import org.testng.annotations.Test;

public class Array59_isIsomorphic {
	
	@Test
	public void test() {
		System.out.println(isIsomorphic("egg","add"));
		
	}
	
	public boolean isIsomorphic(String s, String t) {
	     
        if(s.length() != t.length())
            return false;
        int[] m1 = new int[256];
        int[] m2 = new int[256];
        
        for(int i=0; i< s.length(); i++){
           if(m1[s.charAt(i)] != m2[t.charAt(i)])
               return false;
            m1[s.charAt(i)] = i + 1;
            m2[t.charAt(i)] = i + 1;
        }
        return true;
    }

}
