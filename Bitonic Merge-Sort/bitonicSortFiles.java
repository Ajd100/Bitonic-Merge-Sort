// Bitonic Merge Sort importing a file converting to an arraylist
	// And sorting the array given
// Anthony Decker
//June 17, 2018


import java.math.*;
import java.util.*;
import java.util.stream.IntStream;
import java.util.stream.Collectors;
import java.io.*;

public class bitonicSortFiles {
	public static void main(String args[]) throws IOException{
		//Creating time variables to show the computation time of sorting with this algorithm
		long time, startTime, endTime = (long) 0.0;
		int ascend = 1;
		int descend = 0;
		int[] numArray = fileToStringArray("./numberFile.txt");

		//This line is because I'm lazy and want to use the new IntStream in Java 8
		//After the int[] is created, it is passed into a List<Integer>
		//This uses Instream, introduced in Java 8.
		//It's basically grabbing each element, boxing them, then protecting them instead of using Arrays.toList()
		List<Integer> numList = IntStream.of(numArray).boxed().collect(Collectors.toList());

		//Passing the List to the ArrayList
		ArrayList<Integer> aList = new ArrayList<Integer>(numList);

		//Printing the objects in the ArrayList
		System.out.println("Unsorted Array");
		for(int i = 0; i < aList.size(); ++i){
			System.out.print(aList.get(i) + " ");
		}
		//Creating bitonicSort object to run the outside methods
		bitonicSortFiles bs = new bitonicSortFiles();
		bs.sort(aList, aList.size(), ascend);
		System.out.println("\n\nSorted without making sure it's a power of 2");
		for(int i = 0; i < aList.size(); ++i){
			System.out.print(aList.get(i) + " ");
		}

		//Using Bitwise to check if it's a power of two, if not, add zeros until it is
		int aL = aList.size();
		while((aL & (aL - 1)) != 0){ //BITWISE
			aList.add(0);
			aL = aList.size();
		}

//		---------------------------------------------------------------------------------------------------------------------------------
		/*Sorting in ascending order*/

		//Getting the operating time of the ascending sort
		startTime = System.currentTimeMillis();
		bs.sort(aList, aList.size(), ascend);
		endTime = System.currentTimeMillis();
		time = (endTime - startTime);

		//This is counting the amount of zeros in the array - for later sort
		//It also removes all the zeros in the array because they weren't there in the first place
		int zCounter = 0;
		while (aList.contains(0)){
			aList.remove((Integer) 0);
			zCounter++;
		}

		System.out.println("\n\nAscending Sorted array:");
		//Printing the sorted Array Ascending
		for(int i = 0; i < aList.size(); ++i){
			System.out.print(aList.get(i) + " ");
		}
		System.out.println("\nThe time of completion for this sort is: " + time + "ms");

//		---------------------------------------------------------------------------------------------------------------------------------
		/*Sorting in descending order*/

		//Adding the removed zeros back to the list for descending sort - This is where the zCounter comes in
		for(int i = 0; i < zCounter; ++i){
			aList.add(0);
		}

		//Getting the operating time of the Descending sort
		startTime = System.currentTimeMillis();
		bs.sort(aList, aList.size(), descend);
		endTime = System.currentTimeMillis();
		time = (endTime - startTime);

		//Removing zeros from the list again
		while (aList.contains(0)){
			aList.remove((Integer) 0);
		}

		System.out.println("\nDescending Sorted array:");
		//Printing the sorted Array Descending
		for(int i = 0; i < aList.size(); ++i){
			System.out.print(aList.get(i) + " ");
		}
		System.out.println("\nThe time of completion for this sort is: " + time + "ms");
	}

	//I have this method so that I don't have to statically call the information.
	//Takes the arraylist, it's length, and what direction it will be sorted in and passes it on to another method.

	public void sort(ArrayList<Integer> a, int arrLength, int dir) {
		bitonicSort(a, 0, arrLength, dir);
	}

	//This is the method that makes it bitonicMergeSort RECURSIVE
	//It splits the array in half and sorts the first half in ascending order
	//The second half is sorted in descending order
	//Once they both have been sorted, they are merged into the direction specified

	public void bitonicSort(ArrayList<Integer> a, int low, int cnt, int dir) {
		if (cnt > 1) {
			int k = cnt/2;

			// sort in ascending order
			bitonicSort(a, low, k, 1);

			// sort in descending order
			bitonicSort(a,low+k, k, 0);

			// Will merge whole sequence in whichever sequence dir is given
			bitonicMerge(a, low, cnt, dir);
		}
	}

	//This is a recursive method to "merge" the parallel arrays
	//Both the bitonicMerge method calls are given the array, the starting position
	//how big the 'partitioned' array is, and the direction of sorting

	public void bitonicMerge(ArrayList<Integer> a, int low, int cnt, int dir) {
		if (cnt > 1) {
			int k = cnt/2; //This creates an integer to split the array in 2
			for (int i = low; i < low + k; ++i) {
				swapVar(a,i, i+k, dir);
			}
			bitonicMerge(a, low, k, dir);
			bitonicMerge(a, low+k, k, dir);
		}
	}

	//Checking which way it needs to be sorted and if the elements match the parameters, swap them

	public void swapVar(ArrayList<Integer> a, int i, int j, int dir) {
		if ((a.get(i) > a.get(j) && dir == 1) || (a.get(i) < a.get(j) && dir == 0)) {
			// Swapping elements using the Collections built in swap method
			Collections.swap(a, i, j);
		}
	}

	/* This calls upon fileToString to convert a file to a string
		 It then will convert the stringed context to an array
		 Because I'm trying to send it to an ArrayList<Integer>,
		 I'm parsing the file into an int array */
	//TODO Try to figure out how to get this to return an integer instead of string
	public static int[] fileToStringArray (String fileName) throws IOException{
		String data = fileToString(fileName);
		String str = cleanText(data);
	//	numArray = Integer.parseInt(str);
		String[] wordArray = str.split(" ");
		int[] numArray = new int[wordArray.length];
		try{
			for(int i = 0; i< wordArray.length; ++i){
			numArray[i] = Integer.parseInt(wordArray[i]);
			}
		}
		catch (Exception NumberFormatException){
			System.out.println("Hmmm... Your file must contain characters.\nOnce those characters are eliminated, try again!");
			System.exit(0);
		}
	//	int[] numArray = Integer.parseInt(wordArray);
		return numArray;
	}

	/* This method is creating a string and calling upon
		 a file to send all data in that file to the string */
	public static String fileToString (String fileName){
		String result = "";
		try{
			FileInputStream file = new FileInputStream(fileName);
			byte[] b = new byte[file.available()];
			//System.out.println("Number of byes in this file = " + file.available());
			file.read(b);
			file.close();
			result = new String(b);
		}
		catch (Exception e) {
			System.out.println("oops");
		}
		return result;
	}

	/* This method cleans up the text in the file
		 so that our created array doesn't contain
		 special characters. */
	public static String cleanText(String s){
		s = s.replaceAll("\\p{Punct}", "");
		s = s.replaceAll("\\s+", " ");
		return s;
	}

}
