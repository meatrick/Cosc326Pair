import java.util.Vector;
import java.util.Collections;
import java.util.Comparator;
import java.io.*;


public class Etude10 {
	final static int JOB_SITE_MAX = 5;
	final static int LAB_DESK_MAX = 25;
	final static int JOB_NUMBER_MAX = 99;
	final static char[] ACCEPTABLE_SEPARATORS = {'-', '.', '_', '/'};
	
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
						System.err.println("matching files");
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
			// System.err.println("name: " + getName());

			// DONE: try splitting around each different type of separator
			// DONE:  splitting once for each separator, allowing varying types
			// DONE: ignore the fact that we need an extension: this is built in, I believe
			// TODO: handle if there is no separator at all: split every two characters
			// TODO: account for not adding leading zeroes on single digit numbers

			String[] filename_data = name.split("-");

		
			
			// normal case
			if (filename_data.length == 3) {

				String job_number_str = filename_data[2];
				// remove extension if there is one
				if (filename_data[2].length() > 2) {
					job_number_str = filename_data[2].substring(0, 2); // remove extension
				}

				job_site = Integer.valueOf(filename_data[0]);
				lab_desk = Integer.valueOf(filename_data[1]);
				job_number = Integer.valueOf(job_number_str);
			} else { // irregular separators
				Vector<Integer> indexes = new Vector<Integer>();
				int index = 0;
				String name_cpy = name;
				while (index != -1) {
					for (int i = 0; i < ACCEPTABLE_SEPARATORS.length; i++) {
						index = name_cpy.indexOf(ACCEPTABLE_SEPARATORS[i]);
						if (index != -1) {
							indexes.add(index);
							name_cpy = name_cpy.substring(index + 1);
							break;
						}
					}

				}
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
	public static Vector<File> file_finder(File start_directory) {
		File[] files = start_directory.listFiles();
		Vector<File> all_files = new Vector<File>();
		
		for (File file : files) {
			if (file.isFile()) {
				all_files.add(file);
				// System.err.println(file.getAbsolutePath());
			} else if (file.isDirectory()) {
				Vector<File> sub_files = file_finder(file);
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


		Vector<File> files = file_finder(directory);

		// convert all files into OrderedFile objects
		Vector<OrderedFile> ordered_files = new Vector<OrderedFile>();
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
