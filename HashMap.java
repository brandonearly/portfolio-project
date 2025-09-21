package cs2321;

import net.datastructures.*;

public class HashMap<K, V> extends AbstractMap<K,V> {
	
	/* Use Array of UnorderedMap<K,V> for the Underlying storage for the map of entries.
	 * 
	 */
	private UnorderedMap<K,V>[]  table;
	int 	size;  // number of mappings(entries) 
	int 	capacity; // The size of the hash table. 
	int     DefaultCapacity = 17; //The default hash table size
	
	/* Maintain the load factor <= 0.75.
	 * If the load factor is greater than 0.75, 
	 * then double the table, rehash the entries, and put then into new places. 
	 */
	double  loadfactor= 0.75;  
	
	/**
	 * Constructor that takes a hash size
	 * @param hashtable size: the number of buckets to initialize 
	 */
	public HashMap(int hashtablesize) {
		table = (UnorderedMap<K,V>[]) new UnorderedMap[hashtablesize];
		capacity = hashtablesize;
		size = 0;
		
		for(int i = 0; i < capacity; i++) {
			table[i] = new UnorderedMap<>();
		}
	}
	
	/**
	 * Constructor that takes no argument
	 * Initialize the hash table with default hash table size: 17
	 */
	public HashMap() {
		this(17);
	}
	
	/* This method should be called by map an integer to the index range of the hash table 
	 */
	private int hashValue(K key) {
		return Math.abs(key.hashCode()) % capacity;
	}
	
	private void resizeTable(int newCapacity) {
		UnorderedMap<K, V>[] oldTable = table;
	    table = (UnorderedMap<K, V>[]) new UnorderedMap[newCapacity];
	    capacity = newCapacity;

	    for (int i = 0; i < capacity; i++) {
	        table[i] = new UnorderedMap<>();
	    }


	    for (UnorderedMap<K, V> map : oldTable) {
	        for (Entry<K, V> entry : map.entrySet()) {
	            table[hashValue(entry.getKey())].put(entry.getKey(), entry.getValue());
	        }
	    }
	}
	
	/*
	 * The purpose of this method is for testing if the table was doubled when rehashing is needed. 
	 * Return the the size of the hash table. 
	 * It should be 17 initially, after the load factor is more than 0.75, it should be doubled.
	 */
	public int tableSize() {
		return table.length;
	}
	
	
	@Override
	public int size() {
		return size;
	}

	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	/*
	 * Accessor method that uses the hashValue function to retrieve the hash-key and its associated entry
	 */
	@TimeComplexity(" O( 1 ) ")
	@TimeComplexityExpected(" O( 1 ) ")
	@Override
	public V get(K key) {
		/*
		 * TCJ:
		 * Because we use the hash to create our index for the entry, we can then retrieve the entry at that new 
		 * index in O(1) time
		 */
		return table[hashValue(key)].get(key);
	}

	/*
	 * Insert/update method that checks the load factor, then either inserts or updates the value depending on if the key already
	 * exists, this method uses seperate chaining to handle collisions between keys
	 */
	@TimeComplexity(" O( 1 ) ")
	@TimeComplexityExpected(" O( 1 ) ")
	@Override
	public V put(K key, V value) {
		/*
		 * TCJ:
		 * Because we use the hash to create our index for the entry, we can then update or insert a new entry
		 * at any given index in O(1) time
		 */
		if((double) size / capacity > loadfactor) {
			resizeTable(2 * capacity);
		}
		
		V check = table[hashValue(key)].put(key, value);
		if(check == null) {
			size++;
		}
		return check;
	}

	/*
	 * Deletion method that removes an entry from the hashmap, searching for the hashvalue of the desired key, then removing it.
	 */
	@TimeComplexity(" O( 1 ) ")
	@TimeComplexityExpected(" O( 1 ) ")
	@Override
	public V remove(K key) {
		/*
		 * TCJ:
		 * Because we use the hash to create our index for the entry, we can then remove that entry at any given index
		 * in O(1) time
		 */
		V check = table[hashValue(key)].remove(key);
		if(check != null) {
			size--;
		}
		return check;
	}

	@Override
	public Iterable<Entry<K, V>> entrySet() {
		DoublyLinkedList<Entry<K,V>> entries = new DoublyLinkedList<Entry<K,V>>();
		for(UnorderedMap<K,V> map : table) {
			for(Entry<K,V> entry : map.entrySet()) {
				entries.addLast(entry);
			}
		}
		return entries;
	}
}