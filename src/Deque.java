import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Dequeue
 * -------
 *
 * A double-ended queue or deque (pronounced "deck") is a generalization of
 * a stack and a queue that supports adding and removing items from either
 * the front or the back of the data structure
 *
 * Performance Requirements:
 *  a) Dequeue
 *  - Non-iterator operations (Dequeue public methods - API) should have constant worst-case time, O(1)
 *  - Memory for Dequeue containing N items must use AT MOST 48N + 192 bytes
 *  - Non-iterator memory use linear in current # of items
 *
 *  b) Iterator
 *  - Iterator constructor should have O(1)
 *  - Other iterator operations should have O(1)
 *  - Memory per iterator should be constant
 *
 * Memory analysis:
 * 1. Dequeue:
 *      - overhead      16 bytes
 *      - list ref       8 byte
 *
 * 2. Node:
 *      - overhead      16 bytes
 *      - Item item      8 (assuming max item size of long/double)
 *      - prev           8 (reference)
 *      - next           8 (reference)
 *
 * 3. Sentinel
 *      - overhead       16
 *      - frontSentinel   8 (reference)
 *      - backSentinel    8 (reference)
 *      - int size        4
 */
public class Deque<Item> implements Iterable<Item> {

    private Header list;

    // Helper linked list
    private class Header {
        int size;           // size of Dequeue
        Node frontSentinel; // fast link to head - front marker node
        Node backSentinel;  // fast link to tail - back marker node

        public Header(int size) {
            this.size = size;
        }
    }

    // Helper doubly-linked node
    private class Node {
        Item item;
        Node next;  // backward
        Node prev;  // forward
    }

    // to support for-each loop
    private class DequeIterator implements Iterator<Item> {
        Node current = list.frontSentinel.next;
        public boolean hasNext() { return current != list.backSentinel; }
        public void remove() {
            throw new UnsupportedOperationException("remove() operation is not supported.");
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = current.item;
            current = current.next;
            return item;
        }
    }

    /**
     * Constructor initializes empty
     */
    public Deque() {
        init();
    }

    // helper to initialize list
    private void init() {
        // object allocations
        list = new Header(0);
        list.frontSentinel = new Node();
        list.backSentinel = new Node();

        // empty list
        list.frontSentinel.next = list.backSentinel;
        list.backSentinel.prev = list.frontSentinel;
        list.size = 0;
    }

    // check internal invariants
    private boolean check() {
        if (list.size == 0) {
            if (!(list.frontSentinel.next == list.backSentinel &&
                    list.backSentinel.prev == list.frontSentinel))
                return false;
        }
        return true;
    }
    /**
     * Determine if deque is empty
     *
     * Performance should be O(1).
     * i.e. worst case upper-bound is constant time
     *
     * @return true if empty
     */
    public boolean isEmpty() {
        assert check();
        return list.size == 0;
    }

    /**
     * Return the number of elements of deque
     *
     * Performance should be constant worst-case time, O(1)
     * i.e. worst case upper-bound is constant time
     *
     * @return integer size
     */
    public int size() {
        return list.size;
    }

    // Helper to add node to list
    private void addNode(Node achor, Item item) {
        Node elem = new Node();
        elem.item = item;
        elem.prev = achor.prev;
        elem.next = achor;
        achor.prev.next = elem;
        achor.prev = elem;
        list.size++;
    }

    // Helper to remove node
    private void removeNode(Node achor) {
        achor.prev.next = achor.next;
        achor.next.prev = achor.prev;
        list.size--;
    }

    /**
     * Insert a new element item at the front of the deque
     *
     * Performance should be constant worst-case time, O(1)
     * i.e. worst case upper-bound is constant time
     *
     * @param item
     */
    public void addFirst(Item item) {
        if (item == null) {
            throw new NullPointerException("should not add null item.");
        }
        addNode(list.frontSentinel.next, item);
    }

    /**
     * Insert a new element e at the tail of the deque
     *
     * Performance should be constant worst-case time, O(1)
     * i.e. worst case upper-bound is constant time
     *
     * @param item T
     */
    public void addLast(Item item) {
        if (item == null) {
            throw new NullPointerException("should not add null item.");
        }
        addNode(list.backSentinel, item);
    }

