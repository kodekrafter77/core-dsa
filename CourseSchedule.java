import java.util.*;

public class CourseSchedule {

    public static boolean canFinish(int numCourses, int[][] prerequisites) {
        Map<Integer, List<Integer>> graph = new HashMap<>();
        int[] inDegree = new int[numCourses];

        for (int[] edge : prerequisites) {
            int course = edge[0];
            int prerequisite = edge[1];
            graph.computeIfAbsent(prerequisite, k->new ArrayList<>()).add(course);
            inDegree[course]++;
        }

        Queue<Integer> queue = new ArrayDeque<>();

        for (int i = 0; i < numCourses; ++i) {
            if (inDegree[i] == 0) {
                queue.offer(i);
            }
        }

        int coursesTaken = 0;

        while (!queue.isEmpty()) {
            int currentCourse = queue.poll();
            ++coursesTaken;
            if (graph.containsKey(currentCourse)) {
                for (int nextCourse : graph.get(currentCourse)) {
                    inDegree[nextCourse]--;
                    if (inDegree[nextCourse] == 0) {
                        queue.offer(nextCourse);
                    }
                }
            }
        }

        return coursesTaken == numCourses;
    }

    private static final int UNVISITED = 0;
    private static final int VISITING = 1;
    private static final int VISITED = 2;

    public static boolean canFinishDFS(int numCourses, int[][] prerequisites) {
        // Step 1: Build the adjacency list graph
        Map<Integer, List<Integer>> adj = new HashMap<>();
        for (int[] prereq : prerequisites) {
            int course = prereq[0];
            int prerequisite = prereq[1];
            adj.computeIfAbsent(prerequisite, k -> new ArrayList<>()).add(course);
        }

        // Step 2: Initialize the state tracker for each course
        int[] visited = new int[numCourses];

        // Step 3: The main loop to start DFS from every unvisited node.
        // This is necessary to handle disconnected components in the graph.
        for (int i = 0; i < numCourses; i++) {
            if (visited[i] == UNVISITED) {
                // If the dfs call returns true, it means a cycle was found.
                if (hasCycle(i, adj, visited)) {
                    return false; // Cannot finish courses if a cycle exists.
                }
            }
        }

        // If we get through all nodes without finding a cycle, it's possible.
        return true;
    }

    /**
     * Recursive DFS helper to detect a cycle.
     * @return true if a cycle is detected, false otherwise.
     */
    private static boolean hasCycle(int course, Map<Integer, List<Integer>> adj, int[] visited) {
        // --- The Core of Cycle Detection ---
        // Mark the current course as VISITING. It's now in our recursion path.
        visited[course] = VISITING;

        // Explore all neighbors (courses that depend on this one)
        if (adj.containsKey(course)) {
            for (int neighbor : adj.get(course)) {
                // If the neighbor is already in our current recursion path (VISITING),
                // we've found a back-edge, which means there's a cycle.
                if (visited[neighbor] == VISITING) {
                    return true;
                }
                
                // If the neighbor is unvisited, start a new DFS from there.
                // If that deeper search finds a cycle, propagate the result up.
                if (visited[neighbor] == UNVISITED) {
                    if (hasCycle(neighbor, adj, visited)) {
                        return true;
                    }
                }
                // If the neighbor is VISITED (state 2), we've already processed it and
                // confirmed it's part of a safe path, so we can ignore it.
            }
        }
        
        // If we've explored all neighbors without finding a cycle, this node is safe.
        // Mark it as VISITED and backtrack.
        visited[course] = VISITED;
        return false;
    }

    public static void main(String[] args) {
        System.out.println(canFinish(2, new int[][]{{1, 0}, {0, 1}})); // false
        System.out.println(canFinish(2, new int[][]{{0, 1}})); // true
    }
}