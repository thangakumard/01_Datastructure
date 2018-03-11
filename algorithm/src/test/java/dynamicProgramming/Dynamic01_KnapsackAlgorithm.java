package dynamicProgramming;

import org.testng.annotations.*;
import org.testng.asserts.*;

/**
 * 
 * @author THANGAKUMAR
 * https://www.geeksforgeeks.org/knapsack-problem/
 * Print the MAX value we can pick for the given weight
 * 
 */
public class Dynamic01_KnapsackAlgorithm {

	@Test
	public void Test(){
		int[] value= {1,4,5,7};
		int[] weight = {1,3,4,5};
		int totalWeight = 7;
		System.out.println("Value : " + knapsack(value, weight, totalWeight));
	}
	
	 //*************************************
	 // Time complexity - O(W*total items)
	 //*************************************
	private int knapsack(int[] value, int[] weight, int totalWeight){
		
		int[][] T = new int[value.length+1][totalWeight+1];
		
		for(int i=0; i <= value.length; i++){
			for(int j=0; j <= totalWeight; j++){
				if(i == 0 || j == 0){
					T[i][j] = 0;
					continue;
				}
				if(weight[i-1] > j){ //if weight is GREATER than expected weight (J in column) 
					// ignore this item and take or carry forward the previous item's value
					T[i][j] = T[i-1][j];
				}else{ // if the item's weight is <= expected weight (J in column)
					
					// Take MAX of (current item's weight + T [previous item][current item's weight - expected weight],
									//T[previous item][expected weight]
						T[i][j] = Math.max(value[i-1]+ T[i-1][j -weight[i-1]] , T[i-1][j]);
					
				}
			}
		}
		
		return T[value.length][totalWeight];
	}
	
}
