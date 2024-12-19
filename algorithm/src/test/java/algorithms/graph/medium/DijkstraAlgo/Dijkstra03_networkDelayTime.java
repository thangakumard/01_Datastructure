package algorithms.graph.medium.DijkstraAlgo;

import java.util.*;

/***
 * https://leetcode.com/problems/network-delay-time
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
