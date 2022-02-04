package um.es.parallel.api.parallelregion;

public class RegionRunnable implements Runnable {

	private int threadId;
	private int numbersOfThreads;
	private ParallelRegion parallelRegion;
	
	public RegionRunnable(int threadId, int numbersOfThreads, ParallelRegion parallelRegion) {
		this.threadId = threadId;
		this.numbersOfThreads = numbersOfThreads;
		this.parallelRegion = parallelRegion;
	}
	
	@Override
	public void run() {
		parallelRegion.execute(threadId, numbersOfThreads);
	}

}
