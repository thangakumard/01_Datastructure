package algorithms.graph.medium.dfs;

import algorithms.graph.NodeWithNeighbors;

import java.util.ArrayList;
import java.util.HashMap;
/**
 * https://leetcode.com/problems/clone-graph/description/
 * Given a reference of a node in a connected undirected graph.
 * Return a deep copy (clone) of the graph.
 * Each node in the graph contains a value (int) and a list (List[Node]) of its neighbors.
 *
 * class Node {
 *     public int val;
 *     public List<Node> neighbors;
 * }

 * Test case format:
 *
 * For simplicity, each node's value is the same as the node's index (1-indexed). For example, the first node with val == 1, the second node with val == 2, and so on. The graph is represented in the test case using an adjacency list.
 *
 * An adjacency list is a collection of unordered lists used to represent a finite graph. Each list describes the set of neighbors of a node in the graph.
 *
 * The given node will always be the first node with val = 1. You must return the copy of the given node as a reference to the cloned graph.
 * Example 1:
 * ==========
 * Input: adjList = [[2,4],[1,3],[2,4],[1,3]]
 * Output: [[2,4],[1,3],[2,4],[1,3]]
 * Explanation: There are 4 nodes in the graph.
 * 1st node (val = 1)'s neighbors are 2nd node (val = 2) and 4th node (val = 4).
 * 2nd node (val = 2)'s neighbors are 1st node (val = 1) and 3rd node (val = 3).
 * 3rd node (val = 3)'s neighbors are 2nd node (val = 2) and 4th node (val = 4).
 * 4th node (val = 4)'s neighbors are 1st node (val = 1) and 3rd node (val = 3).
 * Example 2:
 * ===========
 * Input: adjList = [[]]
 * Output: [[]]
 * Explanation: Note that the input contains one empty list. The graph consists of only one node with val = 1 and it does not have any neighbors.
 * Example 3:
 *
 * Input: adjList = []
 * Output: []
 * Explanation: This an empty graph, it does not have any nodes.
 *
 * Constraints:
 *
 * The number of nodes in the graph is in the range [0, 100].
 * 1 <= Node.val <= 100
 * Node.val is unique for each node.
 * There are no repeated edges and no self-loops in the graph.
 * The Graph is connected and all nodes can be visited starting from the given node.
 */
/**
 * Complexity
 * ==========
 * Time: O(V + E) — visit every node once, every edge once.
 * Space: O(V) for the hash map plus recursion stack depth.
 */
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
    /**=============== TESTING =============**/
    public static void main(String[] args) {
        testSingleNode();
        testSimpleGraph();
        testNullInput();
        testGraphWithCycle();
        System.out.println("All tests passed!");
    }

    // Test 1: single node, no neighbors
    private static void testSingleNode() {
        Node original = new Node(1);
        Node cloned = cloneGraph(original);

        assertTrue(cloned != null, "clone should not be null");
        assertTrue(cloned != original, "clone must be a different object");
        assertTrue(cloned.val == original.val, "values should match");
        assertTrue(cloned.neighbors.isEmpty(), "no neighbors expected");
    }

    // Test 2: small connected graph, e.g. 1-2-3-1 triangle
    private static void testSimpleGraph() {
        Node n1 = new Node(1);
        Node n2 = new Node(2);
        Node n3 = new Node(3);
        n1.neighbors.addAll(List.of(n2, n3));
        n2.neighbors.addAll(List.of(n1, n3));
        n3.neighbors.addAll(List.of(n1, n2));

        Node clonedStart = cloneGraph(n1);

        // structural check via BFS, comparing values while ensuring
        // no cloned node is reference-equal to an original node
        Set<Node> originalSeen = Collections.newSetFromMap(new IdentityHashMap<>());
        Set<Node> clonedSeen = Collections.newSetFromMap(new IdentityHashMap<>());
        Deque<Node[]> queue = new ArrayDeque<>();
        queue.add(new Node[]{n1, clonedStart});
        originalSeen.add(n1);
        clonedSeen.add(clonedStart);

        int visitedCount = 0;
        while (!queue.isEmpty()) {
            Node[] pair = queue.poll();
            Node orig = pair[0], copy = pair[1];
            visitedCount++;

            assertTrue(orig != copy, "clone node must not be same reference as original");
            assertTrue(orig.val == copy.val, "values must match");
            assertTrue(orig.neighbors.size() == copy.neighbors.size(), "neighbor count must match");

            for (int i = 0; i < orig.neighbors.size(); i++) {
                Node oNeigh = orig.neighbors.get(i);
                Node cNeigh = copy.neighbors.get(i);
                assertTrue(cNeigh != oNeigh, "neighbor reference must be cloned, not original"); // catches the bug above
                if (!originalSeen.contains(oNeigh)) {
                    originalSeen.add(oNeigh);
                    clonedSeen.add(cNeigh);
                    queue.add(new Node[]{oNeigh, cNeigh});
                }
            }
        }
        assertTrue(visitedCount == 3, "should visit exactly 3 nodes");
    }

    // Test 3: null input
    private static void testNullInput() {
        assertTrue(cloneGraph(null) == null, "cloning null should return null");
    }

    // Test 4: mutate the clone, verify original is untouched (proves deep copy)
    private static void testGraphWithCycle() {
        Node a = new Node(1);
        a.neighbors.add(a); // self-loop
        Node clonedA = cloneGraph(a);

        assertTrue(clonedA != a, "self-loop node must be cloned");
        assertTrue(clonedA.neighbors.get(0) == clonedA, "cloned self-loop should point to itself, not original");

        clonedA.val = 999;
        assertTrue(a.val == 1, "mutating clone must not affect original — proves deep copy");
    }

    private static void assertTrue(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError("FAILED: " + message);
        }
        System.out.println("PASS: " + message);
    }
}
