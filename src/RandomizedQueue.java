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

        public boolean hasNext() {
            // TODO - RandomizedQueueIterator.hasNext()
            return false;
        }
        public void remove() { throw new UnsupportedOperationException("remove() operation is not supported."); }

        public Item next() {
            // TODO - RandomizedQueueIterator.next()
            if (!hasNext()) throw new NoSuchElementException();
            return items[0];
        }
    }

    /**
     * construct an empty randomized queue
     */
    public RandomizedQueue() {
        // TODO - RandomizedQueue constructor
    }

    /**
     * is randomized queue empty?
     *
     * @return
     */
    public boolean isEmpty() {
        // TODO - isEmpty()
        return false;
    }

    /**
     * returns the numbers of items in the queue
     *
     * @return
     */
    public int size() {
        // TODO - size()
        return 1;
    }

    /**
     * add an item to the queue
     *
     * @param item
     */
    public void enqueue(Item item) {
        // TODO - enqueue()
        if (item == null) throw new NullPointerException("should not add null item.");
    }

    /**
     * remove and return a random item
     * @return
     */
    public Item dequeue() {
        // TODO - dequeue
        if (isEmpty()) throw new NoSuchElementException("cannot remove from empty Deque.");
        return items[0];
    }

    /**
     * return (but do not remove) a random item
     *
     * @return
     */
    public Item sample() {
        // TODO - sample
        if (isEmpty()) throw new NoSuchElementException("cannot remove from empty Deque.");
        return items[0];
    }

    /**
     * returns an independent iterator over items in a random order
     *
     * @return
     */
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    /**
     * implement unit testing
     *
     * @param args
     */
    public static void main(String[] args) {
        // TODO - unit testings
    }
}