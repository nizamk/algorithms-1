import edu.princeton.cs.algs4.*;

import java.awt.*;
import java.util.Iterator;

public class KdTree {

    private static final int SCALE = 1000;

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
        public Node(Point2D point, RectHV boundingRect) {
            rect = boundingRect;
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
        root = insert(root, p, new RectHV(0.0, 0.0, 1.0, 1.0));
    }

    private Node insert(Node x, Point2D p, RectHV boundingRect) {
        // todo - to put axis-aligned rectangle
        if (x == null) {
            StdOut.println("create new node " + p);
            return new Node(p, boundingRect);
        }
        StdOut.printf("insert node %s, current node %s\n", p, x.p);
        boolean evenOrientation = isEvenLevel(getLevel(root, x.p));
        double cmp = compare(p, x.p, evenOrientation);
        if (cmp < 0) {
            StdOut.printf("point %s LESS than parent %s, insert LEFT\n",p, x.p);
            RectHV rect = (evenOrientation ?
                    new RectHV(0.0, 0.0, p.x(), boundingRect.ymax()):   // todo - left bounding rectangle
                    new RectHV(0.0, 0.0, p.x(), boundingRect.ymax()));  // todo - bottom bounding rectangle
            x.lb = insert(x.lb, p, rect);
        } else if (cmp > 0) {
            StdOut.printf("point %s GREATER than parent %s, insert RIGHT\n",p, x.p);
            RectHV rect = (evenOrientation ?
                    new RectHV(p.x(), boundingRect.ymin(), boundingRect.xmax(), boundingRect.ymax()):   // todo - right bounding rectangle
                    new RectHV(0.0, 0.0, p.x(), boundingRect.ymax()));  // todo - top bounding rectangle
            x.rt = insert(x.rt, p, rect);
        } else {
            ;
        } // found. Do nothing
        return x;
    }

    private double compare(Point2D p, Point2D x, boolean even) {
        double cmp;
        if (even) {
            cmp = p.x() - x.x();
            StdOut.println("EVEN level x-compare: " + cmp);
            return cmp;
        } else {
            cmp = p.y() - x.y();
            StdOut.println("ODD level y-compare: " + cmp);
            return cmp;
        }
    }

    private boolean isEvenLevel(int level) { return (level % 2) == 0; }

    /**
     * does the set contain point p?
     *
     * @param p
     * @return
     */
    public boolean contains(Point2D p) { return search(root, p); }

    private boolean search(Node x, Point2D p) {
        if (x == null) return false;
        //todo - to fix
        int cmp = p.compareTo(x.p);
        if (cmp < 0) return search(x.lb, p);
        else if (cmp > 0) return search(x.rt, p);
        else return true;
    }

    private int getLevel(Node x, Point2D p) { return getLevel(x, p, 0); }

    private int getLevel(Node x, Point2D p, int level) {
        if (x == null) return 0;
        if (p.compareTo(x.p) == 0) return level;
        int downlevel = getLevel(x.lb, p, level + 1);
        if (downlevel != 0)
            return downlevel;
        downlevel = getLevel(x.rt, p, level + 1);
        return downlevel;
    }

    private void inOrderTraversal(Node x) {
        if (x == null) return;
        inOrderTraversal(x.lb);
        visit(x);
        inOrderTraversal(x.rt);
    }

    private void visit(Node x) { drawNode(x, isEvenLevel(getLevel(root, x.p))); }

    private void drawNode(Node node, boolean even) {
        StdDraw.setPenRadius(0.003);
        StdOut.printf("%s ", node.p);
        if (even) drawVLine(node.p.x(), node.p.y());
        else drawHLine(node.p.x(), node.p.y());
        drawPoint(node.p.x(), node.p.y());
        StdDraw.show();
    }

    private void drawPoint(double x, double y) {
        StdDraw.setPenColor(Color.BLACK);
        StdDraw.filledCircle(x * SCALE, (int) (y * SCALE), 5);
    }

    private void drawVLine(double x, double y) {
        StdOut.println("draw Vertical RED");
        int x0 = (int) (x * SCALE);
        int y0 = 0 * SCALE;
        int x1 = (int) (x * SCALE);
        int y1 = (int) (1 * SCALE);
        StdDraw.setPenColor(Color.RED);
        StdDraw.line(x0, y0, x1, y1);
    }

    private void drawHLine(double x, double y) {
        StdOut.println("draw Horizontal BLUE");
        StdDraw.setPenColor(Color.blue);
//        StdDraw.line(x0, y0, x1, y1);
    }

    /**
     * draw all points to standard draw
     *
     */
    public void draw() {
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, SCALE);
        StdDraw.setYscale(0, SCALE);

        inOrderTraversal(root);
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
