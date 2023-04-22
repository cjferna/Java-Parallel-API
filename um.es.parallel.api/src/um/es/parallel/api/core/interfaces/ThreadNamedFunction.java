package um.es.parallel.api.core.interfaces;

@FunctionalInterface
public interface ThreadNamedFunction<ThreadId>{
	public void run(ThreadId threadId);
}
