package algorithms.array.medium;

import org.testng.annotations.Test;
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
public class Array14_MinimumSwaps {
	@Test
	private void test() {
		int[] input = { 4, 3, 1, 2 };
	 System.out.println(minimumSwaps(input));
	}

	private int minimumSwaps(int[] arr) {

		if (arr.length < 2)
			return 0;
		int min_swap = 0;
		for (int i = 0; i < arr.length; i++) {
			while (arr[i] - 1 != i) {
				int temp = arr[i];
				arr[i] = arr[arr[i] - 1];
				arr[temp - 1] = temp;
				min_swap++;
			}
		}
		return min_swap;
	}

}
