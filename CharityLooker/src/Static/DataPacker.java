package Static;

import java.io.IOException;

public class DataPacker {
	
	private Charity[] export;
	private String[][] names;
	private String[][] progref;
	private AdjacencyHash progHash;
	
	public DataPacker() throws IOException {
		System.out.println("=====");
		
		String[] nameHeader = new String[] { "BN", "Legal Name" };
		names = FileInterpreter.getDataFromFile("data/Charity_Identification.csv", nameHeader);
		
		// Identification
		/*
		 * Meanings:
		 * BN - Business number 
		 * Category - Type of charity 
		 * Legal Name - Name of charity 
		 * City - Mailing address city 
		 * Country - mailing address ccountry
		 */
		String[] header1 = new String[] { "BN", "Category", "Legal Name", "City", "Country" };
		String[][] data1 = FileInterpreter.getDataFromFile("data/Charity_Identification.csv", header1);
		
		// General Info
		/*
		 * Meanings:
		 * BN - Business Number 
		 * Program #1 Code - Operating Area Code 
		 * Program #1 - Program Area #1 Percentage 
		 * Program #2,3 (Code) - Same as above 
		 * 1600 - Is charity private 
		 * 2400 - Has the charity carried any political activities
		 */
		
		String[] header2 = new String[] { "BN", "Program #1 Code", "Program #1",
				"Program #2 Code", "Program #2", "Program #3 Code", "Program #3",
				"1600", "2400", "Program #1 Desc (Eng)", "Program #2 Desc (Eng)", "Program #3 Desc (Eng)"};
		String[][] data2 = FileInterpreter.getDataFromFile("data/Charity_GeneralInfo.csv", header2);

		// Compensation
		/*
		 * Meanings: 
		 * BN - Business Number 
		 * 300 - Number of permanent full time, compensated positions 
		 * 370 - Number of part-time or part-year employees 
		 * 390 - Expenditure on compensated positions
		 */
		String[] header3 = new String[] { "BN", "300", "370", "390" };
		String[][] data3 = FileInterpreter.getDataFromFile("data/Charity_Compensation.csv", header3);

		// Programs
		/*
		 * Meanings: 
		 * BN - Business Number 
		 * Program Type - Program type code 
		 * Description - Description of program
		 */
		String[] header4 = new String[] { "BN", "Program Type", "Description" };
		String[][] data4 = FileInterpreter.getDataFromFile("data/Charity_Programs.csv", header4);

		// Financial
		/*
		 * Meanings: 
		 * BN - Business Number 
		 * 4700 - Total revenue 
		 * 5100 - Total expenditure
		 * 4200 - Total assets 
		 * 4350 - Liabilities 
		 * 4250 - Assets not used in charitable activities 
		 * 4100 - Cash, bank accounts and short-term investments 
		 * 4140 - Long term investments
		 */
		String[] header5 = new String[] { "BN", "4700", "5100", "4200", "4350", "4250", "4100", "4140" };
		String[][] data5 = FileInterpreter.getDataFromFile("data/Charity_Financial.csv", header5);
		
		String[] header6 = new String[] { "BN", "Country", "#" };
		String[][] data6 = FileInterpreter.getDataFromFile("data/Charity_OperatingCountry.csv", header6);
		
		// Program Refernce
		/*
		 * Meanings: 
		 * Program Code - The 2 character alphanumeric code used for searching and UI
		 * Program Desc - The description that goes with the code
		 */
		String[] header7 = new String[] { "Program Code", "Program Desc" };
		String[][] data7 = FileInterpreter.getDataFromFile("data/Charity_ProgRefer.csv", header7);
		
		progref = data7;
		export = new Charity[data1.length];
		progHash = new AdjacencyHash(128);

		int j = 0;
		for (int i = 0; i < export.length; i++) {
			String name = data1[i][2];
			String desc = data4[i][2];
			String BN = data1[i][0];
			String land = data1[i][4];
			String home = data1[i][3];
			String opcy = "CA";
			while(j != data6.length && data6[j][0].equals(data1[i][0])){
				opcy += "," + data6[j][1];
				j++;
			}
			String[] programs = new String[] { data2[i][1], data2[i][3], data2[i][5], data2[i][9],data2[i][10],data2[i][11] };
			
			String[] fstat = new String[] { data5[i][1], data5[i][2], data5[i][3], data5[i][4], data5[i][5],
					data5[i][6], data5[i][7] };
			
			if (data3[i][1]=="")
				data3[i][1]="0";
			if (data3[i][2]=="")
				data3[i][2]="0";
			if (data3[i][3]=="")
				data3[i][3]="0";
			
			String[] misc = new String[] { data1[i][1], data3[i][1], data3[i][2], data3[i][3], data2[i][7], data2[i][8] };
			export[i] = new Charity(name, desc, BN, land, home, opcy, programs, misc, fstat);
			
			for (int k=0; k<3; k++){
				if (export[i].getProg(k)!=null && !export[i].getProg(k).equals(""))
					progHash.put(export[i].getProg(k).toUpperCase(),export[i]);
			}
		}
		
	}
	
	/**
	 * Returns the main charity array
	 * 
	 * @return A Charity array
	 */
	public Charity[] getData() {
		return export;
	}
	
	/**
	 * Returns An array that maps each program code to a description
	 * 
	 * @return A 2d string array
	 */
	public String[][] getProgRef(){
		return progref;
	}
	
	/**
	 * Returns An array that maps business number to the charity name
	 * 
	 * @return A 2d string array
	 */
	public String[][] getNames() {
		return names;
	}
	
	/**
	 * Returns A hashmap where the keys are program codes and the values are linked lists of charities
	 * 
	 * @return An AdjacencyHash of all the programs with the charities as values
	 */
	public AdjacencyHash getProg() {
		return progHash;
	}

}
