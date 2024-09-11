package algorithms.array.medium.Math;

import java.util.HashMap;
import java.util.Map;

/**
 * https://leetcode.com/problems/friends-of-appropriate-ages
 * There are n persons on a social media website. You are given an integer array ages where ages[i] is the age of the ith person.
 * A Person x will not send a friend request to a person y (x != y) if any of the following conditions is true:
 * age[y] <= 0.5 * age[x] + 7
 * age[y] > age[x]
 * age[y] > 100 && age[x] < 100
 * Otherwise, x will send a friend request to y.
 *
 * Note that if x sends a request to y, y will not necessarily send a request to x. Also, a person will not send a friend request to themself.
 *
 * Return the total number of friend requests made.
 *
 *
 *
 * Example 1:
 * Input: ages = [16,16]
 * Output: 2
 * Explanation: 2 people friend request each other.
 *
 * Example 2:
 * Input: ages = [16,17,18]
 * Output: 2
 * Explanation: Friend requests are made 17 -> 16, 18 -> 17.

 * Example 3:
 * Input: ages = [20,30,100,110,120]
 * Output: 3
 * Explanation: Friend requests are made 110 -> 100, 120 -> 110, 120 -> 100.
 * Constraints:
 * n == ages.length
 * 1 <= n <= 2 * 104
 * 1 <= ages[i] <= 120
 */
public class Math01_numFriendRequests {
    public int numFriendRequests(int[] ages) {
        Map<Integer, Integer> count = new HashMap<>();
        for (int age : ages)
            count.put(age, count.getOrDefault(age, 0) + 1);
        int res = 0;
        for (Integer a : count.keySet())
            for (Integer b : count.keySet())
                if (request(a, b))
                    res += count.get(a) * (count.get(b) - (a == b ? 1 : 0));
        return res;
    }

    private boolean request(int a, int b) {
        return !(b <= 0.5 * a + 7 || b > a || (b > 100 && a < 100));
    }
}
