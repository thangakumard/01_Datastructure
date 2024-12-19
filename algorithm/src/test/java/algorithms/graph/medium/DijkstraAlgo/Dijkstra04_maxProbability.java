package algorithms.graph.medium.DijkstraAlgo;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

/**
 * https://leetcode.com/problems/path-with-maximum-probability
 *
 */
public class Dijkstra04_maxProbability {
    public double maxProbability(int n, int[][] edges, double[] succProb, int start, int end) {
        Map<Integer, Map<Integer, Double>> mapPath = new HashMap<>();
        for (int i = 0; i < edges.length; ++i) {
            int[] e = edges[i];
            mapPath.computeIfAbsent(e[0], m -> new HashMap<>()).put(e[1], succProb[i]);
            mapPath.computeIfAbsent(e[1], m -> new HashMap<>()).put(e[0], succProb[i]);
        }
        PriorityQueue<double[]> minQueue = new PriorityQueue<>(Comparator.comparingDouble(a -> -a[0]));
        double[] prob = new double[n];
        minQueue.offer(new double[] { 1, start });
        while (!minQueue.isEmpty()) {
            double[] cur = minQueue.poll();
            int v = (int) cur[1];
            if (v == end) {
                return cur[0];
            }
            if (cur[0] > prob[v]) {
                prob[v] = cur[0];
                for (var entry : mapPath.getOrDefault(v, Map.of()).entrySet()) {
                    int nb = entry.getKey();
                    double p = entry.getValue();
                    minQueue.offer(new double[] { cur[0] * p, nb });
                }
            }
        }
        return 0d;
    }
}
