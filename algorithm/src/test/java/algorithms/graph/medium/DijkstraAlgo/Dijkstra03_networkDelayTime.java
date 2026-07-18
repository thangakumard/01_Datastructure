package algorithms.graph.medium.DijkstraAlgo;

import java.util.*;

/***
 * https://leetcode.com/problems/network-delay-time
 *
 * Complexity
 * ===========
 * Time Complexity: O((V + E) log V) where V = n (nodes) and E = times.length (edges)
 *================
 * Each node is inserted into the heap at most once per edge update: O(E) insertions
 * Each heap operation (push/pop) is O(log V)
 * Edge relaxation: O(E) total
 * Overall: O(E log V) for heap operations + O(V) for initialization = O((V + E) log V)
 * In this problem: O((n + 6000) log n) ≈ O(6000 log 100)
 *
 * Space Complexity: O(V + E)
 *=================
 * graph adjacency list: O(V + E) — V nodes + E edges
 * dist array: O(V)
 * Priority queue: O(E) — worst case all edges are in queue
 * Total: O(V + E) or O(n + 6000)
 */
public class Dijkstra03_networkDelayTime {
    public int networkDelayTime(int[][] times, int N, int K) {
        Map<Integer, List<int[]>> mapDelay = new HashMap<>();

        for(int[] edge : times){
            mapDelay.computeIfAbsent(edge[0], value -> new ArrayList<>()).add(new int[] {edge[1], edge[2]});
        }
        int res = Integer.MIN_VALUE;
        //map for our question's usecase. Key is node, value is distance
        //from origin node K. It also acts as a visited set
        Map<Integer,Integer> mapVisited = new HashMap<>();

        PriorityQueue<int[]> minQueue = new PriorityQueue<>((a, b) -> a[1]-b[1]);
        minQueue.add(new int[]{K, 0});

        while(!minQueue.isEmpty()){

            int[] cur = minQueue.poll();
            int node = cur[0];
            int delay = cur[1];

            if(mapVisited.containsKey(node)) continue;
            mapVisited.put(node, delay);

            res = Math.max(res, delay);

            if(mapDelay.containsKey(node)){
                for(int[] nei : mapDelay.get(node)){
                    int neiNode = nei[0]; int neiDelay = nei[1];
                    if(!mapVisited.containsKey(neiNode)){
                        minQueue.add(new int[]{neiNode, neiDelay + delay});
                    }
                }
            }
        }
        return mapVisited.size() == N ? res : -1;
    }
}
