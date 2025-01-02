package myInterviews.Microsoft.others;

/**
 * When dice A and dice B are at the same time 5 times,
 * you can switch the value of dice B to dice A and vice versa
 * on an ith roll to make all the values in dice A the same or values in dice B the same.
 * Return the number of swap we make to achieve this .
 * If this is not possible return -1
 */
public class Microsoft02_MakeSameValueDice {

        public int FindMinimumSwaps(int[] diceA, int[] diceB) throws Exception {
            if (diceA == null || diceB == null || diceA.length != diceB.length)
                throw new Exception("Input arrays must be non-null and of the same length.");

            // Possible target values are the first elements of both arrays
            int targetA = diceA[0];
            int targetB = diceB[0];

            // Calculate swaps for both target candidates
            int minSwapsForA = CountSwaps(targetA, diceA, diceB);
            int minSwapsForB = CountSwaps(targetB, diceA, diceB);

            // Determine the overall minimum swaps required
            int minSwaps = Math.min(minSwapsForA, minSwapsForB);

            // If minSwaps is still int.MaxValue, it means it's impossible to achieve the goal
            return minSwaps != Integer.MAX_VALUE ? minSwaps : -1;
        }

    // Helper function to count swaps required to make all A or all B equal to target
    private int CountSwaps(int target, int[] A, int[] B)
    {
        int swapsToA = 0;
        int swapsToB = 0;

        for (int i = 0; i < A.length; i++)
        {
            if (A[i] != target && B[i] != target)
            {
                // Neither A[i] nor B[i] can be swapped to target
                return Integer.MAX_VALUE; // Impossible to achieve target
            }
            else if (A[i] != target && B[i] == target)
            {
                swapsToA++; // Swap to make A[i] equal to target
            }
            else if (B[i] != target && A[i] == target)
            {
                swapsToB++; // Swap to make B[i] equal to target
            }
            // If both A[i] and B[i] are already target, no swap needed
        }

        // Return the minimum swaps required between making all A's or all B's equal to target
        return Math.min(swapsToA, swapsToB);
    }

}
