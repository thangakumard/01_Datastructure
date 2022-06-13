package algorithms.tree.binaryTree;

import algorithms.tree.TreeNode;

import java.util.HashMap;
import java.util.PriorityQueue;

public class BinaryTree44_LeafToRootSmallString {
    private int minPathNumber = Integer.MAX_VALUE;

    private PriorityQueue<String> Paths = new PriorityQueue<>();
    HashMap<Integer,String> map = new HashMap<Integer,String>();

    public String smallestFromLeaf(TreeNode root) {
        map.put(0,"a");map.put(1,"b");map.put(2,"c");map.put(3,"d");map.put(4,"e");
        map.put(5,"f");map.put(6,"g");map.put(7,"h");map.put(8,"i");map.put(9,"j");
        map.put(10,"k");map.put(11,"l");map.put(12,"m");map.put(13,"n");map.put(14,"o");
        map.put(15,"p");map.put(16,"q");map.put(17,"r");map.put(18,"s");map.put(19,"t");
        map.put(20,"u");map.put(21,"v");map.put(22,"w");map.put(23,"x");map.put(24,"y");
        map.put(25,"z");
        getPathNumber(root, "");
        return Paths.poll();
    }


    private void getPathNumber(TreeNode root, String currentPath){
        if(root == null)
            return;

        currentPath = map.get(root.data) + currentPath ;
        if(root.left == null && root.right == null){
            Paths.add(currentPath);
            return;
        }

        if(root.left != null)
            getPathNumber(root.left, currentPath);
        if(root.right != null)
            getPathNumber(root.right, currentPath);
    }
}
