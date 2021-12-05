package Static;

/**
 * Performs a Linear Probing hash table approach 
 * to easily search for keys inputted by a user
 * 
 * @author Ryan Schnekenburger
 * @version 1.0
 */
public class LinearProbing {
	
	private int m;
	private int n;
	private String[] keys;
	Charity[] vals;
	
	/**
	 * The constructor that initializes the current size of the hash table, the initial max size of the hash table
	 * the max amount of keys and values the hash table can hold
	 * @param m The size of the hash table which can be subject to change at a later time
	 */
	public LinearProbing(int m) {
		this.n = 0;
		this.m = m;
		this.keys = new String[m];
		this.vals = new Charity[m];
	}
	
	/**
	 * Checks to see if an inputed key is present in the hash table
	 * @param key the key that you wish to know if it is present in the hash table
	 * @return if the key is in the hash table true is returned if not false is returned
	 */
	public boolean contains(String key) {
		if (key == null)
			throw new IllegalArgumentException("key to contain is null");
		return get(key) != null;
	}
	
	/**
	 * either shrinks the array or grows the size of the hash table based on the size of the user input with
	 * comparison to the current max size of the hash table
	 * @param capacity the new max size of the hash table
	 */
	private void resize(int capacity) {
		LinearProbing temp = new LinearProbing(capacity);
		for (int i = 0; i < this.m; i++) {
			if (this.keys[i] != null) {
				temp.put(this.keys[i], this.vals[i]);
			}
		}
		this.keys = temp.keys;
		this.vals = temp.vals;
		this.m = temp.m;
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
	 * a method to put a charity object at an index corresponding to the value of the hashing 
	 * of the corresponding key
	 * @param key the string corresponding to where the charity object should be placed in the hash table
	 * @param charity the charity object that is being placed into the 
	 */
	public void put(String key, Charity charity) {
		if (key == null)
			throw new IllegalArgumentException("key to put is null");

		if (charity == null) {
			delete(key);
			return;
		}

		if (this.n >= this.m / 2)
			resize(2 * m);
		
		int i;
		for (i = hash(key); this.keys[i] != null; i = (i + 1) % m) {
			if (this.keys[i].compareTo(key) == 0) {
				this.vals[i] = charity;
				return;
			}
		}
		this.keys[i] = key;
		this.vals[i] = charity;
		this.n++;
	}
	/**
	 * a method to delete a key and a corresponding charity object from the hash table
	 * @param key the string that must be deleted from the hash table
	 */
	public void delete(String key) 
    {
        if (!contains(key)) 
            return;
        
        int i = hash(key);
        while (!key.equals(keys[i])) 
            i = (i + 1) % this.m;        
        keys[i] = null;
        vals[i] = null;
    
        for (i = (i + 1) % this.m; keys[i] != null; i = (i + 1) % this.m)
        {
            String tmp1 = keys[i];
            Charity tmp2 = vals[i];
            keys[i] = null;
            vals[i] = null;
            this.n--;  
            put(tmp1, tmp2);            
        }
        this.n--;        
    }      
	
	/**
	 * Returns the value at the position of the hash of the key in the value array
	 * @param key the corresponding key to the value that you want to return
	 * @return the value of the vals array at the hash of the given key
	 */
	public Charity get(String key) {
		if (key == null)
			throw new IllegalArgumentException("argument to get() is null");
		for (int i = hash(key); keys[i] != null; i = (i + 1) % m)
			if (keys[i].equals(key))
				return vals[i];
		//System.out.println("Null Return");
		return null;
	}
}
