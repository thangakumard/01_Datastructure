package algorithms.string;
import org.testng.annotations.Test;

public class Palindromic_Substring_Count {
	
	@Test
	public void Test(){
		//System.out.println(countSubstrings_Manacher("aba"));
		//System.out.println(countSubstrings("aaaaa"));
		System.out.println(countSubstrings_1("aaa"));
	}
	 int count = 0;
	public int countSubstrings_1(String s) {
        if(s == null)
            return 0;
        if(s.length() == 1)
            return 1;
       
        System.out.println();
        for(int i=0; i < s.length(); i++){
            
            System.out.print(helper(s,i,i));
            System.out.print(",");
            System.out.print(helper(s, i , i+1));
            System.out.print(",");
        }
        System.out.println();
        return count;
    }
	    
    private String helper(String s, int start, int end){
        while(start >= 0 && end <= s.length()-1 && s.charAt(start) == s.charAt(end)){
            start--;
            end++;
            count++;
        }
        return s.substring(start+1,end);
    }
	    

	//O(N2)
	 public int countSubstrings(String S) {
	        int N = S.length(), ans = 0;
	        int length = 2*N-1;
	        for (int center = 0; center <= length; ++center) {
	            int left = center / 2;
	            int right = left + center % 2;
	            while (left >= 0 && right < N && S.charAt(left) == S.charAt(right)) {
	                ans++;
	                left--;
	                right++;
	            }
	        }
	        return ans;
	    }
	 
	 //Manacher's Algorithm O(N)
	 public int countSubstrings_Manacher(String S) {
	        char[] A = new char[2 * S.length() + 3];
	        A[0] = '@';
	        A[1] = '#';
	        A[A.length - 1] = '$';
	        int t = 2;
	        for (char c: S.toCharArray()) {
	            A[t++] = c;
	            A[t++] = '#';
	        }

	        int[] Z = new int[A.length];
	        int center = 0, right = 0;
	        for (int i = 1; i < Z.length - 1; ++i) {
	            if (i < right)
	                Z[i] = Math.min(right - i, Z[2 * center - i]);
	            while (A[i + Z[i] + 1] == A[i - Z[i] - 1])
	                Z[i]++;
	            if (i + Z[i] > right) {
	                center = i;
	                right = i + Z[i];
	            }
	        }
	        int ans = 0;
	        for (int v: Z) ans += (v + 1) / 2;
	        return ans;
	    }
	 
}
