package binarySearchTree;

/**
 * Project: MergeSort
 * Date: 12.09.13
 * Time: 11:33
 */
public class Main {
    public static void main(String[] args) {
        BinarySearchTree tree = new BinarySearchTree(15);
        tree.add(6);
        tree.add(18);
        tree.add(3);
        tree.add(7);
        tree.add(17);
        tree.add(19);
        tree.add(20);
        tree.add(21);
        tree.add(22);
        tree.add(20);
        tree.printMax();
        tree.printIncreasingSequenceOfTree();
        tree.printMaxDepth();
        return;
    }
}
