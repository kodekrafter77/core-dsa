import java.util.*;

public class RemoveKDigits {

    public static String removeKdigits(String num, int k) {
        StringBuilder res = new StringBuilder();
        Deque<Character> stack = new ArrayDeque<>();

        for (char c : num.toCharArray()) {
            while (k > 0 && !stack.isEmpty() && stack.peek() > c) {
                stack.pop();
                --k;
            }
            stack.push(c);
        }

        while (k > 0) {
            stack.pop();
            --k;
        }

        while(!stack.isEmpty()) {
            res.append(stack.removeLast());
        }

        int start = 0;

        while (start < res.length() && res.charAt(start) == '0') {
            ++start;
        }

        res.delete(0, start);

        return res.length() == 0 ? "0" : res.toString();      
    }

    public static void main(String[] args) {
        System.out.println(removeKdigits("1432219", 3)); // 1219
        System.out.println(removeKdigits("10200", 1));   // 200
        System.out.println(removeKdigits("10", 2));      // 0
        System.out.println(removeKdigits("10", 1));
        System.out.println(removeKdigits("12345", 2));   // 123
        System.out.println(removeKdigits("5000", 1));   // 123
    }
}
