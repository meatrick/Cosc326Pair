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
		long k = sc.nextInt();
		
		// System.err.println("n: " + Integer.toString(n) + "k: " + Integer.toString(k));
		
		
		// math:
		long numerator_start = n;
		long numerator_end = n-k+1;
		long denominator_start = k;
		long denominator_end = 1;

		/**
		 * Solution that I imagine is insufficient
		 */
		// long solution = 1;
		// for (long i = numerator_start; i >= numerator_end; i--) {
		// 	solution *= i;
		// }
		// for (long i = denominator_start; i >= denominator_end; i--) {
		// 	solution /= i;
		// }
		
		/**
		 * This solution is slower but decresases the likelihood of the integer overflowing
		 */
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

		/**
		 * Solution that isn't viable beacuse it uses doubles
		 */
		// double solution = 1;
		// boolean keepGoing = true, numerator_empty = false, denominator_empty = false;
		// long numerator = numerator_start, denominator = denominator_start;
		// while (keepGoing) {
		// 	if (!numerator_empty) {
		// 		// System.err.print("solution * numerator: " + solution + " * " + numerator);
		// 		solution *= numerator;
		// 		numerator--;
		// 		if (numerator < numerator_end) {
		// 			numerator_empty = true;
		// 		}
		// 		// System.err.println(" = " + solution);
		// 	}
		// 	if (!denominator_empty) {
		// 		// System.err.print("solution / denominator: " + solution + " / " + denominator);
		// 		solution /= denominator;
		// 		denominator--;
		// 		if (denominator < denominator_end) {
		// 			denominator_empty = true;
		// 		}
		// 		// System.err.println(" = " + solution);
		// 	}

		// 	// System.err.println("solution: " + solution);
		// 	keepGoing = (!numerator_empty || !denominator_empty);
		// }

		/**
		 * Alternative solution if the current solution is insufficient: incomplete
		 * 
		 */
		// Vector<Integer> partial_products = new Vector<Integer>();
		// partial_products.add(1);
		// long next_solution = 1;
		// for (long i = numerator_start; i >= numerator_end; i--) {
		// 	next_solution = partial_products.lastElement() * i;
		// 	if (next_solution < partial_products.lastElement()) {
		// 		// memory error: add new partial product
		// 		partial_products.add(i);
		// 	} else {
		// 		// set the last partial product to its new solution
		// 		partial_products.set(partial_products.size() - 1, next_solution);
		// 	}
		// }
		// for (long i = denominator_start; i >= denominator_end; i--) {
		// 	solution /= i;
		// }

		
		
		// print solution
		System.out.println(solution);
		
	}
	

}