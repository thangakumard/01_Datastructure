package algorithms.companies.amazon;

import org.testng.annotations.Test;

/***
 * https://www.geeksforgeeks.org/arrange-given-numbers-to-form-the-smallest-number/
 *
 * Given an array arr[] of integer elements, the task is to arrange them in such a way that these numbers form the smallest possible number.
 * For example, if the given array is {5, 6, 2, 9, 21, 1} then the arrangement will be 1212569.
 *
 * Examples:
 *
 * Input: arr[] = {5, 6, 2, 9, 21, 1}
 * Output: 1212569
 *
 * Input: arr[] = {1, 2, 1, 12, 33, 211, 50}
 * Output: 111221123350
 */
public class Amazon_10_ArrangeGivenNumberToGetSmallest {

    @Test
    public void arrageNumbersTest(){
        int[] input1 = new int[]{1, 2, 1, 12, 33, 211, 50};
        arrageNumbers(input1);

        System.out.println("********");
        int[] input2 = new int[]{5, 6, 2, 9, 21, 1} ;
        arrageNumbers(input2);

    }
    public void arrageNumbers(int[] input){
        int l = input.length;

        for(int i=0; i < l; i++){
            for(int j=i+1; j < l; j++){
                if(minNumbers(input[i] , input[j]) > 0){
                    int temp = input[i];
                    input[i] = input[j];
                    input[j] = temp;
                }
            }
        }

        for(int i=0; i < input.length; i++){
            System.out.print(input[i]);
        }
    }

    /***
     *
     * If the first string is lexicographically greater than the second string, it returns a positive number (difference of character value).
     * If the first string is less than the second string lexicographically, it returns a negative number, and
     * if the first string is lexicographically equal to the second string, it returns 0.
     *
     * if x1 > x2, it returns positive number
     * if x1 < x2, it returns negative number
     * if x1 == x2, it returns 0
     */
    private int minNumbers(int x1, int x2){

        String A = Integer.toString(x1);
        String B = Integer.toString(x2);

        return (A+B).compareTo(B+A);
    }
}
