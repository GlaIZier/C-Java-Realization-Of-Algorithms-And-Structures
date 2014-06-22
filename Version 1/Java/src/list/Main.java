package list;

/**
 * Project: MergeSort
 * Date: 12.09.13
 * Time: 10:52
 */
public class Main {
    public static void main(String[] args) {
        LList lList = new LList(1);
        lList.showLList();
        lList.addToTail(5);
        lList.showLList();
        lList.addToTail(212);
        lList.showLList();
        lList.addToTail(45);
        lList.showLList();
        lList.addToTail(88);
        lList.showLList();
        lList.deleteFromTail();
        lList.deleteFromTail();
        lList.showLList();
        return;
    }
}
