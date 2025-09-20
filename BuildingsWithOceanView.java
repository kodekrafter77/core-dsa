import java.util.*;

public class BuildingsWithOceanView {
    public static int[] findBuildings(int[] heights) {
        List<Integer> views = new ArrayList<>();
        int n = heights.length;

        views.add(n - 1);
        
        int maxHeightSoFar = heights[n - 1];
        for (int i = n - 2; i >= 0; --i) {
            if (maxHeightSoFar < heights[i]) {
                maxHeightSoFar = heights[i];
                views.add(i);
            }
        }

        Collections.reverse(views);

        return views.stream().mapToInt(v->v).toArray();
    }

    public static void main(String[] args) {
        System.out.println(Arrays.toString(findBuildings(new int[] {4, 2, 3, 1})));
        System.out.println(Arrays.toString(findBuildings(new int[] {4, 3, 2, 1})));
    }
}