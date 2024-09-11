package algorithms.graph.medium.dfs;

import java.util.ArrayList;
import java.util.List;

/**
 * https://leetcode.com/problems/course-schedule/description
 *
 * There are a total of numCourses courses you have to take, labeled from 0 to numCourses - 1. You are given an array prerequisites where prerequisites[i] = [ai, bi] indicates that you must take course bi first if you want to take course ai.
 * For example, the pair [0, 1], indicates that to take course 0 you have to first take course 1.
 * Return true if you can finish all courses. Otherwise, return false.
 *
 * Example 1:
 * Input: numCourses = 2, prerequisites = [[1,0]]
 * Output: true
 * Explanation: There are a total of 2 courses to take.
 * To take course 1 you should have finished course 0. So it is possible.
 *
 * Example 2:
 * Input: numCourses = 2, prerequisites = [[1,0],[0,1]]
 * Output: false
 * Explanation: There are a total of 2 courses to take.
 * To take course 1 you should have finished course 0, and to take course 0 you should also have finished course 1. So it is impossible.
 * Constraints:
 *
 * 1 <= numCourses <= 2000
 * 0 <= prerequisites.length <= 5000
 * prerequisites[i].length == 2
 * 0 <= ai, bi < numCourses
 * All the pairs prerequisites[i] are unique.
 */

public class graph_dfs02_CanFinishCourses {

    public boolean canFinish(int numCourses, int[][] prerequisites) {
        if(numCourses == 0 || prerequisites == null || prerequisites.length == 0) return true; //??

        // create the array lists to represent the courses
        List<List<Integer>> courses = new ArrayList<List<Integer>>(numCourses);
        for(int i=0; i<numCourses; i++) {
            courses.add(new ArrayList<Integer>());
        }

        // create the dependency graph
        for(int i=0; i<prerequisites.length; i++) {
            courses.get(prerequisites[i][1]).add(prerequisites[i][0]);
        }

        int[] visited = new int[numCourses];

        // dfs visit each course
        for(int i=0; i<numCourses; i++) {
            if (!dfs(i,courses, visited)) return false;
        }

        return true;
    }

    private boolean dfs(int course, List<List<Integer>> courses, int[] visited) {

        visited[course] = 1; // mark it being visited

        List<Integer> eligibleCourses = courses.get(course); // get its children

        // dfs its children
        for(int i=0; i<eligibleCourses.size(); i++) {
            int eligibleCourse = eligibleCourses.get(i).intValue();

            if(visited[eligibleCourse] == 1) return false; // has been visited while visiting its children - cycle !!!!
            if(visited[eligibleCourse]  == 0) { // not visited
                if (!dfs(eligibleCourse,courses, visited)) return false;
            }

        }

        visited[course] = 2; // mark it done visiting
        return true;

    }
}
