import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.RedBlackBST;

import java.util.Iterator;

public class KdTree {

    private PointSET pointSET;

    private Node root;

    private static class Node {
        private Point2D p;      // the point
        private RectHV rect;    // axis-aligned rectangle corresponding to this node
        private Node lb;        // left/bottom
        private Node rt;        // right/top

        public Node(Point2D point) {
            p = point;
        }
    }


    /**
     * construct an empty set of points
     */
    public KdTree() {
        pointSET = new PointSET();
    }

    /**
     * is the set empty?
     *
     * @return
     */
    public boolean isEmpty() {
        return root == null;
    }

    /**
     * number of points in the set
     *
     * @return
     */
    public int size() {
        return size(root);
    }

    private int size(Node x) {
        if (x == null) return 0;
        else return 1 + size(x.lb) + size(x.rt);
    }

    /**
     * add the point to the set (if it is not already in the set)
     * @param point
     */
    public void insert(Point2D point) {
        //todo-insert()
        root = insert(root, point);
    }

    private Node insert(Node x, Point2D point) {
        if (x == null) return new Node(point);
        return null;
    }

    /**
     * does the set contain point p?
     *
     * @param point
     * @return
     */
    public boolean contains(Point2D point) {
        //todo -contains()
        return pointSET.contains(point);
    }

    /**
     * draw all points to standard draw
     */
    public void draw() {
        //todo-draw()
        pointSET.draw();
    }

    /**
     * all points that are inside the rectangle
     *
     * @param rect
     * @return
     */
    public Iterable<Point2D> range(RectHV rect) {
        //todo -range()
        return pointSET.range(rect);
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
        return pointSET.nearest(p);
    }

    public static void main(String[] args) {
        KdTree tree = new KdTree();
        tree.insert(new Point2D(.7, .2));
//        tree.insert(new Point2D(.5, .4));
//        tree.insert(new Point2D(.2, .3));
//        tree.insert(new Point2D(.4, .7));
//        tree.insert(new Point2D(.9, .6));
        tree.draw();
    }
}
