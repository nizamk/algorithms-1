import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private double[] total;
    private int trials;

    /**
     * perform trials independent experiments on an n-by-n grid
     *
     * @param n
     * @param trials
     */
    public PercolationStats(int n, int trials) {
        if (n <= 0) {
            throw new IllegalArgumentException("size n should be greater than 0");
        }
        if (trials <= 0) {
            throw new IllegalArgumentException("size trials should be greater than 0");
        }

        int opened;
        this.trials = trials;
        total = new double[this.trials];
        int row, col;
        Percolation perc;
        for (int i = 0; i < this.trials; i++) {
            opened = 0;
            perc = new Percolation(n);
            while (!perc.percolates()) {
                row = StdRandom.uniform(1, n + 1);
                col = StdRandom.uniform(1, n + 1);
                if (!perc.isOpen(row, col)) {
                    perc.open(row, col);
                    opened++;
                }
            }
            total[i] = 1.0 * opened / (n * n);
        }
    }

    /**
     * sample mean of percolation threshold
     *
     * @return
     */
    public double mean() {
        return StdStats.mean(total);
    }

    /**
     * sample standard deviation of percolation threshold
     *
     * @return
     */
    public double stddev() {
        return StdStats.stddev(total);
    }

    /**
     * low endpoint of 95% confidence interval
     *
     * @return
     */
    public double confidenceLo() {
        double s = stddev();
        return mean() - 1.0 * 1.96 * s / Math.sqrt(trials);
    }

    /**
     * high endpoint of 95% confidence interval
     *
     * @return
     */
    public double confidenceHi() {
        double s = stddev();
        return mean() + 1.0 * 1.96 * s / Math.sqrt(trials);
    }

    public static void main(String[] args) {
        StdOut.println("********************************");
        StdOut.println("Percolation Statistics:");
        StdOut.println("********************************");

        int n = Integer.parseInt(args[0]);         // n-by-n percolation system
        int trials = Integer.parseInt(args[1]);
        PercolationStats stats = new PercolationStats(n, trials);
        StdOut.printf("mean                    = %.16f\n", stats.mean());
        StdOut.printf("stdev                   = %.16f\n", stats.stddev());
        StdOut.println("95% confidence interval = " + stats.confidenceLo() + ", " + stats.confidenceHi());

    }
}

