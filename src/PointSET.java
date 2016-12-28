import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

import java.util.Iterator;

public class PointSET {

    /**
     * construct an empty set of points
     */
    public PointSET() {

    }

    /**
     * is the set empty?
     *
     * @return
     */
    public boolean isEmpty() {
        return false;
    }

    /**
     * number of points in the set
     *
     * @return
     */
    public int size() {
        return 0;
    }

    /**
     * add the point to the set (if it is not already in the set)
     * @param point2D
     */
    public void insert(Point2D point2D) {

    }

    /**
     * does the set contain point p?
     *
     * @param point2D
     * @return
     */
    public boolean contains(Point2D point2D) {
        return false;
    }

    /**
     * draw all points to standard draw
     */
    public void draw() {

    }

    /**
     * all points that are inside the rectangle
     *
     * @param rectHV
     * @return
     */
    public Iterable<Point2D> range(RectHV rectHV) {
        return new Iterable<Point2D>() {
            @Override
            public Iterator<Point2D> iterator() {
                return null;
            }
        };
    }

    /**
     * a nearest neighbor in the set to point p;
     * null if the set is empty
     *
     * @param p
     * @return
     */
    public Point2D nearest(Point2D p) {
        return new Point2D(1, 2);
    }

    public static void main(String[] args) {

    }
}
