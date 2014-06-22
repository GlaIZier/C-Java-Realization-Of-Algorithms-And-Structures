package hashTable;

/**
 * Project: MergeSort
 * Date: 12.09.13
 * Time: 12:13
 */
public class HashTable {

    private final int arrayMax = 5;

    private LinkList[] array;

    public HashTable() {
        array = new LinkList[arrayMax];
    }

    public void add(int key) {
        int hash = key % arrayMax;
        if (array[hash] == null) {
            array[hash] = new LinkList(key, true);
        }
        else {
            array[hash].addToList(key);
        }
    }

    public void printLine(int index) {
        if ((index >= 0 ) && (index < arrayMax)) {
            if (array[index] != null) {
                array[index].showLinkList();
            }
            else {
                System.out.println("Line is empty!");
            }
        }
    }

    public int getArrayMax() {
        return arrayMax;
    }

    private class LinkList {

        private LinkList head = null;

        private int key;

        private LinkList next;

        private LinkList(int key, boolean first) {
            if (first) {
                head = this;
            }
            this.key = key;
        }

        private void addToList(int key) {
            LinkList linkListTail = new LinkList(key, false);
            LinkList lListCurr = this;
            while (lListCurr.next != null) {
                lListCurr = lListCurr.next;
            }
            lListCurr.next = linkListTail;
        }

        private void showLinkList() {
            LinkList linkListCurr = this;
            while (linkListCurr!= null) {
                System.out.print(linkListCurr.key + " ");
                linkListCurr = linkListCurr.next;
            }
            System.out.println();
        }

    }
}
