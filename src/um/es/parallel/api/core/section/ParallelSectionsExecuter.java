package um.es.parallel.api.core.section;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


public class ParallelSectionsExecuter {

	public void run(ParallelSection ...parallelSections) {
		int availableProcessors = Runtime.getRuntime().availableProcessors();	
		ExecutorService newFixedThreadPool = Executors.newFixedThreadPool(parallelSections.length);
		
		for (int t = 0; t < parallelSections.length; t++) {
			newFixedThreadPool.submit(new SectionRunnable(t, availableProcessors, parallelSections[t]));
		}		
		
		newFixedThreadPool.shutdown();
		try {
			newFixedThreadPool.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
