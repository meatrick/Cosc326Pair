import java.util.Scanner;
import java.util.ArrayList;

public class Etude02 { 
	public static String[] orderings = {"d/m/y", "d/y/m", "m/d/y", "m/y/d", "y/d/m", "y/m/d"};
	public static int[] daysPerMonth = {-1, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
	public static String[] monthToString = {"NULL", "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul",
			"Aug", "Sep", "Oct", "Nov", "Dec"};
	
	// has 3 ints that represent each part of the date separated by the slash
	public static class Date {
		int first;
		int second;
		int third;
		String input = "";
		ArrayList<String> errorMessages;
		boolean isValidSyntax;
		
		Date(int first, int second, int third) {
			this.first = first;
			this.second = second;
			this.third = third;

			this.isValidSyntax = true;
			
			errorMessages = new ArrayList<String>();

		}

		// Constructor for a Date with invalid syntax
		Date(String input) {
			this.input = input;
			this.isValidSyntax = false;
		}
	}
	
	// Input: A Date object and the desired ordering to be tested
	// Output: A String containing an error message to be displayed to the output for that given ordering.
	// If the string is empty, there is no error and the ordering for that date is valid.
	public static String testOrdering(Date date, int ordering) {		
		if (ordering == 0) {
			return method0(date.first, date.second, date.third);
		} else if (ordering == 1) {
			return method0(date.first, date.third, date.second);
		} else if (ordering == 2) {
			return method0(date.second, date.first, date.third);
		} else if (ordering == 3) {
			return method0(date.third, date.first, date.second);
		} else if (ordering == 4) {
			return method0(date.second, date.third, date.first);
		} else if (ordering == 5) {
			return method0(date.third, date.second, date.first);
		}
		
		return "invalid ordering number passed to testOrdering()";
	}
	
	// Input: the numbers from the date to treat as the day, month, and year
	// Output: A string containing an error message to be displayed in the output.
	// If the string is the empty string, the date is valid
	public static String method0(int day, int month, int year) {
		// convert single/double digit years to 1950-2049
		if (year < 100) {
			if (year <= 49) {
				year += 2000;
			} else if (year >= 50) {
				year += 1900;
			}
		}
		
		// check if year is in valid range
		if (year < 1753 || year > 3000) {
			return "Year out of range.";
		}
		
		// check if month is in valid range
		if (month > 12) {
			return "Month out of range.";
		}
		
		// check if day is in valid range for given month
		// special case for Feb
		if (month == 2) {
			if (isLeapYear(year)) {
				if (day > 29) {
					return "Day out of range for given month.";
				}
			} else {
				if (day > 28) {
					return "Day out of range for given month";
				}
			}
		} else {
			if (day > daysPerMonth[month]) {
				return "Day out of range for given month";
			}
		}
		
		return ""; // valid case
	}
	
	// Determines whether a year is a leap year
	public static boolean isLeapYear(int year) {
		if (year % 100 == 0 && year % 400 != 0) {
			return false;
		}
		if (year % 4 == 0) {
			return true;
		}
		return false;
	}
	
	public static String getOutput(Date date, int ordering) {
		// for invalid syntax cases, output is always the same, regardless of ordering
		if (!date.isValidSyntax) {
			return date.input + " - INVALID: invalid syntax";
		}


		int day = -1, month = -1, year = -1;
		
		if (ordering == 0) {
			day = date.first;
			month = date.second;
			year = date.third;
		} else if (ordering == 1) {
			day = date.first;
			month = date.third;
			year = date.second;
		} else if (ordering == 2) {
			day = date.second;
			month = date.first;
			year = date.third;
		} else if (ordering == 3) {
			day = date.third;
			month = date.first;
			year = date.second;
		} else if (ordering == 4) {
			day = date.second;
			month = date.third;
			year = date.first;
		} else if (ordering == 5) {
			day = date.third;
			month = date.second;
			year = date.first;
		}
		
		if (date.errorMessages.get(ordering) != "") {
			return date.input + " - INVALID: " + date.errorMessages.get(ordering);
		}
		
		// convert day to string
		String dayStr, monthStr, yearStr;
		if (day < 10) {
			dayStr = "0" + String.valueOf(day);
		} else {
			dayStr = String.valueOf(day);
		}
		
		// convert month to string
		monthStr = monthToString[month];
		
		// convert year to string
		if (year < 100) {
			if (year <= 49) {
				year += 2000;
			} else if (year >= 50) {
				year += 1900;
			}
		}
		yearStr = String.valueOf(year);
		
		
		return dayStr + " " + monthStr + " " + yearStr;
	}
	
	/**
	 * Helper function determines whether an input line has valid syntax
	 */
	public static boolean isValidSyntax(String line) {
		// count the number of '/' in the string
		int count = 0;
		for (int i = 0; i < line.length(); i++) {
			if (line.charAt(i) == '/') {
				count++;
			} 
		}

		// number of '/' must equal 2
		if (count != 2) {
			return false;
		}

		// ensure there are no empty parts of the input
		String[] parts = line.split("/");
		if (parts.length != 3) {
			return false;
		}
		for (String part : parts) {
			if (part.length() == 0) {
				return false;
			}
		}

		return true;

	}

	public static void main (String [] args) {
		// read input and store into objects
		ArrayList<Date> dates = new ArrayList<Date>();
		Scanner scan = new Scanner(System.in);
		String line = "";
		while (scan.hasNextLine()) {
			line = scan.nextLine();
	
			boolean valid_syntax = isValidSyntax(line);

			// if the syntax is invalid, add the date to the list as invalid
			if (!valid_syntax) {
				Date date = new Date(line); // initialized Date as invalid
				dates.add(date);
				continue;
			}
			
			String[] parts = line.split("/"); // splits the string into separate strings around the '/'

			// remove leading zeroes (05/10/99 --> 5/10/99)
			for (int i = 0; i < parts.length; i++) {
				if (parts[i].charAt(0) == '0') {
					parts[i] = parts[i].substring(1);
				}
			}
			Date date = new Date(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]), Integer.parseInt(parts[2]));
			date.input = line;
			dates.add(date);
		}
		
		// attempt all 6 orderings for each date
		// each index represents the number of errors each ordering produces
		ArrayList<Integer> numberOfErrors = new ArrayList<Integer>();
		for (int i = 0; i < 6; i++) {
			numberOfErrors.add(0);
			for (Date date : dates) {
				// skip dates if they are invalid syntax
				if (!date.isValidSyntax) {
					continue;
				}
				String errorMsg = testOrdering(date, i);
				date.errorMessages.add(errorMsg); // add to Date object for printing later
				if (errorMsg != "") {
					// increment the number of errors for this ordering by 1
					numberOfErrors.set(i, numberOfErrors.get(i) + 1); 
				}
			}
		}
		
		// determine which ordering produced the fewest errors
		int bestOrdering = -1;
		int bestOrderingNumErrors = Integer.MAX_VALUE;
		for (int i = 0; i < numberOfErrors.size(); i++) {
			if (numberOfErrors.get(i) < bestOrderingNumErrors) {
				bestOrdering = i;
				bestOrderingNumErrors = numberOfErrors.get(i); 
			}
		}
		
		// print the ordering with the fewest errors, according to output format requirements
		for (Date date : dates) {
			String output = getOutput(date, bestOrdering);
			System.out.println(output);
		}		
	}
}