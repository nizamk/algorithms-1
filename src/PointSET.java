import edu.princeton.cs.algs4.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PointSET {

    private SET<Point2D> points;

    /**
     * construct an empty set of points
     */
    public PointSET() {
        points = new SET<>();
    }

    /**
     * is the set empty?
     *
     * @return
     */
    public boolean isEmpty() {
        return points.isEmpty();
    }

    /**
     * number of points in the set
     *
     * @return
     */
    public int size() {
        return points.size();
    }

    /**
     * add the point to the set (if it is not already in the set)
     * @param point
     */
    public void insert(Point2D point) {
        checkNull(point, "point is null.");
        points.add(point);
    }

    /**
     * does the set contain point p?
     *
     * @param point
     * @return
     */
    public boolean contains(Point2D point) {
        checkNull(point, "point is null");
        return points.contains(point);
    }

    /**
     * draw all points to standard draw
     */
    public void draw() {
        for (Point2D point : points) {
            StdDraw.point(point.x(), point.y());
        }
    }

    /**
     * all points that are inside the rectangle
     *
     * @param rect
     * @return
     */
    public Iterable<Point2D> range(RectHV rect) {
        List<Point2D> solution = new ArrayList<>();
        for (Point2D point : points) {
            if (rect.contains(point))
                solution.add(point);
        }
        return solution;
    }

    /**
     * a nearest neighbor in the set to point p;
     * null if the set is empty
     *
     * @param p
     * @return
     */
    public Point2D nearest(Point2D p) {
        checkNull(p, "point is null");
        Point2D nearest = null;
        for (Point2D point : points) {
            if (nearest == null || point.distanceTo(p) < nearest.distanceTo(p)) {
                nearest = point;
            }
        }
        return nearest;
    }

    private void checkNull(Object o, String msg) {
        if (null == o)
            throw new NullPointerException(msg);
    }

    public static void main(String[] args) {

    }
}
