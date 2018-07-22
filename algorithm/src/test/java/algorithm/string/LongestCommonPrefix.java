package algorithm.string;

import org.testng.Assert;
import org.testng.annotations.Test;

public class LongestCommonPrefix {

	@Test
	public void getLongestPrefix(){
		String[] inputs = new String[4];
		inputs[0]= "leets";
		inputs[1] = "leetcode";
		inputs[2] = "leeds";
		inputs[3] = "leeed";
		
		Assert.assertEquals(longestPrefix(inputs), "lee");
	}

	private String longestPrefix(String[] inputs){
		if(inputs == null || inputs.length == 0)
			return "";
		if(inputs.length == 1)
			return inputs[0];
		String prefix = inputs[0];
		
		for(int i=0; i< inputs.length; i++){
			while(inputs[i].indexOf(prefix) != 0){
				prefix = prefix.substring(0, prefix.length()-1);
				if(prefix.isEmpty())
					return "";
			}
		}		
		return prefix;
	}
}
