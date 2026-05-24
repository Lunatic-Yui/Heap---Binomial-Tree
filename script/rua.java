/* 
    For the references: geekforgeeks (https://www.geeksforgeeks.org/dsa/binary-heap/)
    Another references: https://www.andrew.cmu.edu/course/15-121/lectures/Binary%20Heaps/heaps.html
*/

import java.util.*;

class min_heap{
    private int[] heapArray;
    private int capacity;
    private int size;

    public min_heap(int n) {
        capacity = n;
        heapArray = new int[capacity];
        size = 0;
    }

    // swapping position from a -> b
    private void swap(int[] arr, int a, int b) {
        int temp = arr[a];
        arr[a] = arr[b];
        arr[b] = temp;
    }

    //setting up for parent and left and right position;
    private int parent(int key) { return (key - 1) / 2; }
    private int left(int key) { return 2 * key + 1; }
    private int right(int key) { return 2 * key + 2; }

    private void min_heapify(int key) {
        int l = left(key);
        int r = right(key);

        int small = key;

        if (l < size && heapArray[l] < heapArray[small]) small = l; 
        if ( r < size && heapArray[r] < heapArray[small]) small = r;

        if(small != key) {
            swap(heapArray, key, small);
            min_heapify(small);
        }
    }

    public int getMin() { return heapArray[0]; } 

    public boolean insert_key(int key) {
        if (size == capacity) return false;

        int i = size;
        heapArray[i] = key;
        size++;

        while (i != 0 && heapArray[i] < heapArray[parent(i)]) {
            swap(heapArray, i, parent(i));
            i = parent(i);
        }
        return true;
    }

    public void increase(int key, int new_val) {
        heapArray[key] = new_val;
        min_heapify(key);
    }
    public void decrease(int key, int new_val) {
        heapArray[key] = new_val;
        while(key != 0 && heapArray[key] < heapArray[parent(key)]) {
            swap(heapArray, key, parent(key));
            key = parent(key);
        }
    }

    public void delete(int key) {
        decrease(key, Integer.MIN_VALUE);
        extractMin();
    }

    public int extractMin() {
        if (size <= 0) return Integer.MAX_VALUE;

        if( size == 1) {
            size--;
            return getMin();
        }

        int root = heapArray[0];
        heapArray[0] = heapArray[size - 1];
        size--;
        min_heapify(0);

        return root;
    }

    public void change_val(int key, int new_val) {
        if (heapArray[key] == new_val) return;
        if (heapArray[key] < new_val) {
            increase(key, new_val);
        } else {
            decrease(key, new_val);
        }
    }

}