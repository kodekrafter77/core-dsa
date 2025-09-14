import java.util.*;

public class BusRoutes {

    public static int numBusesToDestination(int[][] routes, int source, int target) {
        if (source == target) {
            return 0;
        }

        Map<Integer, List<Integer>> stopToBuses = new HashMap<>();
        for (int i = 0; i < routes.length; i++) {
            for (int stop : routes[i]) {
                stopToBuses.computeIfAbsent(stop, k -> new ArrayList<>()).add(i);
            }
        }

        if (!stopToBuses.containsKey(source)) {
            return -1;
        }

        Queue<Integer> queue = new ArrayDeque<>();
        Set<Integer> visitedBuses = new HashSet<>();
        
        // --- OPTIMIZATION: Add a visited set for stops ---
        Set<Integer> visitedStops = new HashSet<>();

        for (int startBus : stopToBuses.get(source)) {
            queue.offer(startBus);
            visitedBuses.add(startBus);
        }

        int busCount = 1;
        while (!queue.isEmpty()) {
            int levelSize = queue.size();
            for (int i = 0; i < levelSize; i++) {
                int currentBus = queue.poll();

                for (int stop : routes[currentBus]) {
                    if (stop == target) {
                        return busCount;
                    }
                    
                    // --- OPTIMIZATION: Check if we've already processed this transfer stop ---
                    if (visitedStops.contains(stop)) {
                        continue;
                    }

                    for (int nextBus : stopToBuses.get(stop)) {
                        if (!visitedBuses.contains(nextBus)) {
                            visitedBuses.add(nextBus);
                            queue.offer(nextBus);
                        }
                    }
                    // Mark this stop as fully processed.
                    visitedStops.add(stop);
                }
            }
            busCount++;
        }

        return -1;
    }

  

    public static void main(String[] args) {
        int[][] routes = {{1, 2, 7}, {3, 6, 7}};
        System.out.println(numBusesToDestination(routes, 1, 6));
    }
}