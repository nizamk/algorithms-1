import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Performance requirements:
 *  Randomized queue implementation must support each randomized queue operation
 *  (besides creating an iterator) in constant amortized time. That is, any sequence
 *  of m randomized queue operations (starting from an empty queue) should take at
 *  most cm steps in the worst case, for some constant c.
 *
 *  A randomized queue containing n items must use at most 48n + 192 bytes of memory.
 *  Additionally, your iterator implementation must support operations next() and hasNext()
 *  in constant worst-case time; and construction in linear time; you may (and will need to)
 *  use a linear amount of extra memory per iterator.
 *
 * @param <Item>
 */
public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] items;
    private int n;

    private class RandomizedQueueIterator implements Iterator<Item> {
        private int i = 0;
        public boolean hasNext() { return i < n; }
        public void remove() { throw new UnsupportedOperationException("remove() operation is not supported."); }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            return items[i++];
        }
    }

    /**
     * construct an empty randomized queue
     */
    public RandomizedQueue() {
        items = (Item[]) new Object[2];
        n = 0;
    }

    /**
     * Is this stack empty?
     * @return true if this stack is empty; false otherwise
     */
    public boolean isEmpty() { return n == 0; }

    // resize the underlying array holding the elements
    private void resize(int capacity) {
        assert capacity >= n;
        items = java.util.Arrays.copyOf(items, capacity);
    }

    /**
     * returns the numbers of items in the queue
     *
     * @return
     */
    public int size() { return n; }

    /**
     * add an item to the queue
     *
     * @param item
     */
    public void enqueue(Item item) {
        if (item == null) throw new NullPointerException("should not add null item.");
        if (n == items.length) resize(2*items.length);  // double size of array if necessary
        items[n++] = item;                              // add item
    }

    /**
     * remove and return a random item
     *
     * @return
     */
    public Item dequeue() {
        if (isEmpty()) throw new NoSuchElementException("cannot remove from empty Deque.");
        int k = StdRandom.uniform(n);
        assert k >= 0 && k <= items.length;
        Item item = items[k];
        items[k] = null; // avoids loitering
        n--;
        rearrangeAt(k);
        // shrink size of array if necessary
        if (n > 0 && n == items.length / 4) resize(items.length / 2);
        return item;
    }

    private void rearrangeAt(int k) {
        for (int i = k; i < n; i++) {
            items[i] = items[i + 1];
            items[i + 1] = null; // avoids loitering
        }
    }

    private void printItems() {
        StdOut.print("[");
        for (int i = 0; i < n; i++) {
            StdOut.print(items[i] + " ");
        }
        StdOut.print("]");
        StdOut.println();
    }

    /**
     * return (but do not remove) a random item
     *
     * @return
     */
    public Item sample() {
        if (isEmpty()) throw new NoSuchElementException("cannot remove from empty Deque.");
        return items[StdRandom.uniform(n)];
    }

    /**
     * returns an independent iterator over items in a random order
     *
     * @return
     */
    public Iterator<Item> iterator() { return new RandomizedQueueIterator(); }

    /**
     * implement unit testing
     *
     * @param args
     */
    public static void main(String[] args) {
        // TODO - unit testings
        RandomizedQueue<Integer> queue = new RandomizedQueue<>();
        TestRandomizedQueue<Integer> intQueueTest = new TestRandomizedQueue<>(queue);

        Integer[] input = new Integer[]{10, 20, 30, 40,50};
        intQueueTest.testRun("running testAddItems",
                intQueueTest.testAddItems(input, input.length));

        intQueueTest.testRun("running testDequeueItems",
                intQueueTest.testDequeueItems(input));

        Integer[] expected;
        Integer[] actual;
        input = new Integer[]{10, 20, 30};
        Integer[] input2 = new Integer[]{1, 2, 3};
        expected = new Integer[]{101, 102, 103, 201, 202, 203, 301, 302, 303};
        actual = new Integer[expected.length];
        intQueueTest.testRun("running testIteratorNested",
                intQueueTest.testNestedIteratorsDiffRandomizedQueues(input, input2, expected, actual));

        input = new Integer[]{10, 20, 30, 40};
        actual = new Integer[expected.length];
        intQueueTest.testRun("running testParallelIteratorsSameRandomizedQueue",
                intQueueTest.testParallelIteratorsSameRandomizedQueues(input));

        input = new Integer[]{10, 20, 30, 40};
        actual = new Integer[expected.length];
        intQueueTest.testRun("running testNestedteratorsSameRandomizedQueues",
                intQueueTest.testNestedteratorsSameRandomizedQueues(input));
    }
}