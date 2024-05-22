package coreJava;

import org.assertj.core.api.Assertions;
import org.testng.annotations.Test;

public class SampleString {

	@Test
	public void String_compareTo(){
		
		/*********
		 Compares two strings lexicographically. The comparison is based on the Unicode value of each character in the strings. 
		 The character sequence represented by this String object is compared lexicographically to the character sequence represented by the argument string. 
		 The result is a negative integer if this String object lexicographically precedes the argument string. 
		 The result is a positive integer if this String object lexicographically follows the argument string. 
		 The result is zero if the strings are equal; compareTo returns 0 exactly when the equals(Object) method would return true.
		 This is the definition of lexicographic ordering. If two strings are different, then either they have different characters at some index that is a valid index 
		 for both strings, or their lengths are different, or both. If they have different characters at one or more index positions, let k be the smallest such index; 
		 then the string whose character at position k has the smaller value, as determined by using the < operator, lexicographically precedes the other string.
		 In this case, compareTo returns the difference of the two character values at position k in the two string -- that is, the value:

 		this.charAt(k)-anotherString.charAt(k)
 
		If there is no index position at which they differ, then the shorter string lexicographically precedes the longer string. In this case, compareTo returns the difference of the lengths of the strings -- that is, the value:
 		this.length()-anotherString.length()
		 */
		
		String s1 = "abcd";
		String s2 = "abcd";
		
		System.out.println("If S1 == S2 : " + s1.compareTo(s2));
		Assertions.assertThat(s1.compareTo(s2)).isEqualTo(0);
		
		s1="abcd";
		s2="bcde";
		
		System.out.println("If abcd.compareTo(bcde) : " + s1.compareTo(s2));
		// Char difference at 0th index is a-b. S1 is one char value less than S2.So it will return -1
		Assertions.assertThat(s1.compareTo(s2)).isEqualTo(-1);

		s1="bcde";
		s2="abcd";
		//s1 > s2 => returns 1
		// Char difference at 0th index is b-c. S1 is one char value greater than S2.So it will return 1
		System.out.println("If bcde.compareTo(abcd) : " + s1.compareTo(s2));// Char difference at kth(0th) index is b-a So it will return 1
		Assertions.assertThat(s1.compareTo(s2)).isEqualTo(1);

		s1="abcd";
		s2="ccde";
		// Char difference at kth(0th) index is a-c.S1 is two char value less than S2.So it will return -2. So it will return -2
		System.out.println("If abcd.compareTo(ccde) : " + s1.compareTo(s2));
		Assertions.assertThat(s1.compareTo(s2)).isEqualTo(-2);

		s1="abcd";
		s2="ABCD";
		System.out.println("If abcs.compareTo(ABCD) : " + s1.compareTo(s2));
		// Char difference at kth(0th) index is a-A (97-65). So it will return 32
		Assertions.assertThat(s1.compareTo(s2)).isEqualTo(32);


		System.out.println("CodePointAt(0) : " + s1.codePointAt(0));
		System.out.println("CodePointAt(0) : " + s2.codePointAt(0));
		String_compareToIgnoreCase();
		//String_substring();
//		String_Print_Words();
//		String_Print_Words_inReverse();
		
		String split = "Good I am";
		String[] afterSplit = split.split("\\s",2);
	}
	
	@Test
	public void String_compareToIgnoreCase(){
		String s1="abcd";
		String s2="ABCD";
		
		System.out.println("If abcd.compareToIgnoreCase(ABCD) : " + s1.compareToIgnoreCase(s2));// Char difference at kth(0th) index is a-a So it will return 0
	}
	
	@Test
	public void String_substring(){
		/***********
			Returns a new string that is a substring of this string. 
			The substring begins at the specified beginIndex and extends to the character at index endIndex - 1. 
			Thus the length of the substring is endIndex-beginIndex.
			Examples:

 			"hamburger".substring(4, 8) returns "urge"
 			"smiles".substring(1, 5) returns "mile"
		 */
		String s1="abcd";		
		
		System.out.println("abcd.substring(0) : " + s1.substring(0));
		System.out.println("abcd.substring(1) : " + s1.substring(1));
		System.out.println("abcd.substring(1,s1.length()) : " + s1.substring(1,s1.length()));
		System.out.println("abcd.substring(2) : " + s1.substring(2));
		System.out.println("abcd.substring(3) : " + s1.substring(3));
		System.out.println("abcd.substring(4) : " + s1.substring(4));
		
		System.out.println("abcd.substring(0,1) : " + s1.substring(0,1));

		String math = "2*(5+5)";
		System.out.println("math.substring(2,3) : " + math.substring(3,6));

	}
	
}
