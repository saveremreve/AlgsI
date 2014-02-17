
public class PercolationStats {
	public PercolationStats (int N, int T)			//perform T independent computational experiments on an N-by-N grid
	{
		ResultsArray = new double[T];
		for(int i=0;i<T;i++)
		{
			Percolation testPercolation = new Percolation(N);
			ResultsArray[i] = testPercolation.MonteCarloSimulation();
		} 
	}
	
	private double[] ResultsArray;
	
	
	public double mean()
	{
		return StdStats.mean(ResultsArray);
	}
	
	public double stddev()
	{
		return StdStats.stddev(ResultsArray);
	}
	
	public double confidenceLo()
	{
		return this.mean() - ( (1.96*this.stddev())/Math.sqrt(this.ResultsArray.length) );
	}
	
	public double confidenceHi()
	{
		return this.mean() + ( (1.96*(this.stddev()))/Math.sqrt(this.ResultsArray.length));
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub'
		int percSize = Integer.parseInt(args[0]);
		int simCount = Integer.parseInt(args[1]);
		Stopwatch sw = new Stopwatch();
		PercolationStats ps = new PercolationStats(percSize,simCount);
		for(int i=0;i<simCount;i++)
		{
			StdOut.println(ps.ResultsArray[i]);
		}
		//Print Results
		StdOut.println(args[0] + " " + args[1]);
		String formats ="%-24s %-2s";// %7.24d\n";
		StdOut.printf(formats, "mean", "=");
		StdOut.print(ps.mean() + "\n");
		StdOut.printf(formats, "stddev", "=");
		StdOut.print(ps.stddev() + "\n");
		StdOut.printf(formats, "95% confidence interval", "=");
		StdOut.print(ps.confidenceLo() + ", " + ps.confidenceHi() + "\n");
		StdOut.println("Total Running Time: " + sw.elapsedTime());
	}
}
