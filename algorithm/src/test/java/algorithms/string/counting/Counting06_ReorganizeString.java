package algorithms.string.counting;

public class Counting06_ReorganizeString {
    public String reorganizeString(String s) {
        int n = s.length();
        int[] counts = new int[26];

        int maxFreq = 0;
        char maxChar = 'a';

        // Count frequencies and track the character with maximum frequency
        for (int i = 0; i < n; i++) {
            char c = s.charAt(i);
            counts[c - 'a']++;
            if (counts[c - 'a'] > maxFreq) {
                maxFreq = counts[c - 'a'];
                maxChar = c;
            }
        }

        // Feasibility check: if max frequency exceeds half (rounded up), impossible
        if (maxFreq > (n + 1) / 2) {
            return "";
        }

        char[] res = new char[n];
        int idx = 0;

        // 1. Place the most frequent character first at even indices (0, 2, 4...)
        while (counts[maxChar - 'a'] > 0) {
            res[idx] = maxChar;
            counts[maxChar - 'a']--;
            idx += 2;
        }

        // 2. Place all remaining characters in open slots
        for (int i = 0; i < 26; i++) {
            while (counts[i] > 0) {
                if (idx >= n) {
                    idx = 1; // Switch to odd indices (1, 3, 5...) once evens are filled
                }
                res[idx] = (char) ('a' + i);
                counts[i]--;
                idx += 2;
            }
        }

        return new String(res);
    }
}
