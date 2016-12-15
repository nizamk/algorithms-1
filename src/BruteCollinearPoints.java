import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BruteCollinearPoints {

    private List<LineSegment> lines = new ArrayList<>();
    private Point[] segments = new Point[4]; // contains 4 points

    /**
     * finds all line segments containing 4 points or more points
     *
     * @param points
     */
    public BruteCollinearPoints(Point[] points) {
        // TODO - Find the line segments
        Point p;
        for (int i = 0; i < points.length; i++) {
            p = points[i];
            segments[0] = p;
            int j = 0;
            for (int start = i + 1, end = start + 1; ((end - start) != 4) && end < points.length; end++) {
                double m1 = p.slopeTo(points[end-1]);
                double m2 = p.slopeTo(points[end]);
//                StdOut.println("slope of p, " + p + "-> " + points[end-1] + "is " + Math.abs(m1));
                boolean equalSlope = (m1 == m2);
                if (equalSlope) {
                    j = end - start;
                    segments[j] = points[end - 1];
                    j = end - start + 1;
                    segments[j] = points[end];
                }
            }
//            StdOut.println("j=> " + j);
            if (j == 3) {
                Arrays.sort(segments);
                lines.add(new LineSegment(segments[0], segments[segments.length-1]));
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
