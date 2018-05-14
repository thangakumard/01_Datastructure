package myInterviews;

import org.testng.annotations.Test;

public class Microsoft01_Ruby_01 {
	
	@Test
	public void test(){
		
		removeDuplicate(new int[] {1,2,2,4,4},5);
	}
	
	void removeDuplicate(int[] buffer, int length){
		int insert = 0;
		
		for(int i=1; i < length; i++){
			if(buffer[i] != buffer[i-1]){
				buffer[insert] = buffer[i-1];
				insert++;				
			}			
		}
		buffer[insert] = buffer[length-1];
		insert++;
		for(int i=insert; i < length; i++){
			buffer[i] = -1;
		}
		
		for(int i=0; i < buffer.length; i++){
			System.out.println(buffer[i]);
		}
	}

}
