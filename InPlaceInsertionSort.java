package cs2321;

public class InPlaceInsertionSort<E extends Comparable<E>> implements Sorter<E> {

	/**
	 * sort - Perform an in-place insertion sort
	 * @param array - Array to sort
	 * 	 		K is an generic, but comparable type. 
	 * 			Don't cast K to int type . Don't use == to compare keys, use compareTo method. 
	*/

	@TimeComplexity(" O(n^2) ")
	public void sort(E[] array) {
		/* TCJ
		 * The worst case for this method is O(n^2) because in the worst case,
		 * the algorithm would have to iterate over each element in the array once to check
		 * if it is in the correct position, and also one time for every element that is after 
		 * it in the array or n^2 times where n is the length of the array
		 */

		for(int i = 1; i < array.length; i++) {
			E temp = array[i];
			int j = i - 1;
			
			while(j >= 0 && array[j].compareTo(temp) > 0) {
				array[j+1] = array[j];
				j--;
			}
			array[j+1] = temp;
		}
	}
}