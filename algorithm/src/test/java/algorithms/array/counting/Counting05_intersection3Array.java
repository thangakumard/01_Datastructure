package algorithms.array.counting;
import java.util.ArrayList;
import java.util.List;

/**
 * https://leetcode.com/problems/intersection-of-three-sorted-arrays/description/
 * Given three integer arrays arr1, arr2 and arr3 sorted in strictly increasing order, return a sorted array of only the integers that appeared in all three arrays.
 *
 * Example 1:
 * Input: arr1 = [1,2,3,4,5], arr2 = [1,2,5,7,9], arr3 = [1,3,4,5,8]
 * Output: [1,5]
 * Explanation: Only 1 and 5 appeared in the three arrays.
 *
 * Example 2:
 * Input: arr1 = [197,418,523,876,1356], arr2 = [501,880,1593,1710,1870], arr3 = [521,682,1337,1395,1764]
 * Output: []
 * Constraints:
 *
 * 1 <= arr1.length, arr2.length, arr3.length <= 1000
 * 1 <= arr1[i], arr2[i], arr3[i] <= 2000
 */
public class Counting05_intersection3Array {
    public List<Integer> arraysIntersection(int[] arr1, int[] arr2, int[] arr3) {
        List<Integer> result = new ArrayList<>();
        int[] counter = new int[2001];

        for(int i=0; i < arr1.length; i++){
            counter[arr1[i]]++;
        }
        for(int i=0; i < arr2.length; i++){
            counter[arr2[i]]++;
        }
        for(int i=0; i < arr3.length; i++){
            counter[arr3[i]]++;
        }

        for(int i=0; i < counter.length; i++){
            if(counter[i] == 3){
                result.add(i);
            }
        }
        return result;

    }
}
