import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BruteCollinearPoints {

    private List<LineSegment> lines = new ArrayList<>();

    private boolean collinear(Point p, Point q, Point r, Point s) {
        return p.slopeTo(q) == q.slopeTo(r) &&
               q.slopeTo(r) == r.slopeTo(s);
    }

    /**
     * finds all line segments containing 4 points or more points
     *
     * @param points
     */
    public BruteCollinearPoints(Point[] points) {
        // Performance is O(N^4) :(
        checkDuplicates(points);
        Arrays.sort(points);
        for (int i = 0; i < points.length; i++) {
            for (int j = i + 1; j < points.length; j++) {
                for (int k = j + 1; k < points.length; k++) {
                    for (int l = k + 1; l < points.length; l++) {
                        if (collinear(points[i], points[j], points[k], points[l])) {
                            lines.add(new LineSegment(points[i], points[l]));
                        }
                    }
                }
            }
        }
    }

    private void checkDuplicates(Point[] points) {
        for (int i = 0; i < points.length; i++) {
            for (int j = i + 1; j < points.length; j++) {
                if (points[i].compareTo(points[j]) == 0) {
                    throw new IllegalArgumentException("Duplicate points.");
                }
            }
        }
    }

    /**
     * The number of line segments
     *
     * @return
     */
    public int numberOfSegments() { return lines.size(); }

    /**
     * the line segments
     *
     * @return
     */
    public LineSegment[] segments() { return lines.toArray(new LineSegment[lines.size()]); }

}
