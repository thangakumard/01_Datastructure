package arrayAlgorithms;

import java.util.HashMap;
import java.util.Stack;

import org.testng.Assert;
import org.testng.annotations.Test;

public class ValidParentheses {

	@Test
	public void isValidParentheses() {
		Assert.assertTrue(checkParentheses("sdsd(asdf)asdas[asdasd]"));
	}

	public boolean checkParentheses(String input) {
		Stack<Character> stack = new Stack<Character>();
		HashMap<Character, Character> map = new HashMap<Character, Character>();
		map.put('(', ')');
		map.put('[', ']');
		map.put('{', '}');
		char j = ' ';

		if (input != null) {

			if (!input.trim().isEmpty()) {

				String s = input.trim();

				for (int i = 0; i < s.length(); i++) {
					j = s.charAt(i);

					if (map.keySet().contains(j)) {
						stack.push(j);
					} else if (map.containsValue(j)) {
						if (!stack.empty() && map.get(stack.peek()) == j) {
							stack.pop();
						} else {
							return false;
						}

					}

				}
			}

		}

		return stack.empty();
	}

}
