import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

import java.util.Iterator;

public class KdTree {
    /**
     * construct an empty set of points
     */
    public KdTree() {

    }

    /**
     * is the set empty?
     *
     * @return
     */
    public boolean isEmpty() {
        //todo - isEmpty()
        return false;
    }

    /**
     * number of points in the set
     *
     * @return
     */
    public int size() {
        //todo- size()
        return 0;
    }

    /**
     * add the point to the set (if it is not already in the set)
     * @param point2D
     */
    public void insert(Point2D point2D) {
        //todo-insert()

    }

    /**
     * does the set contain point p?
     *
     * @param point2D
     * @return
     */
    public boolean contains(Point2D point2D) {
        //todo -contains()
        return false;
    }

    /**
     * draw all points to standard draw
     */
    public void draw() {
        //todo-draw()
    }

    /**
     * all points that are inside the rectangle
     *
     * @param rectHV
     * @return
     */
    public Iterable<Point2D> range(RectHV rectHV) {
        //todo -range()
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
        //todo-nearest()
        return new Point2D(1, 2);
    }
}
