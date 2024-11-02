package coreJava.regularExpression;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.util.regex.Pattern;

/**
 * Refer : https://regex101.com/
 */
public class regexSamples {

    @Test
    public void removeWhiteSpace(){
        String input = "apple apple         apple";
        String result = input.replaceAll("\\s","");
        Assertions.assertThat(result).isEqualTo("appleappleapple");
    }

    @Test
    public void String_RemoveSpecialChar() {
        String inout = "I_ am@ a @$#$%$her(o";
        String expected = "I am a hero";
        //remove all the spacial char except space
        String result = inout.replaceAll("[^a-zA-Z ]", "");
        Assertions.assertThat(result).isEqualTo("I am a hero");
    }

    @Test
    public void String_RemoveSpecialCharAllowSpace() {
        String inout = "I_ am@ a @$#$%$            her(o";
        String expected = "I am a hero";
        //Step:1 remove all the spacial char except space
        String withoutSpecialChars = inout.replaceAll("[^a-zA-Z0-9\\s]", "");
        // Step 2: Replace multiple spaces with a single space
        String result = withoutSpecialChars.replaceAll("\\s+", " ");
        Assertions.assertThat(result).isEqualTo("I am a hero");
    }

    @Test
    public void Multiple(){
        Assertions.assertThat(Pattern.matches("\\d", "1")).isTrue();
        Assertions.assertThat(Pattern.matches("\\d+", "123456")).isTrue();

        Assertions.assertThat(Pattern.matches("[0-9]", "1")).isTrue();
        Assertions.assertThat(Pattern.matches("[0-9]+", "123456")).isTrue();

        Assertions.assertThat(Pattern.matches("\\D", "a")).isTrue();
        Assertions.assertThat(Pattern.matches("\\D+", "abc")).isTrue();

        Assertions.assertThat(Pattern.matches("[a-zA-Z]", "a")).isTrue();
        Assertions.assertThat(Pattern.matches("[a-zA-Z]+", "abc")).isTrue();

        //String with the length of 6 characters
        Assertions.assertThat(Pattern.matches("[a-zA-Z]{6}", "abcdef")).isTrue();
        Assertions.assertThat(Pattern.matches("[a-zA-Z]+", "abc")).isTrue();
        //a or b or c must appear once
        Assertions.assertThat(Pattern.matches("[abc]?", "a")).isTrue();
        Assertions.assertThat(Pattern.matches("[abc]?", "aaa")).isFalse();

        Assertions.assertThat(Pattern.matches("[456]{1}[0-9]{9}", "4258291831")).isTrue();
    }
    @Test
    public void Test1(){
        String regex = "apple[,]apple[,]apple";
        String input = "apple,apple,apple";
        Assertions.assertThat(input.matches(regex)).isTrue();
    }

    @Test
    public void Test2(){
        String regex = "apple[,]\\w+[,]apple";
        String input = "apple,apple,apple";
        Assertions.assertThat(input.matches(regex)).isTrue();
    }

    @Test
    public void Test3(){
        String regex = ".*\\w+[,]\\w+[,]\\w+[,]apple[,].*banana[,]";
        String input = "orange,apple,banana,orange,apple,orange,orange,banana,";
        Assertions.assertThat(input.matches(regex)).isTrue();
    }

    @Test
    public void Test4(){
        String regex = ".*\\w+[,]\\w+[,]\\w+[,]apple.*banana[,]\\w+[,]banana.*";
        String input = "orange,apple,banana,orange,apple,orange,orange,banana,apple,banana";
        Assertions.assertThat(input.matches(regex)).isTrue();
    }
}
