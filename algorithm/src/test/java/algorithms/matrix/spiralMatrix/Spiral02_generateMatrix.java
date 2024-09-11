package algorithms.matrix.spiralMatrix;

import org.testng.annotations.Test;

public class Spiral02_generateMatrix {

    @Test
    private void test() {
        int n = 3;
        int[][] result = generateMatrix(n);
        for(int i=0 ;i < n; i++){
            for(int j=0; j < n; j++){
                System.out.println(result[i][j]);
            }
        }
    }

    public int[][] generateMatrix(int n) {
        int[][] result = new int[n][n];

        int rowBegin = 0, rowEnd = n-1;
        int colBegin = 0, colEnd = n-1;
        int counter = 1;

        while(rowBegin <= rowEnd && colBegin <= colEnd){
            for(int i=colBegin; i <= colEnd; i++){
                result[rowBegin][i] = counter++;
            }
            rowBegin++;
            for(int i=rowBegin; i <= rowEnd; i++){
                result[i][colEnd] = counter++;
            }
            colEnd--;
            if(rowBegin <= rowEnd){
                for(int i=colEnd; i >= colBegin; i--){
                    result[rowEnd][i] = counter++;
                }
            }
            rowEnd--;
            if(colBegin <= colEnd)
            {
                for(int i=rowEnd; i >= rowBegin; i--){
                    result[i][colBegin] = counter++;
                }
            }
            colBegin++;
        }
        return result;
    }
}
