package cs2321;
import cs2321util.HeapAPQ;

public class HeapPQSort<E extends Comparable<E>> implements Sorter<E> {

	/**
	 * sort - Perform an PrioiryQueue Sort using the heap implementation of PQ.
	 * @param array - Array to sort
	 * 			K is an generic, but comparable type. 
	 * 			Don't cast K to int type . Don't use == to compare keys, use compareTo method.
	 */
	
	@TimeComplexity(" O(n lg n) ")
	public void sort(E[] array) {
		/* TCJ
		 * The worst case time complexity for this method is O(n log n) because we have to
		 * insert all n elements of the array into the heap, then remove all n elements
		 * from the heap and put them in the proper spot in the array, for a total of
		 * n log n operations
		 */
		
		HeapAPQ<E,E> heap = new HeapAPQ<E,E>(array.length);
		
		for(int i = 0; i < array.length; i++) {
			heap.insert(array[i], null);
		}
		
		int index = 0;
		
		while(!(heap.isEmpty())){
			array[index] = heap.removeMin().getKey();
			index++;
		}
	}
}