import java.security.AlgorithmConstraints;
import java.util.NoSuchElementException;

/**
 * Your implementation of a MinHeap.
 */
public class MinHeap<T extends Comparable<? super T>> {

    /**
     * The initial capacity of the MinHeap.
     *
     * DO NOT MODIFY THIS VARIABLE!
     */
    public static final int INITIAL_CAPACITY = 13;

     /*
     * Do not add new instance variables or modify existing ones.
     */
    private T[] backingArray;
    private int size;

    /**
     * This is the constructor that constructs a new MinHeap.
     *
     * Recall that Java does not allow for regular generic array creation,
     * so instead we cast a Comparable[] to a T[] to get the generic typing.
     */
    public MinHeap() {
        //DO NOT MODIFY THIS METHOD!
        backingArray = (T[]) new Comparable[INITIAL_CAPACITY];
    }

    private void resize() {
        int newCapacity = backingArray.length * 2;
        T[] newArray = (T[]) new Comparable[newCapacity];
        for (int i=1; i < size + 1; i++){
            newArray[i] = backingArray[i];
        }
        backingArray = newArray;
    }
 
    /**
     * Adds an item to the heap. If the backing array is full (except for
     * index 0) and you're trying to add a new item, then double its capacity.
     *
     * Method should run in amortized O(log n) time.
     *
     * @param data The data to add.
     * @throws java.lang.IllegalArgumentException If the data is null.
     */
    public void add(T data) {
        // WRITE YOUR CODE HERE (DO NOT MODIFY METHOD HEADER)!
        if (data == null) {
            throw new java.lang.IllegalArgumentException();
        }
        if (size + 1 == backingArray.length) {
            resize();
        }
        size++;
        backingArray[size] = data;
        // current index is size, this is the leaf node just added 
        // its parent index is size/2
        rAdd(size);
    }

    private void rAdd(int currIdx) {
        int parentIdx = currIdx/2;
        if (parentIdx == 0) {
            return;
        }
        if (backingArray[currIdx].compareTo(backingArray[parentIdx]) < 0) {
            swap(currIdx, parentIdx);
            rAdd(parentIdx);
        }
        return;
    }

    private void swap(int a, int b) {
        T temp = backingArray[a];
        backingArray[a] = backingArray[b];
        backingArray[b] = temp;
    }
    /**
     * Removes and returns the min item of the heap. As usual for array-backed
     * structures, be sure to null out spots as you remove. Do not decrease the
     * capacity of the backing array.
     *
     * Method should run in O(log n) time.
     *
     * @return The data that was removed.
     * @throws java.util.NoSuchElementException If the heap is empty.
     */
    public T remove() {
        // WRITE YOUR CODE HERE (DO NOT MODIFY METHOD HEADER)!
        if (size == 0) {
            throw new java.util.NoSuchElementException();
        }
        T toReturn = backingArray[1];
        backingArray[1] = backingArray[size];
        backingArray[size] = null;
        size--;
        rRemove(1);
        return toReturn;
    }

    private void rRemove(int currIdx) {
        int leftChild = currIdx * 2;
        int rightChild = currIdx * 2 + 1;
        if (leftChild > size) {
            return;
        }
        if (rightChild > size) {
            if (backingArray[currIdx].compareTo(backingArray[leftChild]) > 0) {
                swap(currIdx, leftChild);
                rRemove(leftChild);
            }
        }
        else {
            int smallerIdx = getSmaller(leftChild, rightChild);
            if (backingArray[currIdx].compareTo(backingArray[smallerIdx]) > 0) {
                swap(currIdx, smallerIdx);
                rRemove(smallerIdx);
            }
            return;
        }
    }

    private int getSmaller(int a, int b) {
        if (backingArray[a].compareTo(backingArray[b]) < 0) {
            return a;
        }
        else {
            return b;
        }
    }
    /**
     * Returns the backing array of the heap.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return The backing array of the list
     */
    public T[] getBackingArray() {
        // DO NOT MODIFY THIS METHOD!
        return backingArray;
    }

    /**
     * Returns the size of the heap.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return The size of the list
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }
}