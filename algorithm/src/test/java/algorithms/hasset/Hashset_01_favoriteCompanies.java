package algorithms.hasset;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.testng.annotations.Test;

/*
 * https://leetcode.com/problems/people-whose-list-of-favorite-companies-is-not-a-subset-of-another-list/
 * Given the array favoriteCompanies where favoriteCompanies[i] is the list of favorites companies for the ith person (indexed from 0).

Return the indices of people whose list of favorite companies is not a subset of any other list of favorites companies. You must return the indices in increasing order.

 

Example 1:

Input: favoriteCompanies = [["leetcode","google","facebook"],["google","microsoft"],["google","facebook"],["google"],["amazon"]]
Output: [0,1,4] 
Explanation: 
Person with index=2 has favoriteCompanies[2]=["google","facebook"] which is a subset of favoriteCompanies[0]=["leetcode","google","facebook"] corresponding to the person with index 0. 
Person with index=3 has favoriteCompanies[3]=["google"] which is a subset of favoriteCompanies[0]=["leetcode","google","facebook"] and favoriteCompanies[1]=["google","microsoft"]. 
Other lists of favorite companies are not a subset of another list, therefore, the answer is [0,1,4].
Example 2:

Input: favoriteCompanies = [["leetcode","google","facebook"],["leetcode","amazon"],["facebook","google"]]
Output: [0,1] 
Explanation: In this case favoriteCompanies[2]=["facebook","google"] is a subset of favoriteCompanies[0]=["leetcode","google","facebook"], therefore, the answer is [0,1].
Example 3:

Input: favoriteCompanies = [["leetcode"],["google"],["facebook"],["amazon"]]
Output: [0,1,2,3]
 * 
 */

public class Hashset_01_favoriteCompanies {

	@Test
	public void getFavoriteCompaniesNotSubset() {
		List<List<String>>  input = new ArrayList<List<String>>();
		List<String> company = new ArrayList<String>();
		company.add("leetcode");
		company.add("google");
		company.add("facebook");
		input.add(company);
		
		company = new ArrayList<String>();
		company.add("google");
		company.add("microsoft");
		input.add(company);
		
		company = new ArrayList<String>();
		company.add("google");
		company.add("facebook");
		input.add(company);
		
		company = new ArrayList<String>();
		company.add("google");
		input.add(company);
		
		company = new ArrayList<String>();
		company.add("amazon");
		input.add(company);
		
		System.out.println(peopleIndexes(input));
		
	}
	
	 private List<Integer> peopleIndexes(List<List<String>> favoriteCompanies) {
	        List<Integer> result = new ArrayList<Integer>();
	        Set<String>[] setCompanies = new Set[favoriteCompanies.size()];
	        for(int i=0; i < favoriteCompanies.size(); i++) {
	        	setCompanies[i] = new HashSet<String>(favoriteCompanies.get(i));
	        }
	        boolean isUnique = true;
	        
	        for(int i=0; i< setCompanies.length; i++) {
	        	for(int j=0; j < setCompanies.length; j++) {
	        		if(i!=j && setCompanies[j].containsAll(setCompanies[i])) {
	        			isUnique = false;
	        			break;
	        		}
	        	}
	        	if(isUnique) {
	        		result.add(i);
	        		isUnique = true;
	        	}
	        }
	       
	        return result;
	    }
}
