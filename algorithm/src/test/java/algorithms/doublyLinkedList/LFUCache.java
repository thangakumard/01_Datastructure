package algorithms.doublyLinkedList;

import java.util.HashMap;
import java.util.Map;

class LFUCache {
    private class Node {
        int key, value, freq = 1;
        Node prev, next;
        Node(int k, int v) { key = k; value = v; }
    }


    private class DLL {
        Node head = new Node(-1, -1), tail = new Node(-1, -1);
        int size = 0;
        DLL() { head.next = tail; tail.prev = head; }

        void addLast(Node n) {
            n.prev = tail.prev;
            n.next = tail;
            tail.prev.next = n;
            tail.prev = n;
            size++;
        }

        void remove(Node n) {
            n.prev.next = n.next;
            n.next.prev = n.prev;
            size--;
        }

        Node removeFirst() {
            if (size == 0) return null;
            Node n = head.next;
            remove(n);
            return n;
        }
    }

    private final int capacity;
    private int size;
    private int minFreq;
    private final Map<Integer, Node> keyToNode;
    private final Map<Integer, DLL> freqToList;

    public LFUCache(int capacity) {
        this.capacity = capacity;
        this.size = 0;
        this.minFreq = 0;
        this.keyToNode = new HashMap<>();
        this.freqToList = new HashMap<>();
    }

    public int get(int key) {
        Node node = keyToNode.get(key);
        if (node == null) return -1;
        touch(node);
        return node.value;
    }

    /***
     *
     * @param key
     * @param value
     */
    public void put(int key, int value) {
        if (capacity == 0) return;

        //If the Key already present in the keyToNode
        Node node = keyToNode.get(key);
        if (node != null) {
            node.value = value;
            touch(node);
            return;
        }
        //For a new Key. If Key is not present in the keyToNode
        //If keyToNode already reached the capacity remove the minFreq based on LRU (use removeFirst)
        if (size == capacity) {
            DLL minList = freqToList.get(minFreq);//Pick LFU from DLL based on minFreq
            Node evict = minList.removeFirst();//LRU
            keyToNode.remove(evict.key);
            size--;
        }
        //Now we have a spot for the new key
        Node newNode = new Node(key, value);
        keyToNode.put(key, newNode);
        freqToList.computeIfAbsent(1, k -> new DLL()).addLast(newNode);
        minFreq = 1;
        size++;
    }

    /**
     * Called when we need to remove from the DLL and put it in the back in the tail by calling addLast
     * @param node
     */
    private void touch(Node node) {
        int oldFreq = node.freq;
        DLL oldList = freqToList.get(oldFreq);
        oldList.remove(node);

        if (oldList.size == 0) {
            freqToList.remove(oldFreq);
            if (minFreq == oldFreq) minFreq++;
        }

        node.freq++;
        freqToList.computeIfAbsent(node.freq, k -> new DLL()).addLast(node);
    }
}