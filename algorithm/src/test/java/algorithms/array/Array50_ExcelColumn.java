package algorithms.array;
import org.testng.annotations.Test;
/****
 https://leetcode.com/problems/excel-sheet-column-number/description/
 Given a column title as appear in an Excel sheet, return its corresponding column number.

For example:

    A -> 1
    B -> 2
    C -> 3
    ...
    Z -> 26
    AA -> 27
    AB -> 28 
    ...
Example 1:

Input: "A"
Output: 1
Example 2:

Input: "AB"
Output: 28
Example 3:

Input: "ZY"
Output: 701 
 
 
 */
public class Array50_ExcelColumn {

	
	@Test
	public void getExcelColumn(){
		System.out.println(convertToTitle(19630));
		System.out.println(convertToNumber("ABZZ"));
	}
	
	 public String convertToTitle(int n) {
	       
	       StringBuilder result = new StringBuilder();
	       while(n > 0){
	         
	           int reminder = n % 26;
	           
	           if(reminder == 0){
	               result.insert(0, "Z");
	               n = (n/26)-1;
	           }
	           else{
	               result.insert(0,(char)((reminder-1)+'A'));
	               n = n/26;
	           }
	           
	       }
	      return result.toString();
	    }
	 
	 public int convertToNumber(String s){
		 
		 char[] input = s.toCharArray();
		 int x =0, y=0;
		 int j = input.length-1;
		 for(int i=0; i < input.length; i++){
			 
			 y =  (int) Math.round(Math.pow(26, j)) ; j--;
			 if( i != input.length-1){
				 x += y * ((input[i] -'A') + 1);
			 }
			 else{
				 x += input[i] -'A' + 1;
			 }
		 }
		 
//		 for(int i=1;i < 800; i++ ){
//			 System.out.println((i)+ " : " + convertToTitle(i));
//		 }
		 
		 return x;
	 }
}
