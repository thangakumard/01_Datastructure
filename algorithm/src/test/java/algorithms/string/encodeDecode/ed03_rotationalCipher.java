package algorithms.string.encodeDecode;

/***
 https://www.metacareers.com/profile/coding_practice_question/?problem_id=226517205173943&c=1344573719835068&psid=275492097255885&practice_plan=0&b=0222222
 https://leetcode.com/discuss/interview-question/1950476/rotational-cipher

 One simple way to encrypt a string is to "rotate" every alphanumeric character by a certain amount. Rotating a character means replacing it with another character that is a certain number of steps away in normal alphabetic or numerical order.
 For example, if the string "Zebra-493?" is rotated 3 places, the resulting string is "Cheud-726?". Every alphabetic character is replaced with the character 3 letters higher (wrapping around from Z to A), and every numeric character replaced with the character 3 digits higher (wrapping around from 9 to 0). Note that the non-alphanumeric characters remain unchanged.
 Given a string and a rotation factor, return an encrypted string.
 Signature
 string rotationalCipher(string input, int rotationFactor)
 Input
 1 <= |input| <= 1,000,000
 0 <= rotationFactor <= 1,000,000
 Output
 Return the result of rotating input a number of times equal to rotationFactor.
 Example 1
 input = Zebra-493?
 rotationFactor = 3
 output = Cheud-726?
 Example 2
 input = abcdefghijklmNOPQRSTUVWXYZ0123456789
 rotationFactor = 39
 output = nopqrstuvwxyzABCDEFGHIJKLM9012345678
 */
public class ed03_rotationalCipher {
    String rotationalCipher(String input, int rotationFactor) {

        StringBuilder output = new StringBuilder();
        for (char ch: input.toCharArray()) {
            if (Character.isDigit(ch)) {
                output.append((Character.getNumericValue(ch) + rotationFactor) % 10);
            } else if (Character.isAlphabetic(ch)) {
                if (Character.isUpperCase(ch)) {
                    int temp = (ch - 'A' + rotationFactor) % 26;
                    output.append((char) ('A' + temp));
                } else {
                    int temp = (ch - 'a' + rotationFactor) % 26;
                    output.append((char) ('a' + temp));
                }
            } else {
                output.append(ch);
            }
        }
        return output.toString();
    }
}
