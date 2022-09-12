package algorithms.string;

import java.util.Collections;
import java.util.HashMap;

import org.testng.annotations.Test;

/*
 * https://www.educative.io/module/lesson/data-structures-in-java/NEwrOXBn9Bm
 * 
 * https://leetcode.com/problems/fruit-into-baskets/
 * In a row of trees, the i-th tree produces fruit with type tree[i].

	You start at any tree of your choice, then repeatedly perform the following steps:
	
	Add one piece of fruit from this tree to your baskets.  If you cannot, stop.
	Move to the next tree to the right of the current tree.  If there is no tree to the right, stop.
	Note that you do not have any choice after the initial choice of starting tree: you must perform step 1, then step 2, then back to step 1, then step 2, and so on until you stop.
	
	You have two baskets, and each basket can carry any quantity of fruit, but you want each basket to only carry one type of fruit each.
	
	What is the total amount of fruit you can collect with this procedure?
	
	 
	
	Example 1:
	
	Input: [1,2,1]
	Output: 3
	Explanation: We can collect [1,2,1].
	Example 2:
	
	Input: [0,1,2,2]
	Output: 3
	Explanation: We can collect [1,2,2].
	If we started at the first tree, we would only collect [0, 1].
	Example 3:
	
	Input: [1,2,3,2,2]
	Output: 4
	Explanation: We can collect [2,3,2,2].
	If we started at the first tree, we would only collect [1, 2].
	Example 4:
	
	Input: [3,3,3,1,2,1,1,2,3,3,4]
	Output: 5
	Explanation: We can collect [1,2,1,1,2].
	If we started at the first tree or the eighth tree, we would only collect 4 fruits.
	 
	
	Note:
	
	1 <= tree.length <= 40000
	0 <= tree[i] < tree.length

 */
public class String19_FruitIntoBaskets {
	
	@Test
	private void test() {
		int[] input = {3,3,3,1,2,1,1,2,3,3,4};
		System.out.println(totalFruit(input));
	}

	 public int totalFruit(int[] tree) {
		 int max_fruites = 0;
		 HashMap<Integer, Integer> mapBasket = new HashMap<>();
		 int left = 0, right = 0;
		 while(right < tree.length) {
			 mapBasket.put(tree[right],right++);
			 if(mapBasket.size() > 2) {
				 int mix_index = Collections.min(mapBasket.values());
				 mapBasket.remove(tree[mix_index]);
				 left = mix_index + 1;
			 }
			 max_fruites = Math.max(max_fruites, right-left);
		 }
		 return max_fruites;
	 }
}
