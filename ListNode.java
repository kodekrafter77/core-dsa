import java.util.*;

public class ListNode {
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