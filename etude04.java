import java.util.Scanner;
import java.util.Vector;
import java.lang.Math;
import java.util.Collections;

public class Etude04 {

	// public static Vector<Integer> BigMultiplication(int a, int b) {
	// 	Vector<Integer> a_digits = new Vector<Integer>();
	// 	Vector<Integer> b_digits = new Vector<Integer>();
	// 	Vector<Integer> solution = new Vector<Integer>();

	// 	while (a > 0) {
	// 		a_digits.add(a % 10);
	// 		a /= 10;
	// 	}
	// 	while (b > 0) {
	// 		b_digits.add(b * 10);
	// 		b /= 10;
	// 	}
	// 	// Collections.reverse(a_digits);
	// 	// Collections.reverse(b_digits);

	// 	int min_num_digits = Math.min(a_digits.size(), b_digits.size());

	// 	// smallest digit is at the front of each array
	// 	boolean carry = false;
	// 	for (int i = 0; i < min_num_digits; i++) {
			
			
	// 	}
	// }

	public static void main (String [] args) {
		// get input, define n and k
		Scanner sc = new Scanner(System.in);
		int n = sc.nextInt();
		int k = sc.nextInt();
		
		// System.err.println("n: " + Integer.toString(n) + "k: " + Integer.toString(k));
		
		
		// math:
		long numerator_start = n;
		long numerator_end = n-k+1;
		long denominator_start = k;
		long denominator_end = 1;

	
		
		/**
		 * This solution is slower but decresases the likelihood of the integer overflowing
		 */
		/*
		long solution = 1;
		Vector<Long> skips = new Vector<Long>();
		for (long i = numerator_start; i >= numerator_end; i--) {
			// System.err.print("solution * numerator: " + Long.toString(solution) + " * " + Long.toString(i));
			solution *= i;
			// System.err.println(" = " + Long.toString(solution));
			for (long j = denominator_start; j >= denominator_end; j--) {
				if (skips.contains(j)) continue;
				double check = (double) solution;
				if (check % j == 0) {
					// System.err.print(Long.toString(solution) + " is divisible by " + Long.toString(j));
					solution /= j;
					// System.err.println(" which equals " + Long.toString(solution));
					skips.add(j);
				}
			}
		}
		for (long i = denominator_start; i >= denominator_end; i--) {
			if (skips.contains(i)) continue;
			// System.err.print("solution / denom: " + Long.toString(solution) + " / " + Long.toString(i));
			solution /= i;
			// System.err.println(" = " + Long.toString(solution));
		}
		*/


		
		
		

		// n choose k is line n position k of pascal's triangle
		// create a 2d array representing each line of the triangle, computing each index by adding
		// from the previous line
		
		long[][] pascal = new long[n+1][n+1];

		pascal[0][0] = 1; // first row
		for (int row = 1; row < n+1; row++) { // skip first row
			for (int col = 0; col < row+1; col++) { // num of columns = row + 1

				// edge columns are always 1
				if (col == 0 || col == row) {
					pascal[row][col] = 1;
				} 
				else {
					pascal[row][col] = pascal[row-1][col-1] + pascal[row-1][col];
				}

			}
		}

		System.out.println(pascal[n][k]);

	}
	
	
	

}