import java.util.*;

public class BasicCalculatorIII {

    public static int calculate(String s) {
        Deque<Character> queue = new ArrayDeque<>();
        for (char c : s.toCharArray()) {
            queue.offer(c);
        }
        queue.offer('+');
        return doCalculate(queue);
    }

    private static int doCalculate(Deque<Character> queue) {
        int res = 0;
        int currNum = 0;
        char operator = '+';
        Deque<Integer> stack = new ArrayDeque<>();

        while (!queue.isEmpty()) {
            char c = queue.poll();

            if (Character.isDigit(c)) {
                currNum = (currNum * 10) + (c - '0');
            } else if (c == '(') {
                currNum = doCalculate(queue);
            } else {
                if (operator == '+') {
                    stack.push(currNum);
                } else if (operator == '-') {
                    stack.push(-currNum);
                } else if (operator == '*') {
                    stack.push(stack.pop() * currNum);
                } else if (operator == '/') {
                    stack.push(stack.pop() / currNum);
                }
                currNum = 0;
                operator = c;

                 if (c == ')') break;
            }
        }

        while (!stack.isEmpty()) res += stack.pop();

        return res;
    }

    public static void main(String[] args) {
        System.out.println(calculate("2*(5+5*2)/3+(6/2+8)")); // 21
        System.out.println(calculate("6-4/2")); // 4
    }
}