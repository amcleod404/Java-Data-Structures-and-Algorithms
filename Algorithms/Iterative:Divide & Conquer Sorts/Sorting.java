import java.util.Comparator;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.List;
import java.util.Random;

/**
 * My implementation of various sorting algorithms.
 *
 * @author Ian Andrew McLeod
 * @version 1.0
 */
public class Sorting {

    /**
     * Implementation of insertion sort.
     *
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void insertionSort(T[] arr, Comparator<T> comparator) {

        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("Please enter an existing array and comparator.");
        }

        for (int n = 1; n < arr.length; n++) {
            int i = n;
            while (i > 0 && comparator.compare(arr[i], arr[i - 1]) < 0) {
                T element = arr[i];
                arr[i] = arr[i - 1];
                arr[i - 1] = element;
                i--;
            }

        }

    }

    /**
     * Implementation of bubble sort.
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void bubbleSort(T[] arr, Comparator<T> comparator) {

        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("Please enter an existing array and comparator.");
        }

        int stopIndex = arr.length - 1;
        boolean swapsMade = true;
        while (stopIndex != 0 && swapsMade) {
            swapsMade = false;
            int i = 0;
            int lastSwapped = 0;
            while (i < stopIndex) {
                if (comparator.compare(arr[i], arr[i + 1]) > 0) {
                    T element = arr[i];
                    arr[i] = arr[i + 1];
                    arr[i + 1] = element;
                    swapsMade = true;
                    lastSwapped = i;
                }
                i++;
            }
            stopIndex = lastSwapped;
        }

    }

    /**
     * Implementation of merge sort.
     *
     * @param <T>        data type to sort
     * @param arr        the array to be sorted
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void mergeSort(T[] arr, Comparator<T> comparator) {

        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("Please enter an existing array and comparator.");
        }

        merge(arr, comparator);

    }

    /**
     * Helper method for merge sort
     *
     * @param <T> data type of sort
     * @param arr the array being modified
     * @param comparator object used to compare elements in the array
     */

    private static <T> void merge(T[] arr, Comparator<T> comparator) {
        if (arr.length == 1) {
            return;
        }
        int length = arr.length;
        int midIndex = length / 2;
        T[] leftArray = (T[]) new Object[midIndex];
        T[] rightArray = (T[]) new Object[length - midIndex];
        for (int i = 0; i < midIndex; i++) {
            leftArray[i] = arr[i];
        }
        int count = 0;
        for (int i = midIndex; i < arr.length; i++) {
            rightArray[count] =  arr[i];
            count++;
        }

        merge(leftArray, comparator);
        merge(rightArray, comparator);
        int i = 0;
        int j = 0;
        int currIndex = 0;
        while ((i < midIndex) && (j < (length - midIndex))) {
            if (comparator.compare(leftArray[i], rightArray[j]) <= 0) {
                arr[currIndex] = leftArray[i];
                i++;
            } else {
                arr[currIndex] = rightArray[j];
                j++;
            }
            currIndex++;
        }

        while (i < midIndex) {
            arr[currIndex] = leftArray[i];
            i++;
            currIndex++;
        }
        while (j < (length - midIndex)) {
            arr[currIndex] = rightArray[j];
            j++;
            currIndex++;
        }

    }


    /**
     * Implementation of LSD (least significant digit) radix sort.
     *
     * @param arr the array to be sorted
     * @throws java.lang.IllegalArgumentException if the array is null
     */
    public static void lsdRadixSort(int[] arr) {

        if (arr == null) {
            throw new IllegalArgumentException("Please enter an existing array");
        }
        // create buckets

        List<LinkedList<Integer>> buckets = new ArrayList<>(19);
        for (int i = 0; i < 19; i++) {
            buckets.add(new LinkedList<>());
        }

        // find k

        int largeNum = 0;
        for (int num: arr) {
            if (Math.abs(num) > largeNum) {
                largeNum = Math.abs(num);
            }
        }
        int k = 0;
        while (largeNum != 0) {
            largeNum = largeNum / 10;
            k++;
        }

        // add to appropriate buckets

        int length = arr.length;
        int base = 1;
        for (int i = 0; i < k; i++) {
            for (int j = 0; j < length; j++) {
                int bucket = (arr[j] % (10 * base)) / base;
                buckets.get(bucket + 9).add(arr[j]);
            }
            base = base * 10;

            // resort array

            int index = 0;
            for (LinkedList<Integer> bucket : buckets) {
                while (!bucket.isEmpty()) {
                    arr[index] = bucket.remove();
                    index++;
                }
            }
        }
    }

