package algorithms.array.medium.Math;

/***
 * https://leetcode.com/problems/count-primes/description/
 * Given an integer n, return the number of prime numbers that are strictly less than n.
 *
 * Example 1:
 * Input: n = 10
 * Output: 4
 * Explanation: There are 4 prime numbers less than 10, they are 2, 3, 5, 7.
 *
 * Example 2:
 * Input: n = 0
 * Output: 0

 * Example 3:
 * Input: n = 1
 * Output: 0
 *
 * Constraints:
 * 0 <= n <= 5 * 106
 */
public class Math03_countPrimes {
    public int countPrimes(int n) {
        if (n <= 2) {
            return 0;
        }

        boolean[] nonPrime = new boolean[n];
        for (int p = 2; p <= (int) Math.sqrt(n); p++) {
            if (nonPrime[p] == false) {
                for (int j = p * p; j < n; j += p) {
                    nonPrime[j] = true;
                }
            }
        }

        int numberOfPrimes = 0;
        for (int i = 2; i < n; i++) {
            if (nonPrime[i] == false) {
                ++numberOfPrimes;
            }
        }
        return numberOfPrimes;
    }
}
