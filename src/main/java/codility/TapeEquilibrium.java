package codility;

public class TapeEquilibrium {

	public static void main(String[] args) {
		
		Solution solution = new Solution();
		int min = solution.solution(10, 85, 30);
		System.out.println("Min: " + min);
	}
	
	static class Solution {
		public int solution(int x, int y, int d){
			double minimum = (double)(y-x) / d;
			return (int)Math.ceil(minimum);
		}
	}
	

}
