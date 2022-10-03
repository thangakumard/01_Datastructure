package coreJava.multiThreading.etucatv.thread11_MergeSort;

import org.testng.Assert;
import org.testng.annotations.Test;

public class MultiThreadMergeSort {
    @Test
    public void doMergeSort() {
        int[] input = { 9, 8, 7, 6, 5, 4, 3, 2, 1, 9, 2 };
        int[] output = { 1, 2, 2, 3, 4, 5, 6, 7, 8, 9, 9 };

        long start = System.currentTimeMillis();
        splitAndSort(input, 0, input.length - 1);
        long end = System.currentTimeMillis();
        System.out.println("\n\nTime taken to sort = " + (end - start) + " milliseconds");
        for (int i = 0; i < input.length; i++) {
            System.out.print(input[i] + ",");
        }

        Assert.assertEquals(input, output, "Merge sort fails!!!");
    }

    public void splitAndSort(int[] input, int left, int right) {
        if (left < right) {
            int mid = left + (right- left) / 2;
            // sort first half
            Thread worker1 = new Thread(new Runnable() {

                public void run() {
                    splitAndSort(input, left, mid);
                }
            });

            // sort second half
            Thread worker2 = new Thread(new Runnable() {

                public void run() {
                    splitAndSort(input, mid + 1, right);
                }
            });

            // start the threads
            worker1.start();
            worker2.start();

            try {

                worker1.join();
                worker2.join();
            } catch (InterruptedException ie) {
                // swallow
            }

            mergeAndSort(input, left, mid + 1, right);
        }
    }

    public void mergeAndSort(int[] input, int left, int mid, int right) {
        int array1LeftIndex = left;
        int array1RightIndex = mid - 1;
        int array2LeftIndex = mid;
        int array2RightIndex = right;

        int totalRecords = right - left + 1;

        int tempIndex = left;
        int[] temp = new int[input.length];

        while (array1LeftIndex <= array1RightIndex && array2LeftIndex <= array2RightIndex) {
            if (input[array1LeftIndex] < input[array2LeftIndex]) {
                temp[tempIndex] = input[array1LeftIndex];
                array1LeftIndex++;
            } else {
                temp[tempIndex] = input[array2LeftIndex];
                array2LeftIndex++;
            }
            tempIndex++;
        }

        while (array1LeftIndex <= array1RightIndex) {
            temp[tempIndex] = input[array1LeftIndex];
            array1LeftIndex++;
            tempIndex++;
        }

        while (array2LeftIndex <= array2RightIndex) {
            temp[tempIndex] = input[array2LeftIndex];
            array2LeftIndex++;
            tempIndex++;
        }

        for (int j = right; totalRecords > 0; totalRecords--) {
            input[j] = temp[j];
            j--;
        }

    }
}
