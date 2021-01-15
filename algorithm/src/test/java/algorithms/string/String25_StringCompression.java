package algorithms.string;

import org.testng.annotations.Test;

public class String25_StringCompression {
	
	@Test
	private void test() {
		
		char[] input_01 =  {'a','a','b','b','c','c','c'};
		char[] input_02 =  {'a','b','b','b','b','b','b','b','b','b','b','b','b'};
		char[] input_03 =  {'a','b','b','b','b','b','b','b','b','b','b','b','b'};
		
		 compress(input_01);
	}

	public int compress(char[] chars) {
	     
        int index = 0;
        int i = 0;
        while(i < chars.length){
            int j = i;
            while(j < chars.length && chars[i] == chars[j]){
                j++;
            }
            chars[index++] = chars[i];
            if(j - i > 1){
                String count = j - i + "";
                for(char c: count.toCharArray()){
                    chars[index++] = c;
                }
            }
            i = j;
        }
        return index;
    }
}
