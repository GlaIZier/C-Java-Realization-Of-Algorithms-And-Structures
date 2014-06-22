package binarySearchTree;

import java.util.Collection;
import java.util.Iterator;
import java.util.Queue;

/**
 * Project: MergeSort
 * Date: 12.09.13
 * Time: 11:33
 */
public class BinarySearchTree {

    private Element root = null;

    private int maxDepth = 0;

    BinarySearchTree(int key) {
        root = new Element(key);
    }

    public void add(int key) {
        maxDepth = 0;
        Element newNode = new Element(key);
        Element current = root;
        Element prevCurrent = null;

        while (current != null) {
            prevCurrent = current;
            if (key <= current.key) {
                current = current.left;
            }
            else {
                current = current.right;
            }
        }
        current = prevCurrent;
        if (key <= current.key) {
            current.left = newNode;
            newNode.parent = current;
        }
        else {
            current.right = newNode;
            newNode.parent = current;
        }

    }

    public void printMax() {
        Element current = root;
        while (current.right != null) {
            current = current.right;
        }
        System.out.println("Max key = " + current.key);
    }

    public void printIncreasingSequenceOfTree() {
        System.out.print("Increasing sequence is ");
        comeRoundTree(root);
        System.out.println();
    }

    private void comeRoundTree(Element current) {
        if (current != null) {
            comeRoundTree(current.left);
            System.out.print(current.key + " ");
            comeRoundTree(current.right);
        }
    }

    public void printMaxDepth() {
        maxDepth = 0;
        getMaxDepth(root, 0);
        System.out.println("Max depth of tree is " + maxDepth);
    }

    private void getMaxDepth(Element current, int depth) {
        if ( (maxDepth < depth) && (current != null) ) {
            maxDepth = depth;
        }
        if (current != null) {
            getMaxDepth(current.left, depth + 1);
            getMaxDepth(current.right, depth + 1);
        }
    }

    private class Element {

        private int key;

        private Element left = null;

        private Element right = null;

        private Element parent = null;

        public Element(int key) {
            this.key = key;
        }

    }
}
