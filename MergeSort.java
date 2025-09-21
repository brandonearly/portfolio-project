package cs2321;

import java.util.Arrays;

public class MergeSort<E extends Comparable<E>> implements Sorter<E> {
	/**
	 * sort - Perform merge sort. -Feel free to create other methods. 
	 * @param array - Array to sort
	 * 			 K is an generic, but comparable type. 
	 * 			Don't cast K to int type . Don't use == to compare keys, use compareTo method. 
	 * 
	 * 			If you need to create an array of E,  use the following line as E is Comparable
	 * 			E[]  newarray = (E[]) Comparable[];
	 */

	public void merge(E[] array, E[] left, E[] right) {
		int i = 0;
		int j = 0;
		int k = 0;
		
		
        while (i < left.length && j < right.length) {
            if (left[i].compareTo(right[j]) < 0) {
                array[k++] = left[i++];
            } 
            else {
                array[k++] = right[j++];
            }
        }

        while (i < left.length) {
            array[k++] = left[i++];
        }

        while (j < right.length) {
            array[k++] = right[j++];
        }
    }
	
	
	//TODO: replace ??? with the worst case running time complexity for the method
	@TimeComplexity(" ??? ")
	public void sort(E[] array) {
		// TODO: write the time complexity justification below as comment
		/* TCJ
		 *     
		 */
		// TODO Auto-generated method stub
		if(array.length <= 1) {
			return;
		}
		
		int mid = array.length / 2;
		E[] firstHalf = Arrays.copyOfRange(array, 0, mid);
		E[] secondHalf = Arrays.copyOfRange(array, mid, array.length);
		
		sort(firstHalf);
		sort(secondHalf);
		
		merge(array,firstHalf, secondHalf);
		
	}
}

