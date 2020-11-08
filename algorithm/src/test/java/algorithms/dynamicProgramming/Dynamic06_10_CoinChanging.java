package algorithms.dynamicProgramming;

import org.testng.annotations.*;

/**
 * 
 * @author THANGAKUMAR
 * Given a total and coins of certain denomination with infinite supply, what is the minimum number
 * of coins it takes to form this total.
 *
 * Time complexity - O(coins.size * total)
 * Space complexity - O(coins.size * total)
 * 
 * Formula 
 * T[i] = Minimum (T[i] , 1 + T[i - coins[j])
 *
 */
public class Dynamic06_10_CoinChanging {

@Test
public void Test(){
	int[] coins = {7,2,3,6};
	int total = 13;
	System.out.println(minimumNumberOfCoins(coins, total));
}
	
private int minimumNumberOfCoins(int[] coins, int total){
	int[] t = new int[total+1];
	int[] r = new int[total+1];
	
	t[0] = 0;
	for(int i=1; i<= total; i++){
		t[i] = Integer.MAX_VALUE-1;
		r[i] = -1;
	}
	
	for(int j=0; j < coins.length; j++){
		for(int i=1; i <= total; i++){
			if(i >= coins[j]){
				if(t[i - coins[j]]+ 1 < t[i]){
					t[i] = t[i - coins[j]]+1;
					r[i] = j;
				}
			}
		}
	}
	printCoinCombination(r,coins);
	return t[total];
}

private void printCoinCombination(int[] r, int[] coins){
	if(r[r.length - 1] == -1){
		System.out.println("No solution is possible");
		return;
	}
	int start = r.length-1;
	System.out.println("Coins used to form total");
	
	while(start != 0){
		int j = r[start];
		System.out.println(coins[j]);
		start = start - coins[j];
	}
}

}
