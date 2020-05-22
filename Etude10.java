import java.util.Scanner;
import java.util.Vector;
import java.util.Collections;
import java.util.Comparator;
import java.io.*;


public class Etude10 {
	final int JOB_SITE_MAX = 5;
	final int LAB_DESK_MAX = 25;
	final int JOB_NUMBER_MAX = 99;
	
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
		final int job_site;
		final int lab_desk;
		final int job_number;

		public OrderedFile(String path_name) {
			super(path_name);

			String name = this.getName();
			String[] filename_data = name.split("-");
			String job_number_str = filename_data[2].substring(0, 2);
			
			job_site = Integer.valueOf(filename_data[0]);
			lab_desk = Integer.valueOf(filename_data[1]);
			job_number = Integer.valueOf(job_number_str);
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
			OrderedFile ordered_file = new OrderedFile(file.getName());
			ordered_files.add(ordered_file);
		}

		// sort the orderedfiles
		Collections.sort(ordered_files, new SortFiles());
		
		// read each file in order, concatenating the file contents into result.txt
		// each file gets a new line
		try {
			FileWriter fw = new FileWriter("result.txt");
			for (OrderedFile file : ordered_files) {
				System.err.println("reading file: " + file.getName());
				BufferedReader br = new BufferedReader(new FileReader(file.getAbsolutePath));
				String line = "";
				while ((line = br.readLine()) != null)  {
					fw.write(line);
				}
				fw.write("\n");
			}
			fw.close();
		} catch (IOException e) {
			System.err.println("a file error occured");
			e.printStackTrace();
		}
	}
}
