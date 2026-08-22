package algorithms.array.medium.hashMap;

import java.util.*;

import org.testng.annotations.Test;

/*https://leetcode.com/problems/number-of-good-pairs/description/

 * Given an array of integers nums.
 A pair (i,j) is called good if nums[i] == nums[j] and i < j.

Return the number of good pairs.

 

Example 1:

Input: nums = [1,2,3,1,1,3]
Output: 4
Explanation: There are 4 good pairs (0,3), (0,4), (2,4), (2,5) 0-indexed.
Example 2:

Input: nums = [1,1,1,1]
Output: 6
Explanation: Each pair in the array are good.
Example 3:

Input: nums = [1,2,3]
Output: 0
 */
public class Map01_numIdenticalPairs {
	@Test
	private void test() {
		int[] a = new int[] {1,2,3,1,1,3};
		System.out.println(numIdenticalPairs(a));
	}

    public int numIdenticalPairs(int[] guestList) {
        HashMap<Integer, Integer> hm = new HashMap<>();
        int ans = 0;
        for(int friend:guestList)
        {
            int friendCount = hm.getOrDefault(friend,0);
            ans+=friendCount;
            hm.put(friend,friendCount+1);
        }
        return ans;
    }
}
