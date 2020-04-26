import java.util.Scanner;

public class Etude04 {
	public static void main (String [] args) {
		// get input, define n and k
		int n;
		int k;
		
		
		// math:
		int numerator_start = n;
		int numerator_end = n-k+1;
		int denominator_start = k;
		int denominator_end = 1;
		
		double solution = 1;
		for (int i = numerator_start; i >= numerator_end; i--) {
			solution *= i;
		}
		for (int i = denominator_start; i >= denominator_end; i--) {
			solution /= i;
		}
		
		// print solution
		
	}
	

}