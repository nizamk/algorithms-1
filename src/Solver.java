import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

import java.util.Comparator;
import java.util.Iterator;

public class Solver {

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
        minPQ.insert(new SearchNode(initial, 0, null));
        while (!minPQ.isEmpty()) {
            SearchNode parent = minPQ.delMin();
            if (parent.board().isGoal()) {
                StdOut.println("Found solution");
                goalNode = parent;
                break;
            }

            // insert neighbour nodes to priority queue
            for (Board child : parent.board().neighbors()) {
                SearchNode node = new SearchNode(child, parent.moves() + 1, parent);
                SearchNode grandParent = parent.getPrevNode();
                if (grandParent == null)
                    minPQ.insert(node);
                else {
//                    Board a = node.board();
//                    Board b = grandParent.board();
                    if (!node.board().equals(grandParent.board()))
                        minPQ.insert(node);
                }
            }
        }
    }

    /**
     * is the initial board solvable?
     *
     * @return
     */
    public boolean isSolvable() {
        // todo - isSolvable()
        return true;
    }

    /**
     * min number of moves to solve initial board
     *
     * @return
     */
    public int moves() {
        return goalNode.moves();
    }

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

