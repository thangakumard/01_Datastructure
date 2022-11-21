package algorithms.companies.amazon.amazon;

/*
 * https://leetcode.com/problems/plates-between-candles/
 *
 * Reference : https://leetcode.com/problems/plates-between-candles/discuss/2483886/TreeSet-or-Java-or-Easy-and-Concise
 * 
There is a long table with a line of plates and candles arranged on top of it.
You are given a 0-indexed string s consisting of characters '*' and '|' only, where a '*' represents a plate and a '|' represents a candle.

You are also given a 0-indexed 2D integer array queries where queries[i] = [lefti, righti] denotes the substring s[lefti...righti] (inclusive).
For each query, you need to find the number of plates between candles that are in the substring. A plate is considered between candles if there is at least one candle to its left and at least one candle to its right in the substring.
For example, s = "||**||**|*", and a query [3, 8] denotes the substring "*||**|".
The number of plates between candles in this substring is 2, as each of the two plates has at least one candle in the substring to its left and right.
Return an integer array answer where answer[i] is the answer to the ith query.

Example 1:
Input: s = "**|**|***|", queries = [[2,5],[5,9]]
Output: [2,3]
Explanation:
- queries[0] has two plates between candles.
- queries[1] has three plates between candles.

Example 2:
Input: s = "***|**|*****|**||**|*", queries = [[1,17],[4,5],[14,17],[5,11],[15,16]]
Output: [9,0,0,0,0]
Explanation:
- queries[0] has nine plates between candles.
- The other queries have zero plates between candles.
 */

import org.assertj.core.api.Assertions;
import org.testng.annotations.Test;

import java.util.TreeSet;

public class Amazon_06_PlatesBetweenCandles {

    @Test
    public void test1(){
        String s = "**|**|***|";
        int[][] queries = {{3,8},{0,2},{2,5},{5,9}};
        //{2,5} => |**| => 2 plates
        //{5,9} => |***| => 3 plates

        int[] result = platesBetweenCandles(s, queries);
        Assertions.assertThat(result).containsExactlyInAnyOrder(new int[]{0, 0, 2, 3});
    }

    public int[] platesBetweenCandles(String input, int[][] queries) {
        int[] platsInLeft= new int[input.length()];
        TreeSet<Integer> candleIndexTreeSet = new TreeSet<>();

        int leftPlateCount = 0;
        for(int i=0;i<input.length();i++){
            if(input.charAt(i)=='|')
            {
                candleIndexTreeSet.add(i);
                platsInLeft[i] = leftPlateCount;
            }else{
                leftPlateCount++;
            }
        }
        int[] result = new int[queries.length];
        int i=0;
        for(int query[] : queries){
            Integer leftMostCandleIndex = candleIndexTreeSet.ceiling(query[0]);
            Integer rightMostCandleIndex = candleIndexTreeSet.floor(query[1]);

            if(leftMostCandleIndex!=null && rightMostCandleIndex!=null && leftMostCandleIndex<rightMostCandleIndex)
                result[i] = platsInLeft[rightMostCandleIndex]-platsInLeft[leftMostCandleIndex];

            i++;
        }
        return result;
    }
}
