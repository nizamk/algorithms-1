import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Subset {
    private final static int MAX = 100;

    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);
        String[] s = new String[MAX];
        int n = 0;
        for (int i = 0; !StdIn.isEmpty(); i++) {
            s[i] = StdIn.readString();
            n++;
        }

        RandomizedQueue<String> randQueue = new RandomizedQueue<>();
        for (int i = 0; i < n; i++) {
            randQueue.enqueue(s[i]);
        }

        for (int i = 0; i < k; i++) {
            StdOut.println(randQueue.dequeue());
        }
    }
}
