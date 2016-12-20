import edu.princeton.cs.algs4.StdOut;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Board {

    private final int[][] goal = new int[][]{
                    {1, 2, 3},
                    {4, 5, 6},
                    {7, 8, 0}
    };

    private int[][] blocks;

    /**
     * construct a board from an N-by-N array of blocks
     * (where blocks[i][j] = block in row i, column j)
     *
     * @param blocks
     */
    public Board(int[][] blocks) {
//        for (int i = 0; i < blocks.length; i++) {
//            for (int j = 0; j < blocks[0].length; j++) {
//                this.blocks[i][j] = blocks[i][j];
//            }
//        }

        this.blocks = blocks;


    }

    /**
     * board dimension n
     *
     * @return
     */
    public int dimension() {
        return blocks.length * blocks[0].length;
    }

    /**
     * number of blocks out of place
     *
     * @return
     */
    public int hamming() {
        // todo - hamming
        // Note: need to cache the hamming value
        // for the same board
        return 0;
    }

    /**
     * sum of Manhattan distances between blocks and goal
     *
     * Performance: N^2
     *
     * @return
     */
    public int manhattan() {
        // todo - cache the manhattan value
        // for the same board
        int count = 0;
        int expect = 0;
        for (int i = 0; i < blocks.length; i++) {
            for (int j = 0; j < blocks[i].length; j++) {
                expect++;
                int value = blocks[i][j];
                if (value != 0 && value != expect) {
                    count += manhattan(i, getRow(value - 1), j, getCol(value - 1));
                }
            }
        }
        return count;
    }

    private int getCol(int value) { return value % blocks[0].length; }

    private int getRow(int value) { return value / blocks.length; }

    private int manhattan(int x0, int x1, int y0, int y1) {
        return Math.abs(x0 - x1) + Math.abs(y0 - y1);
    }

    /**
     * is this board the goal board?
     *
     * @return
     */
    public boolean isGoal() {
        int[][] goal = new int[][] {
                { 1, 2, 3},
                { 4, 5, 6},
                { 7, 8, 0},
        };

        for (int i = 0; i < blocks.length; i++) {
            for (int j = 0; j < blocks[0].length; j++) {
                if (blocks[i][j] != goal[i][j]) return false;
            }
        }
        return true;
    }

    /**
     * a board that is obtained by exchanging any pair of blocks
     *
     * @return
     */
    public Board twin() {
        // todo - twin()
        return new Board(new int[1][1]);
    }

    /**
     * does this board equal y?
     *
     * @param y
     * @return
     */
    public boolean equals(Object y)    {
        // todo - equals(Object y)
        return false;
    }

    /**
     * all neighboring boards
     *
     * @return
     */
    public Iterable<Board> neighbors() {
        // todo - neighbors()
        return new Iterable<Board>() {
            @Override
            public Iterator<Board> iterator() {
                return null;
            }
        };
    }

    /**
     * string representation of the board (in the output format specified below)
     *
     * @return
     */
    public String toString() {
        // todo - toString()
        return "Board";
    }

    /**
     * unit test
     *
     * @param args
     */
    public static void main(String[] args) {

        int[][] goal = new int[][] {
                { 1, 2, 3},
                { 4, 5, 6},
                { 7, 8, 0},
        };

        int[][] initial = new int[][] {
                { 8, 1, 3},
                { 4, 0, 2},
                { 7, 6, 5},
        };

        int[][] a = new int[][] {
                { 5, 2, 7},
                { 8, 4, 0},
                { 1, 3, 6},
        };

        Board goalBoard = new Board(goal);
        StdOut.println("manhanttan goal: " + goalBoard.manhattan());
        Board board = new Board(initial);
        StdOut.println("manhanttan initial: " + board.manhattan());
        Board ab = new Board(a);
        StdOut.println("manhanttan initial: " + ab.manhattan());
    }
}
