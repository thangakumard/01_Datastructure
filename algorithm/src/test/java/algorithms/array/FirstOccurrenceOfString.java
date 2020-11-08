package algorithms.array;

import org.testng.Assert;
import org.testng.annotations.Test;

public class FirstOccurrenceOfString {

	@Test
	public void testStrings()
	{
		Assert.assertEquals(strStrFirstOccurrence("ramkumarram", "kumar" ), 3);
	}


//Returns the index of the first occurrence of needle in haystack, or -1 if needle is not part of haystack.
public int strStrFirstOccurrence(String haystack, String needle) {
    if(haystack==null || needle==null)    
        return 0;
 
    if(needle.length() == 0)
        return 0;
 
    for(int i=0; i<haystack.length(); i++){
        if(i + needle.length() > haystack.length())
            return -1;
 
        int m = i;
        for(int j=0; j<needle.length(); j++){
            if(needle.charAt(j)==haystack.charAt(m)){
                if(j==needle.length()-1)
                    return i;
                m++;
            }else{
                break;
            }
 
        }    
    }   
 
    return -1;
}
}
