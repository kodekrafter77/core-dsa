import java.util.*;

public class IncreasingTripletSubsequence {

    public static boolean increasingTriplet(int[] nums) {
        // Handle arrays that are too short to have a triplet.
        if (nums == null || nums.length < 3) {
            return false;
        }

        // Initialize two variables to hold the smallest and second-smallest numbers.
        int firstNum = Integer.MAX_VALUE;
        int secondNum = Integer.MAX_VALUE;

        // Loop through every number in the array.
        for (int num : nums) {
        // If the current number is the smallest we've seen, update firstNum.
            if (num <= firstNum) {
                firstNum = num;
            } 
            // If it's not the smallest, but it's smaller than the second-smallest,
            // update secondNum. This creates a smaller (i, j) pair.
            else if (num <= secondNum) {
                secondNum = num;
            } 
            // If the number is greater than both firstNum and secondNum,
            // we have found our triplet (firstNum < secondNum < num).
            else {
                return true;
            }
        }

        // If we get through the whole loop, no triplet was found.
        return false;
    }

    public static void main(String[] args) {
        System.out.println(increasingTriplet(new int[] {2, 1, 5, 0, 4, 6}));
        System.out.println(increasingTriplet(new int[] {1, 2, 3, 4, 5}));
        System.out.println(increasingTriplet(new int[] {5, 4, 3, 2, 1}));
    }
}