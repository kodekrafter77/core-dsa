import java.util.*;

public class MedianFinder {

    private PriorityQueue<Integer> lowers, uppers;

    public MedianFinder() {
        lowers = new PriorityQueue<>(Collections.reverseOrder());
        uppers = new PriorityQueue<>();
    }
    
    public void addNum(int num) {
        // Step 1: Always add to the Max-Heap first.
        lowers.add(num);

        // Step 2: Fix the ordering invariant. The largest in `lowers` might belong in `uppers`.
        // This single move guarantees max(lowers) <= min(uppers).
        uppers.add(lowers.poll());

        // Step 3: Fix the balancing invariant. `lowers` must be >= `uppers`.
        // This can only ever be off by one element.
        if (uppers.size() > lowers.size()) {
            lowers.add(uppers.poll());
        }
    }
    
    public double findMedian() {
        // If heaps have equal size, the total count is even.
        if (lowers.size() == uppers.size()) {
            // Median is the average of the two middle elements.
            return (lowers.peek() + uppers.peek()) / 2.0;
        } 
        // Otherwise, the total count is odd.
        else {
            // The `lowers` heap holds the single middle element.
            return lowers.peek();
        }
    }
}
