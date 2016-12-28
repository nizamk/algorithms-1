import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

import java.util.Comparator;
import java.util.Iterator;

/**
 * class Solver is immutable
 *
 */
public class Solver {

    private final boolean solvable;
    private final SearchNode goalNode;
    private final int moves;
    private final Comparator<SearchNode> comparator = new Comparator<SearchNode>() {
        @Override
        public int compare(SearchNode a, SearchNode b) {
            if (a.priorityManhattan() == b.priorityManhattan())
                return a.priorityHamming() - b.priorityHamming();
            else // break ties
                return a.priorityManhattan() - b.priorityManhattan();
        }
    };

    private class SearchNode {
        Board board;
        int moves;  // total moves so far
        SearchNode prevNode;
        int manhattanPriority, hammingPriority;

        SearchNode(Board b, int n, SearchNode previous) {
            board = b;
            moves = n;
            prevNode = previous;
            manhattanPriority = moves + b.manhattan();
            hammingPriority = moves + b.hamming();
        }

        SearchNode getPrevNode() { return prevNode; }
        int moves() { return moves; }
        int priorityManhattan() { return manhattanPriority; }
        int priorityHamming() { return hammingPriority; }
        public Board board() { return board; }
    };

    /**
     * find a solution to the initial board (using the A* algorithm)
     *
     * @param initial
     */
    public Solver(Board initial) {
        if (initial == null)
            throw new NullPointerException("Initial board is null.");
        solvable = (goalNode = runAStar(initial, initial.twin())) != null;
        moves = goalNode != null ? goalNode.moves() : -1;
    }

    // A* Algorithm - algorithm for minimum cost movement
    private SearchNode runAStar(Board initial, Board twin) {

        MinPQ<SearchNode> minPQ = new MinPQ<>(100, comparator);
        MinPQ<SearchNode> minTwinPQ = new MinPQ<>(100, comparator);

        // insert initial a node without parent to queue
        minPQ.insert(new SearchNode(initial, 0, null));
        minTwinPQ.insert(new SearchNode(twin, 0, null));

        // Main processing loop. Retrieve the next SearchNode instance from the queue.
        // Process minPQ for both initial puzzle and twin puzzle in lockstep (alternating)
        int moves = 0;
        while (moves < 1000 && !minPQ.isEmpty() && !minTwinPQ.isEmpty()) {
            // Make a next move by sliding a tile 1 step (Down,Up,Left,Right).
            // It's done by retrieving a node with least priority
            SearchNode parent = minPQ.delMin();
            SearchNode parentTwin = minTwinPQ.delMin();
            if (parent.board().isGoal()) {
                // Goal board found. It's solved.
                return parent;
            } else if (parentTwin.board().isGoal()) {
                StdOut.println("Twin goal board found; original puzzle is unsolvable.");
                // If twin solved the puzzle, it means the
                // original is unsolvable. So return null;
                return null;
            }
            // Select the next move by selecting a tile from blank tile neighbors. There
            // will be between 2 to 4 potential tiles to select depending on position of
            // the blank tile. This is done by inserting neighbour nodes to priority queue
            for (Board child : parent.board().neighbors()) {
                SearchNode node = new SearchNode(child, parent.moves() + 1, parent);
                SearchNode grandParent = parent.getPrevNode();
                if (grandParent == null || !node.board().equals(grandParent.board()))
                    minPQ.insert(node);
            }
            for (Board child : parentTwin.board().neighbors()) {
                SearchNode node = new SearchNode(child, parentTwin.moves() + 1, parentTwin);
                SearchNode grandParent = parentTwin.getPrevNode();
                if (grandParent == null || !node.board().equals(grandParent.board()))
                    minTwinPQ.insert(node);
            }
        }
        return null;
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
    public int moves() {
        return moves;
    }
    /**
     * sequence of boards in a shortest solution
     *
     * @return
     */
    public Iterable<Board> solution() {
        if (!solvable) return null;
        return push(goalNode, new Stack<Board>());
    }

    private Stack<Board> push(SearchNode node, Stack<Board> stack) {
        if (node == null) return stack;
        else {
            stack.push(node.board());
            return push(node.getPrevNode(), stack);
        }
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

