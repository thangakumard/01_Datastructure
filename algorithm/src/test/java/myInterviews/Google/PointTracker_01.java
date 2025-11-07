package myInterviews.Google;

import java.util.ArrayList;
import java.util.List;

/**
 * In Java Create a class with the following methods.
 * int[] addPoint(int x, int y) , int[] removePoint(int x, int y) methods.
 * The return array int[] should be with the size of 4.
 * int[0] => min of X
 * int[1] => min of y
 * int[2] => max of x
 * int[3] => max of y
 *
 *
 * addPoint(2,3) //output should be {2,3,2,3}
 * addPoint(5,6) //output should be {2,3,5,6}
 * addPoint(7,10) //output should be {2,3,7,10}
 * removePoint(2,3) //output should be {5,6,7,10}xPointTracker
 */

public class PointTracker_01 {
    private List<int[]> points;

    public PointTracker_01() {
        points = new ArrayList<>();
    }

    // Add a point and return the updated min/max values
    public int[] addPoint(int x, int y) {
        points.add(new int[]{x, y});
        return calculateBounds();
    }

    // Remove a point and return the updated min/max values
    public int[] removePoint(int x, int y) {
        points.removeIf(point -> point[0] == x && point[1] == y);
        return calculateBounds();
    }

    // Calculate the min and max values of x and y
    private int[] calculateBounds() {
        if (points.isEmpty()) {
            throw new IllegalStateException("No points available to calculate bounds.");
        }

        int minX = Integer.MAX_VALUE;
        int minY = Integer.MAX_VALUE;
        int maxX = Integer.MIN_VALUE;
        int maxY = Integer.MIN_VALUE;

        for (int[] point : points) {
            int x = point[0];
            int y = point[1];
            minX = Math.min(minX, x);
            minY = Math.min(minY, y);
            maxX = Math.max(maxX, x);
            maxY = Math.max(maxY, y);
        }

        return new int[]{minX, minY, maxX, maxY};
    }

    public static void main(String[] args) {
        PointTracker_01 tracker = new PointTracker_01();

        // Test cases
        System.out.println(java.util.Arrays.toString(tracker.addPoint(2, 3))); // Output: {2, 3, 2, 3}
        System.out.println(java.util.Arrays.toString(tracker.addPoint(5, 6))); // Output: {2, 3, 5, 6}
        System.out.println(java.util.Arrays.toString(tracker.addPoint(7, 10))); // Output: {2, 3, 7, 10}
        System.out.println(java.util.Arrays.toString(tracker.removePoint(2, 3))); // Output: {5, 6, 7, 10}
    }
}