    /**
     * Remove and the return the first element of the deque; a NoSuchElementException
     * is thrown if deque is empty
     *
     * Performance should be constant worst-case time, O(1)
     * i.e. worst case upper-bound is constant time
     *
     * @return item T
     */
    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException("cannot remove from empty Deque.");
        }
        Node first = list.frontSentinel.next;
        removeNode(first);
        return first.item;
    }

    /**
     * Remove and return last element of the deque; a NoSuchElementException is thrown
     * if deque is empty
     *
     * Performance should be constant worst-case time, O(1)
     * i.e. worst case upper-bound is constant time
     *
     * @return item T
     */
    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException("cannot remove from empty Deque.");
        }
        Node last = list.backSentinel.prev;
        removeNode(last);
        return last.item;
    }

    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    public static void main(String[] args) {

        // Test 1 - Deck of integers
        StdOut.println("================================");
        StdOut.println("    Tests for Deque Components");
        StdOut.println("================================");
        Deque<Integer> deckInteger = new Deque<>();
        TestDeque<Integer> deckTestInteger = new TestDeque<>(deckInteger, /* dummy */ new Integer[]{0});

        Integer[] expected;
        Integer[] actual;
        Integer[] input;

        StdOut.println("==> Running Tests for Deque<Integer>\n");

        input = new Integer[]{3, 5, 7};
        expected = new Integer[]{3, 5, 7};
        actual = new Integer[expected.length];
        StdOut.printf("%s : running testAddFirst()\n\n",
                deckTestInteger.testSizeAddFirst(input) ? "SUCCESS" : "FAILED");
        testRun("running testSizeAddFirst",
                deckTestInteger.testSizeAddFirst(input));
        testRun("running testQueueOperations",
                deckTestInteger.testQueueOperations(input, expected, actual));
        testRun("running testAddFirstRemoveLast",
                deckTestInteger.testAddFirstRemoveLast(input, expected, actual));
        testRun("running testAddFirstRemoveLast",
                deckTestInteger.testRandom(input, 7, 5, 10, 10));

        input = new Integer[]{5, 7};
        expected = new Integer[]{7, 5};
        actual = new Integer[expected.length];
        testRun("running testStackOperations",
                deckTestInteger.testStackOperations(input, expected, actual));

        input = new Integer[]{10, 20, 30, 40, 50};
        expected = new Integer[]{10,20,30,40,50};
        actual = new Integer[expected.length];
        testRun("running testIteratorSingle",
                deckTestInteger.testIteratorSingle(input, expected, actual));

        input = new Integer[]{10, 20, 30};
        Integer[] input2 = new Integer[]{1, 2, 3};
        expected = new Integer[]{101, 102, 103, 201, 202, 203, 301, 302, 303};
        actual = new Integer[expected.length];
        testRun("running testIteratorNested",
                deckTestInteger.testIteratorNested(input,input2, expected, actual));

        // Test 2 - Deck of String
        StdOut.println("==> Running Tests for Deque<String>\n");
        Deque<String> deckString = new Deque<>();
        TestDeque<String> deckTestString = new TestDeque<>(deckString, /* dummy */ new String[]{"0"});
        String[] expectedStr;
        String[] actualStr;
        String[] inputStr;

        inputStr = new String[]{"to", "be"};
        expectedStr = new String []{"be", "to"};
        actualStr = new String[expectedStr.length];
        testRun("running testStackOperations",
                deckTestString.testStackOperations(inputStr, expectedStr, actualStr));

        inputStr = new String[]{"to", "be", "or"};
        expectedStr = new String[]{"to", "be", "or"};
        actualStr = new String[expectedStr.length];
        testRun("running testAddFirst",
                deckTestString.testSizeAddFirst(inputStr));
        testRun("running testQueueOperations",
                deckTestString.testQueueOperations(inputStr, expectedStr, actualStr));
        testRun("running testAddFirstRemoveLast",
                deckTestString.testAddFirstRemoveLast(inputStr, expectedStr, actualStr));

        inputStr = new String[]{"a", "b", "c"};
        String[] inputStr2 = new String[]{"1", "2", "3"};
        expectedStr = new String[]{"a1", "a2", "a3", "b1", "b2", "b3", "c1", "c2", "c3"};
        actualStr = new String[expectedStr.length];
        testRun("running testIteratorNested",
                deckTestString.testIteratorNested(inputStr,inputStr2, expectedStr, actualStr));
    }

    private static void testRun(String s, boolean b) {
        StdOut.printf("%s : %s\n\n", b ? "PASSED":"FAILED", s);
    }
}