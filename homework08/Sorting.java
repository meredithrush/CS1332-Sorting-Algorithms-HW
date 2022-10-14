import java.util.Comparator;
import java.util.Random;
import java.util.List;
import java.util.LinkedList;
import java.util.PriorityQueue;

/**
 * Your implementation of various sorting algorithms.
 *
 * @author Meredith Rush
 * @version 1.0
 * @userid mrush30
 * @GTID 903574798
 *
 * Collaborators: I did not collaborate with anyone else.
 *
 * Resources: I consulted the online video about heap sorts.
 */
public class Sorting {

    /**
     * Implement selection sort.
     *
     * It should be:
     * in-place
     * unstable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(n^2)
     *
     * And a best case running time of:
     * O(n^2)
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void selectionSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("Null parameters cannot be used in selection sort.");
        }
        for (int i = arr.length - 1; i >= 1; i--) {
            int maxIndex = i;
            for (int j = 0; j < i; j++) {
                if (comparator.compare(arr[j], arr[maxIndex]) > 0) {
                    maxIndex = j;
                }
            }
            swapDataT(i, maxIndex, arr);
        }
    }

    /**
     * Implement cocktail sort.
     *
     * It should be:
     * in-place
     * stable
     * adaptive
     *
     * Have a worst case running time of:
     * O(n^2)
     *
     * And a best case running time of:
     * O(n)
     *
     * NOTE: See pdf for last swapped optimization for cocktail sort. You
     * MUST implement cocktail sort with this optimization
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void cocktailSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("Null parameters cannot be used in cocktail sort.");
        }
        boolean swapsMade = true;
        int startIndex = 0;
        int endIndex = arr.length - 1;
        int lastSwapped = -1;
        while (swapsMade) {
            swapsMade = false;
            if (lastSwapped != -1) {
                startIndex = lastSwapped;
            }
            for (int i = startIndex; i < endIndex; i++) {
                if (comparator.compare(arr[i], arr[i + 1]) > 0) {
                    swapDataT(i, i + 1, arr);
                    swapsMade = true;
                    lastSwapped = i;
                }
            }
            if (swapsMade) {
                endIndex--;
                swapsMade = false;
                for (int i = lastSwapped; i > startIndex; i--) {
                    if (comparator.compare(arr[i - 1], arr[i]) > 0) {
                        swapDataT(i, i - 1, arr);
                        swapsMade = true;
                        lastSwapped = i;
                    }
                }
                startIndex++;
            }
        }
    }

    /**
     * Implement merge sort.
     *
     * It should be:
     * out-of-place
     * stable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(n log n)
     *
     * And a best case running time of:
     * O(n log n)
     *
     * You can create more arrays to run merge sort, but at the end, everything
     * should be merged back into the original T[] which was passed in.
     *
     * When splitting the array, if there is an odd number of elements, put the
     * extra data on the right side.
     *
     * Hint: If two data are equal when merging, think about which subarray
     * you should pull from first
     *
     * @param <T>        data type to sort
     * @param arr        the array to be sorted
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void mergeSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("Null parameters cannot be used in merge sort.");
        }
        if (arr.length > 1) {
            int midIndex = arr.length / 2;
            T[] left = (T[]) new Object[midIndex];
            T[] right = (T[]) new Object[arr.length - left.length];
            for (int i = 0; i < left.length; i++) {
                left[i] = arr[i];
            }
            for (int i = 0; i < right.length; i++) {
                right[i] = arr[i + left.length];
            }
            mergeSort(left, comparator);
            mergeSort(right, comparator);
            mergeSortHelper(arr, comparator, left, right);
        }
    }

    /**
     * Merge Sort Helper that merges the two arrays
     * @param arr Array being sorted
     * @param comparator Comparator Object used to compare variables
     * @param left Left subarray being sorted
     * @param right Right subarray being sorted
     * @param <T> Data type being sorted
     */
    private static <T> void mergeSortHelper(T[] arr, Comparator<T> comparator, T[] left, T[] right) {
        int i = 0, j = 0;
        while (i + j < arr.length) {
            if (j == right.length || (i < left.length && comparator.compare(left[i], right[j]) <= 0)) {
                arr[i + j] = left[i];
                i++;
            } else {
                arr[i + j] = right[j];
                j++;
            }
        }
    }

    /**
     * Implement quick sort.
     *
     * Use the provided random object to select your pivots. For example if you
     * need a pivot between a (inclusive) and b (exclusive) where b > a, use
     * the following code:
     *
     * int pivotIndex = rand.nextInt(b - a) + a;
     *
     * If your recursion uses an inclusive b instead of an exclusive one,
     * the formula changes by adding 1 to the nextInt() call:
     *
     * int pivotIndex = rand.nextInt(b - a + 1) + a;
     *
     * It should be:
     * in-place
     * unstable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(n^2)
     *
     * And a best case running time of:
     * O(n log n)
     *
     * Make sure you code the algorithm as you have been taught it in class.
     * There are several versions of this algorithm and you may not receive
     * credit if you do not use the one we have taught you!
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @param rand       the Random object used to select pivots
     * @throws java.lang.IllegalArgumentException if the array or comparator or
     *                                            rand is null
     */
    public static <T> void quickSort(T[] arr, Comparator<T> comparator,
                                     Random rand) {
        if (arr == null || comparator == null || rand == null) {
            throw new IllegalArgumentException("Null parameters cannot be used in quick sort.");
        }
        quickSortHelper(arr, 0, arr.length - 1, rand, comparator);
    }

