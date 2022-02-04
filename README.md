# Simple parallel API for Java

A shared memory parallel API for Java with a close syntax to OpenMP. Developed to make it easier for students who only know Java to understand parallel programming before they start with OpenMP.

## Example 
```sh
parallelApi.parallel( t -> {
    System.out.println("Thread ID: " + t);
	parallelApi.single(() -> System.out.println("First arriving thread will execute"));		
	parallelApi.master(t, () -> System.out.println("Master thread execute));		
	
	parallelApi.ordered(t, () -> {
	    System.out.println("Executed as sequential code")
	    ...
	});	
	
	parallelApi.critical(t, () -> {
	    System.out.println("Only one thread at the same time")
	    ...
	});	
});
```