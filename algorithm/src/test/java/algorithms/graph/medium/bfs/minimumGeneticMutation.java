package algorithms.graph.medium.bfs;

import algorithms.graph.NodeWithNeighbors;

import java.util.ArrayList;
import java.util.HashMap;

/***
 * https://leetcode.com/problems/minimum-genetic-mutation
 * A gene string can be represented by an 8-character long string, with choices from 'A', 'C', 'G', and 'T'.
 * Suppose we need to investigate a mutation from a gene string startGene to a gene string endGene where one mutation is defined as one single character changed in the gene string.
 * For example, "AACCGGTT" --> "AACCGGTA" is one mutation.
 * There is also a gene bank bank that records all the valid gene mutations. A gene must be in bank to make it a valid gene string.
 * Given the two gene strings startGene and endGene and the gene bank bank, return the minimum number of mutations needed to mutate from startGene to endGene. If there is no such a mutation, return -1.
 * Note that the starting point is assumed to be valid, so it might not be included in the bank.
 *
 *
 *
 * Example 1:
 * Input: startGene = "AACCGGTT", endGene = "AACCGGTA", bank = ["AACCGGTA"]
 * Output: 1
 *
 * Example 2:
 * Input: startGene = "AACCGGTT", endGene = "AAACGGTA", bank = ["AACCGGTA","AACCGCTA","AAACGGTA"]
 * Output: 2
 *
 * Constraints:
 *
 * 0 <= bank.length <= 10
 * startGene.length == endGene.length == bank[i].length == 8
 * startGene, endGene, and bank[i] consist of only the characters ['A', 'C', 'G', 'T'].
 */

/***************
 * Complexity
 * ============
 * Time : O(N · L · 4)
 * Space : O(N)
 */
public class minimumGeneticMutation {
    public int minMutation(String start, String end, String[] bank) {
        Set<String> bankSet = new HashSet<>(Arrays.asList(bank));
        if (!bankSet.contains(end)) return -1;

        char[] genes = {'A', 'C', 'G', 'T'};
        Queue<String> queue = new LinkedList<>();
        Set<String> visited = new HashSet<>();
        queue.offer(start);
        visited.add(start);

        int mutations = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                String current = queue.poll();
                if (current.equals(end)) return mutations;

                char[] currArr = current.toCharArray();
                for (int j = 0; j < currArr.length; j++) {
                    char original = currArr[j];
                    for (char g : genes) {
                        if (g == original) continue;
                        currArr[j] = g;
                        String mutated = new String(currArr);
                        if (bankSet.contains(mutated) && visited.add(mutated)) {
                            queue.offer(mutated);
                        }
                    }
                    currArr[j] = original; // restore
                }
            }
            mutations++;
        }
        return -1;
    }
}