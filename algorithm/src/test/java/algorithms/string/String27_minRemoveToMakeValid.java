package algorithms.string;

import java.util.HashSet;
import java.util.Stack;

import org.testng.Assert;
import org.testng.annotations.Test;


public class String27_minRemoveToMakeValid {
	
	@Test
	private void test() {
		Assert.assertEquals("lee(t(c)o)de", minRemoveToMakeValid("lee(t(c)o)de)")); 
		Assert.assertEquals("ab(c)d", minRemoveToMakeValid("a)b(c)d")); 
		Assert.assertEquals("", minRemoveToMakeValid("))(("));
		Assert.assertEquals("a(b(c)d)", minRemoveToMakeValid("(a(b(c)d)"));
	}
	
	
	public String minRemoveToMakeValid(String s) {
        Stack<Integer> stackChar = new Stack<Integer>();
        HashSet<Integer> indexToRemove = new HashSet<Integer>();
        
        for(int i=0; i < s.length(); i++){
            if(s.charAt(i) == '(') stackChar.push(i);
            else if(s.charAt(i) == ')') {
                if(stackChar.isEmpty()){
                    indexToRemove.add(i);
                }else{
                    stackChar.pop();
                }
            }
        }
        
        while(!stackChar.isEmpty()){
            indexToRemove.add(stackChar.pop());
        }
        StringBuilder sb = new StringBuilder();
        for(int i=0; i < s.length(); i++){
            if(!indexToRemove.contains(i)){
                sb.append(s.charAt(i));
            }
        }
        
        return sb.toString();
    }
}
