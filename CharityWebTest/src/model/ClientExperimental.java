package model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

public class ClientExperimental {

	public static void main(String[] args) throws IOException {
		
		DataPacker DP = new DataPacker();
		
		Charity[] charities = DP.getData();

		Quick.sort(DP.getData(),"bnum");
		
		LinearProbing hashBnum = new LinearProbing(charities.length);
		LinearProbing hashName = new LinearProbing(charities.length);
		AdjacencyHash hashProg = new AdjacencyHash(16);
		//System.out.println("ended");
		
		for (int i = 0; i < charities.length; i++) {
			hashBnum.put(charities[i].getBnum(), charities[i]);
			hashName.put(charities[i].getName().toUpperCase(), charities[i]);
			/*for (int j = 0; j < charities[i].getProg().length; j++) {
				hashProg.put(charities[i].getProg()[j], charities[i]);
			}*/
		
		}
		
		/*System.out.println("started2");
		LinkedList<Charity> charity = hashProg.get("I1");
		for (int i = 0; i < charity.size(); i++) {
			System.out.println(charity.get(i));
		AdjacencyHash hashOper = new AdjacencyHash(16);
		for (int i = 0; i < charities.length; i++) {
			hashBnum.put(charities[i].getBnum(), charities[i]);
			hashName.put(charities[i].getName().toUpperCase(), charities[i]);
			String prev = "";
			String prev2 = "";
			for (int j = 0; j < charities[i].getProg().length && !charities[i].getProg()[j].equals(prev) && !charities[i].getProg()[j].equals(prev2); j++) {
				if (j == 1)
					prev2 = prev;
				prev = charities[i].getProg()[j];
				hashProg.put(charities[i].getProg()[j], charities[i]);
			}

			String[] opcountry = charities[i].getOland().split(",");
			for (int k = 0; k < opcountry.length; k++) {
				hashOper.put(opcountry[k], charities[i]);
			}
		}
			for (int j = 0; j < hashProg.get("I1").size(); j++) {
				System.out.println(hashProg.get("I1").get(j));
			}
			//for (int j = 0; j < hashOper.get("RU").size(); j++) {
				//System.out.println(hashOper.get("RU").get(j));
			//}
		
//			for (int k=0; k<100; k++) {
//				String[] opcountry2 = charities[k].getOland().split(",");
//				for (int j = 0; j < opcountry2.length; j++) {
//					System.out.println(opcountry2[j]);
//				}
		//for (int i=0; i<100; i++)
			//System.out.println(DP.getData()[i]);*/
		
		System.out.println("Welcome to CharityLooker, the best one stop shop for charity data!");
		String choice = "";
		Scanner inputStr = new Scanner(System.in);
		
		Charity current = null;
		boolean hold = false;
		boolean nullCur=false;
		String mode = "0";
		do {
			if (!hold){
				current = null;
				System.out.print("Would you like to:\n1. Search by name\n2. Search by business number\n9. Quit\n>");
				choice = inputStr.nextLine();
			}
			hold=false;
			nullCur=false;
			mode=choice;
			
			if (choice.equals("1")){ //search by name
				System.out.print("Please enter the name of the charity:\n>");
				choice = inputStr.nextLine().toUpperCase();
				current = hashName.get(choice);
				if (current==null){
					System.out.println("The entered charity was not found. Did you mean one of the following? Please retry using one of these names if so:");
					nullCur=true;
				}
				
			}else if (choice.equals("2")){ //search by business number
				System.out.print("Please enter the business number of the charity:\n>");
				choice = inputStr.nextLine().toUpperCase();
				current = hashBnum.get(choice);
				if (current==null){
					System.out.println("The entered charity was not found. Did you mean one of the following? Please retry using one of these buesiness numbers if so:");
					nullCur=true;
				}
				
			}else if (choice.equals("9")){
				System.out.println("Exiting now...");
			}else{
				System.out.println("Invalid Input - Please try again");
			}
			
			if (nullCur==true){
				String lookFor=mode;
				
				int[][] indexArr = findSimilarIndex(lookFor,choice,DP.getNames());
				
				System.out.println("("+indexArr.length+" return(s): Currently sorting for \""+choice+"\")");
				
				for (int i = 0; i < indexArr.length; i++){
					if (lookFor.equals("1"))
						System.out.println(DP.getNames()[indexArr[i][0]][1]);
					else if (lookFor.equals("2"))
						System.out.println(charities[indexArr[i][0]].getBnum()+" - "+charities[indexArr[i][0]].getName());
					
				}
				
				System.out.print("Would you like to try again?\n1. Yes\n2. No\n>");
				choice = inputStr.nextLine();
				if (choice.equals("1"))
					hold= true;
				else
					hold=false;
			}
			
			if (current != null){ //explore data
				System.out.println("You have selected: "+current.getName());
				
				do{
					System.out.print("Would you like to:\n1. View basic data\n2. View operating data\n3. View financial data\n4. Miscellaneous data\n5. Find similar charities\n9. Return to charity selection\n>");
					choice = inputStr.nextLine();
					
					if (choice.equals("1")){
						System.out.println("Description: "+current.getDesc());
						System.out.println("Business number: "+current.getBnum());
					}else if (choice.equals("2")){
						System.out.print("Would you like to see: \n1. Home country and HQ location\n2. Operating country\n3. Programs\n>");
						choice = inputStr.nextLine();
						if (choice.equals("1")){
							System.out.println("Home country: "+current.getHland());
							System.out.println("HQ location: "+current.getHome());
						}else if (choice.equals("2")){
							if (current.getOland().equals(""))
								System.out.println("Operating country: Canada");
							else
								System.out.println("Operating country: "+current.getOland());
						}else if (choice.equals("3")){
							for (int i=0; i<current.getProg().length/2; i++){
								if (!current.getProg(i).equals(""))
									System.out.println(current.getProg(i)+": "+current.getProg(i+3));
							}
						}else{
							System.out.println("Invalid input");
						}
					}else if (choice.equals("3")){
						System.out.print("Would you like to see:\n1. Accounting information\n2. Cash and Investment amounts\n3. Private ownership (not working)\n>");
						choice = inputStr.nextLine();
						if (choice.equals("1")){
							System.out.println("Total revenue: $"+current.getStats(0));
							System.out.println("Total expenditure: $"+current.getStats(1));
							System.out.println("Total assets: $"+current.getStats(2));
							System.out.println("Liabilities: $"+current.getStats(3));
							System.out.println("Assets not used towards programs: $"+current.getStats(4));
						}else if (choice.equals("2")){
							System.out.println("Cash and short term investment: "+current.getStats(5));
							System.out.println("Long term investment: "+current.getStats(6));
						}else if (choice.equals("3")){
							System.out.println("Is the charity privately owned: "+current.getServ(4));
						}
					}else if (choice.equals("4")){
						System.out.print("Would you like to see:\n1. Charity category\n2. Employment information\n3. Political Activity\n>");
						choice = inputStr.nextLine();
						if (choice.equals("1")){ //0
							System.out.println("Charity category: "+current.getServ(0));
						}else if (choice.equals("2")){ //1,2,3
							System.out.println("Number of full time positions: "+current.getServ(1));
							System.out.println("Number of part time positions: "+current.getServ(2));
							System.out.println("Expenditure on positions: $"+current.getServ(3));
						}else if (choice.equals("3")){ //5
							System.out.println("Ran political campaign: "+current.getServ(5));
						}
					}
					
				} while (!choice.equals("9"));
				choice="0";
			}
			
		}while(!choice.contentEquals("9"));
		inputStr.close();
	}
	
	private static Charity getCharity(String choice, LinearProbing hashTable){ //For Jason T
		Charity current = hashTable.get(choice.toUpperCase());
		return current;
	}
	
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


