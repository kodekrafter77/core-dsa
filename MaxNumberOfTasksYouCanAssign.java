import java.util.*;

public class MaxNumberOfTasksYouCanAssign {
  public static int maxTaskAssign(int[] tasks, int[] workers, int pills, int strength) {
        int n = tasks.length;
        int m = workers.length;
        
        Arrays.sort(tasks);
        Arrays.sort(workers);
        
        int left = 0;
        int right = Math.min(m, n);
        int ans = 0;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (check(tasks, workers, pills, strength, mid)) {
                ans = mid;
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return ans;
    }

    private static boolean check(int[] tasks, int[] workers, int pills, int strength, int k) {
        int taskIndex = 0;
        int remainingPills = pills;

        Deque<Integer> tasksQueue = new ArrayDeque<>();

        for (int worker = workers.length - k; worker < workers.length; ++worker) {
            while (taskIndex < k && tasks[taskIndex] <= workers[worker] + strength) {
                tasksQueue.offer(tasks[taskIndex]);
                ++taskIndex;
            }

            if (tasksQueue.isEmpty()) return false;

            if (tasksQueue.peekFirst() <= workers[worker]) {
                tasksQueue.pollFirst();
            } else if (remainingPills == 0) {
                return false;
            } else {
                tasksQueue.pollLast();
                --remainingPills;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        System.out.println(maxAssign(new int[]{3, 2, 1}, new int[]{0, 3, 3}, 1, 1));
    }
}