package codility;

public class PermCheck {

	public static void main(String[] args) {
		int[] a = {4, 1, 3, 2}; 
		int[] a2 = {4, 1, 3}; 
		Solution solution = new Solution();
		int permutation = solution.solution(a);
		System.out.println("permutation a: " + (permutation == 1));
		permutation = solution.solution(a2);
		System.out.println("permutation a2: " + (permutation == 1));
	}
	
	static class Solution {
		
		
		public int solution(int [] a){
			int[] count = new int[a.length+1];
			int counter = 0;
			for (int i : a){
				if (i > a.length){
					return 0;
				}
				
				if (count[i] == 0){
					count[i]++;
					counter++;
				}else{
					return 0;
				}
			}
			return counter == a.length ? 1 :0;
		}
	}
	

}
