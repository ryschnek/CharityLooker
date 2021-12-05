package Static;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Performs  depth first search on a randomly generated graph to determine 
 * which charities share common programs
 * 
 * @author Jason Nagy
 * @version 2.0
 */
public class DepthFirstSearch {
	
	private LinkedList<Charity> raw;
	private HashMap<Charity,Boolean> marked; //indexed by business number
	
	/**
	 * The constructor that creates the DFS object. Creates the graph
	 * to be used and then excecutes it
	 * 
	 * @param data A linked list of all the charities that share the same program
	 */
	public DepthFirstSearch(LinkedList<Charity> data){
		raw = data;
		
		//set up random graph to analyze with algorithm 
		for (int i=1; i<raw.size(); i++){
			int connections = ThreadLocalRandom.current().nextInt(1,6); //not inclusive
			for (int t=0; t<connections; t++){
				int toAdd = ThreadLocalRandom.current().nextInt(0,raw.size());
				//System.out.println(raw.get(i)+"|"+raw.get(toAdd));
				raw.get(i).addEdge(raw.get(toAdd));
			}
		}
		
		marked = new HashMap<Charity, Boolean>(); //indexed by business number
		for (Charity c : raw) {
			marked.put(c, false);
		}
		
		dfs(raw.get(0));
	}
	
	/**
	 * A helper, recursive algorithm that does the actual dfs work.
	 * 
	 * @param curr The charity that the program is currently looking at
	 */
	private void dfs(Charity curr){
		marked.put(raw.get(raw.indexOf(curr)), true);
		for (Charity e : raw.get(raw.indexOf(curr)).getEdge()){
			if (!marked.get(e)){
				dfs(e);
			}
		}
	}
	/**
	 * Prints all the found nodes to the console
	 */
	public void print(){
		System.out.println("Some charities that offer this program are:");
		int maxPrint=10;
		int printed=0;
		for (HashMap.Entry<Charity,Boolean> me : marked.entrySet()) {
			if (me.getValue()==true){
				System.out.println(me.getKey());
				printed++;
			}
			//System.out.println(printed);
			if (printed>=maxPrint)
				break;
		}
	}
	
}
