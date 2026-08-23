package algorithms.array.greedy;

public class Greedy04_JunpGame_II {
    /***
     * Time Complexity: O(n)
     * Space Complexity: O(1)
     */
    public int jump(int[] nums) {
        int jumps = 0;
        int end = 0;
        int maxJump = 0;

        for(int i=0; i < nums.length-1; i++){
            maxJump = Math.max(maxJump, i+ nums[i]);
            if(i == end){
                jumps++;
                end = maxJump;
            }
        }
        return jumps;
    }
}
