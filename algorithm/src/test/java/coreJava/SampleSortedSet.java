package coreJava;

import java.util.SortedSet;
import java.util.TreeSet;

import org.testng.annotations.Test;

public class SampleSortedSet {
	
	@Test
	public void test1(){
		SortedSet<Integer> s = new TreeSet<>();
		s.add(15);
		s.add(25);
		s.add(10);
		s.add(35);
		
		System.out.println("First Value :" + s.first());
		System.out.println("Size :" + s.size());
	}

}