    /**
     * Private helper method for quick sort
     *
     * @param arr the array being sorted
     * @param start the start of the section in the array
     * @param end the end of the section in the array
     * @param rand Random class variable for finding a random integer
     * @param comparator Comparator class variable for comparing variables
     * @param <T> the data type to sort
     */
    private static <T> void quickSortHelper(T[] arr, int start, int end, Random rand, Comparator<T> comparator) {
        if (start < end) {
            int pivotIndex = rand.nextInt(end + 1 - start) + start;
            T pivot = arr[pivotIndex];
            arr[pivotIndex] = arr[end];
            arr[end] = pivot;
            int i = start;
            int j = end - 1;
            while (i <= j) {
                while (i <= j && comparator.compare(arr[i],pivot) <= 0) {
                    i++;
                }
                while (i <= j && comparator.compare(arr[j], pivot) >= 0) {
                    j--;
                }
                if (i <= j) {
                    swapDataT(i, j, arr);
                    i++;
                    j--;
                }
            }
            T temp2 = arr[i];
            arr[i] = pivot;
            arr[end] = temp2;
            quickSortHelper(arr, start, i - 1, rand, comparator);
            quickSortHelper(arr, i + 1, end, rand, comparator);
        }
    }

    /**
     * Implement LSD (least significant digit) radix sort.
     *
     * Make sure you code the algorithm as you have been taught it in class.
     * There are several versions of this algorithm and you may not get full
     * credit if you do not implement the one we have taught you!
     *
     * Remember you CANNOT convert the ints to strings at any point in your
     * code! Doing so may result in a 0 for the implementation.
     *
     * It should be:
     * out-of-place
     * stable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(kn)
     *
     * And a best case running time of:
     * O(kn)
     *
     * You are allowed to make an initial O(n) passthrough of the array to
     * determine the number of iterations you need. The number of iterations
     * can be determined using the number with the largest magnitude.
     *
     * At no point should you find yourself needing a way to exponentiate a
     * number; any such method would be non-O(1). Think about how how you can
     * get each power of BASE naturally and efficiently as the algorithm
     * progresses through each digit.
     *
     * Refer to the PDF for more information on LSD Radix Sort.
     *
     * You may use ArrayList or LinkedList if you wish, but it may only be
     * used inside radix sort and any radix sort helpers. Do NOT use these
     * classes with other sorts. However, be sure the List implementation you
     * choose allows for stability while being as efficient as possible.
     *
     * Do NOT use anything from the Math class except Math.abs().
     *
     * @param arr the array to be sorted
     * @throws java.lang.IllegalArgumentException if the array is null
     */
    public static void lsdRadixSort(int[] arr) {
        if (arr == null) {
            throw new IllegalArgumentException("A null array cannot be sorted.");
        }
        LinkedList<Integer>[] buckets = new LinkedList[19];
        for (int i = 0; i < buckets.length; i++) {
            buckets[i] = new LinkedList<Integer>();
        }
        boolean done = false;
        int div = 1;
        while (!done) {
            done = true;
            for (int num : arr) {
                int bucket = num / div;
                if (bucket / 10 != 0) {
                    done = false;
                }
                if (buckets[bucket % 10 + 9] == null) {
                    buckets[bucket % 10 + 9] = new LinkedList<Integer>();
                }
                buckets[bucket % 10 + 9].add(num);
            }
            int arrIndex = 0;
            for (LinkedList<Integer> i : buckets) {
                if (i != null) {
                    for (int j : i) {
                        arr[arrIndex++] = j;
                    }
                    i.clear();
                }
            }
            div *= 10;
        }
    }

    /**
     * Implement heap sort.
     *
     * It should be:
     * out-of-place
     * unstable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(n log n)
     *
     * And a best case running time of:
     * O(n log n)
     *
     * Use java.util.PriorityQueue as the heap. Note that in this
     * PriorityQueue implementation, elements are removed from smallest
     * element to largest element.
     *
     * Initialize the PriorityQueue using its build heap constructor (look at
     * the different constructors of java.util.PriorityQueue).
     *
     * Return an int array with a capacity equal to the size of the list. The
     * returned array should have the elements in the list in sorted order.
     *
     * @param data the data to sort
     * @return the array with length equal to the size of the input list that
     * holds the elements from the list is sorted order
     * @throws java.lang.IllegalArgumentException if the data is null
     */
    public static int[] heapSort(List<Integer> data) {
        if (data == null) {
            throw new IllegalArgumentException("Null data cannot be sorted.");
        }
        PriorityQueue<Integer> heap = new PriorityQueue<Integer>();
        int[] list = new int[data.size()];
        heap.addAll(data);
        for (int i = 0; i < list.length; i++) {
            list[i] = heap.remove();
        }
        return list;
    }

    /**
     * Private helper method that swaps two indices in an array with data type T
     *
     * @param i First index to be swapped
     * @param j Second index to be swapper
     * @param arr Array with data type T
     * @param <T> Method sorting data type T
     */
    private static <T> void swapDataT(int i, int j, T[] arr) {
        T data = arr[i];
        arr[i] = arr[j];
        arr[j] = data;
    }
}