package codility;

import java.util.Arrays;

public class Reverse {

	public static void main(String[] args) {
		// reverse 1 2 3 4 5 6 7 8 9 10 -> 10 9 8 7 6 5 4 3 2 1
		int[] array = {1, 2, 3, 4, 5, 6};
		System.err.println("Before: " + Arrays.toString(array));
		for (int i = 0; i < (array.length)/2; i++){
			int oppositeIndex = array.length-1-i;
			int a = array[i];
			int b = array[oppositeIndex];
			array[i] = b;
			array[oppositeIndex] = a;
		}
		System.err.println("After: " + Arrays.toString(array));
	}

}
