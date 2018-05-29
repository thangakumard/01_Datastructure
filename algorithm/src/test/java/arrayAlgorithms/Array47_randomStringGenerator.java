package arrayAlgorithms;

import java.security.SecureRandom;
import java.util.Locale;
import java.util.Objects;
import java.util.Random;

import org.testng.annotations.Test;

public class Array47_randomStringGenerator {

	
	public static final String upper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public static final String lower = upper.toLowerCase(Locale.ROOT);

    public static final String digits = "0123456789";

    public static final String alphanum = upper + lower + digits;

    private Random random= null;

    private char[] symbols = null;

    private char[] buf = null;
    
    
    @Test
    public void Test(){
    	RandomString(6);
    }
    
    public void RandomString(int length, Random random, String symbols) {
        if (length < 1) throw new IllegalArgumentException();
        if (symbols.length() < 2) throw new IllegalArgumentException();
        this.random = Objects.requireNonNull(random);
        this.symbols = symbols.toCharArray();
        this.buf = new char[length];
    }

    /**
     * Create an alphanumeric string generator.
     */
    public void RandomString(int length, Random random) {
    	RandomString(length, random, alphanum);
    }

    /**
     * Create an alphanumeric strings from a secure generator.
     */
    public void RandomString(int length) {
    	RandomString(length, new SecureRandom());
    }

   
}
