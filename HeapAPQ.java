package cs2321;

import java.util.Comparator;
import net.datastructures.*;
/**
 * A Adaptable PriorityQueue based on an heap. 
 * 
 * Course: CS2321 Section ALL
 * Assignment: #3
 * @author Brandon Early
 */

public class HeapAPQ<K,V> implements AdaptablePriorityQueue<K,V> {
	
	public static class DefaultComparator<K> implements Comparator<K> {
		
		// This compare method simply calls the compareTo method of the argument. 
		// If the argument is not a Comparable object, and therefore there is no compareTo method,
		// it will throw ClassCastException. 
		public int compare(K a, K b) throws IllegalArgumentException {
			if (a instanceof Comparable ) {
			   return ((Comparable <K>) a).compareTo(b);
			} else {
				throw new  IllegalArgumentException();
			}
		}
	}
	
	private static class apqEntry<K,V> implements Entry<K,V> {
		
		/*
		 * instance variables for every entry into the heap
		 */
		private K key;
		private V value;
		private int index;
		
		/**
		 * constructor for entry objects
		 * @param key the key of the entry 
		 * @param value the value we are storing at that key
		 * @param index the index of the entry in the heap
		 */
		public apqEntry(K key, V value, int index) {
			this.key = key;
			this.value = value;
			this.index = index;
		}
		
		/**
		 * getter method to return the key of an entry
		 */
		@Override
		public K getKey() {
			return key;
		}

		/**
		 * getter method to return the value of an entry
		 */
		@Override
		public V getValue() {
			return value;
		}
		
		/*
		 * getter method to return the index of an entry
		 */
		public int getIndex() {
			return index;
		}
		
		/*
		 * setter method to change the key of an entry
		 */
		public void setKey(K k) {
			this.key = k;
		}

		/*
		 * setter method to change the value of an entry
		 */
		public void setValue(V v) {
			this.value = v;
		}
		
		/*
		 * setter method to change the index of an entry
		 */
		public void setIndex(int index) {
			this.index = index;
		}
		
	}
	
	/*
	 * setting up the heap, the comparator used to check the keys and values of the entries in the heap
	 * and initializing the size and default capacity variables
	 * i chose 16 for the default capacity, I couldn't find any specifications for what it should be on the assignment
	 */
	private ArrayList<apqEntry<K,V>> heap;
	private Comparator<K> comp;
	private final int defaultCapacity = 16;
	int size = 0;
	
	
	/* If no comparator is provided, use the default comparator. 
	 * See the inner class DefaultComparator above. 
	 * If no initial capacity is specified, use the default initial capacity.
	 */
	public HeapAPQ() {
		this.comp = new DefaultComparator<K>();
		this.heap = new ArrayList<>(defaultCapacity);
	}
	
	/* Start the PQ with specified initial capacity */
	public HeapAPQ(int capacity) {
		this.comp = new DefaultComparator<K>();
		this.heap = new ArrayList<>(capacity);
	}
	
	
	/* Use specified comparator */
	public HeapAPQ(Comparator<K> c) {
		this.comp = c;
		this.heap = new ArrayList<>(defaultCapacity);
	}
	
	/* Use specified comparator and the specified initial capacity */
	public HeapAPQ(Comparator<K> c, int capacity) {
		this.comp = c;
		this.heap = new ArrayList<>(capacity);
	}
	
	
	/*
	 * A method to return the index of an entry's parent in the arraylist
	 */
	private int parent(int j) {
		return (j-1)/2;
	}
	
	/*
	 * A method to return the index of an entry's left child in the arraylist
	 */
	private int left(int j) {
		return 2*j + 1;
	}
	
	/*
	 * A method to return the index of an entry's right child in the arraylist
	 */
	private int right(int j) {
		return 2*j + 2;
	}
	
	/*
	 * A method to check if an entry has a left child
	 */
	private boolean hasLeft(int j) {
		return left(j) < heap.size();
	}
	
	/*
	 * A method to check if an entry has a right child
	 */
	private boolean hasRight(int j) {
		return right(j) < heap.size();
	}
	
	/*
	 * a method to swap two entries in the heap, and update their indexes in the arraylist as necessary
	 */
	private void swap(int i, int j) {
		apqEntry<K,V> temp = heap.get(i);
		heap.set(i, heap.get(j));
		heap.set(j, temp);
		
		heap.get(i).setIndex(i);
		heap.get(j).setIndex(j);
	}
	
	/*
	 * upheap method used to restore heap order property anytime a change is made to the heap
	 */
	private void upheap(int j) {
		while(j>0) {
			int p = parent(j);
			if(comp.compare(heap.get(j).getKey(), heap.get(p).getKey()) >= 0) {
				break;
			}
			swap(j,p);
			j = p;
		}
	}
	
	/*
	 * downheap method used to restore heap order property anytime a change is made to the heap
	 */
	private void downheap(int j) {
		while(hasLeft(j)) {
			int leftIdx = left(j);
			int smallChildIdx = leftIdx;
			if(hasRight(j)) {
				int rightIdx = right(j);
				if(comp.	(heap.get(leftIdx).getKey(), heap.get(rightIdx).getKey()) > 0) {
					smallChildIdx = rightIdx;
				}
			}
			if(comp.compare(heap.get(smallChildIdx).getKey(), heap.get(j).getKey()) >= 0) {
				break;
			}
			swap(j, smallChildIdx);
			j = smallChildIdx;
		}
	}

	/*
	 * gets the size of the heap
	 */
	@Override
	public int size() {
		return size; 
	}

	/*
	 * checks to see if the heap is empty
	 */
	@Override
	public boolean isEmpty() {
		return size==0;
	}

