package model;

// use another hash table to classify charities based on program codes
// if a charity has multiple codes, put it into multiple slots
// used to determine similarities between charities

/*public class OLDClient {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		DataPacker.dataToCharity();

		// Issues dealing with commas in charity names, but sort works
		// Arrays.sort(charityArray, new nameComparator());

		// businessComparator or nameComparator
		Quick.sort(DataPacker.getData(),"bnum");
		// Quick.sort(export);
		for (int i = 0; i < 100; i++)
			System.out.println(DataPacker.export[i] + " ");
		
		LinearProbing businesshash = new LinearProbing(DataPacker.export.length);
		LinearProbing namehash = new LinearProbing(DataPacker.export.length);
		for (int i = 0; i < DataPacker.export.length; i++) {
			businesshash.put(DataPacker.export[i].getBnum(), DataPacker.export[i].toString());
			namehash.put(DataPacker.export[i].getName().toUpperCase(), DataPacker.export[i].toString());
		}
		String g;
		do
		{
		System.out.println("Are you searching via business name or number");
		Scanner sc = new Scanner(System.in);
		String i = sc.nextLine();
		if (i.contains("na") || i.contains("NA") || i.contains("Na") || i.contains("nA")) {
			System.out.println("You entered business name. What is the business name of the desired charity?");
			Scanner input = new Scanner(System.in);
			String l = (input.nextLine()).toUpperCase();
			String j = namehash.get(l);
			if (j == null) 
				System.out.println("The name has either been misspelled or the charity is currently not covered");
			else
				System.out.println(j);
		}
		
		else if (i.contains("nu") || i.contains("NU") || i.contains("Nu") || i.contains("nU") || i.contains("#")) {
			System.out.println("You entered business number. What is the business number of the desired charity?");
			Scanner input3 = new Scanner(System.in);
			String h = input3.next();
			System.out.println("What would you like to know about the charity?");
			
			String k = businesshash.get(h);
			if (k == null)
				System.out.println("The number has either been misspelled or the charity is currently not covered");
			else
				System.out.println(k);
		}
		else {
			System.out.println("Please enter either name or number");
		}
		System.out.println("Would you like to continue?");
		Scanner input4 = new Scanner(System.in);
		g = input4.nextLine();
	}while (g.contains("y") || g.contains("Y"));
	}
	}
*/
