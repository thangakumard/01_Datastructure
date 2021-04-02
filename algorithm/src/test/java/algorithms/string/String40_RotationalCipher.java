package algorithms.string;

import org.testng.annotations.Test;

public class String40_RotationalCipher {

	@Test
	private void test() {
		String input = "Y";
		System.out.println(rotationalCipher(input, 4));
	}

	private String rotationalCipher(String input, int rotationFactor) {
		// Write your code here
		int charRotate = rotationFactor % 26;
	    int numRotate = rotationFactor % 10; 
	    StringBuilder sb = new StringBuilder();
	    for(char c: input.toCharArray()){
	      if(Character.isDigit(c)){
	        sb.append((Integer.parseInt(c+"") + numRotate) % 10);
	      }
	      else if(Character.isAlphabetic(c)){
	    	  int i = ((int)c + charRotate);
	    	  if(Character.isUpperCase(c))
	    	  {
	    		  int j = (int) ( (i % (int) 'Z') < 65 ? 'Z' - (i % (int) 'Z') : (i % (int) 'Z'));
	    		  sb.append((char)(j));
	    	  }else {
	    		  int j = (int) ( (i % (int) 'z') < 97 ? 'z' - (i % (int) 'z')  : (i % (int) 'z'));
	    		  sb.append((char)(j));
	    		 }
	      }else{
	        sb.append(c);
	      }
	    }
	    return sb.toString();
	}
}
