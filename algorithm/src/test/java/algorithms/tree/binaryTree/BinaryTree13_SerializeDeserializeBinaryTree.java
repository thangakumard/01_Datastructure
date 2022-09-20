package algorithms.tree.binaryTree;

import java.util.*;

import algorithms.tree.TreeNode;
import org.assertj.core.api.Assertions;
import org.junit.Test;

/*
 * https://leetcode.com/problems/serialize-and-deserialize-binary-tree/
 * 
 * Serialization is the process of converting a data structure or object into a sequence of bits so that it can be stored in a file or memory buffer, or transmitted across a network connection link to be reconstructed later in the same or another computer environment.

	Design an algorithm to serialize and deserialize a binary tree. There is no restriction on how your serialization/deserialization algorithm should work. You just need to ensure that a binary tree can be serialized to a string and this string can be deserialized to the original tree structure.
	
	Clarification: The input/output format is the same as how LeetCode serializes a binary tree. You do not necessarily need to follow this format, so please be creative and come up with different approaches yourself.
	
	 
	
	Example 1:
	
	
	Input: root = [1,2,3,null,null,4,5]
	Output: [1,2,3,null,null,4,5]
	Example 2:
	
	Input: root = []
	Output: []
	Example 3:
	
	Input: root = [1]
	Output: [1]
	Example 4:
	
	Input: root = [1,2]
	Output: [1,2]
 */

public class BinaryTree13_SerializeDeserializeBinaryTree {
	@Test
	public void SerializeDeserializeTest(){
		TreeNode root = new TreeNode(10);
		root.left = new TreeNode(5);
		root.right = new TreeNode(15);
		root.right.right = new TreeNode(30);
		String serializeTree = serialize(root);
		TreeNode resultTree = deserialize(serializeTree);
//		Assertions.assertThat(resultTree).isEqualTo(root);
	}
	private final String spliter = ",";
	private final String nullNode = "NULL";

	// Encodes a tree to a single string.
	public String serialize(TreeNode root) {
		StringBuilder sb = new StringBuilder();
		buildtreeString(root, sb);
		return sb.toString();
	}

	private void buildtreeString(TreeNode node, StringBuilder sb) {
		if (node == null)
			sb.append(nullNode).append(spliter);
		else {
			sb.append(node.data).append(spliter);
			buildtreeString(node.left, sb);
			buildtreeString(node.right, sb);
		}
	}

	// Decodes your encoded data to tree.
	public TreeNode deserialize(String data) {
		Deque<String> nodes = new LinkedList<>();
		nodes.addAll(Arrays.asList(data.split(spliter)));
		return buildTree(nodes);
	}

	private TreeNode buildTree(Deque<String> nodes) {
		String val = nodes.remove();
		if (val.equals(nullNode))
			return null;
		else {
			TreeNode node = new TreeNode(Integer.valueOf(val));
			node.left = buildTree(nodes);
			node.right = buildTree(nodes);
			return node;
		}
	}

}
