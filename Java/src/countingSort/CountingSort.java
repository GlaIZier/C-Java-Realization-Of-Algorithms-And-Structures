package countingSort;

import java.util.Random;

/**
 * Project: MergeSort
 * Date: 12.09.13
 * Time: 10:10
 */
public class CountingSort {

    private static final int SIZE = 10;

    public static final int MAX_VALUE = 99;

    private int[] array;

    CountingSort() {
        array = new int[SIZE];
        int r;
        Random random = new Random();
        for (int i = 0; i < SIZE; i++) {
            r = random.nextInt(MAX_VALUE);
            array[i] = r;
        }
    }

    public void show() {
        /*Iterator iterator = array.iterator();
        while (iterator.hasNext()) {
            System.out.print(iterator.next() + " ");
        }*/

        for (int i = 0; i < SIZE; i++) {
            System.out.print(array[i] + " ");
        }
    }

    public void sort() {
        countingSort(MAX_VALUE);
    }

    private void countingSort(int max) {
        int[] b = new int[SIZE];
        int[] c = new int[max + 1];

        // in c number of exactly values
        for (int i = 0; i < SIZE; i++) {
           c[array[i]]++;
        }

        // in c number of values <= c[i]
        for (int i = 1; i < c.length; i++) {
            c[i] = c[i - 1] + c[i];
        }

        for (int j = SIZE - 1; j >= 0 ; j--) {
            b[c[array[j]] - 1] = array[j];
            c[array[j]]--;
        }

        System.arraycopy(b, 0, array, 0, SIZE);
    }
}
