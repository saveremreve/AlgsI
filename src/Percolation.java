
public class Percolation {
	//create N-by-N grid, with all sites blocked

	private int N;							//Stores the initial percolation length	
	private QuickFindUF percUnion;			//Data structure that stores connected elements
	private int topID;						//Virutal Union ID for top connected elements
	private int bottomID;					//Virtual Union ID for bottom connected elements

	private boolean[][] percolationGrid;	//Array of open and closed elements
	private boolean[][] fillGrid;			//Array of filled elements
	private int convertIndexToUnionID(int i, int j)
	{
		return (i*N) + j;
	}
	public Percolation(int N)				
	{
		//Initialize 2d Array to store blocked/open percolation sites
		percolationGrid = new boolean[N][N];
		for (int i = 0; i < N; i++)
		{
			for (int j = 0; j < N; j++)
			{
				percolationGrid[i][j] = false;
			}
		}
		this.N = N;
		topID = (N*N);
		bottomID = (N*N)+1;
		//Creates a new Union Find Data structure with 2 virtual elements for top and bottom
		percUnion = new QuickFindUF(N*N+2);	
	}
	//open site (row i, column j) if it is not already
	public void open(int i, int j)			
	{
		//if (i <= 0 ||  i > N) throw new IndexOutOfBoundsException("row index i out of bounds");
		//if (j <= 0 ||  j > N) throw new IndexOutOfBoundsException("row index i out of bounds");
		
		if (!percolationGrid[i][j])
		{
			percolationGrid[i][j] = true;
			int unionID = convertIndexToUnionID(i, j);
			//Update Fill Status with Connected to Virtual Top
			if (i == 0)
			{
				percUnion.union(unionID, topID);
			}
			//Update Fill Status with Connected to Virtual Top
			if (i == N-1)
			{
				percUnion.union(unionID, bottomID);
			}
			//Union Northern Unit if it exists
			if (i > 0)
			{	
				if (isOpen(i - 1, j))
					percUnion.union(unionID, unionID - N);
			}
			//Connect Western Unit if it exists
			if (j > 0)
			{
				if (isOpen(i, j-1))
					percUnion.union(unionID, unionID-1);
			}
			//Connect Eastern Unit if it exists
			if (j < N-1)
			{
				if (isOpen(i, j+1))
					percUnion.union(unionID, unionID+1);
			}
			//Connect Southern Unit if it exists
			if (i < N-1)
			{
				if (isOpen(i + 1, j))
					percUnion.union(unionID, unionID + N);
			}			

		}
		else
			return;
	}
	//is site (row i, column j) open?
	public boolean isOpen(int i, int j)		
	{
		return percolationGrid[i][j];
	}
	//is site (row i, column j) full?
	public boolean isFull(int i, int j)	
	{
		return percUnion.connected(convertIndexToUnionID(i,j), topID);
	}
	
	//does the system percolate?
	public boolean percolates()				
	{
		return percUnion.connected(topID, bottomID);
	}
	
	private void printPercolation()
	{
		for (int i = 0; i < N; i++)
		{
			for (int j = 0; j < N; j++)
			{
				if (!percolationGrid[i][j])
					StdOut.print("O");
				else
					StdOut.print("X");
			}
			StdOut.print('\n');
		}
	}
	
	public float monteCarloSimulation()
	{
		int count = 0;
		while (!percolates() || count < N)
		{
			boolean randOpen = false;
			while (!randOpen)
			{
				int randi = StdRandom.uniform(N);
				int randj = StdRandom.uniform(N);
				if (!isOpen(randi, randj))
				{
					open(randi, randj);
					//printPercolation();
					count++;
					randOpen = true;
				}
			}
		}
		StdOut.println("Monte Carlo Simulation Complete in: " + count);
		return (float) count/(N*N);
	}
	
};