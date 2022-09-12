package algorithms.string;

/*
 * https://leetcode.com/problems/string-to-integer-atoi/
 * 
Implement atoi which converts a string to an integer.

The function first discards as many whitespace characters as necessary until the first non-whitespace character is found. Then, starting from this character takes an optional initial plus or minus sign followed by as many numerical digits as possible, and interprets them as a numerical value.

The string can contain additional characters after those that form the integral number, which are ignored and have no effect on the behavior of this function.

If the first sequence of non-whitespace characters in str is not a valid integral number, or if no such sequence exists because either str is empty or it contains only whitespace characters, no conversion is performed.

If no valid conversion could be performed, a zero value is returned.

Note:

Only the space character ' ' is considered a whitespace character.
Assume we are dealing with an environment that could only store integers within the 32-bit signed integer range: [−231,  231 − 1]. If the numerical value is out of the range of representable values, 231 − 1 or −231 is returned.
 

Example 1:

Input: str = "42"
Output: 42
Example 2:

Input: str = "   -42"
Output: -42
Explanation: The first non-whitespace character is '-', which is the minus sign. Then take as many numerical digits as possible, which gets 42.
Example 3:

Input: str = "4193 with words"
Output: 4193
Explanation: Conversion stops at digit '3' as the next character is not a numerical digit.
Example 4:

Input: str = "words and 987"
Output: 0
Explanation: The first non-whitespace character is 'w', which is not a numerical digit or a +/- sign. Therefore no valid conversion could be performed.
Example 5:

Input: str = "-91283472332"
Output: -2147483648
Explanation: The number "-91283472332" is out of the range of a 32-bit signed integer. Thefore INT_MIN (−231) is returned.
 

Constraints:

0 <= s.length <= 200
s consists of English letters (lower-case and upper-case), digits, ' ', '+', '-' and '.'.
 ***/


import org.assertj.core.api.Assertions;
import org.testng.annotations.Test;

public class String15_StringToInteger_atoi {

    @Test
    public void atoi_tests(){
        Assertions.assertThat(myAtoi_01("")).isEqualTo(0);
        Assertions.assertThat(myAtoi_01("42")).isEqualTo(42);
        Assertions.assertThat(myAtoi_01("  -42")).isEqualTo(-42);
        Assertions.assertThat(myAtoi_01("  -42some")).isEqualTo(-42);
        Assertions.assertThat(myAtoi_01("  +50sd43545")).isEqualTo(50);
        Assertions.assertThat(myAtoi_01("  -999999999999999sdfsg")).isEqualTo(-2147483648);
        Assertions.assertThat(myAtoi_01("  +999999999999999sdfsg")).isEqualTo(2147483647);
        Assertions.assertThat(myAtoi_01("  -2147483647dsfgasdgg")).isEqualTo(-2147483647);
        Assertions.assertThat(myAtoi_01("  -999999999999999sdfsg")).isEqualTo(-2147483648);
    }


    public int myAtoi_01(String s) {
        if(s == null || s.length() == 0)
            return 0;
        boolean isNegative = false;
        int result = 0;
        s = s.trim(); /*** IMPORTANT - After trim string may be empty */
        
        if(s.length() > 0){
            if(s.charAt(0) == '-')
                isNegative = true;

            int start =0;

            if(s.charAt(0) == '-' || s.charAt(0) == '+'){
                start = 1;
            }

            for(int i = start; i < s.length(); i++){
                if(Character.isDigit(s.charAt(i))){
                    int digit = s.charAt(i) - '0';
                    if(result > Integer.MAX_VALUE/10 || (result == Integer.MAX_VALUE/10 && digit > Integer.MAX_VALUE%10)){
                        return isNegative ? Integer.MIN_VALUE : Integer.MAX_VALUE;
                    }
                    result = result * 10 + digit;
                }else{
                    break;
                }
            }
        }
        return  isNegative ? -1 * result : result;
    }

    public int myAtoi_02(String s) {
        if(s == null || s.isEmpty()){
            return 0;
        }
        s = s.trim();
        boolean isNegative = s.charAt(0) == '-' ? true : false;

        if(s.charAt(0) == '-' || s.charAt(0) == '+'){
            s = s.substring(1,s.length());
        }

        char[] input = s.toCharArray();
        StringBuilder sb = new StringBuilder();
        for(char c: input){
            if(Character.isDigit(c)){
                sb.append(c);

                if(Long.parseLong(sb.toString()) > Integer.MAX_VALUE){
                    return isNegative? Integer.MIN_VALUE : Integer.MAX_VALUE;
                }

            }else
                break;
        }

        if(sb.length() == 0){
            return 0;
        }
        return isNegative ? -Integer.parseInt(sb.toString()) : Integer.parseInt(sb.toString());
    }

}
