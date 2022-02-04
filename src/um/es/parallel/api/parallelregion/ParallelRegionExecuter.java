package um.es.parallel.api.parallelregion;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ParallelRegionExecuter {
	
	public void run(int numberOfThreads, ParallelRegion parallelRegion) {
		ExecutorService newFixedThreadPool = Executors.newFixedThreadPool(numberOfThreads);
		
		for (int t = 0; t < numberOfThreads; t++) {
			newFixedThreadPool.submit(new RegionRunnable(t, numberOfThreads, parallelRegion));
		}		
		
		newFixedThreadPool.shutdown();
		try {
			newFixedThreadPool.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	
}
