package cs2321;
import java.util.Iterator;

import cs2321util.ArrayList;
import net.datastructures.*;
import cs2321util.CircularArrayQueue;
	

/**
 * @author ruihong-adm
 *
 * @param <E>
 */
public class LinkedBinaryTree<E> implements BinaryTree<E>{
	
	/*
	 * Private Node class to handle the creation of the nodes in the linked tree
	 */
	private class Node<E> implements Position<E>{
		Node<E> parent;
		Node<E> left;
		Node<E> right;
		E element;
		
		/*
		 * Because the Node class is using the position interface, I have to implement the getElement method which simply
		 * returns the value of a given node. 
		 */
		@Override
		public E getElement(){
			return element;
		}
	}
	
	Node<E> root; //the root node at the top of the tree
	int size = 0; //a variable to track the size of the tree or the number of nodes in the tree
	
	/*
	 * default constructor to initialize the variables
	 */
	public  LinkedBinaryTree() {
		root = null;
		size = 0;
	}

	/*
	 * Returns the position of the root node in the tree
	 */
	@Override
	public Position<E> root() {
		return (Position<E>) root;
	}
	
	/*
	 * returns the position of the parent node of a given node in the tree, assuming that the given node is not null
	 */
	@Override
		public Position<E> parent(Position<E> p) throws IllegalArgumentException {
		if(p == null) {
			throw new IllegalArgumentException("Position is null");
		}
		
		Node<E> node = (Node<E>) p;
		return (Position<E>) node.parent;
	}

	/*
	 * returns the position of the left child of a given node in the tree, assuming that the given node is not null
	 */
	@Override
	public Position<E> left(Position<E> p) throws IllegalArgumentException {
		if(p == null) {
			throw new IllegalArgumentException("Position is null");
		}
		
		Node<E> node = (Node<E>) p;
		return (Position<E>) node.left;
	}
	
	/*
	 * returns the position of the right child of a given node in the tree, assuming that the given node is not null
	 */
	@Override
	public Position<E> right(Position<E> p) throws IllegalArgumentException {
		if(p == null) {
			throw new IllegalArgumentException("Position is null");
		}
		
		Node<E> node = (Node<E>) p;
		return (Position<E>) node.right;
	}
	
	/*
	 * A method to check if a given node has any children and as such is an 'internal' node
	 * returns true if the node has at least one child and false otherwise
	 * assuming that the node given is not null
	 */
	@Override
	public boolean isInternal(Position<E> p) throws IllegalArgumentException {
		if(p == null) {
			throw new IllegalArgumentException("Position is null");
		}
		
		Node<E> node = (Node<E>) p;
		
		if(node.right != null || node.left != null) {
			return true;
		}
		return false;
	}

	/*
	 * A method to check if a given node has no children and as such is an 'external' node
	 * returns true if the node has no children and false otherwise
	 * assuming that the node given is not null
	 */
	@Override
	public boolean isExternal(Position<E> p) throws IllegalArgumentException {
		if(p == null) {
			throw new IllegalArgumentException("Position is null");
		}
		
		Node<E> node = (Node<E>) p;
		
		if(node.right == null && node.left == null) {
			return true;
		}
		return false;
	}

	/*
	 * Returns the number of nodes in the tree
	 */
	@Override
	public int size() {
		return size;
	}

	/*
	 * Returns true if there are no nodes in the tree and false otherwise
	 */
	@Override
	public boolean isEmpty() {
		return size == 0;
	}
	
	/* creates a root for an empty tree, storing e as element, and returns the 
	 * position of that root. An error occurs if tree is not empty. 
	 */
	public Position<E> addRoot(E e) throws IllegalStateException {
		if(!isEmpty()) {
			throw new IllegalStateException("Tree is not empty");
		}
		
		Node<E> node = new Node<E>();
		node.element = e;
		root = node; //initialize the instance variable root to the node
		size++;
		return (Position<E>) root;
		
	}
	
	
	/* creates a new left child of Position p storing element e, return the left child's position.
	 * If p has a left child already, throw exception IllegalArgumentExeption. 
	 */
	public Position<E> addLeft(Position<E> p, E e) throws IllegalArgumentException {
		if(p == null) {
			throw new IllegalArgumentException("Position is null");
		}
		
		Node<E> node = (Node<E>) p;
		
		if(node.left != null) {
			throw new IllegalArgumentException("Left child already exists");
		}
		
		Node<E> newNode = new Node<E>();
		newNode.element = e;
		
		node.left = newNode; //add the reference to the new node to the previous node
		newNode.parent = node;//add the reference to the old node from the new node
		size++;
		return (Position<E>) newNode;
	}

