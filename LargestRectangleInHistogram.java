import java.util.*;

public class LargestRectangleInHistogram {
    
   public static int largestRectangleArea(int[] heights) {
        int maxArea = 0;
        // The stack is initialized empty, with no -1.
        Deque<Integer> stack = new ArrayDeque<>();

        for (int i = 0; i < heights.length; ++i) {
            // The loop condition now explicitly checks if the stack is empty.
            while (!stack.isEmpty() && heights[stack.peek()] >= heights[i]) {
                int currHeight = heights[stack.pop()];
                
                // The width calculation now needs to handle the empty stack case.
                // If the stack is empty, the rectangle goes to the start (width = i).
                // Otherwise, it goes to the new stack top (width = i - stack.peek() - 1).
                int currWidth = stack.isEmpty() ? i : i - stack.peek() - 1;
                
                maxArea = Math.max(maxArea, currWidth * currHeight);
            }
            stack.push(i);
        }

        // The final cleanup loop must also handle the empty stack case.
        while (!stack.isEmpty()) {
            int currHeight = heights[stack.pop()];
            int currWidth = stack.isEmpty() ? heights.length : heights.length - stack.peek() - 1;
            maxArea = Math.max(maxArea, currWidth * currHeight);
        }

        return maxArea;
    }

    public static void main(String[] args) {
        System.out.println(largestRectangleArea(new int[] {2, 1, 5, 6, 2, 3}));
        System.out.println(largestRectangleArea(new int[] {2, 4}));
    }
}