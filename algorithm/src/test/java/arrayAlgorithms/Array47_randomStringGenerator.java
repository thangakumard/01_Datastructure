package arrayAlgorithms;

import java.security.SecureRandom;
import java.util.Locale;
import java.util.Objects;
import java.util.Random;
import org.testng.annotations.Test;

/****
 * 
 * https://code-examples.net/en/q/a093
 * https://www.edureka.co/community/1779/generate-an-alpha-numeric-string-randomly
 *
 */

public class Array47_randomStringGenerator {

	@Test
    public void Test(){
      System.out.println(new RandomString(6).nextString());
    }
}
