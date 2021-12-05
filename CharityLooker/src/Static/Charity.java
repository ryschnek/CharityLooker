package Static;

import java.util.LinkedList;

/**
 * A charity ADT that contains all relevant data to one particular charity
 * 
 * @author Jason Nagy, 400055130
 * @since February 26/18
 * @version 12.0
 */
public class Charity{
	private String   name; //name of charity
	private String   desc; //description (category number)
	private String   bnum; //business number/ ID
	private String   land; //parent country
	private String   home; //'hometown'/ where HQ is
	private String oper; //operating countries
	private String[] prog; //current programs
	private int[] stat; //financial statistics
	private String[] misc; //misc statistics
	private LinkedList<Charity> edgeIndex; //what the neighbouring charities are to this charity
	
	/**
	 * Blank, default constructor.
	 */
	public Charity(){ //Catch "Void" charity
		this.name="Empty";
		this.desc="Void";
		this.bnum="00000";
		this.land="Nowhere";
		this.home="Nowhere";
		this.oper="Nowhere";
		this.prog=new String[] {"Nothing"};
		this.misc=new String[] {"Nothing"};
		this.stat=new int[] {0};
		this.edgeIndex=new LinkedList<Charity>();
	}
	
	/**
	 * A constructor to create a new charity object.
	 * 
	 * @param name The name of the charity
	 * @param description The charity description
	 * @param ID The business number. Is unique
	 * @param pcountry The home country of the charity
	 * @param hometown The HQ location of the charity
	 * @param ocountry The operating country
	 * @param programs An array of programs that the charity partakes in
	 * @param services An array containing miscellaneous data
	 * @param financial An array containing financial information
	 */
	public Charity(String name, String description, String ID, String pcountry, String hometown, String ocountry, String[] programs, String[] services, String[] financial){
		this.name=name.replace("\"", "");
		this.desc=description;
		this.bnum=ID;
		this.land=pcountry;
		this.home=hometown;
		this.oper=ocountry;
		this.prog=programs;
		this.misc=services;
		this.edgeIndex=new LinkedList<Charity>();
		
		//read an int or if it is empty, mak it 0
		int[] temp = new int[financial.length];
		for (int i=0; i<temp.length; i++)
			try{
				temp[i]=Integer.parseInt(financial[i]);
			} catch (NumberFormatException e){
				temp[i]=0;
			};
		this.stat=temp;
	}
	
	/**
	 * A versitile method that compares charities based on some criteria.
	 * 
	 * @param o The charity to compare with
	 * @param property The property to compare the two charities by
	 * @return Returns 1 if this charity would be ranked first, -1 if the other charity would be ranked first or 0 if they are "equal"
	 */
	public int compareTo(Charity o, String property){
		if (property.compareTo("name")==0){
			return(this.name.compareTo(o.getName()));
		}else if (property.compareTo("desc")==0){
			return(this.desc.compareTo(o.getDesc()));
		}else if (property.compareTo("bnum")==0){
			return(this.bnum.compareTo(o.getBnum()));
		}else if (property.compareTo("land")==0){
			return(this.land.compareTo(o.getHland()));
		}else if (property.compareTo("home")==0){
			return(this.home.compareTo(o.getHome()));
		}else if (property.compareTo("oper")==0){ //used to determine if they share a operating location
			if (getOland().equals(o.getOland()))
				return 1;
			else
				return 0;
		}else if (property.compareTo("prog")==0){ //who has more programs
			return( (this.prog.length > o.getProg().length) ? 1 : -1 );
		}else if (property.compareTo("serv")==0){ //who has more services
			return( (this.misc.length > o.getServ().length) ? 1 : -1 );
		}else{
			System.out.println("No Operation");
			return(0);
		}
	}
	
	/**
	 * Similar to {@link #compareTo(Charity, String)}, but it is meant exclusively for the financial data.
	 * 
	 * @param o The charity to compare with
	 * @param index The index of the financial data to compare
	 * @return Returns 1 if this charity would be ranked first, -1 if the other charity would be ranked first or 0 if they are "equal"
	 */
	public int compareTo(Charity o, int index){ //to compare financial statistics
		if (this.stat[index] > o.getStats(index)){
			return(1);
		}else if (this.stat[index] < o.getStats(index)){
			return(-1);
		}
		return(0);
	}
	
	/**
	 * A way to print charities to the screen
	 * 
	 * @return The string representation of the charity
	 */
	public String toString(){
		return (this.bnum+"- "+this.name);
	}
	
	/**
	 * A way to get the charity's name
	 * 
	 * @return The charity's name
	 */
	public String getName(){
		return (this.name);
	}
	
	/**
	 * A way to get the charity's decription
	 * 
	 * @return The charity's description
	 */
	public String getDesc(){
		return (this.desc);
	}
	
	/**
	 * A way to get the charity's business number
	 * 
	 * @return The charity's business number
	 */
	public String getBnum(){
		return (this.bnum);
	}
	
	/**
	 * A way to get the charity's home country
	 * 
	 * @return The charity's home country
	 */
	public String getHland(){
		return (this.land);
	}
	
	/**
	 * A way to get the charity's HQ location
	 * 
	 * @return Where the charity HQ is located
	 */
	public String getHome(){
		return (this.home);
	}
	
	/**
	 * A way to get the charity's operating country
	 * 
	 * @return The charity's operating country
	 */
	public String getOland(){
		return oper;
	}
	
	/**
	 * A way to get all a charity's current programs
	 * 
	 * @return An array containing the current programs
	 */
	public String[] getProg(){
		return (this.prog);
	}
	/**
	 * A way to get a charity's program by index
	 * 
	 * @return A single charity program
	 */
	public String getProg(int i){
		return (this.prog[i]);
	}
	
	/**
	 * A way to get all a charity's miscallaneous data
	 * 
	 * @return An array containing all the miscallaneous data
	 */
	public String[] getServ(){
		return (this.misc);
	}
	/**
	 * A way to get a single piece of charity's miscallaneous data
	 * 
	 * @return A single piece of miscallaneous data
	 */
	public String getServ(int i){
		return (this.misc[i]);
	}
	
	/**
	 * A way to get all a charity's current financial information
	 * 
	 * @return An array containing the current programs
	 */
	public int[] getStats(){
		return (this.stat);
	}
	/**
	 * A way to get a single piece of charity's financial data
	 * 
	 * @return A single piece of financial data
	 */
	public int getStats(int i){
		return (this.stat[i]);
	}
	
	public void addEdge(Charity edge){
		edgeIndex.add(edge);
	}
	public Charity getEdge(int index){
		return edgeIndex.get(index);
	}
	public LinkedList<Charity> getEdge(){
		return edgeIndex;
	}
	
}
