package um.es.parallel.api.core.section;

import um.es.parallel.api.core.interfaces.ThreadCuantifiedAndNamedFunction;
import um.es.parallel.api.core.interfaces.ThreadNamedFunction;
import um.es.parallel.api.core.interfaces.ThreadNomNamedFunction;

public class ParallelSection {

	private ThreadNomNamedFunction codeUmparametrized;
	private ThreadNamedFunction<Integer> codeSingleParametrized;
	private ThreadCuantifiedAndNamedFunction<Integer, Integer> codeDoubleParametrized;

	public ParallelSection(ThreadCuantifiedAndNamedFunction<Integer, Integer> codeDoubleParametrized) {
		this.codeDoubleParametrized = codeDoubleParametrized;
	}
	
	public ParallelSection(ThreadNamedFunction<Integer> codeSingleParametrized) {
		this.codeSingleParametrized = codeSingleParametrized;
	}	

	public ParallelSection(ThreadNomNamedFunction codeUmparametrized) {
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
