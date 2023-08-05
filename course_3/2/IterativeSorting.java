import java.util.Comparator;

/**
 * Your implementation of various iterative sorting algorithms.
 */
public class IterativeSorting {


    private static <T> void swap(T[] arr, int i, int j) {
        T temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    /**
     * Implement bubble sort.
     *
     * It should be:
     * in-place
     * stable
     * adaptive
     *
     * Have a worst case running time of: O(n^2)
     * And a best case running time of: O(n)
     *
     * NOTE: You should implement bubble sort with the last swap optimization.
     *
     * You may assume that the passed in array and comparator
     * are both valid and will never be null.
     *
     * @param <T>        Data type to sort.
     * @param arr        The array that must be sorted after the method runs.
     * @param comparator The Comparator used to compare the data in arr.
     */
    public static <T> void bubbleSort(T[] arr, Comparator<T> comparator) {
        // WRITE YOUR CODE HERE (DO NOT MODIFY METHOD HEADER)!
        int stopIdx = arr.length - 1;
        while (stopIdx != 0) {
            int i = 0;
            int lastSwapIdx = 0;
            while (i < stopIdx) {
                if (comparator.compare(arr[i], arr[i+1]) > 0) {
                    swap(arr, i, i+1);
                    lastSwapIdx = i;
                }
                i++;
            }
            stopIdx = lastSwapIdx;
        }
    }

    /**
     * Implement selection sort.
     *
     * It should be:
     * in-place
     * unstable
     * not adaptive
     *
     * Have a worst case running time of: O(n^2)
     * And a best case running time of: O(n^2)
     *
     * You may assume that the passed in array and comparator
     * are both valid and will never be null.
     *
     * @param <T>        Data type to sort.
     * @param arr        The array that must be sorted after the method runs.
     * @param comparator The Comparator used to compare the data in arr.
     */
    public static <T> void selectionSort(T[] arr, Comparator<T> comparator) {
        // WRITE YOUR CODE HERE (DO NOT MODIFY METHOD HEADER)!
        for (int stopIdx=arr.length - 1; stopIdx > 0; stopIdx--) {
            int currMaxIdx = 0;
            for (int i=1; i <= stopIdx; i++) {
                if (comparator.compare(arr[i], arr[currMaxIdx]) > 0) {
                    currMaxIdx = i;
                }
            }
            swap(arr, currMaxIdx, stopIdx);
        }
    }

    /**
     * Implement insertion sort.
     *
     * It should be:
     * in-place
     * stable
     * adaptive
     *
     * Have a worst case running time of: O(n^2)
     * And a best case running time of: O(n)
     *
     * You may assume that the passed in array and comparator
     * are both valid and will never be null.
     *
     * @param <T>        Data type to sort.
     * @param arr        The array that must be sorted after the method runs.
     * @param comparator The Comparator used to compare the data in arr.
     */
    public static <T> void insertionSort(T[] arr, Comparator<T> comparator) {
        // WRITE YOUR CODE HERE (DO NOT MODIFY METHOD HEADER)!
        // to start with, the first element is the presorted part
        for (int i = 1; i <= arr.length - 1; i++) {
            int j = i;
            while (j > 0) {
                if (comparator.compare(arr[j], arr[j-1]) < 0) {
                    swap(arr, j, j-1);
                    j--;
                }
                else {
                    break;
                }
            }
        }
    }
}