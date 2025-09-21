package cs2321;
import java.util.Iterator;
import net.datastructures.Position;
import net.datastructures.PositionalList;

/*
 * This is an implementation of a doubly-linked list using a positional list and a node class
 */
public class DoublyLinkedList<E> implements PositionalList<E> {

	/* 
	 * Inner node class that maintains the element contained in the node, as well as pointers to the next and previous nodes
	 */
	private static class Node<E> implements Position<E> {
		E element;
		Node<E> next;
		Node<E> previous;
		
		/*
		 * Constructor for the node class
		 */
		public Node(E e, Node<E> p, Node<E> n) {
			element = e;
			next = n;
			previous = p;
		}
		
		/*
		 * Returns the element of a particular node
		 */
		@Override
		public E getElement() {
			return element;
		}
		
		/*
		 * returns the node that is referenced by the previous pointer of a given node
		 */
		public Node<E> getPrevious(){
			return previous;
		}
		
		/*
		 * returns the node that is referenced by the next pointer of a given node
		 */
		public Node<E> getNext(){
			return next;
		}
		
		/*
		 * changes the value of an element of a node
		 */
		public void setElement(E e) {
			element = e;
		}
		
		/*
		 * changes the reference of the previous pointer of a given node
		 */
		public void setPrevious(Node<E> p) {
			previous = p;
		}
		
		/*
		 * changes the reference of the next pointer of a given node
		 */
		public void setNext(Node<E> n) {
			next = n;
		}
	}
	
	/*
	 * Initializing the head and tail nodes, as well as a size variable to track the number of nodes in the list. 
	 */
	private Node<E> head;
	private Node<E> tail;
	private int size;
	
	
	/*
	 * A constructor that defaults the head, tail and size variables to their default instances to create the 'start' of a list. 
	 */
	public DoublyLinkedList() {
		head = null;
		tail = null;
		size = 0;
	}

	/*
	 * A size method that returns the number of nodes in the list at a given point
	 */
	@Override
	public int size() {
		return size;
	}

	/*
	 * A check method to see if there are any nodes in the list
	 */
	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	/*
	 * A method that returns the value of the node that is the 'head' or the first node in the list at that point
	 */
	@Override
	public Position<E> first() {
		return head;
	}

	/*
	 * A method that returns the value of the node that is the 'tail' or the last node in the list at that point
	 */
	@Override
	public Position<E> last() {
		return tail;
	}

	/*
	 * A method to return the node that is referenced by the previous pointer of another node
	 * assuming that the given node is not null, the list is not empty, and that a node is what is actually supplied to the method
	 */
	@Override
	public Position<E> before(Position<E> p) throws IllegalArgumentException {
		if(p == null) {
			throw new IllegalArgumentException();
		}

		if(!(p instanceof Node<?>)) {
			throw new IllegalArgumentException();
		}
		
		Node<E> node = (Node<E>) p;
		
		if(node == head) {
			return null;
		}
		
		if(node.getPrevious() == null) {
			throw new IllegalArgumentException();
		}
		return node.getPrevious();
	}

	/*
	 * A method to return the node that is referenced by the next pointer of another node
	 * assuming that the given node is not null, the list is not empty, and that a node is what is actually supplied to the method
	 */
	@Override
	public Position<E> after(Position<E> p) throws IllegalArgumentException {
		if(p == null) {
			throw new IllegalArgumentException();
		}
		
		if(!(p instanceof Node<?>)) {
			throw new IllegalArgumentException();
		}
		
		Node<E> node = (Node<E>) p;
		if(node.getNext() == null) {
			return null;
		}
		return node.getNext();
	}

	/*
	 * A method to add a node to the start of the list and update the head variable accordingly
	 * assuming that if the list is empty no pointers will have to be updated
	 */
	@Override
	public Position<E> addFirst(E e) {
		Node<E> newNode = new Node<E>(e, null, head);
		if(isEmpty()) {
			tail = newNode;
		}
		else {
			head.setPrevious(newNode);
		}
		head = newNode;
		size++;
		return head;
	}

	/*
	 * A method to add a node to the end of the list and update the tail variable accordingly
	 * assuming that if the list is empty no pointers will have to be updated. 
	 */
	@Override
	public Position<E> addLast(E e) {
		Node<E> newNode = new Node<E>(e, tail, null);
		if(isEmpty()) {
			head = newNode;
		}
		else {
			tail.setNext(newNode);
		}
		
		tail = newNode;
		size++;
		return tail;
	}
	
	/*
	 * A method to add a node exactly one position previous to another node, updating the next 
	 * and previous pointers accordingly. Assuming that the node provided is not null, and is a node. 
	 */
	@Override
	public Position<E> addBefore(Position<E> p, E e) throws IllegalArgumentException {
		if(p == null) {
			throw new IllegalArgumentException();
		}
		
		if(!(p instanceof Node<?>)) {
			throw new IllegalArgumentException();
		}
		
		Node<E> currNode = (Node<E>) p;
		Node<E> newNode = new Node<E>(e, currNode.getPrevious(), currNode);	
		if(currNode.getPrevious() == null) {
			head = newNode;
		}
		else {
			currNode.getPrevious().setNext(newNode);
		}
		
		currNode.setPrevious(newNode);
		size++;
		return newNode;
	}

