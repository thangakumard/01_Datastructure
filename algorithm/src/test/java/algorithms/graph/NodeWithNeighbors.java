package algorithms.graph;

import java.util.ArrayList;
import java.util.List;

public class NodeWithNeighbors {
    public int val;
    public List<NodeWithNeighbors> neighbors;
    public NodeWithNeighbors() {
        val = 0;
        neighbors = new ArrayList<NodeWithNeighbors>();
    }
    public NodeWithNeighbors(int _val) {
        val = _val;
        neighbors = new ArrayList<NodeWithNeighbors>();
    }
    public NodeWithNeighbors(int _val, ArrayList<NodeWithNeighbors> _neighbors) {
        val = _val;
        neighbors = _neighbors;
    }
}
