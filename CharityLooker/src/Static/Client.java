package Static;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Locale;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

/**
 * A module containing a clas that acts as a user interface to the program
 * 
 * @author Jason Nagy
 * @version 11.0
 */
public class Client {
	
	public static void main(String[] args) {
		Client Experimental = new Client();
		Experimental.CallUI();
	}

	private LinearProbing hashBnum; //a hash table with business number keys and charity values
	private LinearProbing hashName; //a hash table with name keys and charity values
	private AdjacencyHash hashProg; //a hash table with program code keys and linked list values
	private DataPacker DP; //the internal database
	private Charity[] charities; //an array of all charities collected by DP

	/**
	 * The constructor that sets up the user interface. The constructor loads
	 * all nessesary data
	 */
	public Client() {
		Stopwatch stopwatch = new Stopwatch();
		double begin = stopwatch.elapsedTime();
		try{
			//create database
			DP = new DataPacker();
		}catch (IOException e){
			System.out.println("Error with data file collection. Please check DataPacker");
			e.printStackTrace();
			return;
		}
		
		//retrieve main data from database
		charities = DP.getData();

		Quick.sort(charities, "bnum");

		hashBnum = new LinearProbing(charities.length);
		hashName = new LinearProbing(charities.length);
		hashProg = DP.getProg(); //the program hash table is created within the constructor of DataPacker

		//ensure no duplicates in the hash table
		for (int i = 0; i < charities.length; i++) {
			hashBnum.put(charities[i].getBnum(), charities[i]);
			hashName.put(charities[i].getName().toUpperCase(), charities[i]);
			String prev = "";
			String prev2 = "";
			for (int j = 0; j < charities[i].getProg().length && !charities[i].getProg()[j].equals(prev)
					&& !charities[i].getProg()[j].equals(prev2); j++) {
				if (j == 1)
					prev2 = prev;
				prev = charities[i].getProg()[j];
				hashProg.put(charities[i].getProg()[j], charities[i]);
			}
		}
		double end = stopwatch.elapsedTime();
		System.out.println("Loaded in: " + ((end - begin) / 1000000));
	}

	/**
	 * The method that activates the UI and takes control of the console 
	 */
	public void CallUI(){

		System.out.println("Welcome to CharityLooker, the best one stop shop for charity data!");
		String choice = "";
		Scanner inputStr = new Scanner(System.in);

		Charity current = null; //holds the charity that is currently selected
		boolean hold = false; //should the user prompt be hidden for a loop because of a prexisting situation
		String mode = "0";
		do {
			if (!hold) {
				current = null;
				System.out.print("Would you like to:\n1. Search by name\n2. Search by business number\n3. Search by charitable cause\n9. Quit\n>");
				choice = inputStr.nextLine();
				mode = choice;
			}
			hold = false;

			if (mode.equals("1")) { // search by name
				System.out.print("Please enter the name of the charity:\n>");
				choice = inputStr.nextLine().toUpperCase();
				current = hashName.get(choice);

			} else if (mode.equals("2")) { // search by business number
				System.out.print("Please enter the business number of the charity:\n>");
				choice = inputStr.nextLine().toUpperCase();
				current = hashBnum.get(choice);
				
			} else if (mode.equals("3")) {
				System.out.print("Please enter the program code (refer to documentation):\n>");
				choice = inputStr.nextLine().toUpperCase();
				LinkedList<Charity> result = DP.getProg().get(choice);
				
				result=reduceResults(result);
				
				if (result != null) {
					DepthFirstSearch DFS = new DepthFirstSearch(result);
					DFS.print();
				} else {
					System.out.println("Invalid entry, please try again");
				}

				current = new Charity();//pretend something was found to bypass checks
			} else if (mode.equals("9")) {
				System.out.println("Exiting now...");
			} else {
				System.out.println("Invalid Input - Please try again");
			}

			if (current == null && (mode.equals("1") || mode.equals("2"))) { //nothing found when expecting answer
				System.out.println("The entered charity was not found. Did you mean one of the following:");

				//track what the user was looking for
				String lookFor = mode;

				//create an array that is sorted in order of similarity
				int[][] indexArr = findSimilarIndex(lookFor, choice, DP.getNames());

				System.out.println("(" + indexArr.length + " return(s): Currently sorting for \"" + choice + "\")");

				//go through array and print the result to the console. The better matches are at the bottom
				for (int i = 0; i < indexArr.length; i++) {
					if (lookFor.equals("1"))
						System.out.println(DP.getNames()[indexArr[i][0]][1]);
					else if (lookFor.equals("2"))
						System.out.println(
								charities[indexArr[i][0]].getBnum() + " - " + charities[indexArr[i][0]].getName());

				}

				System.out.print("Would you like to try again?\n1. Yes\n2. No\n>");
				choice = inputStr.nextLine();
				if (choice.equals("1"))
					hold = true;
				else
					hold = false;
			}

			if (current != null && (mode.equals("1") || mode.equals("2"))) { // explore data with a selected charity
				System.out.println("You have selected: " + current.getName());

				do {
					System.out.print("Would you like to:\n1. View basic data\n2. View operating data\n3. View financial data\n4. Miscellaneous data\n5. Find similar charities\n9. Return to charity selection\n>");
					choice = inputStr.nextLine();

					if (choice.equals("1")) {
						System.out.println("Description: " + current.getDesc());
						System.out.println("Business number: " + current.getBnum());
					} else if (choice.equals("2")) {
						System.out.print("Would you like to see: \n1. Home country and HQ location\n2. Operating country\n3. Programs\n>");
						choice = inputStr.nextLine();
						if (choice.equals("1")) {
							System.out.println("Home country: " + current.getHland());
							System.out.println("HQ location: " + current.getHome());
						} else if (choice.equals("2")) {
							if (current.getOland().equals(""))
								System.out.println("Operating country: Canada");
							else
								System.out.println("Operating country: " + current.getOland());
						} else if (choice.equals("3")) {
							for (int i = 0; i < current.getProg().length / 2; i++) {
								if (!current.getProg(i).equals(""))
									System.out.println(current.getProg(i) + ": " + current.getProg(i + 3));
							}
						} else {
							System.out.println("Invalid input");
						}
					} else if (choice.equals("3")) {
						System.out.print("Would you like to see:\n1. Accounting information\n2. Cash and Investment amounts\n3. Private ownership\n>");
						choice = inputStr.nextLine();
						if (choice.equals("1")) {
							System.out.println("Total revenue: $" + current.getStats(0));
							System.out.println("Total expenditure: $" + current.getStats(1));
							System.out.println("Total assets: $" + current.getStats(2));
							System.out.println("Liabilities: $" + current.getStats(3));
							System.out.println("Assets not used towards programs: $" + current.getStats(4));
						} else if (choice.equals("2")) {
							System.out.println("Cash and short term investment: " + current.getStats(5));
							System.out.println("Long term investment: " + current.getStats(6));
						} else if (choice.equals("3")) {
							System.out.println("Is the charity privately owned: " + current.getServ(4));
						}
					} else if (choice.equals("4")) {
						System.out.print("Would you like to see:\n1. Charity category\n2. Employment information\n3. Political Activity\n>");
						choice = inputStr.nextLine();
						if (choice.equals("1")) {
							System.out.println("Charity category: " + current.getServ(0));
						} else if (choice.equals("2")) {
							System.out.println("Number of full time positions: " + current.getServ(1));
							System.out.println("Number of part time positions: " + current.getServ(2));
							System.out.println("Expenditure on positions: $" + current.getServ(3));
						} else if (choice.equals("3")) {
							System.out.println("Ran political campaign: " + current.getServ(5));
						}
					} else if (choice.equals("5")) {
						System.out.println("This charity partakes in:");
						String[] options = new String[current.getProg().length];
						for (int i = 0; i < current.getProg().length / 2; i++) {
							if (!current.getProg(i).equals("")){
								options[i]=current.getProg(i);
								System.out.println(current.getProg(i) + ": " + current.getProg(i + 3));
							}else{
								options[i]=null;
							}
						}
						System.out.print("Please enter a code to search:\n>");
						choice = inputStr.nextLine().toUpperCase();
						
						boolean found=false;
						for (int i=0; i<options.length; i++){
							if (options[i]!=null && options[i].equals(choice)){
								found=true;
								break;
							}
						}
						if (found){
							LinkedList<Charity> result = DP.getProg().get(choice);
							
							result=reduceResults(result);
							
							if (result != null) {
								DepthFirstSearch DFS = new DepthFirstSearch(result);
								DFS.print();
							} else {
								System.out.println("Invalid entry, please try again");
							}
						}else{
							System.out.println("Entered in an invalid code, please try again");
						}
						
					}

				} while (!choice.equals("9"));
				choice = "0";
			}

		} while (!mode.contentEquals("9"));
		inputStr.close();
	}

