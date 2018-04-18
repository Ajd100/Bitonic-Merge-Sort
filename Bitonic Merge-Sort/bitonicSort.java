// Bitonic Merge Sort
// Anthony Decker
// CSIS_3000 Final Project

import java.math.*;

public class bitonicSort {
	public static void main(String args[]) {
		//Creating time variables to show the computation time of sorting with this algorithm
		long time, startTime, endTime = (long) 0.0;
		int ascend = 1;
		int descend = 0;
		//Initializing the Array with an amount power of 2
		//int a[] = new int[(int)Math.pow(2,4)];
		int a[] = {5, 18, 7, 2, 9, 3, 19, 1};
		System.out.println("Unsorted array: ");
		//Printing out the randomly generated numbers in the array
			for (int i = 0; i < a.length; ++i){
		//		a[i] = (int) ((Math.random()*1000) + 1);
				System.out.print(a[i] + " ");
			}
		//Creating bitonicSort object to run the outside methods
		bitonicSort bs = new bitonicSort();

//		---------------------------------------------------------------------------------------------------------------------------------
		/*Sorting in ascending order*/

		startTime = System.currentTimeMillis();
		bs.sort(a, a.length, ascend);
		endTime = System.currentTimeMillis();
		time = (endTime - startTime);

		System.out.println("\n\nAscending Sorted array:");
		//Printing the sorted Array Ascending
		for (int i = 0; i < a.length; ++i){
			System.out.print(a[i] + " ");
		}
		System.out.println("\nThe time of completion for this sort is: " + time + "ms");

//		---------------------------------------------------------------------------------------------------------------------------------
		/*Sorting in descending order*/

		startTime = System.currentTimeMillis();
		bs.sort(a, a.length, descend);
		endTime = System.currentTimeMillis();
		time = (endTime - startTime);

		System.out.println("\nDescending Sorted array:");
		//Printing the sorted Array Descending
		for (int i = 0; i < a.length; ++i){
			System.out.print(a[i] + " ");
		}
		System.out.println("\nThe time of completion for this sort is: " + time + "ms");
	}

	//I have this method so that I don't have to statically call the information.
	//Takes the array, it's length, and what direction it will be sorted in and passes it on to another method.

	public void sort(int a[], int arrLength, int dir) {
		bitonicSort(a, 0, arrLength, dir);
	}

	//This is the method that makes it bitonicMergeSort RECURSIVE
	//It splits the array in half and sorts the first half in ascending order
	//The second half is sorted in descending order
	//Once they both have been sorted, they are merged into the direction specified

	public void bitonicSort(int a[], int low, int cnt, int dir) {
		if (cnt > 1) {
			int k = cnt/2;

			// sort in ascending order
			bitonicSort(a, low, k, 1);
			//This is to print the entire swapping process of the first half
			/* for(int i = 0; i < a.length; ++i){
				System.out.print(a[i] + " ");
			}
			System.out.print("\n"); */
			// sort in descending order
			bitonicSort(a,low+k, k, 0);

			// Will merge whole sequence in whichever sequence dir is given
			bitonicMerge(a, low, cnt, dir);

		}
	}

	//This is a recursive method to "merge" the parallel arrays
	//Both the bitonicMerge method calls are given the array, the starting position
	//how big the 'partitioned' array is, and the direction of sorting

	public void bitonicMerge(int a[], int low, int cnt, int dir) {
		if (cnt > 1) {
			int k = cnt/2; //This creates an integer to split the array in 2
			for (int i = low; i < low + k; ++i) {
				swapVar(a,i, i+k, dir);
			}
			//This is for printing out the entire sort to the console.
		/* 	for(int i = 0; i < a.length; ++i){
				System.out.print(a[i] + " ");
			}
			System.out.print("\n"); */
			bitonicMerge(a, low, k, dir);
			bitonicMerge(a, low+k, k, dir);
		}
	}

	//Checking which way it needs to be sorted and if the elements match the parameters, swap them

	public void swapVar(int a[], int i, int j, int dir) {
		if ((a[i] > a[j] && dir == 1) || (a[i] < a[j] && dir == 0)) {
			// Swapping elements
			int temp = a[i];
			a[i] = a[j];
			a[j] = temp;
		}
	}
}
