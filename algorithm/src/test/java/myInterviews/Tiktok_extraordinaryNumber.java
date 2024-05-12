package myInterviews;

import org.assertj.core.api.Assertions;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

/*

Each character of the lowercase English alphabet has been mapped to digits as shown in the figure.
The numerical value corresponding to each letter is its mapped value.

An extraordinary substring is one whose sum of the mapped values of each letter is divisible by its length.
Given string input_str, count its total number of non-empty extraordinary substrings.

Example:

input_str = 'asdf'

All non-empty substrings of input_str are tested in the table.

String  Mapped  Sum   Length    Is divisible
a       1         1     1           Yes
s       7         7     1           Yes
d       2         2     1           Yes
f       3         3     1           Yes
as      1,7       8     2           Yes
sd      7,2       9     2            No
df      2,3       5     2            No
asd     1,7,2    10     3            No
sdf     7,2,3    12     3           Yes
asdf    1,7,2,3  13     4            No



There are 6 extraordinary substrings.


Function Description

Complete the function countSubstrings in the editor.



countSubstrings has the following parameter(s):

    string input_str: a string of length n



Returns

    int: the number of non-empty extraordinary substrings



Constraints

1 ≤ n ≤ 2000
All characters of input_str are lowercase English letters.


Input Format For Custom Testing
Sample Case 0
Sample Input For Custom Testing

STDIN    FUNCTION
-----    --------
bdh   →  input_str = "bdh"
Sample Output

4
Explanation

The extraordinary substrings are 'b', 'd', 'h' and 'bdh'.
 */
public class Tiktok_extraordinaryNumber {

    @Test
    public void sampleTests(){
        //Assertions.assertThat(countSubstrings("bdh")).isEqualTo(4);
        Assertions.assertThat(countSubstrings("asdf")).isEqualTo(6);
    }

    private static int[] values = new int[26];

    public static int countSubstrings(String input_str) {

        //a,b
        values[0] = 1;
        values[1] = 1;

        //c,d,e
        values[2] = 2;
        values[3] = 2;
        values[4] = 2;

        //fgh
        values[5] = 3;
        values[6] = 3;
        values[7] = 3;

        //i,j,k
        values[8] = 4;
        values[9] = 4;
        values[10] = 4;

        //l,m,n
        values[8] = 5;
        values[9] = 5;
        values[10] = 5;

        //o,p,q
        values[8] = 6;
        values[9] = 6;
        values[10] = 6;

        //r,s,t
        values[8] = 7;
        values[9] = 7;
        values[10] = 7;

        //u,v,w
        values[8] = 8;
        values[9] = 8;
        values[10] =8;

        //x,y,z
        values[8] = 9;
        values[9] = 9;
        values[10] =9;

        int result = 0;
        if(input_str == null || input_str.length() == 0)
            return 0;

        List<String> allSubString = new ArrayList<>();
        for (int start = 0; start < input_str.length(); start++) {
            for (int end = start +1; end <= input_str.length(); end++) {
                allSubString.add(input_str.substring(start,end));
            }
        }

//        getSubStringsNew(input_str ,allSubString, 0, 0+1);// for odd length
        for(int i=0; i <allSubString.size(); i++){

            if(isExtraordinary(allSubString.get(i)) == true)
            {
                result++;
            }


        }

        return result;
    }

//    private static void getSubStrings(String input_str, List<String> resultSubString, int index){
//        for(int i=index; i < input_str.length(); i ++){
//            resultSubString.add(input_str.substring(index,i));
//        }
//    }

    private static void getSubStringsNew(String input,List<String> allSubstrings, int start, int end){
        for(int i=start; i < input.length(); i++){
            allSubstrings.add(input.substring(start, input.length())); //IMPORTANT TO COLLECT THE SUBSTRING WITHIN THE WHILE LOOP
            getSubStringsNew(input, allSubstrings, start+1, input.length());
        }
    }


    private static boolean isExtraordinary(String input){
        int sum = 0;
        for(int i=0; i < input.length(); i++){
            sum += values[input.charAt(i) - 'a'];
        }
        return sum%input.length() == 0;
    }

}
