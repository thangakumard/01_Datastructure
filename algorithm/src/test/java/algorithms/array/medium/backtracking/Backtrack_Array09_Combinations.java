package algorithms.array.medium.backtracking;

import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.Test;

/***
 * 
 * https://leetcode.com/problems/combinations/
 *
 * Given two integers n and k, return all possible combinations of k numbers out
 * of 1 ... n.
 * 
 * You may return the answer in any order.
 * 
 * 
 * 
 * Example 1:
 * 
 * Input: n = 4, k = 2 Output: [ [2,4], [3,4], [2,3], [1,2], [1,3], [1,4], ]
 * Example 2:
 * 
 * Input: n = 1, k = 1 Output: [[1]]
 * 
 * 
 * Constraints:
 * 
 * 1 <= n <= 20 1 <= k <= n
 *
 */
public class Backtrack_Array09_Combinations {

	@Test
	private void test() {
		System.out.println(combine(4,2));
		System.out.println(combine(4,3));
	}
	
	public List<List<Integer>> combine(int n, int k) {
        List<List<Integer>> result = new ArrayList<>();
        backtrack(result,n,k,new ArrayList<>(),1);/** STARTS WITH 1 **/
        return result;
    }
    
    private void backtrack(List<List<Integer>> result,int n, int k, List<Integer> current, int start){
        if(current.size()  == k){
            result.add(new ArrayList<>(current));
        }
        for(int i=start; i < n+1; i++){
            if(current.contains(i)) continue;
            current.add(i);
            backtrack(result,n,k,current,i+1);
            current.remove(current.size()-1);
        }
    }
}
