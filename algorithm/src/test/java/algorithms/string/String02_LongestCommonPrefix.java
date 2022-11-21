package algorithms.string;

import org.assertj.core.api.Assertions;
import org.testng.annotations.Test;

/*******
 * https://leetcode.com/problems/longest-common-prefix/
 * 
 * 
 * Write a function to find the longest common prefix string amongst an array of strings.

	If there is no common prefix, return an empty string "".

	Example 1:
	
	Input: strs = ["flower","flow","flight"]
	Output: "fl"
	Example 2:
	
	Input: strs = ["dog","racecar","car"]
	Output: ""
	Explanation: There is no common prefix among the input strings.
	 
	
	Constraints:
	
	0 <= strs.length <= 200
	0 <= strs[i].length <= 200
	strs[i] consists of only lower-case English letters.
 *
 */

public class String02_LongestCommonPrefix {

	@Test
	public void getLongestPrefix(){
		String[] inputs = new String[4];
		inputs[0]= "leets";
		inputs[1] = "leetcode";
		inputs[2] = "leeds";
		inputs[3] = "leeed";

		Assertions.assertThat(longestPrefix(inputs)).isEqualTo("lee");

		inputs = new String[1];
		inputs[0] = "one";
		Assertions.assertThat(longestPrefix(inputs)).isEqualTo("one");

		inputs = new String[3];
		inputs[0] = "one";
		inputs[1] = "two";
		inputs[2] = "three";
		Assertions.assertThat(longestPrefix(inputs)).isEqualTo("aw");
	}

	
	/*
	 * Time complexity : O(S)O(S) , 
	 * where S is the sum of all characters in all strings.
	 * In the worst case all nn strings are the same. 
	 * Space complexity : O(1)O(1). We only used constant extra space.
	 */
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
