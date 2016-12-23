import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

import java.util.Comparator;
import java.util.Iterator;

public class Solver {

    private boolean solvable;
    private SearchNode goalNode;

    private MinPQ<SearchNode> minPQ = new MinPQ<>(100, new Comparator<SearchNode>() {
        @Override
        public int compare(SearchNode a, SearchNode b) {
            return a.priorityManhattan() - b.priorityManhattan() ;
        }
    });

    class SearchNode {
        Board board;
        int moves;  // total moves so far
        SearchNode prevNode;
        int priority;

        SearchNode(Board b, int n, SearchNode previous) {
            board = b;
            moves = n;
            prevNode = previous;
            priority = moves + b.manhattan();
        }

        SearchNode getPrevNode() { return prevNode; }
        int moves() { return moves; }
        int priorityManhattan() { return priority; }
        public Board board() { return board; }
    };

    /**
     * find a solution to the initial board (using the A* algorithm)
     *
     * @param initial
     */
    public Solver(Board initial) {

        solvable = runAStarAlgorithm(initial);
        if (solvable) {
            StdOut.println(initial);
            StdOut.println("This board is solvable with " + moves() + " moves.");
        }
//        solvable = runAStarAlgorithm(initial.twin());
    }

    private boolean runAStarAlgorithm(Board initial) {
        minPQ.insert(new SearchNode(initial, 0, null));
        while (!minPQ.isEmpty()) {

            // retrieve node with least/minimum priority
            SearchNode parent = minPQ.delMin();

            // It's is solved.
            if (parent.board().isGoal()) {
                goalNode = parent;
                return true;
            }

            // insert neighbour nodes to priority queue
            for (Board child : parent.board().neighbors()) {
                SearchNode node = new SearchNode(child, parent.moves() + 1, parent);
                SearchNode grandParent = parent.getPrevNode();
                if (grandParent == null || !node.board().equals(grandParent.board()))
                    minPQ.insert(node);
            }
        }
        return false;
    }

    /**
     * is the initial board solvable?
     *
     * @return
     */
    public boolean isSolvable() {
        return solvable;
    }

    /**
     * min number of moves to solve initial board
     *
     * @return
     */
    public int moves() { return goalNode.moves(); }

    /**
     * sequence of boards in a shortest solution
     *
     * @return
     */
    public Iterable<Board> solution() {
        Stack<Board> stack = new Stack<>();
        for (SearchNode node = goalNode; node != null; node = node.getPrevNode())
            stack.push(node.board());
        return stack;
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

        // solve the puzzle
        Board initial = new Board(blocks);
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