	/*
	 * inserts a new entry into the heap at the last index, then travels through the heap with upheap, restoring heap order as necessary
	 */
	@TimeComplexity("O(lg n)")
	@TimeComplexityAmortized("O(lg n)")
	@Override
	public Entry<K, V> insert(K key, V value) throws IllegalArgumentException {
		/* TCJ
		 * the worst case running time complexity for insert is O(log(n)), this occurs in the case
		 * where the new node is inserted at the end of the heap, and then has to be 'upheaped'
		 * through the entire heap to become the new root. This is also the amortized time complexity because
		 * even in the best case scenario where the node you insert doesn't actually change positions, upheap is
		 * still called and checked. 
		 */
		
		if(key == null) {
			throw new IllegalArgumentException("Key is null");
		}
		
		apqEntry<K, V> newEntry = new apqEntry(key, value, heap.size());
		heap.addLast(newEntry);
		upheap(heap.size() -1);
		size++;
		return newEntry;
	}

	/*
	 * returns the entry at the index 0 in the heap
	 */
	@TimeComplexity("O(1)")
	@Override
	public Entry<K, V> min() {
		/* TCJ
		 * Because all that happens is calling the get method of the heap here, it is O(1)
		 * we are absolutely getting the value at index 0 every time, so we don't have to look through the heap 
		 */
		
		if(heap.isEmpty()) {
			return null;
		}
		return heap.get(0);
	}

	/*
	 * swaps the entry in index 0 of the heap with the entry in the last index of the heap
	 * then removes the entry that is now at the last index, and travels through the rest of the heap
	 * with downheap, restoring heap order property as necessary
	 */
	@TimeComplexity("O(lg n)")
	@Override
	public Entry<K, V> removeMin() {
		/* TCJ
		 * The worst case time complexity is O(log(n)) because in the worst case, after 
		 * swapping the first and last element and removing the desired element, we may
		 * have to downheap and swap at every level of the heap.
		 */
		
		if(heap.isEmpty()) {
			return null;
		}
		Entry<K,V> minimum = heap.get(0);
		swap(0, heap.size()-1);
		heap.remove(heap.size() - 1);
		downheap(0);
		size--;
		return minimum;
	}
	/*
	 * swaps the entry with the entry at the last index of the heap, assuming the entry given isn't itself the last index
	 * the removes the entry that is newly at the last index and travels through the rest of the heap starting at the index
	 * of the original entry with downheap, restoring heap order as necessary
	 */
	@TimeComplexity("O(lg n)")
	@Override
	public void remove(Entry<K, V> entry) throws IllegalArgumentException {
		/* TCJ
		 * Similar to the removeMin method, the worst case time complexity is O(log(n)) because
		 * in the worst case, after swapping the first and last element and removing the desired
		 * element, we may have to downheap and swap at every level of the heap.  
		 */
		
		if(entry == null) {
			throw new IllegalArgumentException("Entry is null");
		}
		
		if(!(entry instanceof apqEntry)) {
			throw new IllegalArgumentException("Entry not valid");
		}
		
		apqEntry<K, V> temp = (apqEntry<K, V>) entry;
		int index = temp.getIndex();
		
		if(index < 0 || index >= heap.size() || heap.get(index) != temp) {
			throw new IllegalArgumentException("Entry not in the heap");
		}
		
		if(index != heap.size() -1) {
			swap(index, heap.size()-1);
			heap.remove(heap.size()-1);
			downheap(index);
		}
		else {
			heap.remove(index);
		}
		size--;
	}
	
	/*
	 * changes the key of an entry, then travels through the rest of the heap with either upheap or downheap
	 * depending on rather the new key is greater than or less than the old key
	 */
	@TimeComplexity("O(lg n)")
	@Override
	public void replaceKey(Entry<K, V> entry, K key) throws IllegalArgumentException {
		/* TCJ
		 * The worst case time complexity for this method is O(log(n)) because the cases
		 * where we have to traverse the entire heap to restore heap order after
		 * replacing the key will take O(log(n)) time
		 */
		
		
		if(entry == null) {
			throw new IllegalArgumentException("Entry is null");
		}
		
		if(!(entry instanceof apqEntry)) {
			throw new IllegalArgumentException("Entry is not valid");
		}
		
		apqEntry<K,V> temp = (apqEntry<K,V>) entry;
		int index = temp.getIndex();
		
		if(index < 0 || index >= heap.size() || heap.get(index) != temp) {
			throw new IllegalArgumentException("Entry not in the heap");
		}
		
		if(key == null) {
			throw new IllegalArgumentException("Key cannot be null");
		}
		
		K oldKey = temp.getKey();
		temp.setKey(key);
		
		if(comp.compare(key,oldKey) < 0) {
			upheap(index);
		}
		else{
			downheap(index);
		}
	}
	
	/*
	 * changes the value of an entry in the heap, no traveling through the heap is necessary
	 */
	@TimeComplexity("O(1)")
	@Override
	public void replaceValue(Entry<K, V> entry, V value) throws IllegalArgumentException {
		/* TCJ
		 * Because we are just using a setter method in this replaceValue method,
		 * it is O(1), because setValue directly accesses the entry we want to change value's
		 */
		
		if(entry == null) {
			throw new IllegalArgumentException("Entry is null");
		}
		
		if(!(entry instanceof apqEntry)) {
			throw new IllegalArgumentException("Entry is not valid");
		}
		
		apqEntry<K,V> temp = (apqEntry<K,V>) entry;
		int index = temp.getIndex();
		
		if(index < 0 || index >= heap.size() || heap.get(index) != temp) {
			throw new IllegalArgumentException("Entry not in the heap");
		}
		
		temp.setValue(value);
	}
}