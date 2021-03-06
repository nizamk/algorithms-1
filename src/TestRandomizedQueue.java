import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

public class TestRandomizedQueue<Item> {
    private RandomizedQueue<Item> queue;

    public TestRandomizedQueue(RandomizedQueue<Item> queue) {
        this.queue = queue;
    }

    public void testRun(String s, boolean b) {
        StdOut.printf("==> %s : %s\n\n", b ? "PASSED" : "FAILED", s);
    }

    public boolean checkArrayEquality(Item[] a, Item[] b) {
        int k = 0;
        boolean res = true;
        for (int i = 0; i < a.length; i++) {
            k = 0;
            for (; k < b.length; k++) {
                if (a[i] == b[k]) break;
            }
            if (k == b.length) res = false;
        }
        return res;
    }

    public void printItems(Item[] items) {
        StdOut.print("[");
        for (int i = 0; i < items.length; i++) {
            StdOut.print(items[i] + " ");
        }
        StdOut.print("]");
        StdOut.println();
    }

    private RandomizedQueue<Item> enqueueItems(Item[] input) {
        reset();
        for (int i = 0; i < input.length; i++) {
            queue.enqueue(input[i]);
        }
        return queue;
    }

    private void reset() {
        queue = new RandomizedQueue<>();
    }

    public boolean testAddItems(Item[] input, int expected) {
        enqueueItems(input);
        StdOut.println("input:      " + Arrays.toString(input));
        StdOut.println("expected size:  " + expected);
        StdOut.println("actual size:    " + queue.size());
        return queue.size() == expected;
    }

    public boolean testDequeueItems(Item[] input) {
        enqueueItems(input);
        int i=0;
        queue.dequeue();
        i++;
        queue.dequeue();
        i++;
        queue.dequeue();
        i++;

        return  input.length-i == queue.size();
    }

    public boolean testNestedIteratorsDiffRandomizedQueues(Item[] input, Item[] input2, Item[] expected, Item[] actual) {
        StdOut.printf("Test Input 1: %s\n", Arrays.toString(input));
        StdOut.printf("Test Input 2: %s\n", Arrays.toString(input2));

        RandomizedQueue<Item> queue = enqueueItems(input); // in = {a,b,c}
        RandomizedQueue<Item> queue2 = enqueueItems(input2); // in = {1,2,3}
        int expectedSize = queue.size() * queue2.size();
        int n = 0;
        StdOut.print("arrays actual:[");
        for (Item s: queue) {
            for (Item t : queue2) {
                StdOut.print("" + s + t + ", ");
                n++;
            }
        }
        StdOut.print("]");
        StdOut.println();
        StdOut.println("arrays expected:    " + Arrays.toString(expected));
        StdOut.println("actual count:       " + n);
        return  expectedSize == n;
    }

    public boolean testParallelIteratorsSameRandomizedQueues(Item[] input) {
        StdOut.printf("Test Input: %s\n", Arrays.toString(input));
        RandomizedQueue<Item> queue = enqueueItems(input);
        int i=0;
        Item[] a = (Item[])new Object[input.length];
        for (Item s: queue) {
            a[i++] = s;
        }

        int j = 0;
        Item[] b = (Item[])new Object[input.length];
        for (Item t : queue) {
            b[j++] = t;
        }
        printItems(a);
        printItems(b);
        return checkArrayEquality(a,b);
    }

    public boolean testNestedteratorsSameRandomizedQueues(Item[] input) {
        StdOut.printf("Test Input: %s\n", Arrays.toString(input));
        RandomizedQueue<Item> queue = enqueueItems(input);
        int i=0,j=0;
        Item[] a = (Item[])new Object[input.length];
        Item[] b = (Item[])new Object[input.length];
        for (Item s: queue) {
            a[i++] = s;
            j = 0;
            for (Item t : queue) {
                b[j++] = t;
            }
        }
        printItems(a);
        printItems(b);
        return checkArrayEquality(a,b);
    }

    public boolean testSingleIterator(Item[] input) {
        StdOut.printf("Test Input: %s\n", Arrays.toString(input));
        RandomizedQueue<Item> queue = enqueueItems(input);
        Item[] a = (Item[])new Object[input.length];
        int i=0;
        for (Item s: queue) {
            a[i++] = s;
        }
        StdOut.printf("actual:  %s (random order)\n",Arrays.toString(a));
        return checkArrayEquality(a, input);
    }
}
