package algorithms.companies.amazon;

import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.testng.annotations.Test;

/*
 * https://leetcode.com/discuss/interview-question/932920/
 * 
 * Amazon Fresh is running a promotion in which customers receive prizes for purchasing a secret combination of fruits. The combination will change each day, and the team running the promotion wants to use a code list to make it easy to change the combination. The code list contains groups of fruits. Both the order of the groups within the code list and the order of the fruits within the groups matter. However, between the groups of fruits, any number, and type of fruit is allowable. The term "anything" is used to allow for any type of fruit to appear in that location within the group.

Consider the following secret code list: [[apple, apple], [banana, anything, banana]]
Based on the above secret code list, a customer who made either of the following purchases would win the prize:
orange, apple, apple, banana, orange, banana
apple, apple, orange, orange, banana, apple, banana, banana

Write an algorithm to output 1 if the customer is a winner else output 0.

Input
The input to the function/method consists of two arguments:
codeList, a list of lists of strings representing the order and grouping of specific fruits that must be purchased in order to win the prize for the day.
shoppingCart, a list of strings representing the order in which a customer purchases fruit.

Output
Return an integer 1 if the customer is a winner else return 0.

Note
'anything' in the codeList represents that any fruit can be ordered in place of 'anything' in the group. 'anything' has to be something, it cannot be "nothing."
'anything' must represent one and only one fruit.
If secret code list is empty then it is assumed that the customer is a winner.

Example 1:

Input: codeList = [[apple, apple], [banana, anything, banana]] shoppingCart = [orange, apple, apple, banana, orange, banana]
Output: 1
Explanation:
codeList contains two groups - [apple, apple] and [banana, anything, banana].
The second group contains 'anything' so any fruit can be ordered in place of 'anything' in the shoppingCart. The customer is a winner as the customer has added fruits in the order of fruits in the groups and the order of groups in the codeList is also maintained in the shoppingCart.

Example 2:

Input: codeList = [[apple, apple], [banana, anything, banana]]
shoppingCart = [banana, orange, banana, apple, apple]
Output: 0
Explanation:
The customer is not a winner as the customer has added the fruits in order of groups but group [banana, orange, banana] is not following the group [apple, apple] in the codeList.

Example 3:

Input: codeList = [[apple, apple], [banana, anything, banana]] shoppingCart = [apple, banana, apple, banana, orange, banana]
Output: 0
Explanation:
The customer is not a winner as the customer has added the fruits in an order which is not following the order of fruit names in the first group.

Example 4:

Input: codeList = [[apple, apple], [apple, apple, banana]] shoppingCart = [apple, apple, apple, banana]
Output: 0
Explanation:
The customer is not a winner as the first 2 fruits form group 1, all three fruits would form group 2, but can't because it would contain all fruits of group 1.


 */
public class Amazon_04_AmazonFreshPromotion {
	
	@Test
	public void test() {

            String[][] codeList1 = { { "apple", "apple" }, { "banana", "anything", "banana" } };
        String[] shoppingCart1 = {"orange", "apple", "apple", "banana", "orange", "banana"};
        Assertions.assertThat(winner(codeList1, shoppingCart1)).isEqualTo(1);

        String[][] codeList2 = { { "apple", "apple" }, { "banana", "anything", "banana" } };
        String[] shoppingCart2 = {"banana", "orange", "banana", "apple", "apple"};
        Assertions.assertThat(winner(codeList2, shoppingCart2)).isEqualTo(0);

        String[][] codeList3 = { { "apple", "apple" }, { "banana", "anything", "banana" } };
        String[] shoppingCart3 = {"apple", "banana", "apple", "banana", "orange", "banana"};
        Assertions.assertThat(winner(codeList3, shoppingCart3)).isEqualTo(0);

        String[][] codeList4 = { { "apple", "apple" }, { "apple", "apple", "banana" } };
        String[] shoppingCart4 = {"apple", "apple", "apple", "banana"};
        Assertions.assertThat(winner(codeList4, shoppingCart4)).isEqualTo(0);

        String[][] codeList5 = { { "apple", "apple" }, { "banana", "anything", "banana" } };
        String[] shoppingCart5 = {"orange", "apple", "apple", "banana", "orange", "banana"};
        Assertions.assertThat(winner(codeList5, shoppingCart5)).isEqualTo(1);

        String[][] codeList6 = { { "apple", "apple" }, { "banana", "anything", "banana" }  };
        String[] shoppingCart6 = {"apple", "apple", "orange", "orange", "banana", "apple", "banana", "banana"};
        Assertions.assertThat(winner(codeList6, shoppingCart6)).isEqualTo(1);

        String[][] codeList7= { { "anything", "apple" }, { "banana", "anything", "banana" }  };
        String[] shoppingCart7 = {"orange", "grapes", "apple", "orange", "orange", "banana", "apple", "banana", "banana"};
        Assertions.assertThat(winner(codeList7, shoppingCart7)).isEqualTo(1);

        String[][] codeList8 = {{"apple", "orange"}, {"orange", "banana", "orange"}};
        String[] shoppingCart8 = {"apple", "orange", "banana", "orange", "orange", "banana", "orange", "grape"};
        Assertions.assertThat(winner(codeList8, shoppingCart8)).isEqualTo(1);

        String[][] codeList9= { { "anything", "anything", "anything", "apple" }, { "banana", "anything", "banana" }  };
        String[] shoppingCart9 = {"orange", "apple", "banana", "orange", "apple", "orange", "orange", "banana", "apple", "banana"};
        Assertions.assertThat(winner(codeList9, shoppingCart9)).isEqualTo(1);

         String[][] codeList10= { { "anything", "anything", "anything", "apple" }, { "banana", "anything", "banana" }  };
         String[] shoppingCart10 = { "apple", "apple", "orange", "banana", "apple", "banana"};
         Assertions.assertThat(winner(codeList10, shoppingCart10)).isEqualTo(0);

	}

        public  int winner(String[][] codes, String[] shoppingCart){
                StringBuilder regex = new StringBuilder(".*");
                for(String[] code : codes){
                        //{ { "apple", "apple" }, { "banana", "anything", "banana" }  } into .*apple[,]apple.*banana[,]\w+[,]banana.*
                        String codeWithDelimit = String.join("[,]",code).replace("anything","\\w+");
                        regex.append(codeWithDelimit);
                        regex.append(".*");
                }
                String cart = String.join(",",shoppingCart);
                return cart.matches(regex.toString()) ? 1 : 0;
        }
}
