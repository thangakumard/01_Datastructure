package algorithms.string.search;

import java.util.Arrays;

import org.testng.Assert;
import org.testng.annotations.Test;



/********
 * 
    https://leetcode.com/problems/find-and-replace-in-string/description/
    
    Example 1:

	Input: S = "abcd", indexes = [0,2], sources = ["a","cd"], targets = ["eee","ffff"]
	Output: "eeebffff"
	Explanation: "a" starts at index 0 in S, so it's replaced by "eee".
	"cd" starts at index 2 in S, so it's replaced by "ffff".
	Example 2:
	
	Input: S = "abcd", indexes = [0,2], sources = ["ab","ec"], targets = ["eee","ffff"]
	Output: "eeecd"
	Explanation: "ab" starts at index 0 in S, so it's replaced by "eee". 
	"ec" doesn't starts at index 2 in the original S, so we do nothing.
	Notes:
	
	0 <= indexes.length = sources.length = targets.length <= 100
	0 < indexes[i] < S.length <= 1000
	All characters in given inputs are lowercase letters.
 *
 */
public class FindAndReplace {
	
	@Test
	public void replaceString(){
		
		Assert.assertEquals(findAndReplace("abcd",new int[]{0,2},new String[]{"a", "cd"},new String[]{"eee", "ffff"}),"eeebffff");
	}
	
	private String findAndReplace(String S, int[] indexes, String[] sources, String[] targets){
		int length = S.length();
		int[] indexInvert = new int[length];
		Arrays.fill(indexInvert, -1);
		StringBuilder sb = new StringBuilder(S);
		
		for(int i=0; i< indexes.length; i++){
			indexInvert[indexes[i]] = i;	
		}
		for(int i = length-1; i >=0 ; i--){
			
			if(indexInvert[i] < 0)
				continue;
			int j = indexInvert[i];
			if(S.substring(indexes[j]).startsWith(sources[j])){
				sb.replace(indexes[j], indexes[j] + sources[j].length(),targets[j]);
			}
		}
		return sb.toString();
	}
}
