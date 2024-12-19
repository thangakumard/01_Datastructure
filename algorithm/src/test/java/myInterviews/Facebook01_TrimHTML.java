package myInterviews;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.testng.annotations.Test;

/*******
 * 
 * To display HTML Content in webpage you may need to trim the given valid HTML string to the required length M.
 * Write an algorithm to trim the given HTML string to the required length M. 
 * Note: 
 * 	1. The given string is always valid
 *  2. All the HTLM tag has closing tag
 *  3. M <= length of input HTML string
 *
 */
public class Facebook01_TrimHTML {
	
	@Test
	public void Trim_HTML(){
		String input = "<html><body><h1>My First Heading</h1><p>My first paragraph.</p><b>My second paragraph.</b><i>My third paragraph.</i></body></html>";
		
		// the pattern we want to search for
	    //Pattern p = Pattern.compile("<code>(\\S+)</code>");
	    Pattern p = Pattern.compile("<([^\\s>/]+)");
	    Matcher m = p.matcher(input);

	    // if we find a match, get the group 
	    if (m.find()) {

	      // get the matching group
	      String codeGroup = m.group(1);
	      //String codeGroup1 = m;
	      
	      // print the group
	      System.out.format("'%s'\n", codeGroup);

	    }
	}
	
}
