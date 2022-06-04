package algorithms.tree.binaryTree;

import org.testng.annotations.Test;

import algorithms.tree.BinaryTree;
import algorithms.tree.TreeNode;

/*
 * https://leetcode.com/problems/lowest-common-ancestor-of-a-binary-tree-iii
 * 
 */

public class BinaryTree04_LCA_LowestCommonAncestor_II {

/*
// Definition for a Node.
class Node {
    public int val;
    public Node left;
    public Node right;
    public Node parent;
};
*/

class Solution {
    public Node lowestCommonAncestor(Node p, Node q) {
        Set<Node> pSet = new HashSet<>();
        
        while(p != null){
            pSet.add(p);
            p = p.parent;
        }
        
        while(q != null){
            if(pSet.contains(q)){
                return q;
            }
            pSet.add(q);
            q = q.parent;
        }
        
        return null;
    }
}
}
