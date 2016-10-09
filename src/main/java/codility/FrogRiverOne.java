package codility;

public class FrogRiverOne {

	public static void main(String[] args) {
		int[] a = {1, 3, 1, 4, 2, 3, 5, 4}; 
		
		Solution solution = new Solution();
		int min = solution.solution(5, a);
		System.out.println("min a: " + min);
		
	}
	
	static class Solution {
		
		
		public int solution(int x, int [] a){
			int [] count = new int[x+1];
			int progress = 0;
			int last = 0;
			for (int second = 0; second < a.length; second++){
				int leaf = a[second];
				if (leaf >= 1 && leaf <= x){
					if (count[leaf] == 0){
						count[leaf] = leaf;
						progress++;
						last = second;
					}
				}
				if (progress == x){
					return last;
				}
			}
			return -1;
		}
	}
	

}
