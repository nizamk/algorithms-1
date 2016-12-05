import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

public class TestDeque<T> {

    private Deque<T> deck;
    private T[] inputs;

    public TestDeque(Deque<T> deck,T[] inputs) {
        this.deck = deck;
        this.inputs = inputs;
        StdOut.printf("* Initial Test Input: %s\n\n", Arrays.toString(inputs));
    }

    // helper
    private void reset() {
        deck = new Deque<>();
    }

    // helper
    private Deque<T> addFirstToDeck(T[] in) {
        reset();
        for (int i = 0; i < in.length; i++) {
            deck.addFirst(in[i]);
        }
        return deck;
    }

    // helper
    private Deque<T> addLastToDeck(T[] in) {
        reset();
        for (int i = 0; i < in.length; i++) {
            deck.addLast(in[i]);
        }
        return deck;
    }

    public boolean testIteratorNested(T[] in1, T[] in2, T[] expected, T[] actual) {
        StdOut.printf("Test Input 1: %s\n", Arrays.toString(in1));
        StdOut.printf("Test Input 2: %s\n", Arrays.toString(in2));

        Deque<T> deck1 = addLastToDeck(in1); // in = {a,b,c}
        Deque<T> deck2 = addLastToDeck(in2); // in = {1,2,3}
        int expectedSize = deck1.size() * deck2.size();
        // nested output = a1,a2,a3,b1,b2,b3,c1,c2,c3
        // size output = deck1.size() * deck2.size()
        int n = 0;
        StdOut.print("arrays actual:[");
        for (T s: deck1) {
            for (T t : deck2) {
                StdOut.print("" + s + t + ", ");
                n++;
            }
        }
        StdOut.print("]");
        StdOut.println();
        StdOut.println("arrays expected:    " + Arrays.toString(expected));
//        StdOut.println("arrays actual:      " + Arrays.toString(actual));
        StdOut.println("actual count:       " + n);
        return  expectedSize == n;
    }

    public boolean testIteratorSingle(T[] in, T[] expected, T[] actual) {
        StdOut.printf("Test Input: %s\n", Arrays.toString(inputs));
        Deque<T> me=addLastToDeck(in); // in = {3,5,7}

        int n = 0;
        StdOut.print("elements:[");
        for (T e : me) {
            actual[n] = e;
            n++;
            StdOut.print(e + " ");
        }
        StdOut.print("]");
        StdOut.println();
        StdOut.println("arrays expected:    " + Arrays.toString(expected));
        StdOut.println("arrays actual:      " + Arrays.toString(actual));
        StdOut.println("actual count:       " + n);
        return me.size() == n && Arrays.equals(expected, actual) ;
    }

    /**
     * Queue semantic
     *  - addLast()     - enqueue
     *  - removeFront() - dequeue
     *
     * @return
     */
    public boolean testQueueOperations(T[] in, T[] expected, T[] actual) {
        StdOut.printf("Test Input: %s\n", Arrays.toString(inputs));
        Deque<T> me = addLastToDeck(in); // in = {3,5,7}
        for (int i = 0; i < actual.length; i++) {
            actual[i] = deck.removeFirst();
        }
        StdOut.println("arrays expected:    " + Arrays.toString(expected));
        StdOut.println("arrays actual:      " + Arrays.toString(actual));
        return Arrays.equals(expected, actual);
    }

    /**
     * Test scenarios:
     *
     * - addfirst(3)                                deck = (3)
     * - addfirst(5)                                deck = (5,3)
     * - removefirst()=> 5                          deck = (3)
     * - addlast(7)                                 deck = (3,7)
     * - removefirst()=> 3                          deck = (7)
     * - removelast() => 7                          deck = ()
     * - removefirst()=> NoSuchElementException     deck = ()
     * - removelast()=> NoSuchElementException      deck = ()
     */
    public boolean testRandom(T[] in, T expected1, T expected2, T expected3, T val1) {
        StdOut.printf("Test Input: %s\n", Arrays.toString(in));

        Deque<T> me = addFirstToDeck(in); // in = {7,5,3}
        T actual = me.removeFirst(); // => 7, {5,3}
        boolean res1 = expected1 == actual; // item = 7
        StdOut.print("expected 1:   " + "[" + expected1 + "],   ");
        StdOut.println("actual: " + "[" + actual + "]");

        me.addLast(val1);    // {5,3, in1}
        actual = me.removeFirst(); // => 5, {7, in1}
        boolean res2 = expected2 == actual; // item = 5
        StdOut.print("expected 2:   " + "[" + expected2 + "],   ");
        StdOut.println("actual: " + "[" + actual + "]");

        actual = me.removeLast(); // => 5, {7, in1}
        boolean res3 = expected3 == actual; // item = in1
        StdOut.print("expected 3:   " + "[" + expected3 + "],  ");
        StdOut.println("actual: " + "[" + actual + "]");

        return res1 && res2 && res3;
    }

    /**
     * Test size
     *
     * @return
     */
    public boolean testSizeAddFirst(T[] in) {
        StdOut.printf("Test Input: %s\n", Arrays.toString(in));
        Deque<T> me = addFirstToDeck(in);
        StdOut.print("expected size:   " + "[" + in.length + "],   ");
        StdOut.println("actual size: " + "[" + me.size() + "]");
        return me.size() == in.length;
    }

    /**
     * Stack semantics:
     * - addFirst()     - push()
     * - removeFirst()  - pop()
     *
     * @param expected
     * @param actual
     * @return
     */
    public boolean testStackOperations(T[] in, T[] expected, T[] actual) {
        StdOut.printf("Test Input: %s\n", Arrays.toString(in));
        addFirstToDeck(in); // in = {7,5}
        for (int i = 0; i < actual.length; i++) {
            actual[i] = deck.removeFirst();
        }
        StdOut.println("arrays expected:    " + Arrays.toString(expected));
        StdOut.println("arrays actual:      " + Arrays.toString(actual));
        return Arrays.equals(expected, actual);
    }

    /**
     * Test - addFirst() and removeLast()
     *
     * @param expected
     * @param actual
     * @return
     */
    public boolean testAddFirstRemoveLast(T[] in, T[] expected, T[] actual) {
        StdOut.printf("Test Input: %s\n", Arrays.toString(in));
        Deque<T> me = addFirstToDeck(in); // in = {7,5,3}
        for (int i = 0; i < actual.length; i++) {
            actual[i] = me.removeLast();
        }
        StdOut.println("arrays expected:    " + Arrays.toString(expected));
        StdOut.println("arrays actual:      " + Arrays.toString(actual));
        return Arrays.equals(expected, actual);
    }
}
