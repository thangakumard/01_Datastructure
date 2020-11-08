package algorithms.tree;

import java.util.*;

public class Tree20_Trie_Tree_Practise {
	
	private class TrieNode{
		Map<Character, TrieNode> children;
		boolean endOfWord;
		public TrieNode(){
			children = new HashMap<>();
			endOfWord = false;
		}
	}
	
	private final TrieNode root;
	public Tree20_Trie_Tree_Practise(){
		root = new TrieNode();
	}
	
	
	/* Insertion */
	
	
	public void insert(String word){
		TrieNode current = root;
		char c;
		TrieNode node = null;
		for(int i=0; i < word.length(); i++){
			c = word.charAt(i);
			node = current.children.get(c);
			
			if(node == null){
				
				node = new TrieNode();
				current.children.put(c, node);				
			}
			current = node;			
		}		
		current.endOfWord = true;
	}
	
	
	public boolean search(String word){
		TrieNode current = root;
		char c;
		TrieNode node = null;
		for(int i=0; i < word.length();i++ ){
			c = word.charAt(i);
			node = current.children.get(c);
			if(node == null){
				return false;
			}
			current = node;
		}			
		return current.endOfWord;
	}
	
	
	public boolean searchRecursive(String word){
		return searchRecursive(root, word, 0 );
	}
	
	public boolean searchRecursive(TrieNode currentNode, String word, int index){
		if(currentNode == null)
			return false;
		if(index == word.length()){
			return currentNode.endOfWord;
		}
		char c = word.charAt(index);
		TrieNode node = currentNode.children.get(c);
		if(node == null) {
			return false;
		}
		
		return searchRecursive(node,word,index+1);
	}
	
	public boolean delete(String word){
		return delete(root, word, 0);
	}
	
	public boolean delete(TrieNode currentNode, String word, int index){
		if(index == word.length()){
			if(!currentNode.endOfWord){
				return currentNode.endOfWord;
			}
			currentNode.endOfWord = false;
			return currentNode.children.size() == 0;
		}
		char c = word.charAt(index);
		
		TrieNode node = currentNode.children.get(c);
		if(node == null){
			return false;
		}
		boolean shouldDelete = delete(node, word, index+1);
		
		if(shouldDelete){
			currentNode.children.remove(c);
			return currentNode.children.size() == 0;
		}
		
		return false;
	}
}
