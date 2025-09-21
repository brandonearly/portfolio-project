package cs2321;

public class InPlaceSelectionSort<E extends Comparable<E>> implements Sorter<E> {

	/**
	 * sort - Perform an in-place selection sort
	 * @param array - Array to sort
	 * 			K is an generic, but comparable type. 
	 *          Don't cast K to int type . Don't use == to compare keys, use compareTo method.
	 */

	@TimeComplexity(" O(n^2) ")
	public void sort(E[] array) {
		/* TCJ
		 * The worst case time complexity for this method is O(n^2) because in the worst case
		 * the algorithm will have to iterate through the array once from left to right to make
		 * sure that the array is sorted, but then also an additional time for each iteration through
		 * the unsorted portion of the list, for O(n^2) run time. 
		 */
		
		for(int i = 0; i < array.length -1; i++) {
			int minIndex = i;
			
			for(int j = i+1; j < array.length; j++) {
				if(array[j].compareTo(array[minIndex]) < 0) {
					minIndex = j;
				}
			}
			
			if(minIndex != i) {
				E temp = array[minIndex];
				array[minIndex] = array[i];
				array[i] = temp;
			}
		}
	}
}