package algorithms.array.medium.backtracking;

import java.util.ArrayList;
import java.util.List;

public class Backtrack_Array06_subset_targetSum {
    public static List<List<Integer>> findSubsetsThatSumToTarget(int[] nums, int target) {
        List<List<Integer>> result = new ArrayList<>();
        findSubsets(nums, 0, target, new ArrayList<>(), result);
        return result;
    }

    private static void findSubsets(int[] nums, int index, int target, List<Integer> current, List<List<Integer>> result) {
        if (target == 0) {
            result.add(new ArrayList<>(current));
            return;
        }
        if (target < 0 || index >= nums.length) {
            return;
        }

        // Include the current number
        current.add(nums[index]);
        findSubsets(nums, index + 1, target - nums[index], current, result);

        // Exclude the current number
        current.remove(current.size() - 1);
        findSubsets(nums, index + 1, target, current, result);
    }
}
