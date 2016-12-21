import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;

public class Solver {

    MinPQ<SearchNode> minPQ = new MinPQ<>();

    class SearchNode {
        Board board;
        int n;  // total moves so far
        SearchNode prevNode;

        public SearchNode(Board board, int n, SearchNode prevNode) {
            this.board = board;
            this.n = n;
            this.prevNode = prevNode;
        }

        public int priority() {
            return n + board.manhattan();
        }

    }

    /**
     * find a solution to the initial board (using the A* algorithm)
     *
     * @param initial
     */
    public Solver(Board initial) {
        SearchNode start = new SearchNode(initial, 0, null);

    }

    /**
     * is the initial board solvable?
     *
     * @return
     */
    public boolean isSolvable() {
        // todo - isSolvable
        return false;
    }

    /**
     * min number of moves to solve initial board
     *
     * @return
     */
    public int moves() {
        // todo - moves()
        return 0;
    }

    /**
     * sequence of boards in a shortest solution
     *
     * @return
     */
    public Iterable<Board> solution() {
        // todo - solution()
        return new Iterable<Board>() {
            @Override
            public Iterator<Board> iterator() {
                return null;
            }
        };
    }

    // solve a slider puzzle (code given below)
    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}
