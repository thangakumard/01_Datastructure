package algorithms.array.sorts;


import java.util.Arrays;
import java.util.HashMap;

/*
 * https://www.hackerrank.com/challenges/minimum-swaps-2/problem
 * 
 * You are given an unordered array consisting of consecutive integers  [1, 2, 3, ..., n] 
 * without any duplicates. You are allowed to swap any two elements. 
 * You need to find the minimum number of swaps required to sort the array in ascending order.

For example, given the array [7,1,3,2,4,5,6]  we perform the following steps:

i   arr                         swap (indices)
0   [7, 1, 3, 2, 4, 5, 6]   swap (0,3)
1   [2, 1, 3, 7, 4, 5, 6]   swap (0,1)
2   [1, 2, 3, 7, 4, 5, 6]   swap (3,4)
3   [1, 2, 3, 4, 7, 5, 6]   swap (4,5)
4   [1, 2, 3, 4, 5, 7, 6]   swap (5,6)
5   [1, 2, 3, 4, 5, 6, 7]
It took  swaps to sort the array.

Function Description

Complete the function minimumSwaps in the editor below. It must return an integer representing the minimum number of swaps to sort the array.

minimumSwaps has the following parameter(s):

arr: an unordered array of integers
Input Format

The first line contains an integer, , the size of .
The second line contains  space-separated integers .
 */

/***
 * Time:  O(n log n)
 * Space: O(n)
 */
public class MinimumSwapsToSort {
	static int minSwaps(int[] arr) {

		// Temporary array to store elements in sorted order
		int[] temp = arr.clone();
		Arrays.sort(temp);

		// Hashing elements with their correct positions
		HashMap<Integer, Integer> pos = new HashMap<>();
		for (int i = 0; i < arr.length; i++)
			pos.put(arr[i], i);

		int swaps = 0;
		for (int i = 0; i < arr.length; i++) {
			if (temp[i] != arr[i]) {

				// Index of the element that should be at index i.
				int ind = pos.get(temp[i]);

				// Swapping element to its correct position
				int tempValue = arr[i];
				arr[i] = arr[ind];
				arr[ind] = tempValue;

				// Update the indices in the hashmap
				pos.put(arr[i], i);
				pos.put(arr[ind], ind);

				swaps++;
			}
		}
		return swaps;
	}

	public static void main(String[] args) {
		int[] arr = {10, 19, 6, 3, 5};
		System.out.println(minSwaps(arr));
	}


}
