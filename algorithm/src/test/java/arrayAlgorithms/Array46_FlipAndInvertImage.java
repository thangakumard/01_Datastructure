package arrayAlgorithms;
import org.testng.annotations.Test;

public class Array46_FlipAndInvertImage {
	
	@Test
	public void Test(){
		 int[][] input = new int[][]{{1,1,0},{1,0,1},{0,0,0}};
  		flipAndInvertImage(input);
	}

	 public int[][] flipAndInvertImage(int[][] a) {
	        int n = a.length;
	        int[][] ret = new int[n][];
	        for(int i = 0;i < n;i++){
	        	int[] b = rev(a[i]);
	        	for(int j = 0;j < b.length;j++){
	        		b[j] ^= 1;
	        	}
	        	ret[i] = b;
	        }
	        return ret;
	    }
	    
		public int[] rev(int[] a)
		{
			int[] b = new int[a.length];
			for(int i = 0;i < a.length;i++)
				b[a.length-1-i] = a[i];
			return b;
		}
}
