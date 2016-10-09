package codility;

import java.util.Arrays;

public class CyclicRotation {

	public static void main(String[] args) {
		int [] a = {3, 8, 9, 7, 6};
		System.out.println("Before: " + Arrays.toString(a));
		Solution solution = new Solution();
		int [] b = solution.solution(a, 6);
		System.out.println("After: " + Arrays.toString(b));
	}
	
	static class Solution {
		public int[] solution(int[] a, int k){
			int length = a.length;
			int newIndex = k % length;
			int [] result = new int[length];
			for (int i = 0; i < length; i++) {
				result[(newIndex+i) % length] = a[i];
			}
			return result;
		}
	}
	

}
