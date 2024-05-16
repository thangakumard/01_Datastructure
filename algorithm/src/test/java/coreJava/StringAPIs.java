package coreJava;

import org.testng.annotations.Test;

import java.util.Locale;

import org.testng.Assert;

public class StringAPIs {

	@Test
	public void compareString(){
	     String str = "abc";
	     char data[] = {'a', 'b', 'c'};
	     String str1 = new String(data);
	 
	     System.out.println(str.compareTo(str1));
	     Assert.assertEquals(str, str1);
	}
	
	@Test
	public void stringAPIs(){
		String input ="I am learning Java";
		
		System.out.println("charAt(int�index) :" + input.charAt(0));
		
		System.out.println("codePointAt(int�index) :" + input.codePointAt(0));
		
		System.out.println("codePointBefore(int�index):" + input.codePointBefore(1));
		
		System.out.println("codePointCount(int�beginIndex, int�endIndex):" + input.codePointCount(0,2));
		System.out.println("");
	}
	
	@Test
	public void stringCompare(){
		System.out.println("********* (INT) STRING COMPARE ***********");
		System.out.println(" 1. -Ve result => Calling string will come first lexicograghically.");
		System.out.println(" 2. +Ve result => Arugument string will come first lexicograghically.");
		System.out.println(" 3. 0 result => Both strings are lexocographically equal.");
		
		String value = "ABC";
		System.out.println("Compare: ABC with abc : " + value.compareTo("abc"));
		System.out.println("Compare: abc with ABC : " + "abc".compareTo("ABC"));
		System.out.println("Compare: ABC with Abc : " + value.compareTo("Abc"));
		System.out.println("Compare: ABC with ABC : " + value.compareTo("ABC"));

		value = "Apple";
		System.out.println("Compare: Apple with Banana : " + value.compareTo("Banana"));
		System.out.println("Compare: Apple with apple : " + value.compareTo("apple"));
		
		System.out.println("Compare: Apple with 123Apple : " + value.compareTo("123Apple") + ".Means 123Apple comes first.");
		
		System.out.println("********* (INT)STRING COMPARE IGNORE CASE***********");
		System.out.println("Compare: Apple with apple : " + value.compareToIgnoreCase("apple"));
		System.out.println("Compare: Apple with APPLE : " + value.compareToIgnoreCase("APPLE"));
		System.out.println("");
		
		System.out.println("********* (BOOLEAN)STRING COMPARE - CHAR ***********");
		System.out.println("contentEquals(CharSequence�s): Apple with apple : " + value.contentEquals("apple"));
		System.out.println("contentEquals(CharSequence�s): Apple with Apple : " + value.contentEquals("Apple"));
		System.out.println("");
		
		System.out.println("********* (BOOLEAN)STRING COMPARE - StringBuffer ***********");
		StringBuffer strBuffer = new StringBuffer();
		strBuffer.append("apple");
		System.out.println("contentEquals(StringBuffer�sb): Apple with apple : " + value.contentEquals(strBuffer));
		System.out.println("contentEquals(StringBuffer�sb): Apple with Apple : " + value.contentEquals(strBuffer));
	
		System.out.println("");		
		System.out.println("********* (BOOLEAN)STRING CONTAINS ***********");
		System.out.println("contains(CharSequence�s): Apple with apple : " + value.contains("App"));
	
		System.out.println("");		
		System.out.println("********* (BOOLEAN)equals(Object�anObject) ***********");
		System.out.println("equals(Object�anObject): Apple with Apple : " + value.equals("Apple"));
		
		System.out.println("");		
		System.out.println("********* (BOOLEAN)equalsIgnoreCase(String�anotherString) ***********");
		System.out.println("equals(Object�anObject): Apple with apple : " + value.equalsIgnoreCase("apple"));
		
		System.out.println("");		
		System.out.println("********* (INT)indexOf(CHAR) ***********");
		System.out.println("value.indexOf('p'): First occurance of : " + value.indexOf('p'));
		
		System.out.println("");		
		System.out.println("********* (INT)indexOf(CHAR, INT FROM_INT) ***********");
		System.out.println("value.indexOf('p',value.indexOf('p')): 2nd occurance of : " + value.indexOf('p',value.indexOf('p')+1));
	
		System.out.println("");		
		System.out.println("********* STATIC STRING join(CharSequence delimiter, CharSequence... elements) ***********");
		System.out.println("String.join(\" < \", \"Four\", \"Five\", \"Six\", \"Seven\") : " + String.join(" < ", "Four", "Five", "Six", "Seven"));
		
		System.out.println("");		
		System.out.println("********* (INT)lastindexOf(CHAR) ***********");
        String Str = new String("Welcome to geeksforgeeks"); 
        System.out.print("Found Last Index of g at : "); 
        System.out.println(); 
		System.out.println("Welcome to geeksforgeeks: Last occurance index of g at : " + Str.lastIndexOf('g'));
		
		System.out.println("");		
		System.out.println("********* (INT)lastindexOf(CHAR, FROMINDEX) ***********");
        Str = new String("Welcome to geeksforgeeks"); 
        System.out.print("Found Last Index of g at : "); 
        System.out.println(Str.lastIndexOf('g', 15));
        
        System.out.println("");		
		System.out.println("********* (BOOLEAN)isEmpty() ***********");
        System.out.println("\"  \".isEmpty():" +"  ".isEmpty());

        
//        System.out.println("");		
//        System.out.println("********* (BOOLEAN)isBlank() ***********");
//        System.out.println("\"  \".isBlank():" +"  ".isBlank());
        
        System.out.println("");		
        System.out.println("********* (STRING)replace(char oldChar, char newChar) ***********");
        Str = "oooooo-hhhh-oooooo";  
        String rs = Str.replace("h","s"); // Replace 'h' with 's'  
        System.out.println("Original String :" + Str);  
        System.out.println("After replacement :" + rs);  

        System.out.println("");		
        System.out.println("********* (STRING)replaceAll(String regex, String replacement) ***********");
        Str = "how to do in java provides java tutorials";
        String newStr = Str.replaceAll("\\s", "");
        System.out.println("(how to do in java provides java tutorials).eplaceAll(\"\\\\s\", \"\") : "+newStr);
        
        System.out.println("");		
        System.out.println("********* (STRING) replaceFirst(String regex, String replacement) ***********");
        Str = "Java says hello world. Java String tutorial";
        //Replace first occurrence of substring "Java" with "JAVA"
        System.out.println("Str.replaceFirst(\"Java\", \"JAVA\") : " + Str.replaceFirst("Java", "JAVA"));
        //Replace first occurrence of substring "a" with "A"
        Str.replaceFirst("[a]", "A");
        System.out.println("Str.replaceFirst(\"[a]\", \"A\") : " + Str.replaceFirst("[a]", "A"));
        
        System.out.println("");		
        System.out.println("********* (STRING[]) split(String regex) ***********");
        Str = "geekss@for@geekss"; 
        String[] arrOfStr = Str.split("@"); 
        System.out.println("(\"geekss@for@geekss\").split(\"@\") : ");
        for (String a : arrOfStr) 
            System.out.println(a); 
        
        System.out.println("");		
        System.out.println("********* (STRING[]) split(String regex, int limit) ***********");
        Str = "geekss@for@geekss"; 
        arrOfStr = Str.split("@", 2); 
        System.out.println("(\"geekss@for@geekss\").split(\"@\", 2) : ");
        for (String a : arrOfStr) 
            System.out.println(a); 
        
        
        System.out.println("");		
        System.out.println("********* (BOOLEAN) startsWith(String prefix) ***********");
        Str = "This is just a sample string";  
        //checking whether the given string starts with "This"
        System.out.println("(\"This is just a sample string\").startsWith(\"This\") :"+ Str.startsWith("This"));  
		
        //checking whether the given string starts with "Hi"
        System.out.println("(\"This is just a sample string\").startsWith(\"Hi\") :"+ Str.startsWith("Hi"));  

        System.out.println("");		
        System.out.println("********* (BOOLEAN) startsWith(String prefix, int toffset) ***********");
        Str= new String("quick brown fox jumps over the lazy dog");
        System.out.println("(\"quick brown fox jumps over the lazy dog\").startsWith(\"brown\",6) :"+ Str.startsWith("brown",6));  

        System.out.println("");		
        System.out.println("********* (CharSequence) subSequence(int beginIndex, int endIndex) ***********");
        Str = "Welcome to geeksforgeeks";
        System.out.println("(\"Welcome to geeksforgeeks\").Str.subSequence(0, 7) :"+ Str.subSequence(0, 7));  

        System.out.println("");		
        System.out.println("********* (String) substring(int beginIndex) ***********");
        System.out.println("********* (String) substring(int beginIndex, int endIndex) ***********");
        Str="javatpoint";  
        System.out.println("(javatpoint).substring(2,4) : " + Str.substring(2,4));//returns va  
        System.out.println("(javatpoint).substring(2) : " +Str.substring(2));//returns vatpoint  
        String s = "leetcode";
        System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$4");
        System.out.println("(leetcode).s.substring(s.length()-1, s.length()) : " + s.substring(s.length()-1, s.length()));
        
        System.out.println("");		
        System.out.println("********* (String) toLowerCase() ***********");
        Str="JAVATPOINT HELLO stRIng";  
        System.out.println("(JAVATPOINT HELLO stRIng).toLowerCase() : " + Str.toLowerCase());
        
        System.out.println("");		
        System.out.println("********* (String) toLowerCase(Locale locale) ***********");
        Str="JAVATPOINT HELLO stRIng";  
        System.out.println("(JAVATPOINT HELLO stRIng).toLowerCase(Locale.ENGLISH) : " + Str.toLowerCase(Locale.ENGLISH));
        System.out.println("(JAVATPOINT HELLO stRIng).toLowerCase(Locale.forLanguageTag(\"tr\")) : " + Str.toLowerCase(Locale.forLanguageTag("tr")));

        System.out.println("");		
        System.out.println("********* (String) trim() ***********");
        Str = " geeks for geeks has all java functions to read  ";  
        System.out.println("(\" geeks for geeks has all java functions to read  \").trim() : "+ Str.trim()); 
        System.out.println("");		
        System.out.println("");		
 
        }
	
		@Test
	    public void stringSplit() {

	        String greeting = "a,b,c,d,,";
	        String [] split_greeting;
	        System.out.println( "Limit is equal to 0" );
	        //This signifies that break the given string on every occurrence of the condition, and remove all whitespace at the end of the string
	        split_greeting = greeting.split(",",0);
	        for(String piece : split_greeting){
	        System.out.println(piece);}

	        System.out.println( "Limit is equal to -1" );
	        split_greeting = greeting.split(",",-1);
	        for(String piece : split_greeting){
	        System.out.println(piece);}

	        System.out.println( "Limit is equal to 3" );
	        split_greeting =  greeting.split(",",3);
	        for(String piece : split_greeting){
	        System.out.println(piece);}
	        
	        System.out.println( "Limit is equal to 6" );
	        split_greeting =  greeting.split(",",6);
	        for(String piece : split_greeting){
	        System.out.println(piece);}
	    
	}
}
