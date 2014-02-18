
public class PercolationStats {
	
	private double[] resultsArray;	//Stores Series of Simulation Results for Stat Analysis
	
	//perform T independent computational experiments on an N-by-N grid
	public PercolationStats(int N, int T)			
	{
		resultsArray = new double[T];
		for (int i = 0; i < T; i++)
		{
			Percolation testPercolation = new Percolation(N);
			resultsArray[i] = testPercolation.monteCarloSimulation();
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
	
	public static void main(String[] args) {
		int N = Integer.parseInt(args[0]);
		int T = Integer.parseInt(args[1]);
		
		Stopwatch sw = new Stopwatch();
		PercolationStats ps = new PercolationStats(N, T);
		for (int i = 0; i < T; i++)
		{
			StdOut.println(ps.resultsArray[i]);
		}
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
