import java.util.Iterator;
import java.util.NoSuchElementException;

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
     * @return
     */
    public Iterator<Item> iterator() {
        // TODO - iterator()
        return null;
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