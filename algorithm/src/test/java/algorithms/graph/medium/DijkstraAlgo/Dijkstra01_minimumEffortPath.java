package algorithms.graph.medium.DijkstraAlgo;

import java.util.*;

public class Dijkstra01_minimumEffortPath {
    private int rows;
    private int cols;

    public int minimumEffortPath(int[][] heights) {
        rows = heights.length;
        cols = heights[0].length;
        int[][] minEffort = new int[rows][cols];

        for(int i=0; i < rows; i++){
            Arrays.fill(minEffort[i], -1);
        }

        PriorityQueue<int[]> pqueue = new PriorityQueue<>(Comparator.comparingInt(a -> a[2]));
        pqueue.offer(new int[]{0,0,0});

        while (!pqueue.isEmpty()) {
            int[] current = pqueue.poll();

            int row = current[0];
            int col = current[1];
            int effort = current[2];

            if(minEffort[row][col] != -1) continue;

            minEffort[row][col] = effort;

            if(row == rows-1 && col == cols-1){
                return minEffort[row][col];
            }

            List<int[]> neighbors = getNeighbors(heights, row, col);

            for(int[] neighbor: neighbors){
                int nextRow = neighbor[0];
                int nextCol = neighbor[1];
                if(minEffort[nextRow][nextCol] == -1){
                    int weight = Math.abs(heights[row][col] - heights[nextRow][nextCol]);
                    weight = Math.max(weight, effort);
                    pqueue.offer( new int[] {nextRow, nextCol, weight});
                }
            }
        }

        return -1;

    }

    private List<int[]> getNeighbors(int[][] heights, int row, int col){
        List<int[]> neighbors = new ArrayList<>();
        if(isValid(row-1, col)) neighbors.add(new int[]{row-1, col});
        if(isValid(row+1, col)) neighbors.add(new int[]{row+1, col});
        if(isValid(row, col-1)) neighbors.add(new int[]{row, col-1});
        if(isValid(row, col+1)) neighbors.add(new int[]{row, col+1});

        return neighbors;
    }

    private boolean isValid(int row, int col){
        return (row >= 0 && row < rows && col >= 0 && col < cols);
    }
}
