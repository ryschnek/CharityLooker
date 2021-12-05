package model;

public class LinearProbing {
	
	private int m;
	private int n;
	private String[] keys;
	Charity[] vals;

	public LinearProbing(int m) {
		this.n = 0;
		this.m = m;
		this.keys = new String[m];
		this.vals = new Charity[m];
	}

	public boolean contains(String key) {
		if (key == null)
			throw new IllegalArgumentException("argument to contains() is null");
		return get(key) != null;
	}

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

	private int hash(String key) {
		int x = (key.hashCode() % 0x7fffffff) % this.m;
		if (x > 0)
			return x;
		else
			return x*-1;
	}

	public void put(String key, Charity charity) {
		if (key == null)
			throw new IllegalArgumentException("first argument to put() is null");

		if (charity == null) {
			delete(key);
			return;
		}

		if (this.n >= this.m / 2)
			resize(2 * m);
		
		int i;
		for (i = hash(key); this.keys[i] != null; i = (i + 1) % m) {
			if (this.keys[i].equals(key)) {
				this.vals[i] = charity;
				return;
			}
		}
		this.keys[i] = key;
		this.vals[i] = charity;
		this.n++;
	}

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
	
	public Charity get(String key) {
		if (key == null)
			throw new IllegalArgumentException("argument to get() is null");
		for (int i = hash(key); keys[i] != null; i = (i + 1) % m)
			if (keys[i].equals(key))
				return vals[i];
		System.out.println("Null Return");
		return null;
	}
}
