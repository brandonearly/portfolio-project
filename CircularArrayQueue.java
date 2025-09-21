/**
 * 
 */
package cs2321;

import net.datastructures.Queue;

/**
 * @author ruihong-adm
 * @param <E>
 *
 */

public class CircularArrayQueue<E> implements Queue<E> {

	int front; //instance variable to track where the 'front' of the circular array is
	int rear; //instance variable to track where the 'rear' of the circular array is
	int size; //instance variable to track the number of elements in the array
	int checkSize; //instance variable to track the number of elements that CAN fit into the array
	E[] queue; //initializing our array

	public CircularArrayQueue(int queueSize) {
		checkSize = queueSize; //set our capacity size for the rest of this instance
		queue = (E[]) new Object[queueSize]; //create the array using Object in order to enforce generics
	}

	/*
	 * size method to track the number of elements in the array at a given point
	 */
	@Override
	public int size() {
		return size;
	}

	/*
	 * check method to verify if the array is empty
	 */
	@Override
	public boolean isEmpty() {
		if(size ==0) {
			return true;
		}
		return false;
	}

	/*
	 * method to return the 'front' of the queue, this is the element that was put into the array before the rest
	 */
	@Override
	public E first() {
		return queue[front];
	}
	
	/*
	 * removal method that returns the element at the front of the queue and then updates
	 * the value of the front of the queue as well as the 'size' of the array
	 */
	@Override
	public E dequeue(){
		E result = queue[front];
		queue[front] = null;
		front++;
		if(front == checkSize) {
			front = 0;
		}
		size--;
		return result;
	}

	/*
	 * add method to put elements into the queue, assuming there is space, hence the need for the 
	 * capacity size variable. If there is space however, we add the new element to the end of the queue
	 * and then update the 'pointer' rear that tracks where the end of the circular array is. 
	 */
	/* Throw the exception IllegalStateException when the queue is full */
	@Override
	public void enqueue(E e) throws IllegalStateException {
		if(size >= checkSize) {
			rear = front;
			throw new IllegalStateException("Queue is full"); 
		}
		if(rear == checkSize) {
			rear = 0;
		}
		if(size == 0) {
			queue[front] = e;
			rear++;
			size++;
			return;
		}
		queue[rear] = e;
		rear++;		
		size++;
	}
}