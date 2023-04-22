package um.es.parallel.api.main;

import um.es.parallel.api.core.ParallelApi;

public class MainProgram {

	public static void main(String[] args) {
		ParallelApi parallelApi = new ParallelApi();
		parallelApi.setMaximumNumberOfThreads(3);
		

		parallelApi.parallel((t, nt) -> {
			System.out.println("Hello from thread " + t +" out of " + nt);
		});
		
		
		Integer im = 0;
		
		parallelApi.parallel( t -> {
			System.out.println("Hello from thread " + im.intValue());
		});
		
		parallelApi.parallel( t -> {
		    System.out.println("ThreadID: " + t);
			parallelApi.single(() -> System.out.println("Hello i'm: " + t));		
			parallelApi.master(t, () -> System.out.println("Hello i'm: " + t));		
			
			parallelApi.ordered(t, () -> {
				System.out.println("Hello: 1");
				System.out.println("Hello: 2");
			});	
			
			parallelApi.critical(t, () -> {
				System.out.println("Hello: 1");
				System.out.println("Hello: 2");
			});	
		});
	
		parallelApi.repeat(8, i -> {
			System.out.print(i);
		});
		System.out.println();		
		parallelApi.forLess(0, 8, 
		i -> {
			System.out.print(i);
		});
		System.out.println();
		parallelApi.forLessOrEqual(0, 7,
		i -> {
			System.out.print(i);
		});
			
		int array[] = new int[400];
		parallelApi.parallelSections(
			parallelApi.section( (t, nt) -> {
				for (int i = 0; i < 100; i++) {
					array[i] = 1;
				}
			}),
			parallelApi.section( t -> {
				for (int i = 100; i < 200; i++) {
					array[i] = 2;
				}
			}),
			parallelApi.section( (t, nt) -> {
				for (int i = 200; i < 300; i++) {
					array[i] = 3;
				}
			}),
			parallelApi.section( () -> {
				for (int i = 300; i < array.length; i++) {
					array[i] = 4;
				}
			})
		);

		for (int i = 0; i < array.length; i++) {
			System.out.println("i: " + i + " - Value: " + array[i]);
		}
		
		
	}
	
}
