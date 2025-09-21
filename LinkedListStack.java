package cs2321;

import net.datastructures.Stack;

public class LinkedListStack<E> implements Stack<E> {
	
	//instance variable to track the size of the stack
	int stackSize = 0;
	
	//node class to implement the linked list
	private class Node{
		E element;
		Node next;
	}
	
	//initial node
	private Node head;


	/*
	 * size method to return the size of the stack at a given point
	 */
	@Override
	public int size() {
		return stackSize;
	}

	/*
	 * check method to verify if the stack is empty or not
	 */
	@Override
	public boolean isEmpty() {
		if(head == null) {
			return true;
		}
		return false;
	}

	/*
	 * push method to put a new element on the stack and update the head node and size of the stack as necessary
	 * if the stack is empty, then we just update the head node, otherwise we have to address the pointers as well
	 */
	@Override
	public void push(E e) {
		Node newNode = new Node();
		newNode.element = e;
		if(isEmpty()) {
			head = newNode;
		}
		else {
			newNode.next = head;
			head = newNode;
		}
		stackSize++;
	}

	/*
	 * top method to get the value that is contained by the head node at the 'top' of the stack
	 */
	@Override
	public E top(){
		if(isEmpty()) {
			return null;
		}
		return head.element;
	}

	/*
	 * pop method that returns and removes the top element of the stack assuming it exists 
	 * if the stack is empty then we can't remove anything from it, otherwise we update the pointers appropriately
	 * to 'remove' the top element of the stack
	 */
	@Override
	public E pop(){
		if(isEmpty()) {
			return null;
		}
		else {
			E temp = head.element;
			head = head.next;
			stackSize --;
			return temp;
		}
	}

}