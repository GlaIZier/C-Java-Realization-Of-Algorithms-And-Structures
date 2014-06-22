package hashTable;

/**
 * Project: MergeSort
 * Date: 12.09.13
 * Time: 12:13
 */
public class Main {
    public static void main(String[] args) {
        HashTable hashTable = new HashTable();
        for (int i = 0; i < 9; i++) {
           hashTable.add((int) (Math.random() * 100));
        }
        for (int i = 0; i < hashTable.getArrayMax(); i++) {
            hashTable.printLine(i);
        }
        return;
    }

}
