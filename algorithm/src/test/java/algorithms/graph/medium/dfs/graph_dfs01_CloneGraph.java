package algorithms.graph.medium.dfs;

import algorithms.graph.NodeWithNeighbors;

import java.util.ArrayList;
import java.util.HashMap;

public class graph_dfs01_CloneGraph {
    private HashMap<NodeWithNeighbors, NodeWithNeighbors> visited = new HashMap<>();

    public NodeWithNeighbors cloneGraph(NodeWithNeighbors node) {
        if(node == null) return null;
        if(visited.containsKey(node)){
            return visited.get(node);
        }
        NodeWithNeighbors cloneNode = new NodeWithNeighbors(node.val, new ArrayList<>());
        visited.put(node, cloneNode);

        for(NodeWithNeighbors neigbhor: node.neighbors){
            cloneNode.neighbors.add(cloneGraph(neigbhor));
        }
        return cloneNode;
    }
}
