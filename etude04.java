import java.util.Scanner;

public class Etude04 {

	public static void main (String [] args) {
		// get input, define n and k
		Scanner sc = new Scanner(System.in);
		int n = sc.nextInt();
		int k = sc.nextInt();
		sc.close();

		// handle special cases

		// n choose 0 or n choose n: only 1 way to do that
		if (k == 0 || k == n) {
			System.out.println(1);
			return;
		}

		// n choose 1 = n
		if (k == 1) {
			System.out.println(n);
			return;
		}

		// standard cases using pascal's triange

		long[][] pascal = new long[2][n+1];

		pascal[0][0] = 1; // first row
		for (int row = 1; row <= n+1; row++) { // skip first row
			pascal[row%2][0] = 0;
			for (int col = 1; col <= Math.min(n,k+1); col++) { // num of columns = row + 1

				pascal[row%2][col] = pascal[(row-1)%2][col-1] + pascal[(row-1)%2][col];

				if (col == Math.min(n, k+1) && row == n+1) {
					System.out.println(pascal[row%2][col]);
				}
			}
		}
	}	
}