import edu.princeton.cs.algs4.*;
import edu.princeton.cs.algs4.Point2D;

import java.awt.*;
import java.awt.geom.*;
import java.util.Iterator;

public class KdTree {

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
    public KdTree() { root = null; }

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
        root = insert(root, p, new RectHV(0, 0, 1, 1));
    }

    private Node insert(Node node, Point2D p, RectHV boundingRect) {
        if (node == null) {
            StdOut.println("created new node " + p + ", bounding rect [" + boundingRect.xmin() + "," + boundingRect.ymin() + "]" + "," +
                    "[" + boundingRect.xmax() + "," + boundingRect.ymax() + "]\n");
            return new Node(p, boundingRect);
        }
        boolean evenOrientation = isEvenLevel(getLevel(root, node.p));
//        boolean evenOrientation = isEvenLevel(getLevel(root));
        double cmp = compare(p, node.p, evenOrientation);
        if (cmp < 0) {
            if (evenOrientation)
                StdOut.printf("%s insert LEFT of %s\n", p, node.p);
            else
                StdOut.printf("%s insert BOTTOM of %s\n", p, node.p);

            RectHV rect = (evenOrientation ?
                    new RectHV(boundingRect.xmin(), boundingRect.ymin(), node.p.x(), boundingRect.ymax()):  // LEFT bounding rectangle
                    new RectHV(boundingRect.xmin(), boundingRect.ymin(), boundingRect.xmax(), node.p.y())); // BOTTOM bounding rectangle
            node.lb = insert(node.lb, p, rect);
        } else if (cmp >= 0) {
            if (evenOrientation)
                StdOut.printf("%s insert RIGHT of %s\n", p, node.p);
            else
                StdOut.printf("%s insert TOP of %s\n", p, node.p);

            RectHV rect = (evenOrientation ?
                    new RectHV(node.p.x(), boundingRect.ymin(), boundingRect.xmax(), boundingRect.ymax()):  // RIGHT bounding rectangle
                    new RectHV(boundingRect.xmin(), node.p.y(), boundingRect.xmax(), boundingRect.ymax())); // TOP bounding rectangle
            node.rt = insert(node.rt, p, rect);
        }
        return node;
    }

    private double compare(Point2D p, Point2D x, boolean even) {
        if (even) return p.x() - x.x();
        else return p.y() - x.y();
    }

    private boolean isEvenLevel(int level) { return (level % 2) == 0; }

    /**
     * does the set contain point p?
     *
     * @param p
     * @return
     */
    public boolean contains(Point2D p) { return search(root, p); }

    private boolean search(Node node, Point2D p) {
        if (node == null) return false;
        double cmp = compare(p, node.p, isEvenLevel(getLevel(root, node.p)));
//        double cmp = compare(p, node.p, isEvenLevel(getLevel(root)));
        if (cmp < 0) return search(node.lb, p);
        else if (cmp > 0) return search(node.rt, p);
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

//    private int getLevel(Node x, int level) {
//        if (x == null) return 0;
//        int downlevel = getLevel(x.lb, level + 1);
//        if (downlevel != 0)
//            return downlevel;
//        downlevel = getLevel(x.rt, level + 1);
//        return downlevel;
//    }

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

    private void visit(Node x) {
        drawNode(x, isEvenLevel(getLevel(root, x.p)));
    }

    private void displayRectangleDistance(Node x, Point2D p) {
        if (x == null) return;
        displayRectangleDistance(x.lb, p);

        double distance = x.rect.distanceSquaredTo(p);
        StdOut.print("distance from" + " Bounding rect [" + x.rect.xmin() + "," + x.rect.ymin() + "]" + "," +
                "[" + x.rect.xmax() + "," + x.rect.ymax() + "]");
        StdOut.printf(" (splitting point %s) to %s is %2.6f, distance point  %2.6f\n", x.p, p, distance*10, x.p.distanceSquaredTo(p)*10.0);
        StdOut.printf("%s\n", (distance == 0 ? "Do not prune" : "Prune"));

        displayRectangleDistance(x.rt, p);
    }

    private void drawNode(Node node, boolean even) {
        if (even) drawVLine(node);
        else drawHLine(node);
        drawPoint(node);
        StdDraw.show();
    }

    private void drawPoint(Node node) {
        StdDraw.setPenColor(Color.BLACK);
        StdDraw.filledCircle(node.p.x(), node.p.y(), 0.005);
    }

    private void drawVLine(Node node) {
        StdDraw.setPenColor(Color.RED);
        StdDraw.line(node.p.x(), node.rect.ymin(), node.p.x(), node.rect.ymax());
    }

    private void drawHLine(Node node) {
        StdDraw.setPenColor(Color.blue);
        StdDraw.line(node.rect.xmin(), node.p.y(), node.rect.xmax(), node.p.y());
    }

    /**
     * draw all points to standard draw
     *
     */
    public void draw() {
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
    public Point2D nearest(Point2D p) { return nearest(root, p, null, root.p.distanceSquaredTo(p)); }

    private Point2D nearest(Node node, Point2D qp, Point2D nearest, double champion) {

        // base case
        if (node == null) return nearest;

        // calculate the distance
        double rectDistance = node.rect.distanceSquaredTo(qp);
        double distance = node.p.distanceSquaredTo(qp);

        // select champion
        if (distance < champion) {
            champion = distance;
            nearest = node.p;
        }

        // The query point is within current node bounding rectangle so keep
        // searching. Even level - choose subtree either left or right of the
        // splitting line which is closer to query point. Odd level - choose
        // subtree either top or bottom of the splitting line which is closer
        boolean evenOrientation = isEvenLevel(getLevel(root, qp));
//        boolean evenOrientation = isEvenLevel(getLevel(root));
        if (rectDistance == 0) {
            double cmp = compare(qp, node.p, evenOrientation);
            if (cmp < 0) {
                return nearest(node.lb, qp, nearest, champion);
            } else if (cmp > 0) {
                return nearest(node.rt, qp, nearest, champion);
            }
        }
        return nearest;
    }


}
