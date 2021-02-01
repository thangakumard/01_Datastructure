package algorithms.string;

import org.testng.annotations.Test;

public class String31_MultiplyString {
	
	@Test
	 private void test() {
		System.out.println(multiply("99","9"));
	 }

	 public String multiply(String num1, String num2) {
	        if(num1.equals("0") || num2.equals("0")) return "0";
	        int m=num1.length();
	        int n=num2.length();
	        int[] result=new int[m+n];
	        for(int i=m-1;i>=0;i--){
	            for(int j=n-1;j>=0;j--){
	                int product=(num1.charAt(i)-'0')*(num2.charAt(j)-'0');
	                int sum=result[i+j+1]+product;
	                result[i+j]=result[i+j]+sum/10;
	                result[i+j+1]=sum%10;
	            }
	        }
	       
	        StringBuilder sb=new StringBuilder();
	        for(int val : result){
	            if(sb.length()!=0 || val!=0){
	                sb.append(val);
	            }
	        }
	        return (sb.length()==0) ? "0" : sb.toString();
	        
	        
	    }
}
