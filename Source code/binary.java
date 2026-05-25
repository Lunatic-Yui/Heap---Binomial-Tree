/* 
    Paper Reference (Basic Tree): Stefan Edelkamp, Amr Elmasry, & Jyrki Katajainen. (2017). Optimizing Binary Heaps. 
    Theory of Computing Systems, 61:606-636. DOI 10.1007/s00224-017-9760-2
*/

import java.util.Arrays;
import java.util.Scanner;

public class BinaryMinHeap {
    private int[] heap;
    private int size;
    private int capacity;

    public BinaryMinHeap(int capacity) {
        this.capacity = capacity;
        this.size = 0;
        this.heap = new int[capacity];
    }

    // indexing
    private int parent(int i) { return (i - 1) / 2; }
    private int leftChild(int i) { return (2 * i) + 1; }
    private int rightChild(int i) { return (2 * i) + 2; }

    private void swap(int i, int j) {
        int temp = heap[i];
        heap[i] = heap[j];
        heap[j] = temp;
    }
  
    public void insert(int key) {
        if (size >= capacity) {
            System.out.println("Heap is full");
            return;
        }
        heap[size] = key;
        size++;
        siftUp(size - 1);
    }

    private void siftUp(int i) {
        while (i > 0 && heap[parent(i)] > heap[i]) {
            swap(i, parent(i));
            i = parent(i);
        }
    }
      
    public int extractMin() {
        if (size <= 0) throw new RuntimeException("Heap is empty");
        if (size == 1) {
            size--;
            return heap[0];
        }
        int min = heap[0];
        heap[0] = heap[size - 1];
        size--;
        siftDown(0);
        return min;
    }

    private void siftDown(int i) {
        int smallest = i;
        int left = leftChild(i);
        int right = rightChild(i);

        if (left < size && heap[left] < heap[smallest]) smallest = left;
        if (right < size && heap[right] < heap[smallest]) smallest = right;

        if (smallest != i) {
            swap(i, smallest);
            siftDown(smallest);
        }
    }

    public int peek() {
        if (size <= 0) throw new RuntimeException("Heap is empty");
        return heap[0];
    }
    
    public static BinaryMinHeap buildHeap(int[] arr) {
        BinaryMinHeap h = new BinaryMinHeap(arr.length);
        h.heap = Arrays.copyOf(arr, arr.length);
        h.size = arr.length;
        for (int i = h.size / 2 - 1; i >= 0; i--) { h.siftDown(i); }
        return h;
    }
    
    public void delete(int i) {
        if (i >= size) throw new IndexOutOfBoundsException();
        decreaseKey(i, Integer.MIN_VALUE);
        extractMin();
    }
    
    public void decreaseKey(int i, int newVal) {
        if (newVal > heap[i]) throw new IllegalArgumentException("New value must be smaller than current value!");
        heap[i] = newVal;
        siftUp(i);
    }

    public int getSize() { return size; }
    public boolean isEmpty() { return size == 0; }

    @Override
    public String toString() {
        return "BinaryMinHeap" + Arrays.toString(Arrays.copyOf(heap, size));
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        BinaryMinHeap heap = new BinaryMinHeap(30);

        System.out.println("===== BINARY MIN-HEAP USER INPUT =====");
        System.out.println("1. Insert | 2. Extract Min | 3. Peek Min | 4. Delete Index | 5. Print Heap | 6. Exit");

        while (true) {
            System.out.print("\nSelect option: ");
            int opsi = in.nextInt();

            if (opsi == 1) {
                System.out.print("Enter data value: ");
                int val = in.nextInt();
                heap.insert(val);
                System.out.println(val + " successfully inserted.");
            } else if (opsi == 2) {
                if (heap.isEmpty()) {
                    System.out.println("Heap is empty!");
                } else {
                    System.out.println("Min value extracted: " + heap.extractMin());
                }
            } else if (opsi == 3) {
                if (heap.isEmpty()) {
                    System.out.println("Heap is empty!");
                } else {
                    System.out.println("Current minimum value: " + heap.peek());
                }
            } else if (opsi == 4) {
                if (heap.isEmpty()) {
                    System.out.println("Heap is empty!");
                } else {
                    System.out.print("Enter index to delete: ");
                    int idx = in.nextInt();
                    if (idx >= heap.getSize() || idx < 0) {
                        System.out.println("Index out of bounds!");
                    } else {
                        heap.delete(idx);
                        System.out.println("Element at index " + idx + " successfully deleted.");
                    }
                }
            } else if (opsi == 5) {
                System.out.println("Current Heap state: " + heap);
            } else if (opsi == 6) {
                System.out.println("Exiting program.");
                break;
            } else {
                System.out.println("Invalid option.");
            }
        }
        in.close();
    }
}