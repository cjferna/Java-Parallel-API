package um.es.parallel.api.core.parallelfor;

import java.util.function.Consumer;

public class PartialForRunnable implements Runnable {
	
	@SuppressWarnings("unused")
	private int threadId;
	private Consumer<Integer> threadingCode;
	private int start;
	private int end;
	
	public PartialForRunnable(int threadId, int start, int end, Consumer<Integer> threadingCode) {
		this.threadId = threadId;
		this.start = start;
		this.end = end;
		this.threadingCode = threadingCode;
	}

	@Override
	public void run() {		
		for (int i = start; i < end; i++) {
			threadingCode.accept(i);
		}
	}

}
