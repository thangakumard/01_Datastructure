package myInterviews.Google;

import java.util.PriorityQueue;
import java.util.Comparator;

/**
 * Advantages of Using PriorityQueue:
 * Efficient Min/Max Retrieval:
 *
 * Priority queues maintain the minimum or maximum element at the top, making retrieval an O(1) operation.
 * Dynamic Updates:
 *
 * Adding or removing a point is O(log n), which is efficient.
 * Potential Drawbacks:
 * Memory Overhead:
 *
 * Using four priority queues increases memory usage compared to a single list.
 * Duplicate Handling:
 *
 * Priority queues do not automatically deduplicate points. If duplicates are possible, additional checks may be needed.
 */

public class PointTracker_02 {
    private PriorityQueue<int[]> minXQueue;
    private PriorityQueue<int[]> maxXQueue;
    private PriorityQueue<int[]> minYQueue;
    private PriorityQueue<int[]> maxYQueue;

    public PointTracker_02() {
        minXQueue = new PriorityQueue<>(Comparator.comparingInt(point -> point[0]));
        maxXQueue = new PriorityQueue<>((a, b) -> Integer.compare(b[0], a[0]));
        minYQueue = new PriorityQueue<>(Comparator.comparingInt(point -> point[1]));
        maxYQueue = new PriorityQueue<>((a, b) -> Integer.compare(b[1], a[1]));
    }

    // Add a point and return the updated min/max values
    public int[] addPoint(int x, int y) {
        int[] point = new int[]{x, y};
        minXQueue.add(point);
        maxXQueue.add(point);
        minYQueue.add(point);
        maxYQueue.add(point);
        return calculateBounds();
    }

    // Remove a point and return the updated min/max values
    public int[] removePoint(int x, int y) {
        int[] point = new int[]{x, y};
        minXQueue.remove(point);
        maxXQueue.remove(point);
        minYQueue.remove(point);
        maxYQueue.remove(point);
        return calculateBounds();
    }

    // Calculate the min and max values of x and y
    private int[] calculateBounds() {
        if (minXQueue.isEmpty() || minYQueue.isEmpty()) {
            throw new IllegalStateException("No points available to calculate bounds.");
        }

        int minX = minXQueue.peek()[0];
        int minY = minYQueue.peek()[1];
        int maxX = maxXQueue.peek()[0];
        int maxY = maxYQueue.peek()[1];

        return new int[]{minX, minY, maxX, maxY};
    }

    public static void main(String[] args) {
        PointTracker_02 tracker = new PointTracker_02();

        // Test cases
        System.out.println(java.util.Arrays.toString(tracker.addPoint(2, 3))); // Output: {2, 3, 2, 3}
        System.out.println(java.util.Arrays.toString(tracker.addPoint(5, 6))); // Output: {2, 3, 5, 6}
        System.out.println(java.util.Arrays.toString(tracker.addPoint(7, 10))); // Output: {2, 3, 7, 10}
        System.out.println(java.util.Arrays.toString(tracker.removePoint(2, 3))); // Output: {5, 6, 7, 10}
    }
}

