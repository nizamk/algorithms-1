import java.util.Iterator;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] items;
    private int n;

    public RandomizedQueue() {

    }

    public boolean isEmpty() {
        // TODO - isEmpty()
        return false;
    }

    public int size() {
        // TODO - size()
        return 1;
    }

    public void enqueue(Item item) {
        // TODO - enqueue()
    }

    public Item dequeue() {
        // TODO - dequeue
        return items[0];
    }

    public Item sample() {
        // TODO - sample
        return items[0];
    }

    public Iterator<Item> iterator() {
        // TODO - iterator()
        return null;
    }

    public static void main(String[] args) {

    }
}