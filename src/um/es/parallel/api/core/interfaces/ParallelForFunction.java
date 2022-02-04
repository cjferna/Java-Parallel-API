package um.es.parallel.api.core.interfaces;

@FunctionalInterface
public interface ParallelForFunction<ThreadId, NumbersOfThreads, Int>{
	public void run(ThreadId threadId, NumbersOfThreads numbersOfThreads, Int number);
}