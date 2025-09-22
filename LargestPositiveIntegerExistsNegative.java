import java.util.*;

public class LargestPositiveIntegerExistsNegative {

    public static int findMaxK(int[] nums) {
        Set<Integer> s = new HashSet<>();
        int maxNum = -1;
        for (int num : nums) {
            if (num < 0 && s.contains(-num)) {
                maxNum = Math.max(-num, maxNum);
            } else if (num > 0 && s.contains(-num)) {
                maxNum = Math.max(num, maxNum);
            } else {
                s.add(num);
            }
        }
        return maxNum;
    }

    public static void main(String[] args) {
        System.out.println(findMaxK(new int[] {-1,2,-3,3}));
        System.out.println(findMaxK(new int[] {-1,10,6,7,-7,1}));
        System.out.println(findMaxK(new int[] {-10,8,6,7,-2,-3}));
        System.out.println(findMaxK(new int[] {-7, 1, 6, -1, 7}));
    }
}