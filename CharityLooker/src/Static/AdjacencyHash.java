package Static;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Performs a modified Linear Probing hash table approach 
 * to put duplicate keys into a linked list in order to handle cases where
 * duplicate keys occur
 * 
 * @author Ryan Schnekenburger
 * @version 1.0
 */
public class AdjacencyHash {

		private int m;
		private String[] keys;
		private ArrayList<LinkedList<Charity>> vals;
		private int n;
		/**
		 * The constructor that initializes the current size of the hash table, the initial max size of the hash table
	     * the max amount of keys and values the hash table can hold
		 * @param m the initial capacity of the hash table
		 */
		public AdjacencyHash(int m){
			this.m = m;
			this.keys = new String[this.m];
			this.n = 0;
			int i = 0;
			this.vals = new ArrayList<LinkedList<Charity>>();
			while(i != this.m) {
					LinkedList<Charity> list = new LinkedList<Charity>();
				    this.vals.add(list);
				    i++;
				}
		}
		
		/**
		 * a method to return an index based on the hashcode of an inputed string
		 * @param key the string that is being hashed
		 * @return the index where the value corresponding to the string should go
		 */
		private int hash(String key) {
			int x = (key.hashCode() % 0x7fffffff) % this.m;
			if (x > 0)
				return x;
			else
				return x*-1;
		}
		
		/**
		 * a method to put a charity object into a linked list at an index corresponding to the value of the hashing 
		 * of the corresponding key
		 * @param key the string that is being hashed
		 * @param val the charity object that is being inserted into the linked list corresponding to the hash of the key
		 */
		public void put(String key, Charity val) {
			if (key == null)
				return;
			if (this.n >= this.m / 2)
				resize(2 * m);
			
			int i;
			for (i = hash(key); this.keys[i] != null; i = (i + 1) % m) {
				if (this.keys[i].equals(key)){ // && !this.vals.get(i).contains(val)) {
					this.vals.get(i).add(val);
					return;
				}
			}
			this.keys[i] = key;
			this.vals.get(i).add(val);
			this.n++;
		}     
		
		/**
		 * either shrinks the array or grows the size of the hash table based on the size of the user input with
		 * comparison to the current max size of the hash table
		 * @param capacity the new max size of the hash table
		 */
		private void resize(int capacity) {
			AdjacencyHash temp = new AdjacencyHash(capacity);
			for (int i = 0; i < this.m; i++) {
				if (this.keys[i] != null) {
					for (int j = 0; j < this.vals.get(i).size(); j++)
						temp.put(this.keys[i], this.vals.get(i).get(j));
				}
			}
			this.keys = temp.keys;
			this.vals = temp.vals;
			this.m = temp.m;
		}
		
		/**
		 * Returns the linked list at the position of the hash of the key in the value arraylist
		 * @param key the corresponding key to the linked list that you want to return
		 * @return the linked list of the vals array at the hash of the given key
		 */
		public LinkedList<Charity> get(String key) {
			if (key == null)
				throw new IllegalArgumentException("argument to get() is null");
			for (int i = hash(key); keys[i] != null; i = (i + 1) % m) {
				if (keys[i].equals(key)) {
					return this.vals.get(i);
				}
			}
			return null;
		}
		
		/**
		 * A method to transform the adjacency list into a string output
		 */
		public String toString() {
			String temp = "";
			for (int i = 0; i < keys.length; i++) {
				temp+="\nThe Adjacency list for " + keys[i] + " is ";
				for (int j = 0; j < this.vals.get(i).size(); j++) {
					temp += this.vals.get(i).get(j) + ", ";
				}
			}
			return temp;
		}
}
