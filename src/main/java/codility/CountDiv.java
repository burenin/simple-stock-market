package codility;

public class CountDiv {

	public static void main(String[] args) {
		
		Solution solution = new Solution();
		int count = solution.solution(6, 11, 2);
		System.out.println("count: " + count);
		
	}
	
	static class Solution {
		
		
		public int solution(int a, int b, int k){
			int count = 0;
			int n = a;
			while (n <= b) {
				if (((count == 0) && ((n % k) == 0)) || (count > 0)) {
					count++;
					n += k;
				}
			}
			return count;
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
