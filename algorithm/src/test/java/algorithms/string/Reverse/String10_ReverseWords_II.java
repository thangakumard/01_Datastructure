package algorithms.string.Reverse;

import org.testng.annotations.Test;

public class String10_ReverseWords_II {
	
	@Test
	 private void test() {
		 String s ="We love Java";
		 char[] c = s.toCharArray();
		 reverseWords(c);
		 System.out.println(c);
	 }
	public  void reverseWords (char[] sentence) {
	      int start = 0;
	      for(int end =0; end < sentence.length; end++){
	        if(sentence[end] == ' '){
	           reverse(sentence,start,end-1);
	           start = end+1;
	        }
	      }

	      reverse(sentence,start,sentence.length-1);
	      System.out.println(sentence);
	      reverse(sentence,0,sentence.length-1);
	  }   
	  private  void reverse(char[] input,int start, int end){
	      while(start <= end){
	        char temp = input[start];
	        input[start] = input[end];
	        input[end] = temp;
	        start++;
	        end--;
	      }
	  }

}
