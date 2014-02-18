
public class Percolation {
	//create N-by-N grid, with all sites blocked
	private int N;							//Stores the initial percolation length	
	private QuickFindUF percUnion;			//Data structure that stores connected elements
	private boolean[][] percolationGrid;	//Array of open and closed elements
	private boolean[][] fillGrid;			//Array of filled elements
	
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
		N = N;
		percUnion = new QuickFindUF(N*N); 
	}
	//open site (row i, column j) if it is not already
	public void open(int i, int j)			
	{
		if (i <= 0 ||  i > N) throw new IndexOutOfBoundsException("row index i out of bounds");
		if (j <= 0 ||  j > N) throw new IndexOutOfBoundsException("row index i out of bounds");
		
		if (!percolationGrid[i][j])
		{
			percolationGrid[i][j] = true;
			int unionID = (i*(N) + j);
			//Union Northern Unit if it exists
			if (i > 0)
			{	
				if (isOpen(i - 1, j))
					percUnion.union(unionID, unionID-N);
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
		return false;
	}
	//does the system percolate?
	public boolean percolates()				
	{
		for (int k = 0; k < N; k++)
		{
			for (int j = 0; j < N; j++)
			{
				if (percUnion.connected(k, ((N-1)*N)+j))
					return true;
			}
		}
		return false;
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