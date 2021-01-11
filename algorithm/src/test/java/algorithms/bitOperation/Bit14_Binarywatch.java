package algorithms.bitOperation;

import java.util.*;

import org.testng.annotations.Test;

/***
 * https://leetcode.com/problems/binary-watch/
 * https://www.youtube.com/watch?v=nuUq7k4BU8I
 * 
 * A binary watch has 4 LEDs on the top which represent the hours (0-11), and
 * the 6 LEDs on the bottom represent the minutes (0-59).
 * 
 * Each LED represents a zero or one, with the least significant bit on the
 * right.
 * 
 * 
 * For example, the above binary watch reads "3:25".
 * 
 * Given a non-negative integer n which represents the number of LEDs that are
 * currently on, return all possible times the watch could represent.
 * 
 * Example:
 * 
 * Input: n = 1 Return: ["1:00", "2:00", "4:00", "8:00", "0:01", "0:02", "0:04",
 * "0:08", "0:16", "0:32"] Note: The order of output does not matter. The hour
 * must not contain a leading zero, for example "01:00" is not valid, it should
 * be "1:00". The minute must be consist of two digits and may contain a leading
 * zero, for example "10:2" is not valid, it should be "10:02".
 * 
 */
public class Bit14_Binarywatch {
	
	@Test
	private void test() {
		System.out.println(readBinaryWatch(2));
	}

	public List<String> readBinaryWatch(int num) {
        List<String> result = new ArrayList<>();
        
        for(int hour = 0; hour < 12; hour++){
            for(int minutes = 0; minutes < 60; minutes ++){
                if(Integer.bitCount(hour) + Integer.bitCount(minutes) == num)
                {
                    if(minutes < 10){
                        result.add(String.format("%d:0%d", hour, minutes));
                    }else{
                        result.add(String.format("%d:%d", hour, minutes));
                    }
                }
            }
        }
        
        return result;
    }
}
