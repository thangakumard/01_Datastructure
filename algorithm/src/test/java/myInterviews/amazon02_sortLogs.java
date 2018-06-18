package myInterviews;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.testng.annotations.Test;

/*******
 * Interview Date: 29_Apr_2018
 * @author THANGAKUMAR
 * In a given log file there may be n number of line.
 * In each line, the first word is the unique identifier of the line in the log. Following the identifier,
 * the log may contain sequence of characters in lower case or sequence of numbers. 
 * Have to arrange these log lines in such a way all the log lines with character comes top and lexicographically sorted, 
 * all the log lines with numbers comes next to that. When sorting the character lines lexicographically if two log lines are
 * identical then sort based on line identifier.
 * 
 * Input :
 * [er5we sedfd erer aa bdsfsdf]
 * [fr4dd 56456 4564 6456 45645]
 * [ae3sf aabsd fgfg ndfg wqewr]
 * [b3sfs bdfgf cdfd asdf brgfg]
 * [g3sdf 34545 9879 98698 9879]
 * [z4dff sedfd erer aa bdsfsdf]
 * 
 * Output:
 * [ae3sf aabsd fgfg ndfg wqewr]
 * [b3sfs bdfgf cdfd asdf brgfg]
 * [er5we sedfd erer aa bdsfsdf]
 * [z4dff sedfd erer aa bdsfsdf]
 * [fr4dd 56456 4564 6456 45645]
 * [g3sdf 34545 9879 98698 9879]
 */
public class amazon02_sortLogs {
	@Test
	public void sortLogFile(){
		List<String> input = new ArrayList<>();
		input.add("al 9 2 3 1");
		input.add("g1 Act car");
		input.add("zo4 4 7");
		input.add("abl off KEY dog");
		input.add("a8 act zoo");
		System.out.println(sortfile(input));
	}
	
	private List<String> sortfile(List<String> input){
		List<String> lstSortedLogs = new ArrayList<>();
		
		List<String> lstAlphabets = new ArrayList<>();
		List<String> lstNumeric = new ArrayList<>();
		String temp = "";
		for(int i=0; i < input.size(); i++){			
			String[] line = input.get(i).split(" ", 2);			
			if(line[1] != null && line[1].length() > 0){
				if(Character.isDigit(line[1].charAt(0))){
					lstNumeric.add(input.get(i));
				}
				else{
					temp = line[1] + " " + line[0];
					lstAlphabets.add(temp);
				}
			}
		}	
		
		Collections.sort(lstAlphabets);
		for(int j=0; j < lstAlphabets.size(); j++){
			StringBuilder sbuilder = new StringBuilder();
			String s = lstAlphabets.get(j);
			sbuilder.append(s.substring(s.lastIndexOf(" ")+1));
			sbuilder.append(" ");
			sbuilder.append(s.substring(0, s.lastIndexOf(" ")));
			lstSortedLogs.add(sbuilder.toString());
		}		
		lstSortedLogs.addAll(lstNumeric);
		return lstSortedLogs;
	}
}
