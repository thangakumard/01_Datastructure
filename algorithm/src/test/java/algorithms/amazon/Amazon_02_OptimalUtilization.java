package algorithms.amazon;

import java.util.*;

import org.testng.annotations.Test;

/*https://leetcode.com/discuss/interview-question/373202
 * 
 * Given 2 lists a and b. Each element is a pair of integers where the first integer represents the unique id and the second integer represents a value. 
 * Your task is to find an element from a and an element form b such that the sum of their values is less or equal to target 
 * and as close to target as possible. 
 * Return a list of ids of selected elements. If no pair is possible, return an empty list.

Example 1:

Input:
a = [[1, 2], [2, 4], [3, 6]]
b = [[1, 2]]
target = 7

Output: [[2, 1]]

Explanation:
There are only three combinations [1, 1], [2, 1], and [3, 1], which have a total sum of 4, 6 and 8, respectively.
Since 6 is the largest sum that does not exceed 7, [2, 1] is the optimal pair.
Example 2:

Input:
a = [[1, 3], [2, 5], [3, 7], [4, 10]]
b = [[1, 2], [2, 3], [3, 4], [4, 5]]
target = 10

Output: [[2, 4], [3, 2]]

Explanation:
There are two pairs possible. Element with id = 2 from the list `a` has a value 5, and element with id = 4 from the list `b` also has a value 5.
Combined, they add up to 10. Similarily, element with id = 3 from `a` has a value 7, and element with id = 2 from `b` has a value 3.
These also add up to 10. Therefore, the optimal pairs are [2, 4] and [3, 2].
Example 3:

Input:
a = [[1, 8], [2, 7], [3, 14]]
b = [[1, 5], [2, 10], [3, 14]]
target = 20

Output: [[3, 1]]
Example 4:

Input:
a = [[1, 8], [2, 15], [3, 9]]
b = [[1, 8], [2, 11], [3, 12]]
target = 20

Output: [[1, 3], [3, 2]]
 */

public class Amazon_02_OptimalUtilization {
	
	@Test
	public void test() {
		
		int[][][] As = {
	            {{1, 2}, {2, 4}, {3, 6}},
	            {{1, 3}, {2, 5}, {3, 7}, {4, 10}},
	            {{1, 8}, {2, 7}, {3, 14}},
	            {{1, 8}, {2, 15}, {3, 9}}
	        };
	        int[][][] Bs = {
	            {{1, 2}},
	            {{1, 2}, {2, 3}, {3, 4}, {4, 5}},
	            {{1, 5}, {2, 10}, {3, 14}},
	            {{1, 8}, {2, 11}, {3, 12}}
	        };
	        int[] targets = {7, 10, 20, 20};

	        for (int i=0; i<4; i++) {
	            System.out.println(solution_TreeMap(As[i], Bs[i], targets[i]).toString());
	        }
		
	}
	
	
	public List<int[]> solution_HashMap(List<int[]> a, List<int[]> b, int target){
        Map<Integer, List<int[]>> map = new HashMap<>();//key is sum , value is list of ids from a and b.

        for (int i = 0; i < a.size(); i ++){
            for (int j = 0; j < b.size(); j ++){
                List<int[]> sums = map.getOrDefault(a.get(i)[1] + b.get(j)[1], new ArrayList<int[]>());
                sums.add(new int[] {a.get(i)[0], b.get(j)[0]});
                map.put(a.get(i)[1] + b.get(j)[1], sums);
            }
        }

        List<Integer> allSums = new ArrayList<>();
        for (Integer i : map.keySet()){
            if (i < target){
                allSums.add(i);
            } else if (i == target){
                return map.get(target);
            }
        }
        if (allSums.size() == 0){
            return new ArrayList<>();//target is less than every possible sums.
        }
        return map.get(Collections.max(allSums));
    }
	
	private  List<List<Integer>> solution_TreeMap(int[][] a, int[][] b, int target) {
        List<List<Integer>> res = new ArrayList<>();
        TreeMap<Integer, List<List<Integer>>> treeMap = new TreeMap<>();

        for (int i=0; i<a.length; i++) {
            for (int j=0; j<b.length; j++) {
                int sum = a[i][1] + b[j][1]; 
                if (sum <= target) {
                    List<List<Integer>> list =  treeMap.computeIfAbsent(sum, (k) -> new ArrayList<>());
                    list.add(Arrays.asList(a[i][0], b[j][0]));//Add the key at 0th index as key
                }
            }
        }

        return treeMap.floorEntry(target).getValue();
    }

}
