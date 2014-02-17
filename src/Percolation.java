
public class Percolation {
	public Percolation(int N)				//create N-by-N grid, with all sites blocked
	{
		//Initialize 2d Array to store blocked/open percolation sites
		PercolationGrid = new boolean[N][N];
		for(int i=0;i<N;i++)
		{
			for(int j=0;j<N;j++)
			{
				PercolationGrid[i][j] = false;
			}
		}
		this.Size = N;
		this.percUnion = new QuickFindUF(N*N); 
	}
	
	public void open(int i,int j)			//open site (row i, column j) if it is not already
	{
		if(PercolationGrid[i][j] == false)
		{
			PercolationGrid[i][j] = true;
			int UnionID = (i*(this.Size) + j);
			//Union Northern Unit if it exists
			if(i>0)
			{	
				if(this.isOpen(i-1,j))
					percUnion.union(UnionID, UnionID-this.Size);
			}
			//Connect Western Unit if it exists
			if(j>0)
			{
				if(this.isOpen(i,j-1))
					percUnion.union(UnionID, UnionID-1);
			}
			//Connect Eastern Unit if it exists
			if(j<this.Size-1)
			{
				if(this.isOpen(i,j+1))
					percUnion.union(UnionID, UnionID+1);
			}
			//Connect Southern Unit if it exists
			if(i<this.Size-1)
			{
				if(this.isOpen(i+1,j))
					percUnion.union(UnionID, UnionID+this.Size);
			}
		}
		else
			return;
	}
	
	public boolean isOpen(int i, int j)		//is site (row i, column j) open?
	{
		return PercolationGrid[i][j];
	}
	
	public boolean isFull (int i, int j)	//is site (row i, column j) full?
	{
		return false;}
	
	public boolean percolates()				//does the system percolate?
	{
		for(int k = 0; k < this.Size; k++)
		{
			for(int j = 0;j<this.Size; j++)
			{
				if(percUnion.connected(k, ((this.Size-1)*this.Size)+j))
					return true;
			}
		}
		return false;
	}
	
	private void printPercolation()
	{
		for(int i=0;i<this.Size;i++)
		{
			for(int j=0;j<this.Size;j++)
			{
				if(this.PercolationGrid[i][j] == false)
					StdOut.print("O");
				else
					StdOut.print("X");
			}
			StdOut.print('\n');
		}
	}
	
	public float MonteCarloSimulation()
	{
		int count = 0;
		while(!this.percolates() || count < this.Size)
		{
			boolean randOpen = false;
			while(randOpen == false)
			{
				int randi = StdRandom.uniform(this.Size);
				int randj = StdRandom.uniform(this.Size);
				if(!this.isOpen(randi,randj))
				{
					this.open(randi,randj);
					//this.printPercolation();
					count++;
					randOpen = true;
				}
			}
		}
		StdOut.println("Monte Carlo Simulation Complete in: " + count);
		return (float)count/(this.Size*this.Size);
	}
	
	private int Size;
	
	private QuickFindUF percUnion;
	
	private boolean[][] PercolationGrid;
};