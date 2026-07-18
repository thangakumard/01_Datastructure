package algorithms.graph.medium.kahn_topological_sort;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
There are a total of numCourses courses you have to take, labeled from 0 to numCourses - 1. You are given an array prerequisites where prerequisites[i] = [ai, bi] indicates that you must take course bi first if you want to take course ai.

For example, the pair [0, 1], indicates that to take course 0 you have to first take course 1.
Return true if you can finish all courses. Otherwise, return false.



Example 1:

Input: numCourses = 2, prerequisites = [[1,0]]
Output: true
Explanation: There are a total of 2 courses to take.
To take course 1 you should have finished course 0. So it is possible.
Example 2:

Input: numCourses = 2, prerequisites = [[1,0],[0,1]]
Output: false
Explanation: There are a total of 2 courses to take.
To take course 1 you should have finished course 0, and to take course 0 you should also have finished course 1. So it is impossible.


Constraints:

1 <= numCourses <= 2000
0 <= prerequisites.length <= 5000
prerequisites[i].length == 2
0 <= ai, bi < numCourses
All the pairs prerequisites[i] are unique.
 */
/**
 * Time O(V + E) V = numCourses, E = number of prerequisites.
 *      We visit each node once (V) and process each edge once (E) during graph traversal.
 * SpaceO(V + E)
 *      Graph adjacency list: O(V + E).
 *      In-degree array: O(V). Queue: O(V) in worst case. Total: O(V + E).
 */
public class canFinishCourse {
    public boolean canFinish(int numCourses, int[][] prerequisites) {
        List<List<Integer>> graph = new ArrayList<>();
        int[] inDegree = new int[numCourses];
        int processedCount = 0;

        //build an empty graph
        for (int i = 0; i < numCourses; i++) {
            graph.add(new ArrayList<>());
        }

        //Complete the graph with and count inDegree
        for (int[] prereq : prerequisites) {
            int course = prereq[0];
            int prereqCourse = prereq[1];
            graph.get(prereqCourse).add(course);
            inDegree[course]++;
        }

        Queue<Integer> queue = new LinkedList<>();
        //add inDegree == 0 into the queue
        for (int i = 0; i < numCourses; i++) {
            if (inDegree[i] == 0) {
                queue.offer(i);
            }
        }

        //process the queue
        while (!queue.isEmpty()) {
            int prereqCourse = queue.poll();
            processedCount++;
            List<Integer> courses = graph.get(prereqCourse);

            for (int course : courses) {
                inDegree[course]--;
                if (inDegree[course] == 0) {
                    queue.offer(course);
                }
            }
        }

        return processedCount == numCourses;
    }
}
