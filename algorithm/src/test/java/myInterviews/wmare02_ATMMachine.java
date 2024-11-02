package myInterviews;

import org.testng.annotations.Test;
import java.util.Arrays;

/**An ATM machine has a list of bills available(configurable array):
 Example: Bills_Available = [10, 40, 20, 50]
  
 A user is able to input on the pin pad a desired amount:
 Example: Desired_Amount = 330
  
 Write a method that:
 - Make sure the desired amount is a multiple of 10
 - Returns the least number of bills to match that amount
 - Write tests for the above

 Example:
 Desired_Amount = 330
 bills returned:
 50*6
 20*1
 10*1
 */
/**
 TDD
 1. [] ,  * 10 => 0
 2. [10] ,  100 => 10
 3. [10,20], 101 => 0
 4. [10,20,40]  330 => 8 (50 *6 , 20 * 1, 10 * 1)
 5. [10,20,40]  -1 => 0
 6. [10,20,40]  0 => 0
 */


//Example: Bills_Available = [(100,1), (20,5), (50, 4), (10,-1)]

public class wmare02_ATMMachine{
    @Test
    public  void ATMMachineTest(String[] arg){

        //TC#4
        int[] bills = new int[] {10,40,20, 50};
        int numberOfBills = 0;
        numberOfBills = getAmount(bills, 330);
        System.out.println("TC#4: " + numberOfBills);

        //TC#1
        numberOfBills =  getAmount( null, 330);
        System.out.println("TC#1 :" + numberOfBills);

        //TC#2
        bills = new int[] {10};
        numberOfBills =  getAmount(bills, 10);
        System.out.println("TC#2 :" + numberOfBills);
    }
    public static int getAmount(int[] bills, int desiredAmount){
        if(bills == null || bills.length == 0)
            return 0;
        if(desiredAmount < 0 || desiredAmount%10 != 0)
            return 0;

        Arrays.sort(bills);
        int numberOfBills = 0;

        for(int i=bills.length-1; i >=0; i--){

            while(desiredAmount >= bills[i]){
                numberOfBills += desiredAmount / bills[i];
                desiredAmount = desiredAmount % bills[i];
            }
            if(desiredAmount == 0) break;

        }
        return numberOfBills;
    }
}


