package matrix;

import org.testng.annotations.Test;

/*******
 * https://leetcode.com/problems/flipping-an-image/description/
 * @author THANGAKUMAR
 *	Input: [[1,1,0],[1,0,1],[0,0,0]]
	Output: [[1,0,0],[0,1,0],[1,1,1]]
	Explanation: First reverse each row: [[0,1,1],[1,0,1],[0,0,0]].
	Then, invert the image: [[1,0,0],[0,1,0],[1,1,1]]
 */
public class FlippingAnImage {
	
	@Test
	public void test1(){
		int[][] A = new int[3][3];
		A[0][0] = 1;
		A[0][1] = 1;
		A[0][2] = 0;
		
		A[1][0] = 1;
		A[1][1] = 0;
		A[1][2] = 1;
		
		A[2][0] = 0;
		A[2][1] = 0;
		A[2][2] = 0;
		
		A = flipAndInvertImage(A);
		for(int i=0; i < A.length; i++){
            for(int j=A[0].length-1; j>=0;j--){                
                System.out.println(A[i][j]);
            }
        }
	}
	 
	 public int[][] flipAndInvertImage(int[][] A) {
	        
	        int[][] result = new int[A.length][A[0].length];
	        
	        for(int i=0; i < A.length; i++){
	            int k=0;
	            for(int j=A[0].length-1; j>=0;j--){                
	                result[i][k] = A[i][j];
	                System.out.println(result[i][k]);
	                
	                System.out.println(~result[i][k]);
	                k++;
	            }
	        }
	        return result;
	    }
}
