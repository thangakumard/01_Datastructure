package algorithms.stack;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.testng.annotations.Test;

public class HourglassSum {

    @Test
    public void hourglassSumTest() {
        List<List<Integer>> arr = Arrays.asList(
                Arrays.asList(-9, -9, -9, 1, 1, 1),
                Arrays.asList(0, -9, 0, 4, 3, 2),
                Arrays.asList(-9, -9, -9, 1, 2, 3),
                Arrays.asList(0, 0, 8, 6, 6, 0),
                Arrays.asList(0, 0, 0, -2, 0, 0),
                Arrays.asList(0, 0, 1, 2, 4, 0)
        );
        Assertions.assertThat(hourglassSum(arr)).isEqualTo(28);

        List<List<Integer>> allNegative = Arrays.asList(
                Arrays.asList(-1, -1, 0, -9, -2, -2),
                Arrays.asList(-2, -1, -6, -8, -2, -5),
                Arrays.asList(-1, -1, -1, -2, -3, -4),
                Arrays.asList(-1, -9, -2, -4, -4, -5),
                Arrays.asList(-7, -3, -3, -2, -9, -9),
                Arrays.asList(-1, -3, -1, -2, -4, -5)
        );
        Assertions.assertThat(hourglassSum(allNegative)).isEqualTo(-6);
    }

    public static int hourglassSum(List<List<Integer>> arr) {
        // Initialize max_sum to the lowest possible hourglass value (-9 * 7 = -63)
        int maxSum = -63;

        // Iterate over the 4x4 valid top-left positions
        for (int r = 0; r < 4; r++) {
            for (int c = 0; c < 4; c++) {

                // Top row of the hourglass
                int top = arr.get(r).get(c)
                        + arr.get(r).get(c + 1)
                        + arr.get(r).get(c + 2);

                // Middle element of the hourglass
                int mid = arr.get(r + 1).get(c + 1);

                // Bottom row of the hourglass
                int bot = arr.get(r + 2).get(c)
                        + arr.get(r + 2).get(c + 1)
                        + arr.get(r + 2).get(c + 2);

                int currentSum = top + mid + bot;

                // Update maximum sum found so far
                if (currentSum > maxSum) {
                    maxSum = currentSum;
                }
            }
        }

        return maxSum;
    }
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        List<List<Integer>> arr = new ArrayList<>();

        for (int i = 0; i < 6; i++) {
            String[] rowItems = bufferedReader.readLine().replaceAll("\\s+$", "").split(" ");
            List<Integer> row = new ArrayList<>();
            for (int j = 0; j < 6; j++) {
                row.add(Integer.parseInt(rowItems[j]));
            }
            arr.add(row);
        }

        int result = hourglassSum(arr);
        System.out.println(result);
        bufferedReader.close();
    }
}
