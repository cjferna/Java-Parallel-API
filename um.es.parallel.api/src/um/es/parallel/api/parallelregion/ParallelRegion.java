package um.es.parallel.api.parallelregion;

import um.es.parallel.api.core.interfaces.ThreadCuantifiedAndNamedFunction;
import um.es.parallel.api.core.interfaces.ThreadNamedFunction;
import um.es.parallel.api.core.interfaces.ThreadNomNamedFunction;

public class ParallelRegion {

	private ThreadNomNamedFunction codeUmparametrized;
	private ThreadNamedFunction<Integer> codeSingleParametrized;
	private ThreadCuantifiedAndNamedFunction<Integer, Integer> codeDoubleParametrized;

	public ParallelRegion(ThreadCuantifiedAndNamedFunction<Integer, Integer> codeDoubleParametrized) {
		this.codeDoubleParametrized = codeDoubleParametrized;
	}
	
	public ParallelRegion(ThreadNamedFunction<Integer> codeSingleParametrized) {
		this.codeSingleParametrized = codeSingleParametrized;
	}	

	public ParallelRegion(ThreadNomNamedFunction codeUmparametrized) {
		this.codeUmparametrized = codeUmparametrized;
	}
	
	void execute(int threadId, int numbersOfThreads) {
		if (codeUmparametrized != null) {
			codeUmparametrized.run();
		} else if (codeSingleParametrized != null) {
			codeSingleParametrized.run(threadId);
		} else if (codeDoubleParametrized != null) {
			codeDoubleParametrized.run(threadId, numbersOfThreads);
		}
	}
	
}
