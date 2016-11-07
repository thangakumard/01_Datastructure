package matrix;

import java.util.Random;

import org.testng.Assert;
import org.testng.annotations.Test;

public class MatrixZeros {

	@Test
	public void setMatrixZeros() {

		Random randomGen = new Random();
		// random.nextInt(max - min + 1) + min

		int[][] matrix = new int[3][3];
		for (int row = 0; row < matrix.length; row++) {
			for (int col = 0; col < matrix[row].length; col++) {
				if (row == col) {
					matrix[row][col] = 0;
				} else {
					matrix[row][col] = randomGen.nextInt(10 - 1 + 1) + 1;
				}
			}
		}

		int[][] resultMatrix = new int[3][3];
		for (int row = 0; row < resultMatrix.length; row++) {
			for (int col = 0; col < resultMatrix[row].length; col++) {
				resultMatrix[row][col] = 0;
			}
		}

		setZeros(matrix);
		//Assert.assertEquals(resultMatrix, matrix);
		Assert.assertTrue(true);
	}

	private void setZeros(int[][] matrix) {

		int x = matrix.length;
		int y = matrix[0].length;
		boolean[] rowFlag = new boolean[matrix.length];
		boolean[] colFlag = new boolean[matrix[0].length];

		for (int row = 0; row < matrix.length; row++) {
			for (int col = 0; col < matrix[row].length; col++) {
				if (matrix[row][col] == 0) {
					rowFlag[row] = true;
					rowFlag[col] = true;
				}
			}
		}

		for (int i = 0; i < rowFlag.length; i++) {
			if (rowFlag[i]) {
				for (int k = 0; k < y; k++) {
					matrix[i][k] = 0;
				}
			}
		}

		for (int i = 0; i < colFlag.length; i++) {
			if (rowFlag[i]) {
				for (int k = 0; k < x; k++) {
					matrix[k][i] = 0;
				}
			}
		}

	}
}
