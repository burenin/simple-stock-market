package codility;

import java.util.HashMap;
import java.util.Map;

public class FrogJmp {

	public static void main(String[] args) {
		int [] a = {3, 1, 2, 4, 3};
		
		Solution solution = new Solution();
		int min = solution.solution(a);
		System.out.println("Min: " + min);
	}
	
	static class Solution {
		public int solution(int[] a){
			int minimum = Integer.MAX_VALUE;
			int length = a.length;
			int [] diff = new int[length-1];
			for (int i = 0; i < length; i++) {		// int [] a = {3, 1, 2, 4, 3};
				int n = a[i];
				if (i < (length-1)){
					diff[i] = n;
					if (i > 0){
						diff[i] += diff[i-1];
						
					}
					
				} 
				if (i > 0){
					for (int j = (i-1); j >= 0; j--) {
						diff[j] -= n;
						if ( i == (length -1)) {
							int abs = Math.abs(diff[j]);
							if (abs < minimum){
								minimum = abs;
							}
						}
					}
				}
			}
			return minimum;
		}
	}
	

}
