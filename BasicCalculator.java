import java.util.*;

public class BasicCalculator {

    public static int calculate(String s) {
        int res = 0;
        int sign = 1; // assuming '+'
        Deque<Integer> stack = new ArrayDeque<>();
        int num = 0;

        for (int i = 0; i < s.length(); ++i) {
            if (Character.isDigit(s.charAt(i))) {
                num = (num * 10) + (s.charAt(i) - '0');
            } else {
                res += num * sign;
                num = 0;
                char c = s.charAt(i);
                if (c == '+') {
                    sign = 1;
                } else if (c == '-') {
                    sign = -1;
                } else if (c == '(') {
                    stack.push(res);
                    stack.push(sign);
                    res = 0;
                    sign = 1;
                } else if (c == ')') {
                    res *= stack.pop(); // sign
                    res += stack.pop(); // previous result
                }
                // any other characters are skipped including whitespaces
            }
        }
        res += num * sign; // For the last number
        return res;
    }



    public static void main(String[] args) {
        System.out.println(calculate("(1+(4+5+2)-3)+(6+8)"));
        System.out.println(calculate("1 + 1"));
        System.out.println(calculate("2  + 1 - (3 + 4 - 6"));
    }
}