package algorithms.graph.medium.bfs;
import java.util.*;

/****
 * https://leetcode.com/problems/word-ladder-ii/
 * A transformation sequence from word beginWord to word endWord using a dictionary wordList is a sequence of words beginWord -> s1 -> s2 -> ... -> sk such that:
 *
 * Every adjacent pair of words differs by a single letter.
 * Every si for 1 <= i <= k is in wordList. Note that beginWord does not need to be in wordList.
 * sk == endWord
 * Given two words, beginWord and endWord, and a dictionary wordList, return all the shortest transformation sequences from beginWord to endWord, or an empty list if no such sequence exists. Each sequence should be returned as a list of the words [beginWord, s1, s2, ..., sk].
 *
 *
 *
 * Example 1:
 *
 * Input: beginWord = "hit", endWord = "cog", wordList = ["hot","dot","dog","lot","log","cog"]
 * Output: [["hit","hot","dot","dog","cog"],["hit","hot","lot","log","cog"]]
 * Explanation: There are 2 shortest transformation sequences:
 * "hit" -> "hot" -> "dot" -> "dog" -> "cog"
 * "hit" -> "hot" -> "lot" -> "log" -> "cog"
 * Example 2:
 *
 * Input: beginWord = "hit", endWord = "cog", wordList = ["hot","dot","dog","lot","log"]
 * Output: []
 * Explanation: The endWord "cog" is not in wordList, therefore there is no valid transformation sequence.
 *
 *
 * Constraints:
 *
 * 1 <= beginWord.length <= 5
 * endWord.length == beginWord.length
 * 1 <= wordList.length <= 500
 * wordList[i].length == beginWord.length
 * beginWord, endWord, and wordList[i] consist of lowercase English letters.
 * beginWord != endWord
 * All the words in wordList are unique.
 * The sum of all shortest transformation sequences does not exceed 105.
 */

/***
 * Algorithm: BFS (level graph) + DFS/Backtracking (path reconstruction)
 *
 * This is a two-phase approach — don't try to enumerate paths during the BFS itself.
 *
 * Phase 1 — BFS, level by level, to build a shortest-path DAG. Run BFS from beginWord. Instead of just tracking distance (Word Ladder I), record parent pointers: child -> list of predecessor words that lie on a shortest path to child. Process one full BFS level at a time (a Set<String>, not one word at a time) and only remove those words from the dictionary after the whole level is generated. This is the crux of the problem: it lets two different words in the same level point to the same child, which is exactly how multiple equally-short paths arise. If words were marked visited immediately, we'd silently drop valid parent edges.
 *
 * Stop growing levels once endWord is discovered in the current level, but let that level finish processing so every parent edge into endWord gets recorded.
 *
 * Phase 2 — DFS/backtracking from endWord back to beginWord. Walk the parent map from endWord, branching over every parent at each step, appending words to a path. When the path reaches beginWord, reverse it and record it. This is standard backtracking: extend the path, recurse, then pop before trying the next parent (undo step).
 *
 * Why not BFS the paths directly? The number of shortest paths can be exponential in the branching factor; carrying full path lists through BFS wastes huge memory. Recording only parent pointers keeps BFS at O(number of words), and backtracking reconstructs paths lazily, one path at a time.
 *
 * Why generate neighbors via letter substitution instead of scanning wordList pairwise? For a word of length L, substitution generates L * 25 candidates — a small constant (<= 125 here). Comparing a word against every other word in wordList to check "differs by one letter" costs O(N * L) per word. Since N (up to 500) can exceed L * 25, substitution is the better-scaling choice, and it's also just the standard technique for this problem family.
 */

public class wordLadderII {

        public List<List<String>> findLadders(String beginWord, String endWord, List<String> wordList) {
            List<List<String>> result = new ArrayList<>();
            Set<String> wordSet = new HashSet<>(wordList);
            if (!wordSet.contains(endWord)) return result;

            // child -> list of parents that lie on some shortest path to child
            Map<String, List<String>> parents = new HashMap<>();
            Set<String> currentLevel = new HashSet<>();
            currentLevel.add(beginWord);
            boolean found = false;

            while (!currentLevel.isEmpty() && !found) {
                // Remove the whole level at once so same-level words can still be
                // discovered as siblings before being closed off.
                wordSet.removeAll(currentLevel);
                Set<String> nextLevel = new HashSet<>();

                for (String word : currentLevel) {
                    char[] chars = word.toCharArray();
                    for (int i = 0; i < chars.length; i++) {
                        char original = chars[i];
                        for (char c = 'a'; c <= 'z'; c++) {
                            if (c == original) continue;
                            chars[i] = c;
                            String next = new String(chars);
                            if (wordSet.contains(next)) {
                                nextLevel.add(next);
                                parents.computeIfAbsent(next, k -> new ArrayList<>()).add(word);
                                if (next.equals(endWord)) found = true;
                            }
                        }
                        chars[i] = original; // restore before mutating next index
                    }
                }
                currentLevel = nextLevel;
            }

            if (found) {
                LinkedList<String> path = new LinkedList<>();
                path.add(endWord);
                backtrack(endWord, beginWord, parents, path, result);
            }
            return result;
        }

        private void backtrack(String word, String beginWord, Map<String, List<String>> parents,
                               LinkedList<String> path, List<List<String>> result) {
            if (word.equals(beginWord)) {
                List<String> seq = new ArrayList<>(path);
                Collections.reverse(seq);
                result.add(seq);
                return;
            }
            for (String parent : parents.getOrDefault(word, Collections.emptyList())) {
                path.addLast(parent);
                backtrack(parent, beginWord, parents, path, result);
                path.removeLast(); // undo
            }
        }
}
