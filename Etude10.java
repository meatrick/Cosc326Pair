package Cosc326Pair;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;



public class Etude10 {
	final int JOB_SITE_MAX = 5;
	final int LAB_DESK_MAX = 25;
	final int JOB_NUMBER_MAX = 99;
	
	public class FileName {
		int job_site;
		int lab_desk;
		int job_number;
	}
	
	public static void main(String [] args) {
		String directory_name = args[0]; // directory name given from command line arguments
		
		File directory = new File(directory_name);
		
		try {
			Scanner ifile = new Scanner(directory);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		
		// open directory
		// read all file names in directory
		// for each sub-directory, open it and read all of the files
		// this needs to be recursive or DFS if there can be multiple subdirectories
		
		// having saved all file names recorded as FileName objects, created an ordered List
		// read each file in order, concatenating the file contents into result.txt
		// each file gets a new line
		
	}
}
