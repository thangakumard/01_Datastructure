package algorithms.tree;

public class TreeNodeWithParent {
    public int data, height;
    public TreeNodeWithParent left, right;
    public int val;
    public TreeNodeWithParent parent;

    public TreeNodeWithParent(int item){
        data = item;
        left = right = null;
    }
}
