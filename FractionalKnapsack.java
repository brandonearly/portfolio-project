package cs2321;

import net.datastructures.*;
import java.util.Comparator; 
import cs2321util.HeapAPQ;

/**
 * @author:
 *
 */
public class FractionalKnapsack{

   
	/**
	 * Goal: Choose items with maximum total benefit but with weight at most W.
	 *       You are allowed to take fractional amounts from items.
	 *       
	 * @param items items[i][0] is weight for item i
	 *              items[i][1] is benefit for item i
	 * @param knapsackWeight
	 * @return The maximum total benefit. Please use double type operation. For example 5/2 = 2.5
	 * 		 
	 */
	public static double MaximumValue(int[][] items, int knapsackWeight){
		 //track the number of elements in the array
		int numRows = items.length;
		
		//i made my own comparator using the comparator interface to compare the values in the PQ
		Comparator<Double> unitPriceComp = new Comparator<Double>() {
			public int compare(Double u1, Double u2) {
				//backwards because its a min-heap
				return Double.compare(u2, u1);
			}
		};
		
		HeapAPQ<Double,Double> entry = new HeapAPQ<Double,Double>(unitPriceComp,numRows);
		
		//iterate through the array and calculate the unit price for each item
		for(int i = 0; i < numRows; i++) {
			double weight = (double) items[i][0];
			double benefit = (double) items[i][1];
			double unitPrice =  benefit / weight;
			entry.insert(unitPrice, weight);
		}
		
		double totalBenefit = 0;
		double currWeight = 0;
		
		//while the PQ still has items in it and the current weight of the knapsack isn't above the limit, add as much
		//of the item with the highest current unit price into our knapsack, and adjust the current weight as necessary
		while(!entry.isEmpty() && currWeight < knapsackWeight) {
			Entry<Double,Double> newEntry = entry.removeMin();
			double newUnitPrice = newEntry.getKey();
			double newWeight = newEntry.getValue();
			
			if(currWeight + newWeight <= knapsackWeight) {
				totalBenefit += newUnitPrice * newWeight;
				currWeight += newWeight;
			}
			else {
				double weightLeft = knapsackWeight - currWeight;
				totalBenefit += newUnitPrice * weightLeft;
				currWeight = knapsackWeight;
			}
		}
		
		return totalBenefit;
	}
}
