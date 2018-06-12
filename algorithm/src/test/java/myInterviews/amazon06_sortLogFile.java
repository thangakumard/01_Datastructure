package myInterviews;

import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/********
 https://www.careercup.com/question?id=5749605374361600
You have been given a task of reordering some data from a log file. Every line in the log file is a space delimited list of strings and all lines begin with an identifier that is alphanumeric. After the identifier, a line will consist of either a list of words using only English letters or only a list of integers. There will be no lines that consist of only an identifier. 

Your task is to reorder the data from the log file such that all the lines with words are at the top in the log file. in lexicographical order. Words are ordered lexicographically ignoring the identifier except in the case of ties. In the case of ties (if there are two lines that are identical except for the identifier), the identifier is used to order lexicographically. Alphanumerics should be sorted in ASCII order (numbers come before letters). The identifiers must still be part of the lines in the output Strings. Lines with integers do not need to be sorted relative to other lines with integers. 

Write an algorithm to reorder the data in the log file. 

The input to the function/method consists of two arguments - 

logFileSize: an integer representing the number of lines in the log file, 
logLines: a list of strings representing the log file. 

Output: 
Return a list of strings representing the reordered log file data. 

Note: 
Identifier consists of only English letters and numbers. The lines with words are not required to match case and the sort needs to be case insensitive. 

Example: 
Input: 
logFileSize = 5 
logLines = 
[al 9 2 3 1] 
[g1 Act car] 
[zo4 4 7] 
[abl off KEY dog] 
[a8 act zoo] 

Output: 
[gl Act car] 
[a8 act zoo] 
[ab1 off KEY dog] 
[al 9 2 3 1] 
[zo4 4 7] 

Explanation: 
Second, fourth. and fifth lines are the lines with words. According to the lexicographical order, the second line will be reordered first in the log file, then fifth, and the fourth comes in the log file. Next, the lines with numbers come in the order in which these lines were in the input.

 * @author THANGAKUMAR
 *
 */
public class amazon06_sortLogFile {

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
