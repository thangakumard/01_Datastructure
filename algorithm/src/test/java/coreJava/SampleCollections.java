package coreJava;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.testng.annotations.Test;

/*****
 * 
 * @author THANGAKUMAR
 * This class consists exclusively of static methods that operate on or return collections. 
 * It contains polymorphic algorithms that operate on collections, "wrappers", 
 * which return a new collection backed by a specified collection, and a few other odds and ends.
 * 
 * Field Summary
 * ==============
 * static List	EMPTY_LIST
 * static Map	EMPTY_MAP
 * static Set	EMPTY_SET
 */
public class SampleCollections {

	@Test
	public void test(){
		  List list = Collections.EMPTY_LIST;
		  Set set = Collections.EMPTY_SET;
		  Map map = Collections.EMPTY_MAP;
		  
		  List<String> s = Collections.emptyList();
		  Set<Long> l = Collections.emptySet();
		  Map<Date, String> d = Collections.emptyMap();
		  
	}
	
}
