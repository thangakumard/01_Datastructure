package algorithms.array.medium;

import java.util.HashMap;
import java.util.Set;

/***
 * https://leetcode.com/problems/dot-product-of-two-sparse-vectors/
 *
 * Given two sparse vectors, compute their dot product.
 *
 * Implement class SparseVector:
 *
 * SparseVector(nums) Initializes the object with the vector nums
 * dotProduct(vec) Compute the dot product between the instance of SparseVector and vec
 * A sparse vector is a vector that has mostly zero values, you should store the sparse vector efficiently and compute the dot product between two SparseVector.
 * Follow up: What if only one of the vectors is sparse?
 *
 * Example 1:
 * Input: nums1 = [1,0,0,2,3], nums2 = [0,3,0,4,0]
 * Output: 8
 * Explanation: v1 = SparseVector(nums1) , v2 = SparseVector(nums2)
 * v1.dotProduct(v2) = 1*0 + 0*3 + 0*0 + 2*4 + 3*0 = 8
 *
 * Example 2:
 * Input: nums1 = [0,1,0,0,0], nums2 = [0,0,0,0,2]
 * Output: 0
 * Explanation: v1 = SparseVector(nums1) , v2 = SparseVector(nums2)
 * v1.dotProduct(v2) = 0*0 + 1*0 + 0*0 + 0*0 + 0*2 = 0
 *
 * Example 3:
 * Input: nums1 = [0,1,0,0,2,0,0], nums2 = [1,0,0,0,3,0,4]
 * Output: 6
 *
 */
public class Array39_SparseVector {
    private HashMap<Integer, Integer> mapVector;

    Array39_SparseVector(int[] nums) {
        this.mapVector =  new HashMap<Integer, Integer>();
        for(int i=0; i < nums.length; i++){
            if(nums[i] != 0){
                mapVector.put(i, nums[i]);
            }
        }
    }

    // Return the dotProduct of two sparse vectors
    public int dotProduct(Array39_SparseVector vec) {
        Set<Integer> v2 = vec.mapVector.keySet();
        Set<Integer> v1 = this.mapVector.keySet();
        HashMap<Integer, Integer> mapV2 = vec.mapVector;

        v1.retainAll(v2);
        int result = 0;
        for(Integer i: v1){
            result += this.mapVector.get(i) * mapV2.get(i);
        }

        return result;
    }
}
