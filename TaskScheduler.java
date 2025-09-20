import java.util.*;

public class TaskScheduler {

    public static final char IDLE_TASK = '\0';

    public static int leastInterval(char[] tasks, int n) {
        // Edge case: If there's no cooldown, the time is simply the number of tasks.
        if (n == 0) {
            return tasks.length;
        }

        // --- Step 1: Count task frequencies ---
        Map<Character, Integer> freqMap = new HashMap<>();
        for (char task : tasks) {
            freqMap.put(task, freqMap.getOrDefault(task, 0) + 1);
        }

        // --- Step 2: Populate the Max Heap ---
        // We use a PriorityQueue as a Max Heap to always get the most frequent task.
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Collections.reverseOrder());
        maxHeap.addAll(freqMap.values());

        // --- Step 3: Simulate the schedule ---
        int totalTime = 0;
        // Loop until all tasks are completed (heap is empty).
        while (!maxHeap.isEmpty()) {
            // A temporary list to hold tasks executed in the current round.
            List<Integer> temp = new ArrayList<>();
            int tasksInRound = 0;

            // Each round is n+1 time units long. We try to execute n+1 tasks.
            for (int i = 0; i < n + 1; i++) {
                if (!maxHeap.isEmpty()) {
                    // 'Execute' the most frequent task.
                    int currentFreq = maxHeap.poll();
                    temp.add(currentFreq - 1);
                    tasksInRound++;
                }
            }

            // Add the tasks back to the heap if they still need more processing.
            for (int freq : temp) {
                if (freq > 0) {
                    maxHeap.add(freq);
                }
            }

            // Calculate the time for this round.
            if (maxHeap.isEmpty()) {
                // This was the last round, so no more cooldowns are needed.
                // Add only the time for the tasks that actually ran.
                totalTime += tasksInRound;
            } else {
                // This was a full round, so n+1 time units passed (tasks + idle time).
                totalTime += n + 1;
            }
        }

        return totalTime;
    }

    public static void main(String[] args) {
        System.out.println(leastInterval(new char[]{'A','A','A','B','B','B'}, 2)); // 8
        System.out.println(leastInterval(new char[]{'A','A','A','B','B','B'}, 3)); // 10
    }
}