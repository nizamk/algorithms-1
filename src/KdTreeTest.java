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

    public void test3Circle10() {

        KdTree tree = new KdTree();
        Point2D p = new Point2D(0.206107,0.095492 );
        tree.insert(p);
        p = new Point2D(0.975528, 0.654508);
        tree.insert(p);
        p = new Point2D(0.024472, 0.345492);
        tree.insert(p);
        p = new Point2D(0.793893, 0.095492);
        tree.insert(p);
        p = new Point2D(0.793893, 0.904508);
        tree.insert(p);
        p = new Point2D(0.975528, 0.345492);
        tree.insert(p);
        p = new Point2D(0.206107, 0.904508);
        tree.insert(p);
        p = new Point2D(0.500000, 0.000000);
        tree.insert(p);
        p = new Point2D(0.024472, 0.654508);
        tree.insert(p);
        p = new Point2D(0.500000, 1.000000);
        tree.insert(p);

        tree.draw();
    }

    public static void main(String[] args) {
        KdTreeTest test = new KdTreeTest();
        test.test3Circle10();
//        test.test2();
//        test.test1();
    }
}
