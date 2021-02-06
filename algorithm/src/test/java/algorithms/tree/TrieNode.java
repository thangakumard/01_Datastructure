package algorithms.tree;

public class TrieNode{
    
    private TrieNode[] Nodes;
        
    private boolean isEnd = false;
    
    public TrieNode(){
        Nodes = new TrieNode[26];
    }
    
    public boolean containsKey(char ch){
        return Nodes[ch-'a'] != null;
    }
    
    public void put(char ch, TrieNode node){
        Nodes[ch-'a'] = node;        
    }
    
    public TrieNode get(char ch){
        return Nodes[ch-'a'];
    }
    
    public void setEnd(){
        this.isEnd = true;
    }
    
    public boolean isEnd(){
        return this.isEnd;
    }
}
