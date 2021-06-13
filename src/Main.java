import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;

public class Main {

	static final int terms = 30;
	
	// First 10 terms of Fibonnaci sequence:
	// 0 1 1 2 3 5 8 13 21 34
	public static void main(String[] args) {

		ArrayList<Long> iterativeTimes = new ArrayList<Long>();
		ArrayList<Long> recursiveTimes = new ArrayList<Long>();
		ArrayList<Integer> results = new ArrayList<Integer>();

		long startTime = 0, endTime = 0, totalTime = 0;
		
		for (int i = 0; i <= terms; i++) {	
			
			startTime = System.nanoTime();
			fibonacciIterative(i);
			endTime = System.nanoTime();
			totalTime = endTime - startTime;		
			iterativeTimes.add(totalTime);
			System.out.println("Iterative result for " + i + " terms: "  + totalTime);
	
			startTime = System.nanoTime();
			fibonacciRecursive(i);
			endTime = System.nanoTime();
			totalTime = endTime - startTime;
			recursiveTimes.add(totalTime);
			System.out.println("Recursive result for " + i + " terms: "  + totalTime);
			
			results.add(fibonacciIterative(i));
			
		}
		recordData(iterativeTimes, recursiveTimes, results);
		
	}

	static void recordData(ArrayList<Long> iterativeTimes, ArrayList<Long> recursiveTimes, ArrayList<Integer> results) {
		
		try {
			Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("data.txt"), "utf-8"));
			writer.write("Summary (total terms: " + terms + ")\n-----");
			for (int i = 0; i <= terms; i++) {
				writer.write("\nNumber of terms: " + i + 
						"\nIterative time: " + iterativeTimes.get(i) + 
						"\nRecursive time: " + recursiveTimes.get(i) + 
						"\nResult: " + results.get(i) +
						"\n");
			}
			writer.close();
			
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		
	}
	
	static int fibonacciIterative(int n) {

		if (n == 0 || n == 1)
			return n;

		int first = 0, second = 1, result = 1;

		for (int i = 1; i < n; i++) {
			result = first + second;
			first = second;
			second = result;
		}
		return result;
	}

	static int fibonacciRecursive(int n) {

		if (n == 0 || n == 1)
			return n;
		return fibonacciRecursive(n - 1) + fibonacciRecursive(n - 2);

	}

}
