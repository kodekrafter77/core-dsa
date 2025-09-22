import java.util.*;

public class SplitStringsBySeparator {

    public static List<String> splitWordsBySeparator(List<String> words, char separator) {
        List<String> res = new ArrayList<>();

        for (String word : words) {
            res.addAll(split(word, separator));
        }

        return res;
    }

    private static List<String> split(String word, char separator) {
        StringBuilder sb = new StringBuilder();
        List<String> res = new ArrayList<>();
        for (char c: word.toCharArray()) {
            if (c == separator) {
                if (sb.length() != 0) {
                    res.add(sb.toString());
                    sb = new StringBuilder();
                }
            } else {
                sb.append(c);
            }
        }
        if (sb.length() != 0) res.add(sb.toString());
        return res;
    }

    public static void main(String[] args) {
        System.out.println(splitWordsBySeparator(Arrays.asList(new String[] {"|||"}), '|'));
    }
}