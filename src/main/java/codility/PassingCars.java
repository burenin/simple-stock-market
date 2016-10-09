package codility;

public class PassingCars {

	public static void main(String[] args) {
		int[] a = {0, 1, 0, 1, 1}; 
		
		Solution solution = new Solution();
		int pairs = solution.solution(a);
		System.out.println("pairs: " + pairs);
		
	}
	
	static class Solution {
		
		
		public int solution(int [] a){
			int pairs = 0;
			int[] prefixSums = prefixSums(a);
			int lastIndex = a.length-1;
			for (int i = 0; i <= lastIndex; i++) {
				int n = a[i];
				
				if ((n == 0) && (i < lastIndex)) {
					pairs += countTotal(prefixSums, i, lastIndex);
				}
			}
			if (pairs > 1_000_000_000){
				pairs = -1;
			}
			return pairs;
		}
		
		private int[] prefixSums(int[] a){
			int lenght = a.length;
			int[] p = new int[lenght + 1];
			for (int i = 1; i <= lenght; i++) {
				p[i] = p[i-1] + a[i-1];
			}
			return p;
		}
		
		private int countTotal(int[] p, int x, int y) {
			return p[y+1] - p[x];
		}
	}
	

}
