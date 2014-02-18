
public class PercolationStats {

	private double[] resultsArray;	//Stores Series of Simulation Results for Stat Analysis
	
	//perform T independent computational experiments on an N-by-N grid
	public PercolationStats(int N, int T)			
	{
		if (N <= 0) throw new IllegalArgumentException("N Must be larger than 0");
		if (T <= 0 ) throw new IllegalArgumentException("T Must be larger than 0");
		
		resultsArray = new double[T];
		for (int i = 0; i < T; i++)
		{
			Percolation testPercolation = new Percolation(N);
			resultsArray[i] = monteCarlo(testPercolation,N);
		} 
	}
	
	public double mean()
	{
		return StdStats.mean(resultsArray);
	}
	
	public double stddev()
	{
		return StdStats.stddev(resultsArray);
	}
	
	public double confidenceLo()
	{
		return this.mean() - ((1.96*this.stddev())/Math.sqrt(this.resultsArray.length));
	}
	
	public double confidenceHi()
	{
		return this.mean() + ((1.96*(this.stddev()))/Math.sqrt(this.resultsArray.length));
	}
	
	private float monteCarlo(Percolation p, int N)
	{
		int count = 0;
		while (!p.percolates() || count < N)
		{
			boolean randOpen = false;
			while (!randOpen)
			{
				int randi = StdRandom.uniform(1,N+1);
				int randj = StdRandom.uniform(1,N+1);
				if (!p.isOpen(randi, randj))
				{
					p.open(randi, randj);
					//printPercolation();
					count++;
					randOpen = true;
				}
			}
		}
		return (float) count/(N*N);
	}
	
	public static void main(String[] args) {
		int N = Integer.parseInt(args[0]);
		int T = Integer.parseInt(args[1]);
		
		Stopwatch sw = new Stopwatch();
		PercolationStats ps = new PercolationStats(N, T);

		//Print Results
		StdOut.println(args[0] + " " + args[1]);
		String formats = "%-24s %-2s";
		StdOut.printf(formats, "mean", "=");
		StdOut.print(ps.mean() + "\n");
		StdOut.printf(formats, "stddev", "=");
		StdOut.print(ps.stddev() + "\n");
		StdOut.printf(formats, "95% confidence interval", "=");
		StdOut.print(ps.confidenceLo() + ", " + ps.confidenceHi() + "\n");
		StdOut.println("Total Running Time: " + sw.elapsedTime());
	}
}
