package algorithms.array.medium;
import java.util.*;

import org.testng.annotations.Test;

public class Array17_FindSmallestLetterGreaterThanTarget {

	  public char searchNextLetter(char[] letters, char key) {
		    int start = 0, end = letters.length-1;
		    int n = letters.length;
		    if (key < letters[0] || key > letters[n - 1])
		      return letters[0];
		    while(start <= end){
		      int mid = start + (end - start)/2;
		      if(letters[mid] > key){
		        end = mid -1;
		      }else {
		        start = mid + 1;
		      }
		    }

		    return letters[start%n];
		  }

	  	  @Test
		  public void test() {
		    System.out.println(this.searchNextLetter(new char[] { 'c', 'f', 'h' }, 'd'));
		    System.out.println(this.searchNextLetter(new char[] { 'a', 'c', 'f', 'h' }, 'f'));
		    System.out.println(this.searchNextLetter(new char[] { 'a', 'c', 'f', 'h' }, 'b'));
		    System.out.println(this.searchNextLetter(new char[] { 'a', 'c', 'f', 'h' }, 'm'));
		    System.out.println(this.searchNextLetter(new char[] { 'a', 'c', 'f', 'h' }, 'h'));
		  }
}
