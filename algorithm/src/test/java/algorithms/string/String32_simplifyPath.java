package algorithms.string;

import java.util.Stack;

import org.testng.annotations.Test;

public class String32_simplifyPath {
	
	@Test
	private void test() {
//		System.out.println(simplifyPath("/home/"));
//		System.out.println(simplifyPath("/home/../"));
		System.out.println(simplifyPath("/a/./b/../../c/d/"));
	}

	    public String simplifyPath(String path) {

	        // Initialize a stack
	        Stack<String> stack = new Stack<String>();
	        String[] components = path.split("/");

	        // Split the input string on "/" as the delimiter
	        // and process each portion one by one
	        for (String directory : components) {

	            // A no-op for a "." or an empty string
	            if (directory.equals(".") || directory.isEmpty()) {
	                continue;
	            } else if (directory.equals("..")) {

	                // If the current component is a "..", then
	                // we pop an entry from the stack if it's non-empty
	                if (!stack.isEmpty()) {
	                    stack.pop();
	                }
	            } else {

	                // Finally, a legitimate directory name, so we add it
	                // to our stack
	                stack.add(directory);
	            }
	        }

	        // Stich together all the directory names together
	        StringBuilder result = new StringBuilder();
	        for (String dir : stack) {
	            result.append("/");
	            result.append(dir);
	        }

	        return result.length() > 0 ? result.toString() : "/" ;
	    }
	
}
