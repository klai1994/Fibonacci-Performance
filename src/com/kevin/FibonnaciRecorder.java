package com.kevin;
import java.io.*;
import java.util.ArrayList;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.chart.ChartUtilities;

/**
 * 
 * @author Kevin Lai
 * 
 */

public class FibonnaciRecorder {

	static final int terms = 20;
	
	// First 10 terms of Fibonnaci sequence:

	/**
	 * The main method stores data from the Fibonacci methods as well as the times needed to execute them.
	 * It also will store the actual Fibonacci sequence to pass to the
	 * {@link #recordData(ArrayList, ArrayList, ArrayList)} method.
	 */
	public static void main(String[] args) {

		ArrayList<Long> iterativeTimes = new ArrayList<Long>();
		ArrayList<Long> recursiveTimes = new ArrayList<Long>();
		ArrayList<Integer> results = new ArrayList<Integer>();

		long startTime = 0, endTime = 0;
		
		for (int i = 0; i <= terms; i++) {	
			
			startTime = System.nanoTime();
			fibonacciIterative(i);
			endTime = System.nanoTime();	
			iterativeTimes.add(endTime - startTime);
			// System.out.println("Iterative result for " + i + " terms: "  + endTime - startTime);
	
			startTime = System.nanoTime();
			fibonacciRecursive(i);
			endTime = System.nanoTime();
			recursiveTimes.add(endTime - startTime);
			// System.out.println("Recursive result for " + i + " terms: "  + endTime - startTime);
			
			results.add(fibonacciIterative(i));
			
		}
		
		createChart(iterativeTimes, recursiveTimes);
		recordData(iterativeTimes, recursiveTimes, results);
		
	}
	
	/**
	 * Using this method exports a line chart to a .png file with results from the sequences. 
	 * The x axis represents the number of terms used, and the y axis represents time 
	 * taken to execute the method in nanoseconds.
	 * @param iterativeTimes The time data for the iterative method.
	 * @param recursiveTimes The time data for the recursive method.
	 */
	public static void createChart(ArrayList<Long> iterativeTimes, ArrayList<Long> recursiveTimes) {

		XYSeriesCollection seriesCollection =  new XYSeriesCollection();
		XYSeries iterativeSeries = new XYSeries("Iterative");
		XYSeries recursiveSeries = new XYSeries("Recursive");
		for (int i = 0; i <= terms; i++) {
			iterativeSeries.add(i, iterativeTimes.get(i));
			recursiveSeries.add(i, recursiveTimes.get(i));
		}
		seriesCollection.addSeries(iterativeSeries);
		seriesCollection.addSeries(recursiveSeries);
		XYDataset dataset = seriesCollection;
		
		JFreeChart chart = ChartFactory.createXYLineChart(
				"Performance of Fibonacci Methods", 
				"Terms", 
				"Times (nanoseconds)",
				dataset,
				PlotOrientation.VERTICAL,
				true,
				true, 
				false);
		
		int width = 1024;
		int height = 768;	
		try {
			ChartUtilities.saveChartAsPNG(new File("fib_chart.png"), chart, width, height);		
			} catch (IOException e) {
				System.out.println(e.getMessage());
		}
	}
	
	/**
	 * This method exports a text file with results from the sequence, with the Fibonacci sequence itself as refrence.
	 * @param iterativeTimes The time data for the iterative method.
	 * @param recursiveTimes The time data for the recursive method.
	 * @param results The Fibonacci sequence, recorded by using the iterative method.
	 */
	public static void recordData(ArrayList<Long> iterativeTimes, ArrayList<Long> recursiveTimes, ArrayList<Integer> results) {
		
		try {
			Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("data.txt"), "utf-8"));
			writer.write("Summary (total terms: " + terms + ")\n-----");
			for (int i = 0; i <= terms; i++) {
				writer.write("\nNumber of terms: " + i + 
						"\nResult: " + results.get(i) +
						"\nIterative time: " + iterativeTimes.get(i) + 
						"\nRecursive time: " + recursiveTimes.get(i) + 
						"\n");
			}
			writer.close();
			
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		
	}
	
	/**
	 * This is the iterative implementation of the Fibonacci sequence.
	 * @param n The number of terms to be used.
	 * @return Returns the nth number in the sequence.
	 */
	public static int fibonacciIterative(int n) {

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

	/**
	 * This is the recursive implementation of the Fibonacci sequence.
	 * @param n The number of terms to be used.
	 * @return Returns the nth number in the sequence.
	 */
	public static int fibonacciRecursive(int n) {

		if (n == 0 || n == 1)
			return n;
		return fibonacciRecursive(n - 1) + fibonacciRecursive(n - 2);

	}

}
