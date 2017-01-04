import edu.princeton.cs.algs4.*;
import edu.princeton.cs.algs4.Point2D;

import java.awt.*;
import java.awt.geom.*;
import java.util.Iterator;

public class KdTree {

    // top of KD tree
    private Node root;

    // tree size
    private int size;

    private static class Node {
        private Point2D p;      // the point
        private RectHV rect;    // axis-aligned rectangle corresponding to this node
        private Node lb;        // left/bottom subtree
        private Node rt;        // right/top subtree

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
        root = null;
        size = 0;
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
        return size;
//        else return 1 + size(x.lb) + size(x.rt);
    }

    /**
     * add the point to the set (if it is not already in the set)
     * @param p
     */
    public void insert(Point2D p) {
        root = insert(root, p, new RectHV(0, 0, 1, 1), true);
    }

    private Node insert(Node node, Point2D p, RectHV boundingRect, boolean xCmp) {
        if (node == null) {
//            StdOut.println("created new node " + p + ", bounding rect [" + boundingRect.xmin() + "," + boundingRect.ymin() + "]" + "," +
//                    "[" + boundingRect.xmax() + "," + boundingRect.ymax() + "]");
            size++;
            return new Node(p, boundingRect);
        }
        double cmp = compare(p, node.p, xCmp);
        if (cmp < 0) {
            RectHV rect = getRect(node, true, xCmp);
            node.lb = insert(node.lb, p, rect, !xCmp);
        } else if (cmp >= 0) {
            RectHV rect = getRect(node, false, xCmp);
            node.rt = insert(node.rt, p, rect, !xCmp);
        }
        return node;
    }

    private RectHV getRect(Node node, boolean less, boolean xCmp) {
        // comparing x's
        if (xCmp) {
            if (less)
                return new RectHV(node.rect.xmin(), node.rect.ymin(), node.p.x(), node.rect.ymax()); // left
            else
                return new RectHV(node.p.x(), node.rect.ymin(), node.rect.xmax(), node.rect.ymax()); // right
        }
        // comparing y's
        else {
            if (less)
                return new RectHV(node.rect.xmin(), node.rect.ymin(), node.rect.xmax(), node.p.y()); // bottom
            else
                return new RectHV(node.rect.xmin(), node.p.y(), node.rect.xmax(), node.rect.ymax()); // top
        }
    }

    private double compare(Point2D p, Point2D x, boolean even) {
        if (even)
            return p.x() - x.x();
        else
            return p.y() - x.y();
    }

    private boolean isEvenLevel(int level) { return (level % 2) == 0; }

    /**
     * does the set contain point p?
     *
     * @param p
     * @return
     */
    public boolean contains(Point2D p) {
        return search(p) != null;
    }

    private Point2D search(Point2D p) {
        if (p == null)
            return null;
        return search(root, p, true);
    }

    private Point2D search(Node node, Point2D p, boolean xCmp) {
        // base case
        if (node == null)
            return null;
        double cmp = compare(p, node.p, xCmp);
        if (cmp < 0) {
            // less go left or bottom
            return search(node.lb, p, !xCmp);
        }
        else if (cmp > 0) {
            // go right or top
            return search(node.rt, p, !xCmp);
        } else
            return node.p;
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

    private void levelOrderTraversal(Node x) {
        if (x == null)
            return;

        visit(x);
        levelOrderTraversal(x.lb);
        levelOrderTraversal(x.rt);
    }

    private void visit(Node x) {
//        drawNode(x, isEvenLevel(getLevel(root, x.p)));
        drawPoint(x);
    }

    private void displayRectangleDistance(Node x, Point2D p) {
        double distance = x.rect.distanceSquaredTo(p);
        StdOut.print("distance from" + " Bounding rect [" + x.rect.xmin() + "," + x.rect.ymin() + "]" + "," +
                "[" + x.rect.xmax() + "," + x.rect.ymax() + "]");
        StdOut.printf(" (splitting point %s) to %s is %2.6f, distance point  %2.6f\n", x.p, p, distance*10, x.p.distanceSquaredTo(p)*10.0);
        StdOut.printf("%s\n", (distance == 0 ? "Do not prune" : "Prune"));
    }

    private void drawNode(Node node, boolean xCmp) {
        if (xCmp)
            drawVLine(node);
        else
            drawHLine(node);
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
        levelOrderTraversal(root);
    }

    /**
     * all points that are inside the rectangle
     *
     * @param rect
     * @return
     */
    public Iterable<Point2D> range(RectHV rect) {
        Stack<Point2D> stack = new Stack<>();
        range(root, rect, stack);
        return stack;
    }

    private void range(Node node, RectHV rect, Stack<Point2D> stack) {
        // base case
        if (node == null)
            return;

        // if the point is contained, add it to the stack
        if (rect.contains(node.p))
            stack.push(node.p);

        if (rect.intersects(node.rect)) {
            range(node.lb, rect, stack);
            range(node.rt, rect, stack);
        }
    }

    /**
     *
     * a nearest neighbor in the set to point p;
     * null if the set is empty
     *
     * @param p
     * @return
     */
    public Point2D nearest(Point2D p) {
        if (isEmpty())
            return null;

        // set closest point to top point
        Point2D closest = root.p;
        return nearest(root, closest, p, true);
    }

    private Point2D nearest(Node node, Point2D closest, Point2D p, boolean xCmp) {

        // base case
        if (node == null)
            return closest;

        // distance from visiting node's rectangle to query point
        double distRectToP = node.rect.distanceSquaredTo(p);

        // distance from closest node so far to query point
        double distClosest = closest.distanceSquaredTo(p);

        // return if closest distance is less than rectangle distance
        if (distClosest < distRectToP)
            return closest;

        // creating a new closest so closest does not change
        Point2D newClosest = closest;

        // if this point is closer than replace closest
        if (node.p.distanceSquaredTo(p) < distClosest)
            newClosest = node.p;

        // go down subtree in the correct order depending on which side
        // the queried point is on
        if ((p.x() < node.p.x() && xCmp) || (p.y() < node.p.y() && !xCmp)) {
            newClosest = nearest(node.lb, newClosest, p, !xCmp);
            newClosest = nearest(node.rt, newClosest, p, !xCmp);
        } else {
            newClosest = nearest(node.rt, newClosest, p, !xCmp);
            newClosest = nearest(node.lb, newClosest, p, !xCmp);
        }
        return newClosest;
    }


}
