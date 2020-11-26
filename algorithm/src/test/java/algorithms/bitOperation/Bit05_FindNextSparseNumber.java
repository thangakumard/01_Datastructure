package algorithms.bitOperation;

import java.util.ArrayList;

import org.testng.annotations.*;
/*******
 * 
 * A number is Sparse if there are no two adjacent 1s in its binary representation. 
 * For example 5 (binary representation: 101) is sparse, 
 * but 6 (binary representation: 110) is not sparse.
 * Given a number x, find the next smallest Sparse number which greater than or equal to x.
 *
 * Input: x = 6
 * Output: Next Sparse Number is 8
	
 * Input: x = 4
 * Output: Next Sparse Number is 4
	
 * Input: x = 38
 * Output: Next Sparse Number is 40
	
 * Input: x = 44
 * Output: Next Sparse Number is 64
 * 
 * 1	1
2	10
3	11
4	100
5	101
6	110
7	111
8	1000
9	1001
10	1010
11	1011
12	1100
13	1101
14	1110
15	1111
16	10000
17	10001
18	10010
19	10011
20	10100
21	10101
22	10110
23	10111
24	11000
25	11001
26	11010
27	11011
28	11100
29	11101
30	11110
31	11111
32	100000
33	100001
34	100010
35	100011
36	100100
37	100101
38	100110
39	100111
40	101000
41	101001
42	101010
43	101011
44	101100
45	101101
46	101110
47	101111
48	110000
49	110001
50	110010
51	110011
52	110100
53	110101
54	110110
55	110111
56	111000
57	111001
58	111010
59	111011
60	111100
61	111101
62	111110
63	111111
64	1000000
65	1000001
66	1000010
67	1000011
68	1000100
69	1000101
70	1000110
71	1000111
72	1001000
73	1001001
74	1001010
75	1001011
76	1001100
77	1001101
78	1001110
79	1001111
80	1010000
81	1010001
82	1010010
83	1010011
84	1010100
85	1010101
86	1010110
87	1010111
88	1011000
89	1011001
90	1011010
91	1011011
92	1011100
93	1011101
94	1011110
95	1011111
96	1100000
97	1100001
98	1100010
99	1100011
100	1100100
 */
public class Bit05_FindNextSparseNumber {

	@Test
	public void test(){		
		System.out.println("**************************");
		System.out.println(simpleSolution(6));
		System.out.println("**************************");
		System.out.println(simpleSolution(4));
		System.out.println("**************************");
		System.out.println(simpleSolution(38));
		System.out.println("**************************");
		System.out.println(simpleSolution(44));
		System.out.println("**************************");
		System.out.println(nextSparse(6));
		System.out.println("**************************");
		System.out.println(nextSparse(19));
		System.out.println("**************************");
	}
	
	public int  simpleSolution(int n){			
		while(true){
			if(isSparse(n)){
				return n;
			}else{
				n++;
			}
		}		
	}
	
	public boolean isSparse(int n){
		
		int continuousSetBit = 0;
		while(n > 0){			
			if((n & 1) == 1){
				continuousSetBit ++;
			}
			else{
				continuousSetBit = 0;
			}
			if(continuousSetBit == 2)
				return false;
			n = n >> 1;
		}		
		return true;
	}
	
	
	
	static int nextSparse(int x)  
	{  
	    // Find binary representation of x and store it in bin.get(].  
	    // bin.get(0] contains least significant bit (LSB), next  
	    // bit is in bin.get(1], and so on.  
	    ArrayList<Integer> bin = new ArrayList<Integer>();  
	    while (x != 0)  
	    {  
	        bin.add(x&1);  
	        x >>= 1;  
	    }  
	  
	    // There my be extra bit in result, so add one extra bit  
	    bin.add(0);  
	    int n = bin.size(); // Size of binary representation  
	  
	    // The position till which all bits are finalized  
	    int last_final = 0;  
	  
	    // Start from second bit (next to LSB)  
	    for (int i=1; i<n-1; i++)  
	    {  
	    // If current bit and its previous bit are 1, but next  
	    // bit is not 1.  
	    if (bin.get(i) == 1 && bin.get(i-1) == 1 && bin.get(i+1) != 1)  
	    {  
	            // Make the next bit 1  
	            bin.set(i+1,1);  
	  
	            // Make all bits before current bit as 0 to make  
	            // sure that we get the smallest next number  
	            for (int j=i; j>=last_final; j--)  
	                bin.set(j,0);  
	  
	            // Store position of the bit set so that this bit  
	            // and bits before it are not changed next time.  
	            last_final = i+1;  
	        }  
	    }  
	  
	    // Find decimal equivalent of modified bin.get(]  
	    int ans = 0;  
	    for (int i =0; i<n; i++)  
	        ans += bin.get(i)*(1<<i);  
	    return ans;  
	}
	
}
