package model;

import java.util.Comparator;

/**
 * 
 * 
 * @author Jason Nagy, 400055130
 * @since February 26/18
 * @version 1.0
 */
public class Charity implements Comparable<Charity>{
	private String   name; //name of charity
	private String   desc; //description (category number)
	private String   bnum; //business number/ ID
	private String   land; //parent country
	private String   home; //'hometown'/ where HQ is
	private String oper; //operating countries
	private String[] prog; //current programs
	private int[] stat; //financial statistics
	private String[] misc; //misc statistics
	
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
	}
	
	public Charity(String name, String description, String ID, String pcountry, String hometown, String ocountry, String[] programs, String[] services, String[] financial){
		this.name=name.replace("\"", "");
		this.desc=description;
		this.bnum=ID;
		this.land=pcountry;
		this.home=hometown;
		this.oper=ocountry;
		this.prog=programs;
		this.misc=services;
		
		int[] temp = new int[financial.length];
		for (int i=0; i<temp.length; i++)
			try{
				temp[i]=Integer.parseInt(financial[i]);
			} catch (NumberFormatException e){
				temp[i]=0;
			};
		this.stat=temp;
	}
	
	/* compareTo()
	 * s1 == s2 :0
	 * s1 > s2  :positive value
	 * s1 < s2  :negative value
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
	
	public int similarTo(Charity o, String property){
		//TODO: function to determine similarity between 2 charities for graph
		System.out.println("Not Implemented");
		return 0;
	}
	
	public int compareTo(Charity o, int index){ //to compare financial statistics
		if (this.stat[index] > o.getStats(index)){
			return(1);
		}else if (this.stat[index] < o.getStats(index)){
			return(-1);
		}
		return(0);
	}
	
	public int compareTo(Charity o){
	 	return (this.name.compareTo(o.getName()));
	}
	
	public String toString(){
		return (this.bnum+"\n "+this.name+"\n"+this.desc);
	}
	
	public void setName(String s){
		this.name=s;
	}
	public String getName(){
		return (this.name);
	}
	
	public void setDesc(String s){
		this.desc=s;
	}
	public String getDesc(){
		return (this.desc);
	}
	
	public void setBnum(String s){
		this.bnum=s;
	}
	public String getBnum(){
		return (this.bnum);
	}
	
	public void setHland(String s){
		this.land=s;
	}
	public String getHland(){
		return (this.land);
	}
	
	public void setHome(String s){
		this.home=s;
	}
	public String getHome(){
		return (this.home);
	}
	
	public String getOland(){
		return oper;
	}
	
	public void setProg(String[] s){
		this.prog=s;
	}
	public String[] getProg(){
		return (this.prog);
	}
	public String getProg(int i){
		return (this.prog[i]);
	}
	
	public void setServ(String[] s){
		this.misc=s;
	}
	public String[] getServ(){
		return (this.misc);
	}
	public String getServ(int i){
		return (this.misc[i]);
	}
	
	public void setStats(int[] x){
		this.stat=x;
	}
	public int[] getStats(){
		return (this.stat);
	}
	public int getStats(int i){
		return (this.stat[i]);
	}
}

class businessComparator implements Comparator<Charity> 
{


	public int compare(Charity o1, Charity o2) {
    	String name1 = o1.getBnum();
    	String name2 = o2.getBnum(); 
    	
    	return name1.compareTo(name2);
	}

}
