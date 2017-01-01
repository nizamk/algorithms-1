import edu.princeton.cs.algs4.Point2D;

public class KdTreeTest {

    public void test1() {
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

    public void test2() {
        KdTree tree = new KdTree();
        Point2D p = new Point2D(0.2,0.3 );
        tree.insert(p);
        p = new Point2D(0.1, 0.5);
        tree.insert(p);
        p = new Point2D(0.4, 0.2);
        tree.insert(p);
        p = new Point2D(0.4, 0.5);
        tree.insert(p);
        p = new Point2D(0.3, 0.3);
        tree.insert(p);
        p = new Point2D(0.4, 0.4);
        tree.insert(p);

        tree.draw();
    }

    public static void main(String[] args) {
        KdTreeTest test = new KdTreeTest();
        test.test2();
//        test.test1();
    }
}
