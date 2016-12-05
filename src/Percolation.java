import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    public static final boolean SITE_OPENED     = true;
    public static final boolean SITE_BLOCKED    = false;
    public static final int     TOP_ROW         = 1;

    // using component root with bit set techniques
    private byte[] mRootState;
    private boolean[] mStates;  // model states
    private int size;           // grid side (grid is side-by-side of sites)
    private int source;         // virtual top site
    private int sink;           // virtual bottom site
    private WeightedQuickUnionUF uf;        // network of sites
    private WeightedQuickUnionUF ufbwash;   // network of sites
    private boolean percolate;  // model percolates?

    /**
     * create n-by-n grid, with all sites blocked.
     * performance of constructor should take N-square.
     *
     * @param n one side of grid of size n-by-n
     */
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("size n should be greater than 0");
        }
        source = 0;
        sink = n * n + 1;
        size = n;
        mStates = new boolean[n * n];
        percolate = false;
        uf = new WeightedQuickUnionUF(n * n + 2);       // union model contains N^2 + 2 (top & bottom virtual) sites
        ufbwash = new WeightedQuickUnionUF(n * n + 1);  // union model contains N^2 + 1 (top virtual) sites
        int i;
        for (int row = 1; row <= n; row++) {
            for (int col = 1; col <= n; col++) {
                i = xyTo1D(row, col);
                mStates[i] = SITE_BLOCKED;
            }
        }
    }

    /**
     * open site (row, col) if it is not open already.
     * <p>
     * performance of open() should take constant time.
     * <p>
     * algorithm:
     * - set the site to opened state
     * - connected the new site to adjacent open sites by using few union commands
     *
     * @param row site row
     * @param col site column
     */
    public void open(int row, int col) {
        validate(row, col);
        int i = xyTo1D(row, col);
        if (mStates[i]) { return; } // site open already
        mStates[i] = SITE_OPENED;
        int id =  i + 1;    // site id start from 1...N, to accomodate virtual top node (id 1) and bottom (n+1)
        if (row == TOP_ROW) {
            uf.union(id, source);
            ufbwash.union(id, source); // handling backwash, only virtual top node required
        }
        if (row == size) {
            uf.union(id, sink);
        }
        connectNeighboringSites(id, row, col);
    }

    /**
     * is site (row, col) open?.
     * performance should take constant time.
     *
     * @param row
     * @param col
     * @return true if site is opened
     */
    public boolean isOpen(int row, int col) {
        validate(row, col);
        return mStates[xyTo1D(row, col)];
    }

    /**
     * is site (row, col) full?
     * performance should be constant time
     * <p>
     * a full site is an open site that can be connected to an open site in
     * the top row via a chain of neighboring (up, down, left, right) sites.
     * implementation - a full site is an open site connected to the source site
     *
     * @param row
     * @param col
     * @return true if it's a full site
     */
    public boolean isFull(int row, int col) {
        validate(row, col);
        int id = xyTo1D(row, col) + 1;
        return isOpen(row, col) && ufbwash.connected(id, source);
    }

    /**
     * does the system percolate?
     * <p>
     * definition:
     * a system percolates if there is a full site at the bottom row, that's i.e.
     * there is an open site at the BOTTOM row that can be connected to an open site
     * at the TOP row via chain of neighboring sites.
     * <p>
     * performance should be constant time
     *
     * @return true if system percolates
     */
    public boolean percolates() {
        return  uf.connected(sink, source);
    }

    private void connectNeighboringSites(int site, int row, int col) {
        int leftId;
        int rightId;
        int topId;
        int bottomId;

        if ((col - 1) > 0) {
            leftId = xyTo1D(row, col - 1) + 1;
            if (isOpen(row, col - 1)) {
                uf.union(site, leftId);
                ufbwash.union(site, leftId);
            }
        }
        if ((col + 1) <= size) {
            rightId = xyTo1D(row, col + 1) + 1;
            if (isOpen(row, col + 1)) {
                uf.union(site, rightId);
                ufbwash.union(site, rightId);
            }
        }
        if ((row - 1) > 0) {
            topId = xyTo1D(row - 1, col) + 1;
            if (isOpen(row - 1, col)) {
                uf.union(site, topId);
                ufbwash.union(site, topId);
            }
        }
        if ((row + 1) <= size) {
            bottomId = xyTo1D(row + 1, col) + 1;
            if (isOpen(row + 1, col)) {
                uf.union(site, bottomId);
                ufbwash.union(site, bottomId);
            }
        }
    }

    // row and col each should have range between 1..N
    private void validate(int row, int col) {
        if (row < 1 || row > size) {
            throw new IndexOutOfBoundsException(String.format("row index %d is out of bounds.", row));
        }
        if (col < 1 || col > size) {
            throw new IndexOutOfBoundsException(String.format("col index %d is out of bounds.", col));
        }
    }

    private int xyTo1D(int r, int c) {
        return (r - 1) * size + (c - 1);
    }

    public static void main(String[] args) {
        StdOut.println("********************************");
        StdOut.println("Running Percolation Simulation:");
        StdOut.println("********************************");

        In in = new In(args[0]);      // input file
        int n = in.readInt();         // n-by-n percolation system
        Percolation perc = new Percolation(n);
        int opened = 0;
        while (!in.isEmpty()) {
            int i = in.readInt();
            int j = in.readInt();
            perc.open(i, j);
            if (perc.isOpen(i, j)) {
                opened++;
            }
        }
        StdOut.printf("System %s, %d opened sites\n", perc.percolates() ? "percolates" : "DOES NOT percolate", opened);
    }
}

