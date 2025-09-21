package cs2321;

public class QuickSort<E extends Comparable<E>> implements Sorter<E> {
	
	/**
	 * sort - Perform quick sort. - Feel free to create helper methods. 
	 * @param array - Array to sort 
	 * 			E is an generic, but comparable type. 
	 * 			Don't cast K to int type . Don't use == to compare keys, use compareTo method. 
	 */	
	
	public int partition(E[] array, int p, int r) {
		
		E pivot = array[r];
		int i = p-1;
		
		for(int j = p; j < r; j++) {
			if(array[j].compareTo(pivot) <= 0) {
				i++;
				E temp = array[i];
				array[i] = array[j];
				array[j] = temp;
			}
		}
		
		E temp = array[i+1];
		array[i+1] = array[r];
		array[r] = temp;
		
		return i + 1;
	}

	public void quickSort(E[] array, int p, int r) {
		if(p<r) {
			int pivot = partition(array, p, r);
			quickSort(array, p, pivot - 1);
			quickSort(array, pivot + 1, r);
		}
	}
	
	@TimeComplexity(" O(n^2) ")
	
	@TimeComplexityExpected(" O(n lg n) ")
	
	public void sort(E[] array) {
		 /* TCJ
		 * Technically the worst case time complexity of this method is O(n^2), however this is 
		 * only in the case where the pivot is the smallest or largest value in the set, more often
		 * than not the run time of this method will be n log n, with the best case scenario being 
		 * a pivot that is the median of the set    
		 */
		quickSort(array, 0, array.length -1);
	}
}
