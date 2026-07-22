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

/***
 * Kahn's algorithm finds a topological order of a directed acyclic graph (DAG)
 *  — an ordering of nodes where every edge u→v has u appearing before v.
 *      It's the BFS-based approach to topological sorting (the alternative being DFS + reverse postorder).
 * The core idea
 *==============
 * Compute the in-degree (number of incoming edges) for every node.
 * Push all nodes with in-degree 0 into a queue — these have no prerequisites, so they can go first.
 * Repeatedly pop a node from the queue, add it to the result, and "remove" its outgoing edges by decrementing the in-degree of each neighbor.
 * Whenever a neighbor's in-degree hits 0, push it into the queue.
 * If the result contains all nodes → valid topological order. If it contains fewer nodes than the graph → there's a cycle (this is Kahn's built-in cycle detector).
 */
public class canFinishCourse {
    public class Main {
        public static void main(String[] args) {
            Solution sol = new Solution();

            // Test 1: simple valid case, 1 -> 0
            int numCourses1 = 2;
            int[][] prereq1 = {{1, 0}};
            System.out.println("Test 1 (expect true): " + sol.canFinish(numCourses1, prereq1));

            // Test 2: cycle 0 -> 1 -> 0
            int numCourses2 = 2;
            int[][] prereq2 = {{1, 0}, {0, 1}};
            System.out.println("Test 2 (expect false): " + sol.canFinish(numCourses2, prereq2));

            // Test 3: no prerequisites at all
            int numCourses3 = 3;
            int[][] prereq3 = {};
            System.out.println("Test 3 (expect true): " + sol.canFinish(numCourses3, prereq3));

            // Test 4: larger valid chain 0<-1<-2<-3
            int numCourses4 = 4;
            int[][] prereq4 = {{1, 0}, {2, 1}, {3, 2}};
            System.out.println("Test 4 (expect true): " + sol.canFinish(numCourses4, prereq4));

            // Test 5: larger cycle 0->1->2->0
            int numCourses5 = 3;
            int[][] prereq5 = {{1, 0}, {2, 1}, {0, 2}};
            System.out.println("Test 5 (expect false): " + sol.canFinish(numCourses5, prereq5));

            // Test 6: disconnected components, one with a cycle
            int numCourses6 = 5;
            int[][] prereq6 = {{1, 0}, {3, 2}, {2, 3}}; // 0-1 fine, 2-3 cycle
            System.out.println("Test 6 (expect false): " + sol.canFinish(numCourses6, prereq6));
        }
    }
    public boolean canFinish(int numCourses, int[][] prerequisites) {
        if(numCourses == 0) return true;
        List<List<Integer>> graph = new ArrayList<>();
        int[] inDegree = new int[numCourses];
        int totalCompletedCourses = 0;

        //1.Build an empty graph
        for(int i=0; i < numCourses; i++){
            graph.add(new ArrayList<>());
        }

        //2.Fill the graph and inDegree
        for(int i=0; i < prerequisites.length; i++){
            int course = prerequisites[i][1];
            int prerequestCourse = prerequisites[i][0];

            graph.get(prerequestCourse).add(course);
            inDegree[course]++;
        }

        //3.Build Queue with inDegree == 0 courses
        Queue<Integer> queue = new LinkedList<>();
        for(int i=0; i < inDegree.length; i++){
            if(inDegree[i] == 0){
                queue.offer(i);
            }
        }

        //4.Process Queue
        while(!queue.isEmpty()){
            int prerequestCourse = queue.poll();
            totalCompletedCourses++;

            List<Integer> courses = graph.get(prerequestCourse);
            for(int course: courses){
                inDegree[course]--;
                if(inDegree[course] == 0){
                    queue.offer(course);
                }
            }
        }
        //5. Return TRUE if totalCompletedCourses equals numCourses
        return totalCompletedCourses == numCourses;
    }
}
