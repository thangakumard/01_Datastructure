package algorithms.string;
import org.testng.annotations.Test;
import java.util.*;
import org.testng.Assert;


public class String13_ZigZagConversion {

	
	@Test
	public void Test(){
		Assert.assertEquals(convert("PAYPALISHIRING",2) , "PYAIHRNAPLSIIG");
		Assert.assertEquals(convert("AB",1) , "AB");
	}
	
	public String convert(String str, int n) {
        
		  // Corner Case (Only one row) 
		        if (n == 1)  
		        { 
		            return str;
		        } 
		        char[] str1 = str.toCharArray(); 
		  
		        // Find length of string 
		        int len = str.length(); 
		  
		        // Create an array of 
		        // strings for all n rows 
		        String[] arr = new String[n]; 
		        Arrays.fill(arr, ""); 
		        
		  
		        // Initialize index for 
		        // array of strings arr[] 
		        int row = 0; 
		        boolean down = true; // True if we are moving  
		        // down in rows, else false 
		  
		        // Travers through 
		        // given string 
		        for (int i = 0; i < len; ++i)  
		        { 
		            // append current character 
		            // to current row 
		            arr[row] += (str1[i]); 
		  
		            // If last row is reached, 
		            // change direction to 'up' 
		            if (row == n - 1)  
		            { 
		                down = false; 
		            }  
		              
		            // If 1st row is reached,  
		            // change direction to 'down' 
		            else if (row == 0)  
		            { 
		                down = true; 
		            } 
		  
		            // If direction is down,  
		            // increment, else decrement 
		            if (down) 
		            { 
		                row++; 
		            }  
		            else 
		            { 
		                row--; 
		            } 
		        } 
		  
		        StringBuilder sb = new StringBuilder();
		        // Print concatenation 
		        // of all rows 
		        for (int i = 0; i < n; ++i)  
		        { 
		            sb.append(arr[i]); 
		        } 
		        
		        return sb.toString();
		    } 
}
