package arrayAlgorithms;

import java.util.List;
import java.util.ArrayList;
import org.testng.annotations.Test;

public class Array57_SortLogFile {
	
	@Test
	public void Test(){
		
	}
	
	
	private List<String> sortLogFile(List<String> logLines){
		List<String> result = new ArrayList<>();
		
		List<String> lstNumericLogs = new ArrayList<>();
		List<String> lstStringLogs = new ArrayList<>();
		
		for(String S: logLines){
			if(isNum(S)){
				lstNumericLogs.add(S);
			}
			else{
				lstStringLogs.add(S);
			}
		}
		
		if(lstStringLogs.size() > 0){
			
			String[] arrLogs = new String[lstStringLogs.size()];
			String log = "";
			for(int i =0; i < lstStringLogs.size(); i++){
				log = lstNumericLogs.get(i);
				log = log.substring(log.indexOf(" "));
				arrLogs[i] = log;
			}
			sortLogs(arrLogs);
			
		}
		else{
			return lstNumericLogs;
		}
		
		return result;
	}
	
	
	private void sortLogs(String[] input){
		quickSort(input, 0 , input.length);
	}
	
	private void quickSort(String[] input, int left, int right){
		int initialLeft = left;
		int initialRight = right;
		String pivotValue = input[left];
		
		while(left < right){
		
			while(input[right].compareTo(pivotValue) >= 0 && left < right){
				right--;
			}
			if(left != right){
				input[left] = input[right];
				left++;
			}
			
			while(input[left].compareTo(pivotValue) <= 0 && left < right){
				left++;
			}
			if(left != right){
				input[right] = input[left];
				right--;
			}
			
		}
		
		int pivotIndex = left;
		input[left] = pivotValue;
		if(pivotIndex - 1 > initialLeft){
			quickSort(input, initialLeft, pivotIndex-1);
		}
		if(initialRight > pivotIndex+1){
			quickSort(input, pivotIndex+1, initialRight);
		}
	}

	public static boolean isNum(String strNum) {
	    boolean ret = true;
	    try {

	        Double.parseDouble(strNum);

	    }catch (NumberFormatException e) {
	        ret = false;
	    }
	    return ret;
	}
}
