package algorithms.array.counting;

import java.util.HashMap;
import java.util.Map;

/**
 *https://leetcode.com/problems/number-of-equivalent-domino-pairs/description/
 *
 * Given a list of dominoes, dominoes[i] = [a, b] is equivalent to dominoes[j] = [c, d] if and only if either (a == c and b == d), or (a == d and b == c) - that is, one domino can be rotated to be equal to another domino.
 *
 * Return the number of pairs (i, j) for which 0 <= i < j < dominoes.length, and dominoes[i] is equivalent to dominoes[j].
 *
 *
 *
 * Example 1:
 *
 * Input: dominoes = [[1,2],[2,1],[3,4],[5,6]]
 * Output: 1
 * Example 2:
 *
 * Input: dominoes = [[1,2],[1,2],[1,1],[1,2],[2,2]]
 * Output: 3
 *
 *
 * Constraints:
 *
 * 1 <= dominoes.length <= 4 * 104
 * dominoes[i].length == 2
 * 1 <= dominoes[i][j] <= 9
 */

/**
 * Solution 1:
 * You need to distinguish the different dominoes and count the same.
 *
 * I did it in this way:
 * f(domino) = min(d[0], d[1]) * 10 + max(d[0], d[1])
 * For each domino d, calculate min(d[0], d[1]) * 10 + max(d[0], d[1])
 * This will put the smaller number on the left and bigger one on the right (in decimal).
 * So same number same domino, different number different domino.
 *
 * We sum up the pair in the end after the loop,
 * using the guass formula sum = v * (v + 1) / 2,
 * where v is the number of count.
 */
public class Counting01_numEquivDominoPairs {
    public int numEquivDominoPairs(int[][] dominoes) {
        Map<Integer,Integer> counter = new HashMap<>();
        int result = 0;
        for(int[] input: dominoes){
            int k = Math.min(input[0], input[1]) * 10 + Math.max(input[0], input[1]);
            counter.put(k, counter.getOrDefault(k, 0)+1);
        }
        for(int v: counter.values()){
            result += v * (v-1)/2;
        }
        return result;
    }
}
