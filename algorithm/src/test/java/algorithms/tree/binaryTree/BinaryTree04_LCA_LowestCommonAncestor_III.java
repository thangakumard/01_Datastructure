package algorithms.tree.binaryTree;

import java.util.HashSet;
import java.util.Set;

/*
 * https://leetcode.com/problems/lowest-common-ancestor-of-a-binary-tree-iii
 * 
 */


class Node {
    public int val;
    public Node left;
    public Node right;
    public Node parent;
    public Node(int v){
        val = v;
    }
};

public class BinaryTree04_LCA_LowestCommonAncestor_III {
    public Node lowestCommonAncestor(Node p, Node q) {
         HashSet<Node> pAncestor = new HashSet<>();
        
        while(p != null){
            pAncestor.add(p);
            p = p.parent;
        }
        
        while(q != null){
            if(pAncestor.contains(q)){
                return q;
            }
            q = q.parent;
        }
        
        return null;
    }
}
