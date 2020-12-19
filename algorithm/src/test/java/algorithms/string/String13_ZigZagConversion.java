package algorithms.string;
import org.testng.annotations.Test;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;
public class String13_ZigZagConversion {

	
	@Test
	public void Test(){
		Assert.assertEquals(getZigZag("PAYPALISHIRING",2) , "PYAIHRNAPLSIIG");
		Assert.assertEquals(getZigZag("AB",1) , "AB");
	}
	 public String getZigZag(String s, int numRows) {
	        
	        if(s == null || s.length() == 0 || s.length() < numRows )
	            return s;
	        
	        ArrayDeque<Character> queue = new ArrayDeque<Character>();
	        List<ArrayDeque<Character>> lstQue = new ArrayList<>();
	        int n = 0;
	        int i = 0;
	        boolean zig = true;
	        
	        for(int j=0; j< numRows; j++){
	            queue = new ArrayDeque<Character>();
	            lstQue.add(queue);
	        }
	        
	        while(n < s.length()){            
	            if(i > -1 && i < numRows){	            
	                if(zig){
	                    lstQue.get(i).addLast(s.charAt(n));
	                    i++;                    
	                }else{
	                    lstQue.get(i).addLast(s.charAt(n));
	                    i--;
	                }                
	                n++;
	            }
	            else{
	                zig = !zig;
	                if(numRows == 1){
	                	i = 0;
	                }
	                else{
		                if(i < 0) {
		                    i = 1;
		                }
		                else{
		                    i = numRows -2;
		                }
	                }
	            }            
	        }
	        
	        StringBuilder result = new StringBuilder();
	        ArrayDeque<Character> resultQueue = new ArrayDeque<Character>();
	        for(int j =0 ; j <numRows; j++){            
	            resultQueue = lstQue.get(j);
	            while(!resultQueue.isEmpty()){
	                result.append(resultQueue.removeFirst());
	            }
	        }
	        return result.toString();
	    }
}
