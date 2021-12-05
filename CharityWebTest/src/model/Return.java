package model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

/**
 * ADT that leverages all other modules and turns simple strings/arrays
 * @author Jason Tsui, 400073151
 *
 */
public class Return {
	

	static LinearProbing hashBnum;
	static LinearProbing hashName;
	static AdjacencyHash hashProg;
	static Charity[] charities;
	static DataPacker DP;
	
	/**
	 * For testing values
	 * @param arg
	 * @throws IOException
	 */
	public static void main(String[] arg) throws IOException {
		Return test = new Return(); 
		//System.out.println(test.getName("AGA KHAN FOUNDATION CANADA / FONDATION AGA KHAN CANADA"));
		
		//String[] boop = test.getFuzzyName("Heart and Stroke");
		//for(int i = 0; i < boop.length ; i++) {
		//	System.out.println(boop[i]);
		//}
		
		//String[] beep = test.getFuzzyBnum("811036425RR");
		//for(int i = 0; i < beep.length ; i++) {
		//System.out.println(beep[i]);
		//}
	}
	
	/**
	 * Constructor for Return ADT
	 * @throws IOException
	 */
	public Return() throws IOException {
		
		DP = new DataPacker();
		
		charities = DP.getData();
		Quick.sort(DP.getData(),"bnum");
		
		hashBnum = new LinearProbing(charities.length);
		hashName = new LinearProbing(charities.length);
		hashProg = new AdjacencyHash(16);
		
		for (int i = 0; i < charities.length; i++) {
			hashBnum.put(charities[i].getBnum(), charities[i]);
			hashName.put(charities[i].getName().toUpperCase(), charities[i]);
		}
	}
		
	/**
	 * Returns name of charity
	 * @param name
	 * @return name of charity
	 */
	public static Charity getName(String name) {
		
		return hashName.get(name); 
	}	
	
	/**
	 * Returns business number of charity
	 * @param Bnum
	 * @return business number of charity
	 */
	public static Charity getBnum(String Bnum) {
		return hashBnum.get(Bnum);
	}
	
	/**
	 * Returns array of all charities in database
	 * @return array of charities
	 */
	public static Charity[] getAll() {
		return charities;
	}
	
	//if null
	/**
	 * Fuzzy search for charity name
	 * @param name
	 * @return array of charity names
	 */
	public static String[] getFuzzyName(String name){
		String mode = "1";
		String lookFor=mode;
		
		//System.out.println(charities[1]);
		//charities = DP.getData();
		//Charity[] temp1 = DP.getData();
		//String[][] temp = DP.getNames();
		//for(int i = 0; i < 10; i++) {
		//	System.out.println(temp[i][0]);
		//}

		
		int[][] indexArr = findSimilarIndex(lookFor,name,DP.getNames());
		int count = 0; 

		for (int i = 0; i < indexArr.length; i++){
			if (lookFor.equals("1")) {
				count = count + 1; 
			}
		}
		
		String returnArray[] = new String[count];
		count = 0; 
		
		for (int i = 0; i < indexArr.length; i++){
			if (lookFor.equals("1")) {
				returnArray[count] = DP.getNames()[indexArr[i][0]][1];
				count++; 
			}
		}
		
		String[] actualReturnArray = new String[returnArray.length+1];
		count = 0; 
		for (int i = 1; i < returnArray.length+1; i++){
			actualReturnArray[i] = returnArray[count];
			count++;
		}
		actualReturnArray[0] = "Did you mean?";
		return actualReturnArray;
		
	}
	
	//if null
	/**
	 * Fuzzy search for business number
	 * @param name
	 * @return array of charity name and business number 
	 */
	public static String[] getFuzzyBnum(String name){
		String mode = "2";
		String lookFor=mode;
		
		int[][] indexArr = findSimilarIndex(lookFor,name,DP.getNames());
		int count = 0; 

		for (int i = 0; i < indexArr.length; i++){
			if (lookFor.equals("2")) {
				count = count + 1; 
			}
		}
		
		String returnArray[] = new String[count];
		count = 0; 
		
		for (int i = 0; i < indexArr.length; i++){
			if (lookFor.equals("2")) {
				returnArray[count] = charities[indexArr[i][0]].getBnum()+" - "+charities[indexArr[i][0]].getName();
				count++; 
			}
		}
		
		String[] actualReturnArray = new String[returnArray.length+1];
		count = 0; 
		for (int i = 1; i < returnArray.length+1; i++){
			actualReturnArray[i] = returnArray[count];
			count++;
		}
		actualReturnArray[0] = "Did you mean?";
		return actualReturnArray;
		
	}
	
	//local function
	/**
	 * Implements fuzzy search
	 * @param mode
	 * @param base
	 * @param charityNames
	 * @return array of fuzzy searched items 
	 */
	private static int[][] findSimilarIndex(String mode,String base,String[][] charityNames){
		ArrayList<int[]> closeL = new ArrayList<int[]>();
		Locale language = new Locale("English");
		FuzzyScore fs = new FuzzyScore(language);
		
		double factor=0;
		if (mode.equals("1"))
			factor=0.6;
		else if (mode.equals("2"))
			factor=0.7;
		
		int max = fs.fuzzyScore(base,base);
		int cutOff = (int) (max*factor);
		int score = 0;
		
		
		for (int cur=0; cur<charityNames.length; cur++){
			if (mode.equals("1"))
				score = fs.fuzzyScore(base,charityNames[cur][1].toUpperCase());
			else if (mode.equals("2"))
				score = fs.fuzzyScore(base,charityNames[cur][0].toUpperCase());
			if (score>=cutOff){
				System.out.println(base+"|"+charityNames[cur][0].toUpperCase());
				closeL.add(new int[] {cur,score});
			}
		}
		int[][] close =closeL.toArray(new int[0][0]);
		
		sortSimilar(close);
		
		return close;
	}
	
	
	//local function
	/**
	 * Sorts array for fuzzy search implementation 
	 * @param close
	 */
	private static void sortSimilar(int[][] close){
		for (int i=0; i<close.length-1; i++){
			for (int j=0; j<close.length-1; j++){
				if (close[i][1]<close[j][1]){
					int[] temp = close[i];
					close[i]=close[j];
					close[j]=temp;
				}
			}
		}
		
	}
	
}
