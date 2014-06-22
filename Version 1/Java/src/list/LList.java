package list;

/**
 * Project: MergeSort
 * Date: 12.09.13
 * Time: 10:52
 */
public class LList {

    private Element head;

    private Element tail;

    public LList(int firstElementKey) {
        head = new Element(firstElementKey);
        tail = head;
        head.next = null;
        head.prev = null;
    }

    public void addToTail(int key) {
        Element elementTail = new Element(key);
        Element elementCurr = tail;
        tail.next = elementTail;
        elementTail.prev = elementCurr;
        tail = elementTail;
        elementCurr = null;
        elementTail = null;

    }

    public void showLList() {
        Element elementCurr = head;
        while (elementCurr!= null) {
            System.out.print(elementCurr.key + " ");
            elementCurr = elementCurr.next;
        }
        System.out.println();
    }

    public void deleteFromTail() {
        Element elementCurr = tail;
        tail = elementCurr.prev;
        elementCurr.prev = null;
        tail.next = null;
        elementCurr.next = null; //  just in case
        elementCurr = null; // let GC do it's job
    }

    private class Element {

        int key;

        Element next;

        Element prev;

        public Element(int key) {
            this.key = key;
        }

        public Element getNext() {
            return next;
        }

        public Element getPrev() {
            return prev;
        }

        public int getKey() {
            return key;
        }

        public void setKey(int key) {
            this.key = key;
        }

        public void setNext(Element next) {
            this.next = next;
        }

        public void setPrev(Element prev) {
            this.prev = prev;
        }
    }
}
