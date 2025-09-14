import java.util.*;

public class MergeKLists {
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