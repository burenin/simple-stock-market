package codility;

public class Fibonacci {

	public static void main(String[] args) {
		int a = 0;
		int b = 1;
		int c = 0;
		int n = 10;
		int i = 2;
		while (a <= n) {
			System.err.print(a + " ");
			c = a + b;
			
			a = b;
			b = c;
		}
	}
	
	
	
}
