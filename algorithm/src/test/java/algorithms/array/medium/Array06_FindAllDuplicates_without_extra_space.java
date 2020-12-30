package algorithms.array.medium;

import java.util.*;

public class Array06_FindAllDuplicates_without_extra_space {

	public List<Integer> findDuplicates(int[] nums) {
        List<Integer> result = new ArrayList<>();
        
        for(int i=0; i< nums.length; i++){
            if(nums[Math.abs(nums[i])-1] < 0){
                result.add(Math.abs(nums[i]));
            }else{
                nums[Math.abs(nums[i])-1] = -nums[Math.abs(nums[i])-1];
            }
        }
        return result;
    }
}
