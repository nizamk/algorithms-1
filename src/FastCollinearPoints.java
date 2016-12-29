import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.*;

public class FastCollinearPoints {

    private List<List<Point>> foundSegments = new ArrayList<>();
    private HashMap<String, LineSegment> segmentExisted = new HashMap<>();
    private List<LineSegment> lines = new ArrayList<>();

    /**
     * finds all line segments containing 4 points or more points
     *
     * @param points
     */
    public FastCollinearPoints(Point[] points) {

        checkDuplicates(points);

        // Do not mutate constructor argument
        Point[] copy = Arrays.copyOf(points, points.length);

        // for all points in the plane
        for (Point origin : points) {

            // sort other points according to the slope
            Arrays.sort(copy, origin.slopeOrder());

            double prevSlope = Double.NEGATIVE_INFINITY;
            double slope = 0;

            List<Point> pointsInSlope = new ArrayList<>();
            for (int i = 1; i < copy.length; i++) {
                slope = origin.slopeTo(copy[i]);
                if (slope == prevSlope) {
                    pointsInSlope.add(copy[i]);
                } else {
                    if (pointsInSlope.size() >= 3) {
                        pointsInSlope.add(origin);
                        addIfNewSegment(pointsInSlope, prevSlope);
                    }

                    // initialize first points
                    pointsInSlope.clear();
                    pointsInSlope.add(copy[i]);
                }
                prevSlope = slope;
            }
            if (pointsInSlope.size() >= 3) {
                pointsInSlope.add(origin);
                addIfNewSegment(pointsInSlope, prevSlope);
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

    private static String getMapKey(Point start, Point end, double slope) {
        return String.valueOf(slope) + "_" +  start + "_" + end;
    }

    private void addIfNewSegment(List<Point> pointsInSlope, double slope) {
        Collections.sort(pointsInSlope);
        Point start = pointsInSlope.get(0);
        Point end = pointsInSlope.get(pointsInSlope.size() - 1);
        String key = getMapKey(start, end, slope);
        if (segmentExisted.get(key) == null) {
            LineSegment line = new LineSegment(start, end);
            lines.add(line);
            segmentExisted.put(key, line);
        }

//        List<Point> endpoints = new ArrayList<>();
//        endpoints.add(start);
//        endpoints.add(end);
//        if(!checkIfSegmentExist(start, end)) {
//            lines.add(new LineSegment(start, end));
//            foundSegments.add(endpoints);
//        }
    }

//    private void addIfNewSegment(List<Point> pointsInSlope, double slope) {
//        Collections.sort(pointsInSlope);
//        Point start = pointsInSlope.get(0);
//        Point end = pointsInSlope.get(pointsInSlope.size() - 1);
//        List<Point> endpoints = new ArrayList<>();
//        endpoints.add(start);
//        endpoints.add(end);
//        if(!checkIfSegmentExist(start, end)) {
//            lines.add(new LineSegment(start, end));
//            foundSegments.add(endpoints);
//        }
//    }


    private boolean checkIfSegmentExist(Point start, Point end) {
        for (List<Point> segment : foundSegments) {
            if ((segment.get(0) == start) && (segment.get(1) == end))
                return true;
        }
        return false;
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
