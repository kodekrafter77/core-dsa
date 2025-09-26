import java.util.*;

public class DecodeString {

    public static String decodeString(String s) {
        if (s == null || s.length() == 0) return null;
        Deque<Character> queue = new ArrayDeque<>();
        for (char c : s.toCharArray()) {
            queue.offer(c);
        }
        return decode(queue);
    }

    private static String decode(Deque<Character> queue) {
        StringBuilder sb = new StringBuilder();
        int num = 0;
        while (!queue.isEmpty()) {
            char c = queue.poll();
            if (Character.isDigit(c)) {
                num = (num * 10) + (c - '0');
            } else if (c == '[') {
                String s = decode(queue);
                sb.repeat(s, num);
                num = 0;
            } else if (c == ']') {
               break;
            } else if (Character.isLetter(c)) {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        System.out.println(decodeString("3[a2[c]]"));
    }
}