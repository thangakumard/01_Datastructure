package algorithms.tree.binaryTree;

import algorithms.tree.TreeNode;
import org.testng.internal.collections.Pair;

import java.util.Stack;

/***
 * https://leetcode.com/problems/sum-root-to-leaf-numbers/
 */
public class BinaryTree43_PathsumNumbers {
     private int totalSum = 0;


        public int solution1_recursive(TreeNode root) {
            if(root == null) return 0;
            getPathNumber(root, 0);
            return totalSum;
        }

        private void getPathNumber(TreeNode root, int number){
            if(root == null)
                return;
            int levelSum = number * 10 + root.data;
            if(root.right == null && root.left == null){
                totalSum += levelSum;
                return;
            }

            if(root.left != null) getPathNumber(root.left,  levelSum);
            if(root.right != null) getPathNumber(root.right, levelSum);
        }

    public int solution2_preOrder_Iterative(TreeNode root) {

        Stack<Pair<TreeNode, Integer>> stack = new Stack<>();
        int totalSum = 0;
        int currentSum = 0;
        stack.push(new Pair(root, 0));

        while(!stack.isEmpty()){
            Pair<TreeNode, Integer> currentPair = stack.pop();
            TreeNode currentNode = currentPair.first();
            currentSum = currentPair.second();

            currentSum = currentSum * 10 + currentNode.data;

            if(currentNode.left == null && currentNode.right == null){
                totalSum += currentSum;
            }

            if(currentNode.right != null){
                stack.add(new Pair(currentNode.right, currentSum));
            }
            if(currentNode.left != null){
                stack.add(new Pair(currentNode.left, currentSum));
            }

        }

        return totalSum;
    }
    }
