import edu.princeton.cs.algs4.*;

import java.awt.*;
import java.util.Iterator;

public class KdTree {

    private static final int SCALE = 1000;
    private static final int N = 1;

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
        root = insert(root, p, new RectHV(0, 0, N, N));
    }

    private Node insert(Node node, Point2D p, RectHV boundingRect) {
        if (node == null) {
            StdOut.println("created new node " + p + ", bounding rect " + boundingRect + "\n");
            return new Node(p, boundingRect);
        }
        StdOut.printf("insert node %s, current node %s\n", p, node.p);
        boolean evenOrientation = isEvenLevel(getLevel(root, node.p));
        double cmp = compare(p, node.p, evenOrientation);
        if (cmp < 0) {
            StdOut.printf("point %s LESS than parent %s, insert LEFT\n",p, node.p);
            RectHV rect = (evenOrientation ?
                    new RectHV(boundingRect.xmin(), boundingRect.ymin(), node.p.x(), boundingRect.ymax()):  // even - left bounding rectangle
                    new RectHV(boundingRect.xmin(), boundingRect.ymin(), node.p.x(), node.p.y()));          // odd - bottom bounding rectangle
            node.lb = insert(node.lb, p, rect);
        } else if (cmp >= 0) {
            StdOut.printf("point %s GREATER/EQUAL than parent %s, insert RIGHT\n",p, node.p);
            RectHV rect = (evenOrientation ?
                    new RectHV(node.p.x(), boundingRect.ymin(), boundingRect.xmax(), boundingRect.ymax()):  // even - right bounding rectangle
                    new RectHV(boundingRect.xmin(), node.p.y(), boundingRect.xmax(), boundingRect.ymax()));          // odd - top bounding rectangle
            node.rt = insert(node.rt, p, rect);
        } //else {;} // found. Do nothing
        return node;
    }

    private double compare(Point2D p, Point2D x, boolean even) {
        double cmp;
        if (even) {
            cmp = p.x() - x.x();
            StdOut.printf("EVEN level x-compare: %.2f\n", cmp);
            return cmp;
        } else {
            cmp = p.y() - x.y();
            StdOut.printf("ODD level y-compare: %.2f\n", cmp);
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

    private int height(Node t, Point2D p) {
        if (t == null) return 0;
        int hl = height(t.lb, p);
        int hr = height(t.rt, p);
        int h = 1 + Math.max(hl, hr);
        return h;
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

    private void inOrderTraversal(Node x) {
        if (x == null) return;
        inOrderTraversal(x.lb);
        visit(x);
        inOrderTraversal(x.rt);
    }

    private void visit(Node x) { drawNode(x, isEvenLevel(getLevel(root, x.p))); }

    private void drawNode(Node node, boolean even) {
        StdDraw.setPenRadius(0.003);
        StdOut.printf("Node %s, ", node.p);
        if (even) drawVLine(node);
        else drawHLine(node);
        drawPoint(node.p.x(), node.p.y());
        StdDraw.show();
    }

    private void drawPoint(double x, double y) {
        StdDraw.setPenColor(Color.BLACK);
        StdDraw.filledCircle(x * SCALE, (int) (y * SCALE), 8);
    }

    private void drawVLine(Node node) {
        StdOut.println("draw Vertical RED");
        int x0 = (int) (node.p.x() * SCALE);
        int y0 = (int) (node.rect.ymin() * SCALE);
        int x1 = (int) (node.p.x() * SCALE);
        int y1 = (int) (node.rect.ymax() * SCALE);
        StdOut.printf("from (%d,%d) -> (%d,%d)\n", x0, y0, x1, y1);
        StdDraw.setPenColor(Color.RED);
        StdDraw.line(x0, y0, x1, y1);
    }

    private void drawHLine(Node node) {
        StdOut.println("draw Horizontal BLUE");
        int x0 = (int) (node.rect.xmin() * SCALE);
        int y0 = (int) (node.p.y() * SCALE);
        int x1 = (int) (node.rect.xmax() * SCALE);
        int y1 = (int) (node.p.y() * SCALE);
        StdOut.printf("from (%d,%d) -> (%d,%d)\n", x0, y0, x1, y1);
        StdDraw.setPenColor(Color.blue);
        StdDraw.line(x0, y0, x1, y1);
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

    }


}
