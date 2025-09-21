package cs2321;


import net.datastructures.Entry;
import net.datastructures.List;

import java.util.Iterator;

import net.datastructures.BinaryTree;
import net.datastructures.SortedMap;
import net.datastructures.Position;


public class BinarySearchTree<K extends Comparable<K>,V> extends AbstractMap<K,V>  {
	
	
	// TODO: Copy your implementation of LinkedBinaryTree to this project 
	//      or use the cs2321util archive
	//      Then uncomment this line
	
	/* The base data structure is a linked binary tree with the leaf nodes used as sentinel nodes*/
	LinkedBinaryTree<Entry<K,V>> tree; 
	
	int size = 0;  //the number of entries (mappings)
	
	/* 
	 * default constructor
	 */
	public BinarySearchTree() {
		tree = new LinkedBinaryTree<Entry<K,V>>();
	}
	
	/* 
	 * Return the tree. The purpose of this method is purely for testing.  
	 */
	public BinaryTree<Entry<K,V>> getTree() {
		return tree;
	}
	
	/*
	 * Helper method to find the node of a specific key in the tree, uses a binary search principle
	 */
	public Position<Entry<K,V>> search(Position<Entry<K,V>> v, K key){
		if(tree.isExternal(v)) {
			return v;
		}
		int check = key.compareTo(v.getElement().getKey());
		if(check == 0) {
			return v;
		}
		else if(check < 0) {
			return search(tree.left(v), key);
		}
		else {
			return search(tree.right(v), key);
		}
	}
	
	@Override
	public int size(){
		return size;
	}
	
	/*
	 * Accessor method that searches through the tree, then returns the value of the node with that key, so long as 
	 * the search didn't reach a sentinel node
	 */
	@TimeComplexity(" O( n ) ")
	@TimeComplexityExpected(" O( lg n ) ")
	@Override
	public V get(K key) {
		/*
		 * TCJ:
		 * Because we search the tree for our node in a binary fashion, we expect the method to take 
		 * O(log n) time, however the worst case, in an unbalanced tree is O(n)
		 */
		Position<Entry<K,V>> node = search(tree.root(), key);
		if(tree.isExternal(node)) {
			return null;
		}
		else {
			return node.getElement().getValue();
		}
	}

	/*
	 * Insert/update method that checks to see if the key already exists, then updates it if so, or inserts a new node at the 
	 * correct position if not. It also handles the edge case that is the tree being empty, and adds the root with sentinel nodes. 
	 */
	@TimeComplexity(" O( n ) ")
	@TimeComplexityExpected(" O( lg n ) ")
	@Override
	public V put(K key, V value) {
		/*
		 * TCJ:
		 * Because we search the tree for our node in a binary fashion, we expect the method to take O(log n) time,
		 * however the worst case, in an unbalanced tree is O(n)
		 */
		mapEntry<K,V> newEntry = new mapEntry<>(key,value);
		
		if(tree.isEmpty()) {
			tree.addRoot(newEntry);
			tree.addLeft(tree.root(), null);
			tree.addRight(tree.root(), null);
			size++;
			return null;
		}
		
		Position<Entry<K,V>> node = search(tree.root(), key);
		if(!tree.isExternal(node)){
			V oldVal = node.getElement().getValue();
			tree.setElement(node, newEntry);
			return oldVal;
		}
		if(tree.isExternal(node)) {
			tree.setElement(node, newEntry);
			tree.addLeft(node, null);
			tree.addRight(node, null);
			size++;
			return null;
		}
		return null;
	}

	/*
	 * Removal/deletion method that searches the tree for the desired key, then removes the node, as well as any sentinel nodes it might have
	 * then it returns the value of the node it is deleting. 
	 */
	@TimeComplexity(" O( n ) ")
	@TimeComplexityExpected(" O( lg n ) ")
	@Override
	public V remove(K key) {
		/*
		 * TCJ:
		 *Because we search the tree for our node in a binary fashion, we expect the method to take O(log n) time,
		 * however the worst case, in an unbalanced tree is O(n)
		 */
		Position<Entry<K,V>> node = search(tree.root(), key);
		if(tree.isExternal(node)) {
			return null;
		}
		V oldVal = node.getElement().getValue();
		Position<Entry<K,V>> left = tree.left(node);
		Position<Entry<K,V>> right = tree.right(node);
		
		if(tree.isExternal(left)) {
			tree.remove(left);
			tree.remove(node);
		}
		else if(tree.isExternal(right)) {
			tree.remove(right);
			tree.remove(node);
		}
		else {
			Position<Entry<K,V>> successor = right;
			while(tree.isInternal(tree.left(successor))) {
				successor = tree.left(successor);
			}
			tree.setElement(node, successor.getElement());
			tree.remove(tree.left(successor));
			tree.remove(successor);
		}
		size--;
		return oldVal;
	}

	@Override
	public Iterable<Entry<K, V>> entrySet() {
		int listSize = 0;
		List<Entry<K,V>> originalList = tree.inOrderElements(tree.root());
		List<Entry<K,V>> newList = new ArrayList<>();
		for(Entry<K,V> entry : originalList) {
			if(entry != null) {
				newList.add(listSize, entry);
				listSize++;
			}
		}
		return newList;
	}

	@Override
	public boolean isEmpty() {
		return size == 0; 
	}
}