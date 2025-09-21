import java.util.*;

class RangeModule {

    private class Interval {
        int left;
        int right;

        public Interval(int left, int right) {
            this.left = left;
            this.right = right;
        }

        @Override
        public String toString() {
            return "[" + this.left + ", " + this.right + ")";
        }
    }

    private NavigableSet<Interval> ranges = new TreeSet<Interval>((a, b) -> a.left - b.left);

    public RangeModule() {
        
    }

    public void addRange(int left, int right) {
        int newLeft = left;
        int newRight = right;

        // Find all overlapping intervals
        Iterator<Interval> iter = ranges.iterator();
        while (iter.hasNext()) {
            Interval curr = iter.next();
            
            // Check overlap condition: curr.left <= right && left <= curr.right
            if (curr.left <= right && left <= curr.right) {
                newLeft = Math.min(newLeft, curr.left);
                newRight = Math.max(newRight, curr.right);
                iter.remove();
            }
            
            // Optimization: if curr.left > right, we can break
            // (since intervals are sorted by left)
            if (curr.left > right) {
                break;
            }
        }    
        // Add the merged interval
        ranges.add(new Interval(newLeft, newRight));
    }
    
    public boolean queryRange(int left, int right) {
        Interval candidate = ranges.floor(new Interval(left, left));

        if (candidate == null) {
            return false;
        }

        return candidate.right >= right;
    }
    
   public void removeRange(int left, int right) {
        Iterator<Interval> itr = ranges.iterator(); // Fix: use 'ranges'
        List<Interval> toAdd = new ArrayList<>();
        
        while (itr.hasNext()) {
            Interval current = itr.next();
            if (current.left >= right) {
                break; // No more overlapping intervals possible
            }
            if (current.right > left) { // Check overlap condition
                // Keep left part if it exists
                if (current.left < left) {
                    toAdd.add(new Interval(current.left, left));
                }
                // Keep right part if it exists  
                if (current.right > right) {
                    toAdd.add(new Interval(right, current.right));
                }
                itr.remove(); // Remove the original interval
            }
        }
    
        // Add new intervals
        for (Interval interval : toAdd) {
            ranges.add(interval);
        }
    }
}