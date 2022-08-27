package coreJava.regularExpression;

import org.assertj.core.api.Assertions;
import org.junit.Test;

public class regexSamples {

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
