import java.util.Arrays;

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

    // Insert elemen baru ke heap : O(log n)    
    public void insert(int key) {
        if (size >= capacity) {
            System.out.println("Heap penuh!");
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
    
    // Ambil dan hapus elemen minimum (root) : O(log n)    
    public int extractMin() {
        if (size <= 0) throw new RuntimeException("Heap kosong!");
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

        if (left < size && heap[left] < heap[smallest])
            smallest = left;
        if (right < size && heap[right] < heap[smallest])
            smallest = right;

        if (smallest != i) {
            swap(i, smallest);
            siftDown(smallest);
        }
    }

    // Lihat elemen minimum tanpa menghapus : O(1)
    public int peek() {
        if (size <= 0) throw new RuntimeException("Heap kosong!");
        return heap[0];
    }
    
    // Bangun heap dari array sembarang : O(n)
    public static BinaryMinHeap buildHeap(int[] arr) {
        BinaryMinHeap h = new BinaryMinHeap(arr.length);
        h.heap = Arrays.copyOf(arr, arr.length);
        h.size = arr.length;
        // Mulai dari node non-leaf terakhir
        for (int i = h.size / 2 - 1; i >= 0; i--) {
            h.siftDown(i);
        }
        return h;
    }
    
    // Hapus elemen di indeks i : O(log n)
    public void delete(int i) {
        if (i >= size) throw new IndexOutOfBoundsException();
        decreaseKey(i, Integer.MIN_VALUE);
        extractMin();
    }
    
    // Kurangi nilai kunci di indeks i : O(log n)
    public void decreaseKey(int i, int newVal) {
        if (newVal > heap[i])
            throw new IllegalArgumentException("Nilai baru harus lebih kecil!");
        heap[i] = newVal;
        siftUp(i);
    }

    public int getSize() { return size; }
    public boolean isEmpty() { return size == 0; }

    @Override
    public String toString() {
        return "BinaryMinHeap" + Arrays.toString(Arrays.copyOf(heap, size));
    }

    // ==================== MAIN ====================
    public static void main(String[] args) {
        System.out.println("===== BINARY MIN-HEAP DEMO =====\n");

        BinaryMinHeap heap = new BinaryMinHeap(20);

        // Insert
        int[] values = {15, 10, 8, 25, 3, 18, 6, 30};
        System.out.print("Insert: ");
        for (int v : values) {
            System.out.print(v + " ");
            heap.insert(v);
        }
        System.out.println("\nHeap: " + heap);

        // Peek
        System.out.println("Peek (min): " + heap.peek());

        // Extract Min
        System.out.print("\nExtract Min sequence: ");
        BinaryMinHeap heapCopy = new BinaryMinHeap(20);
        for (int v : values) heapCopy.insert(v);
        while (!heapCopy.isEmpty()) {
            System.out.print(heapCopy.extractMin() + " ");
        }
        System.out.println("← sorted!");

        // Build Heap
        int[] arr = {40, 20, 30, 10, 5, 50, 25};
        BinaryMinHeap built = BinaryMinHeap.buildHeap(arr);
        System.out.println("\nBuild Heap dari " + Arrays.toString(arr));
        System.out.println("Hasil: " + built);
    }
}
