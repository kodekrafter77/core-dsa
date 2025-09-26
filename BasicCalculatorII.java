import java.util.*;

public class BasicCalculatorII {

    public static int calculate(String s) {
        Deque<Integer> stack = new ArrayDeque<>();
        int res = 0;
        int currNum = 0;
        char operator = '+';

        for (int i = 0; i < s.length(); ++i) {
            char c = s.charAt(i);
            if (Character.isDigit(c)) {
                currNum = (currNum * 10) + (c - '0');
            }

            if (!Character.isDigit(c) && !Character.isWhitespace(c) || i == s.length() - 1) {
                if (operator == '+') {
                    stack.push(currNum);
                } else if (operator == '-') {
                    stack.push(-currNum);
                } else if (operator == '*') {
                    stack.push(stack.pop() * currNum);
                } else if (operator == '/') {
                    stack.push(stack.pop() / currNum);
                }
                operator = c;
                currNum = 0;
            }
        }

        while (!stack.isEmpty()) {
            res += stack.pop();
        }
        return res;
    }

    public static void main(String[] args) {
        System.out.println(calculate(" 3+5 / 2 ")); // 5
        // System.out.println(calculate("2*(5+5*2)/3+(6/2+8)")); // should be 21, but this evaluates to 11 because the calculate() doesn't honor operator precedence
    }
}