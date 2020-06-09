import java.util.Scanner;
import java.util.Vector;
import java.lang.Math;
import java.util.Collections;

public class Etude04 {

	public static void main (String [] args) {
		// get input, define n and k
		Scanner sc = new Scanner(System.in);
		int n = sc.nextInt();
		int k = sc.nextInt();
				
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