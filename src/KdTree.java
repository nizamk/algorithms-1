import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.RedBlackBST;
import edu.princeton.cs.algs4.StdOut;

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
     * @param p
     */
    public void insert(Point2D p) {
        root = insert(root, p);
    }

    private Node insert(Node x, Point2D p) {
        if (x == null) return new Node(p);

        int level = getLevel(root, x.p);
        StdOut.printf("p %s\n", p);
        StdOut.printf("x.p %s\n", x.p);
        double cmp = compare(p, x.p, level);
        if (cmp < 0) {
            StdOut.printf("<- p  %s\n", p);
            x.lb = insert(x.lb, p);
        } else if (cmp > 0) {
            StdOut.printf("-> p %s\n", p);
            x.rt = insert(x.rt, p);
        } else {
            ;
        } // Found. Do nothing.
        return x;
    }

    private double compare(Point2D p, Point2D x, int level) {
        if ((level % 2) == 0) { // even
            StdOut.printf("Level %d, %s\n", level, ((level % 2) == 0 ? "EVEN" : "ODD"));
            return p.x() - x.x();
        } else {
            StdOut.printf("Level %d, %s\n", level, ((level % 2) == 0 ? "EVEN" : "ODD"));
            return p.y() - x.y();
        }
    }

//    private Node insert(Node x, Point2D p) {
//        if (x == null) return new Node(p);
//
//        int cmp = p.compareTo(x.p);
//        if (cmp < 0) x.lb = insert(x.lb, p);
//        else if (cmp > 0) x.rt = insert(x.rt, p);
//        else {
//            ;
//        } // Found. Do nothing.
//        return x;
//    }

    /**
     * does the set contain point p?
     *
     * @param p
     * @return
     */
    public boolean contains(Point2D p) {
        return search(root, p);
    }

    private boolean search(Node x, Point2D p) {
        if (x == null) return false;

        int cmp = p.compareTo(x.p);
        if (cmp < 0) return search(x.lb, p);
        else if (cmp > 0) return search(x.rt, p);
        else return true;
    }

    private int getLevel(Node x, Point2D p) {
        return getLevel(x, p, 0);
    }

    private int getLevel(Node x, Point2D p, int level) {
        if (x == null) return 0;
        if (p.compareTo(x.p) == 0) return level;
        int downlevel = getLevel(x.lb, p, level + 1);
        if (downlevel != 0)
            return downlevel;
        downlevel = getLevel(x.rt, p, level + 1);
        return downlevel;
    }

    private void visitInOrder(Node x) {
        if (x == null) return;
        visitInOrder(x.lb);
        visit(x);
        visitInOrder(x.rt);
    }

    private void visit(Node x) {
        int level = getLevel(root, x.p);
        StdOut.printf("visited %s, level %d, %s\n", x.p, level, (level % 2) == 0? "draw Vertical RED": "draw Horizontal Blue" );
    }

    /**
     * draw all points to standard draw
     */
    public void draw() {
        //todo-draw()
        visitInOrder(root);
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
        Point2D p = new Point2D(0.7,0.2 );
        tree.insert(p);
        p = new Point2D(0.5, 0.4);
        tree.insert(p);
        p = new Point2D(.2, .3);
        tree.insert(p);
        p = new Point2D(.4, .7);
        tree.insert(p);
        p = new Point2D(0.9, 0.6);
        tree.insert(p);
        tree.draw();
    }
}