	/* creates a new right child of Position p storing element e, return the right child's position.
	 * If p has a right child already, throw exception IllegalArgumentExeption. 
	 */
	public Position<E> addRight(Position<E> p, E e) throws IllegalArgumentException {
		if(p == null) {
			throw new IllegalArgumentException("Position is null");
		}
		
		Node<E> node = (Node<E>) p;
		
		if(node.right != null) {
			throw new IllegalArgumentException("Right child already exists");
		}
		
		Node<E> newNode = new Node<E>();
		newNode.element = e;
		
		node.right = newNode;//add the reference to the new node to the previous node
		newNode.parent = node;//add the reference to the old node from the new node
		size++;
		return (Position<E>) newNode;
	}
		
	
	/* Set element in p.
	 * @return the old element in p. 
	 * assuming that p is not null
	 */
	public E setElement(Position<E> p, E e) throws IllegalArgumentException {
		if(p == null) {
			throw new IllegalArgumentException("Position is null");
		}
		
		Node<E> node = (Node<E>) p;
		
		E temp = node.element;
		node.element = e;
		return temp;
	}
	

	
	/**
	 * If p has two children, throw IllegalAugumentException. 
	 * If p is an external node ( that is it has no child), remove it from the tree.
	 * If p has one child, replace it with its child. 
	 * If p is root node, update the root accordingly. 
	 * @param p who has at most one child. 
	 * @return the element stored at position p if p was removed.
	 */
	public E remove(Position<E> p) throws IllegalArgumentException {
		if(p == null) {
			throw new IllegalArgumentException("Position is null");
		}
		
		Node<E> node = (Node<E>) p;
		E temp = node.element;
		
		if(node.left != null && node.right != null) {
			throw new IllegalArgumentException("Node has two children and cannot be removed");
		}
		
		Node<E> child = null;
		if(isInternal(p)) {
			//once in this if statement we know the node is internal, but we don't know if it's child is a left or right child so we check:
			if(node.left != null) {
				child = node.left;
			}
			else if(node.right != null) {
				child = node.right;
			}
		}
		
		//a quick redundant check to update the parent reference of the child node
		if(child != null) {
			child.parent = node.parent;
		}
		
		//if we want to remove the root node we have to update the rest of the tree to handle that as well
		if(p == root()) {
			root = child;
		}
		//
		else {
			//figuring out which side the node is the child of the parent again
			Node<E> parent = node.parent;
			if(node == parent.left) {
				parent.left = child;
			}
			if(node == parent.right) {
				parent.right = child;
			}
		}
		
		//This one is real easy, no other references have to be changed
		if(isExternal(p)) {
			node.parent = null;
		}
		
		size--;
		return temp;
	}
	
	
	/**
	 * Return the elements in the subtree of node P, including the data in node P. 
	 * The data in the return list need to match the in-order traversal.  
	 * @param p who has at most one child. 
	 * @return the List of elements in subtree of P following the in-order traversal. 
	 */
	public List<E> inOrderElements(Position<E> p) {
		//Hint: List is an interface. ArrayList implements List. 
		Node<E> node = (Node<E>) p;
		List<E> list = new ArrayList<>();
		
		inOrderHelp(node, list);
		return list;
	}
	
	/*
	 * A recursive helper method that does an inOrderTraversal and adds the elements of the tree to a list as it does so
	 */
	private void inOrderHelp(Node<E> node, List<E> list) {
		if(node == null) {
			return;
		}
		inOrderHelp(node.left,list);
		list.add(list.size(), node.element);
		inOrderHelp(node.right, list);
	}
	
	
	/**
	 * Return the elements in the subtree of node P, including the data in node P. 
	 * The data in the return list need to match the pre-order traversal.  
	 * @param p who has at most one child. 
	 * @return the List of elements in subtree of P following the in-order traversal. 
	 */
	public List<E> preOrderElements(Position<E> p) {
		//Hint: List is an interface. ArrayList implements List. 
		Node<E> node = (Node<E>) p;
		List<E> list = new ArrayList<>();
		
		preOrderHelp(node,list);
		return list;
	}
	
	/*
	 * A recursive helper method that does a preOrderTraversal and adds the elements of the tree to a list as it does so
	 */
	private void preOrderHelp(Node<E> node, List<E> list) {
		if(node == null) {
			return;
		}
		list.add(list.size(), node.element);
		preOrderHelp(node.left, list);
		preOrderHelp(node.right,list);
	}
	
	
	/**
	 * Return the elements in the subtree of node P, including the data in node P. 
	 * The data in the return list need to match the post-order traversal.  
	 * @param p who has at most one child. 
	 * @return the List of elements in subtree of P following the in-order traversal. 
	 */
	public List<E> postOrderElements(Position<E> p) {
		//Hint: List is an interface. ArrayList implements List. 
		Node<E> node = (Node<E>) p;
		List<E> list = new ArrayList<>();
		
		postOrderHelp(node, list);
		return list;
	}
	
	/*
	 * A recursive helper method that does a postOrderTraversal and adds the elements of the tree to a list as it does so
	 */
	private void postOrderHelp(Node<E> node, List<E> list) {
		if(node == null) {
			return;
		}
		postOrderHelp(node.left, list);
		postOrderHelp(node.right, list);
		list.add(list.size(),node.element);
	}
	
	/**
	 * Return the elements in the subtree of node P, including the data in node P. 
	 * The data in the return list need to match the level-order traversal.  
	 * @param p who has at most one child. 
	 * @return the List of elements in subtree of P following the in-order traversal. 
	 */
	public List<E> levelOrderElements(Position<E> p) {
		//Hint: List is an interface. ArrayList implements List. 
		List<E> queue = new ArrayList<>();
		List<E> result = new ArrayList<>();
		Node<E> rootNode = (Node<E>) p;
		queue.add(queue.size(), (E) rootNode);
		while(!queue.isEmpty()) {
			Node<E> node = new Node<E>();
			node = (Node<E>) queue.remove(0);
			if(node.left != null) {
				queue.add(queue.size(), (E) node.left);
			}
			if(node.right != null) {
				queue.add(queue.size(), (E) node.right);
			}
			
			result.add(result.size(), node.getElement());
		}
		return result;
	}
}
