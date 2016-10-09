package codility;

public class MissingInteger {

	public static void main(String[] args) {
		int[] a = {1, 3, 6, 4, 1, 2}; 
		
		Solution solution = new Solution();
		int min = solution.solution(a);
		System.out.println("min a: " + min);
		
	}
	
	static class Solution {
		
		
		public int solution(int [] a){
			int[] count = new int[a.length+1];
			int min = -1;
			int max = -1;
			for (int i : a){
				if (i > 0){
					count[i]++;
					if ((min == -1) || (i < min)){
						min = i;
					}
					if ((max == -1) || (i > max)){
						max = i;
					}
				}
			}
			for (int i = min; i <= max; i++){
				if (count[i] == 0){
					return i;
				}
			}
			return -1;
		}
	}
	

}
