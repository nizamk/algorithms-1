import java.util.Iterator;

public class Board {
    /**
     * construct a board from an N-by-N array of blocks
     * (where blocks[i][j] = block in row i, column j)
     *
     * @param blocks
     */
    public Board(int[][] blocks) {

    }

    /**
     * board dimension n
     *
     * @return
     */
    public int dimension() {
        return 0;
    }

    /**
     * number of blocks out of place
     *
     * @return
     */
    public int hamming() {
        return 0;
    }

    /**
     * sum of Manhattan distances between blocks and goal
     *
     * @return
     */
    public int manhattan() {
        return 0;
    }

    /**
     * is this board the goal board?
     *
     * @return
     */
    public boolean isGoal() {
        return false;
    }

    /**
     * a board that is obtained by exchanging any pair of blocks
     *
     * @return
     */
    public Board twin() {
        return new Board(new int[1][1]);
    }

    /**
     * does this board equal y?
     *
     * @param y
     * @return
     */
    public boolean equals(Object y)    {
        return false;
    }

    /**
     * all neighboring boards
     *
     * @return
     */
    public Iterable<Board> neighbors() {
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
        return "Board";
    }

    /**
     * unit test
     *
     * @param args
     */
    public static void main(String[] args) {

    }
}
