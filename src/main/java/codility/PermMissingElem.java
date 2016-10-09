package codility;

public class PermMissingElem {

	public static void main(String[] args) {
		int[] a = {2, 3, 4, 5}; 
		Solution solution = new Solution();
		int missed = solution.solution(a, 4);
		System.out.println("missed: " + missed);
		System.out.println("missed2: " + solution.solution2(a));
	}
	
	static class Solution {
		public int solution(int [] a, int n){
			int missed = 0;
			int mustBeSum = (n * n + n)/2;
			int realSum = 0;
			for (int i : a){
				realSum += i;
			}
			missed = n+1 - (realSum - mustBeSum);
			return missed;
		}
		
		public int solution2(int [] a){
			int n = a.length + 1;
			int total = (n * (n + 1))/2;
			for (int i : a){
				total -=i;
			}
			return total;
		}
	}
	

}
