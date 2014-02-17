
public class Percolation {
	//create N-by-N grid, with all sites blocked
	private int size;						//Stores the initial percolation length	
	private QuickFindUF percUnion;			//Data structure that stores connected elements
	private boolean[][] percolationGrid;	//Array of open and closed elements
	
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
		this.size = N;
		this.percUnion = new QuickFindUF(N*N); 
	}
	//open site (row i, column j) if it is not already
	public void open(int i, int j)			
	{
		if (!percolationGrid[i][j])
		{
			percolationGrid[i][j] = true;
			int unionID = (i*(this.size) + j);
			//Union Northern Unit if it exists
			if (i > 0)
			{	
				if (this.isOpen(i - 1, j))
					percUnion.union(unionID, unionID-this.size);
			}
			//Connect Western Unit if it exists
			if (j > 0)
			{
				if (this.isOpen(i, j-1))
					percUnion.union(unionID, unionID-1);
			}
			//Connect Eastern Unit if it exists
			if (j < this.size-1)
			{
				if (this.isOpen(i, j+1))
					percUnion.union(unionID, unionID+1);
			}
			//Connect Southern Unit if it exists
			if (i < this.size-1)
			{
				if (this.isOpen(i + 1, j))
					percUnion.union(unionID, unionID + this.size);
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
		for (int k = 0; k < this.size; k++)
		{
			for (int j = 0; j < this.size; j++)
			{
				if (percUnion.connected(k, ((this.size-1)*this.size)+j))
					return true;
			}
		}
		return false;
	}
	
	private void printPercolation()
	{
		for (int i = 0; i < this.size; i++)
		{
			for (int j = 0; j < this.size; j++)
			{
				if (!this.percolationGrid[i][j])
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
		while (!this.percolates() || count < this.size)
		{
			boolean randOpen = false;
			while (!randOpen)
			{
				int randi = StdRandom.uniform(this.size);
				int randj = StdRandom.uniform(this.size);
				if (!this.isOpen(randi, randj))
				{
					this.open(randi, randj);
					//this.printPercolation();
					count++;
					randOpen = true;
				}
			}
		}
		StdOut.println("Monte Carlo Simulation Complete in: " + count);
		return (float) count/(this.size*this.size);
	}
	
};