    /**
     * Implementation of heap sort.
     *
     * @param data the data to sort
     * @return the array with length equal to the size of the input list that
     * holds the elements from the list is sorted order
     * @throws java.lang.IllegalArgumentException if the data is null
     */

    public static int[] heapSort(List<Integer> data) {

        if (data == null) {
            throw new IllegalArgumentException("The data you entered is null. Please enter existing data.");
        }

        PriorityQueue<Integer> queue = new PriorityQueue<>(data);
        int[] arr = new int[data.size()];
        int count = 0;
        while (!queue.isEmpty()) {
            arr[count] = queue.poll();
            count++;
        }
        return arr;
    }

    /**
     * Implementation of kth select algorithm.
     *
     *
     * @param <T>        data type to sort
     * @param k          the index to retrieve data from + 1 (due to
     *                   0-indexing) if the array was sorted; the 'k' in "kth
     *                   select"; e.g. if k == 1, return the smallest element
     *                   in the array
     * @param arr        the array that should be modified after the method
     *                   is finished executing as needed
     * @param comparator the Comparator used to compare the data in arr
     * @param rand       the Random object used to select pivots
     * @return the kth smallest element
     * @throws java.lang.IllegalArgumentException if the array or comparator
     *                                            or rand is null or k is not
     *                                            in the range of 1 to arr
     *                                            .length
     */
    public static <T> T kthSelect(int k, T[] arr, Comparator<T> comparator,
                                  Random rand) {

        if (arr == null || comparator == null || rand == null || k < 1 || k > arr.length) {
            throw new IllegalArgumentException("Please check to make sure your array is not null,"
                    + " the k value is valid, and the random object exists");
        }

        return kHelper(k, arr, comparator, rand, 0, arr.length - 1);

    }

    /**
     * Helper method for kth Select
     *
     * @param <T> data type of sort
     * @param k the kth smallest element in the array
     * @param arr the array being modified
     * @param comparator object used to compare elements in the array
     * @param rand a random number generator
     * @param left the left most index of the search
     * @param right the right most index of the search
     * @return the kth smallest element in the array
     */

    private static <T> T kHelper(int k, T[] arr, Comparator<T> comparator,
                                       Random rand, int left, int right) {

        int pivotIndex = rand.nextInt(right - left + 1) + left;
        T pivot = arr[pivotIndex];
        arr[pivotIndex] = arr[left];
        arr[left] = pivot;
        int leftIndex = left + 1;
        int rightIndex = right;
        while (leftIndex <= rightIndex) {
            while (leftIndex <= rightIndex && comparator.compare(arr[leftIndex], pivot) <= 0) {
                leftIndex++;
            }
            while (leftIndex <= rightIndex && comparator.compare(arr[rightIndex], pivot) >= 0) {
                rightIndex--;
            }
            if (leftIndex <= rightIndex) {
                T temp = arr[leftIndex];
                arr[leftIndex] = arr[rightIndex];
                arr[rightIndex] = temp;
                leftIndex++;
                rightIndex--;
            }

        }
        T temp = arr[rightIndex];
        arr[rightIndex] = arr[left];
        arr[left] = temp;
        if (rightIndex == k - 1) {
            return arr[rightIndex];
        } else if (rightIndex > k - 1) {
            return kHelper(k, arr, comparator, rand, left, rightIndex - 1);
        } else {
            return kHelper(k, arr, comparator, rand, rightIndex + 1, right);
        }
    }
}
