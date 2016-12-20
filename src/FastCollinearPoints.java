import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.*;

public class FastCollinearPoints {

    private HashMap<Double, List<Point>> foundSegments = new HashMap<>();
    private List<LineSegment> lines = new ArrayList<>();

    /**
     * finds all line segments containing 4 points or more points
     *
     * @param points
     */
    public FastCollinearPoints(Point[] points) {
        // Do not mutate constructor argument
        Point[] pointsCopy = Arrays.copyOf(points, points.length);

        for (Point pivot : points) {
            Arrays.sort(pointsCopy, pivot.slopeOrder());
            double prevSlope = Double.NEGATIVE_INFINITY;
            double slope = 0;
            List<Point> slopePoints = new ArrayList<>();
            for (int i = 1; i < pointsCopy.length; i++) {
                slope = pivot.slopeTo(pointsCopy[i]);
                if (slope == prevSlope) {
                    slopePoints.add(pointsCopy[i]);
                } else {
                    if (slopePoints.size() >= 3) {
                        slopePoints.add(pivot);
//                        lines.add(new LineSegment(slopePoints.get(0), slopePoints.get(slopePoints.size()-1)));
                        addNewSegment(slopePoints, slope);
                    }
                    slopePoints.clear();
                    slopePoints.add(pointsCopy[i]);
                }
                prevSlope = slope;
            }
            if (slopePoints.size() >= 3) {
                slopePoints.add(pivot);
                lines.add(new LineSegment(slopePoints.get(0), slopePoints.get(slopePoints.size()-1)));
                addNewSegment(slopePoints, slope);
            }
        }

    }

    private void addNewSegment(List<Point> slopePoints, double slope) {
        List<Point> endPoints = foundSegments.get(slope);
        Collections.sort(slopePoints);

        Point startPoint = slopePoints.get(0);
        Point endPoint = slopePoints.get(slopePoints.size() - 1);

        if (endPoints == null) {
            endPoints = new ArrayList<>();
            endPoints.add(endPoint);
            foundSegments.put(slope, endPoints);
            lines.add(new LineSegment(startPoint, endPoint));
        } else {
            for (Point currentEndPoint : endPoints) {
                if (currentEndPoint.compareTo(endPoint) == 0) {
                    return;
                }
            }
            endPoints.add(endPoint);
            lines.add(new LineSegment(startPoint, endPoint));
        }
    }

    private void addSegmentIfNew(List<Point> slopePoints, double slope) {
        List<Point> endPoints = foundSegments.get(slope);
        Collections.sort(slopePoints);

        Point startPoint = slopePoints.get(0);
        Point endPoint = slopePoints.get(slopePoints.size() - 1);

        if (endPoints == null) {
            endPoints = new ArrayList<>();
            endPoints.add(endPoint);
            foundSegments.put(slope, endPoints);
            lines.add(new LineSegment(startPoint, endPoint));
        } else {
            for (Point currentEndPoint : endPoints) {
                if (currentEndPoint.compareTo(endPoint) == 0) {
                    return;
                }
            }
            endPoints.add(endPoint);
            lines.add(new LineSegment(startPoint, endPoint));
        }
    }


    private void showPoints(Point[] points) {
        for (Point p : points) {
            StdOut.print(p + " ");
        }
        StdOut.println("");
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
