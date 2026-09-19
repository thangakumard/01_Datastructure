package algorithms.array.twoPointer.slidingWindow;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class temp {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer stk = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(stk.nextToken());
        for (int i=0; i < n; i++){
            System.out.println("Enter a value");
            stk = new StringTokenizer(br.readLine());
            System.out.println("stk.nextToken is:"  + stk.nextToken());
        }
    }
}
