package cs2321;
import java.util.Iterator;
import net.datastructures.List;

/*
 * This is an implementation of an ArrayList, using the List interface and an Iterator
 */

public class ArrayList<E> implements List<E> {

	/*
	 * Setting up of the array we use to implement the arraylist, as well as the capacity and size variables
	 * used to track the number of elements and the amount of space in the array
	 */
	public static int capacity = 16; 
	private E[] data; 
	private int size = 0;

	/*
	 * An iterator to move through the array one element at a time
	 */
	private class ArrayListIterator implements Iterator<E> {

		private int currentIdx; //a default zero index variable to help the hasNext() method stay in bounds
		/*
		 * A hasNext method to check if there is an element in the list at the next position in the array
		 */
		@Override
		public boolean hasNext() {
			if(currentIdx >= size()) {
				return false;
			}
			return true;
		}

		/*
		 * A method to return the value of the element at the next position in the array
		 */
		@Override
		public E next() {
			return data[currentIdx++];
		}
	}
	
	/*
	 * A default constructor that assumes the capacity of the array is 16
	 */
	public ArrayList() {
		this(capacity);
	}
	
	/*
	 * A constructor that initializes an array with a given number of elements
	 */
	public ArrayList(int capacity) {
		data = (E[]) new Object[capacity];
	}

	/*
	 * Size method to get the number of elements currently in the array
	 */
	@Override
	public int size() {
		return size;
	}

	/*
	 * A capacity method to return the number of available spaces in the array
	 */
	public int capacity() {
		return data.length;
	}

	/*
	 * A method to double the size of the array if there is no more available spaces in the array
	 * this is done by creating a new array with twice the size, then iterating through it and putting the elements
	 * of the original array into the new one, before setting the newArray to the same name as the old one
	 */
	public void expandCapacity() {
		if(size == data.length) {
			int newCapacity = data.length * 2;
			E[] newArray = (E[]) new Object[newCapacity];
			for(int i =0; i < data.length; i++) {
				newArray[i] = data[i];
			}
			data = newArray;
		}
	}

	/*
	 * A method to see if there are any elements in the array
	 */
	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	/*
	 * A method to return the value at a position in the array
	 */
	@Override
	public E get(int i) throws IndexOutOfBoundsException {
		if(i >= size() || i < 0) {
			throw new IndexOutOfBoundsException("Invalid Index");
		}
		return data[i];
	}

	/*
	 * A method to change the value of an element at a given index in the array
	 */
	@Override
	public E set(int i, E e) throws IndexOutOfBoundsException {
		if(i >= size() || i < 0) {
			throw new IndexOutOfBoundsException("Invalid Index");
		}
		E temp = data[i];
		data[i] = e;
		return temp;
	}

	/*
	 * A method to add a new element to the array at a given index
	 */
	@Override
	public void add(int i, E e) throws IndexOutOfBoundsException {
		if(i > size() || i < 0) {
			throw new IndexOutOfBoundsException("Invalid Index");
		}
		expandCapacity();
		for(int j = size -1; j >= i; j--) {
			data[j+1] = data[j];
		}
		data[i] = e;
		size++;
	}

	/*
	 * A method to delete an element at a given index from the array and return that element
	 */
	@Override
	public E remove(int i) throws IndexOutOfBoundsException {
		if(i >= size() || i < 0) {
			throw new IndexOutOfBoundsException("Invalid Index");
		}
		E temp = data[i];

		for(int j = i; j< size-1; j++) {
			data[j] = data[j+1];
		}
		data[size-1] = null;
		size--;
		return temp;
	}

	/*
	 * A method to add a new element to the start of the array
	 */
	public void addFirst(E e)  {
		expandCapacity();
		for(int i = size() -1 ; i >= 0; i--) {
			data[i+1] = data[i];
		}
		data[0] = e;
		size++;
	}
	
	/*
	 * A method to add a new element to the end of the array
	 */
	public void addLast(E e)  {
		expandCapacity();
		data[size++] = e;
	}

	/*
	 * A method to delete the first element of the array
	 */
	public E removeFirst() throws IndexOutOfBoundsException {
		if(isEmpty()) {
			throw new IndexOutOfBoundsException("Array is empty");
		}
		E temp = data[0];
		for(int i =0; i < size()-1; i++) {
			data[i] = data[i+1];
		}
		size--;
		data[size] = null;
		return temp;
	}

	/*
	 * A method to delete the last element of the array
	 */
	public E removeLast() throws IndexOutOfBoundsException {
		if(isEmpty()) {
			throw new IndexOutOfBoundsException("Array is empty");
		}
		E temp = data[size-1];
		size--;
		data[size] = null;
		return temp;
	}
	
	/*
	 * A method to create an iterator using the iterator interface
	 */
	@Override
	public Iterator<E> iterator() {
		return new ArrayListIterator();
	}
}