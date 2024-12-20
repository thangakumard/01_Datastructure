package coreJava.casting;

import org.testng.annotations.Test;

public class CastingInt {
    @Test
    public void CharToInt() {
        String str = "198";
        System.out.println(Character.getNumericValue(str.charAt(0)));
        System.out.println(Character.getNumericValue(str.charAt(1)));
        System.out.println(Character.getNumericValue(str.charAt(2)));


        /******* Get integer value in the index WITHOUT USING INBUILT FUNCTIONS *********/
        System.out.println("integer value in the index 0 :" + (str.charAt(0) - '0'));//returns 1
        System.out.println("integer value in the index 1 :" + (str.charAt(1) - '0'));//returns 9
        System.out.println("integer value in the index 2 :" + (str.charAt(2) - '0'));//returns 8
        /****WRONG Way to get integer value in the index ****/
        System.out.println("WRONG Way to get integer value :" + str.charAt(0) + '0'); //returns 10
        System.out.println("WRONG Way to get integer value :" + str.charAt(1) + '0'); //returns 90

        /***** Get integer ASCII value in the index *********/
        str = "123";
        System.out.println("ASCII value in the index 0 :" + (str.charAt(0) - 0));//returns 1
        System.out.println("ASCII value in the index 1:" + (str.charAt(1) - 0));//returns 1
        System.out.println("ASCII value in the index 2 :" + (str.charAt(2) - 0));//returns 1

        /***** To get ASCII values *********/
        str = "abc";
        System.out.println("ASCII value in the index 0 with -0:" + (str.charAt(0) - 0));//returns 1
        System.out.println("ASCII value in the index 1 with -0 :" + (str.charAt(1) - 0));//returns 1
        System.out.println("ASCII value in the index 2 with -0 :" + (str.charAt(2) - 0));//returns 1

        System.out.println("ASCII value in the index 0  with +0:" + (str.charAt(0) + 0));//returns 1
        System.out.println("ASCII value in the index 0  with +0 :" + (str.charAt(1) + 0));//returns 1
        System.out.println("ASCII value in the index 0  with +0 :" + (str.charAt(1) + 0));//returns 1

        /***ASCII value for a = 97 , z = 122 **/
        for(int i='a'-0; i <= 'z'-0; i++){
            System.out.println("Int value of "+  (char) (i - 0) + " is " +i);
        }
        /***ASCII value for a = 65 , z = 90 **/
        for(int i='A'-0; i <= 'Z'-0; i++){
            System.out.println("Int value of "+  (char) (i - 0) + " is " +i);
        }
    }
    @Test
    public void StringToInt() {
        String str = "10";
        int i = Integer.parseInt(str); // To covert String to int (Primitive Type)
    }

    @Test
    public void intToInteger() {
        int i = 10;
        Integer j = Integer.valueOf(i); // To covert int to Integer
    }

    @Test
    public void IntegerToInt(){
        int IntegerValue = 10; // To covert String to int (Primitive Type)
        int intValue = IntegerValue; // To covert int to Integer
        System.out.println("intValue is :" + intValue);
    }

    @Test
    public void DoubleToInt(){

        double doubleValue = 15.4;
        int intValue = (int) doubleValue;
        System.out.println("(int) 15.4: " + intValue);

        //Convert Double to Int with Rounding
        double doubleValue1 = -21.3;
        double doubleValue2 = -21.7;

        int intValue1 = (int) Math.round(doubleValue1);
        int intValue2 = (int) Math.round(doubleValue2);
        System.out.println("(int) Math.round(-21.3): " + intValue1);
        System.out.println("(int) Math.round(-21.7): " + intValue2);

        //Double to int/Integer
        Double d = 5.25;
        Integer value = d.intValue();
        int x = d.intValue();
        System.out.println("(5.25).intValue(): " + value);
    }

    @Test
    public void LongToInt(){
        //Refer : https://stackoverflow.com/questions/1590831/safely-casting-long-to-int-in-java
        long foo = 10L;
        System.out.println("Math.toIntExact(foo) :" + Math.toIntExact(foo));
    }

    @Test
    public void handleMaxIntValue(){

        System.out.println("Int MaxValue is " + Integer.MAX_VALUE);
        int i = 1000000000; //1 Billion
        int j = 2000000000; //2 Billion (Max value : 2,147,483,647)
    }
}
