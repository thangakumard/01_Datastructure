package algorithms.stack;

import org.assertj.core.api.Assertions;
import org.testng.annotations.Test;

import java.util.Stack;
/******
 https://leetcode.com/problems/simplify-path/
 Given a string path, which is an absolute path (starting with a slash '/') to a file
 or directory in a Unix-style file system, convert it to the simplified canonical path.

 In a Unix-style file system, a period '.' refers to the current directory,
 a double period '..' refers to the directory up a level,
 and any multiple consecutive slashes (i.e. '//') are treated as a single slash '/'.
 For this problem, any other format of periods such as '...' are treated as file/directory names.

 The canonical path should have the following format:

 The path starts with a single slash '/'.
 Any two directories are separated by a single slash '/'.
 The path does not end with a trailing '/'.
 The path only contains the directories on the path from the root directory to the target file or directory (i.e., no period '.' or double period '..')
 Return the simplified canonical path.



 Example 1:

 Input: path = "/home/"
 Output: "/home"
 Explanation: Note that there is no trailing slash after the last directory name.
 Example 2:

 Input: path = "/../"
 Output: "/"
 Explanation: Going one level up from the root directory is a no-op, as the root level is the highest level you can go.
 Example 3:

 Input: path = "/home//foo/"
 Output: "/home/foo"
 Explanation: In the canonical path, multiple consecutive slashes are replaced by a single one.
 Example 4:

 Input: path = "/a/./b/../../c/"
 Output: "/c"


 Constraints:

 1 <= path.length <= 3000
 path consists of English letters, digits, period '.', slash '/' or '_'.
 path is a valid absolute Unix path.
 *
 */

public class Stack03_SimplifyPath {

    @Test
    private void simplifyPathTest() {
        Assertions.assertThat(simplifyPath("/home/")).isEqualTo("/home");
        Assertions.assertThat(simplifyPath("/home/../")).isEqualTo("/");
        Assertions.assertThat(simplifyPath("/a/./b/../../c/d/")).isEqualTo("/c/d");
    }
    /****
     1. Split string based on /
     2. If the directory is empty string or '.' ignore the directory
     3. If the character is .. pop() a value from stack
     4. else add the directory to the stack
     5. Create a stringbuilder and append all the values in stack split by /
     6. If stringbuilder is empty return / alse return the stringbuilder as String
     */
    public String simplifyPath(String path) {

        // Initialize a stack
        Stack<String> stack = new Stack<>();
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

        /*** stack in the for loop will read the stack from bottom to top
         * In an interview situation, its likely that the interviewer would not want you to iterate over a stack using a for loop.
         * It defeats the whole purpose of using a stack.
         * So in situations like these, the smallest change which works on most of the cases is
         * to insert at index 0 of the StringBuilder instead of appending.

         // Stich together all the directory names together
         for (String dir : stack) {
         result.append("/");
         result.append(dir);
         }
         */
        StringBuilder result = new StringBuilder();
        while(!stack.isEmpty()){
            result.insert(0, stack.pop());
            result.insert(0, "/");
        }
        return result.length() > 0 ? result.toString() : "/" ;
    }
}
