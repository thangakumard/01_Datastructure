package algorithms.bitOperation.rightShift;

import java.util.ArrayList;
import java.util.List;

/**
 * https://leetcode.com/problems/gray-code/
 */
public class rightShift02_GreyCode {
    public List<Integer> grayCode(int n) {
        List<Integer> result = new ArrayList<>();
        // there are 2 ^ n numbers in the Gray code sequence.
        int sequenceLength = 1 << n;
        for (int i = 0; i < sequenceLength; i++) {
            int num = i ^ (i >> 1);
            result.add(num);
        }
        return result;
    }
}
