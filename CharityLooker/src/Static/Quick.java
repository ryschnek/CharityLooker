package Static;

import java.util.Arrays;
import java.util.Collections;

public class Quick {
	

	    // This class should not be instantiated.
	    private Quick() { }

	    /**
	     * Rearranges the array in ascending order, using the natural order.
	     * @param a the array to be sorted
	     */
	    public static void sort(Charity[] a, String sortBy){ //, Comparator comparator) {
	        Collections.shuffle(Arrays.asList(a)); //Protects against worst case
	        sort(a, 0, a.length - 1, sortBy);
	    }

	    /**
	     * Quicksort the subarray from a[lo] to a[hi]
	     * 
	     * @param a The array to sort
	     * @param lo The low index to sort at
	     * @param hi The high index to sort at
	     * @param sortBy What value to sort the charities by
	     */
	    private static void sort(Charity[] a, int lo, int hi, String sortBy) { 
	        if (hi <= lo) return;
	        int j = partition(a, lo, hi, sortBy);
	        sort(a, lo, j-1, sortBy);
	        sort(a, j+1, hi, sortBy);
	    }

	    /**
	     * Partition the subarray a[lo..hi] so that a[lo..j-1] <= a[j] <= a[j+1..hi] and return the index j
	     * 
	     * @param a The array to sort
	     * @param lo The low index to sort at
	     * @param hi The high index to sort at
	     * @param sortBy What value to sort the charities by
	     */
	    private static int partition(Charity[] a, int lo, int hi, String sortBy){
	        int i = lo;
	        int j = hi + 1;
	        Charity v = a[lo];
	        while (true) { 

	            // find item on lo to swap
	            while (less(a[++i], v, sortBy)) {
	                if (i == hi) break;
	            }

	            // find item on hi to swap
	            while (less(v, a[--j], sortBy)) {
	                if (j == lo) break;      // redundant since a[lo] acts as sentinel
	            }

	            // check if pointers cross
	            if (i >= j) break;

	            exch(a, i, j);
	        }

	        // put partitioning item v at a[j]
	        exch(a, lo, j);

	        // now, a[lo .. j-1] <= a[j] <= a[j+1 .. hi]
	        return j;
	    }


	   /***************************************************************************
	    *  Helper sorting functions.
	    ***************************************************************************/
	    
	    /**
	     * is v < w ?
	     * 
	     * @param v A charity to compare
	     * @param w The charity to compare against v
	     * @sortBy What to compare by
	     */
	    private static boolean less(Charity v, Charity w, String sortBy) {
	        if (v == w) return false;   // optimization when reference equals
	        return v.compareTo(w,sortBy) < 0;
	    }
	        
	    /**
	     * exchange a[i] and a[j]
	     * 
	     * @param The array to swap elements from
	     * @param i The index of the first element
	     * @param j The index of the element to swap with i
	     */
	    private static void exch(Object[] a, int i, int j) {
	        Object swap = a[i];
	        a[i] = a[j];
	        a[j] = swap;
	    }
	    


	   /*/***************************************************************************
	    *  Check if array is sorted - useful for debugging.
	    ***************************************************************************
	    public static boolean isSorted(Charity[] a, String sortBy) {
	        return isSorted(a, 0, a.length - 1, sortBy);
	    }

	    private static boolean isSorted(Charity[] a, int lo, int hi, String sortBy) {
	        for (int i = lo + 1; i <= hi; i++)
	            if (less(a[i], a[i-1], sortBy)) return false;
	        return true;
	    }


	    // print array to standard output
	    public static void show(Charity[] a) {
	        for (int i = 0; i < a.length; i++) {
	            System.out.println(a[i]);
	        }
	    }*/
}
