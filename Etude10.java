import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.io.*;


public class Etude10 {
	final static int JOB_SITE_MAX = 5;
	final static int LAB_DESK_MAX = 25;
	final static int JOB_NUMBER_MAX = 99;
	final static char[] ACCEPTABLE_SEPARATORS = {'-', '_', '/', ' '};
	
	public static class SortFiles implements Comparator<OrderedFile> {
		public int compareTo(int a, int b) {
			if (a == b) return 0;
			if (a > b) return 1;
			else return -1;
		}

		public int compare(OrderedFile a, OrderedFile b) {
			int job_site_compare = compareTo(a.get_job_site(), b.get_job_site());
			int lab_desk_compare = compareTo(a.get_lab_desk(), b.get_lab_desk());
			int job_number_compare = compareTo(a.get_job_number(), b.get_job_number());

			if (job_site_compare == 0) {
				if (lab_desk_compare == 0) {
					if (job_number_compare == 0) {
						// System.err.println("matching files");
					} else {
						return job_number_compare;
					}
				} else {
					return lab_desk_compare;
				}
			} 
			return job_site_compare;
		}
	}

	public static class OrderedFile extends File {
		int job_site;
		int lab_desk;
		int job_number;
		String original_path;

		public OrderedFile(String path_name) {
			super(path_name);

			original_path = getAbsolutePath();
			// System.err.println("original absolute path: " + original_path);
			
			String name = this.getName();
			// System.err.print("name: " + getName());
			
			
			// forget about separators, just get the numbers 2 by 2
			ArrayList<Character> nums = new ArrayList<Character>();
			for (int i = 0; i < name.length(); i++) {
				char c = name.charAt(i);
				if (c >= 48 && c <= 57) { // if char is a number
					nums.add(c);
					// System.err.println("Adding: " + c);
				}
			}
			if (nums.size() != 6) {
				// big error
			} else {
				String job_site_str = Character.toString(nums.get(0));
				job_site_str += Character.toString(nums.get(1));
				String lab_desk_str = Character.toString(nums.get(2));
				lab_desk_str += Character.toString(nums.get(3));
				String job_number_str = Character.toString(nums.get(4));
				job_number_str += Character.toString(nums.get(5));
				
				job_site = Integer.valueOf(job_site_str);
				lab_desk = Integer.valueOf(lab_desk_str);
				job_number = Integer.valueOf(job_number_str);

				// System.err.println(": " + job_site_str + " " + lab_desk_str + " " + job_number_str);
			}
			
		}

		public String get_original_path() {
			return original_path;
		}
		public int get_job_site() {
			return job_site;
		}
		public int get_lab_desk() {
			return lab_desk;
		}
		public int get_job_number() {
			return job_number;
		}

		public boolean isValid() {
			int pos = this.getName().indexOf(".txt");
			if (pos == -1) {
				return false; // filename does not contain .txt
			}	
			if (job_site < 1 || job_site > 5) {
				return false;
			}
			if (lab_desk < 1 || lab_desk > 25) {
				return false;
			}
			if (job_number < 1 || job_number > 99) {
				return false;
			}

			return true;
		}
	}
	
	/**
	 *  Returns a list of all of the files in the system
	 */
	public static ArrayList<File> file_finder(File start_directory) {
		File[] files = start_directory.listFiles();
		ArrayList<File> all_files = new ArrayList<File>();
		
		for (File file : files) {
			if (file.isFile()) {
				all_files.add(file);
				// System.err.println(file.getAbsolutePath());
			} else if (file.isDirectory()) {
				ArrayList<File> sub_files = file_finder(file);
				all_files.addAll(sub_files);
			}
		}
		return all_files;
	}

	public static void main(String [] args) {
		String directory_name = "";
		try {
			directory_name = args[0]; // directory name given from command line arguments
		} catch (ArrayIndexOutOfBoundsException e) {
			System.err.println("no command line arg given.");
			return;
		}
		
		File directory = new File(directory_name);


		ArrayList<File> files = file_finder(directory);

		// convert all files into OrderedFile objects
		ArrayList<OrderedFile> ordered_files = new ArrayList<OrderedFile>();
		for (File file : files) {
			
			// check if the file has the valid syntax
			// String[] split = file.getName().split("-");
			// int pos = file.getName().indexOf(".txt");
			// if (pos == -1 || split.length != 3) {
			// 	System.err.println("invalid file name");
			// 	continue; // invalid file name, skip this file
			// }

			OrderedFile ordered_file = new OrderedFile(file.getAbsolutePath());
			ordered_files.add(ordered_file);
			
		}

		// sort the orderedfiles
		Collections.sort(ordered_files, new SortFiles());
		
		// read each file in order, concatenating the file contents into result.txt
		// each file gets a new line
		try {
			FileWriter fw = new FileWriter("result.txt");
			BufferedReader br = null;
			for (OrderedFile file : ordered_files) {
				// System.err.println("reading file: " + file.getName());
				br = new BufferedReader(new FileReader(file.get_original_path()));
				String line = "";
				while ((line = br.readLine()) != null)  {
					fw.write(line);
					fw.write("\n");
				}
			}
			fw.close();
			br.close();
		} catch (IOException e) {
			System.err.println("a file error occured");
			e.printStackTrace();
		}
	}
}
