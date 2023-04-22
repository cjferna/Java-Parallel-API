package um.es.parallel.api.core;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.Callable;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

import um.es.parallel.api.core.interfaces.ThreadCuantifiedAndNamedFunction;
import um.es.parallel.api.core.interfaces.ThreadNamedFunction;
import um.es.parallel.api.core.interfaces.ThreadNomNamedFunction;
import um.es.parallel.api.core.parallelfor.ParallelForExecuter;
import um.es.parallel.api.core.section.ParallelSection;
import um.es.parallel.api.core.section.ParallelSectionsExecuter;
import um.es.parallel.api.parallelregion.ParallelRegion;
import um.es.parallel.api.parallelregion.ParallelRegionExecuter;

public class ParallelApi {
	
	private ParallelForExecuter parallelForExecuter;
	private ParallelSectionsExecuter parallelSectionsExecuter;
	private ParallelRegionExecuter parallelRegionExecuter;
	private int numberOfThreads;
	private AtomicBoolean singleExecuted;
	private AtomicBoolean orderedExecuting;
	int numberOfSingleExecuted;
	int numberOfOrderedExecuted;
	private CyclicBarrier cyclicBarrier;

	public ParallelApi() {
		numberOfThreads = Runtime.getRuntime().availableProcessors();
		parallelForExecuter = new ParallelForExecuter();
		parallelSectionsExecuter = new ParallelSectionsExecuter();
		parallelRegionExecuter = new ParallelRegionExecuter();
		singleExecuted = new AtomicBoolean(false);
		orderedExecuting = new AtomicBoolean(false);
	}
	
	/* Parallel For */
	public void forLess(int start, int end, Consumer<Integer> code) {
		cyclicBarrier = new CyclicBarrier(numberOfThreads);
		parallelForExecuter.repeatFromZero(end - start, code);
	}
	
	public void forLessOrEqual(int start, int end, Consumer<Integer> code) {
		cyclicBarrier = new CyclicBarrier(numberOfThreads);
		parallelForExecuter.repeatFromZero(end - start + 1, code);
	}

	public void repeat(int iterations, Consumer<Integer> code) {
		cyclicBarrier = new CyclicBarrier(numberOfThreads);
		parallelForExecuter.repeatFromZero(iterations, code);
	}
	
	/* Parallel Sections */
	public void parallelSections(ParallelSection ...parallelSections) {
		cyclicBarrier = new CyclicBarrier(numberOfThreads);
		parallelSectionsExecuter.run(parallelSections);
	}
	
	public ParallelSection section(ThreadNomNamedFunction codeUmparametrized) {
		return new ParallelSection(codeUmparametrized);
	}

	public ParallelSection section(ThreadNamedFunction<Integer> codeSingleParametrized) {
		return new ParallelSection(codeSingleParametrized);
	}	

	public ParallelSection section(ThreadCuantifiedAndNamedFunction<Integer, Integer> codeDoubleParametrized) {
		return new ParallelSection(codeDoubleParametrized);
	}	
	
	/* Parallel Region */
	public void parallel(ThreadNomNamedFunction codeUmparametrized) {
		ParallelRegion parallelRegion = new ParallelRegion(codeUmparametrized);
		cyclicBarrier = new CyclicBarrier(numberOfThreads);
		parallelRegionExecuter.run(numberOfThreads, parallelRegion);
		singleExecuted.set(false);
	}

	public void parallel(ThreadNamedFunction<Integer> codeSingleParametrized) {
		ParallelRegion parallelRegion = new ParallelRegion(codeSingleParametrized);
		cyclicBarrier = new CyclicBarrier(numberOfThreads);
		parallelRegionExecuter.run(numberOfThreads, parallelRegion);		
		singleExecuted.set(false);
	}

	public void parallel(ThreadCuantifiedAndNamedFunction<Integer, Integer> codeDoubleParametrized) {
		ParallelRegion parallelRegion = new ParallelRegion(codeDoubleParametrized);
		cyclicBarrier = new CyclicBarrier(numberOfThreads);
		parallelRegionExecuter.run(numberOfThreads, parallelRegion);
		singleExecuted.set(false);
	}
	
	/* Task */
	public <T> Future<T> task(ThreadNomNamedFunction codeUmparametrized) {
		ExecutorService newSingleThreadExecutor = Executors.newSingleThreadExecutor();
		return newSingleThreadExecutor.submit(new Callable<T>() {

			@Override
			public T call() throws Exception {
				codeUmparametrized.run();
				return null;
			}});
	}
	
	
	/* Master */
	public void master(Integer t, ThreadNomNamedFunction codeUmparametrized) {
		if (t == 0) {
			codeUmparametrized.run();
		}
	}

	public synchronized void single(ThreadNomNamedFunction codeUmparametrized) {
		if (!singleExecuted.getAndSet(true)) {
			codeUmparametrized.run();
		}
		
		numberOfSingleExecuted++;
		if (numberOfSingleExecuted == numberOfThreads) {
			singleExecuted.set(false);
			numberOfSingleExecuted = 0;
		}
	}

	public synchronized void critical(Integer t, ThreadNomNamedFunction codeUmparametrized) {
		codeUmparametrized.run();
	}


	public void ordered(Integer t, ThreadNomNamedFunction codeUmparametrized) {
		if (!orderedExecuting.getAndSet(true)) {
			codeUmparametrized.run();
		}
		
		numberOfOrderedExecuted++;
		if (numberOfOrderedExecuted == numberOfThreads) {
			orderedExecuting.set(false);
			numberOfOrderedExecuted = 0;
		}
	}

	public void barrier() {
		try {
			cyclicBarrier.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (BrokenBarrierException e) {
			e.printStackTrace();
		}
	}
	
	/* Maximum Number of Threads */	
	public void setMaximumNumberOfThreads(int numberOfThreads) {
		this.numberOfThreads = numberOfThreads;		
	}
	
	public int getMaximumNumberOfThreads() {
		return numberOfThreads;
	}
	
	public void resetMaximumNumberOfThreads() {
		numberOfThreads = Runtime.getRuntime().availableProcessors();
	}



}
