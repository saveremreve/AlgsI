public class Percolation {

    // Store initial percolation length
    private int N;
    // Store connected elements
    private WeightedQuickUnionUF percUnion;
    // Virtual Union ID for top connected elements
    private int topID;
    // Virtual Union ID for bottom connected elements
    private int bottomID;
    // Array of open and closed elements
    private boolean[][] percolationGrid; 

    // create N-by-N grid, with all sites blocked
    public Percolation(int N) {
        // Initialize 2d Array to store blocked/open percolation sites
        percolationGrid = new boolean[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                percolationGrid[i][j] = false;
            }
        }
        this.N = N;
        topID = (N * N);
        bottomID = (N * N) + 1;
        // Creates a new Union Find Data structure with 2 virtual elements for
        // top and bottom
        percUnion = new WeightedQuickUnionUF((N * N) + 2);
    }

    // open site (row i, column j) if it is not already
    public void open(int i, int j) {
        if (i <= 0 || i > N)
            throw new IndexOutOfBoundsException("row index i out of bounds");
        if (j <= 0 || j > N)
            throw new IndexOutOfBoundsException("row index j out of bounds");

        // Expected i & j values between (1,N), given values
        // i = i-1;
        // j = j-1;

        if (!percolationGrid[i - 1][j - 1]) {
            percolationGrid[i - 1][j - 1] = true;
            int unionID = convertIndexToUnionID(i, j);
            // Update Fill Status with Connected to Virtual Top
            if (i - 1 == 0) {
                percUnion.union(unionID, topID);
            }
            // Update Fill Status with Connected to Virtual Top
            if (i - 1 == N - 1) {
                percUnion.union(unionID, bottomID);
            }
            // Union Northern Unit if it exists
            if (i - 1 > 0) {
                if (isOpen(i - 1, j))
                    percUnion.union(unionID, unionID - N);
            }
            // Connect Western Unit if it exists
            if (j - 1 > 0) {
                if (isOpen(i, j - 1))
                    percUnion.union(unionID, unionID - 1);
            }
            // Connect Eastern Unit if it exists
            if (j - 1 < N - 1) {
                if (isOpen(i, j + 1))
                    percUnion.union(unionID, unionID + 1);
            }
            // Connect Southern Unit if it exists
            if (i - 1 < N - 1) {
                if (isOpen(i + 1, j))
                    percUnion.union(unionID, unionID + N);
            }

        } else
            return;
    }

    // is site (row i, column j) open?
    public boolean isOpen(int i, int j) {
        if (i <= 0 || i > N)
            throw new IndexOutOfBoundsException("row index i out of bounds");
        if (j <= 0 || j > N)
            throw new IndexOutOfBoundsException("row index j out of bounds");

        return percolationGrid[i - 1][j - 1];
    }

    // is site (row i, column j) full?
    public boolean isFull(int i, int j) {
        if (i <= 0 || i > N)
            throw new IndexOutOfBoundsException("row index i out of bounds");
        if (j <= 0 || j > N)
            throw new IndexOutOfBoundsException("row index j out of bounds");

        return percUnion.connected(convertIndexToUnionID(i, j), topID);
    }

    // does the system percolate?
    public boolean percolates() {
        return percUnion.connected(topID, bottomID);
    }
    
    private int convertIndexToUnionID(int i, int j) 
    {
        if (i <= 0 || i > N)
            throw new IndexOutOfBoundsException("row index i out of bounds");
        if (j <= 0 || j > N)
            throw new IndexOutOfBoundsException("row index j out of bounds");
        return ((i - 1) * N) + (j - 1);
    }

    private void printPercolation() {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (!percolationGrid[i][j])
                    StdOut.print("O");
                else
                    StdOut.print("X");
            }
            StdOut.print('\n');
        }
    }

    private void testOpen(int i, int j) {
        StdOut.println("Opened: " + i + ", " + j);
        this.open(i, j);
        this.printPercolation();
        StdOut.println("Percolates?: " + this.percolates());
    }

    public static void main(String[] args) {
        Percolation p = new Percolation(5);
        p.printPercolation();
        p.testOpen(2, 5);
        p.testOpen(3, 5);
        p.testOpen(4, 5);
        p.testOpen(5, 5);
        p.testOpen(1, 5);
    }
};