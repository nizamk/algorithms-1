import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

public class Board {

    private final int[] tiles;
    private final int n;            // N - one-side dimension
    private final int blankTileIndex;
    private final int dimension;    // NxN grid
    private final int manhattan;
    private final int hamming;

    /**
     * construct a board from an N-by-N array of blocks
     * (where blocks[i][j] = block in row i, column j)
     *
     * Performance O(N^2)
     *
     * @param blocks
     */
    public Board(int[][] blocks) {
        if (blocks.length != blocks[0].length)
            throw new IllegalArgumentException("Row and column must be same.");

        n = blocks.length;
        dimension = n*n;

        tiles = new int[dimension];
        int k;
        int nBlankSquare = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (blocks[i][j] > (dimension - 1)) {
                    String s = String.format("Board should contain integers between 0 and %d", dimension - 1);
                    throw new IllegalArgumentException(s);
                }
                if (blocks[i][j] == 0) nBlankSquare++;
                // set initial tiles
                tiles[xyTo1D(i, j)] = blocks[i][j];
            }
        }
        blankTileIndex = indexOf(0);
        manhattan = calculateManhattanDistance();
        hamming = calculateHammingDistance();

        if (nBlankSquare == 0)
            throw new IllegalArgumentException("One block should be designated as blank square with value of 0.");
        if (nBlankSquare > 1)
            throw new IllegalArgumentException("Multiple blank squares are disallowed.");
    }

    /**
     * board dimension n
     *
     * Performance: O(1)
     * @return
     */
    public int dimension() { return n; }

    /**
     * Number of blocks in wrong position
     *
     * Performance O(1)
     *
     * @return
     */
    public int hamming() { return hamming; }

    /**
     * sum of Manhattan distances between blocks and goal
     *
     * Performance: O(N^2)
     *
     * @return
     */
    public int manhattan() { return manhattan; }


    /**
     * is this board the goal board?
     *
     * Performance is O(N^2)
     *
     * @return
     */
    public boolean isGoal() {
        return Arrays.equals(tiles, getGoalTiles());
    }

    private int[] getGoalTiles() {
        int[] goalTiles = new int[dimension];
        for (int i = 0; i < (dimension - 1); i++)
            goalTiles[i] = i + 1;
        return goalTiles;
    }
    /**
     * a board that is obtained by exchanging any pair of blocks
     *
     * @return
     */
    public Board twin() {
        Board board = new Board(map1dTo2dTiles(tiles));
        int k, l;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n - 1; j++) {
                k = xyTo1D(i, j);
                l = xyTo1D(i, j+1);
                if (tiles[k] != 0 && tiles[l] != 0) {
                    board.swap(i, j, i, j + 1);
                    return board;
                }
            }
        }
        return board;
    }

    private boolean swap(int i, int j, int it, int jt) {
        if (it < 0 || it >= n || jt < 0 || jt >= n)
            return false;
        int k = xyTo1D(i, j);
        int l = xyTo1D(it, jt);
        int temp = tiles[k];
        tiles[k] = tiles[l];
        tiles[l] = temp;
        return true;
    }


    /**
     * Does this board equal y?
     *
     * Performance O(N^2)
     *
     * @param y
     * @return
     */
    public boolean equals(Object y)    {
        if (this == y) return true;
        if (y == null) return false;
        if (y instanceof Board)
            return Arrays.equals(tiles, ((Board)y).tiles);
        return false;
    }

    /**
     * All neighboring boards
     *
     * @return
     */
    public Iterable<Board> neighbors() {
        Queue<Board> boardsNeighbor = new Queue<>();
        // Generate adjacent boards: no of boards can be from 2 to 4
        // blanktile at corner              - 2 adjacent boards
        // blanktile at one-sided boundary  - 3 adjacent boards
        // blanktile in other locations     - 4 adjancent boards
        Board slideDown = generateNeighborBoard(blankTileIndex, blankTileIndex > n - 1 ? blankTileIndex - n : -1);
        Board slideUp = generateNeighborBoard(blankTileIndex, blankTileIndex < n * (n - 1) ? blankTileIndex + n : -1);
        Board slideRight = generateNeighborBoard(blankTileIndex, blankTileIndex % n > 0 ? blankTileIndex - 1 : -1);
        Board slideLeft = generateNeighborBoard(blankTileIndex, blankTileIndex % n < n - 1 ? blankTileIndex + 1 : -1);
        if (slideDown != null)
            boardsNeighbor.enqueue(slideDown);
        if (slideUp != null)
            boardsNeighbor.enqueue(slideUp);
        if (slideRight != null)
            boardsNeighbor.enqueue(slideRight);
        if (slideLeft != null)
            boardsNeighbor.enqueue(slideLeft);
        return boardsNeighbor;
    }

    /**
     * string representation of the board (in the output format specified below)
     *
     * Performance O(N^2)
     *
     * @return
     */
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(n + "\n");
        for (int i = 0; i < dimension; i++) {
            if ((i > (n - 1)) && ((i % n) == 0))
                s.append("\n");
            s.append(String.format("%2d ",tiles[i]));
        }
        return s.toString();
    }

    private int xyTo1D(int row, int col) { return col + row * n; }

    // NOTE:
    // map 1D i to 2D (x,y):
    // row = i / n  where n is one-side size
    // col = i % n
    private int manhattanDistance(int a, int b) {
        return Math.abs(a / n - b / n) + Math.abs(a % n - b % n);
    }

    private int indexOf(int val) {
        for (int i = 0; i < tiles.length; i++)
            if (tiles[i] == 0) return i;
        return -1;
    }

    private Board generateNeighborBoard(int blankTileIndex, int slideFromTileIndex) {
        // blank index at the boundary
        if (slideFromTileIndex == -1) return null;
        int[] newTiles = Arrays.copyOf(tiles, tiles.length);
        newTiles[blankTileIndex] = tiles[slideFromTileIndex];
        newTiles[slideFromTileIndex] = 0;
        return new Board(map1dTo2dTiles(newTiles));
    }

    private int[][] map1dTo2dTiles(int[] newTiles) {
        int[][] thisTiles = new int[n][n];
        for (int i = 0; i < n * n; i++)
            thisTiles[i / n][i % n] = newTiles[i];
        return thisTiles;
    }

    private int calculateHammingDistance() {
        int expect = 0;
        int hammingDist = 0;
        for (int i = 0; i < dimension; i++) {
            expect++;
            if (tiles[i] != 0 && tiles[i] != expect)
                hammingDist++;
        }
        return hammingDist;
    }

    private int calculateManhattanDistance() {
        int expect = 0;
        int manhattanDist = 0;
        for (int i = 0; i < dimension; i++) {
            expect++;
            if (tiles[i] != 0 && tiles[i] != expect)
                manhattanDist += manhattanDistance(i, tiles[i] - 1);
        }
        return manhattanDist;
    }

// tileAt(row,col) to remove before submission - this method only for visualizer
//    public int tileAt(int row, int col) {
//        return tiles[xyTo1D(row, col)];
//    }


    /**
     * unit test
     *
     * @param args
     */
    public static void main(String[] args) {

//        int[][] initial = new int[][] {
//                { 8, 1, 3},
//                { 4, 0, 2},
//                { 7, 6, 5},
//        };
//        Board board = new Board(initial);
//        StdOut.println("initial board: \n" + board);
//        StdOut.println("neighbors:");
//        for (Board b : board.neighbors())
//            StdOut.println(b);

    }
}
