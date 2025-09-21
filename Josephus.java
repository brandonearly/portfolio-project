package cs2321;

public class Josephus{
	/**
	 * All persons sit in a circle. When we go around the circle, initially starting
	 * from the first person, then the second person, then the third... 
	 * we count 1,2,3,.., k-1. The next person, that is the k-th person is out. 
	 * Then we restart the counting from the next person, go around, the k-th person 
	 * is out. Keep going the same way, when there is only one person left, she/he 
	 * is the winner. 
	 *  
	 * @parameter persons  an array of string which contains all player names.
	 * @parameter k  an integer specifying the k-th person will be kicked out of the game
	 * @return return a array in the order when the players were out of the game. 
	 *         The last one in the array is the winner.  
	 */
	public String[] order(String[] persons, int k ) {

		int startSize = persons.length; 	//instance variable to keep record of the original size of the array parameter
		
		int currentStep = k; 	//instance variable to track the current value of the 'counter' between removals
		
		CircularArrayQueue<String> queue = new CircularArrayQueue<String>(startSize); 	//the queue we use to solve the problem
		
		String[] result = new String[startSize]; 	//the array for the results of the removed Strings
		
		int currentPos = 0; 	//an instance variable to track the index of the result array

		
		//A for loop to move every element in the persons array into the queue
		for(int i = 0; i < startSize; i++) {
			queue.enqueue(persons[i]);
		}

		/*
		 * This loop checks a few things: if the array is empty, if not it continues to execute, if there is
		 * only one element in the queue, at which point that element is the 'winner', and the value of the 
		 * current step that we are using to keep track of where we are in the process of removing everyone.
		 * when the current step is equal to 1, the value at the front of the queue at that point is removed and
		 * stored in the result array. If none of those checks are valid, then it simply cycles through the queue and
		 * continues to count the step.
		 */
		while(!queue.isEmpty()) {
			if(queue.size() ==1) {
				result[currentPos] = queue.dequeue();
				break;
			}
			if(currentStep == 1) {
				result[currentPos] = queue.dequeue();
				currentPos++;
				currentStep = k;
			}
			else {
				String temp = queue.dequeue();
				queue.enqueue(temp);
				currentStep--;
			}
		}

		return result;
	}
}
