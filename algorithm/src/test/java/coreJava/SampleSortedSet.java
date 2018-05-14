package coreJava;

import java.util.SortedSet;
import java.util.TreeSet;

import org.testng.annotations.Test;

public class SampleSortedSet {
	
	@Test
	public void test1(){
		SortedSet<Integer> s = new TreeSet<Integer>();
		s.add(new Integer(10));
		s.add(new Integer(15));
		s.add(new Integer(25));
		s.add(new Integer(10));
		
		System.out.println("First Value :" + s.first());
		System.out.println("Size :" + s.size());
	}

}
