package algorithms.hashMap;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
/***
** https://leetcode.com/problems/time-based-key-value-store
***/

public class HashMap03_Design_TimeMap {
    private class Node {
        int timestamp;
        String value;
        
        Node(int timestamp, String value) {
            this.timestamp = timestamp;
            this.value = value;
        }
    }

    private Map<String, List<Node>> map;

    public TimeMap() {
        map = new HashMap<>();
    }
    
    public void set(String key, String value, int timestamp) {
        map.putIfAbsent(key, new ArrayList<>());
        map.get(key).add(new Node(timestamp, value));
    }
    
    public String get(String key, int timestamp) {
        if (!map.containsKey(key)) {
            return "";
        }
        
        List<Node> list = map.get(key);
        int left = 0, right = list.size() - 1;
        int ansIndex = -1;
        
        // Standard binary search to find the largest timestamp <= target
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (list.get(mid).timestamp <= timestamp) {
                ansIndex = mid; // Potential answer, look for a larger valid one on the right
                left = mid + 1;
            } else {
                right = mid - 1; // Too large, look on the left
            }
        }
        
        return ansIndex == -1 ? "" : list.get(ansIndex).value;
    }
}
