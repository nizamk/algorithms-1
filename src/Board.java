import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

public class Board {

    private int[] goalTiles;
    private int[] tiles;
    private int n;  // one-side dimension
    private int blankTileIndex;
    private int dimension;
    private int manhattan;
    private int hamming;
    private Queue<Board> boardsNeighbor = new Queue<>();

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
        int nBlankSquare = 0;
        dimension = n*n;

        tiles = new int[dimension];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (blocks[i][j] > (dimension - 1)) {
                    String s = String.format("Board should contain integers between 0 and %d", dimension - 1);
                    throw new IllegalArgumentException(s);
                }
                if (blocks[i][j] == 0) nBlankSquare++;
                tiles[xyTo1D(i, j)] = blocks[i][j];
            }
        }
        goalTiles = generateGoalTiles();
//        boardsNeighbor = generateNeighbors();
        blankTileIndex = indexOf(0);
        manhattan = manhattanDistance();
        hamming = hammingDistance();

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
    public int dimension() { return n * n; }

    /**
     * Number of blocks in wrong position
     *
     * Performance O(N^2)
     *
     * @return
     */
    public int hamming() {
        return hamming;
    }

    /**
     * sum of Manhattan distances between blocks and goal
     *
     * Performance: O(N^2)
     *
     * @return
     */
    public int manhattan() {
        return manhattan;
    }



    /**
     * is this board the goal board?
     *
     * Performance is O(N^2)
     *
     * @return
     */
    public boolean isGoal() { return Arrays.equals(tiles, goalTiles); }

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
        if (y instanceof Board) {
            Board other = (Board) y;
            return Arrays.equals(tiles, other.tiles);
        }
        return false;
    }

    /**
     * All neighboring boards
     *
     * @return
     */
    public Iterable<Board> neighbors() {
        // Generate adjacent boards: no of boards can be from 2 to 4
        // if blanktile at corner - 2 adjacent boards
        // if blanktile at one-sided boundary - 3 adjacent boards
        // otherwise 4 adjancent boards
        Board slideBelow = generateNeighborBoard(blankTileIndex, blankTileIndex > 2 ? blankTileIndex - 3 : -1);
        Board slideAbove = generateNeighborBoard(blankTileIndex, blankTileIndex < 6 ? blankTileIndex + 3 : -1);
        Board slideRight = generateNeighborBoard(blankTileIndex, blankTileIndex % 3 > 0 ? blankTileIndex - 1  : -1);
        Board slideLeft = generateNeighborBoard(blankTileIndex, blankTileIndex % 3 < 2 ? blankTileIndex + 1  : -1);
        if (slideBelow != null)
            boardsNeighbor.enqueue(slideBelow);
        if (slideAbove != null)
            boardsNeighbor.enqueue(slideAbove);
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
        for (int i = 0; i < n*n; i += 3) {
            s.append(String.format("%2d%2d%2d", tiles[i], tiles[i+1], tiles[i+2]));
            s.append("\n");
        }
        return s.toString();
    }

    // Helper
    private int xyTo1D(int row, int col) { return col + row * n; }

    // Helper
    //
    // NOTE:
    // map 1D i to 2D (x,y):
    // row = i / n  where n is one-side size
    // col = i % n
    private int manhattanDistance(int a, int b) {
        return Math.abs(a / n - b / n) + Math.abs(a % n - b % n);
    }

    // Helper
    private int indexOf(int val) {
        for (int i = 0; i < tiles.length; i++)
            if (tiles[i] == 0) return i;
        return -1;
    }

    // Helper
    private int[] generateGoalTiles() {
        goalTiles = new int[n * n];
        for (int i = 0; i < n * n - 1; i++)
            goalTiles[i] = i+1;
        return goalTiles;
    }

    // Helper
    private Board generateNeighborBoard(int blankTileIndex, int slideFromTileIndex) {
        // blank index at the boundary
        if (slideFromTileIndex == -1)
            return null;

        int[] newTiles = Arrays.copyOf(tiles, tiles.length);
        newTiles[blankTileIndex] = tiles[slideFromTileIndex];
        newTiles[slideFromTileIndex] = 0;
        return new Board(map1dTo2dTiles(newTiles));
    }

    // Helper
    private int[][] map1dTo2dTiles(int[] newTiles) {
        int[][] thisTiles = new int[n][n];
        for (int i = 0; i < n * n; i++)
            thisTiles[i / n][i % n] = newTiles[i];
        return thisTiles;
    }

    private int manhattanDistance() {
        int count = 0;
        int expect = 0;
        for (int i = 0; i < n*n; i++) {
            expect++;
            if (tiles[i] != 0 && tiles[i] != expect)
                count += manhattanDistance(i, tiles[i] - 1);
        }
        return count;
    }

    private int hammingDistance() {
        int count = 0;
        int expect = 0;
        for (int i = 0; i < n*n; i++) {
            expect++;
            if (tiles[i] != 0 && tiles[i] != expect)
                count++;
        }
        return count;
    }

    private Queue<Board> generateNeighbors() {
        // Generate adjacent boards: no of boards can be from 2 to 4
        // if blanktile at corner - 2 adjacent boards
        // if blanktile at one-sided boundary - 3 adjacent boards
        // otherwise 4 adjancent boards
        Board slideBelow = generateNeighborBoard(blankTileIndex, blankTileIndex > 2 ? blankTileIndex - 3 : -1);
        Board slideAbove = generateNeighborBoard(blankTileIndex, blankTileIndex < 6 ? blankTileIndex + 3 : -1);
        Board slideRight = generateNeighborBoard(blankTileIndex, blankTileIndex % 3 > 0 ? blankTileIndex - 1  : -1);
        Board slideLeft = generateNeighborBoard(blankTileIndex, blankTileIndex % 3 < 2 ? blankTileIndex + 1  : -1);
        if (slideBelow != null)
            boardsNeighbor.enqueue(slideBelow);
        if (slideAbove != null)
            boardsNeighbor.enqueue(slideAbove);
        if (slideRight != null)
            boardsNeighbor.enqueue(slideRight);
        if (slideLeft != null)
            boardsNeighbor.enqueue(slideLeft);
        return boardsNeighbor;
    }

    // tileAt(row,col) todo -  to remove before submission - this method only for visualizer
    public int tileAt(int row, int col) {
        return tiles[xyTo1D(row, col)];
    }


    /**
     * unit test
     *
     * @param args
     */
    public static void main(String[] args) {

//        int[][] errBoard = new int[][] {
//                { 5, 2, 7},
//                { 8, 4, 0},
//                { 1, 3, 10},
//        };
//        Board err = new Board(errBoard);
//        StdOut.println("manhanttan err: " + err.manhattan());

//        int[][] a = new int[][] {
//                { 0, 1, 3},
//                { 4, 2, 5},
//                { 7, 8, 6},
//        };

//        Board ab = new Board(a);
//        StdOut.println("ab board: \n" + ab);
//        StdOut.println("manhanttan ab: " + ab.manhattan());


        int[][] initial = new int[][] {
                { 8, 1, 3},
                { 4, 0, 2},
                { 7, 6, 5},
        };
        Board board = new Board(initial);
        StdOut.printf("initial manhattan %d, hamming %d \n", board.manhattan(), board.hamming());
        StdOut.println("initial board: \n" + board);
        StdOut.println("neighbors:");
        for (Board b : board.neighbors())
            StdOut.println(b);

//        int[][] initial = new int[][] {
//                { 0, 1, 3},
//                { 4, 2, 5},
//                { 7, 8, 6},
//        };
//        Board board = new Board(initial);
//        StdOut.printf("initial manhattan %d, hamming %d \n", board.manhattan(), board.hamming());
//        StdOut.println("initial board: \n" + board);
//        StdOut.println("neighbors:");
//        for (Board b : board.neighbors())
//            StdOut.println(b );

    }
}