	/**
	 * Goes through the charities and compares them to a inputted string, looking
	 * for similarily named charities
	 * 
	 * @param mode The mode of the search (1=name, 2=business number). Affects the threshhold for a match
	 * @param base The string to compare against
	 * @param charityNames The names and business numbers of all charities; all seperately indexable
	 */
	private static int[][] findSimilarIndex(String mode, String base, String[][] charityNames) {
		ArrayList<int[]> closeL = new ArrayList<int[]>();
		Locale language = new Locale("English");
		FuzzyScore fs = new FuzzyScore(language);

		//set the minimum required score to classify as "matched"
		double factor = 0;
		if (mode.equals("1"))
			factor = 0.65;
		else if (mode.equals("2"))
			factor = 0.8;

		int max = fs.fuzzyScore(base, base);
		int cutOff = (int) (max * factor);
		int score = 0;

		//compare all elements
		for (int cur = 0; cur < charityNames.length; cur++) {
			if (mode.equals("1"))
				score = fs.fuzzyScore(base, charityNames[cur][1].toUpperCase());
			else if (mode.equals("2"))
				score = fs.fuzzyScore(base, charityNames[cur][0].toUpperCase());
			if (score >= cutOff) {
				closeL.add(new int[] { cur, score });
			}
		}
		int[][] close = closeL.toArray(new int[0][0]);

		sortSimilar(close);

		return close;
	}

	/**
	 * Modularized sortinf for the "matches" Since the list is (usually) short, bubble sort has no large impact.
	 * However, it was modularized so that the sort method can be easily changed.
	 * 
	 * @param close The array to sort
	 */
	private static void sortSimilar(int[][] close) {
		for (int i = 0; i < close.length; i++) {
			for (int j = i; j < close.length; j++) {
				if (close[i][1] > close[j][1]) {
					int[] temp = close[i];
					close[i] = close[j];
					close[j] = temp;
				}
			}
		}
	}
	
	/**
	 * When the searching for similar charities, sometimes the result array is too long and a stack overflow occurs.
	 * This method caps the maximum amount of results to 500 and randomly chooses from the larger array which ones
	 * will stay included
	 * 
	 * @param result The array to reduce
	 * @return The thinned out input array
	 */
	private LinkedList<Charity> reduceResults(LinkedList<Charity> result){
		LinkedList<Charity> temp = new LinkedList<Charity>();
		if (result.size() > 500){
			for (int i=0; i<500; i++){
				int toAdd = ThreadLocalRandom.current().nextInt(0,result.size()); //not inclusive
				temp.add(result.get(toAdd));
			}
			result=temp;
		}
		
		return result;
	}
	
}
