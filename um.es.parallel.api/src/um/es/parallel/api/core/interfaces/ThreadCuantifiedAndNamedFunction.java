package um.es.parallel.api.core.interfaces;

@FunctionalInterface
public interface ThreadCuantifiedAndNamedFunction<ThreadId, NumbersOfThreads>{
	public void run(ThreadId threadId, NumbersOfThreads numbersOfThreads);
}
