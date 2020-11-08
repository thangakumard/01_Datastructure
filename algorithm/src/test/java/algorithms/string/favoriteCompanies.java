package algorithms.string;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.testng.annotations.Test;

public class favoriteCompanies {

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
		
		peopleIndexes(input);
		
	}
	
	 private List<Integer> peopleIndexes(List<List<String>> favoriteCompanies) {
	        List<Integer> result = new ArrayList<Integer>();
	        Set<String>[] set = new Set[favoriteCompanies.size()];
	        for(int i=0; i< set.length;i++){
	            set[i] = new HashSet<>(favoriteCompanies.get(i));
	        }
	        boolean isUnique = false;
	        for(int i=0; i < set.length; i++){
	            for(int j=0; j< set.length; j++){
	                if(i!=j && set[j].containsAll(set[i])){
	                	isUnique = true;
	                	break;
	                }
	            }
	            if(!isUnique) {
	            	result.add(i);
	            }
	            isUnique = false;
	        }
	        return result;
	    }
}
