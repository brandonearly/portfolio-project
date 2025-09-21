package cs2321;

import net.datastructures.*;
import java.util.Comparator; 
import cs2321util.HeapAPQ;


public class TaskScheduling {
	/**
	 * Goal: Perform all the tasks using a minimum number of machines. 
	 * 
	 *       
	 * @param tasks tasks[i][0] is start time for task i
	 *              tasks[i][1] is end time for task i
	 * @return The minimum number or machines
	 */
	public static int NumOfMachines(int[][] tasks) {
		//check the number of elements in the array
		int numRows = tasks.length;

		//make sure we aren't dealing with an empty array
		if(numRows == 0) {
			return 0;
		}

		int numMachines = 0;
		int activeMachines = 0;

		//queues for start and end times
		HeapAPQ<Integer,Integer> startTimeQueue = new HeapAPQ<Integer,Integer>(numRows);

		HeapAPQ<Integer,Integer> endTimeQueue = new HeapAPQ<Integer,Integer>(numRows);

		//get all the tasks into the start time queue
		for(int i = 0; i < tasks.length; i++) {
			startTimeQueue.insert(tasks[i][0],tasks[i][1]);
		}

		//process each task one at a time
		while(!startTimeQueue.isEmpty()) {
			Entry<Integer, Integer> task = startTimeQueue.removeMin();
			int startTime = task.getKey();
			int endTime = task.getValue();
			
			//see if we need an extra machine for the current task or if there is one available
			while(!endTimeQueue.isEmpty() && endTimeQueue.min().getKey() <= startTime) {
				endTimeQueue.removeMin();
				activeMachines--;
			}
			
			endTimeQueue.insert(endTime, 0);
			activeMachines++;
			
			//track the maximum number of machines used
			if(activeMachines > numMachines) {
				numMachines = activeMachines;
			}
		}

		return numMachines;
	}
}