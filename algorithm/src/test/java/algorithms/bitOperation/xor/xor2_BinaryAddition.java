package algorithms.bitOperation.xor;
/**
 * https://leetcode.com/problems/add-binary/description/
 * Given two binary strings a and b, return their sum as a binary string.
 *
 * Example 1:
 * Input: a = "11", b = "1"
 * Output: "100"
 *
 * Example 2:
 * Input: a = "1010", b = "1011"
 * Output: "10101"
 * Constraints:
 * 1 <= a.length, b.length <= 104
 * a and b consist only of '0' or '1' characters.
 * Each string does not contain leading zeros except for the zero itself.
 */
public class xor2_BinaryAddition {
    public String addBinary(String a, String b) {
        int x = a.length()-1, y = b.length()-1;
        StringBuilder sb = new StringBuilder();
        int i, j, carry = 0;

        while(x > -1 || y > -1 || carry == 1){
            i = (x > -1 ? a.charAt(x)-'0' : 0);
            j = (y > -1 ? b.charAt(y)-'0' : 0);
            sb.insert(0, i ^ j ^ carry);
            carry = (i + j + carry) >= 2 ? 1 : 0;
            x--;y--;
        }
        return sb.toString();
    }
}
