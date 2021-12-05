package model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Abstract object for the extraction of information from .csv files
 * 
 * @author Jason Nagy, 400055130
 * @since March 04/18
 * @version 1.0
 */
public class FileInterpreter {
	
	/* EXAMPLE USE
		String   file = "data/Charity_Identification.csv"; - The file to extract from
		String[] toExtract = new String[] {"BN", "Country"}; - The columns (headers) you want to extract; use 'listHeaders' to learn what the file contains
		
		String[][] data = getDataFromFile(file,toExtract); - The method to extract the chosen headers from the chosen file

		- Then put data into Charity list
	*/
	
	/**
	 * Method to return extract desired columns from a specified .csv file with the intended use of inserting the data into Charity ADTs
	 * 
	 * @param file - the file to extract from
	 * @param toExtrace - the column headers to extrace data from
	 * @return String[][] with the first index being the charity number and the second being the data point corresponding to the chosen header sharing that index
	 * @throws IOException - when the specified file is not found
	 */
	public static String[][] getDataFromFile(String file, String[] toExtract) throws IOException{
		String[][] raw = readFile(file);
		String[][] data = extractElements(raw, getHeaders(file), toExtract);
		return(data);
	}
	
	/**
	 * Open and copy all data from a specific file
	 * 
	 * @param s - the file to read from
	 * @throws IOException - when the specified file is not found
	 */
	private static String[][] readFile(String s) throws IOException{
		
		List<String[]> list = new ArrayList<String[]>(); // will store the raw data outputted from the .csv file
		BufferedReader br = new BufferedReader(new FileReader(s));
		
		String line = br.readLine(); //dont read the first line (the headers)
		String delimiter=","; // what the line strings will be seperated by
		
		while ( (line = br.readLine() ) != null) // while there is a line
			list.add(line.split(delimiter)); // add the raw line to the list
		br.close();
		
		String[][] export = list.toArray(new String[0][0]);
		
		return(export);
	}
	
	/**
	 * Utility method to retrieve the headers (1st row, containing which columns are which) so the other methods can search through the data file
	 * 
	 * @param s - the file to extract headers from
	 * @throws IOException - when the specified file is not found
	 */
	public static String[] getHeaders(String s) throws IOException{
		
		BufferedReader br = new BufferedReader(new FileReader(s));
		
		String[] dirtyheader = (br.readLine().split(",")); // will store english and french name seperated by '/'
		String[] header = new String[dirtyheader.length]; // will store only english
		
		for (int i=0; i<dirtyheader.length; i++){
			String Header = dirtyheader[i].split("/")[0]; // remove french name if it is there
			header[i]=Header;
		}
		
		br.close();
		
		return(header);
	}
	
	/**
	 * Prints out the headers of a specified file so the programmer can learn what their options are
	 * 
	 * @param s - the file to print headers from
	 * @throws IOException - when the specified file is not found
	 */
	public static void listHeaders(String s) throws IOException{
		// same as getHeaders but will print instead
		
		BufferedReader br = new BufferedReader(new FileReader(s));
		
		String[] headers = (br.readLine().split(","));
		
		for (int i=0; i<headers.length; i++){
			String Header = headers[i].split("/")[0];
			System.out.println(">"+Header+"<"); // >< will show if a space is there or not
		}
		
		br.close();
	}
	
	/**
	 * Trims dataset to only include the desired columns
	 * 
	 * @param data - The dataset to extract from
	 * @param headers - All headers in the dataset
	 * @param find - The desired headers
	 * @return String[][] - A set of data trimmed to only include the columns corresponding to desired headers
	 * @throws RuntimeException - Thrown when an invalid header is encountered or if it isnt found
	 */
	private static String[][] extractElements(String[][] data, String[] headers, String[] find){
		
		int[] index = new int[find.length];
		boolean[] found = new boolean[find.length];
		
		for (int i=0; i<find.length; i++){
			for (int j=0; j<headers.length; j++){
				//System.out.println(find[i].equals(headers[j])+"|"+find[i]+"|"+headers[j]);
				if ( find[i].trim().equals(headers[j].trim()) ){
					index[i]=j;
					found[i]=true;
					break;
				}		
			}
			if (found[i]==false){
				System.out.println(">"+find[i]+"<");
				throw new RuntimeException("Header index not found");
			}
		}
		
		String[][] export = new String[data.length][index.length];
		for (int node=0; node<data.length; node++){
			for (int property=0; property<index.length; property++){
				try {
					int temp = index[property];
					String thing = data[node][temp];
					export[node][property]=thing;
				} catch (ArrayIndexOutOfBoundsException e) {
					
				}
				
			}
		}
		
		return(export);
	}
}


/*if (find[property].compareTo("300")==0){
	System.out.println(node+"|"+property);
	System.out.println(export[0].length+"|"+data[0].length+"|"+property);
}*/