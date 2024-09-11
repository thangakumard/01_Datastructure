package algorithms.tree.Trie;

public class TrieNode{
    
    private TrieNode[] Nodes;
    public TrieNode(){
        Nodes = new TrieNode[26];
    }
    public void put(char ch, TrieNode node){
        Nodes[ch-'a'] = node;
    }
    public boolean containsKey(char ch){
        return Nodes[ch-'a'] != null;
    }
    public TrieNode get(char ch){
        return Nodes[ch-'a'];
    }

    private boolean isEnd = false;
    public void setEnd(){
        this.isEnd = true;
    }
    public boolean isEnd(){
        return this.isEnd;
    }
}
