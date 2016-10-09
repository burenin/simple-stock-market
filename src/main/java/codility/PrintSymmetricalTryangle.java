package codility;

public class PrintSymmetricalTryangle {

	public static void main(String[] args) {
		int n = 4;
		for (int i = n; i > 0; i--){
			for (int j = 0; j < (n-i); j++){
				System.err.print("  ");
			}
			for (int j = 0; j < (2*i - 1); j++) {
				System.err.print("* ");
				
			}
			System.err.println();
		}

	}

}
