import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Collections;
import java.util.NoSuchElementException;

/**
 * A generic implementation of a Min-Heap data structure.
 * The heap's ordering is determined by a Comparator provided at construction time.
 *
 * @param <E> the type of elements held in this heap
 */
public class MinHeap<E> {

    private final List<E> heap;
    private final Comparator<E> comparator;

    public MinHeap(Comparator<E> comparator) {
        this.heap = new ArrayList<>();
        this.comparator = comparator;
    }

    // --- Core Public API ---

    /**
     * Adds an element to the heap.
     * TC: O(log n)
     * @param element the element to add
     */
    public void add(E element) {
        heap.add(element);
        heapifyUp();
    }

    /**
     * Retrieves and removes the minimum element of this heap.
     * TC: O(log n)
     * @return the minimum element
     * @throws NoSuchElementException if the heap is empty
     */
    public E poll() {
        if (heap.isEmpty()) {
            throw new NoSuchElementException("Heap is empty.");
        }
        E element = heap.get(0);
        swap(0, heap.size() - 1);
        heap.remove(heap.size() - 1);
        heapifyDown();
        return element;
    }

    /**
     * Retrieves, but does not remove, the minimum element of this heap.
     * TC: O(1)
     * @return the minimum element
     * @throws NoSuchElementException if the heap is empty
     */
    public E peek() {
        if (heap.isEmpty()) {
            throw new NoSuchElementException("Heap is empty.");
        }
        return heap.get(0);
    }

    /**
     * Returns the number of elements in the heap.
     * @return the size of the heap
     */
    public int size() {
        return heap.size();
    }

    /**
     * Returns true if the heap contains no elements.
     * @return true if the heap is empty
     */
    public boolean isEmpty() {
        return heap.isEmpty();
    }


    // --- Private Helper Methods ---

    private void heapifyUp() {
        int currentIndex = heap.size() - 1;
        while (currentIndex > 0 && isSmaller(heap.get(currentIndex), heap.get(getParentIndex(currentIndex)))) {
            int parentIndex = getParentIndex(currentIndex);
            swap(currentIndex, parentIndex);
            currentIndex = parentIndex;
        }
    }

    private void heapifyDown() {
        int currentIndex = 0;
        while (hasLeftChild(currentIndex)) {
            int smallerChildIndex = getLeftChildIndex(currentIndex);
            if (hasRightChild(currentIndex) && isSmaller(rightChild(currentIndex), leftChild(currentIndex))) {
                smallerChildIndex = getRightChildIndex(currentIndex);
            }

            if (isSmaller(heap.get(smallerChildIndex), heap.get(currentIndex))) {
                swap(currentIndex, smallerChildIndex);
                currentIndex = smallerChildIndex;
            } else {
                break; // The heap property is restored.
            }
        }
    }

    // --- Navigation and Utility Methods ---

    private int getParentIndex(int childIndex) { return (childIndex - 1) / 2; }
    private int getLeftChildIndex(int parentIndex) { return 2 * parentIndex + 1; }
    private int getRightChildIndex(int parentIndex) { return 2 * parentIndex + 2; }

    private boolean hasLeftChild(int index) { return getLeftChildIndex(index) < heap.size(); }
    private boolean hasRightChild(int index) { return getRightChildIndex(index) < heap.size(); }
    
    private E leftChild(int index) { return heap.get(getLeftChildIndex(index)); }
    private E rightChild(int index) { return heap.get(getRightChildIndex(index)); }

    private void swap(int indexOne, int indexTwo) {
        Collections.swap(heap, indexOne, indexTwo);
    }
    
    private boolean isSmaller(E first, E second) {
        return comparator.compare(first, second) < 0;
    }

    // --- Main method for demonstration ---
    public static void main(String[] args) {
        // Create a Min-Heap for Integers
        // We can use a lambda for the comparator: (a, b) -> a - b
        // Or a method reference: Integer::compare
        MinHeap<Integer> minHeap = new MinHeap<>(Integer::compare);

        System.out.println("Adding elements: 5, 3, 8, 1, 9");
        minHeap.add(5);
        minHeap.add(3);
        minHeap.add(8);
        minHeap.add(1);
        minHeap.add(9);

        System.out.println("Heap size: " + minHeap.size());
        System.out.println("Smallest element (peek): " + minHeap.peek());

        System.out.println("\nPolling elements from the heap:");
        while (!minHeap.isEmpty()) {
            System.out.println("Polled: " + minHeap.poll() + ", New size: " + minHeap.size());
        }
    }
}

