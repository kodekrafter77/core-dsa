import java.util.*;

public class  KthLargestElement {

    public static int findKthLargestUsingMinHeap(int[] nums, int k) {
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        for (int num : nums) {
            pq.offer(num);
            if (!pq.isEmpty() && pq.size() > k) {
                pq.poll();
            }
        }
        return pq.peek();
    }

    public static int findKthLargest(int[] nums, int k) {
        int left = 0;
        int right = nums.length - 1;
        // The index we are looking for is the (n-k)th element in a 0-indexed sorted array.
        int targetIndex = nums.length - k;
        Random rand = new Random();

        while (left <= right) {
            // Mitigate the worst-case O(N^2) by choosing a random pivot.
            int randomPivotIndex = left + rand.nextInt(right - left + 1);
            int finalPivotIndex = partition(nums, left, right, randomPivotIndex);

            if (finalPivotIndex == targetIndex) {
                return nums[finalPivotIndex];
            } else if (finalPivotIndex < targetIndex) {
                left = finalPivotIndex + 1;
            } else { // finalPivotIndex > targetIndex
                right = finalPivotIndex - 1;
            }
        }
        // This line should not be reached given the problem constraints.
        return -1; 
    }

    /**
     * Partitions the subarray around a chosen pivot.
     * @return The final index of the pivot element after partitioning.
     */
    private static int partition(int[] nums, int left, int right, int pivotIndex) {
        int pivotValue = nums[pivotIndex];
        // 1. Move pivot to the end.
        swap(nums, pivotIndex, right);
        int storeIndex = left;

        // 2. Move all smaller elements to the left.
        for (int i = left; i < right; i++) {
            if (nums[i] < pivotValue) {
                swap(nums, storeIndex, i);
                storeIndex++;
            }
        }

        // 3. Move pivot to its final sorted position.
        swap(nums, right, storeIndex);
        return storeIndex;
    }

    private static void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

    public static void main(String[] args) {
        int[] nums = {3,2,1,5,6,4};
        System.out.println(findKthLargest(nums, 2));
    }
}