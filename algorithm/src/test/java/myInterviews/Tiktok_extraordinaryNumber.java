package myInterviews;
/*

Each character of the lowercase English alphabet has been mapped to digits as shown in the figure. The numerical value corresponding to each letter is its mapped value.







An extraordinary substring is one whose sum of the mapped values of each letter is divisible by its length. Given string input_str, count its total number of non-empty extraordinary substrings.



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
}
