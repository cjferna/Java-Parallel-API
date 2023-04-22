package um.es.parallel.api.core.section;

public class SectionRunnable implements Runnable {

	private int threadId;
	private int numbersOfThreads;
	private ParallelSection parallelSection;
	
	public SectionRunnable(int threadId, int numbersOfThreads, ParallelSection parallelSection) {
		this.threadId = threadId;
		this.numbersOfThreads = numbersOfThreads;
		this.parallelSection = parallelSection;
	}
	
	@Override
	public void run() {
		parallelSection.execute(threadId, numbersOfThreads);
	}

}
