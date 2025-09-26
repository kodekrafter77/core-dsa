import java.util.*;

public class MergeKLists {
    static class ListNode {
        int val;
        ListNode next;
        ListNode() {}
        ListNode(int val) { this.val = val; }
        ListNode(int val, ListNode next) { this.val = val; this.next = next; }

        public String toString() {
            return "" + this.val;
        }

        public int value() {
            return val;
        }

        public static ListNode build(int ... vals) {
            ListNode head = null;
            ListNode tail = null; // better name than headRef
            for (int v : vals) {
                if (head == null) {
                    head = new ListNode(v);
                    tail = head;
                } else {
                    tail.next = new ListNode(v); // link new node
                    tail = tail.next;            // move tail forward
                }
            }
            return head;
        }

        public static void print(ListNode head) {
            var node = head;
            List<Integer> out = new ArrayList<>();

            while (node != null) {
                out.add(node.val);
                node = node.next;
            }
            System.out.println(out);
        }
    }
    
    public static ListNode mergeKLists(ListNode[] lists) {
       PriorityQueue<ListNode> minHeap = new PriorityQueue<>(Comparator.comparingInt(node -> node.val));
        ListNode dummy = new ListNode(-1);
        for (ListNode list : lists) {
            if (list != null) {
                minHeap.offer(list);
            }
        }

        ListNode current = dummy;
        while (!minHeap.isEmpty()) {
            ListNode smallestNode = minHeap.poll();
            current.next = smallestNode;
            current = current.next;
            if (smallestNode.next != null) {
               minHeap.add(smallestNode.next);
            }
        }

        return dummy.next;
    }

    public static void main(String[] args) {
        ListNode.print(mergeKLists(new ListNode[] {
            ListNode.build(1, 4, 5),
            ListNode.build(1, 3, 4),
            ListNode.build(2, 6)
        }));
    }
}