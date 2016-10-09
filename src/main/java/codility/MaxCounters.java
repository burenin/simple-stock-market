package codility;

import java.util.Arrays;

public class MaxCounters {

	public static void main(String[] args) {
		int[] a = {3, 4, 4, 6, 1, 4, 4}; 
		
		Solution solution = new Solution();
		int[] counters = solution.solution(5, a);
		System.out.println("counters: " + Arrays.toString(counters));
		
	}
	
	static class Solution {
		
		
		public int[] solution(int n, int [] a){
			int [] counters = new int[n];
			int max = 0;
			for (int i : a){
				if ((i >= 1) && (i <= n)) {
					int index = i-1;
					counters[index]++;
					if (counters[index] > max){
						max = counters[index];
					}
				}else if (i == (n+1)) {
					Arrays.fill(counters, max);
				}
			}
			return counters;
		}
	}
	

}
