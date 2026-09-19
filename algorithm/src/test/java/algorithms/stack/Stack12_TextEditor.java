package algorithms.stack;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;
import java.util.StringTokenizer;

/***
 * https://www.hackerrank.com/challenges/simple-text-editor/problem?isFullScreen=true
 */
public class Stack12_TextEditor {
    public static void main(String[] args) throws IOException {
        // Fast I/O using BufferedReader and StringTokenizer
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int q = Integer.parseInt(st.nextToken());

        StringBuilder s = new StringBuilder();
        Stack<String> history = new Stack<>();

        for (int i = 0; i < q; i++) {
            st = new StringTokenizer(br.readLine());
            int type = Integer.parseInt(st.nextToken());

            switch (type) {
                case 1:
                    // Append W to S
                    String w = st.nextToken();
                    history.push(s.toString());
                    s.append(w);
                    break;

                case 2:
                    // Delete last k characters from S
                    int kDel = Integer.parseInt(st.nextToken());
                    history.push(s.toString());
                    s.delete(s.length() - kDel, s.length());
                    break;

                case 3:
                    // Print k-th character (1-indexed)
                    int kPrint = Integer.parseInt(st.nextToken());
                    System.out.println(     );
                    break;

                case 4:
                    // Undo last modification
                    s = new StringBuilder(history.pop());
                    break;
            }
        }
    }
}
