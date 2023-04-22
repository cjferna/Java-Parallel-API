package um.es.parallel.api.core.parallelfor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public class ParallelForExecuter {

	@SuppressWarnings("unused")
	private void forX(int i, Predicate<Integer> expression, Function<Integer, Integer> increment, Consumer<Integer> code) {
		while (expression.test(i)) {
			code.accept(i);
			i = increment.apply(i);
		}
		
	}

	public void repeatFromZero(int iterations, Consumer<Integer> code) {		
		int availableProcessors = Runtime.getRuntime().availableProcessors();	
		
		if (availableProcessors > iterations) {
			noThreadFor(iterations, code);			
		} else {
			threadedFor(iterations, code, availableProcessors);
		}
		
	}
	
	private void noThreadFor(int iterations, Consumer<Integer> code) {
		for (int i = 0; i < iterations; i++) {
			code.accept(i);
		}
	}

	private void threadedFor(int iterations, Consumer<Integer> code, int availableProcessors) {
		int starts[]  = new int[availableProcessors];   // Numero de comienzo de la iteracion
		int ends[]  = new int[availableProcessors];     // Numero de inicio de la iteracion
		
		calculateRanges(iterations, availableProcessors, starts, ends);		
			
		ExecutorService newFixedThreadPool = Executors.newFixedThreadPool(availableProcessors);
		for (int t = 0; t < availableProcessors; t++) {
			newFixedThreadPool.submit(new PartialForRunnable(t, starts[t], ends[t], code));
		}		
		newFixedThreadPool.shutdown();

		try {
			newFixedThreadPool.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private void calculateRanges(int iterations, int availableProcessors, int[] starts, int[] ends) {
		int counts[]  = new int[availableProcessors];	// Numero de iteraciones por thread
		
		starts[0] = 0;
		for (int i = 0; i < availableProcessors; i++) {
			// Se calcula el numero de iteraciones por thread como la division. Se le suma uno si hay resto.
			counts[i] = iterations / availableProcessors;
			if (i < iterations % availableProcessors) {
				counts[i]++;
			}
			
			// Se calcula el comienzo t el final de cada uno. 
			// El comienzo es el final del thread anterior. Y el final la suma del inicio más el trozo.
			if (i > 0) {
				starts[i] = ends[i - 1];
			}
			ends[i] = starts[i] + counts[i];
		}
	}
	
}
