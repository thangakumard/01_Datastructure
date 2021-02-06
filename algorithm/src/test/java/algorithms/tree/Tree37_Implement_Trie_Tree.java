package algorithms.tree;

import org.testng.annotations.Test;

public class Tree37_Implement_Trie_Tree {

    @Test
	public void test() {
    	Tree37_Implement_Trie_Tree tree = new Tree37_Implement_Trie_Tree();
    	tree.insert("apple");
//    	System.out.println("Apple :" + tree.search("apple"));
//    	System.out.println("Act :" + tree.search("act"));
    	tree.insert("act");
    	System.out.println("Apple :" + tree.search("apple"));
    	System.out.println("Act :" + tree.search("act"));
	}
	
	
    TrieNode root;
    
    /** Initialize your data structure here. */
    public Tree37_Implement_Trie_Tree() {
        root = new TrieNode();    
    }
    
    /** Inserts a word into the trie. */
    public void insert(String word) {
        TrieNode node = root;
        for(int i=0 ; i < word.length(); i++){
            if(!node.containsKey(word.charAt(i))){
                node.put(word.charAt(i), new TrieNode());
            }
            node = node.get(word.charAt(i));
        }
        node.setEnd();
    }
    private TrieNode searchPrefix(String prefix){
        TrieNode node = root;
        for(int i=0;i < prefix.length(); i++){
            if(node.containsKey(prefix.charAt(i))){
               node = node.get(prefix.charAt(i)) ;
            }
            else{
                return null;
            }
        }
        return node;
    }
    
    /** Returns if the word is in the trie. */
    public boolean search(String word) {
        TrieNode node = searchPrefix(word);
        return node != null && node.isEnd();
    }
    
    /** Returns if there is any word in the trie that starts with the given prefix. */
    public boolean startsWith(String prefix) {
        TrieNode node = searchPrefix(prefix);
        return node != null;
    }
}
