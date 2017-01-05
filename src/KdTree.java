import edu.princeton.cs.algs4.*;
import edu.princeton.cs.algs4.Point2D;

import java.awt.*;
import java.awt.geom.*;
import java.util.Iterator;

public class KdTree {

    // top of KD tree
    private Node root;

    private static class Node {
        private Point2D p;      // the point
        private RectHV rect;    // axis-aligned rectangle corresponding to this node
        private Node lb;        // left/bottom subtree
        private Node rt;        // right/top subtree
        private int count;

        public Node(Point2D point, RectHV boundingRect) {
            rect = boundingRect;
            p = point;
            count = 1;
        }
    }

    /**
     * construct an empty set of points
     */
    public KdTree() {
        root = null;
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
        return x.count;
    }

    /**
     * add the point to the set (if it is not already in the set)
     * @param p
     */
    public void insert(Point2D p) {
        root = insert(root, null, p, true);
    }

    private Node insert(Node node, Node prevNode, Point2D p, boolean xOrientation)
    {
        // Base case - create new node to be inserted
        if (node == null) {
            return (root != null) ?
                    new Node(p, getRect(prevNode, compare(p, prevNode.p, !xOrientation) < 0, !xOrientation)) :
                    new Node(p, new RectHV(0, 0, 1, 1));
        }

        double cmp = compare(p, node.p, xOrientation);
        if (cmp < 0)
            node.lb = insert(node.lb, node, p, !xOrientation);
        else if (cmp >= 0)
            node.rt = insert(node.rt, node, p, !xOrientation);

        // if equal then go right
        else {
            cmp = compare(p, node.p, !xOrientation);
            if (cmp == 0)
                node.p = p;
            else
                node.rt = insert(node.rt, node, p, !xOrientation);
        }
        node.count = 1 + size(node.lb) + size(node.rt);
        return node;
    }

    private RectHV getRect(Node node, boolean less, boolean xOrientation) {
        // comparing x's
        if (xOrientation) {
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

    private Point2D search(Node node, Point2D p, boolean xOrientation) {
        // base case
        if (node == null)
            return null;

        double cmp = compare(p, node.p, xOrientation);
        if (cmp < 0)
            return search(node.lb, p, !xOrientation);
        else if (cmp > 0)
            return search(node.rt, p, !xOrientation);
        // if equal then go right
        else {
            cmp = compare(p, node.p, !xOrientation);
            if (cmp == 0)
                return node.p;
            else
                return search(node.rt, p, !xOrientation);
        }
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
        drawNode(x, isEvenLevel(getLevel(root, x.p)));
        drawPoint(x);
    }

    private void displayRectangleDistance(Node x, Point2D p) {
        double distance = x.rect.distanceSquaredTo(p);
        StdOut.print("distance from" + " Bounding rect [" + x.rect.xmin() + "," + x.rect.ymin() + "]" + "," +
                "[" + x.rect.xmax() + "," + x.rect.ymax() + "]");
        StdOut.printf(" (splitting point %s) to %s is %2.6f, distance point  %2.6f\n", x.p, p, distance*10, x.p.distanceSquaredTo(p)*10.0);
        StdOut.printf("%s\n", (distance == 0 ? "Do not prune" : "Prune"));
    }

    private void drawNode(Node node, boolean xOrientation) {
        if (xOrientation)
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
        SET<Point2D> iter = new SET<>();
//        Stack<Point2D> stack = new Stack<>();
        range(root, rect, iter);
        return iter;
    }

    private void range(Node node, RectHV rect, SET<Point2D> coll) {
        // base case
        if (node == null)
            return;

        // if the point is contained, add it to the stack
        if (rect.contains(node.p))
            coll.add(node.p);

        if (rect.intersects(node.rect)) {
            range(node.lb, rect, coll);
            range(node.rt, rect, coll);
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
        Point2D closest= root.p;
        return nearest(root, closest, p, true);
    }

    private Point2D nearest(Node node, Point2D closest, Point2D query, boolean xOrientation)
    {
        // Base case
        if (node == null)
            return closest;

        // Important prune rule! - This is to ensure that we can quickly find the
        // query point without travelling unnecessarily to a subtree which won't
        // contain the query point. The observation is that if the closest distance
        // found so far is less than the node's rectangle distance, Then the query
        // point is outside the node's rectangle and hence the query point is already
        // farther away. So there is no need to explore the node's subtree any further
        double rectDistance = node.rect.distanceSquaredTo(query);
        double closestDistance = closest.distanceSquaredTo(query);
        if (closestDistance < rectDistance)
            return closest;

        // Choose a new champion if the node's distance is less than the current champion
        Point2D newClosest = (node.p.distanceSquaredTo(query) < closestDistance ? node.p : closest);

        // Go down subtree in the order of which side the query point is on. If query
        // point is on left or bottom of the current node, then traverse left or bottom
        // subtree first, followed by right or top subtree. If the query point on the
        // other side, then traverse in the reverse order
        if ((xOrientation && query.x() < node.p.x()) ||
                (!xOrientation && query.y() < node.p.y())) {
            newClosest = nearest(node.lb, newClosest, query, !xOrientation);
            newClosest = nearest(node.rt, newClosest, query, !xOrientation);
        } else {
            newClosest = nearest(node.rt, newClosest, query, !xOrientation);
            newClosest = nearest(node.lb, newClosest, query, !xOrientation);
        }
        return newClosest;
    }
}
