package codility;

public class MashroomPicker {

	public static void main(String[] args) {
		int[] a = {2, 3, 7, 5, 1, 3, 9}; 
		
		Solution solution = new Solution();
		int mashrooms = solution.solution(4, 6, a);
		System.out.println("mashrooms: " + mashrooms);
		
	}
	
	static class Solution {
		
		
		public int solution(int k, int m, int [] a){
			int mashrooms = 0;
			int length = a.length;
			int[] prefixSums = prefixSums(a);
			for (int p = 0; p < (Math.min(m, k) + 1); p++) {
				int leftPos = k-p;
				int rightPos = Math.min(length-1, Math.max(k, k+m-2*p));
				mashrooms = Math.max(mashrooms, countTotal(prefixSums, leftPos, rightPos));
			}
			for (int p = 0; p < Math.min(m+1, length-k); p++) {
				int rightPos = k + p;
				int leftPos = Math.max(0, Math.min(k, k - (m - 2 * p)));
				mashrooms = Math.max(mashrooms, countTotal(prefixSums, leftPos, rightPos));
			}
			return mashrooms;
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
