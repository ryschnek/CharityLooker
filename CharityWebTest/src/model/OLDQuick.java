package model;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

public class OLDQuick {
	

	    // This class should not be instantiated.
	    private OLDQuick() { }

	    /**
	     * Rearranges the array in ascending order, using the natural order.
	     * @param a the array to be sorted
	     */
	    @SuppressWarnings("rawtypes")
		public static void sort(Comparable[] a, Comparator comparator) {
	        Collections.shuffle(Arrays.asList(a)); //Protects against worst case
	        sort(a, 0, a.length - 1, comparator);
	        assert isSorted(a);
	    }

	    // quicksort the subarray from a[lo] to a[hi]
	    private static void sort(Comparable[] a, int lo, int hi, Comparator comparator) { 
	        if (hi <= lo) return;
	        int j = partition(a, lo, hi, comparator);
	        sort(a, lo, j-1, comparator);
	        sort(a, j+1, hi, comparator);
	        assert isSorted(a, lo, hi);
	    }

	    // partition the subarray a[lo..hi] so that a[lo..j-1] <= a[j] <= a[j+1..hi]
	    // and return the index j.
	    private static int partition(Comparable[] a, int lo, int hi, Comparator comparator) {
	        int i = lo;
	        int j = hi + 1;
	        Comparable v = a[lo];
	        while (true) { 

	            // find item on lo to swap
	            while (less(a[++i], v, comparator)) {
	                if (i == hi) break;
	            }

	            // find item on hi to swap
	            while (less(v, a[--j], comparator)) {
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
	    
	    // is v < w ?
	    private static boolean less(Comparable v, Comparable w) {
	        if (v == w) return false;   // optimization when reference equals
	        return v.compareTo(w) < 0;
	    }
	    
	    
	    private static boolean less(Object v, Object w, Comparator comparator) 
	    {
	        return comparator.compare(v, w) < 0;
	    }
	        
	    // exchange a[i] and a[j]
	    private static void exch(Object[] a, int i, int j) {
	        Object swap = a[i];
	        a[i] = a[j];
	        a[j] = swap;
	    }
	    


	   /***************************************************************************
	    *  Check if array is sorted - useful for debugging.
	    ***************************************************************************/
	    private static boolean isSorted(Comparable[] a) {
	        return isSorted(a, 0, a.length - 1);
	    }

	    private static boolean isSorted(Comparable[] a, int lo, int hi) {
	        for (int i = lo + 1; i <= hi; i++)
	            if (less(a[i], a[i-1])) return false;
	        return true;
	    }


	    // print array to standard output
	    private static void show(Comparable[] a) {
	        for (int i = 0; i < a.length; i++) {
	            System.out.println(a[i]);
	        }
	    }


	    public static void main(String[] args) {

	        
	    }

}
