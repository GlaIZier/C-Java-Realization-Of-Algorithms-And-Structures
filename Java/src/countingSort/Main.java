package countingSort;

/**
 * Project: MergeSort
 * Date: 12.09.13
 * Time: 10:10
 */
public class Main {
    public static void main(String[] args) {
        CountingSort countingSort = new CountingSort();
        countingSort.show();
        System.out.println();
        countingSort.sort();
        countingSort.show();
        return;
    }
}