	/*
	 * A method to add a node exactly one position after another node, updating the next 
	 * and previous pointers accordingly. Assuming that the node provided is not null and is a node
	 */
	@Override
	public Position<E> addAfter(Position<E> p, E e) throws IllegalArgumentException {
		if(p == null) {
			throw new IllegalArgumentException();
		}
		
		if(!(p instanceof Node<?>)) {
			throw new IllegalArgumentException();
		}
		
		Node<E> currNode = (Node<E>) p;
		Node<E> newNode = new Node<E>(e, currNode, currNode.getNext());
		if(currNode.getNext() == null) {
			tail = newNode;
		}
		else {
			currNode.getNext().setPrevious(newNode);
		}
		
		currNode.setNext(newNode);
		size++;
		return newNode;
	}

	/*
	 * A method to update the value of an element of a node at a position, assuming that the 
	 * node provided is not null and is actually a node
	 */
	@Override
	public E set(Position<E> p, E e) throws IllegalArgumentException {
		if(p == null) {
			throw new IllegalArgumentException();
		}
		
		if(!(p instanceof Node<?>)) {
			throw new IllegalArgumentException();
		}
		
		Node<E> node = (Node<E>) p;
		E element = node.getElement();
		node.setElement(e);
		return element;
	}

	/*
	 * A method to remove a node from the list, updating the next and previous pointers accordingly
	 * assuming that the node provided is not null, is actually a node, and if the size of the list is one,
	 * that is if the element to be removed is the only element left, to update the head and tail references
	 */
	@Override 
	public E remove(Position<E> p) throws IllegalArgumentException { 
		if(p == null) { 
			throw new IllegalArgumentException(); 
		} 
		
		if(!(p instanceof Node<?>)) {
			throw new IllegalArgumentException();
		}
		
		Node<E> node = (Node<E>) p; 
		E temp = node.getElement(); 
		
		if(size() == 1) {
			head = null;
			tail = null;
		}
		else {
			if(node.getPrevious() != null) {
				node.getPrevious().setNext(node.getNext());
			}
			else {
				head = node.getNext();
				if(head != null) {
					head.setPrevious(null);
				}
			}
			
			if(node.getNext() != null) {
				node.getNext().setPrevious(node.getPrevious());
			}
			else {
				tail = node.getPrevious();
			}
		}
		
		size--;
		node.setNext(null);
		node.setPrevious(null);
		return temp;
	}
	
	/*
	 * A method to remove the first node in the list, updating the head reference in the process
	 * assuming that the head is not already null
	 */
	public E removeFirst() throws IllegalArgumentException {
		
		if(head == null) {
			throw new IllegalArgumentException();
		}
		
		E temp = head.getElement();
		if(size == 1) {
			head = null;
			tail = null;
		}
		else {
			head = head.getNext();
			head.setPrevious(null);
		}
			
		size--;
		return temp;
	}
	
	/*
	 * A method to remove the last element of the list, updating the tail reference in the process
	 * assuming that the tail is not already null
	 */
	public E removeLast() throws IllegalArgumentException {
		
		if(tail == null) {
			throw new IllegalArgumentException();
		}
		
		E temp = tail.getElement();
		if(size ==1) {
			head = null;
			tail = null;
		}
		else {
			tail = tail.getPrevious();
			tail.setNext(null);
		}
		
		size--;
		return temp;		
	}


	/*
	 * A method to take all of the elements of the list, store them in an array and then return the array
	 * I use an iterator over a for loop for simplicity
	 */
	public Object [] toArray() {
		E[] array =  (E[]) new Object[size];
		Iterator<E> iterator = iterator();
		int index = 0;
		
		while(iterator.hasNext()) {
			array[index++] = iterator.next();
		}
		return array;
	}
	
	/*
	 * A method to create an iterator object for
	 */
	@Override
	public Iterator<E> iterator() {
		return new ElementIterator();
	}
	
	/*
	 *Element iterator will return one element at a time  
	 */
	private class ElementIterator implements Iterator<E> {

		Node<E> currNode = head;
		
		/*
		 * a check to see if there is another node in the list
		 */
		@Override
		public boolean hasNext() {
			if(currNode == null) {
				return false;
			}
			return true;
		}

		/*
		 * A method to return the value of the next element in the list
		 */
		@Override
		public E next() {
			if(!hasNext()) {
				throw new IndexOutOfBoundsException();
			}
			E temp = currNode.getElement();
			currNode = currNode.getNext();
			return temp;
		}

	}
	


	
	/************************************************************************
	 * 
	 * The method positions(), PositionInterable class and PositionIterator class
	 * have been fully implemented.  
	 * It depends the methods first(), after() that you are implementing. 
	 *
	 ************************************************************************/

	@Override
	public Iterable<Position<E>> positions() {
		return new PositionIterable();
	}
	
	/*
	 * Position iterator will return one Position at a time  
	 */
	private class PositionIterable implements Iterable<Position<E>> {

		@Override
		public Iterator<Position<E>> iterator() {
			return new PositionIterator();
		}
		
	}
	
	private class PositionIterator implements Iterator<Position<E>> {
		Position<E> p=first();
		
		@Override
		public boolean hasNext() {
			return p!=null;
		}

		@Override
		public Position<E> next() {
			Position<E> cur = p;
			p = after(p);
			return cur;
		}
		
	}

}
