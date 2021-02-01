package algorithms.singlyLinkedList.medium;

import algorithms.tree.TreeNode;

public class LList06_BSTtoDoublyLinkedList {
		  // the smallest (first) and the largest (last) nodes
		  TreeNode first = null;
		  TreeNode last = null;

		  public void helper(TreeNode node) {
		    if (node != null) {
		      // left
		      helper(node.left);
		      // node 
		      if (last != null) {
		        // link the previous node (last)
		        // with the current one (node)
		        last.right = node;
		        node.left = last;
		      }
		      else {
		        // keep the smallest node
		        // to close DLL later on
		        first = node;
		      }
		      last = node;
		      // right
		      helper(node.right);
		    }
		  }

		  public TreeNode treeToDoublyList(TreeNode root) {
		    if (root == null) return null;

		    helper(root);
		    // close DLL
		    last.right = first;
		    first.left = last;
		    return first;
		  }
		}