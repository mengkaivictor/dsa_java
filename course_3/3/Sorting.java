import java.util.Comparator;
import java.util.List;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Your implementation of various divide & conquer sorting algorithms.
 */

public class Sorting {

    /**
     * Implement merge sort.
     *
     * It should be:
     * out-of-place
     * stable
     * not adaptive
     *
     * Have a worst case running time of: O(n log n)
     * And a best case running time of: O(n log n)
     *
     * You can create more arrays to run merge sort, but at the end, everything
     * should be merged back into the original T[] which was passed in.
     *
     * When splitting the array, if there is an odd number of elements, put the
     * extra data on the right side.
     *
     * Hint: You may need to create a helper method that merges two arrays
     * back into the original T[] array. If two data are equal when merging,
     * think about which subarray you should pull from first.
     *
     * You may assume that the passed in array and comparator are both valid
     * and will not be null.
     *
     * @param <T>        Data type to sort.
     * @param arr        The array to be sorted.
     * @param comparator The Comparator used to compare the data in arr.
     */
    public static <T> void mergeSort(T[] arr, Comparator<T> comparator) {
        // WRITE YOUR CODE HERE (DO NOT MODIFY METHOD HEADER)!
        // split arr to left and right and sort each part
        if (arr.length < 2) {
            return;
        }
        int length = arr.length;
        int mid = length/2;
        T[] left = (T[]) new Object[mid];
        for (int i=0; i<left.length; i++) {
            left[i] = arr[i];  
        }
        T[] right = (T[]) new Object[length-mid];
        for (int i=0; i<right.length; i++) {
            right[i] = arr[mid+i];
        }
        mergeSort(left, comparator);
        mergeSort(right, comparator);
        // merge sorted left and right part
        int i=0;
        int j=0;
        while (i<left.length && j<right.length) {
            if (comparator.compare(left[i], right[j])<=0) {
                arr[i+j] = left[i];
                i++;
            }
            else {
                arr[i+j] = right[j];
                j++;
            }
        }
        // add unadded portion back into array
        while (i<left.length) {
            arr[i+j] = left[i];
            i++;
        }
        while (j<right.length) {
            arr[i+j] = right[j];
            j++;
        }
    }   

    /**
     * Implement LSD (least significant digit) radix sort.
     *
     * It should be:
     * out-of-place
     * stable
     * not adaptive
     *
     * Have a worst case running time of: O(kn)
     * And a best case running time of: O(kn)
     *
     * Feel free to make an initial O(n) passthrough of the array to
     * determine k, the number of iterations you need.
     *
     * At no point should you find yourself needing a way to exponentiate a
     * number; any such method would be non-O(1). Think about how how you can
     * get each power of BASE naturally and efficiently as the algorithm
     * progresses through each digit.
     *
     * You may use an ArrayList or LinkedList if you wish, but it should only
     * be used inside radix sort and any radix sort helpers. Do NOT use these
     * classes with merge sort. However, be sure the List implementation you
     * choose allows for stability while being as efficient as possible.
     *
     * Do NOT use anything from the Math class except Math.abs().
     *
     * You may assume that the passed in array is valid and will not be null.
     *
     * @param arr The array to be sorted.
     */
    
    private static int findK(int[] arr) {
        int k=0;
        for (int num:arr) {
            int cur_k = 1;
            int residual = num/10;
            while (residual!=0) {
                cur_k++;
                residual = residual/10;
            }
            if (cur_k > k) {
                k = cur_k;
            }
        }
        return k;
    }

    private static int findCurDigit(int num, int divisor) {
        int digit = (num/divisor)%10;
        return digit;
    }

    public static void lsdRadixSort(int[] arr) {
        // WRITE YOUR CODE HERE (DO NOT MODIFY METHOD HEADER)!
        LinkedList<Integer>[] buckets = new LinkedList[19];
        for (int i=0; i<buckets.length; i++) {
            buckets[i] = new LinkedList<>();
        }
        int k = findK(arr);
        int divisor = 1;
        for (int iteration=1; iteration<k+1; iteration++) {
            for (int i=0; i<arr.length; i++) {
                int digit = findCurDigit(arr[i], divisor);
                buckets[digit+9].add(arr[i]);
            }
            int idx=0;
            for (LinkedList<Integer> bucket: buckets) {
                while (!bucket.isEmpty()) {
                    arr[idx] = bucket.poll();
                    idx++;
                }
            }
            divisor *= 10;
        }
    }
}