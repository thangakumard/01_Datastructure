package algorithms.array.twoPointer.slidingWindow;

import java.io.*;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class GameOfTwoStack {
    /***
     * Time Complexity: O(n + m)
     * Space Complexity: O(1)
     */
    public static int twoStacks(int maxSum, List<Integer> a, List<Integer> b) {
        int n = a.size(), m = b.size();
        long sum = 0;
        int i = 0;

        // Take as many elements from the front of A as fit.
        while (i < n && sum + a.get(i) <= maxSum) {
            sum += a.get(i);
            i++;
        }

        int count = i;
        int maxCount = count;

        // Slide j across B, shrinking the A-prefix (from the back) as needed.
        for (int j = 0; j < m; j++) {
            sum += b.get(j);
            count++;
            while (sum > maxSum && i > 0) {
                i--;
                sum -= a.get(i);
                count--;
            }
            if (sum <= maxSum) {
                maxCount = Math.max(maxCount, count);
            }
        }

        return maxCount;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        int g = Integer.parseInt(bufferedReader.readLine().trim());

        IntStream.range(0, g).forEach(gItr -> {
            try {
                String[] firstMultipleInput = bufferedReader.readLine().replaceAll("\\s+$", "").split(" ");

                int n = Integer.parseInt(firstMultipleInput[0]);

                int m = Integer.parseInt(firstMultipleInput[1]);

                int maxSum = Integer.parseInt(firstMultipleInput[2]);

                List<Integer> a = Stream.of(bufferedReader.readLine().replaceAll("\\s+$", "").split(" "))
                        .map(Integer::parseInt)
                        .collect(toList());

                List<Integer> b = Stream.of(bufferedReader.readLine().replaceAll("\\s+$", "").split(" "))
                        .map(Integer::parseInt)
                        .collect(toList());

                int result = GameOfTwoStack.twoStacks(maxSum, a, b);

                bufferedWriter.write(String.valueOf(result));
                bufferedWriter.newLine();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        bufferedReader.close();
        bufferedWriter.close();
    }
}
