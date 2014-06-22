package quickSort;

import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: Mike
 * Date: 12.09.13
 * Time: 8:26
 * To change this template use File | Settings | File Templates.
 */
public class QuickSort {

    private static final int SIZE = 10;

    private int[] array;

    QuickSort() {
        array = new int[SIZE];
        int r;
        Random random = new Random();
        for (int i = 0; i < SIZE; i++) {
            r = random.nextInt(100) + 1;
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
        quickSort(0, SIZE - 1);
    }


    private void quickSort(int p, int r) {
        int q;
        if (p < r) {
            q = partition(p, r);
            quickSort(p, q);
            quickSort(q + 1, r);
        }

    }

    private int partition(int p, int r) {
        int x, i, j, temp;
        x = array[p];      // можно x = Math.random между p и r
        i = p - 1;
        j = r + 1;
        while (true) {
            do {
                i++;
            } while (array[i] < x && i <= j);
            do {
                j--;
            } while (array[j] > x && i <= j );
            if (i < j) {
                temp = array[i];
                array[i] = array[j];
                array[j] = temp;
            }
            else return j;
        }
    }
}
