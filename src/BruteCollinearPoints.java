import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BruteCollinearPoints {

    Point[] others;
    List<Point> lines = new ArrayList<>();
    Point[] segments = new Point[4]; // contains 4 points

    /**
     * finds all line segments containing 4 points or more points
     *
     * @param points
     */
    public BruteCollinearPoints(Point[] points) {
//        others = new Point[points.length];

        // TODO - Find the line segments
        Point p;
        for (int i = 0; i < points.length; i++) {
            p = points[i];
            StdOut.println("p = " + points[i]);
            for (int s = i + 1, j = s; ((j - s) != 3) && j < points.length; j++) {
                double m = p.slopeTo(points[j]);
                StdOut.println("slope of p, " + p + "-> " + points[j] + "is " + Math.abs(m));
            }
        }
//        for (int i = 0; i < points.length; i++) {
//            p = points[i];
//            int start= i;
//            int end = 2;
//            int count = 0;
//            int j = 0;
//            segments[j] = p;
//            while (end < points.length) {
//                double m1 = p.slopeTo(points[start + end]);
//                double m2 = p.slopeTo(points[start + end - 1]);
//                boolean equalSlope = (m1 == m2);
//                if (equalSlope) {
//                    end++;
//                    segments[++j] = points[start + end - 1];
//                    if (end == 3) {
//                    }
//                }
//
//                if (end == points.length) {
//
//                }
//            }
//        }
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
