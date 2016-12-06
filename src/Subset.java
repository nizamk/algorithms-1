import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;

public class Subset {
    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);
        RandomizedQueue<String> randQueue = new RandomizedQueue<>();
        String s;
        for (int i = 0; !StdIn.isEmpty(); i++) {
            s = StdIn.readString();
            randQueue.enqueue(s);
        }
        for (int i = 0; i < k & randQueue.size() > 0; i++) {
            StdOut.println(randQueue.dequeue());
        }
    }
}
